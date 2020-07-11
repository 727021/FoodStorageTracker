package final_project.fstracker.foodstoragetracker;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.allyants.notifyme.NotifyMe;
import com.fstracker.foodstoragetracker.R;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This activity will addItems the food storage application it will create a notification a {@link FoodItem}.
 * the notification will be display if the expiration date is set up in one week the notification will pass the
 * food item so the user can be transfer to {@link ViewItemActivity}.
 */
public class AddItemActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText nameEditText;
    private EditText countText;
    private TextView textViewDate;
    public static String CHANNEL_ID = "1";
    public int year;
    public int month;
    public int day;

    /**
     * This will create all the elements on the AddItemActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        AppCompatDelegate.setDefaultNightMode((Settings.getSettings().darkMode) ?
            AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        //this will require data on the editText
        nameEditText = findViewById(R.id.editText);
        nameEditText.requestFocus();
        //this will require data on the editText3
        countText = findViewById(R.id.editText3);
        //this will require data on the date spinner
        textViewDate = findViewById(R.id.tvDate);

        // This will create the text View for the Expiration Date
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first we create the calendar with today date;
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                    AddItemActivity.this,
                    //we set the stile for the spinner
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    mDateSetListener,
                    year, month, day);
                    //now that we know the style we draw it and make it visible
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                final String[] format = Settings.getSettings().getDateFormat().toPattern()
                    .toLowerCase().split("/");
                char[] ymdOrder = { 'd', 'm', 'y' };
                int i = 0;
                for (String s : format) {
                    ymdOrder[i++] = s.charAt(0);
                }
                orderDate(dialog, ymdOrder);
            }
        });

        // Fill the Category spinner.
        Spinner spnSearchCategory2 = findViewById(R.id.spnSearchCategory2);
        List<Category> values = new ArrayList<>();
        for (Category cat : Category.values()) {
            if (cat != Category.ALL) // ALL shouldn't be a choice when adding an item
                values.add(cat);
        }
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchCategory2.setAdapter(adapter);

        // Fill the Unit spinner
        Spinner spnSearchFoodUnit = findViewById(R.id.spnSearchFoodUnit);
        ArrayAdapter<Unit> adapter2 = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, Unit.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchFoodUnit.setAdapter(adapter2);

        //this is used so the user can set the expiration date
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
                String date2 = (month2 + 1) + "/" + day2 + "/" + year2;

                // compare that the date is not expired
                if(date.compareTo(date2) < 0) {
                    textViewDate.requestFocus();
                    Log.d(TAG, "The day  compareTo: " + date);
                    Log.d(TAG, "The day2 compareTo: " + date2);
                    textViewDate.setError("This Product is Expired");
                } else if(date.equals(date2)) { // compare that the product does not expire today.
                    textViewDate.requestFocus();
                    Log.d(TAG, "The date equals: " + date);
                    Log.d(TAG, "The date equals: " + date2);
                    textViewDate.setError("This Product Will Expire today");
                } else {
                    textViewDate.requestFocus();
                    textViewDate.setError(null);
                    try {
                        date = Settings.getSettings().getDateFormat().
                            format(new SimpleDateFormat("MM/dd/yyyy").parse(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // here we set the date
                    textViewDate.setText(date);

                    //this is used to get ready the notification channel
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                         getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_warning_white_24dp)
                        .setContentTitle("The date equals: ")
                        .setContentText("The food item will expire in")
                        .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("The tv text equals:"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    Log.d(TAG, "The date1 equals: " + date);
                    Log.d(TAG, "The date2 equals: " + date2);
                }
            }
        };

        /*
         * this step will access that all data is filled
         * and make sure the food won't expire today
         */
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameEditText.getText().toString();
                final String count = countText.getText().toString();
                final String textv = textViewDate.getText().toString();
                Calendar c = Calendar.getInstance();
                 String date = Settings.getSettings().getDateFormat().format(c.getTime());
                Log.d(TAG, "The date equals: " + date);
                Log.d(TAG, "The tv text equals: " + textv);
                if(textv.equals(date)) {
                    textViewDate.requestFocus();
                    Log.d(TAG, "The date equals: " + date);
                    Log.d(TAG, "The date equals: " + textv);
                    textViewDate.setError("This Product Will Expire today");
                }
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
                    openAllItemActivity();
                }
            }
        });
    }

    /**
     * this will create the foodItem to be store and the notification that will remind the user.
     * if the product to store will expire in one week from creation
     * a notification will be created automatic, the instructor can test
     * in this way the notification, if the product expire in a later date
     * a notification will be set a week before the expiration date the product.
     */
    public void openAllItemActivity() {
        // Create a FoodItem
        FoodItem foodItem = new FoodItem();
        // set the name from the editText
        foodItem.setName(((TextView)findViewById(R.id.editText)).getText().toString());
        // set the category
        Category category = (Category)((Spinner)findViewById(R.id.spnSearchCategory2))
            .getSelectedItem();
        foodItem.setCategory(category);
        try {
            //set expiration date
            foodItem.setExpirationDate(Settings.getSettings().getDateFormat()
                .parse(((TextView)findViewById(R.id.tvDate)).getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //set the quantity
        foodItem.setQuantity(Double.valueOf(((TextView)findViewById(R.id.editText3)).getText()
            .toString()));
        //set the unit
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
        Toast.makeText(getApplicationContext(), String.format("Saved %s", foodItem),
            Toast.LENGTH_LONG).show();

        // set the notification
        Calendar notiCal = Calendar.getInstance();
        final String textv = textViewDate.getText().toString();
        int month2 = Integer.parseInt(textv.substring(0,2));
        int day2 = Integer.parseInt(textv.substring(3,5));
        int Year2 = Integer.parseInt(textv.substring(6,10));

        // this is so the teacher test  the notification  will call a week before expire.
        if (month2 == month && Year2 == year && day2 == day || day2 == day + 1
                || day2 == day +2 || day2 == day +3 || day2 == day +4
                || day2 == day +5 || day2 == day +6 || day2 == day +7) {
            notiCal.set(Calendar.YEAR, year);
            notiCal.set(Calendar.MONTH, month);
            notiCal.set(Calendar.DAY_OF_MONTH, day);
            int totalDay = day2 - day;
            //this log is to test that is working
            Log.d(TAG, "The notification date is " + textv);
            Log.d(TAG, "The notification date is " + month2);
            Log.d(TAG, "The notification date is " + day2);
            Log.d(TAG, "The notification date is " + Year2);
            // now we use the notification channel created and set it above
            createNotificationChannel();

            /*
             * now we create a pending intent so when the notification pops if the user click the notification
             * the user will be taken to the view list activity with the full information of the product that will expire.
             * note that this will work even if the app is closed.
             */
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder2 = new NotificationCompat.Builder(
                getApplicationContext(), AddItemActivity.CHANNEL_ID)
                // this will create the icon to be display on the notification, I created that layout.
                .setSmallIcon(R.drawable.ic_warning_white_24dp)
                // this will be the title of the notification
                .setContentTitle("Food Storage Tracker ")
                // this will be the text to appear on the notification
                .setContentText(String.format("%s will expire in %d day%s.", foodItem, totalDay,
                    (totalDay > 1) ? "s" : ""))
                // this will set the priority
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // this will make the notification disappear after the user click on it
                .setAutoCancel(true)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            int number  = 2;
            // this will build the notification
            notificationManager.notify(number, builder2.build());
        }

        /*
         * if the moth is not january but the product will expire on the first week
         * of the month then we need to set the notification to warn a month before a week before
         * the product will expire.
         */
        else if (day2 < 7 && month > 1) {
            day2 = 30 - day2;
            month2 = month2 -1;
        }

        /*
         * if the moth is january and the product will expire on the first week
         * of the month then we need to set the notification to warn a month before a week before
         * and a year prior the product will expire.
         */
        else if (day2 < 7 && month == 1) {
            day2 = 30 - day2;
            month2 = 12;
            Year2 = Year2 - 1;
        }

        notiCal.set(Calendar.YEAR, Year2);
        notiCal.set(Calendar.MONTH, month2);
        notiCal.set(Calendar.DAY_OF_MONTH, day2);

        Log.d(TAG, "The notification date is " + textv);
        Log.d(TAG, "The notification date is " + month2);
        Log.d(TAG, "The notification date is " + day2);
        Log.d(TAG, "The notification date is " + Year2);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
            intent, 0);
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
            .title("Food Storage Tracker ")
            .content(String.format("%s will expire in 1 month.", foodItem))
            .large_icon(R.drawable.ic_check_circle)
            .time(notiCal)
            .addAction(new Intent(), "Snooze", false)
            .key("test")
            .addAction(new Intent(), "Dismiss", true, false)
            .addAction(new Intent(), "Done")
            .small_icon(R.drawable.ic_warning_white_24dp)
            .build();
    }

    /**
     * This will create the notification channel.
     */
    private  void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * The number of Spinners in the DatePickerDialog.
     * Used by orderDate() and setImeOptions().
     * <p>
     * Taken from <a href="https://stackoverflow.com/questions/33624824/how-to-change-the-order-of-the-numberpickers-in-datepickerdialog">this StackOverflow question</a>.
     */
    private static final int SPINNER_COUNT = 3;

    /**
     * Used to reorder the spinners in a DatePickerDialog.
     * <p>
     * Taken from <a href="https://stackoverflow.com/questions/33624824/how-to-change-the-order-of-the-numberpickers-in-datepickerdialog">this StackOverflow question</a>.
     *
     * @param dialog The DatePickerDialog to set the order of.
     * @param ymdOrder A char array of length SPINNER_COUNT, consisting of 'd' 'm' and 'y'.
     */
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
//        layout.removeAllViews();
      /*  for (int i = 0; i < SPINNER_COUNT; i++) {
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
            }*/
        //}
    }

    /**
     * Taken from <a href="https://stackoverflow.com/questions/33624824/how-to-change-the-order-of-the-numberpickers-in-datepickerdialog">this StackOverflow question</a>.
     *
     * @param spinner The Spinner to set the ImeOptions of.
     * @param spinnerIndex The index of the spinner in the DatePickerDialog.
     */
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
