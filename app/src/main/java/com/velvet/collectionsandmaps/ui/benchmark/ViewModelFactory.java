package com.velvet.collectionsandmaps.ui.benchmark;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.collectionsandmaps.App;
import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;

import javax.inject.Inject;
import javax.inject.Named;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @Inject
    @Named("ListBenchmark")
    Benchmarks listBenchmark;

    @Inject
    @Named("MapBenchmark")
    Benchmarks mapBenchmark;

    private final int index;

    public ViewModelFactory(int index) {
        super();
        this.index = index;
        App.getInstance().getComponent().inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == BenchmarkViewModel.class) {
            if (index == 0) {
                return (T) new BenchmarkViewModel(listBenchmark);
            } else {
                return (T) new BenchmarkViewModel(mapBenchmark);
            }

        } else {
            return super.create(modelClass);
        }
    }
}
