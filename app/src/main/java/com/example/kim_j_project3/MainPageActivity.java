package com.example.kim_j_project3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Log.d("HERE", "creating main page 1");
        setContentView(R.layout.activity_mainpage); // breaks here
        Log.d("HERE", "creating main page 2");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.i("HERE", "Created main page");

        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        Log.i("HERE", "meal list size: " + JsonManager.loadMeals(this, username).size());

        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        Fragment frag = new MealFragment();
        frag.setArguments(bundle);
        replaceFragment(frag);


        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // action on selected tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment = null;
                switch(tab.getPosition()) {
                    case 0:
                        selectedFragment = new MealFragment();
                        selectedFragment.setArguments(bundle);
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