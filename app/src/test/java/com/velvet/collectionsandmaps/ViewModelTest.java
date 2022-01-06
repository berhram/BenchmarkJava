package com.velvet.collectionsandmaps;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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

    @Mock
    Benchmarks mockBenchmark;
    private BenchmarkViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new BenchmarkViewModel(mockBenchmark);
    }

    private List<BenchmarkData> createMockList(boolean isProgress) {
        final List<BenchmarkData> mockList = new ArrayList<>();
        mockList.add(new BenchmarkData(R.string.hash_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds, isProgress));
        mockList.add(new BenchmarkData(R.string.tree_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds, isProgress));
        mockList.add(new BenchmarkData(R.string.hash_map, R.string.search, R.string.notApplicable, R.string.milliseconds, isProgress));
        mockList.add(new BenchmarkData(R.string.tree_map, R.string.search, R.string.notApplicable, R.string.milliseconds, isProgress));
        mockList.add(new BenchmarkData(R.string.hash_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds, isProgress));
        mockList.add(new BenchmarkData(R.string.tree_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds, isProgress));
        return mockList;
    }

    @Test
    public void setupTest() {
        final Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);
        final Observer<Integer> mockErrorObserver = mock(Observer.class);
        final Observer<Integer> mockButtonTextObserver = mock(Observer.class);

        viewModel.getItemsData().observeForever(mockDataObserver);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);

        final List<BenchmarkData> mockList = createMockList(false);
        when(mockBenchmark.createList(false)).thenReturn(mockList);

        viewModel.setup();

        assertEquals(R.string.button_start, (long) viewModel.getButtonText().getValue());
        assertEquals(createMockList(false), viewModel.getItemsData().getValue());
        verify(mockDataObserver, times(1)).onChanged(isA(List.class));
        verify(mockButtonTextObserver, times(1)).onChanged(isA(Integer.class));
        verify(mockBenchmark, times(1)).createList(false);
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
        verifyNoMoreInteractions(mockBenchmark);
    }

    @Test
    public void measurementsTestWhenAllIsOk() {
        final List<BenchmarkData> mockedTrueList = createMockList(true);
        when(mockBenchmark.createList(true)).thenReturn(mockedTrueList);

        final List<BenchmarkData> mockedFalseList = createMockList(false);
        when(mockBenchmark.createList(false)).thenReturn(mockedFalseList);

        doAnswer(invocation -> 50D).when(mockBenchmark).measureTime(any(BenchmarkData.class), anyInt());

        final Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);
        final Observer<Integer> mockErrorObserver = mock(Observer.class);
        final Observer<Integer> mockButtonTextObserver = mock(Observer.class);

        viewModel.getItemsData().observeForever(mockDataObserver);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);

        viewModel.tryToMeasure("1000");

        verify(mockDataObserver, times(7)).onChanged(isA(List.class));
        verify(mockButtonTextObserver, times(2)).onChanged(buttonTextCaptor.capture());
        verify(mockErrorObserver, never()).onChanged(errorCaptor.capture());
        verify(mockBenchmark, times(1)).createList(false);
        verify(mockBenchmark, times(1)).createList(true);
        verify(mockBenchmark, times(6)).measureTime(any(BenchmarkData.class), anyInt());
        assertEquals(R.string.button_start, (long) buttonTextCaptor.getValue());
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
        verifyNoMoreInteractions(mockBenchmark);
    }

    @Test
    public void measurementsTestWhenInterrupted() {
        final List<BenchmarkData> mockedTrueList = createMockList(true);
        when(mockBenchmark.createList(true)).thenReturn(mockedTrueList);

        final List<BenchmarkData> mockedFalseList = createMockList(false);
        when(mockBenchmark.createList(false)).thenReturn(mockedFalseList);

        doAnswer(invocation -> {Thread.sleep(2000); return 50D;}).when(mockBenchmark).measureTime(any(BenchmarkData.class), anyInt());

        final Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);
        final Observer<Integer> mockErrorObserver = mock(Observer.class);
        final Observer<Integer> mockButtonTextObserver = mock(Observer.class);

        viewModel.getItemsData().observeForever(mockDataObserver);
        viewModel.getValidationErrorData().observeForever(mockErrorObserver);
        viewModel.getButtonText().observeForever(mockButtonTextObserver);

        viewModel.tryToMeasure("1000");
        viewModel.tryToMeasure("1000");

        verify(mockDataObserver, times(8)).onChanged(isA(List.class));
        verify(mockButtonTextObserver, times(3)).onChanged(buttonTextCaptor.capture());
        verify(mockErrorObserver, never()).onChanged(errorCaptor.capture());
        verify(mockBenchmark, times(2)).createList(false);
        verify(mockBenchmark, times(1)).createList(true);
        verify(mockBenchmark, times(6)).measureTime(any(BenchmarkData.class), anyInt());
        assertEquals(R.string.button_start, (long) buttonTextCaptor.getValue());
        verifyNoMoreInteractions(mockDataObserver);
        verifyNoMoreInteractions(mockButtonTextObserver);
        verifyNoMoreInteractions(mockBenchmark);
    }

    @Test
    public void measurementsTestWhenNumberIsInvalid() {
        final Observer<List<BenchmarkData>> mockDataObserver = mock(Observer.class);
        final Observer<Integer> mockErrorObserver = mock(Observer.class);
        final Observer<Integer> mockButtonTextObserver = mock(Observer.class);

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
        when(mockBenchmark.getNumberOfColumn()).thenReturn(3);

        assertEquals(3, viewModel.getNumberOfColumn());
        verify(mockBenchmark, times(1)).getNumberOfColumn();

        verifyNoMoreInteractions(mockBenchmark);
    }
}
