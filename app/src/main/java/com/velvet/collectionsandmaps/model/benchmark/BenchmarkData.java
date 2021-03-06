package com.velvet.collectionsandmaps.model.benchmark;

import androidx.annotation.Nullable;

import java.util.Objects;

public class BenchmarkData {

    public final int collectionName;
    public final int operationName;
    public final int defaultValue;
    public final int measureUnits;
    public final boolean isInProgress;
    private double time = 0;

    public BenchmarkData(int collectionName, int operationName, int defaultValue, int measureUnits, boolean isProgress) {
        this.collectionName = collectionName;
        this.operationName = operationName;
        this.defaultValue = defaultValue;
        this.measureUnits = measureUnits;
        this.isInProgress = isProgress;
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
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BenchmarkData that = (BenchmarkData) obj;
        return collectionName == that.collectionName && operationName == that.operationName
                && defaultValue == that.defaultValue && measureUnits == that.measureUnits
                && isInProgress == that.isInProgress && time == that.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionName, operationName, defaultValue, measureUnits, time, isInProgress);
    }
}
