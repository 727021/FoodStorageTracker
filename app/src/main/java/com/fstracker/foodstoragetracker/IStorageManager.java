package com.fstracker.foodstoragetracker;

import java.util.List;

/**
 * An interface defining functions for StorageManager implementations.
 */
public interface IStorageManager {

    /**
     * @return A list of all FoodItem objects in the database.
     */
    List<FoodItem> getAllItems();

    /**
     * Saves a collection of new or existing FoodItem objects to the database.
     * @param items A list of FoodItem objects to be saved.
     * @return The number of FoodItems saved or updated.
     */
    int saveAllItems(List<FoodItem> items);

    /**
     * Saves a single FoodItem object to the database.
     * @param item The FoodItem object to save.
     * @return The number of FoodItems saved or updated (0 or 1).
     */
    int saveItem(FoodItem item);

    /**
     * Delete a collection of FoodItem objects from the database.
     * @param items A list of FoodItem objects to be deleted.
     * @return The number of FoodItems deleted.
     */
    int deleteAllItems(List<FoodItem> items);

    /**
     * Delete a single FoodItem object from the database.
     * @param item The FoodItem object to be deleted.
     * @return The number of FoodItems deleted (0 or 1).
     */
    int deleteItem(FoodItem item);

    /**
     * Get a FoodItem object from the database by ID.
     * @param id The ID of the FoodItem to search for
     * @return The FoodItem with the given id, or null if none is found.
     */
    FoodItem getItemById(int id);

    /**
     * Get a collection of FoodItems objects with a given name.
     * @param name The name to search for.
     * @return A list of FoodItems with the given name.
     */
    List<FoodItem> getItemsByName(String name);

    /**
     * Get all FoodItem objects with a certain category.
     * @param category The category to search for.
     * @return A list of all FoodItems in the given category.
     */
    List<FoodItem> getItemsByCategory(Category category);
}
