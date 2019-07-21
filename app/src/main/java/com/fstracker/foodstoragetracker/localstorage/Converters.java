package com.fstracker.foodstoragetracker.localstorage;

import androidx.room.TypeConverter;

import com.fstracker.foodstoragetracker.Category;
import com.fstracker.foodstoragetracker.StorageManager;
import com.fstracker.foodstoragetracker.Unit;

import java.text.ParseException;
import java.util.Date;

/**
 * A class containing type converters for use by a Room database
 */
public class Converters {

    /**
     * Convert from a {@link Date} to a String.
     *
     * @param date The date to convert.
     * @return A formatted Date string.
     */
    @TypeConverter
    public static String fromDate(Date date) {
        // Dates are saved in the same format regardless of settings
        return StorageManager.storageDateFormat.format(date);
    }

    /**
     * Convert from a String to a {@link Date}.
     *
     * @param date The String to convert.
     * @return A Date parsed from a String.
     */
    @TypeConverter
    public static Date toDate(String date) {
        try {
            return StorageManager.storageDateFormat.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * Convert a {@link Category} to a String.
     *
     * @param category The Category to convert.
     * @return The name of the Category.
     */
    @TypeConverter
    public static String fromCategory(Category category) {
        return category.toString();
    }

    /**
     * Convert a String to a {@link Category}.
     *
     * @param category The String to convert.
     * @return The Category whose name matches the String.
     */
    @TypeConverter
    public static Category toCategory(String category) {
        for (Category c : Category.values()) {
            if (c.toString().equals(category))
                return c;
        }
        return Category.ALL;
    }

    /**
     * Convert a {@link Unit} to a String.
     *
     * @param unit The Unit to convert.
     * @return The name of the Unit.
     */
    @TypeConverter
    public static String fromUnit(Unit unit) {
        return unit.getName();
    }

    /**
     * Convert a String to a {@link Unit}.
     *
     * @param unit The String to convert.
     * @return The Unit whose name matches the String.
     */
    @TypeConverter
    public static Unit toUnit(String unit) {
        for (Unit u : Unit.values()) {
            if (u.getName().equals(unit))
                return u;
        }
        return Unit.COUNT;
    }
}
