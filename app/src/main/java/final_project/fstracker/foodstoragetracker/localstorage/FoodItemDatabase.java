package final_project.fstracker.foodstoragetracker.localstorage;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import final_project.fstracker.foodstoragetracker.FoodItem;

/**
 * The RoomDatabase class to be used with {@link LocalStorage}.
 * The only instance of this class should be in LocalStorage.
 */
@Database(entities = {FoodItem.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class FoodItemDatabase extends RoomDatabase {
    public abstract FoodItemDao getDao();
}
