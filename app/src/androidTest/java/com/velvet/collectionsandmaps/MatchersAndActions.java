package com.velvet.collectionsandmaps;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

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
}
