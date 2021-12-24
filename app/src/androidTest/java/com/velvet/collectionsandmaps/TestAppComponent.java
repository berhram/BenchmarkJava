package com.velvet.collectionsandmaps;

import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.model.AppComponent;
import com.velvet.collectionsandmaps.ui.benchmark.ViewModelFactory;

import dagger.Component;

@Component(modules = TestAppModule.class)
public interface TestAppComponent extends AppComponent {
    @Override
    void inject(ViewModelFactory factory);

    MockViewModel getViewModel(ListFragmentTests listFragmentTests);

    MockViewModel getViewModel(MapFragmentTests mapFragmentTests);
}
