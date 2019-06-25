package com.fstracker.foodstoragetracker;

import android.app.Application;

public class FoodStorageApplication extends Application {
    private static FoodStorageApplication instance;

    public FoodStorageApplication() {
        instance = this;
    }

    public static FoodStorageApplication getInstance() {
        return instance;
    }
}
