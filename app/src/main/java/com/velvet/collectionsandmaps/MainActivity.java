package com.velvet.collectionsandmaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.velvet.collectionsandmaps.databinding.ActivityMainBinding;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragmentArrayList;
    private TabLayoutMediator tabLayoutMediator;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        View view = getLayoutInflater().inflate(R.layout.activity_main, null, false);
        ButterKnife.bind(this, view);
        //
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(PlaceholderFragment.newInstance(0));
        fragmentArrayList.add(PlaceholderFragment.newInstance(1));
        //

        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        //
        CustomFragmentStateAdapter stateAdapter = new CustomFragmentStateAdapter(this, fragmentArrayList);
        viewPager.setAdapter(stateAdapter);
        //
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(getResources().getStringArray(R.array.tabTitles)[position])
        );
        tabLayoutMediator.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tabLayoutMediator.detach();
    }
}