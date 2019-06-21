package com.fstracker.foodstoragetracker;

public class Settings {
    public static transient final String SETTINGS_FILENAME = "fstracker.appsettings.xml";
    public static transient final String SETTINGS_KEY = "com.fstracker.foodstoragetracker.SETTINGS";
    public static transient Settings settings;

    public boolean darkMode;
    public boolean useScanner;
    public int dateFormat;
    public int reminderTime;
    public int reminderUnits;
}
