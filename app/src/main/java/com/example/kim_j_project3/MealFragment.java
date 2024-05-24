package com.example.kim_j_project3;

import static com.example.kim_j_project3.JsonManager.loadMeals;
import static com.example.kim_j_project3.JsonManager.saveMeals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MealFragment extends Fragment {
    private String username;
    private ArrayList<Meal> mealList;
    public MealFragment() {}

    public static MealFragment newInstance(String username) {
        MealFragment fragment = new MealFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments().getString("username");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        mealList = loadMeals(getContext(), username);
        RecyclerView recyclerView = view.findViewById(R.id.meal_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MealAdapter(mealList));
        FloatingActionButton button = view.findViewById(R.id.add_meal_button);
        button.setOnClickListener(v -> addMealDialog());
        return view;
    }

    public void addMealDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_meal, null);
        builder.setView(dialogView);

        EditText mealNameText = dialogView.findViewById(R.id.add_meal_name);
        EditText mealCalText = dialogView.findViewById(R.id.add_meal_calories);

        builder.setTitle("Add Meal")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button buttonAdd = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mealName = mealNameText.getText().toString();
                        String mealCaloriesStr = mealCalText.getText().toString();

                        if (mealName.isEmpty() || mealCaloriesStr.isEmpty()) {
                            Toast.makeText(getContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int mealCalories;
                        try {
                            mealCalories = Integer.parseInt(mealCaloriesStr);
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Invalid Numerical Input", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Meal newMeal = new Meal(mealName, mealCalories);
                        mealList.add(newMeal);

                        saveMeals(getContext(), mealList, username);
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }
}