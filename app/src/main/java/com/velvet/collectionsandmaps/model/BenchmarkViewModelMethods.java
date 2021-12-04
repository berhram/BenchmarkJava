package com.velvet.collectionsandmaps.model;

import java.util.List;

public interface BenchmarkViewModelMethods {

    double measureTime(BenchmarkData item, int iterations);

    int getNumberOfColumn();

    List<BenchmarkData> createList(boolean isProgress);

}
