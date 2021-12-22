package com.velvet.collectionsandmaps;

import com.velvet.collectionsandmaps.model.AppComponent;
import com.velvet.collectionsandmaps.model.AppModule;
import com.velvet.collectionsandmaps.ui.benchmark.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = TestModule.class)
@Singleton
public interface TestAppComponent  extends  AppComponent {
    void inject(BenchmarkTest test);
}
