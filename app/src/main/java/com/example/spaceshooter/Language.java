package com.example.spaceshooter;

import static android.content.ContentValues.TAG;

import android.util.Log;

public class Language {
    private static Language instance;

    private String start;
    private String points;
    private static String countryName;

    private Language() {
        if (countryName.equals("Vietnam")) {
            start = "Bắt đầu";
            points = "Điểm";
        } else {
            start = "Start";
            points = "Points";
        }
    }

    public String getStart() {
        return start;
    }

    public String getPoints() {
        return points;
    }

    public static void setCountryName(String name) {
        countryName = name;
    }

    public static Language getInstance() {
        if (instance == null) {
            instance = new Language();
        }
        return instance;
    }
}
