package com.velvet.collectionsandmaps;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.velvet.collectionsandmaps.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BenchmarkTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void benchmarkApplicationTest() {
        ActivityTests.tabsAreClickable();
        ActivityTests.tabsAreSwipeableAndSwipesChangeTabs();
        waitTestEnd();

        ListFragmentTests.listIsCorrect();
        ListFragmentTests.invalidInputTest();
        ListFragmentTests.measurementsCompleted();
        ListFragmentTests.measurementsHasBeenInterrupted();

        MapFragmentTests.listIsCorrect();
        MapFragmentTests.invalidInputTest();
        MapFragmentTests.measurementsCompleted();
        MapFragmentTests.measurementsHasBeenInterrupted();
    }

    private void waitTestEnd() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
}
