package com.velvet.collectionsandmaps.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.collectionsandmaps.model.CustomViewModel;
import com.velvet.collectionsandmaps.model.ListMethods;
import com.velvet.collectionsandmaps.model.MapMethods;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final int index;

    public ViewModelFactory(int index) {
        super();
        this.index = index;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == CustomViewModel.class) {
            if (index == 0) {
                return (T) new CustomViewModel(new ListMethods());
            } else {
                return (T) new CustomViewModel(new MapMethods());
            }

        } else {
            return super.create(modelClass);
        }
    }
}
