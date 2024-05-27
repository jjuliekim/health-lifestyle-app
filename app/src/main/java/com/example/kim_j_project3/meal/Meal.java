package com.example.kim_j_project3.meal;

public class Meal {
    private String name;
    private int calories;
    private String date;

    // constructor
    public Meal(String name, int calories, String date) {
        this.name = name;
        this.calories = calories;
        this.date = date;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public String getDate() { return date; }

    public void setName(String newName) {
        name = newName;
    }

    public void setCalories(int newCalories) {
        calories = newCalories;
    }

    public void setDate(String newDate) { date = newDate; }
}
