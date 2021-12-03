package com.velvet.collectionsandmaps.ui.benchmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.BenchmarkData;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomViewModel extends ViewModel {

    private final MutableLiveData<Integer> validationErrorData = new MutableLiveData<>();
    private final MutableLiveData<List<BenchmarkData>> itemsData = new MutableLiveData<>();
    private final MutableLiveData<Integer> buttonText = new MutableLiveData<>();
    private final IndexRelatedMethods methods;
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(28,
            28,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            r -> new Thread(r));

    public CustomViewModel(IndexRelatedMethods methods) {
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
        itemsData.setValue(methods.createList());
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
            List<BenchmarkData> measuredItems = methods.createList();
            executor.execute(() -> {
                for (BenchmarkData item :
                        measuredItems) {
                    item.setProgressState(true);
                    executor.submit(() -> {
                        item.setTime(methods.measureTime(item, items));
                        item.setProgressState(false);
                        itemsData.postValue(measuredItems);
                    });
                }
            });
            buttonText.setValue(R.string.button_start);
        }
    }


    private boolean measurementRunning() {
        return false;
    }

    private void stopMeasurements() {
        executor.shutdown();
    }

    @Override
    protected void onCleared() {
        if (measurementRunning()) {
            stopMeasurements();
        }
        super.onCleared();
    }
}
