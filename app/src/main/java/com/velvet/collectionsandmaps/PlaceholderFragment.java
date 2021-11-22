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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.velvet.collectionsandmaps.databinding.FragmentMainBinding;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlaceholderFragment extends Fragment {

    private static final String TAB_NUMBER = "section_number";

    private Unbinder unbinder;

    @BindView(R.id.calculate_button)
    Button calculateButton;
    @BindView(R.id.operations_input)
    EditText operationsInput;
    @BindView(R.id.operations_label)
    TextView operationsLabel;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private CustomViewModel viewModel;
    private ArrayList<InputData> arrayList;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CustomViewModel.class);
        int index;
        if (getArguments() != null) {
            index = getArguments().getInt(TAB_NUMBER);
        }
        else {
            index = 1;
        }
        if (index == 1) {
            viewModel.setNumberOfColumns(3);
            String [] names = getResources().getStringArray(R.array.list_items);
            for (int i = 0; i < names.length; i++) {
                InputData tempInputData = new InputData(false, names[i], getString(R.string.notApplicable )+ " " + getString(R.string.milliseconds));
                arrayList.add(tempInputData);
            }
        }
        else {
            viewModel.setNumberOfColumns(2);
            String [] names = getResources().getStringArray(R.array.map_items);
            for (int i = 0; i < names.length; i++) {
                InputData tempInputData = new InputData(false, names[i], getString(R.string.notApplicable )+ " " + getString(R.string.milliseconds));
                arrayList.add(tempInputData);
            }
        }
        viewModel.setIndex(index);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomViewModel viewModel = new ViewModelProvider(this).get(CustomViewModel.class);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(viewModel.getNumberOfColumns(), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        final CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter();
        adapter.setArrayList(arrayList);
        recyclerView.setAdapter(adapter);
        calculateButton.setOnClickListener(v -> {
                    AddingToStart addingToStartArrayList = new AddingToStart(new ArrayList(), 1, Integer.parseInt(operationsInput.getText().toString()), adapter);
                    addingToStartArrayList.execute();
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
