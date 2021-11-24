package com.velvet.collectionsandmaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;


import com.velvet.collectionsandmaps.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class PlaceholderFragment extends Fragment {

    private static final String INDEX = "fragment index";

    private FragmentMainBinding binding;
    private CustomViewModel viewModel;
    private ArrayList<InputData> arrayList;
    private CustomRecyclerViewAdapter adapter;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(getArguments().getInt(INDEX))).get(CustomViewModel.class);
        String[] names;
        arrayList = new ArrayList<>();
        if (getArguments().getInt(INDEX) == 0) {
            names = getResources().getStringArray(R.array.list_items);
        }
        else {
            names = getResources().getStringArray(R.array.map_items);
        }
        for (int i = 0; i < names.length; i++) {
        InputData tempInputData = new InputData(false, names[i], getString(R.string.notApplicable) + " " + getString(R.string.milliseconds));
        arrayList.add(tempInputData);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutManager lm = new GridLayoutManager(getContext(),viewModel.getNumberOfColumn());
        binding.recycler.setLayoutManager(lm);
        adapter = new CustomRecyclerViewAdapter();
        adapter.setItems(arrayList);
        binding.recycler.setAdapter(adapter);
        binding.calculateButton.setOnClickListener(v -> {
                    AddingToStart addingToStartArrayList = new AddingToStart(new ArrayList(), 1, Integer.parseInt(binding.operationsInput.getText().toString()), adapter);
                    addingToStartArrayList.execute();
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
