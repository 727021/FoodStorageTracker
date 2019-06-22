package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewItemActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        String foodItemString = getIntent().getStringExtra(FoodItem.EXTRA);
        FoodItem foodItem = new Gson().fromJson(foodItemString, FoodItem.class);

        String name = foodItem.getName();
        Date expirationDate = foodItem.getExpirationDate();
        Category category = foodItem.getCategory();
        Unit units = foodItem.getUnits();
        double quantity = foodItem.getQuantity();

        String displayText = "Name: " + name + "\n" +
                "Category: " + category + "\n" +
                "Expiration Date: " + new SimpleDateFormat(getResources().getStringArray(R.array.date_formats)[Settings.settings.dateFormat]).format(expirationDate) + "\n" +
                "Amount: " + quantity + " " + units.getName().toLowerCase() + ((quantity > 1) ? "s" : "");

        TextView textView = findViewById(R.id.tvViewItem);
        textView.setText(displayText);
    }

    /**
     * Open DeleteItemActivity.
     * @param v The button that was clicked.
     */
    public void deleteItemClick(View v) {
        Log.d(TAG, "Open DeleteItemActivity");
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), DeleteItemActivity.class);
        intent.putExtra(FoodItem.EXTRA, getIntent().getStringExtra(FoodItem.EXTRA));
        startActivity(intent);
    }

    /**
     * Open ModifyItemActivity.
     * @param v The button that was clicked.
     */
    public void modifyItemClick(View v) {
        Log.d(TAG, "Open DeleteItemActivity");
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ModifyItemActivity.class);
        startActivity(intent);
    }
}
