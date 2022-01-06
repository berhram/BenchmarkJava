package com.velvet.collectionsandmaps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.velvet.collectionsandmaps.MatchersAndActions.isSelectedTabTitleCorrect;
import static com.velvet.collectionsandmaps.MatchersAndActions.selectTabAtIndex;
import static com.velvet.collectionsandmaps.MatchersAndActions.waitFor;

import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Test;

public class ActivityTests {
    @Test
    static public void tabsAreClickable() {
        Matcher<View> firstItem;
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(1));
        firstItem = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(0, R.id.item_name);
        onView(isRoot()).perform(waitFor(500));
        onView(firstItem).check(matches(withText("Adding to HashMap")));
        onView(withId(R.id.tabs)).perform(selectTabAtIndex(0));
        onView(isRoot()).perform(waitFor(500));
        firstItem = new RecyclerViewMatcher(R.id.recycler).atPositionOnView(0, R.id.item_name);
        onView(firstItem).check(matches(withText("Adding to start in ArrayList")));
    }

    @Test
    static public void tabsAreSwipeableAndSwipesChangeTabs() {
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        onView(withId(R.id.tabs)).check(matches(isSelectedTabTitleCorrect("Maps")));
        onView(withId(R.id.view_pager)).perform(swipeRight());
        onView(withId(R.id.tabs)).check(matches(isSelectedTabTitleCorrect("Collections")));
    }
}
