package com.velvet.collectionsandmaps;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Matcher;

public class TabClick implements ViewAction {

    private int index;

    public TabClick(int index) {
        this.index = index;
    }

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
}
