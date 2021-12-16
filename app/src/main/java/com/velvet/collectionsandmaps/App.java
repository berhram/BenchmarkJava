package com.velvet.collectionsandmaps;

import android.app.Application;

import com.velvet.collectionsandmaps.model.AppComponent;
import com.velvet.collectionsandmaps.model.AppModule;
import com.velvet.collectionsandmaps.model.DaggerAppComponent;

public class App extends Application {

    private static App instance;

    private AppComponent component;

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
