package com.example.kim_j_project3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        setSummaryList(view);

        return view;
    }

    // 1st part: user register stats
    private void setUserInfo(View view) {
        // set user info texts
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

    // 2nd part: summary stats sorted by date
    private void setSummaryList(View view) {

    }

}