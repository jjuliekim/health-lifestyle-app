package com.example.kim_j_project3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kim_j_project3.hydration.Hydration;
import com.example.kim_j_project3.meal.Meal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SummaryFragment extends Fragment {
    private String username;

    public SummaryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }

        setUserInfo(view);
        ArrayList<Summary> summaryList = setSummaryList();
        RecyclerView recyclerView = view.findViewById(R.id.summary_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SummaryAdapter adapter = new SummaryAdapter(summaryList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    // 1st part: set user register stats
    private void setUserInfo(View view) {
        TextView heightText = view.findViewById(R.id.heightTextView);
        TextView weightText = view.findViewById(R.id.weightTextView);
        TextView bmiText = view.findViewById(R.id.bmiTextView);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String height = sharedPreferences.getString(username + "_height", "");
        String weight = sharedPreferences.getString(username + "_weight", "");
        double bmi = (Double.parseDouble(weight) * 703) / Math.pow(Double.parseDouble(height), 2);

        heightText.setText(String.format("Height: %s", height));
        weightText.setText(String.format("Weight: %s", weight));
        bmiText.setText(String.format("BMI: %.2f", bmi));
    }

    // 2nd part: display summary stats sorted by date
    private ArrayList<Summary> setSummaryList() {
        ArrayList<Meal> mealList = JsonManager.loadMeals(getContext(), username);
        ArrayList<Hydration> hydrationList = JsonManager.loadHydration(getContext(), username);

        // group by date and add total cal and mL
        Map<String, Summary> summaryMap = new HashMap<>(); // date -> Summary (date, cal, ml)
        for (Meal meal : mealList) {
            String date = meal.getDate();
            summaryMap.putIfAbsent(date, new Summary(date, 0, 0));
            Summary summary = summaryMap.get(date);
            summary.totalCal += meal.getCalories();
        }
        for (Hydration hydration : hydrationList) {
            String date = hydration.getDate();
            summaryMap.putIfAbsent(date, new Summary(date, 0, 0));
            Summary summary = summaryMap.get(date);
            summary.totalML += hydration.getMl();
        }
        // sort by date
        ArrayList<Summary> summaryList = new ArrayList<>(summaryMap.values());
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd");
        summaryList.sort((o1, o2) -> {
            try {
                Date date1 = DATE_FORMAT.parse(o1.getDate());
                Date date2 = DATE_FORMAT.parse(o2.getDate());
                Log.i("HERE", "date 1: " + date1 + "date 2: " + date2);
                return date2.compareTo(date1); // Descending order
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        });

        return summaryList;
    }

}