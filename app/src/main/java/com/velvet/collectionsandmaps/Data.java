package com.velvet.collectionsandmaps;

import android.content.Context;

import java.util.ArrayList;

public class Data {

    //first array containing titles (String[]), second - names (String[]), third - execution time blank, fourth - execution time measure.
    ArrayList<Object> arrayList;
    boolean isCollections;

    public Data(Context context, boolean isCollections){
        arrayList = new ArrayList<>();
        this.isCollections = isCollections;
        if (isCollections) {
            arrayList.add(context.getResources().getStringArray(R.array.list_titles));
            arrayList.add(context.getResources().getStringArray(R.array.list_names));
        }
        else {
            arrayList.add(context.getResources().getStringArray(R.array.map_titles));
            arrayList.add(context.getResources().getStringArray(R.array.map_names));
        }
        arrayList.add(context.getResources().getString(R.string.notApplicable));
        arrayList.add(context.getResources().getString(R.string.milliseconds));
    }
}
