package com.velvet.collectionsandmaps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomViewModel extends ViewModel {

    private MutableLiveData<String> mName = new MutableLiveData<>();
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> mNumberOfColumns = new MutableLiveData<>();
    private MutableLiveData<String[]> mArray = new MutableLiveData<>();

    public void setName(String name) {
        mName.setValue(name);
    }

    public String getName() {
        return mName.getValue();
    }

    public void setNumberOfColumns(int numberOfColumns) {
        mNumberOfColumns.setValue(numberOfColumns);
    }

    public Integer getNumberOfColumns() {
        return mNumberOfColumns.getValue();
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public void setArray(String[] array) {
        mArray.setValue(array);
    }

    public String[] getArray() {
        return mArray.getValue();
    }
}
