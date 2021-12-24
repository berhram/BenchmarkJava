package com.velvet.collectionsandmaps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.velvet.collectionsandmaps.MatchersAndActions.isProgressBarNotVisible;
import static com.velvet.collectionsandmaps.MatchersAndActions.isProgressBarVisible;
import static com.velvet.collectionsandmaps.MatchersAndActions.isSelectedTabTitleCorrect;
import static com.velvet.collectionsandmaps.MatchersAndActions.selectTabAtIndex;
import static com.velvet.collectionsandmaps.MatchersAndActions.waitFor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.velvet.collectionsandmaps.model.AppComponent;
import com.velvet.collectionsandmaps.model.AppModule;
import com.velvet.collectionsandmaps.model.DaggerAppComponent;
import com.velvet.collectionsandmaps.model.benchmark.ListBenchmark;
import com.velvet.collectionsandmaps.ui.MainActivity;
import com.velvet.collectionsandmaps.ui.benchmark.BenchmarkViewModel;
import com.velvet.collectionsandmaps.ui.benchmark.ViewModelFactory;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import dagger.Component;

@RunWith(AndroidJUnit4.class)
public class BenchmarkTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void benchmarkApplicationTest() {
        ActivityTests.tabsAreClickable();
        ActivityTests.tabsAreSwipeableAndSwipesChangeTabs();

        ListFragmentTests.listIsCorrect();
        ListFragmentTests.invalidInputTest();
        ListFragmentTests.measurementsCompleted();
        ListFragmentTests.measurementsHasBeenInterrupted();

        ListFragmentTests.listIsCorrect();
        ListFragmentTests.invalidInputTest();
        ListFragmentTests.measurementsCompleted();
        ListFragmentTests.measurementsHasBeenInterrupted();
    }
}
