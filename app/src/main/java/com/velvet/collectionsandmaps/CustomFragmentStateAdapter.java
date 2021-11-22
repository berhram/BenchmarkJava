package com.velvet.collectionsandmaps;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomFragmentStateAdapter extends FragmentStateAdapter {

    private List<Fragment> listFragment = new ArrayList<>();

    public CustomFragmentStateAdapter(FragmentActivity fa, List<Fragment> list) {
        super(fa);
        listFragment = list;
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}