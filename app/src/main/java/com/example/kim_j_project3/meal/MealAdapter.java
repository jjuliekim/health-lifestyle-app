package com.example.kim_j_project3.meal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kim_j_project3.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> mealList;

    public MealAdapter(List<Meal> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.nameTextView.setText(meal.getName());
        holder.caloriesTextView.setText(meal.getCalories() + " cal");
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    // return meal
    public Meal getItem(int position) {
        return mealList.get(position);
    }

    // Method to add a meal and notify the adapter
    public void addMeal(Meal meal) {
        mealList.add(meal);
        notifyItemInserted(mealList.size() - 1);
    }

    // delete meal
    public void deleteItem(int position) {
        mealList.remove(position);
        notifyItemRemoved(position);
    }

    // edit meal
    public void editItem(int position, String newName, int newCalories) {
        Meal meal = mealList.get(position);
        meal.setName(newName);
        meal.setCalories(newCalories);
        notifyItemChanged(position);
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView caloriesTextView;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.meal_name);
            caloriesTextView = itemView.findViewById(R.id.meal_cal);
        }
    }
}
