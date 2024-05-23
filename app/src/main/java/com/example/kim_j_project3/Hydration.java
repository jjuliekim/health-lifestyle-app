package com.example.kim_j_project3;

public class Hydration {
    private String time;
    private int ml;

    // constructor
    public Hydration(String time, int ml) {
        this.time = time;
        this.ml = ml;
    }

    // getters and setters
    public String getTime() {
        return time;
    }

    public int getMl() {
        return ml;
    }

    public void setTime(String newTime) {
        time = newTime;
    }

    public void setMl(int newMl) {
        ml = newMl;
    }
}
