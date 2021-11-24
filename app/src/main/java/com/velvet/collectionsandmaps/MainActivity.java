package com.velvet.collectionsandmaps;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.tabs.TabLayoutMediator;
import com.velvet.collectionsandmaps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TabLayoutMediator tabLayoutMediator;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewPagerFragmentAdapter stateAdapter = new ViewPagerFragmentAdapter(this);
        binding.viewPager.setAdapter(stateAdapter);
        tabLayoutMediator = new TabLayoutMediator(binding.tabs, binding.viewPager,
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