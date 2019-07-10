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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        Spinner mySpinner = findViewById(R.id.categorySpinner);
        mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Category.values()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.rvItems);
        // use a linear layout manager
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        final ViewListRecyclerAdapter mAdapter = new ViewListRecyclerAdapter(StorageManager.getLocalStorage().getItemsByCategory(Category.ALL), this);
        recyclerView.setAdapter(mAdapter);

        //Create adapter for recyclerview, that allows us toadd/replace itens
        //https://developer.android.com/guide/topics/ui/layout/recyclerview
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //A callback with the position selected
                Category selected = Category.values()[position];
                //Here we should update the  list by /StorageManager.getLocalStorage().getItemsByCategory()
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
