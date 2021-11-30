package com.velvet.collectionsandmaps.ui.benchmark;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.BenchmarkData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(27,
            27,
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
            if (index==0) {
                List<BenchmarkData> list = new ArrayList<>();
                List<Future<Integer>> resultsList = new ArrayList<>();
                //Add to start in ArrayList
                Callable<Integer> addToStartInAL = () -> {
                    double startTime = System.nanoTime();
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < items; i++) {
                        arrayList.add(0, "Denver");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(addToStartInAL));
                //Add to start in LinkedList
                Callable<Integer> addToStartInLL = () -> {
                    double startTime = System.nanoTime();
                    LinkedList<String> linkedList = new LinkedList<>();
                    for (int i = 0; i < items; i++) {
                        linkedList.add(0, "Denver");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(addToStartInLL));
                //Add to start in CopyOnWriteArrayList
                Callable<Integer> addToStartInCOW = () -> {
                    double startTime = System.nanoTime();
                    CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                    for (int i = 0; i < items; i++) {
                        copyOnWriteArrayList.add(0, "Denver");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(addToStartInCOW));

                //Add to middle in ArrayList
                Callable<Integer> addToMiddleInAL = () -> {
                    double startTime = System.nanoTime();
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < items; i++) {
                        arrayList.add(arrayList.size()/2, "Denver");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(addToMiddleInAL));
                //Add to middle in LinkedList
                Callable<Integer> addToMiddleInLL = () -> {
                    double startTime = System.nanoTime();
                    LinkedList<String> linkedList = new LinkedList<>();
                    for (int i = 0; i < items; i++) {
                        linkedList.add(linkedList.size()/2, "Denver");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(addToMiddleInLL));
                //Add to middle in CopyOnWriteArrayList
                Callable<Integer> addToMiddleInCOW = () -> {
                    double startTime = System.nanoTime();
                    CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                    for (int i = 0; i < items; i++) {
                        copyOnWriteArrayList.add(copyOnWriteArrayList.size()/2, "Denver");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(addToMiddleInCOW));

                //Add to end in ArrayList
                Callable<Integer> addToEndInAL = () -> {
                    double startTime = System.nanoTime();
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < items; i++) {
                        arrayList.add(arrayList.size(), "Denver");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(addToEndInAL));
                //Add to end in LinkedList
                Callable<Integer> addToEndInLL = () -> {
                    double startTime = System.nanoTime();
                    LinkedList<String> linkedList = new LinkedList<>();
                    for (int i = 0; i < items; i++) {
                        linkedList.add(linkedList.size(), "Denver");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(addToEndInLL));
                //Add to end in CopyOnWriteArrayList
                Callable<Integer> addToEndInCOW = () -> {
                    double startTime = System.nanoTime();
                    CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                    for (int i = 0; i < items; i++) {
                        copyOnWriteArrayList.add(copyOnWriteArrayList.size(), "Denver");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(addToEndInCOW));

                //Search in ArrayList
                Callable<Integer> searchInAL = () -> {
                    double startTime = System.nanoTime();
                    ArrayList<String> arrayList = (ArrayList<String>) populateList(new ArrayList<>(), items);
                    for (int i = 0; i < items; i++) {
                        arrayList.indexOf("Detroit");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(searchInAL));
                //Search in LinkedList
                Callable<Integer> searchInLL = () -> {
                    double startTime = System.nanoTime();
                    LinkedList<String> linkedList = (LinkedList<String>) populateList(new LinkedList<>(),items);
                    for (int i = 0; i < items; i++) {
                        linkedList.indexOf("Detroit");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(searchInLL));
                //Search in CopyOnWriteArrayList
                Callable<Integer> searchInCOW = () -> {
                    double startTime = System.nanoTime();
                    CopyOnWriteArrayList<String> copyOnWriteArrayList = (CopyOnWriteArrayList<String>) populateList(new CopyOnWriteArrayList<>(), items);
                    for (int i = 0; i < items; i++) {
                        copyOnWriteArrayList.indexOf("Detroit");
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(searchInCOW));

                //Remove from start in ArrayList
                Callable<Integer> removeFromStartInAL = () -> {
                    double startTime = System.nanoTime();
                    ArrayList<String> arrayList = (ArrayList<String>) populateList(new ArrayList<>(), items);
                    for (int i = 0; i < items; i++) {
                        arrayList.remove(0);
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(removeFromStartInAL));
                //Remove from start in LinkedList
                Callable<Integer> removeFromStartInLL = () -> {
                    double startTime = System.nanoTime();
                    LinkedList<String> linkedList = (LinkedList<String>) populateList(new LinkedList<>(),items);
                    for (int i = 0; i < items; i++) {
                        linkedList.remove(0);
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(removeFromStartInLL));
                //Remove from start in CopyOnWriteArrayList
                Callable<Integer> removeFromStartInCOW = () -> {
                    double startTime = System.nanoTime();
                    CopyOnWriteArrayList<String> copyOnWriteArrayList = (CopyOnWriteArrayList<String>) populateList(new CopyOnWriteArrayList<>(), items);
                    for (int i = 0; i < items; i++) {
                        copyOnWriteArrayList.remove(0);
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(removeFromStartInCOW));

                //Remove from middle in ArrayList
                Callable<Integer> removeFromMiddleInAL = () -> {
                    double startTime = System.nanoTime();
                    ArrayList<String> arrayList = (ArrayList<String>) populateList(new ArrayList<>(), items);
                    for (int i = 0; i < items; i++) {
                        arrayList.remove(arrayList.size()/2);
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(removeFromMiddleInAL));
                //Remove from middle in LinkedList
                Callable<Integer> removeFromMiddleInLL = () -> {
                    double startTime = System.nanoTime();
                    LinkedList<String> linkedList = (LinkedList<String>) populateList(new LinkedList<>(),items);
                    for (int i = 0; i < items; i++) {
                        linkedList.remove(linkedList.size()/2);
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(removeFromMiddleInLL));
                //Remove from middle in CopyOnWriteArrayList
                Callable<Integer> removeFromMiddleInCOW = () -> {
                    double startTime = System.nanoTime();
                    CopyOnWriteArrayList<String> copyOnWriteArrayList = (CopyOnWriteArrayList<String>) populateList(new CopyOnWriteArrayList<>(), items);
                    for (int i = 0; i < items; i++) {
                        copyOnWriteArrayList.remove(copyOnWriteArrayList.size()/2);
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(removeFromMiddleInCOW));

                //Remove from end in ArrayList
                Callable<Integer> removeFromEndInAL = () -> {
                    double startTime = System.nanoTime();
                    ArrayList<String> arrayList = (ArrayList<String>) populateList(new ArrayList<>(), items);
                    for (int i = 0; i < items; i++) {
                        arrayList.remove(arrayList.size());
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(removeFromEndInAL));
                //Remove from end in LinkedList
                Callable<Integer> removeFromEndInLL = () -> {
                    double startTime = System.nanoTime();
                    LinkedList<String> linkedList = (LinkedList<String>) populateList(new LinkedList<>(),items);
                    for (int i = 0; i < items; i++) {
                        linkedList.remove(linkedList.size());
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(removeFromEndInLL));
                //Remove from end in CopyOnWriteArrayList
                Callable<Integer> removeFromEndInCOW = () -> {
                    double startTime = System.nanoTime();
                    CopyOnWriteArrayList<String> copyOnWriteArrayList = (CopyOnWriteArrayList<String>) populateList(new CopyOnWriteArrayList<>(), items);
                    for (int i = 0; i < items; i++) {
                        copyOnWriteArrayList.remove(copyOnWriteArrayList.size());
                    }
                    double endTime = (System.nanoTime()-startTime)/1_000_000;
                    return (int) endTime;
                };
                resultsList.add(executor.submit(removeFromEndInCOW));
                try {
                    for (Future<Integer> f : resultsList) {
                        f.get();
                        //under construction
                    }
                } catch (ExecutionException | InterruptedException e) {
                    Log.e("Error","Exception", e);
                }
            } else {

            }
        }
    }

    private List populateList(List list, int size) {
        Collections.nCopies(size-1, "Denver");
        Random random = new Random();
        list.add(random.nextInt(size), "Detroit");
        return list;
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
