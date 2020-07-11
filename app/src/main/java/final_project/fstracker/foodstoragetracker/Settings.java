package final_project.fstracker.foodstoragetracker;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fstracker.foodstoragetracker.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;

/**
 * A class containing user settings.
 */
public class Settings {
    // The key used for storing settings in SharedPreferences
    public static transient final String SETTINGS_KEY = "com.fstracker.foodstoragetracker.SETTINGS";
    // The Settings instance used by the application
    private static transient Settings settings;
    private static transient Settings defaultSettings;
    public static Settings getSettings(boolean isDefault) {
        if (isDefault) {
            if (defaultSettings == null) {
                defaultSettings = new Gson().fromJson(FoodStorageApplication.getInstance()
                    .getApplicationContext().getString(R.string.default_settings_json),
                    Settings.class);
            }
            return defaultSettings;
        }

        if (settings == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                FoodStorageApplication.getInstance().getApplicationContext());
            String json = prefs.getString(SETTINGS_KEY, FoodStorageApplication.getInstance()
                .getApplicationContext().getString(R.string.default_settings_json));
            settings = new Gson().fromJson(json, Settings.class);
        }
        return settings;
    }
    public static Settings getSettings() { return getSettings(false); }

    public boolean darkMode;
    public boolean useScanner;
    public int dateFormat;
    public int reminderTime;
    public int reminderUnits;

    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(FoodStorageApplication.getInstance().getResources()
            .getStringArray(R.array.date_formats)[dateFormat]);
    }
}
