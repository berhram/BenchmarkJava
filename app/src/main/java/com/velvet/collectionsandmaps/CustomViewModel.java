package com.velvet.collectionsandmaps;

import androidx.lifecycle.ViewModel;

public class CustomViewModel extends ViewModel {

    private final int index;

    public CustomViewModel(int index) {
        this.index = index;
    }

    public int getNumberOfColumn() {
        if (index==0) {
            return 3;
        }
        else  {
            return 2;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
