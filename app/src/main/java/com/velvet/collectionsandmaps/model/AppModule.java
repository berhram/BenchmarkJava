package com.velvet.collectionsandmaps.model;

import androidx.annotation.NonNull;

import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;
import com.velvet.collectionsandmaps.model.benchmark.ListBenchmark;
import com.velvet.collectionsandmaps.model.benchmark.MapBenchmark;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

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
