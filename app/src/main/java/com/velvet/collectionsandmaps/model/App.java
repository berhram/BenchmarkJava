package com.velvet.collectionsandmaps.model;

import android.app.Application;

public class App extends Application {

    private AppComponent component;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public AppComponent getComponent() {
        return component;
    }

    public static App getInstance() {
        return instance;
    }

}
