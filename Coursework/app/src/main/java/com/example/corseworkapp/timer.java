package com.example.corseworkapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class timer extends AppCompatActivity {

    private Chronometer DrinkingTimer;
    private boolean TimerStatus = false;
    private long pause = 0;
    long SafeTimeP = 99999999;
    Boolean CanSave = false;
    TextView BestTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        DrinkingTimer = findViewById((R.id.DrinksTimer));
        BestTime = findViewById(R.id.Besttime);
        BottomNavigationView NavBot = findViewById(R.id.NavigationBar);

        Menu Menu = NavBot.getMenu();
        MenuItem MenuItems = Menu.getItem(3);
        MenuItems.setChecked(true);


        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home_Icon:
                        overridePendingTransition(0, 0);
                        //Intent HomeIntent = new Intent(timer.this, HomeScreen.class);
                        finish();
                        //startActivity(HomeIntent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Map_Icon:
                        overridePendingTransition(0, 0);
                        Intent MapsIntent= new Intent(timer.this, MapsActivity.class);
                        finish();
                        startActivity(MapsIntent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Calander_Icon:
                        overridePendingTransition(0, 0);
                        Intent CalanderIntent = new Intent(timer.this, Callander.class);
                        finish();
                        startActivity(CalanderIntent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Timer_Icon:
                        break;
                }
                return false;
            }
        });

    }

    public void StartTime(View view)
    {
        if (TimerStatus == false)
        {
            TimerStatus = true;
            DrinkingTimer.setBase(SystemClock.elapsedRealtime() - pause); //fixes resume time
            DrinkingTimer.start();
        }
    }

    public void StopTime(View view) {
        if (TimerStatus == true) {
            TimerStatus = false;
            CanSave = true;
            DrinkingTimer.stop();
            pause = SystemClock.elapsedRealtime() - DrinkingTimer.getBase(); // fixes time difference

            long SaveTime = (SystemClock.elapsedRealtime() - DrinkingTimer.getBase());
            SaveTime = SaveTime /1000;
            if(CanSave == false)
            {
                SafeTimeP = SaveTime;
                CanSave = true;
            }
            if (SaveTime <= SafeTimeP) {
                SafeTimeP = SaveTime;
                BestTime.setText(String.valueOf(SaveTime));
            }
        }
    }

    public void RestartTime(View view) {
        pause = 0;
        DrinkingTimer.setBase(SystemClock.elapsedRealtime());
    }
}