package com.velvet.collectionsandmaps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ViewModelTest.class,
        MapBenchmarkTest.class,
        ListBenchmarkTest.class
})
public class TestSuite {
}
