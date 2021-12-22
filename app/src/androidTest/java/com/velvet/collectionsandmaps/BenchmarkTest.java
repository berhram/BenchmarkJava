package com.velvet.collectionsandmaps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.velvet.collectionsandmaps.MatchersAndActions.isSelectedTabTitleCorrect;
import static com.velvet.collectionsandmaps.MatchersAndActions.selectTabAtIndex;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.action.ViewActions;
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

    private final AppComponent testAppComponent = DaggerAppComponent.builder().appModule(new TestModule()).build();

    @Mock
    BenchmarkViewModel mockModel = mock(BenchmarkViewModel.class);

    @Test
    public void tabsAreClickable() {
        Matcher firstItem = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(0, R.id.item_name);
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(1));
        onView(firstItem).check(matches(withText("Adding to HashMap")));
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(0));
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
    public void measurementsCompletedOnFirstTab() {


        List<Matcher> recyclerViewCellsTime = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            recyclerViewCellsTime.add(new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_execution_time));
        }
        onView(withId(R.id.operations_input)).perform(typeText("1000000"));
        onView(withId(R.id.calculate_button)).perform(click());
    }
}
