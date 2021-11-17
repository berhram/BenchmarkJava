package com.velvet.collectionsandmaps;

import android.content.Context;

public class MapsData extends Data {

    MapsData(Context context) {
        this.names = context.getResources().getStringArray(R.array.map_operations_names);
        this.states = new boolean[]{false, false,
                false, false,
                false, false};
        this.execTime = new String[]{"N/A ms", "N/A ms",
                "N/A ms", "N/A ms",
                "N/A ms", "N/A ms"};
    }
}
