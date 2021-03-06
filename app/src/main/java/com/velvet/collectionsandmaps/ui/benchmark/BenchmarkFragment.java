package com.velvet.collectionsandmaps.ui.benchmark;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.velvet.collectionsandmaps.databinding.FragmentBenchmarkBinding;
import com.velvet.collectionsandmaps.ui.benchmark.adapter.BenchmarkAdapter;

public class BenchmarkFragment extends Fragment implements View.OnClickListener {

    private static final String INDEX = "fragment index";

    private FragmentBenchmarkBinding binding;
    private BenchmarkViewModel viewModel;
    private final BenchmarkAdapter adapter = new BenchmarkAdapter();

    public static BenchmarkFragment newInstance(int index) {
        final BenchmarkFragment fragment = new BenchmarkFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(getArguments().getInt(INDEX))).get(BenchmarkViewModel.class);
        viewModel.setup();
        viewModel.getItemsData().observe(this, adapter::setItems);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBenchmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getValidationErrorData().observe(getViewLifecycleOwner(), errorId -> binding.operationsInput.setError(errorId == 0 ? null : getString(errorId)));
        viewModel.getButtonText().observe(getViewLifecycleOwner(), binding.calculateButton::setText);
        binding.recycler.setLayoutManager(new GridLayoutManager(getContext(), viewModel.getNumberOfColumn()));
        binding.recycler.addItemDecoration(new MarginItemDecoration(20));
        binding.recycler.setAdapter(adapter);
        binding.calculateButton.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        viewModel.tryToMeasure(binding.operationsInput.getText().toString());
    }
}
