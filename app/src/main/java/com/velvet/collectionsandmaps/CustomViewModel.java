package com.velvet.collectionsandmaps;

import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomViewModel extends ViewModel {

    private final int index;
    private final ArrayList<InputData> data = new ArrayList<>();

    public CustomViewModel(int index) {
        this.index = index;
        if (index == 0) {
            setData(Resources.getSystem().getStringArray(R.array.lists), Resources.getSystem().getStringArray(R.array.list_actions), Resources.getSystem().getString(R.string.notApplicable), Resources.getSystem().getString(R.string.milliseconds));
        }
        else {
            setData(Resources.getSystem().getStringArray(R.array.maps), Resources.getSystem().getStringArray(R.array.map_actions), Resources.getSystem().getString(R.string.notApplicable), Resources.getSystem().getString(R.string.milliseconds));
        }
    }

    public int getNumberOfColumn() {
        if (index==0) {
            return 3;
        }
        else  {
            return 2;
        }
    }

    public void setData(String[] items, String[] actions, String notApplicable, String milliseconds) {
        for (int i = 0; i < actions.length; i++) {
            for (int j = 0; j < items.length; j++) {
                data.add(new InputData(false, actions[i] + " " + items[j],notApplicable + " " + milliseconds));
            }
        }
    }

    public List getData() {
        return data;
    }

    public boolean validateInput(String input) {
        try {
            int inputInteger =  Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
