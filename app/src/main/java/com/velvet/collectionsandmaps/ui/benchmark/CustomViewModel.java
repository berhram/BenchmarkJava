package com.velvet.collectionsandmaps.ui.benchmark;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.BenchmarkData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomViewModel extends ViewModel {

    private final MutableLiveData<Integer> validationErrorData = new MutableLiveData<>();
    private final MutableLiveData<List<BenchmarkData>> itemsData = new MutableLiveData<>();
    private final MutableLiveData<Integer> buttonText = new MutableLiveData<>();
    private final int index;
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(28,
            28,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            r -> new Thread(r));

    public CustomViewModel(int index) {
        this.index = index;
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
        if (index == 0) {
            return 3;
        } else {
            return 2;
        }
    }

    private List<BenchmarkData> createList() {
        final List<BenchmarkData> list = new ArrayList<>();
        if (index == 0) {
            list.add(new BenchmarkData(R.string.array_list, R.string.add_to_start, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.linked_list, R.string.add_to_start, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.add_to_start, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.array_list, R.string.add_to_middle, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.linked_list, R.string.add_to_middle, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.add_to_middle, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.array_list, R.string.add_to_end, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.linked_list, R.string.add_to_end, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.add_to_end, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.array_list, R.string.search, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.linked_list, R.string.search, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.search, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.array_list, R.string.remove_from_start, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.linked_list, R.string.remove_from_start, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.remove_from_start, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.array_list, R.string.remove_from_middle, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.linked_list, R.string.remove_from_middle, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.remove_from_middle, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.array_list, R.string.remove_from_end, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.linked_list, R.string.remove_from_end, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.remove_from_end, R.string.notApplicable, R.string.milliseconds));
        }
        else {
            list.add(new BenchmarkData(R.string.hash_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.tree_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.hash_map, R.string.search, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.tree_map, R.string.search, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.hash_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.tree_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds));
        }
        return list;
    }

    public void setup() {
        itemsData.setValue(createList());
        buttonText.setValue(R.string.button_start);
    }

    public void tryToMeasure(String itemsStr) {
        int items;
        try {
            items = Integer.parseInt(itemsStr.trim());
        } catch (NumberFormatException e) {
            validationErrorData.postValue(R.string.invalid_number);
            return;
        }

        if (measurementRunning()) {
            stopMeasurements();
            buttonText.postValue(R.string.button_start);
        } else {
            buttonText.postValue(R.string.button_stop);
            List<BenchmarkData> measuredItems = createList();
            executor.execute(() -> {
                for (BenchmarkData item :
                        measuredItems) {
                    item.setProgressState(true);
                    executor.submit(() -> {
                        item.setTime(measureTime(item, items));
                        item.setProgressState(false);
                        itemsData.postValue(measuredItems);
                    });
                }
            });
            buttonText.postValue(R.string.button_start);
        }
    }

    private double measureTime(BenchmarkData item, int iterations) {
        double startTime;
        if (index == 0) {
            List<String> measuredList;
            if (item.collectionName == R.string.array_list) {
                measuredList = new ArrayList<>(Collections.nCopies(iterations-1, "Denver"));
            } else if (item.collectionName == R.string.linked_list) {
                measuredList = new LinkedList<>(Collections.nCopies(iterations-1, "Denver"));
            } else {
                measuredList = new CopyOnWriteArrayList<>(Collections.nCopies(iterations-1, "Denver"));
            }
            Random random = new Random();
            measuredList.add(random.nextInt(iterations), "Detroit");
            if (item.operation == R.string.add_to_start) {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredList.add(0, "Denver");
                }
            } else if (item.operation == R.string.add_to_middle) {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredList.add(measuredList.size()/2, "Denver");
                }
            } else if (item.operation == R.string.add_to_end) {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredList.add(measuredList.size(), "Denver");
                }
            } else if (item.operation == R.string.search) {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredList.indexOf("Detroit");
                }
            } else if (item.operation == R.string.remove_from_start) {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredList.remove(0);
                }
            } else if (item.operation == R.string.remove_from_middle) {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredList.remove(measuredList.size()/2);
                }
            } else {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredList.remove(measuredList.size()-1);
                }
            }
        }
        else {
            Map<String, String> measuredMap;
            if (item.collectionName == R.string.hash_map) {
                measuredMap= new HashMap<>();
            } else {
                measuredMap= new TreeMap<>();
            }
            for (int i = 0; i < iterations; i++) {
                measuredMap.put("Key " + i, "Value" + i);
            }
            if (item.operation == R.string.add_to_map) {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredMap.put("Key " + i, "Value " + i);
                }
            } else if (item.operation == R.string.search) {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredMap.get("Key " + i);
                }
            } else {
                startTime = System.nanoTime();
                for (int i = 0; i < iterations; i++) {
                    measuredMap.remove("Key " + i);
                }
            }
        }
        double endTime = (System.nanoTime() - startTime)/1_000_000;
        return endTime;
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
