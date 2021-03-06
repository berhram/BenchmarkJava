package com.velvet.collectionsandmaps;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.widget.ProgressBar;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class MatchersAndActions {
    static public Matcher<View> isSelectedTabTitleCorrect(final String title) {
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

    static public ViewAction selectTabAtIndex(int index) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
            }

            @Override
            public String getDescription() {
                return  String.format("No tab at index %s", index);
            }

            @Override
            public void perform(UiController uiController, View view) {
                TabLayout tabLayout = (TabLayout) view;
                TabLayout.Tab tabAtIndex = tabLayout.getTabAt(index);
                if (tabAtIndex == null) {
                    throw new PerformException.Builder().withCause(new Throwable(String.format("No tab at index %s", index))).build();
                }
                tabAtIndex.select();
            }
        };
    }

    static public ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    static public Matcher<View> isProgressBarVisible() {
        return new TypeSafeMatcher<View>() {
            public void describeTo(Description description) {
                description.appendText("With running progressbar");
            }

            public boolean matchesSafely(View view) {
                ProgressBar progressBar = (ProgressBar) view;
                if (progressBar == null) {
                    throw new PerformException.Builder().withCause(new Throwable("No progressbar")).build();
                } else {
                    return progressBar.getAlpha() != 0F;
                }
            }
        };
    }

    static public Matcher<View> isProgressBarNotVisible() {
        return new TypeSafeMatcher<View>() {
            public void describeTo(Description description) {
                description.appendText("With stopped progressbar");
            }

            public boolean matchesSafely(View view) {
                ProgressBar progressBar = (ProgressBar) view;
                if (progressBar == null) {
                    throw new PerformException.Builder().withCause(new Throwable("No progressbar")).build();
                } else {
                    return progressBar.getAlpha() != 1F;
                }
            }
        };
    }
}
