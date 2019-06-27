package com.fstracker.foodstoragetracker.localstorage;

import android.util.Log;

import androidx.room.Room;

import com.fstracker.foodstoragetracker.Category;
import com.fstracker.foodstoragetracker.FoodItem;
import com.fstracker.foodstoragetracker.FoodStorageApplication;
import com.fstracker.foodstoragetracker.IStorageManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of a local food storage database.
 * <p>
 * The only instance of this class should be in {@link com.fstracker.foodstoragetracker.StorageManager}.
 * Use <pre>StorageManager.getLocalStorage()</pre> to access the local database.
 */
public class LocalStorage implements IStorageManager {
    private static final String TAG = LocalStorage.class.getSimpleName();
    private static final String dbName = "fstDB";
    // Keep a list of all FoodItem objects in memory so we
    // don't have to get them from the database every time.
    private List<FoodItem> allItems;
    private FoodItemDatabase db;

    // Keep allItems up to date
    private Date lastUpdate;
    // Minimum time in seconds between updates
    private final long updateInterval = 20;
    // allItems will only be updated when this is false
    private boolean upToDate;

    public LocalStorage() {
        // Initialize local database
        db = Room.databaseBuilder(FoodStorageApplication.getInstance().getApplicationContext(), FoodItemDatabase.class, dbName)
                .allowMainThreadQueries()
                .build();

        // Only use this for getting items. Saves and deletions should happen on the database.
        lastUpdate = new Date();

        // Do an initial query of all the items in the database
        // (this won't happen every time we need to look at an item)
        allItems = getAllItems();
    }

    /**
     * @return The time, in seconds, since allItems was last updated.
     */
    private long secondsSinceUpdate() {
        return (new Date().getTime() - lastUpdate.getTime()) / 1000;
    }

    /**
     * Update allItems if needed.
     * <p>
     * An update only happens if either allItems is null, or the updateInterval has passed and the
     * list isn't up to date.
     */
    private void updateList() {
        // This could be done in a different thread later
        if (allItems == null || (secondsSinceUpdate() >= updateInterval && !upToDate))
            allItems = db.getDao().getAllItems();
        upToDate = true;
    }

    @Override
    public List<FoodItem> getAllItems() {
        updateList();
        return allItems;
    }

    @Override
    public int saveAllItems(List<FoodItem> items) {
        long[] ids = db.getDao().saveAllItems(items);
        upToDate = false;
        // Count ids and return the number of saved items
        int i = 0;
        for (; i < ids.length; i++) {
            if (ids[i] == 0)
                return i;
        }
        Log.i(TAG, String.format("Saved %d items", i));
        return i;
    }

    @Override
    public int saveItem(FoodItem item) {
        long id = db.getDao().saveItem(item);
        upToDate = false;
        Log.i(TAG, String.format("Saved %s", item.toString()));
        return (id > 0) ? 1 : 0;
    }

    @Override
    public int deleteAllItems(List<FoodItem> items) {
        upToDate = false;
        Log.i(TAG, String.format("Deleting %d items", items.size()));
        return db.getDao().deleteAllItems(items);
    }

    @Override
    public int deleteItem(FoodItem item) {
        upToDate = false;
        Log.i(TAG, String.format("Deleting %s", item.toString()));
        return db.getDao().deleteItem(item);
    }

    @Override
    public FoodItem getItemById(int id) {
        updateList();
        // If ids are sorted, this could change to a binary search
        for (FoodItem item : allItems) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    @Override
    public List<FoodItem> getItemsByName(String name) {
        updateList();
        List<FoodItem> result = new ArrayList<>();
        for (FoodItem item : allItems) {
            if (item.getName().equals(name))
                result.add(item);
        }
        return result;
    }

    @Override
    public List<FoodItem> getItemsByCategory(Category category) {
        updateList();
        List<FoodItem> result = new ArrayList<>();
        for (FoodItem item : allItems) {
            if (item.getCategory() == category)
                result.add(item);
        }
        return result;
    }
}
