package com.velvet.collectionsandmaps;

import androidx.lifecycle.MutableLiveData;

import com.velvet.collectionsandmaps.model.benchmark.BenchmarkData;
import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;
import com.velvet.collectionsandmaps.ui.benchmark.BenchmarkViewModel;

import java.util.List;



public class MockViewModel extends BenchmarkViewModel {
    public MockViewModel(Benchmarks benchmark) {
        super(benchmark);
        this.benchmark = benchmark;
    }

    private final MutableLiveData<List<BenchmarkData>> itemsData = new MutableLiveData<>();
    private final MutableLiveData<Integer> buttonText = new MutableLiveData<>();
    private final Benchmarks benchmark;

    @Override
    public void tryToMeasure(String itemsStr) {
        buttonText.setValue(R.string.button_stop);
        itemsData.setValue(benchmark.createList(true));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        for (int i = 0; i < itemsData.getValue().size(); i++) {
            itemsData.getValue().get(i).setTime(5000);
        }
        buttonText.setValue(R.string.button_start);
    }
}
