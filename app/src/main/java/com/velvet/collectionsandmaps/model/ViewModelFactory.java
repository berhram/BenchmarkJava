package com.velvet.collectionsandmaps.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.collectionsandmaps.model.FragmentViewModel;
import com.velvet.collectionsandmaps.model.ListCreatorAndMeasurer;
import com.velvet.collectionsandmaps.model.MapViewModelMethods;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final int index;

    public ViewModelFactory(int index) {
        super();
        this.index = index;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FragmentViewModel.class) {
            if (index == 0) {
                return (T) new FragmentViewModel(new ListCreatorAndMeasurer());
            } else {
                return (T) new FragmentViewModel(new MapViewModelMethods());
            }

        } else {
            return super.create(modelClass);
        }
    }
}
