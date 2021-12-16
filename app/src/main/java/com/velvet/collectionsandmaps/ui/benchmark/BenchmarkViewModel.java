package com.velvet.collectionsandmaps.ui.benchmark;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.benchmark.BenchmarkData;
import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BenchmarkViewModel extends ViewModel {

    private final MutableLiveData<Integer> validationErrorData = new MutableLiveData<>();
    private final MutableLiveData<List<BenchmarkData>> itemsData = new MutableLiveData<>();
    private final MutableLiveData<Integer> buttonText = new MutableLiveData<>();
    private final Benchmarks benchmark;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public BenchmarkViewModel(Benchmarks benchmark) {
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
            final List<BenchmarkData> measuredItems = benchmark.createList(false);
            disposable.add(Observable.fromIterable(measuredItems)
                    .doOnSubscribe(d -> buttonText.postValue(R.string.button_stop))
                    .doOnNext(item -> item.setTime(benchmark.measureTime(item, items)))
                    .doFinally(() -> {
                        buttonText.postValue(R.string.button_start);
                        disposable.clear();
                    })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(benchmarkData -> {
                                final List<BenchmarkData> tempList = new ArrayList<>(itemsData.getValue());
                                final int i = measuredItems.indexOf(benchmarkData);
                                tempList.set(i, benchmarkData);
                                itemsData.setValue(tempList);
                            },
                            throwable -> Log.e("Error", throwable.getMessage())));
        }
    }

    private boolean measurementRunning() {
        return disposable.size() != 0;
    }

    private void stopMeasurements() {
        disposable.clear();
        reset();
    }

    private void reset() {
        itemsData.setValue(benchmark.createList(false));
        buttonText.setValue(R.string.button_start);
    }

    @Override
    protected void onCleared() {
        if (measurementRunning()) {
            stopMeasurements();
        }
        disposable.dispose();
        super.onCleared();
    }
}
