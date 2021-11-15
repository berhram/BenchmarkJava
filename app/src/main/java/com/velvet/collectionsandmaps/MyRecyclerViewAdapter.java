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
    private LayoutInflater mInflater;

    MyRecyclerViewAdapter(Context context, String[] data, boolean[] states, String[] execTime) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mStates = states;
        this.mExecTime = execTime;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.operationName.setText(mData[position]);
        if (mStates[position] == true) {
            holder.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            holder.progressBar.setVisibility(View.GONE);
        }
        holder.executionTime.setText(mExecTime[position]);
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
