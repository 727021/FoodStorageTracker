package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fstracker.foodstoragetracker.localstorage.FoodItemDao;
import com.fstracker.foodstoragetracker.localstorage.FoodItemDatabase;
import com.google.gson.Gson;

public class MenuActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    // Used to display a Toast in MenuActivity from another activity.
    public static final String EXTRA_TOAST = "com.fstracker.foodstoragetracker.MENU_TOAST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initialize app settings
        if (Settings.settings == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String json = prefs.getString(Settings.SETTINGS_KEY, getString(R.string.default_settings_json));
            Settings.settings = new Gson().fromJson(json, Settings.class);
        }
        if (Settings.defaultSettings == null) {
            Settings.defaultSettings = new Gson().fromJson(getString(R.string.default_settings_json), Settings.class);
        }

        // Fill the category spinner
        Spinner spnSearchCategory = findViewById(R.id.spnSearchCategory);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchCategory.setAdapter(adapter);

        // Allow toast to be shown on the MenuActivity by other activities
        String toast = getIntent().getStringExtra(EXTRA_TOAST);
        if (toast != null && !toast.trim().equals("")) {
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Open AddItemActivity.
     * @param v The button that was clicked.
     */
    public void addItemClick(View v) {
        Log.d(TAG, "Open AddItemActivity");
        startActivity(new Intent(getApplicationContext(), AddItemActivity.class));
    }
//
    /**
     * Open ViewListActivity.
     * @param v The button that was clicked.
     */
    public void searchNameClick(View v) {
        // Tell ViewListActivity to display all items that match a search String
        String search = ((TextView)findViewById(R.id.txtSearchName)).getText().toString();
        Log.d(TAG, String.format("Open ViewListActivity and display matching items for \"%s\"", search));
        Intent intent = new Intent(getApplicationContext(), ViewListActivity.class);
        intent.putExtra(ViewListActivity.EXTRA_SEARCH, search);
        startActivity(intent);
    }

    /**
     * Open ViewListActivity.
     * @param v The button that was clicked.
     */
    public void searchCategoryClick(View v) {
        // Tell ViewListActivity to display all items in a category
        Category category =  (Category)((Spinner)findViewById(R.id.spnSearchCategory)).getSelectedItem();
        Log.d(TAG, String.format("Open ViewListActivity and display items in category \"%s\"", category.toString()));
        Intent intent = new Intent(getApplicationContext(), ViewListActivity.class);
        intent.putExtra(ViewListActivity.EXTRA_CATEGORY, category.toString());
        startActivity(intent);
    }

    /**
     * Open SettingsActivity.
     * @param v The button that was clicked.
     */
    public void settingsClick(View v) {
        Log.d(TAG, "Open SettingsActivity");
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }
}
