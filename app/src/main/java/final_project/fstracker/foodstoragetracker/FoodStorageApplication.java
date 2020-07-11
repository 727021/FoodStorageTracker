package final_project.fstracker.foodstoragetracker;

import android.app.Application;

/**
 * This class exists so we can get an application context from outside an activity.
 */
public class FoodStorageApplication extends Application {
    private static FoodStorageApplication instance;

    public FoodStorageApplication() {
        instance = this;
    }

    public static FoodStorageApplication getInstance() {
        return instance;
    }
}
