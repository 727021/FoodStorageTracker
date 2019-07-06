package com.fstracker.foodstoragetracker.localstorage;

import androidx.room.TypeConverter;

import com.fstracker.foodstoragetracker.Category;
import com.fstracker.foodstoragetracker.FoodStorageApplication;
import com.fstracker.foodstoragetracker.R;
import com.fstracker.foodstoragetracker.Settings;
import com.fstracker.foodstoragetracker.Unit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class containing type converters for use by a Room database
 */
public class Converters {

    @TypeConverter
    public static String fromDate(Date date) {
        // Dates are saved in the format selected in SettingsActivity
        return new SimpleDateFormat(FoodStorageApplication.getInstance().getResources().getStringArray(R.array.date_formats)[Settings.getSettings().dateFormat]).format(date);
    }

    @TypeConverter
    public static Date toDate(String date) {
        try {
            return new SimpleDateFormat(FoodStorageApplication.getInstance().getResources().getStringArray(R.array.date_formats)[Settings.getSettings().dateFormat]).parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    @TypeConverter
    public static String fromCategory(Category category) {
        return category.toString();
    }

    @TypeConverter
    public static Category toCategory(String category) {
        for (Category c : Category.values()) {
            if (c.toString().equals(category))
                return c;
        }
        return Category.ALL;
    }

    @TypeConverter
    public static String fromUnit(Unit unit) {
        return unit.getName();
    }

    @TypeConverter
    public static Unit toUnit(String unit) {
        for (Unit u : Unit.values()) {
            if (u.getName().equals(unit))
                return u;
        }
        return Unit.COUNT;
    }
}
