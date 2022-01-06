package com.velvet.collectionsandmaps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.velvet.collectionsandmaps.MatchersAndActions.isProgressBarNotVisible;
import static com.velvet.collectionsandmaps.MatchersAndActions.isProgressBarVisible;
import static com.velvet.collectionsandmaps.MatchersAndActions.selectTabAtIndex;
import static com.velvet.collectionsandmaps.MatchersAndActions.waitFor;

import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MapFragmentTests {
    @Test
    static public void measurementsCompleted() {
        onView(withId(R.id.operations_input)).perform(clearText());
        onView(withId(R.id.operations_input)).perform(typeText("100000"));
        onView(withId(R.id.calculate_button)).perform(click());

        for (int i = 0; i < 6; i++) {
            Matcher<View> recyclerViewCellProgressBar = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_progress_bar);
            Matcher<View> recyclerViewCellExecutionTime = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_execution_time);
            onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(i));
            try {
                onView(recyclerViewCellProgressBar).check(matches(isProgressBarVisible()));
            } catch (AssertionError e) {
                onView(recyclerViewCellExecutionTime).check(matches(not(withText("0 ms"))));
            }
        }

        onView(isRoot()).perform(waitFor(3000));

        for (int i = 0; i < 6; i++) {
            Matcher<View> recyclerViewCellExecutionTime = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_execution_time);
            onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(recyclerViewCellExecutionTime).check(matches(not(withText("0 ms"))));
        }
    }

    @Test
    static public void measurementsHasBeenInterrupted() {
        onView(withId(R.id.operations_input)).perform(clearText());
        onView(withId(R.id.operations_input)).perform(typeText("5000000"));
        onView(withId(R.id.calculate_button)).perform(click());
        onView(isRoot()).perform(waitFor(50));
        onView(withId(R.id.calculate_button)).perform(click());
        for (int i = 0; i < 6; i++) {
            Matcher<View> recyclerViewCellTime = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_execution_time);
            Matcher<View> recyclerViewCellProgressBas = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_progress_bar);
            onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(isRoot()).perform(waitFor(500));
            onView(recyclerViewCellTime).check(matches(withText("N/A ms")));
            onView(recyclerViewCellProgressBas).check(matches(isProgressBarNotVisible()));
        }
    }

    @Test
    static public void listIsCorrect() {
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(1));
        onView(isRoot()).perform(waitFor(1000));
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Adding to HashMap");
        expectedNames.add("Adding to TreeMap");
        expectedNames.add("Search in HashMap");
        expectedNames.add("Search in TreeMap");
        expectedNames.add("Removing from HashMap");
        expectedNames.add("Removing from TreeMap");
        for (int i = 0; i < 6; i++) {
            Matcher<View> recyclerViewCellName = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_name);
            onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(recyclerViewCellName).check(matches(withText(expectedNames.get(i))));
        }
    }

    @Test
    static public void invalidInputTest() {
        onView(withId(R.id.operations_input)).perform(typeText("aaa"));
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.operations_input)).check(matches(hasErrorText("Number is invalid!")));
    }
}
