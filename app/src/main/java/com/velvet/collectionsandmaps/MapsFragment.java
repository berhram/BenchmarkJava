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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MapsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final Button button = view.findViewById(R.id.calculate_button);
        final EditText operations = view.findViewById(R.id.operationsInput);
        final TextView textView = view.findViewById(R.id.operationsLabel);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        MapsData mapsData = new MapsData(getContext());
        final MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(view.getContext(), mapsData.names, mapsData.states, mapsData.execTime);
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(v -> {
                    //under construction
                }
        );
        return view;
    }

}
