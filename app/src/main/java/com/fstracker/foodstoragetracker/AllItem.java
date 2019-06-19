package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AllItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_item);

        Intent intent = getIntent();
        String text = intent.getStringExtra(AddItemActivity.EXTRA_TEXT1);
        String text2 = intent.getStringExtra(AddItemActivity.EXTRA_TEXT2);
        String text3 = intent.getStringExtra(AddItemActivity.EXTRA_TEXT3);
        TextView textView13 = (TextView) findViewById(R.id.textView13);
        TextView textView14 = (TextView) findViewById(R.id.textView14);
        TextView textView15 = (TextView) findViewById(R.id.textView15);

        textView13.setText(text);
        textView14.setText(text2);
        textView14.setText(text3);
    }
}
