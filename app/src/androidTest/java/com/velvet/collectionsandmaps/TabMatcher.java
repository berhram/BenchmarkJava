package com.velvet.collectionsandmaps;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.espresso.PerformException;

import com.google.android.material.tabs.TabLayout;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class TabMatcher extends BaseMatcher {

    private String title;

    public TabMatcher(String title) {
        this.title = title;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("Tab with title %s",title));
    }

    @Override
    public boolean matches(Object view) {
        TabLayout tabLayout = (TabLayout) view;
        TabLayout.Tab tabAtIndex = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
        if (tabAtIndex == null) {
            throw new PerformException.Builder().withCause(new Throwable(String.format("No tab with title %s",title))).build();
        } else {
            return tabAtIndex.getText().toString().contentEquals(title);
        }
    }
}
