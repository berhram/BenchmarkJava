package com.velvet.collectionsandmaps.ui.benchmark;

import com.velvet.collectionsandmaps.model.BenchmarkData;

import java.util.List;

public interface IndexRelatedMethods {

    double measureTime(BenchmarkData item, int iterations);

    int getNumberOfColumn();

    List<BenchmarkData> createList();

}
