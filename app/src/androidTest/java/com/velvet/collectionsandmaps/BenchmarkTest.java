package com.velvet.collectionsandmaps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.tabs.TabLayout;
import com.velvet.collectionsandmaps.model.benchmark.ListBenchmark;
import com.velvet.collectionsandmaps.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
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
        return new TypeSafeMatcher<View>() {

            public void describeTo(Description description) {
                description.appendText(String.format("Tab with title %s",title));
            }

            public boolean matchesSafely(View view) {
                TabLayout tabLayout = (TabLayout) view;
                TabLayout.Tab tabAtIndex = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
                if (tabAtIndex == null) {
                    throw new PerformException.Builder().withCause(new Throwable(String.format("No tab with title %s",title))).build();
                } else {
                    return tabAtIndex.getText().toString().contentEquals(title);
                }
            }
        };
    }

    private ViewAction selectTabAtIndex(int index) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
            }

            @Override
            public String getDescription() {
                return  String.format("No tab at index %s",Integer.toString(index));
            }

            @Override
            public void perform(UiController uiController, View view) {
                TabLayout tabLayout = (TabLayout) view;
                TabLayout.Tab tabAtIndex = tabLayout.getTabAt(index);
                if (tabAtIndex == null) {
                    throw new PerformException.Builder().withCause(new Throwable(String.format("No tab at index %s",Integer.toString(index)))).build();
                }
                tabAtIndex.select();
            }
        };
    }

    public class RecyclerViewMatcher {

        private final int recyclerViewId;

        public RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPositionOnView(final int position, final int targetViewId) {
            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;
                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if(this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)",
                                    new Object[] {Integer.valueOf(recyclerViewId) });
                        }
                    }
                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {
                    this.resources = view.getResources();
                    if (childView == null) {
                        RecyclerView recyclerView = view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        } else {
                            return false;
                        }
                    }
                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }
                }
            };
        }
    }
}
