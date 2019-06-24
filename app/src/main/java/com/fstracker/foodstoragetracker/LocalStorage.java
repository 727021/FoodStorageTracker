package com.fstracker.foodstoragetracker;

import java.util.List;

public class LocalStorage implements IStorageManager {
    private List<FoodItem> allItems;

    public LocalStorage() {
        // Initialize local database
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
