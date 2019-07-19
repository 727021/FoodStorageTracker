package com.fstracker.foodstoragetracker;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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

import com.allyants.notifyme.NotifyMe;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private NotificationManager mNotificationManager;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText nameEditText;
    private EditText countText;
    private TextView textViewName;
    public static String CHANNEL_ID = "1";
    public int year;
    public int month;
    public int day;


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
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

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
        // best notification
        Calendar notiCal = Calendar.getInstance();
        final String textv = textViewName.getText().toString();
        int month2 = Integer.parseInt(textv.substring(0,2));
        int day2 = Integer.parseInt(textv.substring(3,5));
        int Year2 = Integer.parseInt(textv.substring(6,10));
        // this is so the teacher test  the notification  will call a week before expire.
        if (month2 == month && Year2 == year && day2 == day || day2 == day + 1 || day2 == day +2 || day2 == day +3 || day2 == day +4
                || day2 == day +5 || day2 == day +6 || day2 == day +7) {
            notiCal.set(Calendar.YEAR, year);
            notiCal.set(Calendar.MONTH, month);
            notiCal.set(Calendar.DAY_OF_MONTH, day);


            Log.d(TAG, "The  notification date is " + textv);
            Log.d(TAG, "The  notification date is " + month2);
            Log.d(TAG, "The  notification date is " + day2);
            Log.d(TAG, "The  notification date is " + Year2);
            createNotificationChannel();
            //Intent intent2 = new Intent(this, ViewItemActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext(), AddItemActivity.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_warning_white_24dp)
                    .setContentTitle("Food Storage Tracker ")
                     .setContentText("The item " + nameEditText.getText().toString() + " will expire in one week")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    // Set the intent that will fire when the user taps the notification
            .setAutoCancel(true)
            .setContentIntent(pendingIntent);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            int number  = 2;

            notificationManager.notify(number, builder2.build());

            //Notification notification =  builder2.build();
            //MenuActivity menu = null;
            //menu.createNotificationChannel();




            /* working
            NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                    .title("Food Storage Tracker ")
                    .content("The item " + nameEditText.getText().toString() + " will expire in one week")
                    .large_icon(R.drawable.ic_check_circle)

                    .time(notiCal)
                    .addAction(new Intent(), "Snooze", false)
                    .key("test")
                    .addAction(new Intent(), "Dismiss", true, false)
                    .addAction(new Intent(), "Done")
                    .small_icon(R.drawable.ic_warning_white_24dp)

                    .build();

*/
        }
        else
        if (day2 < 7 && month >1) {
            day2 = 30 - day2;
            month2 = month2 -1;}
        else if (day2 < 7 && month == 1) {
            day2 = 30 - day2;
            month2 = 12;
        }
            notiCal.set(Calendar.YEAR, Year2);
            notiCal.set(Calendar.MONTH, month2);
            notiCal.set(Calendar.DAY_OF_MONTH, day2);


            Log.d(TAG, "The  notification date is " + textv);
            Log.d(TAG, "The  notification date is " + month2);
            Log.d(TAG, "The  notification date is " + day2);
            Log.d(TAG, "The  notification date is " + Year2);


            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                    .title("Food Storage Tracker ")
                    .content("The item " + nameEditText.getText().toString() + " will expire in one Month")
                    .large_icon(R.drawable.ic_check_circle)

                    .time(notiCal)
                    .addAction(new Intent(), "Snooze", false)
                    .key("test")
                    .addAction(new Intent(), "Dismiss", true, false)
                    .addAction(new Intent(), "Done")
                    .small_icon(R.drawable.ic_warning_white_24dp)

                    .build();


/* working
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext(), AddItemActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_warning_white_24dp)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent);
                //.setAutoCancel(true);

              //Notification notification =  builder2.build();
              //MenuActivity menu = null;
              //menu.createNotificationChannel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int number  = 2;

        notificationManager.notify(number, builder2.build());

*/
/* working 2
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext(), AddItemActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        //bigText.bigText(verseurl);
        bigText.setBigContentTitle("Food Storage Warning");
        bigText.setSummaryText("The following product will expire soon");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("This porduct will expire soon");
        mBuilder.setContentText("Vivo pues ");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

// === Removed some obsoletes


            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(AddItemActivity.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
            mNotificationManager.notify(0, mBuilder.build());

        }

        mNotificationManager.notify(0, mBuilder.build());
mNotificationManager.notify(foodItem.getName(), 0, mBuilder.build());
*/
      //  displayNotification();
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

    public void displayNotification(){
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), AddItemActivity.CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_warning_white_24dp);
        builder.setContentTitle("Food Storage Tracker ");
        builder.setContentText("The item " + nameEditText.getText().toString() +" will expire in one week");
        //builder.setContentInfo("The following item " + nameEditText.getText().toString() +" will expire in one week");






        builder.setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, builder.build());
        }
        private  void createNotificationChannel()
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                CharSequence name = getString(R.string.channel_name);
                String description = getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
            }
        }


}
