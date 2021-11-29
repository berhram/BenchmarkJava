package com.velvet.collectionsandmaps.ui.benchmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.velvet.collectionsandmaps.R;
import com.velvet.collectionsandmaps.databinding.ItemBenchmarksBinding;
import com.velvet.collectionsandmaps.model.BenchmarkData;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkAdapter extends RecyclerView.Adapter<BenchmarkAdapter.ItemViewHolder> {

    private final List<BenchmarkData> items = new ArrayList<>();

    public void setItems(List<BenchmarkData> inputItems) {
        items.clear();
        items.addAll(inputItems);
        notifyDataSetChanged();

        // TODO: add diffutil or use ListAdapter for recyclerView
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(ItemBenchmarksBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        final private ItemBenchmarksBinding binding;

        public ItemViewHolder(ItemBenchmarksBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(BenchmarkData item) {
            final Context ctx = binding.getRoot().getContext();
            binding.itemName.setText(ctx.getString(R.string.benchmark_title, ctx.getString(item.operation), ctx.getString(item.collectionName)));
            final String time;
            if (item.isMeasured()) {
                time = Double.toString(item.getTime());
            } else {
                time = ctx.getString(item.defaultValue);
            }
            binding.itemExecutionTime.setText(ctx.getString(R.string.benchmark_time, time));

            final float alpha = item.isInProgress() ? 1F : 0F;
            binding.itemProgressBar.animate().setDuration(500).alpha(alpha);
        }
    }
}
