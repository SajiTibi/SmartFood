package com.example.saji.smartfood;

public class AvailabilityModel {
    private final String time;
    private final UserModel cooker;
    private final String[] dishesList;
    private final String availabilityID;

    public AvailabilityModel(String timeID, String time, String[] dishesList, UserModel cooker) {

        this.time = time;
        this.availabilityID = timeID;
        this.dishesList = dishesList;
        this.cooker = cooker;

    }

    public String[] getDishesList() {
        return dishesList;
    }

    public String getTime() {
        return time;
    }

    public String getAvailabilityID() { return availabilityID; }
}
