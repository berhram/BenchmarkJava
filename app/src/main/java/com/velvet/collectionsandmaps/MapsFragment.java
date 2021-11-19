package com.velvet.collectionsandmaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class MapsFragment extends Fragment {
        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_main, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomViewModel viewModel = new ViewModelProvider(this).get(CustomViewModel.class);
        final Button calculateButton = view.findViewById(R.id.calculate_button);
        final EditText operationsInput = view.findViewById(R.id.operationsInput);
        final TextView operationsLabel = view.findViewById(R.id.operationsLabel);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler);
        int numberOfColumns = 2;
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(numberOfColumns, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        Data mapsData = new Data(getContext(), false);
        final MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(view.getContext(), mapsData, numberOfColumns);
        recyclerView.setAdapter(adapter);
        calculateButton.setOnClickListener(v -> {
                    //under construction
                }
        );
    }
}
