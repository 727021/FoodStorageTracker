package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.List;

public class ViewListActivity extends AppCompatActivity implements ViewListRecyclerAdapter.OnFoodItemListener {

    public static final String EXTRA_SEARCH = "com.fstracker.foodstoragetracker.SEARCH";
    public static final String EXTRA_CATEGORY = "com.fstracker.foodstoragetracker.CATEGORY";

    private Category category;
    private Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        mySpinner = findViewById(R.id.categorySpinner);
        mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Category.values()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.rvItems);
        // use a linear layout manager
        recyclerView.setLayoutManager(layoutManager);

        String categoryString = getIntent().getExtras().getString(EXTRA_CATEGORY);

        for (Category category : Category.values()) {
            if (category.toString().equals(categoryString)) {
                this.category = category;
            }
        }

        System.out.println(category.toString());
        // specify an adapter (see also next example)
        final ViewListRecyclerAdapter mAdapter = new ViewListRecyclerAdapter(StorageManager.getLocalStorage().getItemsByCategory(category), this);
        recyclerView.setAdapter(mAdapter);

        mySpinner.setSelection(Category.indexOf(category));

        //Create adapter for RecyclerView, that allows us to add/replace items
        //https://developer.android.com/guide/topics/ui/layout/recyclerview
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //A callback with the position selected
                Category selected = Category.values()[position];
                //Update the  list by /StorageManager.getLocalStorage().getItemsByCategory()
                List<FoodItem> updatedList =  (selected == Category.ALL) ? StorageManager.getLocalStorage().getAllItems() : StorageManager.getLocalStorage().getItemsByCategory(selected);
                mAdapter.updateList(updatedList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onFoodItemClick(int position) {
        Intent intent = new Intent(this, ViewItemActivity.class);
        FoodItem item = ((ViewListRecyclerAdapter)((RecyclerView)findViewById(R.id.rvItems)).getAdapter()).getItem(position);
        intent.putExtra(FoodItem.EXTRA, new Gson().toJson(item));
        startActivity(intent);
    }
}
