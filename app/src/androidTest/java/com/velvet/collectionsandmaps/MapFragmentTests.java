package com.velvet.collectionsandmaps;

import static androidx.test.espresso.Espresso.onView;
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

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

public class MapFragmentTests {
    @Test
    static public void measurementsCompleted() {
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(1));
        onView(withId(R.id.operations_input)).perform(typeText("5000000"));
        onView(withId(R.id.calculate_button)).perform(click());
        for (int i = 0; i < 21; i++) {
            Matcher recyclerViewCellProgressBas = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(i, R.id.item_progress_bar);
            onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(recyclerViewCellProgressBas).check(matches(isProgressBarVisible()));
            //
        }
    }
    @Test
    static public void measurementsHasBeenInterrupted() {
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(1));
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
            onView(recyclerViewCellProgressBas).check(matches(isProgressBarNotVisible()));
        }
    }

    @Test
    static public void listIsCorrect() {
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
    static public void invalidInputTest() {
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(1));
        onView(withId(R.id.operations_input)).perform(typeText("aaa"));
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.operations_input)).check(matches(hasErrorText("Number is invalid!")));
    }
}