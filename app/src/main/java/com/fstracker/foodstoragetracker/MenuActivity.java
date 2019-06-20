package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MenuActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Fill the category spinner
        Spinner spnSearchCategory = findViewById(R.id.spnSearchCategory);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchCategory.setAdapter(adapter);
    }

    /**
     * Open AddItemActivity.
     * @param v The button that was clicked.
     */
    public void addItemClick(View v) {
        Log.d(TAG, "Open AddItemActivity");
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), AddItemActivity.class);
        startActivity(intent);
    }

    /**
     * Open ViewListActivity.
     * @param v The button that was clicked.
     */
    public void searchNameClick(View v) {
        // Tell ViewListActivity to display all items that match a search String
        String search = ((TextView)findViewById(R.id.txtSearchName)).getText().toString();
        Log.d(TAG, String.format("Open ViewListActivity and display matching items for \"%s\"", search));
    }

    /**
     * Open ViewListActivity.
     * @param v The button that was clicked.
     */
    public void searchCategoryClick(View v) {
        // Tell ViewListActivity to display all items in a category
        Category category =  (Category)((Spinner)findViewById(R.id.spnSearchCategory)).getSelectedItem();
        Log.d(TAG, String.format("Open ViewListActivity and display items in category \"%s\"", category.toString()));
    }

    /**
     * Open SettingsActivity.
     * @param v The button that was clicked.
     */
    public void settingsClick(View v) {
        Log.d(TAG, "Open SettingsActivity");
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }
}
