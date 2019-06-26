package com.fstracker.foodstoragetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        /*
         * This will create the text View for the Expiration Date
         */
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddItemActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // Fill the category spinner
        Spinner spnSearchCategory2 = findViewById(R.id.spnSearchCategory2);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchCategory2.setAdapter(adapter);


        // Fill the FoodUnit spinner
        Spinner spnSearchFoodUnit = findViewById(R.id.spnSearchFoodUnit);
        ArrayAdapter<Unit> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Unit.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchFoodUnit.setAdapter(adapter2);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month +1 ;
                Log.d(TAG, "onDateSet: mm/dd/yyy " + month + "/" + dayOfMonth + "/" + year);
                //the variable date has the date.
                String date = month + "/" + dayOfMonth + "/" + year;
                mDisplayDate.setText(date);

            }
        };
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllItemActivity();
            }
        });
    }

    public void openAllItemActivity() {
        // TODO Validate input

        // Create a FoodItem
        FoodItem foodItem = new FoodItem();
        SimpleDateFormat sdf = new SimpleDateFormat(getResources().getStringArray(R.array.date_formats)[Settings.settings.dateFormat]);
        foodItem.setName(((TextView)findViewById(R.id.editText)).getText().toString());
        Category category = (Category)((Spinner)findViewById(R.id.spnSearchCategory2)).getSelectedItem();
        foodItem.setCategory(category);
        try {
            foodItem.setExpirationDate(sdf.parse(((TextView)findViewById(R.id.tvDate)).getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        foodItem.setQuantity(new Double(((TextView)findViewById(R.id.editText3)).getText().toString()));
        Unit units = (Unit)((Spinner)findViewById(R.id.spnSearchFoodUnit)).getSelectedItem();
        foodItem.setUnits(units);

        // TODO Save FoodItem to database

        // Get JSON string
        String json = new Gson().toJson(foodItem);

        // Start ViewItemActivity
        Intent intent = new Intent (this, ViewItemActivity.class);
        intent.putExtra(FoodItem.EXTRA, json);
        startActivity(intent);
    }

}
