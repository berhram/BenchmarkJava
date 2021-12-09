package com.velvet.collectionsandmaps.ui.benchmark;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {

    private final int margin;

    public MarginItemDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(
            Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state
    ) {
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

