package com.fstracker.foodstoragetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

public class ViewListByNameActivity extends AppCompatActivity implements ViewListRecyclerAdapter.OnFoodItemListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_by_name);
        AppCompatDelegate.setDefaultNightMode((Settings.getSettings().darkMode) ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        // Create a linear layout manager for the RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        // Create a recycler view for holding the list of FoodItems
        RecyclerView recyclerView = findViewById(R.id.rvItemsByName);

        // Attach the linear layout manager to the RecyclerView
        recyclerView.setLayoutManager(layoutManager);

        // Get the string containing the name for the FoodItems
        String searchString = getIntent().getExtras().getString(ViewListActivity.EXTRA_SEARCH);

        // Specify an adapter for the RecyclerView
        final ViewListRecyclerAdapter mAdapter;

        // Set the adapter to view all items with the searched name
        mAdapter = new ViewListRecyclerAdapter(StorageManager.getLocalStorage().getItemsByName(searchString), this);

        // Attach the adapter to the RecyclerView
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onFoodItemClick(int position) {
        // Create Intent to pass clicked FoodItem to the ViewItemActivity
        Intent intent = new Intent(this, ViewItemActivity.class);

        // Get the clicked FoodItem from the clicked position
        FoodItem item = ((ViewListRecyclerAdapter)((RecyclerView)findViewById(R.id.rvItemsByName)).getAdapter()).getItem(position);

        // Save the FoodItem to shared preferences
        intent.putExtra(FoodItem.EXTRA, new Gson().toJson(item));

        // Start ViewItemActivity
        startActivity(intent);
    }
}
