package com.example.kim_j_project3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SensorFragment extends Fragment implements SensorEventListener {
    private TextView stepText;
    private int stepsCount = 0;

    public SensorFragment() {
    }

    public static SensorFragment newInstance() {
        return new SensorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        stepText = view.findViewById(R.id.stepTextView);
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepsCount = (int) event.values[0];
        stepText.setText(String.format("%d steps", stepsCount));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}