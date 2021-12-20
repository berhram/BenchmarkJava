package com.velvet.collectionsandmaps;

import com.velvet.collectionsandmaps.model.benchmark.BenchmarkData;
import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;

import java.util.ArrayList;
import java.util.List;

public class MockMapBenchmark implements Benchmarks {
    @Override
    public double measureTime(BenchmarkData item, int iterations) {
        return 1000;
    }

    @Override
    public int getNumberOfColumn() {
        return 2;
    }

    @Override
    public List<BenchmarkData> createList(boolean isProgress) {
        final List<BenchmarkData> list = new ArrayList<>();
        list.add(new BenchmarkData(R.string.hash_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.tree_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds, isProgress));

        list.add(new BenchmarkData(R.string.hash_map, R.string.search, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.tree_map, R.string.search, R.string.notApplicable, R.string.milliseconds, isProgress));

        list.add(new BenchmarkData(R.string.hash_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds, isProgress));
        list.add(new BenchmarkData(R.string.tree_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds, isProgress));
        return list;
    }
}
