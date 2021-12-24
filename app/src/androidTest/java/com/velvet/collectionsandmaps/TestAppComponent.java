package com.velvet.collectionsandmaps;

import androidx.lifecycle.ViewModel;

import com.velvet.collectionsandmaps.model.AppComponent;
import com.velvet.collectionsandmaps.model.AppModule;
import com.velvet.collectionsandmaps.ui.benchmark.ViewModelFactory;

import dagger.Component;

@Component(modules = AppModule.class)
public interface TestAppComponent extends AppComponent {

}
