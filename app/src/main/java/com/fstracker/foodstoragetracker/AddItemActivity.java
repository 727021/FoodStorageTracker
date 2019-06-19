package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT1 = "com.fstracker.foodstoragetracker.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.fstracker.foodstoragetracker.EXTRA_TEXT2";
    public static final String EXTRA_TEXT3 = "com.fstracker.foodstoragetracker.EXTRA_TEXT3";
    private final String TAG = getClass().getSimpleName();
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    //private DatePickerDialog.OnDateSetListener mDateSetListener;

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
    public void openAllItemActivity(){
        EditText editText = (EditText) findViewById((R.id.editText));
        String text = editText.getText().toString();
        EditText editText2 = (EditText) findViewById((R.id.editText2));
        String text2 = editText.getText().toString();
        TextView tvDate = (TextView) findViewById((R.id.tvDate));
        String text3 = editText.getText().toString();

        Intent intent = new Intent (this, AllItem.class);
        intent.putExtra(EXTRA_TEXT1, text);
        intent.putExtra(EXTRA_TEXT2, text2);
        intent.putExtra(EXTRA_TEXT3, text3);

        startActivity(intent);
    }
}
