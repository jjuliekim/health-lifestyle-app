package com.example.kim_j_project3;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonManager {

    // save list of meals
    public static void saveMeals(Context context, ArrayList<Meal> mealList, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonList = gson.toJson(mealList);
        editor.putString(username + "_mealList", jsonList);
        editor.apply();
    }

    // save list of hydration
    public static void saveHydration(Context context, ArrayList<Hydration> mealList, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonList = gson.toJson(mealList);
        editor.putString(username + "_hydrationList", jsonList);
        editor.apply();
    }

    // load meal list
    public static ArrayList<Meal> loadMeals(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(username + "_mealList", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Meal>>() {}.getType();
        return gson.fromJson(json, type);
    }

    // load hydration list
    public static ArrayList<Meal> loadHydration(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(username + "_hydrationList", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Hydration>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
