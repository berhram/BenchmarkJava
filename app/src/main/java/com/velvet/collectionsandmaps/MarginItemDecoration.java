package com.velvet.collectionsandmaps;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {

    private int margin;

    public MarginItemDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = margin;
        outRect.right = margin;
        outRect.bottom = margin;
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = margin;
        } else {
            outRect.top = 0;
        }
    }

}
