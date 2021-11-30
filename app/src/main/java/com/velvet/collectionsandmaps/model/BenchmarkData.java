package com.velvet.collectionsandmaps.model;

import androidx.annotation.Nullable;

public class BenchmarkData {

    public final int collectionName;
    public final int operation;
    public final int defaultValue;
    public final int measureUnits;
    private boolean progressState = false;
    private double time = 0;

    public BenchmarkData(int collectionName, int operation, int defaultValue, int measureUnits) {
        this.collectionName = collectionName;
        this.operation = operation;
        this.defaultValue = defaultValue;
        this.measureUnits = measureUnits;
    }

    public boolean isInProgress() {
        return progressState;
    }

    public boolean isMeasured() {
        return time != 0;
    }

    public double getTime() {
        return time;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        BenchmarkData benchmarkData = (BenchmarkData) obj;
        return (benchmarkData.operation==this.operation &&
                benchmarkData.getTime()==this.getTime() &&
                benchmarkData.isInProgress() == this.isInProgress() &&
                benchmarkData.collectionName == this.collectionName &&
                benchmarkData.measureUnits == this.measureUnits &&
                benchmarkData.defaultValue == this.defaultValue);
    }
}
