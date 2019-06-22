package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AllItem extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

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

        Button button2 = (Button) findViewById(R.id.button3);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openModifyItemActivity();
            }
        });
        }

    public void openModifyItemActivity(){
        //EditText editText = (EditText) findViewById((R.id.editText));
        //String text = editText.getText().toString();
//        EditText editText2 = (EditText) findViewById((R.id.editText2));
        //      String text2 = editText.getText().toString();
        //TextView tvDate = (TextView) findViewById((R.id.tvDate));
        //String text3 = editText.getText().toString();

        Intent intent = new Intent (this, ModifyItemActivity.class);
        //intent.putExtra(EXTRA_TEXT1, text);
        //    intent.putExtra(EXTRA_TEXT2, text2);
        //intent.putExtra(EXTRA_TEXT3, text3);

        startActivity(intent);
    }
    }

