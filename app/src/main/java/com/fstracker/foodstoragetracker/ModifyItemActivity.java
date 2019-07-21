package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ModifyItemActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText nameEditText;
    private EditText countText;
    private FoodItem foodItem;
    private TextView textViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);
        AppCompatDelegate.setDefaultNightMode((Settings.getSettings().darkMode) ?
            AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        nameEditText = findViewById(R.id.editText4);
        countText = findViewById(R.id.editText2);
        textViewDate = findViewById(R.id.tvDate2);

        // This will create the text View for the Expiration Date
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);  
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog2 = new DatePickerDialog(
                    ModifyItemActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                    mDateSetListener, year, month, day);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();

                final String[] format = Settings.getSettings().getDateFormat().toPattern()
                    .toLowerCase().split("/");
                char[] ymdOrder = { 'd', 'm', 'y' };
                int i = 0;
                for (String s : format) {
                    ymdOrder[i++] = s.charAt(0);
                }
                orderDate(dialog2, ymdOrder);
            }
        });

        // Fill the category spinner
        final Spinner spnSearchCategory3 = findViewById(R.id.spnSearchCategory3);
        List<Category> values = new ArrayList<>();
        for (Category cat : Category.values()) {
            if (cat != Category.ALL) // ALL shouldn't be a choice when modifying an item
                values.add(cat);
        }
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchCategory3.setAdapter(adapter);

        // Fill the FoodUnit spinner
        final Spinner spnSearchFoodUnit2 = findViewById(R.id.spnSearchFoodUnit2);
        ArrayAdapter<Unit> adapter2 = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, Unit.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchFoodUnit2.setAdapter(adapter2);

        foodItem = new Gson().fromJson(getIntent().getStringExtra(FoodItem.EXTRA), FoodItem.class);

        // Fill views with FoodItem information
        nameEditText.setText(foodItem.getName());
        spnSearchCategory3.setSelection(Category.indexOf(foodItem.getCategory()) - 1);
        countText.setText(String.valueOf(foodItem.getQuantity()));
        spnSearchFoodUnit2.setSelection(Unit.indexOf(foodItem.getUnits()));

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month++;
                Log.d(TAG, "onDateSet: mm/dd/yyy " + month + "/" + dayOfMonth + "/" + year);
                //the variable date has the date.
                String date = month + "/" + dayOfMonth + "/" + year;

                Calendar c = Calendar.getInstance();
                int day2 = c.get(Calendar.DAY_OF_MONTH);
                int month2 = c.get(Calendar.MONTH);
                int year2 = c.get(Calendar.YEAR);
                String date2 = (month2 +1) + "/" + day2 + "/" + year2;

                if (date.compareTo(date2)< 0) {
                    textViewDate.requestFocus();
                    Log.d(TAG, "The day  compareTo: " + date);
                    Log.d(TAG, "The day2 compareTo: " + date2);
                    textViewDate.setError("This Product is Expired");
                } else if (date.equals(date2)) {
                    textViewDate.requestFocus();
                    Log.d(TAG, "The date equals: " + date);
                    Log.d(TAG, "The date equals: " + date2);
                    textViewDate.setError("This Product Will Expire today");
                } else {
                    textViewDate.requestFocus();
                    textViewDate.setError(null);
                    try {
                        date = Settings.getSettings().getDateFormat().format(
                            new SimpleDateFormat("MM/dd/yyyy").parse(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    textViewDate.setText(date);

                    Log.d(TAG, "The date1 equals: " + date);
                    Log.d(TAG, "The date2 equals: " + date2);
                }
            }
        };

        textViewDate.setText(Settings.getSettings().getDateFormat().format(
            foodItem.getExpirationDate()));

        Button button = findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameEditText.getText().toString();
                final String count = countText.getText().toString();
                final String textv = textViewDate.getText().toString();
                if(name.length() == 0) {
                    nameEditText.requestFocus();
                    nameEditText.setError("Field Cannot Be Empty");
                } else if(textv.length() == 0) {
                    textViewDate.requestFocus();
                    textViewDate.setError("Field Cannot Be Empty");
                } else if(count.length() == 0) {
                    countText.requestFocus();
                    countText.setError("Field Cannot Be Empty");
                } else {
                    // Update FoodItem with new data
                    foodItem.setName(name);
                    foodItem.setCategory((Category)spnSearchCategory3.getSelectedItem());
                    foodItem.setUnits((Unit)spnSearchFoodUnit2.getSelectedItem());
                    foodItem.setQuantity(Double.valueOf(count));
                    try {
                        foodItem.setExpirationDate(Settings.getSettings().getDateFormat()
                            .parse(textv));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    StorageManager.getLocalStorage().saveItem(foodItem);
                    Intent intent = new Intent(getApplicationContext(), ViewItemActivity.class);
                    intent.putExtra(FoodItem.EXTRA, new Gson().toJson(foodItem));
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), String.format("Updated %s", foodItem),
                        Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // From https://stackoverflow.com/questions/33624824/how-to-change-the-order-of-the-numberpickers-in-datepickerdialog
    private static final int SPINNER_COUNT = 3;

    private void orderDate(DatePickerDialog dialog, char[] ymdOrder) {
        if(!dialog.isShowing()) {
            throw new IllegalStateException("Dialog must be showing");
        }

        final int idYear = Resources.getSystem().getIdentifier("year", "id", "android");
        final int idMonth = Resources.getSystem().getIdentifier("month", "id", "android");
        final int idDay = Resources.getSystem().getIdentifier("day", "id", "android");
        final int idLayout = Resources.getSystem().getIdentifier("pickers", "id", "android");

        final NumberPicker spinnerYear = dialog.findViewById(idYear);
        final NumberPicker spinnerMonth = dialog.findViewById(idMonth);
        final NumberPicker spinnerDay = dialog.findViewById(idDay);
        final LinearLayout layout = dialog.findViewById(idLayout);

        layout.removeAllViews();
        for (int i = 0; i < SPINNER_COUNT; i++) {
            switch (ymdOrder[i]) {
                case 'y':
                    layout.addView(spinnerYear);
                    setImeOptions(spinnerYear, i);
                    break;
                case 'm':
                    layout.addView(spinnerMonth);
                    setImeOptions(spinnerMonth, i);
                    break;
                case 'd':
                    layout.addView(spinnerDay);
                    setImeOptions(spinnerDay, i);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid char[] ymdOrder");
            }
        }
    }

    private void setImeOptions(NumberPicker spinner, int spinnerIndex) {
        final int imeOptions;
        if (spinnerIndex < SPINNER_COUNT - 1) {
            imeOptions = EditorInfo.IME_ACTION_NEXT;
        }
        else {
            imeOptions = EditorInfo.IME_ACTION_DONE;
        }
        int idPickerInput = Resources.getSystem().getIdentifier("numberpicker_input", "id",
            "android");
        TextView input = spinner.findViewById(idPickerInput);
        input.setImeOptions(imeOptions);
    }
}
