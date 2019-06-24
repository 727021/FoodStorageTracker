package com.fstracker.foodstoragetracker;

import android.content.Context;

import androidx.room.Room;

import com.fstracker.foodstoragetracker.localstorage.FoodItemDatabase;

import java.util.List;

public class LocalStorage implements IStorageManager {
    private static final String dbName = "fstDB";
    private List<FoodItem> allItems;
    private FoodItemDatabase db;

    public LocalStorage(Context context) {
        // Initialize local database
        db = Room.databaseBuilder(context, FoodItemDatabase.class, dbName)
                .allowMainThreadQueries()
                .build();

        // Do an initial query of all the items in the database
        // (this won't happen every time we need to look at an item)
        allItems = getAllItems();
    }

    @Override
    public List<FoodItem> getAllItems() {
        return null;
    }

    @Override
    public int saveAllItems(List<FoodItem> items) {
        return 0;
    }

    @Override
    public int saveItem(FoodItem item) {
        return 0;
    }

    @Override
    public int deleteAllItems(List<FoodItem> items) {
        return 0;
    }

    @Override
    public int deleteItem(FoodItem item) {
        return 0;
    }

    @Override
    public FoodItem getItemById(int id) {
        return null;
    }

    @Override
    public List<FoodItem> getItemsByName(String name) {
        return null;
    }

    @Override
    public List<FoodItem> getItemsByCategory(Category category) {
        return null;
    }
}
