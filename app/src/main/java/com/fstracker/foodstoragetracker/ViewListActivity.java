package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ViewListActivity extends AppCompatActivity {

    public static final String EXTRA_SEARCH = "com.fstracker.foodstoragetracker.SEARCH";
    public static final String EXTRA_CATEGORY = "com.fstracker.foodstoragetracker.CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        Spinner mySpinner = (Spinner) findViewById(R.id.categorySpinner);
        mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Category.values()));
    }
}
