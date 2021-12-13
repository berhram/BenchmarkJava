package com.velvet.collectionsandmaps.ui.benchmark;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.BenchmarkData;
import com.velvet.collectionsandmaps.model.CollectionBenchmark;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BenchmarkViewModel extends ViewModel {

    private final MutableLiveData<Integer> validationErrorData = new MutableLiveData<>();
    private final MutableLiveData<List<BenchmarkData>> itemsData = new MutableLiveData<>();
    private final MutableLiveData<Integer> buttonText = new MutableLiveData<>();
    private final CollectionBenchmark methods;
    private final Scheduler scheduler = Schedulers.io();

    public BenchmarkViewModel(CollectionBenchmark methods) {
        this.methods = methods;
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
        return methods.getNumberOfColumn();
    }

    public void setup() {
        itemsData.setValue(methods.createList(false));
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
            buttonText.setValue(R.string.button_start);
        } else {
            buttonText.setValue(R.string.button_stop);
            itemsData.setValue(methods.createList(true));
            Observable.fromIterable(itemsData.getValue())
                    .observeOn(scheduler)
                    .map(benchmarkData -> {
                        benchmarkData.setProgressState(false);
                        benchmarkData.setTime(methods.measureTime(benchmarkData,items));
                        List<BenchmarkData> tempList = itemsData.getValue();
                        tempList.set(tempList.indexOf(benchmarkData), benchmarkData);
                        return tempList;
                    })
                    .subscribe(
                    tempList -> {
                        itemsData.postValue(tempList);
                        },
                    throwable -> {
                        Log.e("Error", throwable.getMessage());
                        },
                    () -> {
                        buttonText.postValue(R.string.button_start);
                    });
        }
    }

    private boolean measurementRunning() {
        if (buttonText.getValue() == R.string.button_start) {
            return false;
        }
        else {
            return true;
        }
    }

    private void stopMeasurements() {
        scheduler.shutdown();
    }

    @Override
    protected void onCleared() {
        if (measurementRunning()) {
            stopMeasurements();
        }
        super.onCleared();
    }
}
