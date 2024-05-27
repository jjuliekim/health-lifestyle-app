package com.example.kim_j_project3.hydration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kim_j_project3.R;

import java.util.List;

public class HydrationAdapter extends RecyclerView.Adapter<HydrationAdapter.HydrationViewHolder> {
    private List<Hydration> hydrationList;

    public HydrationAdapter(List<Hydration> hydrationList) {
        this.hydrationList = hydrationList;
    }

    @NonNull
    @Override
    public HydrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liquid_item, parent, false);
        return new HydrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HydrationViewHolder holder, int position) {
        Hydration hydration = hydrationList.get(position);
        holder.timeTextView.setText(hydration.getTime());
        holder.mLTextView.setText(hydration.getMl() + " mL");
    }

    @Override
    public int getItemCount() {
        return hydrationList.size();
    }

    // return hydration entry
    public Hydration getItem(int position) {
        return hydrationList.get(position);
    }

    // Method to add a meal and notify the adapter
    public void addLiquid(Hydration hydration) {
        hydrationList.add(hydration);
        notifyItemInserted(hydrationList.size() - 1);
    }

    // delete item
    public void deleteItem(int position) {
        hydrationList.remove(position);
        notifyItemRemoved(position);
    }

    // edit item
    public void editItem(int position, String newTime, int newML) {
        Hydration hydration = hydrationList.get(position);
        hydration.setTime(newTime);
        hydration.setMl(newML);
        notifyItemChanged(position);
    }

    public static class HydrationViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView;
        TextView mLTextView;

        public HydrationViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.liquid_time);
            mLTextView = itemView.findViewById(R.id.liquid_ml);
        }
    }
}
