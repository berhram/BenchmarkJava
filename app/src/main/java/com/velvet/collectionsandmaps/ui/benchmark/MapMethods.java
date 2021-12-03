package com.velvet.collectionsandmaps.ui.benchmark;

import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.model.BenchmarkData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapMethods extends ViewModel implements IndexRelatedMethods {

    @Override
    public int getNumberOfColumn() {
        return 2;
    }

    @Override
    public List<BenchmarkData> createList() {
        final List<BenchmarkData> list = new ArrayList<>();
        list.add(new BenchmarkData(R.string.hash_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds));
        list.add(new BenchmarkData(R.string.tree_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds));

        list.add(new BenchmarkData(R.string.hash_map, R.string.search, R.string.notApplicable, R.string.milliseconds));
        list.add(new BenchmarkData(R.string.tree_map, R.string.search, R.string.notApplicable, R.string.milliseconds));

        list.add(new BenchmarkData(R.string.hash_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds));
        list.add(new BenchmarkData(R.string.tree_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds));
        return list;
    }

    @Override
    public double measureTime(BenchmarkData item, int iterations) {
        double startTime;
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
        double endTime = (System.nanoTime() - startTime)/1_000_000;
        return endTime;
    }
}
