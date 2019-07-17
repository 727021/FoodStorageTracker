package com.fstracker.foodstoragetracker;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText nameEditText;
    private EditText countText;
    private TextView textViewName;
    public static String CHANNEL_ID = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        nameEditText = findViewById(R.id.editText);
        countText = findViewById(R.id.editText3);
        textViewName = findViewById(R.id.tvDate);

        // This will create the text View for the Expiration Date
        mDisplayDate = findViewById(R.id.tvDate);
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

                final String[] format = Settings.getSettings().getDateFormat().toPattern().toLowerCase().split("/");
                char[] ymdOrder = { 'd', 'm', 'y' };
                int i = 0;
                for (String s : format) {
                    ymdOrder[i++] = s.charAt(0);
                }
                orderDate(dialog, ymdOrder);
            }
        });

        // Fill the category spinner
        Spinner spnSearchCategory2 = findViewById(R.id.spnSearchCategory2);
        List<Category> values = new ArrayList<>();
        for (Category cat : Category.values()) {
            if (cat != Category.ALL) // ALL shouldn't be a choice when adding an item
                values.add(cat);
        }
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
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
                month = month + 1 ;

                Log.d(TAG, "onDateSet: mm/dd/yyy " + month + "/" + dayOfMonth + "/" + year);
                //the variable date has the date.
                String date = month + "/" + dayOfMonth + "/" + year;

                Calendar c = Calendar.getInstance();
                 int day2 = c.get(Calendar.DAY_OF_MONTH);
                 int month2 = c.get(Calendar.MONTH);
                 int year2 = c.get(Calendar.YEAR);
                String date2 = (month2 +1) + "/" + day2 + "/" + year2;

                if(date.compareTo(date2) < 0){
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
                    try {
                        date = Settings.getSettings().getDateFormat().format(new SimpleDateFormat("MM/dd/yyyy").parse(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mDisplayDate.setText(date);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_warning_white_24dp)
                            .setContentTitle("My notification")
                            .setContentText("Much longer text that cannot fit one line...")
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("Much longer text that cannot fit one line..."))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                    //now we create the tap action
                    // Create an explicit intent for an Activity in your app
                    //Intent intent = new Intent(this, AlertDetails.class);


                    Log.d(TAG, "The date1 equals: " + date);
                    Log.d(TAG, "The date2 equals: " + date2);

                }
            }
        };

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameEditText.getText().toString();
                final String count = countText.getText().toString();
                final String textv = textViewName.getText().toString();
                Calendar c = Calendar.getInstance();
                String date = Settings.getSettings().getDateFormat().format(c.getTime());
                Log.d(TAG, "The date equals: " + date);
                Log.d(TAG, "The tv text equals: " + textv);
                if(textv == date){
                    textViewName.requestFocus();
                    Log.d(TAG, "The date equals: " + date);
                    Log.d(TAG, "The date equals: " + textv);
                    textViewName.setError("This Product Will Expire today");
                }
                if(name.length() == 0) {
                    nameEditText.requestFocus();
                    nameEditText.setError("Field Cannot Be Empty");
                }
                else if(textv.length() == 0) {
                    textViewName.requestFocus();
                    textViewName.setError("Field Cannot Be Empty");
                }
                else if(count.length() == 0) {
                    countText.requestFocus();
                    countText.setError("Field Cannot Be Empty");
                }
                else {
                    openAllItemActivity();

                }
            }
        });
    }

    public void openAllItemActivity() {
        // Create a FoodItem
        FoodItem foodItem = new FoodItem();
        foodItem.setName(((TextView)findViewById(R.id.editText)).getText().toString());
        Category category = (Category)((Spinner)findViewById(R.id.spnSearchCategory2)).getSelectedItem();
        foodItem.setCategory(category);
        try {
            foodItem.setExpirationDate(Settings.getSettings().getDateFormat().parse(((TextView)findViewById(R.id.tvDate)).getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        foodItem.setQuantity(Double.valueOf(((TextView)findViewById(R.id.editText3)).getText().toString()));
        Unit units = (Unit)((Spinner)findViewById(R.id.spnSearchFoodUnit)).getSelectedItem();
        foodItem.setUnits(units);

        // Save FoodItem to database
        Log.d(TAG, "food item contain: " + foodItem);
        StorageManager.getLocalStorage().saveItem(foodItem);

        // Get JSON string
        String json = new Gson().toJson(foodItem);

        // Start ViewItemActivity
        Intent intent = new Intent(this, ViewItemActivity.class);
        intent.putExtra(FoodItem.EXTRA, json);
        startActivity(intent);
        Log.d(TAG, "Saved Items: " + json);
        Toast.makeText(getApplicationContext(), String.format("Saved %s", foodItem), Toast.LENGTH_LONG).show();

        // set the notification
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext(), AddItemActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_warning_white_24dp)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

                builder2.build();
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
        int idPickerInput = Resources.getSystem().getIdentifier("numberpicker_input", "id", "android");
        TextView input = spinner.findViewById(idPickerInput);
        input.setImeOptions(imeOptions);
    }
}
