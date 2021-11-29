package com.velvet.collectionsandmaps.ui.benchmark;

import android.os.Process;

import java.util.concurrent.ThreadFactory;

public class PriorityThreadFactory implements ThreadFactory {

    private final int threadPriority;

    public PriorityThreadFactory(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Runnable wrapperRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Process.setThreadPriority(threadPriority);
                }
                catch (Throwable t) {

                }
            }
        };
        return new Thread(wrapperRunnable);
    }
}
