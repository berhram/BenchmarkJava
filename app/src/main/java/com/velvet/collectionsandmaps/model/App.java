package com.velvet.collectionsandmaps.model;

import android.app.Application;
import android.content.Context;

import dagger.Component;

public class App extends Application {

    AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public static AppComponent getComponent(Context context) {
        return ((App) context.getApplicationContext()).component;
    }
}
