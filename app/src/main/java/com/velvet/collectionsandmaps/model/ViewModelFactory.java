package com.velvet.collectionsandmaps.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.collectionsandmaps.model.BenchmarkViewModel;
import com.velvet.collectionsandmaps.model.ListBenchmark;
import com.velvet.collectionsandmaps.model.MapBenchmark;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final int index;

    public ViewModelFactory(int index) {
        super();
        this.index = index;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == BenchmarkViewModel.class) {
            if (index == 0) {
                return (T) new BenchmarkViewModel(new ListBenchmark());
            } else {
                return (T) new BenchmarkViewModel(new MapBenchmark());
            }

        } else {
            return super.create(modelClass);
        }
    }
}
