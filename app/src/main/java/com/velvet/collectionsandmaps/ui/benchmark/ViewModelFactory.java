package com.velvet.collectionsandmaps.ui.benchmark;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.collectionsandmaps.model.AppComponent;
import com.velvet.collectionsandmaps.model.AppModule;
import com.velvet.collectionsandmaps.model.DaggerAppComponent;
import com.velvet.collectionsandmaps.model.ListBenchmark;
import com.velvet.collectionsandmaps.model.MapBenchmark;

import javax.inject.Inject;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    @Inject
    ListBenchmark listBenchmark;

    @Inject
    MapBenchmark mapBenchmark;



    private final int index;

    public ViewModelFactory(int index) {
        super();
        this.index = index;
        AppComponent component = buildComponent();
        component.inject(this);
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == BenchmarkViewModel.class) {
            if (index == 0) {

                return (T) new BenchmarkViewModel(listBenchmark);
            } else {
                return (T) new BenchmarkViewModel(mapBenchmark);
            }

        } else {
            return super.create(modelClass);
        }
    }
}
