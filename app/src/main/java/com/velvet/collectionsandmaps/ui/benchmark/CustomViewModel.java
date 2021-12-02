package com.velvet.collectionsandmaps.ui.benchmark;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;

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
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomViewModel extends ViewModel {

    private final MutableLiveData<Integer> validationErrorData = new MutableLiveData<>();
    private final MutableLiveData<List<BenchmarkData>> itemsData = new MutableLiveData<>();
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
            list.add(new BenchmarkData(R.string.hash_map, R.string.add_to, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.tree_map, R.string.add_to, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.hash_map, R.string.search, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.tree_map, R.string.search, R.string.notApplicable, R.string.milliseconds));

            list.add(new BenchmarkData(R.string.hash_map, R.string.remove_from, R.string.notApplicable, R.string.milliseconds));
            list.add(new BenchmarkData(R.string.tree_map, R.string.remove_from, R.string.notApplicable, R.string.milliseconds));
        }
        return list;
    }

    public void setup() {
        itemsData.setValue(createList());
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
            List<BenchmarkData> itemsList = createList();
            for (int i = 0; i < itemsList.size(); i++) {
                itemsList.get(i).setProgressState(true);
            }
            List<Future<Double>> resultsList = new ArrayList<>();

            if (index==0) {
                List<List<String>> listOfLists = new ArrayList<>();
                listOfLists.add(new ArrayList<>());
                listOfLists.add(new LinkedList<>());
                listOfLists.add(new CopyOnWriteArrayList<>());
                for (int i = 0; i < listOfLists.size(); i++) {
                    final int finalI = i;
                    Callable<Double> addToStart = () -> {
                        double startTime = System.nanoTime();
                        List tempList = listOfLists.get(finalI);
                        addToStartInList(tempList,items);
                        double endTime = (System.nanoTime()-startTime)/1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(addToStart));

                    Callable<Double> addToMiddle = () -> {
                        double startTime = System.nanoTime();
                        List tempList = listOfLists.get(finalI);
                        addToMiddleInList(tempList,items);
                        double endTime = (System.nanoTime()-startTime)/1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(addToMiddle));

                    Callable<Double> addToEnd = () -> {
                        double startTime = System.nanoTime();
                        List tempList = listOfLists.get(finalI);
                        addToEndInList(tempList,items);
                        double endTime = (System.nanoTime()-startTime)/1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(addToEnd));

                    Callable<Double> searchIn = () -> {
                        double startTime = System.nanoTime();
                        List tempList = populateList(listOfLists.get(finalI), items);
                        searchInList(tempList,items);
                        double endTime = (System.nanoTime()-startTime)/1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(searchIn));

                    Callable<Double> removeFromStart = () -> {
                        double startTime = System.nanoTime();
                        List tempList = populateList(listOfLists.get(finalI), items);
                        removeFromStartInList(tempList,items);
                        double endTime = (System.nanoTime()-startTime)/1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(removeFromStart));

                    Callable<Double> removeFromMiddle = () -> {
                        double startTime = System.nanoTime();
                        List tempList = populateList(listOfLists.get(finalI), items);
                        removeFromMiddleInList(tempList,items);
                        double endTime = (System.nanoTime()-startTime)/1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(removeFromMiddle));

                    Callable<Double> removeFromEnd = () -> {
                        double startTime = System.nanoTime();
                        List tempList = populateList(listOfLists.get(finalI), items);
                        removeFromEndInList(tempList,items);
                        double endTime = (System.nanoTime()-startTime)/1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(removeFromEnd));
                }
            }
            else {
                ArrayList<Map<String, String>> listOfMaps = new ArrayList<>();
                listOfMaps.add(new HashMap<>());
                listOfMaps.add(new TreeMap<>());
                for (int i = 0; i < listOfMaps.size(); i++) {
                    final int finalI = i;
                    Callable<Double> addTo = () -> {
                        double startTime = System.nanoTime();
                        Map tempMap = listOfMaps.get(finalI);
                        addToMap(tempMap, items);
                        double endTime = (System.nanoTime() - startTime) / 1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(addTo));

                    Callable<Double> searchIn = () -> {
                        Map tempMap = populateMap(listOfMaps.get(finalI), items);
                        double startTime = System.nanoTime();
                        searchInMap(tempMap, items);
                        double endTime = (System.nanoTime() - startTime) / 1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(searchIn));

                    Callable<Double> removeFrom  = () -> {
                        Map tempMap = populateMap(listOfMaps.get(finalI), items);
                        double startTime = System.nanoTime();
                        removeFromMap(tempMap, items);
                        double endTime = (System.nanoTime() - startTime) / 1_000_000;
                        return endTime;
                    };
                    resultsList.add(executor.submit(removeFrom));
                }
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (!allItemsIsMeasured(itemsList)) {
                            for (Future<Double> f : resultsList) {
                                if ((f.get() != null) && (!itemsList.get(resultsList.indexOf(f)).isMeasured())) {
                                    itemsList.get(resultsList.indexOf(f)).setTime(f.get());
                                    itemsList.get(resultsList.indexOf(f)).setProgressState(false);
                                    itemsData.setValue(itemsList);
                                }
                            }
                        }
                    }
                    catch(ExecutionException | InterruptedException e){
                        Log.e("Error", "Exception", e);
                    }
                }
            }).run();
        }
    }

    private boolean allItemsIsMeasured(List<BenchmarkData> list) {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isMeasured()) {
                return false;
            }
        }
        return true;
    }

    private void removeFromMap(Map map, int iterations) {
        for (int i = 0; i < iterations; i++) {
            map.remove("Key " + i);
        }
    }

    private void searchInMap(Map map, int iterations) {
        for (int i = 0; i < iterations; i++) {
            map.get("Key " + i);
        }
    }

    private Map populateMap(Map<String, String> map, int size) {
        for (int i = 0; i < size; i++) {
            map.put("Key " + i, "Value" + i);
        }
        return map;
    }

    private void addToMap(Map<String, String> map, int size) {
        for (int i = 0; i < size; i++) {
            map.put("Key " + i, "Value" + i);
        }
    }

    private List populateList(List list, int size) {
        Collections.nCopies(size-1, "Denver");
        Random random = new Random();
        list.add(random.nextInt(size), "Detroit");
        return list;
    }

    private void addToStartInList(List list, int iterations) {
        for (int i = 0; i < iterations; i++) {
            list.add(0, "Denver");
        }
    }

    private void addToMiddleInList(List list, int iterations) {
        for (int i = 0; i < iterations; i++) {
            list.add(list.size()/2, "Denver");
        }
    }

    private void addToEndInList(List list, int iterations) {
        for (int i = 0; i < iterations; i++) {
            list.add(list.size(), "Denver");
        }
    }

    private void searchInList(List list, int iterations) {
        for (int i = 0; i < iterations; i++) {
            list.indexOf("Detroit");
        }
    }

    private void removeFromStartInList(List list, int iterations) {
        for (int i = 0; i < iterations; i++) {
            list.remove(0);
        }
    }

    private void removeFromMiddleInList(List list, int iterations) {
        for (int i = 0; i < iterations; i++) {
            list.remove(list.size()/2);
        }
    }

    private void removeFromEndInList(List list, int iterations) {
        for (int i = 0; i < iterations; i++) {
            list.remove(list.size());
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
