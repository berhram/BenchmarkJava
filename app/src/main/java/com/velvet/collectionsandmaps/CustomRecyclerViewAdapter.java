package com.velvet.collectionsandmaps;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.velvet.collectionsandmaps.databinding.ItemBenchmarksBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ItemViewHolder> {

        private final List<InputData> items = new ArrayList<>();

        public void setItems(List<InputData> inputItems) {
            items.addAll(inputItems);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(ItemBenchmarksBinding.inflate(LayoutInflater.from(parent.getContext())));
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.bind(holder);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            final private ItemBenchmarksBinding binding;

            public ItemViewHolder(ItemBenchmarksBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            void bind(ItemViewHolder viewHolder) {
                viewHolder.binding.itemName.setText(items.get(getLayoutPosition()).getItemName());
                viewHolder.binding.itemExecutionTime.setText(items.get(getLayoutPosition()).getItemExecutionTime());
                float alpha = items.get(getLayoutPosition()).getProgressState() ? 1f : 0f;
                viewHolder.binding.itemProgressBar.animate().setDuration(500).alpha(alpha);
            }
        }
        public void editExecutionTime(int position, String time) {
            items.get(position).setItemExecutionTime(time);
            notifyItemChanged(position);
        }
        public void executionStart(int position) {
            items.get(position).setProgressState(true);
            notifyItemChanged(position);
        }
        public void executionEnd(int position) {
            items.get(position).setProgressState(false);
            notifyItemChanged(position);
        }
 }
