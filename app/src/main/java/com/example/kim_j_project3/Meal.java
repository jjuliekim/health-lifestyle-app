package com.example.kim_j_project3;

public class Meal {
    private String name;
    private int calories;

    // constructor
    public Meal(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setCalories(int newCalories) {
        calories = newCalories;
    }
}
