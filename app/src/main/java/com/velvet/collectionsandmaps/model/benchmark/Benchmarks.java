package com.velvet.collectionsandmaps.model.benchmark;

import java.util.List;

public interface Benchmarks {

    double measureTime(BenchmarkData item, int iterations);

    int getNumberOfColumn();

    List<BenchmarkData> createList(boolean isProgress);

}
