package com.velvet.collectionsandmaps;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final Button button = view.findViewById(R.id.calculate_button);
        final EditText operations = view.findViewById(R.id.operationsInput);
        final TextView textView = view.findViewById(R.id.operationsLabel);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        CollectionsData collectionsData = new CollectionsData(getContext());
        final MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(view.getContext(), getResources().getStringArray(R.array.list_operations_names), collectionsData.states, collectionsData.execTime);
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(v -> {
            //under construction
            }
        );
        return view;
    }


}
