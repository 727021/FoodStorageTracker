package final_project.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fstracker.foodstoragetracker.R;
import com.google.gson.Gson;

import java.util.Date;

public class ViewItemActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        AppCompatDelegate.setDefaultNightMode((Settings.getSettings().darkMode) ?
        AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        String foodItemString = getIntent().getStringExtra(FoodItem.EXTRA);
        FoodItem foodItem = new Gson().fromJson(foodItemString, FoodItem.class);

        String name = foodItem.getName();
        Date expirationDate = foodItem.getExpirationDate();
        Category category = foodItem.getCategory();
        Unit units = foodItem.getUnits();
        double quantity = foodItem.getQuantity();

        String displayText = "Name: " + name + "\n" +
                "Category: " + category + "\n" +
                "Expiration Date: " +
                Settings.getSettings().getDateFormat().format(expirationDate) + "\n" +
                "Amount: " + quantity + " " + units.getName().toLowerCase() +
                ((quantity > 1 && units != Unit.COUNT) ? "s" : "");

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
        intent.putExtra(FoodItem.EXTRA, getIntent().getStringExtra(FoodItem.EXTRA));
        startActivity(intent);
    }
}
