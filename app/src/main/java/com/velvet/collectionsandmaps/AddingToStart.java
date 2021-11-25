package com.velvet.collectionsandmaps;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.text.DecimalFormat;
import java.util.List;

public class AddingToStart extends AsyncTask<Void, Void, Void> {

    private int operations;
    private int position;
    private CustomRecyclerViewAdapter adapter;
    private List list;

    @Override
    protected Void doInBackground(Void... context) {
        double startTime = System.nanoTime();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
                adapter.executionStart(position);
            }
        });
        for (int i = 0; i < operations; i++) {
            list.add(0, null);
        }
        double execTime = (System.nanoTime() - startTime)/1000000;
        DecimalFormat twoDForm = new DecimalFormat("0.000000");
        twoDForm.format(execTime);
        String output = Double.toString(execTime);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
                adapter.editExecutionTime(position, output);
                adapter.executionEnd(position);
            }
        });
        return null;
    }
    public AddingToStart(List list , int position, int operations, CustomRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        this.position = position;
        this.operations = operations;
        this.list = list;
    }
    }

