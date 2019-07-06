package com.fstracker.foodstoragetracker;

import com.fstracker.foodstoragetracker.localstorage.LocalStorage;

/**
 * A static class containing a single instance of each {@link IStorageManager} implementation.
 */
public class StorageManager {
    private StorageManager() {}

    private static LocalStorage localStorage;
//    private static CloudStorage cloudStorage;
//
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
