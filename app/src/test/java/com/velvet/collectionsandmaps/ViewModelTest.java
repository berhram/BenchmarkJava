package com.velvet.collectionsandmaps;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.velvet.collectionsandmaps.model.benchmark.BenchmarkData;
import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;
import com.velvet.collectionsandmaps.ui.benchmark.BenchmarkViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


@RunWith(JUnit4.class)
public class ViewModelTest {
    @Rule
    public InstantTaskExecutorRule iter = new InstantTaskExecutorRule();

    private BenchmarkViewModel viewModel;

    @Mock
    Benchmarks benchmark;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        viewModel = new BenchmarkViewModel(benchmark);
    }

    @After
    public void tearDown() throws Exception {
        viewModel = null;
    }

    @Test(expected =  NullPointerException.class)
    public void setupWhenNullProvided() {
        Mockito.when(benchmark.createList(false)).thenReturn(null);
        viewModel.setup();
    }

    @Test
    public void setupWhenAllIsOk() {
        Mockito.when(benchmark.createList(false)).thenReturn(Mocks.getMeasuredList(false));
        viewModel.setup();
    }

    @Test
    public void tryToMeasureTest() {
        Mockito.when(benchmark.createList(false)).thenReturn(Mocks.getMeasuredList(false));
        viewModel.setup();
        List<BenchmarkData> startData = viewModel.getItemsData().getValue();
        viewModel.tryToMeasure("1000000");
        Assert.assertNotEquals(startData, viewModel.getItemsData().getValue());
    }

    @Test
    public void getNumberOfColumn() {
        Mockito.when(benchmark.getNumberOfColumn()).thenReturn(Mocks.getNumberOfColumn());
        Assert.assertEquals(3, viewModel.getNumberOfColumn());
    }
}