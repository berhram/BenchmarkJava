package com.velvet.collectionsandmaps;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.velvet.collectionsandmaps.model.benchmark.BenchmarkData;
import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;
import com.velvet.collectionsandmaps.ui.benchmark.BenchmarkViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

@RunWith(JUnit4.class)
public class ViewModelTest {
    @Rule
    public InstantTaskExecutorRule iter = new InstantTaskExecutorRule();

    private BenchmarkViewModel viewModel;

    Benchmarks mockBenchmark = new MockBenchmark();

    //TODO implement rule rxandroidplugins

    @Before
    public void setUp() throws Exception{
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        viewModel = new BenchmarkViewModel(mockBenchmark);
    }

    @After
    public void tearDown() throws Exception {

        viewModel = null;
    }

    @Test
    public void setupTest() {
        Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);;
        Observer<Integer> mockErrorObserver = mock(Observer.class);;
        Observer<Integer> mockButtonTextObserver = mock(Observer.class);;
        viewModel.getItemsData().observeForever(mockDataObserver);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);
        viewModel.setup();
        assertEquals(R.string.button_start, (long) viewModel.getButtonText().getValue());
        assertEquals(new MockBenchmark().createList(false), viewModel.getItemsData().getValue());
        verify(mockDataObserver, times(1)).onChanged(isA(List.class));
        verify(mockButtonTextObserver, times(1)).onChanged(isA(Integer.class));
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
    }

    @Test
    public void measurementsTestWhenAllIsOk() {
        Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);;
        Observer<Integer> mockErrorObserver = mock(Observer.class);;
        Observer<Integer> mockButtonTextObserver = mock(Observer.class);;
        viewModel.getItemsData().observeForever(mockDataObserver);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);
        viewModel.tryToMeasure("1000");
        verify(mockDataObserver, times(21)).onChanged(isA(List.class));
        verify(mockButtonTextObserver, times(21)).onChanged(isA(Integer.class));
        verify(mockErrorObserver, never()).onChanged(isA(Integer.class));
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
    }

    @Test
    public void measurementsTestWhenNumberIsInvalid() {
        Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);;
        Observer<Integer> mockErrorObserver = mock(Observer.class);;
        Observer<Integer> mockButtonTextObserver = mock(Observer.class);;
        viewModel.getItemsData().observeForever(mockDataObserver);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);
        viewModel.tryToMeasure("aaa");
        verify(mockDataObserver, never()).onChanged(isA(List.class));
        verify(mockErrorObserver, times(1)).onChanged(isA(Integer.class));
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
    }

    @Test
    public void numberOfColumnsIsCorrect() {
        assertEquals(3, viewModel.getNumberOfColumn());
    }
}
