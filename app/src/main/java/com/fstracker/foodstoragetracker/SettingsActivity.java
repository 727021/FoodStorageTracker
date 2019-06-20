package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity {
    public static final String SETTINGS_KEY = "com.fstracker.foodstoragetracker.SETTINGS";
    private String TAG = getClass().getSimpleName();
    private static final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Settings.settings.darkMode = true;
        Settings.settings.dateFormat = 1;
        Settings.settings.useScanner = false;
        Settings.settings.dayReminder = 14;



        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        String json1 = prefs.getString(SettingsActivity.SETTINGS_KEY, getString(R.string.default_settings_json));
        Log.d(TAG, "Current: " + json1);
        Settings.settings = new Gson().fromJson(json1, Settings.class);





        String json = gson.toJson(Settings.settings);
        Log.d(TAG, "New: " + json);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SETTINGS_KEY, json);
        editor.apply();
    }
}
