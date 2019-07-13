package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModifyItemActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private TextView mDisplayDate2;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText nameEditText;
    private EditText countText;
    private FoodItem foodItem;
    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_item);

        nameEditText = findViewById(R.id.editText4);
        countText = findViewById(R.id.editText2);
        textViewName= findViewById(R.id.tvDate2);

        // This will create the text View for the Expiration Date
        mDisplayDate2 = findViewById(R.id.tvDate2);
        mDisplayDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);  
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog2 = new DatePickerDialog(
                        ModifyItemActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
            }
        });

        // Fill the category spinner
        final Spinner spnSearchCategory3 = findViewById(R.id.spnSearchCategory3);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchCategory3.setAdapter(adapter);

        // Fill the FoodUnit spinner
        final Spinner spnSearchFoodUnit2 = findViewById(R.id.spnSearchFoodUnit2);
        ArrayAdapter<Unit> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Unit.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchFoodUnit2.setAdapter(adapter2);

        foodItem = new Gson().fromJson(getIntent().getStringExtra(FoodItem.EXTRA), FoodItem.class);

        // Fill views with FoodItem information
        SimpleDateFormat sdf = new SimpleDateFormat(getResources().getStringArray(R.array.date_formats)[Settings.getSettings().dateFormat]);
        nameEditText.setText(foodItem.getName());
        spnSearchCategory3.setSelection(Category.indexOf(foodItem.getCategory()));
        countText.setText(String.valueOf(foodItem.getQuantity()));
        spnSearchFoodUnit2.setSelection(Unit.indexOf(foodItem.getUnits()));

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month +1 ;
                Log.d(TAG, "onDateSet: mm/dd/yyy " + month + "/" + dayOfMonth + "/" + year);
                //the variable date has the date.
                String date = month + "/" + dayOfMonth + "/" + year;

                Calendar c = Calendar.getInstance();
                int day2 = c.get(Calendar.DAY_OF_MONTH);
                int month2 = c.get(Calendar.MONTH);
                int year2 = c.get(Calendar.YEAR);
                String date2 = (month2 +1) + "/" + day2 + "/" + year2;
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/DD/YYY");

                //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                if(date.compareTo(date2)< 0){
                    textViewName.requestFocus();
                    Log.d(TAG, "The day  compareTo: " + date);
                    Log.d(TAG, "The day2 compareTo: " + date2);
                    textViewName.setError("This Product is Expired");
                }

                else if(date.equals(date2)){
                    textViewName.requestFocus();
                    Log.d(TAG, "The date equals: " + date);
                    Log.d(TAG, "The date equals: " + date2);
                    textViewName.setError("This Product Will Expire today");
                }
                else {
                    textViewName.requestFocus();
                    textViewName.setError(null);
                    mDisplayDate2.setText(date);


                    Log.d(TAG, "The date1 equals: " + date);
                    Log.d(TAG, "The date2 equals: " + date2);

                }





            }
        };
        textViewName.setText(sdf.format(foodItem.getExpirationDate()));
        Button button = findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name= nameEditText.getText().toString();
                final String count= countText.getText().toString();
                final String textv = textViewName.getText().toString();
                if(name.length()==0) {
                    nameEditText.requestFocus();
                    nameEditText.setError("Field Cannot Be Empty");
                }
                //textv
                else if(textv.length() ==0) {
                    textViewName.requestFocus();
                    textViewName.setError("Field Cannot Be Empty");
                }
                //count
                else if(count.length() ==0) {
                    countText.requestFocus();
                    countText.setError("Field Cannot Be Empty");
                }
                // DO WE WANT THE FOOD NAME WITHOUT NUMBERS?
                    /*else if(!Name.matches("[a-zA-Z]+"))
                {
                    nameEditText.requestFocus();
                    nameEditText.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }*/
                else {
                    // Update FoodItem with new data
                    SimpleDateFormat sdf = new SimpleDateFormat(getResources().getStringArray(R.array.date_formats)[Settings.getSettings().dateFormat]);
                    foodItem.setName(((TextView)findViewById(R.id.editText4)).getText().toString());
                    foodItem.setCategory((Category)spnSearchCategory3.getSelectedItem());
                    foodItem.setUnits((Unit)spnSearchFoodUnit2.getSelectedItem());
                    foodItem.setQuantity(Double.valueOf(countText.getText().toString()));
                    try {
                        foodItem.setExpirationDate(sdf.parse(((TextView)findViewById(R.id.tvDate2)).getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    StorageManager.getLocalStorage().saveItem(foodItem);
                    Intent intent = new Intent(getApplicationContext(), ViewItemActivity.class);
                    intent.putExtra(FoodItem.EXTRA, new Gson().toJson(foodItem));
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), String.format("Updated %s", foodItem), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
