package com.velvet.collectionsandmaps;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private Button button;
    private EditText operations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        String[] names = new String[]{"Adding to start in ArrayList", "Adding to start in LinkedList", "Adding to start in CopyOnWriteArrayList",
                "Adding to middle in ArrayList", "Adding to middle in LinkedList", "Adding to middle in CopyOnWriteArrayList",
                "Adding to end in ArrayList", "Adding to end in LinkedList", "Adding to end in CopyOnWriteArrayList",
                "Search in ArrayList", "Search in LinkedList", "Search to start in CopyOnWriteArrayList",
                "Removing from start in ArrayList", "Removing from in LinkedList", "Removing from in CopyOnWriteArrayList",
                "Removing from middle in ArrayList", "Removing from middle in LinkedList", "Removing from middle in CopyOnWriteArrayList",
                "Removing from end in ArrayList", "Removing from end in LinkedList", "Removing from end in CopyOnWriteArrayList"};
        boolean[] states = new boolean[]{false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false};
        String[] execTime = new String[]{"N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms",
                "N/A ms", "N/A ms", "N/A ms"};
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), numberOfColumns));
        adapter = new MyRecyclerViewAdapter(view.getContext(), names, states, execTime);
        recyclerView.setAdapter(adapter);
        button = view.findViewById(R.id.calculate_button);
        operations = view.findViewById(R.id.operationsInput);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1
                Input arraylistStart = new Input(0,Integer.parseInt(operations.getText().toString()), new ArrayList());
                AddingToStart arrayListAddingToStart = new AddingToStart();
                arrayListAddingToStart.execute(arraylistStart);
                //2
                Input linkedlistStart = new Input(1,Integer.parseInt(operations.getText().toString()), new LinkedList());
                AddingToStart linkedListAddingToStart = new AddingToStart();
                arrayListAddingToStart.execute(linkedlistStart);
                //3
                Input copyOnWriteArrayListStart = new Input(2,Integer.parseInt(operations.getText().toString()), new CopyOnWriteArrayList());
                AddingToStart copyOnWriteArrayListAddingToStart = new AddingToStart();
                arrayListAddingToStart.execute(arraylistStart);
            }
        });
        return view;
    }

    private class Input {
        int id;
        List list;
        int operations;
        Input(int id, int operations, List list) {
            this.id = id;
            this.operations = operations;
            this.list = list;
        }
    }

    protected class AddingToStart extends AsyncTask<Input, Integer, String> {
        @Override
        protected String doInBackground(Input... params) {
            double startTime = System.nanoTime();
            adapter.executionStartInCell(params[0].id);
            for (int i = 0; i < params[0].operations; i++) {
                params[0].list.add(0,null);
            }
            double endTime = System.nanoTime() - startTime;
            String output = Double.toString(endTime);
            adapter.executionCompleteInCell(params[0].id, output);
            return output;
        }
    }
}