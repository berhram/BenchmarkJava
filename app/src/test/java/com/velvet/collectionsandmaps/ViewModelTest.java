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
import com.velvet.collectionsandmaps.model.benchmark.ListBenchmark;
import com.velvet.collectionsandmaps.model.benchmark.MapBenchmark;
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

import java.util.List;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.TestScheduler;

@RunWith(JUnit4.class)
public class ViewModelTest {
    @Rule
    public InstantTaskExecutorRule iter = new InstantTaskExecutorRule();

    private BenchmarkViewModel viewModel;

    Benchmarks mockBenchmark = new MockBenchmark();

    Observer<List<BenchmarkData>> mockDataObserver;
    Observer<Integer> mockErrorObserver;
    Observer<Integer> mockButtonTextObserver;

    @Before
    public void setUp() throws Exception{
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        viewModel = new BenchmarkViewModel(mockBenchmark);
        mockDataObserver = mock(Observer.class);
        viewModel.getItemsData().observeForever(mockDataObserver);
        mockErrorObserver = mock(Observer.class);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        mockButtonTextObserver = mock(Observer.class);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);
    }

    @After
    public void tearDown() throws Exception {

        viewModel = null;
    }

    @Test
    public void setupTest() {
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
        viewModel.tryToMeasure("1000");
        verify(mockDataObserver, times(21)).onChanged(isA(List.class));
        verify(mockButtonTextObserver, times(21)).onChanged(isA(Integer.class));
        verify(mockErrorObserver, never()).onChanged(isA(Integer.class));
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
    }

    @Test
    public void measurementsTestWhenNumberIsInvalid() {
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
