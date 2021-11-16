package com.velvet.collectionsandmaps;

import android.content.Context;

public class CollectionsData {

    String[] names;
    boolean[] states;
    String[] execTime;

    CollectionsData(Context context) {
        this.names = context.getResources().getStringArray(R.array.list_operations_names);
        this.states = new boolean[]{false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false};
        this.execTime = new String[]{"N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms"};
    }
}
