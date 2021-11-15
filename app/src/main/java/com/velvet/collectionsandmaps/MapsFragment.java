package com.velvet.collectionsandmaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MapsFragment extends Fragment {
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        String[] names = new String[]{"Adding to TreeMap","Adding to HashMap",
                "Search in TreeMap","Search in HashMap",
                "Removing from TreeMap","Removing from HashMap"};
        boolean[] states = new boolean[]{false, false,
                false, false,
                false, false};
        String[] execTime = new String[]{"N/A ms", "N/A ms",
                "N/A ms", "N/A ms",
                "N/A ms", "N/A ms"};
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), numberOfColumns));
        adapter = new MyRecyclerViewAdapter(view.getContext(), names, states, execTime);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
