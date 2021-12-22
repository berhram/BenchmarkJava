package com.velvet.collectionsandmaps;

import androidx.annotation.NonNull;

import com.velvet.collectionsandmaps.model.AppModule;
import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;
import com.velvet.collectionsandmaps.model.benchmark.ListBenchmark;
import com.velvet.collectionsandmaps.model.benchmark.MapBenchmark;
import com.velvet.collectionsandmaps.ui.benchmark.ViewModelFactory;

import org.mockito.Mockito;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestModule extends AppModule {
    @Provides
    @Singleton
    @NonNull
    @Named("ListBenchmark")
    Benchmarks providesTestListBenchmark() {
        return new TestListBenchmark();
    }

    @Provides
    @Singleton
    @NonNull
    @Named("MapBenchmark")
    Benchmarks providesTestMapBenchmark() {
        return new TestMapBenchmark();
    }
}
