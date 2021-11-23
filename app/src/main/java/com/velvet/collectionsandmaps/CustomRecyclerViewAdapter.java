package com.velvet.collectionsandmaps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.velvet.collectionsandmaps.databinding.ItemBenchmarksBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ItemViewHolder> {

        private final List<InputData> items = new ArrayList<>();

        public void addInputData(InputData inputData) {
            items.add(inputData);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(ItemBenchmarksBinding.inflate(LayoutInflater.from(parent.getContext())));
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            ItemViewHolder viewHolder = holder;
            viewHolder.bind(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            private ItemBenchmarksBinding binding;

            public ItemViewHolder(ItemBenchmarksBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            void bind(int position) {
                InputData inputData =  items.get(position);
                binding.itemName.setText(inputData.getItemName());
                binding.itemExecutionTime.setText(inputData.getItemExecutionTime());
                if (inputData.getProgressState()) {
                    binding.itemProgressBar.animate()
                            .setDuration(500)
                            .alpha(0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    binding.itemProgressBar.setVisibility(View.VISIBLE);
                                }
                            });
                }
                else {
                    binding.itemProgressBar.animate()
                            .setDuration(500)
                            .alpha(0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    binding.itemProgressBar.setVisibility(View.GONE);
                                }
                            });
                }
            }
        }
        public void editExecutionTime(int position, String time) {
            items.get(position).setItemExecutionTime(time);
        }
 }
