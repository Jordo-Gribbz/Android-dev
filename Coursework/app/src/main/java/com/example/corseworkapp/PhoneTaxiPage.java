package com.example.corseworkapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PhoneTaxiPage extends AppCompatActivity {

    private static final int CallPermission = 1;
    private EditText PhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_taxi_page);

        PhoneNum = findViewById(R.id.CustomNumber);
        ImageView CallButton = findViewById((R.id.Callcab));

       CallButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PhoneCall();
            }
        });
    }

    public void PhoneCall()
    {
        String callnumber = PhoneNum.getText().toString();
        if (callnumber.trim().length() > 0)
        {
            if(ContextCompat.checkSelfPermission(PhoneTaxiPage.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(PhoneTaxiPage.this, new String[] {Manifest.permission.CALL_PHONE}, CallPermission);
            }
            else
            {
                String calling = "tel:" + callnumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(calling)));
            }
        }
        else
        {
            Toast.makeText(PhoneTaxiPage.this, "Please enter a number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CallPermission)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                PhoneCall();
            }
            else
            {
                Toast.makeText(this, "You must grant permissions to call", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Call(View view)
    {
        switch (view.getId()) {

            case R.id.DundeePic :
            case R.id.Dundee :

                Intent CallD = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +1292500344));
                startActivity(CallD);

                break;

            case R.id.GlasgowPic:
            case R.id.Glasgow:

                Intent CallG = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +1414297070));
                startActivity(CallG);

                break;
        }
    }

    public void ReturnHome(View view)
    {
        finish();
    }
}