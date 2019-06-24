package com.fstracker.foodstoragetracker.localstorage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;

import com.fstracker.foodstoragetracker.Category;
import com.fstracker.foodstoragetracker.FoodItem;

import java.util.List;

@Dao
public interface FoodItemDao {
    @Query("SELECT * FROM foodItems")
    List<FoodItem> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] saveAllItems(List<FoodItem> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveItem(FoodItem item);

    @Delete
    int deleteAllItems(List<FoodItem> items);

    @Delete
    int deleteItem(FoodItem item);

    @Query("SELECT * FROM foodItems WHERE id = :id LIMIT 1")
    FoodItem getItemById(int id);

    @Query("SELECT * FROM foodItems WHERE name = :name")
    List<FoodItem> getItemsByName(String name);

    @Query("SELECT * FROM foodItems WHERE category = :category")
    List<FoodItem> getItemsByCategory(Category category);

    @RawQuery
    FoodItem queryItem(String query);

    @RawQuery
    List<FoodItem> queryItems(String query);

}
