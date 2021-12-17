package com.velvet.collectionsandmaps;

import com.velvet.collectionsandmaps.model.benchmark.BenchmarkData;

import java.util.ArrayList;
import java.util.List;

public class Mocks {
    static List<BenchmarkData> getMeasuredList(boolean isProgress) {
        List<BenchmarkData> mockList = new ArrayList<>();
        mockList.add(new BenchmarkData(R.string.array_list, R.string.add_to_start, R.string.notApplicable, R.string.milliseconds, isProgress));
        mockList.add(new BenchmarkData(R.string.linked_list, R.string.add_to_start, R.string.notApplicable, R.string.milliseconds, isProgress));
        mockList.add(new BenchmarkData(R.string.copy_on_write_list, R.string.add_to_start, R.string.notApplicable, R.string.milliseconds, isProgress));
        return mockList;
    }

    static int getNumberOfColumn() {
        return 3;
    }
}
