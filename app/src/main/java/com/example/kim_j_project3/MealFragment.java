package com.example.kim_j_project3;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MealFragment extends Fragment {
    private ArrayList<Meal> mealList;
    private MealAdapter mealAdapter;
    private String username;

    public MealFragment() {
    }

    public static MealFragment newInstance() {
        return new MealFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        if (getArguments() != null) {
            username = getArguments().getString("username");
            Log.i("HERE", "username: " + username);
        }
        mealList = JsonManager.loadMeals(getContext(), username);
        mealAdapter = new MealAdapter(mealList);
        // set recycler view
        RecyclerView recyclerView = view.findViewById(R.id.meal_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mealAdapter);
        // set button on click listener
        FloatingActionButton button = view.findViewById(R.id.add_meal_button);
        button.setOnClickListener(v -> addMealDialog());

        return view;
    }

    // set up and display dialog
    public void addMealDialog() {
        // inflate dialog
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_meal, null);
        EditText mealNameText = dialogView.findViewById(R.id.add_meal_name);
        EditText mealCalText = dialogView.findViewById(R.id.add_meal_calories);
        Button buttonAdd = dialogView.findViewById(R.id.button_add);
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);

        // make dialog
        AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(dialogView).create();

        // add button action
        buttonAdd.setOnClickListener(view -> {
            String mealName = mealNameText.getText().toString();
            String mealCaloriesStr = mealCalText.getText().toString();
            if (mealName.isEmpty() || mealCaloriesStr.isEmpty()) {
                Toast.makeText(getContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                return;
            }
            int mealCalories = Integer.parseInt(mealCaloriesStr);
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String date = month + "/" + day;
            Meal newMeal = new Meal(mealName, mealCalories, date);
            mealAdapter.addMeal(newMeal);
            JsonManager.saveMeals(getContext(), mealList, username);
            dialog.dismiss();
        });

        // cancel button action
        buttonCancel.setOnClickListener(view -> dialog.cancel());

        dialog.show();
    }
}