package com.velvet.collectionsandmaps.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.velvet.collectionsandmaps.ui.benchmark.BenchmarkFragment;

import org.jetbrains.annotations.NotNull;

public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return BenchmarkFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
