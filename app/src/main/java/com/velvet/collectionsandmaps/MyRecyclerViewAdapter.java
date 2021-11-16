package com.velvet.collectionsandmaps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private String[] mData;
    private boolean[] mStates;
    private String[] mExecTime;

    MyRecyclerViewAdapter(Context context, String[] data, boolean[] states, String[] execTime) {
        this.mData = data;
        this.mStates = states;
        this.mExecTime = execTime;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_cell, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView operationName;
        ProgressBar progressBar;
        TextView executionTime;

        ViewHolder(View itemView) {
            super(itemView);
            operationName = itemView.findViewById(R.id.operationName);
            progressBar = itemView.findViewById(R.id.progress_circular);
            executionTime = itemView.findViewById(R.id.executionTime);
        }

        void bind(int position) {
            operationName.setText(mData[position]);
            if (mStates[position] == true) {
                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                progressBar.setVisibility(View.GONE);
            }
            executionTime.setText(mExecTime[position]);
        }
    }

    void executionCompleteInCell(int id, String time) {
        mStates[id] = false;
        mExecTime[id] = time;
        notifyDataSetChanged();
    }

    void executionStartInCell(int id) {
        mStates[id] = true;
        notifyDataSetChanged();
    }

 }
