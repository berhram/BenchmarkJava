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


import com.google.android.material.snackbar.Snackbar;
import com.velvet.collectionsandmaps.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class PlaceholderFragment extends Fragment {

    private static final String INDEX = "fragment index";

    private FragmentMainBinding binding;
    private CustomViewModel viewModel;
    final private CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter();;

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
        if (getArguments().getInt(INDEX) == 0) {
            viewModel.setData(getResources().getStringArray(R.array.lists), getResources().getStringArray(R.array.list_actions), getString(R.string.notApplicable), getString(R.string.milliseconds));
        }
        else {
            viewModel.setData(getResources().getStringArray(R.array.maps), getResources().getStringArray(R.array.map_actions), getString(R.string.notApplicable), getString(R.string.milliseconds));
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
        binding.recycler.setLayoutManager(new GridLayoutManager(getContext(), viewModel.getNumberOfColumn()));
        binding.recycler.addItemDecoration(new MarginItemDecoration(20));
        adapter.setItems(viewModel.getData());
        binding.recycler.setAdapter(adapter);
        binding.calculateButton.setOnClickListener(v -> {
                    if (viewModel.validateInput(binding.operationsInput.getText().toString())) {
                        AddingToStart addingToStartArrayList = new AddingToStart(new ArrayList(), 0, Integer.parseInt(binding.operationsInput.getText().toString()), adapter);
                        addingToStartArrayList.execute();
                        binding.operationsInputLayout.setError(null);
                    }
                    else {
                        binding.operationsInputLayout.setError("Input number is incorrect!");
                    }
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
