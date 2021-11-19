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

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean[] states;
    private String[] executionTime;
    private String ms;
    private static final int TYPE_FULL = 0;
    private static final int TYPE_HALF = 1;
    private final boolean isCollection;
    private final String[] cellsAndTitlesData;
    private final int numberOfColumns;

    MyRecyclerViewAdapter(Context context, Data data, int numberOfColumns) {
        String[] titles = (String[]) data.arrayList.get(0);
        String[] names = (String[]) data.arrayList.get(1);
        String notApplicable = (String) data.arrayList.get(2);
        ms = (String) data.arrayList.get(3);
        isCollection = data.isCollections;
        this.numberOfColumns = numberOfColumns;
        int numberOfElements = (titles.length*names.length)+titles.length;
        cellsAndTitlesData = new String[numberOfElements];
        states = new boolean[numberOfElements];
        for (int i = 0; i < numberOfElements; i++) {
            states[i] = false;
        }
        executionTime = new String[numberOfElements];
        for (int i = 0; i < numberOfElements; i++) {
            executionTime[i] = notApplicable;
        }
        int titlesPassed = 0;
        for (int i = 0; i < cellsAndTitlesData.length; i++) {
            if (i%(numberOfColumns+1)==0) {
                cellsAndTitlesData[i] = titles[titlesPassed];
                titlesPassed++;
            }
            else {
                if (isCollection) {
                    if ((i-1) % 4 == 0) {
                        cellsAndTitlesData[i] = names[0];
                    } else if ((i-2) % 4 == 0) {
                        cellsAndTitlesData[i]= names[1];
                    } else if ((i-3) % 4 == 0) {
                        cellsAndTitlesData[i] = names[2];
                    }
                }
                else {
                    if ((i-1) % 3 == 0) {
                        cellsAndTitlesData[i] = names[0];
                    }
                    else if ((i-2) % 3 == 0) {
                        cellsAndTitlesData[i] = names[1];
                    }
                }
            }
        }
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==TYPE_FULL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_title, parent, false);
            //
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    //final int type = viewType;
                    final ViewGroup.LayoutParams lp = view.getLayoutParams();
                    if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                        StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
                        sglp.setFullSpan(true);
                        view.setLayoutParams(sglp);
                        final StaggeredGridLayoutManager lm =
                                (StaggeredGridLayoutManager) ((RecyclerView) parent).getLayoutManager();
                        lm.invalidateSpanAssignments();
                    }
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    return false;
                }
            });
            return new TitleViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_cell, parent, false);
            return new CellViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == TYPE_FULL) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.bind(position);
        }
        else {
            CellViewHolder cellViewHolder = (CellViewHolder) holder;
            cellViewHolder.bind(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position%(numberOfColumns+1)==0) {
            return TYPE_FULL;
        }
        else {
            return TYPE_HALF;
        }
    }

    @Override
    public int getItemCount() {
        return cellsAndTitlesData.length;
    }

    public class CellViewHolder extends RecyclerView.ViewHolder {
        TextView objectName;
        ProgressBar progressBar;
        TextView executionTimeText;

        CellViewHolder(View itemView) {
            super(itemView);
            objectName = itemView.findViewById(R.id.operationName);
            progressBar = itemView.findViewById(R.id.progress_circular);
            executionTimeText = itemView.findViewById(R.id.executionTimeText);
        }

        void bind(int position) {
            if (states[position]) {
                progressBar.animate()
                            .setDuration(500)
                            .alpha(0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            });
                }
            else {
                progressBar.animate()
                        .setDuration(500)
                        .alpha(0)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
            objectName.setText(cellsAndTitlesData[position]);
            executionTimeText.setText(executionTime[position] + " " + ms);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        TitleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
        void bind (int position) {
            title.setText(cellsAndTitlesData[position]);
        }
    }

    void executionCompleteInCell(int id, String time) {
        states[id] = false;
        executionTime[id] = time;
        notifyItemChanged(id);
    }

    void executionStartInCell(int id) {
        states[id] = true;
        notifyItemChanged(id);
    }

 }
