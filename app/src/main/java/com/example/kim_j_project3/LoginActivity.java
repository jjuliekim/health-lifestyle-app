package com.example.kim_j_project3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kim_j_project3.hydration.Hydration;
import com.example.kim_j_project3.meal.Meal;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.i("HERE", "log in screen");
    }

    // handles process when log in button is clicked
    public void loggingIn(View view) {
        EditText usernameText = findViewById(R.id.userText);
        String username = usernameText.getText().toString();
        EditText passwordText = findViewById(R.id.pwText);
        String password = passwordText.getText().toString();

        // check if entries are empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            usernameText.setText("");
            passwordText.setText("");
            return;
        }

        // check if username already exists
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        Intent myIntent = new Intent(LoginActivity.this, MainPageActivity.class);
        // test account
        if (username.equals("test") && password.equals("test")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("test", "username");
            editor.putString("test_password", "test");
            editor.putString("test_height", "70");
            editor.putString("test_weight", "120");
            editor.putString("test_age", "21");
            ArrayList<Meal> mealList = new ArrayList<>(Arrays.asList(
                    new Meal("Pasta", 200, "05/23"),
                    new Meal("Banana", 105, "05/25"),
                    new Meal("French Toast", 150, "05/24"),
                    new Meal("Steak", 510, "05/25"),
                    new Meal("Eggs", 218, "05/23")
            ));
            JsonManager.saveMeals(this, mealList, username);
            ArrayList<Hydration> hydrationList = new ArrayList<>(Arrays.asList(
                    new Hydration("12:00 AM", 250, "05/23"),
                    new Hydration("1:00 PM", 305, "05/25"),
                    new Hydration("6:00 PM", 520, "05/25"),
                    new Hydration("9:00 AM", 230, "05/23")
            ));
            JsonManager.saveHydration(this, hydrationList, username);
            editor.apply();
            myIntent.putExtra("username", username);
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show();
            startActivity(myIntent);
        }
        if (sharedPreferences.contains(username)) {
            // validate password
            String storedPw = sharedPreferences.getString(username + "_password", null);
            if (password.equals(storedPw)) {  // correct pw
                Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show();
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            } else {  // incorrect pw
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                usernameText.setText("");
                usernameText.setHint("Username");
                passwordText.setText("");
                passwordText.setHint("Password");
            }
        } else {
            Toast.makeText(this, "User Does Not Exist", Toast.LENGTH_SHORT).show();
        }
    }

    // go to sign up page
    public void signingUp(View view) {
        EditText usernameText = findViewById(R.id.userText);
        String username = usernameText.getText().toString();
        Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
        myIntent.putExtra("username", username);
        startActivity(myIntent);
    }
}