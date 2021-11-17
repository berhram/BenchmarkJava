package com.velvet.collectionsandmaps;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.text.DecimalFormat;
import java.util.List;

public class AddingToStart extends AsyncTask<Void, Void, Void> {

    private int operations;
    private int id;
    private MyRecyclerViewAdapter adapter;
    private List list;

    @Override
    protected Void doInBackground(Void... context) {
        double startTime = System.nanoTime();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
                adapter.executionStartInCell(id);
            }
        });
        double execTime = (System.nanoTime() - startTime)/1000000;
        DecimalFormat twoDForm = new DecimalFormat("0.000000");
        twoDForm.format(execTime);
        String output = Double.toString(execTime);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
                adapter.executionCompleteInCell(id, output+ " ms");
            }
        });
        return null;
    }
    public AddingToStart(List list , int id, int operations, MyRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        this.id = id;
        this.operations = operations;
        this.list = list;
    }
    }

