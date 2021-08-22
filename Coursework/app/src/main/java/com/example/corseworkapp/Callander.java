package com.example.corseworkapp;

/* Tutorials point, 2021. Android - Shared Preferences. [Online]
Available at: https://www.tutorialspoint.com/android/android_shared_preferences.htm
[Accessed 15 April 2021].
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Callander extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String SHARED_PREFS = "SharredPrefrences";
    public static final String TEXT = "Text";
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callander);

        BottomNavigationView NavBot = findViewById(R.id.NavigationBar);

        Menu Menu = NavBot.getMenu();
        MenuItem MenuItems = Menu.getItem(2);
        MenuItems.setChecked(true);

        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.Home_Icon:
                        overridePendingTransition(0, 0);
                        SaveDate();
                        //Intent HomeIntent= new Intent(Callander.this, HomeScreen.class);
                        finish();
                        //startActivity(HomeIntent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Map_Icon:
                        overridePendingTransition(0, 0);
                        SaveDate();
                        Intent MapsIntent= new Intent(Callander.this, MapsActivity.class);
                        finish();
                        startActivity(MapsIntent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Calander_Icon:
                        break;

                    case R.id.Timer_Icon:
                        overridePendingTransition(0, 0);
                        SaveDate();
                        Intent TimerIntent= new Intent(Callander.this, timer.class);
                        finish();
                        startActivity(TimerIntent);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });

       LoadDate();
       UpdateSharedPref();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);
        String ChosenDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView DisplayDate = findViewById(R.id.CalendarTextView);
        DisplayDate.setText(ChosenDate);
    }

   public void LoadDate()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "When are you drinking?");
    }

    public void UpdateSharedPref()
    {
        TextView DisplayDate =  findViewById(R.id.CalendarTextView);
        DisplayDate.setText(text);
    }

    public void onClick(View view)
    {
        DialogFragment PickDate = new DateSelect();
        PickDate.show(getSupportFragmentManager(), "Pick Date");
    }

    public void SaveDate()
    {
        TextView DisplayDate = findViewById(R.id.CalendarTextView);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, DisplayDate.getText().toString());
        editor.commit();
    }
}