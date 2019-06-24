package com.fstracker.foodstoragetracker.localstorage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fstracker.foodstoragetracker.FoodItem;

@Database(entities = {FoodItem.class}, version = 1)
public abstract class FoodItemDatabase extends RoomDatabase {
    public abstract FoodItemDao getDao();

}
