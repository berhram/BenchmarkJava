package com.velvet.collectionsandmaps.model;

public class BenchmarkData {

    public final int collectionName;
    public final int operationName;
    public final int defaultValue;
    public final int measureUnits;
    private boolean progressState;
    private double time = 0;

    public BenchmarkData(int collectionName, int operationName, int defaultValue, int measureUnits, boolean isProgress) {
        this.collectionName = collectionName;
        this.operationName = operationName;
        this.defaultValue = defaultValue;
        this.measureUnits = measureUnits;
        this.progressState = isProgress;
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

    public void setTime(double time) {
        this.time = time;
    }

}
