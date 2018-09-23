package com.example.saji.smartfood;

import android.app.Application;
import android.content.Context;

// This class for getting context anytime we want
public class SmartFood extends Application {
    private SmartFood instance;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        instance= this;
        SmartFood.context =getApplicationContext();

    }

    public static Context getAppContext() {
        return SmartFood.context;
    }
}