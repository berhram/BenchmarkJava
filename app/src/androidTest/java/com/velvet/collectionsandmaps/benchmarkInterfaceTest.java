package com.velvet.collectionsandmaps;

import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.velvet.collectionsandmaps.ui.MainActivity;
import com.velvet.collectionsandmaps.ui.PagerAdapter;

import org.junit.Rule;
import org.junit.Test;

public class benchmarkInterfaceTest {

    MainActivity activity;
    FragmentStateAdapter adapter;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityActivityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testListBenchmark() {

    }

    @Test
    public void testMapBenchmark() {

    }

    private Fragment launchFragment(int page) {
        PagerAdapter pagerAdapter = new PagerAdapter(activity);
        return pagerAdapter.createFragment(page);
    }
}
