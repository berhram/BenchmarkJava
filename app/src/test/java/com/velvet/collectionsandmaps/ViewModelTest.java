package com.velvet.collectionsandmaps;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.class)
public class ViewModelTest {
    @Rule
    public InstantTaskExecutorRule iter = new InstantTaskExecutorRule();

    @Rule
    public RxSchedulerRule schedulerRule = new RxSchedulerRule();

    @Captor
    ArgumentCaptor<Integer> errorCaptor;

    @Captor
    ArgumentCaptor<Integer> buttonTextCaptor;

    public List<BenchmarkData> createMockList(boolean isProgress) {
        List<BenchmarkData> mockList = new ArrayList<>();
        mockList.add(new BenchmarkData(R.string.hash_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds, false));
        mockList.add(new BenchmarkData(R.string.tree_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds, false));
        mockList.add(new BenchmarkData(R.string.hash_map, R.string.search, R.string.notApplicable, R.string.milliseconds, false));
        mockList.add(new BenchmarkData(R.string.tree_map, R.string.search, R.string.notApplicable, R.string.milliseconds, false));
        mockList.add(new BenchmarkData(R.string.hash_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds, false));
        mockList.add(new BenchmarkData(R.string.tree_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds, false));
        return mockList;
    }

    @Mock
    Benchmarks mockBenchmark;

    @Before
    public void setUp() throws Exception{
        BenchmarkViewModel viewModel = new BenchmarkViewModel(mockBenchmark);
    }

    @Test
    public void setupTest() {
        BenchmarkViewModel viewModel = new BenchmarkViewModel(mockBenchmark);

        Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);
        Observer<Integer> mockErrorObserver = mock(Observer.class);
        Observer<Integer> mockButtonTextObserver = mock(Observer.class);

        viewModel.getItemsData().observeForever(mockDataObserver);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);

        List<BenchmarkData> mockList = createMockList(false);
        when(mockBenchmark.createList(false)).thenReturn(mockList);

        viewModel.setup();

        assertEquals(R.string.button_start, (long) viewModel.getButtonText().getValue());
        assertEquals(createMockList(false), viewModel.getItemsData().getValue());
        verify(mockDataObserver, times(1)).onChanged(isA(List.class));
        verify(mockButtonTextObserver, times(1)).onChanged(isA(Integer.class));
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
        verifyNoMoreInteractions(mockBenchmark);
    }

    @Test
    public void measurementsTestWhenAllIsOk() {
        BenchmarkViewModel viewModel = new BenchmarkViewModel(mockBenchmark);

        Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);
        Observer<Integer> mockErrorObserver = mock(Observer.class);
        Observer<Integer> mockButtonTextObserver = mock(Observer.class);

        viewModel.getItemsData().observeForever(mockDataObserver);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);

        List<BenchmarkData> mockedList = createMockList(true);
        when(mockBenchmark.measureTime(any(), anyInt())).thenAnswer();
        when(mockBenchmark.createList(true)).thenReturn(mockedList);

        viewModel.tryToMeasure("1000");

        verify(mockDataObserver, times(21)).onChanged(isA(List.class));
        //here is bug. mockDataObserver number of invocation is only 1, not 21
        verify(mockButtonTextObserver, times(6)).onChanged(buttonTextCaptor.capture());
        verify(mockErrorObserver, never()).onChanged(errorCaptor.capture());
        assertEquals(R.string.button_start, (long) buttonTextCaptor.getValue());
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
        verifyNoMoreInteractions(mockBenchmark);
    }

    @Test
    public void measurementsTestWhenNumberIsInvalid() {
        BenchmarkViewModel viewModel = new BenchmarkViewModel(mockBenchmark);

        Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);
        Observer<Integer> mockErrorObserver = mock(Observer.class);
        Observer<Integer> mockButtonTextObserver = mock(Observer.class);
        
        viewModel.getItemsData().observeForever(mockDataObserver);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);

        viewModel.tryToMeasure("aaa");

        verify(mockDataObserver, never()).onChanged(isA(List.class));
        verify(mockErrorObserver, times(1)).onChanged(errorCaptor.capture());
        assertEquals(R.string.invalid_number, (long) errorCaptor.getValue());
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
        verifyNoMoreInteractions(mockBenchmark);
    }

    @Test
    public void numberOfColumnsIsCorrect() {
        BenchmarkViewModel viewModel = new BenchmarkViewModel(mockBenchmark);

        when(mockBenchmark.getNumberOfColumn()).thenReturn(3);

        assertEquals(3, viewModel.getNumberOfColumn());

        verifyNoMoreInteractions(mockBenchmark);
    }
}
