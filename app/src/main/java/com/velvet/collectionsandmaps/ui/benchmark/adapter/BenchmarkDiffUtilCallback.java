package com.velvet.collectionsandmaps.ui.benchmark.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.velvet.collectionsandmaps.model.benchmark.BenchmarkData;

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
        final BenchmarkData oldBenchmark = oldList.get(oldItemPosition);
        final BenchmarkData newBenchmark = newList.get(newItemPosition);
        return oldBenchmark.hashCode() == newBenchmark.hashCode();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
