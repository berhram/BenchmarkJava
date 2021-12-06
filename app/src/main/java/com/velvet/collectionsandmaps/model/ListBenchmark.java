package com.velvet.collectionsandmaps.model;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.BenchmarkData;
import com.velvet.collectionsandmaps.model.CollectionBenchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListBenchmark implements CollectionBenchmark {

    private final Random random = new Random();

    @Override
    public int getNumberOfColumn() {
        return 3;
    }

    @Override
    public List<BenchmarkData> createList(boolean isProgress) {
        final List<BenchmarkData> list = new ArrayList<>();
        list.add(new BenchmarkData(R.string.array_list, R.string.add_to_start, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.linked_list, R.string.add_to_start, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.add_to_start, R.string.notApplicable, R.string.milliseconds, isProgress));

        list.add(new BenchmarkData(R.string.array_list, R.string.add_to_middle, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.linked_list, R.string.add_to_middle, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.add_to_middle, R.string.notApplicable, R.string.milliseconds, isProgress));

        list.add(new BenchmarkData(R.string.array_list, R.string.add_to_end, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.linked_list, R.string.add_to_end, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.add_to_end, R.string.notApplicable, R.string.milliseconds, isProgress));

        list.add(new BenchmarkData(R.string.array_list, R.string.search, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.linked_list, R.string.search, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.search, R.string.notApplicable, R.string.milliseconds, isProgress));

        list.add(new BenchmarkData(R.string.array_list, R.string.remove_from_start, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.linked_list, R.string.remove_from_start, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.remove_from_start, R.string.notApplicable, R.string.milliseconds, isProgress));

        list.add(new BenchmarkData(R.string.array_list, R.string.remove_from_middle, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.linked_list, R.string.remove_from_middle, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.remove_from_middle, R.string.notApplicable, R.string.milliseconds, isProgress));

        list.add(new BenchmarkData(R.string.array_list, R.string.remove_from_end, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.linked_list, R.string.remove_from_end, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.copy_on_write_list, R.string.remove_from_end, R.string.notApplicable, R.string.milliseconds, isProgress));
        return list;
    }

    @Override
    public double measureTime(BenchmarkData item, int iterations) {
        double startTime;
        final List<String> measuredList;
        if (item.collectionName == R.string.array_list) {
            measuredList = new ArrayList<>(Collections.nCopies(iterations - 1, "Denver"));
        } else if (item.collectionName == R.string.linked_list) {
            measuredList = new LinkedList<>(Collections.nCopies(iterations - 1, "Denver"));
        } else {
            measuredList = new CopyOnWriteArrayList<>(Collections.nCopies(iterations - 1, "Denver"));
        }
        measuredList.add(random.nextInt(iterations), "Detroit");
        startTime = System.nanoTime();
        if (item.operation == R.string.add_to_start) {
            measuredList.add(0, "Denver");
        } else if (item.operation == R.string.add_to_middle) {
            measuredList.add(measuredList.size() / 2, "Denver");
        } else if (item.operation == R.string.add_to_end) {
            measuredList.add(measuredList.size(), "Denver");
        } else if (item.operation == R.string.search) {
            measuredList.indexOf("Detroit");
        } else if (item.operation == R.string.remove_from_start) {
            measuredList.remove(0);
        } else if (item.operation == R.string.remove_from_middle) {
            measuredList.remove(measuredList.size() / 2);
        } else {
            measuredList.remove(measuredList.size() - 1);
        }
        return (System.nanoTime() - startTime) / 1_000_000;
    }
}
