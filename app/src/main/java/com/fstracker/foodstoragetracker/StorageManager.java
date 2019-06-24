package com.fstracker.foodstoragetracker;

public class StorageManager {
    private StorageManager() {}

    private static LocalStorage localStorage;
//    private static CloudStorage cloudStorage;

    public static LocalStorage getLocalStorage() {
        // TODO This needs to pass getApplicationContext() to LocalStorage
        //if (localStorage == null)
            //localStorage = new LocalStorage();
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
