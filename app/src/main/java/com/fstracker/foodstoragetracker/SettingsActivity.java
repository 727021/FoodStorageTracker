package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private Spinner spnDateFormat;
    private Spinner spnReminderUnits;
    private Switch switchDarkMode;
    private Switch switchUseScanner;
    private TextView txtReminderTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spnDateFormat = findViewById(R.id.spnDateFormat);
        spnReminderUnits = findViewById(R.id.spnReminderUnits);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchUseScanner = findViewById(R.id.switchScanner);
        txtReminderTime = findViewById(R.id.txtReminderTime);

        // TODO Remove this once settings are fully implemented
        spnDateFormat.setEnabled(false);
        spnReminderUnits.setEnabled(false);

        // Fill date format spinner
        ArrayAdapter<String> dfAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.date_formats));
        dfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDateFormat.setAdapter(dfAdapter);

        // Fill time units spinner
        ArrayAdapter<String> ruAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.notification_time_units));
        ruAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnReminderUnits.setAdapter(ruAdapter);

        // Fill views with the current app settings
        switchDarkMode.setChecked(Settings.getSettings().darkMode);
        switchUseScanner.setChecked(Settings.getSettings().useScanner);
        spnDateFormat.setSelection(Settings.getSettings().dateFormat);
        txtReminderTime.setText(String.valueOf(Settings.getSettings().reminderTime));
        spnReminderUnits.setSelection(Settings.getSettings().reminderUnits);
    }

    /**
     * Update {@link Settings}.settings and write to SharedPreferences
     */
    private void saveSettings() {
        Settings.getSettings().darkMode = ((Switch)findViewById(R.id.switchDarkMode)).isChecked();
        Settings.getSettings().useScanner = ((Switch)findViewById(R.id.switchScanner)).isChecked();
        Settings.getSettings().dateFormat = ((Spinner)findViewById(R.id.spnDateFormat)).getSelectedItemPosition();
        int reminderTime = Integer.parseInt(((TextView)findViewById(R.id.txtReminderTime)).getText().toString());
        Settings.getSettings().reminderTime = (reminderTime <= 0) ? Settings.getSettings().reminderTime : reminderTime;
        Settings.getSettings().reminderUnits = ((Spinner)findViewById(R.id.spnReminderUnits)).getSelectedItemPosition();

        String json = new Gson().toJson(Settings.getSettings());
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putString(Settings.SETTINGS_KEY, json);
        editor.apply();
        Log.d(TAG, "Saved settings: " + json);
    }

    /**
     * Save {@link Settings} and display a toast in {@link MenuActivity}.
     * @param v The button that was clicked.
     */
    public void saveClick(View v) {
        saveSettings();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra(MenuActivity.EXTRA_TOAST, "Saved settings");
        startActivity(intent);
    }

    /**
     * Go back to {@link MenuActivity}.
     * @param v The button that was clicked.
     */
    public void cancelClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    public void resetClick(View v) {
        switchDarkMode.setChecked(Settings.getSettings(true).darkMode);
        switchUseScanner.setChecked(Settings.getSettings(true).useScanner);
        spnDateFormat.setSelection(Settings.getSettings(true).dateFormat);
        txtReminderTime.setText(String.valueOf(Settings.getSettings(true).reminderTime));
        spnReminderUnits.setSelection(Settings.getSettings(true).reminderUnits);
    }

    /**
     * Delete all food storage data after confirmation from the user.
     * @param v The button that was clicked.
     */
    public void clearClick(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Clear Database?")
                .setMessage("This will permanently erase ALL of your food storage data. Are you sure you want to continue?")
                .setPositiveButton("Clear Data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<FoodItem> all = StorageManager.getLocalStorage().getAllItems();
                        StorageManager.getLocalStorage().deleteAllItems(all);
                        Toast.makeText(getApplicationContext(), "Database cleared", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).create().show();
    }

    /**
     * Open a web browser to the issue report page for the app
     * @param v The button that was clicked.
     */
    public void reportClick(View v) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://bugbucket.io/issues/727021/foodstoragetracker")));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), String.format("No web browser found.%nPlease install one and try again."), Toast.LENGTH_LONG).show();
        }
    }

}
