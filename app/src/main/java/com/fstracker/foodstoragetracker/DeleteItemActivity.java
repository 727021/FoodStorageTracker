package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

public class DeleteItemActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    public static final String EXTRA_FOOD_ITEM = "fstracker.EXTRA_FOOD_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        String json = getIntent().getStringExtra(EXTRA_FOOD_ITEM);
        FoodItem item = new Gson().fromJson(json, FoodItem.class);
        String text = "No food item selected.";
        if (item != null)
            text = String.format("%s (%.2f %s)", item.getName(), item.getQuantity(), item.getUnits().getSymbol());
        ((TextView)findViewById(R.id.txtFoodItem)).setText(text);
    }

    public void cancelClick(View v) {
        Log.d(TAG, "Delete item cancelled");
    }

    public void deleteClick(View v) {
        Log.d(TAG, "Delete item confirmed");
    }
}
