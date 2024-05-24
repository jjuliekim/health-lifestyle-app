package com.example.kim_j_project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MealAdapter extends ArrayAdapter<Meal> {
    public MealAdapter(Context context, List<Meal> mealList) {
        super(context, 0, mealList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Meal meal = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.meal_item, parent, false);

        TextView nameText = convertView.findViewById(R.id.meal_name);
        TextView amtText = convertView.findViewById(R.id.meal_cal);
        nameText.setText(meal.getName());
        amtText.setText(meal.getCalories());

        return convertView;
    }
}
