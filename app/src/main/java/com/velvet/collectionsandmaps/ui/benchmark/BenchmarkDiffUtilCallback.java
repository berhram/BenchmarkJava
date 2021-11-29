package com.velvet.collectionsandmaps.ui.benchmark;

import androidx.recyclerview.widget.DiffUtil;

import com.velvet.collectionsandmaps.model.BenchmarkData;

import java.util.List;

public class BenchmarkDiffUtilCallback extends DiffUtil.Callback {
    private final List<BenchmarkData> oldList;
    private final List<BenchmarkData> newList;

    public BenchmarkDiffUtilCallback(List<BenchmarkData> oldList, List<BenchmarkData> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        BenchmarkData oldBenchmark = oldList.get(oldItemPosition);
        BenchmarkData newBenchmark = newList.get(newItemPosition);
        return oldBenchmark.collectionName==newBenchmark.collectionName && oldBenchmark.operation==newBenchmark.operation;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        BenchmarkData oldBenchmark = oldList.get(oldItemPosition);
        BenchmarkData newBenchmark = newList.get(newItemPosition);
        return oldBenchmark.getTime() == newBenchmark.getTime();
    }
}
