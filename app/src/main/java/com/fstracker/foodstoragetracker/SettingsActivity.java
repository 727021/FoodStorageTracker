package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Fill date format spinner
        Spinner spnDateFormat = findViewById(R.id.spnDateFormat);
        ArrayAdapter<String> dfAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.date_formats));
        dfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDateFormat.setAdapter(dfAdapter);

        // Fill time units spinner
        Spinner spnReminderUnits = findViewById(R.id.spnReminderUnits);
        ArrayAdapter<String> ruAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.notification_time_units));
        ruAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnReminderUnits.setAdapter(ruAdapter);

        // Fill views with the current app settings
        ((Switch)findViewById(R.id.switchDarkMode)).setChecked(Settings.settings.darkMode);
        ((Switch)findViewById(R.id.switchScanner)).setChecked(Settings.settings.useScanner);
        spnDateFormat.setSelection(Settings.settings.dateFormat);
        ((TextView)findViewById(R.id.txtReminderTime)).setText(String.valueOf(Settings.settings.reminderTime));
        spnReminderUnits.setSelection(Settings.settings.reminderUnits);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save settings to SharedPreferences
        String json = new Gson().toJson(Settings.settings);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putString(Settings.SETTINGS_KEY, json);
        editor.apply();
        Log.d(TAG, "Saved settings: " + json);
    }
}
