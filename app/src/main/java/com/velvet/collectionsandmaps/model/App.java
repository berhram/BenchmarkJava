package com.velvet.collectionsandmaps.model;

import android.app.Application;
import android.content.Context;

import dagger.Component;

public class App extends Application {

    private AppComponent component;

    private static App instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public AppComponent getComponent() {
        return component;
    }

    public static App getInstance() {
        if (instance == null) {
            return new App();
        } else {
            return instance;
        }
    }

    private App() {

    }
}
