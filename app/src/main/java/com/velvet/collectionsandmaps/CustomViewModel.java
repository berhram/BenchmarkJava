package com.velvet.collectionsandmaps;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomViewModel extends ViewModel {

    private final int index;
    private final ArrayList<InputData> data = new ArrayList<>();

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

    public void setData(String[] items, String[] actions, String notApplicable, String milliseconds) {
        for (int i = 0; i < actions.length; i++) {
            for (int j = 0; j < items.length; j++) {
                data.add(new InputData(false, actions[i] + " " + items[j],notApplicable + " " + milliseconds));
            }
        }
    }

    public ArrayList<InputData> getData() {
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
