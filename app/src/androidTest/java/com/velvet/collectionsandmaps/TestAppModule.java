package com.velvet.collectionsandmaps;

import androidx.annotation.NonNull;

import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;
import com.velvet.collectionsandmaps.model.benchmark.ListBenchmark;
import com.velvet.collectionsandmaps.model.benchmark.MapBenchmark;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestAppModule {
    @Provides
    @Singleton
    @NonNull
    @Named("ListBenchmark")
    Benchmarks providesListBenchmark() {
        return new ListBenchmark();
    }

    @Provides
    @Singleton
    @NonNull
    @Named("MapBenchmark")
    Benchmarks providesMapBenchmark() {
        return new MapBenchmark();
    }
}
