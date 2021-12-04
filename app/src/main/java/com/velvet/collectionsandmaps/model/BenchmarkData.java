package com.velvet.collectionsandmaps.model;

import java.util.Objects;

public class BenchmarkData {

    public final int collectionName;
    public final int operation;
    public final int defaultValue;
    public final int measureUnits;
    public final boolean progressState;
    private double time = 0;

    public BenchmarkData(int collectionName, int operation, int defaultValue, int measureUnits, boolean isProgress) {
        this.collectionName = collectionName;
        this.operation = operation;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenchmarkData that = (BenchmarkData) o;
        return collectionName == that.collectionName && operation == that.operation
                && defaultValue == that.defaultValue && measureUnits == that.measureUnits
                && progressState == that.progressState && Double.compare(that.time, time) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionName, operation, defaultValue, measureUnits, progressState, time);
    }
}
