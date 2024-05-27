package com.example.kim_j_project3.hydration;

public class Hydration {
    private String time;
    private int ml;
    private String date;

    // constructor
    public Hydration(String time, int ml, String date) {
        this.time = time;
        this.ml = ml;
        this.date = date;
    }

    // getters and setters
    public String getTime() {
        return time;
    }

    public int getMl() {
        return ml;
    }

    public String getDate() { return date; }

    public void setTime(String newTime) {
        time = newTime;
    }

    public void setMl(int newMl) {
        ml = newMl;
    }

    public void setDate(String newDate) { date = newDate; }
}
