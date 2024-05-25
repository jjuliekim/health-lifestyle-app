package com.example.kim_j_project3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {
    private final List<Summary> summaryList;

    public SummaryAdapter(List<Summary> summaryList) {
        this.summaryList = summaryList;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_item, parent, false);
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        Summary summary = summaryList.get(position);
        holder.dateTextView.setText(summary.getDate());
        holder.caloriesTextView.setText(String.format("%d cal", summary.getTotalCal()));
        holder.mlTextView.setText(String.format("%d mL", summary.getTotalML()));
    }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }

    public static class SummaryViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView caloriesTextView;
        TextView mlTextView;

        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.summary_date);
            caloriesTextView = itemView.findViewById(R.id.summary_cal);
            mlTextView = itemView.findViewById(R.id.summary_ml);
        }
    }
}
