package com.velvet.collectionsandmaps;

import static org.mockito.Mockito.mock;

import androidx.annotation.NonNull;

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
public class TestAppModule {

    private final ViewModelFactory factory = mock(ViewModelFactory.class);

    @Provides
    ViewModelFactory provideViewModelFactory() {
        return factory;
    }
}
