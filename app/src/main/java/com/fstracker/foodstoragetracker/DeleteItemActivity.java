package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * This activity prompts the user for confirmation before deleting a {@link FoodItem}.
 * In the future it may be replaced with a dialog in {@link ViewItemActivity}.
 */
public class DeleteItemActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private FoodItem foodItem;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        AppCompatDelegate.setDefaultNightMode((Settings.getSettings().darkMode) ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        // Store the json from ViewItemActivity and deserialize it
        json = getIntent().getStringExtra(FoodItem.EXTRA);
        foodItem = new Gson().fromJson(json, FoodItem.class);
        // Show the user what they're deleting
        ((TextView)findViewById(R.id.txtFoodItem)).setText(
                (foodItem == null) ? "No food item selected." :
                        String.format("%s%nExpires %s", foodItem.toString(), Settings.getSettings()
                                .getDateFormat().format(foodItem.getExpirationDate()))
        );
    }

    /**
     * Don't delete the FoodItem and reopen {@link ViewItemActivity}.
     * @param v The button that was clicked.
     */
    public void cancelClick(View v) {
        Log.d(TAG, "Delete item cancelled");
        Intent intent = new Intent(getApplicationContext(), ViewItemActivity.class);
        // Give ViewItemActivity the same json it sent us
        intent.putExtra(FoodItem.EXTRA, json);
        startActivity(intent);
    }

    /**
     * Delete the FoodItem and display a toast in {@link MenuActivity}.
     * @param v The button that was clicked.
     */
    public void deleteClick(View v) {
        Log.d(TAG, "Delete item confirmed");
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

        // Don't try to delete null
        if (foodItem != null) {
            // Actually delete the item
            StorageManager.getLocalStorage().deleteItem(foodItem);

            // Display a toast in MenuActivity
            intent.putExtra(MenuActivity.EXTRA_TOAST, String.format("Deleted %s", foodItem));
        }

        // Go back to the MenuActivity
        startActivity(intent);
    }
}
