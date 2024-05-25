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
    private TextView heartRateText;

    public SensorFragment() {
    }

    public static SensorFragment newInstance() {
        return new SensorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        heartRateText = view.findViewById(R.id.heartRateTextView);
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float heartRate = event.values[0];
        heartRateText.setText(String.format("%.1f bpm", heartRate));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}