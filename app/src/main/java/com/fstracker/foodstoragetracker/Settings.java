package com.fstracker.foodstoragetracker;

/**
 * A class containing user settings.
 */
public class Settings {
    // The key used for storing settings in SharedPreferences
    public static transient final String SETTINGS_KEY = "com.fstracker.foodstoragetracker.SETTINGS";
    // The Settings instance used by the application
    public static transient Settings settings;
    public static transient Settings defaultSettings;

    public boolean darkMode;
    public boolean useScanner;
    public int dateFormat;
    public int reminderTime;
    public int reminderUnits;
}
