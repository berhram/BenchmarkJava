package com.velvet.collectionsandmaps.ui.benchmark;

import android.os.Looper;

import java.util.concurrent.Executor;
import  android.os.Handler;

public class ThreadsExecutor implements Executor {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        handler.post(command);
    }
}
