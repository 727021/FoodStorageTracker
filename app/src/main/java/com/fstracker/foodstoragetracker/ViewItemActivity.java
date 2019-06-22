package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ViewItemActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
    }

    /**
     * Open DeleteItemActivity.
     * @param v The button that was clicked.
     */
    public void deleteItemClick(View v) {
        Log.d(TAG, "Open DeleteItemActivity");
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), DeleteItemActivity.class);
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
