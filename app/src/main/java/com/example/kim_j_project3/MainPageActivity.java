package com.example.kim_j_project3;

import static com.example.kim_j_project3.JsonManager.loadMeals;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HERE", "creating main page 1");
        super.onCreate(savedInstanceState);
        Log.d("HERE", "creating main page 2");
        EdgeToEdge.enable(this);
        Log.d("HERE", "creating main page 3");
        setContentView(R.layout.activity_mainpage);
        Log.d("HERE", "creating main page 4");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Log.d("HERE", "creating main page 5");
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Log.d("HERE", "creating main page 6");
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            Log.d("HERE", "creating main page 7");
            return insets;
        });
        Log.i("HERE", "Created main page");

        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        Log.i("HERE", "meal list size: " + loadMeals(this, username).size());

        replaceFragment(new HydrationFragment());
//        replaceFragment(MealFragment.newInstance(username)); // default fragment on creation
        Log.i("HERE", "replaced fragment");
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // action on selected tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment = null;
                switch(tab.getPosition()) {
                    case 0:
                        selectedFragment = MealFragment.newInstance(username);
                        break;
                    case 1:
                        selectedFragment = new HydrationFragment();
                        break;
                    case 2:
                        selectedFragment = new SummaryFragment();
                        break;
                    case 3:
                        selectedFragment = new SensorFragment();
                        break;
                }
                if (selectedFragment != null) {
                    replaceFragment(selectedFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    // change fragments
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}