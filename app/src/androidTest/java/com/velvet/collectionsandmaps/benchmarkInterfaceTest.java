package com.velvet.collectionsandmaps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.velvet.collectionsandmaps.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;

public class benchmarkInterfaceTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testListBenchmark() {
        onView(withId(R.id.operations_input)).perform(typeText("1000000"));
        onView(withId(R.id.calculate_button)).perform(click());
        onView(allOf(withId(R.id.recycler), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testMapBenchmark() {
        onView(allOf(withId(R.id.view_pager), isDisplayed())).perform(swipeLeft());
        onView(allOf(withId(R.id.operations_input), isClickable(),isCompletelyDisplayed())).perform(typeText("1000000"));
        onView(allOf(withId(R.id.calculate_button), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.recycler), isDisplayed()))
                .check(matches(isDisplayed()));
    }
}
