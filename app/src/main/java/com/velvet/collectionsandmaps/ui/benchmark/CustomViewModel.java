package com.velvet.collectionsandmaps.ui.benchmark;

import android.os.Process;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.BenchmarkData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
            new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND));

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
                List<Future<Integer>> resultList = new ArrayList<>();
                List<List> lists = new ArrayList<>();
                lists.add(new ArrayList<>(items));
                lists.add(new LinkedList<>());
                lists.add(new CopyOnWriteArrayList<>());
                for (int i = 0; i < lists.size(); i++) {
                    final int finalI = i;
                    //adding to beginning
                    Future<Integer> result = executor.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            List temporaryList = ListPopulator.populate(lists.get(finalI), items);
                            double startTime = System.nanoTime();
                            for (int j = 0; j < items; j++) {
                                temporaryList.add(0,null);
                            }
                            double endTime = (System.nanoTime() - startTime) / 1_000_000;
                            DecimalFormat doubleFormatter  = new DecimalFormat("0.000000");
                            return Integer.parseInt(Double.toString(endTime));
                        }
                    });
                    resultList.add(result);
                    //Adding to middle
                    result = executor.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            List temporaryList = ListPopulator.populate(lists.get(finalI), items);
                            double startTime = System.nanoTime();
                            for (int j = 0; j < items; j++) {
                                temporaryList.add(temporaryList.size()/2,null);
                            }
                            double endTime = (System.nanoTime() - startTime) / 1_000_000;
                            DecimalFormat doubleFormatter  = new DecimalFormat("0.000000");
                            return Integer.parseInt(Double.toString(endTime));
                        }
                    });
                    resultList.add(result);
                    //Adding to end
                    result = executor.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            List temporaryList = ListPopulator.populate(lists.get(finalI), items);
                            double startTime = System.nanoTime();
                            for (int j = 0; j < items; j++) {
                                temporaryList.add(temporaryList.size(),null);
                            }
                            double endTime = (System.nanoTime() - startTime) / 1_000_000;
                            DecimalFormat doubleFormatter  = new DecimalFormat("0.000000");
                            return Integer.parseInt(Double.toString(endTime));
                        }
                    });
                    resultList.add(result);
                    //Search
                    result = executor.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            List temporaryList = ListPopulator.populate(lists.get(finalI), items);
                            double startTime = System.nanoTime();
                            for (int j = 0; j < items; j++) {
                                temporaryList.indexOf(null);
                            }
                            double endTime = (System.nanoTime() - startTime) / 1_000_000;
                            DecimalFormat doubleFormatter  = new DecimalFormat("0.000000");
                            return Integer.parseInt(Double.toString(endTime));
                        }
                    });
                    resultList.add(result);
                    //Remove at beginning
                    result = executor.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            List temporaryList = ListPopulator.populate(lists.get(finalI), items);
                            double startTime = System.nanoTime();
                            for (int j = 0; j < items; j++) {
                                temporaryList.remove(0);
                            }
                            double endTime = (System.nanoTime() - startTime) / 1_000_000;
                            DecimalFormat doubleFormatter  = new DecimalFormat("0.000000");
                            return Integer.parseInt(Double.toString(endTime));
                        }
                    });
                    resultList.add(result);
                    //Remove at middle
                    result = executor.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            List temporaryList = ListPopulator.populate(lists.get(finalI), items);
                            double startTime = System.nanoTime();
                            for (int j = 0; j < items; j++) {
                                temporaryList.remove(temporaryList.size()/2);
                            }
                            double endTime = (System.nanoTime() - startTime) / 1_000_000;
                            DecimalFormat doubleFormatter  = new DecimalFormat("0.000000");
                            return Integer.parseInt(Double.toString(endTime));
                        }
                    });
                    resultList.add(result);
                    //Remove at end
                    result = executor.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            List temporaryList = ListPopulator.populate(lists.get(finalI), items);
                            double startTime = System.nanoTime();
                            for (int j = 0; j < items; j++) {
                                temporaryList.remove(temporaryList.size()-1);
                            }
                            double endTime = (System.nanoTime() - startTime) / 1_000_000;
                            DecimalFormat doubleFormatter  = new DecimalFormat("0.000000");
                            return Integer.parseInt(Double.toString(endTime));
                        }
                    });
                    resultList.add(result);
                }
                for(Future<Integer> future : resultList)
                {
                    try
                    {
                        Log.d("Future", future.get() + " status:" + future.isDone());
                    }
                    catch (InterruptedException | ExecutionException e)
                    {
                        e.printStackTrace();
                    }
                }
            } else {

            }
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
