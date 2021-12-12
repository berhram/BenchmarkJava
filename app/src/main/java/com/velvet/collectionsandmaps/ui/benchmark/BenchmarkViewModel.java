package com.velvet.collectionsandmaps.ui.benchmark;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.BenchmarkData;
import com.velvet.collectionsandmaps.model.CollectionBenchmark;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class BenchmarkViewModel extends ViewModel {

    private final MutableLiveData<Integer> validationErrorData = new MutableLiveData<>();
    private final MutableLiveData<List<BenchmarkData>> itemsData = new MutableLiveData<>();
    private final MutableLiveData<Integer> buttonText = new MutableLiveData<>();
    private final CollectionBenchmark methods;

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
            ArrayList<Integer> posList = new ArrayList<>();
            for (int i = 0; i < itemsData.getValue().size(); i++) {
                posList.add(itemsData.getValue().get(i).hashCode());
            }
            itemsData.setValue(methods.createList(true));
            Observable.fromIterable(itemsData.getValue()).subscribe(
                    benchmarkData -> {
                        benchmarkData.setTime(methods.measureTime(benchmarkData, items));
                        benchmarkData.setProgressState(false);
                        List<BenchmarkData> tempList = itemsData.getValue();
                        tempList.set(posList.indexOf(benchmarkData.hashCode()), benchmarkData);
                        itemsData.postValue(tempList);
                        },
                    throwable -> {
                        Log.e("Error", throwable.getMessage());
                        },
                    () -> {
                        buttonText.setValue(R.string.button_start);
                    });
        }
    }

    private boolean measurementRunning() {
        return false;
    }

    private void stopMeasurements() {

    }

    @Override
    protected void onCleared() {
        if (measurementRunning()) {
            stopMeasurements();
        }
        super.onCleared();
    }
}
