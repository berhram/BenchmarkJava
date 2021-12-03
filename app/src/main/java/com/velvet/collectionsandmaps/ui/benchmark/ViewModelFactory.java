package com.velvet.collectionsandmaps.ui.benchmark;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
