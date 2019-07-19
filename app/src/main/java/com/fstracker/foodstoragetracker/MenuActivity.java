package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    // Used to display a Toast in MenuActivity from another activity.
    public static final String EXTRA_TOAST = "com.fstracker.foodstoragetracker.MENU_TOAST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Fill the category spinner
        Spinner spnSearchCategory = findViewById(R.id.spnSearchCategory);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchCategory.setAdapter(adapter);

        // Allow toast to be shown on the MenuActivity by other activities
        String toast = getIntent().getStringExtra(EXTRA_TOAST);
        if (toast != null && !toast.trim().equals("")) {
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();

        }

    }
    // create the notification
    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(AddItemActivity.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


    }
    /**
     * Open AddItemActivity.
     * @param v The button that was clicked.
     */
    public void addItemClick(View v) {
        Log.d(TAG, "Open AddItemActivity");
        startActivity(new Intent(getApplicationContext(), AddItemActivity.class));
    }
//
    /**
     * Open ViewListActivity.
     * @param v The button that was clicked.
     */
    public void searchNameClick(View v) {
        // Tell ViewListActivity to display all items that match a search String
        String search = ((TextView)findViewById(R.id.txtSearchName)).getText().toString();
        Log.d(TAG, String.format("Open ViewListByNameActivity and display matching items for \"%s\"", search));
        Intent intent = new Intent(getApplicationContext(), ViewListByNameActivity.class);
        intent.putExtra(ViewListActivity.EXTRA_SEARCH, search);
        startActivity(intent);
    }

    /**
     * Open ViewListActivity.
     * @param v The button that was clicked.
     */
    public void searchCategoryClick(View v) {
        // Tell ViewListActivity to display all items in a category
        Category category =  (Category)((Spinner)findViewById(R.id.spnSearchCategory)).getSelectedItem();
        Log.d(TAG, String.format("Open ViewListActivity and display items in category \"%s\"", category.toString()));
        Intent intent = new Intent(getApplicationContext(), ViewListActivity.class);
        intent.putExtra(ViewListActivity.EXTRA_CATEGORY, category.toString());
        startActivity(intent);
    }

    /**
     * Open SettingsActivity.
     * @param v The button that was clicked.
     */
    public void settingsClick(View v) {
        Log.d(TAG, "Open SettingsActivity");
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }
}
