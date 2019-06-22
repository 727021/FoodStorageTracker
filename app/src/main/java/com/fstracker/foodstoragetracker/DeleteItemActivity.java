package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

public class DeleteItemActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private FoodItem foodItem;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        json = getIntent().getStringExtra(FoodItem.EXTRA);
        foodItem = new Gson().fromJson(json, FoodItem.class);
        String text = "No food item selected.";
        if (foodItem != null)
                text =  foodItem.getQuantity() + " " + foodItem.getUnits().getName().toLowerCase() + ((foodItem.getQuantity() > 1) ? "s" : "") + " of " + foodItem.getName();
        ((TextView)findViewById(R.id.txtFoodItem)).setText(text);
    }

    public void cancelClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewItemActivity.class);
        intent.putExtra(FoodItem.EXTRA, json);
        startActivity(intent);
        Log.d(TAG, "Delete item cancelled");
    }

    public void deleteClick(View v) {
        Log.d(TAG, "Delete item confirmed");
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

        if (foodItem != null) {
            // TODO Actually delete the item

            // Display a toast in MenuActivity
            String toast = "Deleted " + foodItem.getQuantity() + " " + foodItem.getUnits().getName().toLowerCase() + ((foodItem.getQuantity() > 1) ? "s" : "") + " of " + foodItem.getName();
            intent.putExtra(MenuActivity.EXTRA_TOAST, toast);
        }

        // Go back to the MenuActivity
        startActivity(intent);
    }
}
