package com.velvet.collectionsandmaps.model.benchmark;

import com.velvet.collectionsandmaps.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MapBenchmark implements Benchmarks {

    private final Random random = new Random();

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

    @Override
    public double measureTime(BenchmarkData item, int items) {
        final Map<String, String> measuredMap;
        if (item.collectionName == R.string.hash_map) {
            measuredMap = new HashMap<>();
        } else {
            measuredMap = new TreeMap<>();
        }
        for (int i = 0; i < items; i++) {
            measuredMap.put("Key " + i, "Value" + i);
        }
        final double startTime = System.nanoTime();
        if (item.operationName == R.string.add_to_map) {
            measuredMap.put("Key " + (items + 1), "Denver");
        } else if (item.operationName == R.string.search) {
            measuredMap.get("Key " + random.nextInt(items));
        } else {
            measuredMap.remove("Key " + random.nextInt(items));
        }
        return (System.nanoTime() - startTime) / 1_000_000;
    }
}
