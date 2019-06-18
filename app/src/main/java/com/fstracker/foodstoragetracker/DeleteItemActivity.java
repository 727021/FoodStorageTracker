package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DeleteItemActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    public static final String EXTRA_FOOD_ITEM = "fstracker.EXTRA_FOOD_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        String itemName = getIntent().getStringExtra(EXTRA_FOOD_ITEM);
        ((TextView)findViewById(R.id.txtFoodItem)).setText(itemName);
    }

    public void cancelClick(View v) {
        Log.d(TAG, "Delete item cancelled");
    }

    public void deleteClick(View v) {
        Log.d(TAG, "Delete item confirmed");
    }
}
