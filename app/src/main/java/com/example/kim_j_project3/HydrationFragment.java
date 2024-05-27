package com.example.kim_j_project3;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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

public class HydrationFragment extends Fragment {
    private ArrayList<Hydration> hydrationList;
    private HydrationAdapter hydrationAdapter;
    private String username;

    public HydrationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hydration, container, false);
        if (getArguments() != null) {
            username = getArguments().getString("username");
            Log.i("HERE", "username: " + username);
        }
        hydrationList = JsonManager.loadHydration(getContext(), username);
        hydrationAdapter = new HydrationAdapter(hydrationList);
        // set recycler view
        RecyclerView recyclerView = view.findViewById(R.id.liquid_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(hydrationAdapter);
        // set button on click listener
        FloatingActionButton button = view.findViewById(R.id.add_liquid_button);
        button.setOnClickListener(v -> addLiquidDialog());

        // touch event
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                // if swipe left, delete entry
                if (direction == ItemTouchHelper.LEFT) {
                    hydrationAdapter.deleteItem(position);
                    Toast.makeText(getContext(), "Water Entry Deleted", Toast.LENGTH_SHORT).show();
                    JsonManager.saveHydration(getContext(), hydrationList, username);
                } else if (direction == ItemTouchHelper.RIGHT) { // if swipe right, edit entry
                    showEditDialog(position);
                }
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    // set up and display dialog
    public void addLiquidDialog() {
        // inflate dialog
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_liquid, null);
        EditText liquidTimeText = dialogView.findViewById(R.id.add_liquid_time);
        EditText liquidMLText = dialogView.findViewById(R.id.add_liquid_ml);
        Button buttonAdd = dialogView.findViewById(R.id.liquid_button_add);
        Button buttonCancel = dialogView.findViewById(R.id.liquid_button_cancel);

        // make dialog
        AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(dialogView).create();

        // add button action
        buttonAdd.setOnClickListener(view -> {
            String liquidTime = liquidTimeText.getText().toString();
            String liquidML = liquidMLText.getText().toString();
            if (liquidTime.isEmpty() || liquidML.isEmpty()) {
                Toast.makeText(getContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                return;
            }
            int drinkML = Integer.parseInt(liquidML);
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String date = month + "/" + day;
            Hydration newHydration = new Hydration(liquidTime, drinkML, date);
            hydrationAdapter.addLiquid(newHydration);
            JsonManager.saveHydration(getContext(), hydrationList, username);
            dialog.dismiss();
        });

        // cancel button action
        buttonCancel.setOnClickListener(view -> dialog.cancel());

        dialog.show();
    }

    // set up and display edit meal dialog
    private void showEditDialog(int position) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_liquid, null);
        EditText liquidTimeText = dialogView.findViewById(R.id.edit_liquid_time);
        EditText liquidMLText = dialogView.findViewById(R.id.edit_liquid_ml);
        Button buttonAdd = dialogView.findViewById(R.id.edit_liquid_add);
        Button buttonCancel = dialogView.findViewById(R.id.edit_liquid_cancel);

        Hydration hydration = hydrationAdapter.getItem(position);
        liquidTimeText.setHint(hydration.getTime());
        liquidMLText.setHint(String.valueOf(hydration.getMl()));

        AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(dialogView).create();

        // add button action
        buttonAdd.setOnClickListener(view -> {
            String liquidTime = liquidTimeText.getText().toString();
            String liquidML = liquidMLText.getText().toString();
            if (liquidTime.isEmpty() || liquidML.isEmpty()) {
                Toast.makeText(getContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                return;
            }
            int mL = Integer.parseInt(liquidML);
            hydrationAdapter.editItem(position, liquidTime, mL);
            JsonManager.saveHydration(getContext(), hydrationList, username);
            dialog.dismiss();
        });

        // cancel button action
        buttonCancel.setOnClickListener(view -> {
            hydrationAdapter.notifyItemChanged(position);
            dialog.cancel();
        });

        dialog.show();
    }
}