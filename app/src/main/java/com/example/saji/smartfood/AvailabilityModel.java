package com.example.saji.smartfood;

import android.icu.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

public class AvailabilityModel {
    private final String time;
    private final UserModel cooker;
    private final String[] dishesList;
    private final String availabilityID;
    private final String date;

    public AvailabilityModel(String timeID, String time, String[] dishesList, UserModel cooker) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Date c = Calendar.getInstance().getTime();
        this.time = time;
        this.availabilityID = timeID;
        this.dishesList = dishesList;
        this.date = df.format(c);
        this.cooker = cooker;

    }

    public String[] getDishesList() {
        return dishesList;
    }

    public String getTime() {
        return time;
    }

    public String getAvailabilityID() { return availabilityID; }

    public String getDate() { return date; }

}
