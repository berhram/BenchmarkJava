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

import android.view.View;

import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.velvet.collectionsandmaps.ui.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BenchmarkTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void tabsAreClickable() {
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(1));
        onView(new RecyclerViewMatcher(R.id.recycler).atPositionOnView(0, R.id.item_name)).check(matches(withText("Adding to HashMap")));
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(0));
        onView(new RecyclerViewMatcher(R.id.recycler).atPositionOnView(0, R.id.item_name)).check(matches(withText("Adding to start in ArrayList")));
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

    public Matcher<View> isSelectedTabTitleCorrect(final String title) {
        return new TabMatcher(title);
    }


    private ViewAction selectTabAtIndex(int index) {
        return (ViewAction) new TabClick(index);
    }

}
