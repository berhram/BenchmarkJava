package com.velvet.collectionsandmaps.model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

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
    CollectionBenchmark providesListBenchmark() {
        return new ListBenchmark();
    }

    @Provides
    @Singleton
    @NonNull
    @Named("MapBenchmark")
    CollectionBenchmark providesMapBenchmark() {
        return new MapBenchmark();
    }
}
