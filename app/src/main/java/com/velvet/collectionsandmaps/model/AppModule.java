package com.velvet.collectionsandmaps.model;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    @NonNull
    ListBenchmark providesListBenchmark() {
        return new ListBenchmark();
    }

    @Provides
    @Singleton
    @NonNull
    MapBenchmark providesMapBenchmark() {
        return new MapBenchmark();
    }
}
