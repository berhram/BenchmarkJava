package com.velvet.collectionsandmaps.model;

import com.velvet.collectionsandmaps.ui.benchmark.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(ViewModelFactory factory);

}
