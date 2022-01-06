package com.velvet.collectionsandmaps;

import com.velvet.collectionsandmaps.model.benchmark.BenchmarkData;
import com.velvet.collectionsandmaps.model.benchmark.Benchmarks;
import com.velvet.collectionsandmaps.model.benchmark.MapBenchmark;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MapBenchmarkTest {

    private final Benchmarks mapBenchmark = new MapBenchmark();

    @Test
    public void create() {
        List<BenchmarkData> testList = mapBenchmark.createList(true);
        for (int i = 0; i < testList.size(); i++) {
            Assert.assertNotEquals(0, testList.get(i).getTime());
            Assert.assertEquals(true, testList.get(i).isInProgress);
        }
    }

    @Test
    public void numberOfColumnsIsCorrect() {
        Assert.assertEquals(2, mapBenchmark.getNumberOfColumn());
    }

    @Test
    public void listCreatedCorrectly() {
        List<BenchmarkData> expectedList = new ArrayList<>();
        expectedList.add(new BenchmarkData(R.string.hash_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds, true));
        expectedList.add(new BenchmarkData(R.string.tree_map, R.string.add_to_map, R.string.notApplicable, R.string.milliseconds, true));
        expectedList.add(new BenchmarkData(R.string.hash_map, R.string.search, R.string.notApplicable, R.string.milliseconds, true));
        expectedList.add(new BenchmarkData(R.string.tree_map, R.string.search, R.string.notApplicable, R.string.milliseconds, true));
        expectedList.add(new BenchmarkData(R.string.hash_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds, true));
        expectedList.add(new BenchmarkData(R.string.tree_map, R.string.remove_from_map, R.string.notApplicable, R.string.milliseconds, true));

        List<BenchmarkData> testList = mapBenchmark.createList(true);

        Assert.assertEquals(expectedList, testList);
    }

    @Test
    public void measureTimeWorksCorrectly() {
        List<BenchmarkData> testList = mapBenchmark.createList(true);
        for (int i = 0; i < testList.size(); i++) {
            Assert.assertTrue(mapBenchmark.measureTime(testList.get(i), 1000)!=0);
        }
    }
}
