package com.fstracker.foodstoragetracker;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.fstracker.foodstoragetracker.localstorage.FoodItemDao;
import com.fstracker.foodstoragetracker.localstorage.FoodItemDatabase;

import java.util.Date;
import java.util.List;

public class LocalStorage implements IStorageManager {
    private static final String dbName = "fstDB";
    private List<FoodItem> allItems;
    private FoodItemDatabase db;

    private static final String TAG = LocalStorage.class.getSimpleName();

    public LocalStorage() {
        // Initialize local database
        db = Room.databaseBuilder(FoodStorageApplication.getInstance().getApplicationContext(), FoodItemDatabase.class, dbName)
                .allowMainThreadQueries()
                .build();

        // Just a test
        FoodItemDao dao = db.getDao();
        Log.d(TAG, dao.getAllItems().toString());

        FoodItem f1 = new FoodItem();
        f1.setName("Name1");
        f1.setCategory(Category.ALL);
        f1.setQuantity(5);
        f1.setUnits(Unit.POUND);
        f1.setExpirationDate(new Date());
        FoodItem f2 = new FoodItem();
        f2.setName("Name2");
        f2.setCategory(Category.ALL);
        f2.setQuantity(5);
        f2.setUnits(Unit.POUND);
        f2.setExpirationDate(new Date());
        FoodItem f3 = new FoodItem();
        f3.setName("Name3");
        f3.setCategory(Category.ALL);
        f3.setQuantity(5);
        f3.setUnits(Unit.POUND);
        f3.setExpirationDate(new Date());

        Log.d(TAG, String.format("Saving f1: %d%n", dao.saveItem(f1)));

        Log.d(TAG, dao.getItemsByName("Name1").toString());

        // Do an initial query of all the items in the database
        // (this won't happen every time we need to look at an item)
        // allItems = getAllItems();
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
