package com.fstracker.foodstoragetracker.localstorage;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.fstracker.foodstoragetracker.Converters;
import com.fstracker.foodstoragetracker.FoodItem;

@Database(entities = {FoodItem.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class FoodItemDatabase extends RoomDatabase {
    public abstract FoodItemDao getDao();
}
