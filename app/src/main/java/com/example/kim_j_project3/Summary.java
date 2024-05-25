package com.example.kim_j_project3;

public class Summary {
    private final String date;
    int totalCal;
    int totalML;

    // constructor
    public Summary(String date, int totalCal, int totalML) {
        this.date = date;
        this.totalCal = totalCal;
        this.totalML = totalML;
    }

    // getters
    public String getDate() {
        return date;
    }

    public int getTotalCal() {
        return totalCal;
    }

    public int getTotalML() {
        return totalML;
    }
}
