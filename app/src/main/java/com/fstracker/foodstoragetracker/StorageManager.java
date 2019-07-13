package com.fstracker.foodstoragetracker;

import com.fstracker.foodstoragetracker.localstorage.LocalStorage;

import java.text.SimpleDateFormat;

/**
 * A static class containing a single instance of each {@link IStorageManager} implementation.
 */
public class StorageManager {
    private StorageManager() {}

    public static SimpleDateFormat storageDateFormat = new SimpleDateFormat(FoodStorageApplication.getInstance().getResources().getStringArray(R.array.date_formats)[0]);

    private static LocalStorage localStorage;
//    private static CloudStorage cloudStorage;

    /**
     * Create a {@link LocalStorage} instance if there is not one already.
     * @return an instance of {@link LocalStorage}.
     */
    public static LocalStorage getLocalStorage() {
        if (localStorage == null)
            localStorage = new LocalStorage();
        return localStorage;
    }
/*
    public static CloudStorage getCloudStorage() {
        if (cloudStorage == null)
            cloudStorage = new CloudStorage();
        return cloudStorage;
    }
*/
}
