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

    @Mock
    BenchmarkViewModel mockModel = mock(BenchmarkViewModel.class);

    @Test
    public void tabsAreClickable() {
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(1));
        Matcher firstItem = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(0, R.id.item_name);
        onView(firstItem).check(matches(withText("Adding to HashMap")));
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(0));
        firstItem = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(0, R.id.item_name);
        onView(firstItem).check(matches(withText("Adding to start in ArrayList")));
    }

    @Test
    public void tabsAreSwipeableAndSwipesChangeTabs() {
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        onView(withId(R.id.tabs)).check(matches(isSelectedTabTitleCorrect("Maps")));
        onView(withId(R.id.view_pager)).perform(swipeRight());
        onView(withId(R.id.tabs)).check(matches(isSelectedTabTitleCorrect("Collections")));
    }

    @Test
    public void invalidInputTest() {
        onView(withId(R.id.operations_input)).perform(typeText("aaa"));
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.operations_input)).check(matches(hasErrorText("Number is invalid!")));
    }

    @Test
    public void firstListIsCorrect() {
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Adding to start in ArrayList");
        expectedNames.add("Adding to start in LinkedList");
        expectedNames.add("Adding to start in CopyOnWriteArrayList");
        expectedNames.add("Adding to middle in ArrayList");
        expectedNames.add("Adding to middle in LinkedList");
        expectedNames.add("Adding to middle in CopyOnWriteArrayList");
        expectedNames.add("Adding to end in ArrayList");
        expectedNames.add("Adding to end in LinkedList");
        expectedNames.add("Adding to end in CopyOnWriteArrayList");
        expectedNames.add("Search in ArrayList");
        expectedNames.add("Search in LinkedList");
        expectedNames.add("Search in CopyOnWriteArrayList");
        expectedNames.add("Removing from start in ArrayList");
        expectedNames.add("Removing from start in LinkedList");
        expectedNames.add("Removing from start in CopyOnWriteArrayList");
        expectedNames.add("Removing from middle in ArrayList");
        expectedNames.add("Removing from middle in LinkedList");
        expectedNames.add("Removing from middle in CopyOnWriteArrayList");
        expectedNames.add("Removing from end in ArrayList");
        expectedNames.add("Removing from end in LinkedList");
        expectedNames.add("Removing from end in CopyOnWriteArrayList");
        for (int i = 0; i < 21; i++) {
            Matcher recyclerViewCellName = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_name);
            onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(recyclerViewCellName).check(matches(withText(expectedNames.get(i))));
        }
    }

    @Test
    public void secondListIsCorrect() {
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(1));
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Adding to HashMap");
        expectedNames.add("Adding to TreeMap");
        expectedNames.add("Search in HashMap");
        expectedNames.add("Search in TreeMap");
        expectedNames.add("Removing from HashMap");
        expectedNames.add("Removing from TreeMap");
        for (int i = 0; i < 6; i++) {
            Matcher recyclerViewCellName = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_name);
            onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(recyclerViewCellName).check(matches(withText(expectedNames.get(i))));
        }
    }

    @Test
    public void measurementsHasBeenInterrupted() {
        onView(withId(R.id.operations_input)).perform(typeText("100000000"));
        onView(withId(R.id.calculate_button)).perform(click());
        onView(isRoot()).perform(waitFor(500));
        onView(withId(R.id.calculate_button)).perform(click());
        onView(isRoot()).perform(waitFor(500));
        for (int i = 0; i < 21; i++) {
            Matcher recyclerViewCellTime = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_execution_time);
            Matcher recyclerViewCellProgressBas = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_progress_bar);
            onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(recyclerViewCellTime).check(matches(withText("N/A ms")));
            onView(isRoot()).perform(waitFor(500));
            onView(recyclerViewCellProgressBas).check(matches(isProgressBarNotVisible()));
        }
    }

    @Test
    public void measurementsCompletedOnFirstTab() {
        onView(withId(R.id.operations_input)).perform(typeText("5000000"));
        onView(withId(R.id.calculate_button)).perform(click());
        for (int i = 0; i < 21; i++) {
            Matcher recyclerViewCellProgressBas = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_progress_bar);
            onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(recyclerViewCellProgressBas).check(matches(isProgressBarVisible()));
            //
        }
    }
}
