package com.velvet.collectionsandmaps.ui.benchmark;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.BenchmarkData;
import com.velvet.collectionsandmaps.model.CollectionBenchmark;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BenchmarkViewModel extends ViewModel {

    private final MutableLiveData<Integer> validationErrorData = new MutableLiveData<>();
    private final MutableLiveData<List<BenchmarkData>> itemsData = new MutableLiveData<>();
    private final MutableLiveData<Integer> buttonText = new MutableLiveData<>();
    private final CollectionBenchmark benchmark;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public BenchmarkViewModel(CollectionBenchmark benchmark) {
        this.benchmark = benchmark;
    }

    public LiveData<Integer> getButtonText() {
        return buttonText;
    }

    public LiveData<Integer> getValidationErrorData() {
        return validationErrorData;
    }

    public LiveData<List<BenchmarkData>> getItemsData() {
        return itemsData;
    }

    public int getNumberOfColumn() {
        return benchmark.getNumberOfColumn();
    }

    public void setup() {
        itemsData.setValue(benchmark.createList(false));
        buttonText.setValue(R.string.button_start);
    }

    public void tryToMeasure(String itemsStr) {
        int items;
        try {
            items = Integer.parseInt(itemsStr.trim());
        } catch (NumberFormatException e) {
            validationErrorData.setValue(R.string.invalid_number);
            return;
        }

        if (measurementRunning()) {
            stopMeasurements();
        } else {
            itemsData.setValue(benchmark.createList(true));
            disposable.add(Observable.fromIterable(benchmark.createList(false))
                    .observeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<BenchmarkData>() {
                        @Override
                        public void onNext(@NonNull BenchmarkData benchmarkData) {
                            benchmarkData.setTime(benchmark.measureTime(benchmarkData, items));
                            List<BenchmarkData> tempList = itemsData.getValue();
                            tempList.set(tempList.indexOf(benchmarkData), benchmarkData);
                            itemsData.postValue(tempList);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e("Error", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            buttonText.postValue(R.string.button_start);
                        }
                    }));
        }
    }

    private boolean measurementRunning() {
        return buttonText.getValue() != R.string.button_start;
    }

    private void stopMeasurements() {
        disposable.clear();
    }

    @Override
    protected void onCleared() {
        if (measurementRunning()) {
            stopMeasurements();
        }
        super.onCleared();
    }
}
