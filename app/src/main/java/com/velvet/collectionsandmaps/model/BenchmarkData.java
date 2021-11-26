package com.velvet.collectionsandmaps.model;

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
}
