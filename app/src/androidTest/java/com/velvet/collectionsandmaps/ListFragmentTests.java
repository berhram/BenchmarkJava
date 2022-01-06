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
import static com.velvet.collectionsandmaps.MatchersAndActions.waitFor;

import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListFragmentTests {
    @Test
    static public void measurementsCompleted() {
        onView(withId(R.id.operations_input)).perform(clearText());
        onView(withId(R.id.operations_input)).perform(typeText("100000"));
        onView(withId(R.id.calculate_button)).perform(click());

        for (int i = 0; i < 21; i++) {
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

        for (int i = 0; i < 21; i++) {
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
        onView(withId(R.id.calculate_button)).perform(click());
        for (int i = 0; i < 21; i++) {
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
