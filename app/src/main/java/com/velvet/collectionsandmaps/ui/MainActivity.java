package com.velvet.collectionsandmaps.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayoutMediator;
import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PagerAdapter stateAdapter = new PagerAdapter(this);
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
