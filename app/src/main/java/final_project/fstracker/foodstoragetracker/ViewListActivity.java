package final_project.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fstracker.foodstoragetracker.R;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

public class ViewListActivity extends AppCompatActivity implements
        ViewListRecyclerAdapter.OnFoodItemListener {

    public static final String EXTRA_SEARCH = "com.fstracker.foodstoragetracker.SEARCH";
    public static final String EXTRA_CATEGORY = "com.fstracker.foodstoragetracker.CATEGORY";

    Animation translate_anim;

    private Category category;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        AppCompatDelegate.setDefaultNightMode((Settings.getSettings().darkMode) ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        // Create a spinner for selecting a FoodItem category to view
        Spinner mySpinner = findViewById(R.id.categorySpinner);

        // place the divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));

        // Attach the array adapter to the spinner
        mySpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, Category.values()));

        // Create a linear layout manager for the RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        // Create a recycler view for holding the list of FoodItems
        RecyclerView recyclerView = findViewById(R.id.rvItems);

        // Attach the linear layout manager to the RecyclerView
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        //this.context = context;

        //translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
        //recyclerView.setAnimation(translate_anim);


        // Get the category string
        String categoryString = getIntent().getExtras().getString(EXTRA_CATEGORY);

        // Specify an adapter for the RecyclerView
        final ViewListRecyclerAdapter mAdapter;

        // Find searched category in category Enum
        for (Category category : Category.values()) {
            if (category.toString().equals(categoryString)) {
                this.category = category;

            }
        }

        // Set spinner selection to searched category
        mySpinner.setSelection(Category.indexOf(category));

        // Set adapter to view all items in searched category
        mAdapter = new ViewListRecyclerAdapter(
                StorageManager.getLocalStorage().getItemsByCategory(category), this);


        // Attach the adapter to the RecyclerView
        recyclerView.setAdapter(mAdapter);
        if( mAdapter != null){
            Toast.makeText(getApplicationContext(), String.format("Please click on the desired item to see the details"),
                    Toast.LENGTH_LONG).show();
        }


        //Create adapter for RecyclerView, that allows us to add/replace items
        //https://developer.android.com/guide/topics/ui/layout/recyclerview
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // A callback with the position selected
                Category selected = Category.values()[position];

                // Create a list for the updated FoodItems
                List<FoodItem> updatedList = (selected == Category.ALL) ?
                        StorageManager.getLocalStorage().getAllItems() :
                        StorageManager.getLocalStorage().getItemsByCategory(selected);

                // Update the  list
                mAdapter.updateList(updatedList);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    @Override
    public void onFoodItemClick(int position) {
        // Create Intent to pass clicked FoodItem to the ViewItemActivity
        Intent intent = new Intent(this, ViewItemActivity.class);

        // Get the clicked FoodItem from the clicked position
        FoodItem item = ((ViewListRecyclerAdapter)((RecyclerView)findViewById(R.id.rvItems))
                .getAdapter()).getItem(position);

        // Save the FoodItem to shared preferences
        intent.putExtra(FoodItem.EXTRA, new Gson().toJson(item));

        // Start ViewItemActivity
        startActivity(intent);
    }
}
