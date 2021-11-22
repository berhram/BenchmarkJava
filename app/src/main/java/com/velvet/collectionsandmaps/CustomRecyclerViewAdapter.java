package com.velvet.collectionsandmaps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<InputData> arrayList;

        public CustomRecyclerViewAdapter() {
            this.arrayList = new ArrayList<InputData>();
        }

        public void setArrayList(ArrayList<InputData> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
            return new ItemViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder viewHolder= (ItemViewHolder) holder;
            viewHolder.bind(position);
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public void updateList(final ArrayList<InputData> arrayList) {
            this.arrayList.clear();
            this.arrayList = arrayList;
            notifyDataSetChanged();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.item_name)
            TextView itemName;
            @BindView(R.id.item_execution_time)
            TextView itemExecutionTime;
            @BindView(R.id.item_progress_bar)
            ProgressBar itemProgressBar;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bind(int position) {
                InputData inputData =  arrayList.get(position);
                itemName.setText(inputData.getItemName());
                itemExecutionTime.setText(inputData.getItemExecutionTime());
                if (inputData.getProgressState()) {
                    itemProgressBar.animate()
                            .setDuration(500)
                            .alpha(0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    itemProgressBar.setVisibility(View.VISIBLE);
                                }
                            });
                }
                else {
                    itemProgressBar.animate()
                            .setDuration(500)
                            .alpha(0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    itemProgressBar.setVisibility(View.GONE);
                                }
                            });
                }
            }
        }
        public void editExecutionTime(int position, String time) {
            arrayList.get(position).setItemExecutionTime(time);
        }
 }
