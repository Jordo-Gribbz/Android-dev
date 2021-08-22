package com.example.corseworkapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TodaysDrink extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    TextView textView;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_drink);

        mAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.TodaysDrink);
        input = findViewById(R.id.TodaysDrinkInput);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://corseworkapp-default-rtdb.europe-west1.firebasedatabase.app/");
        String ID = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = database.getReference("Users").child(ID).child("Dusername");
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String username = dataSnapshot.getValue().toString();
                textView.setText(username.toString() + " what drink are you having tonight?");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    public void onClick(View view)
    {
        String DrinkInput = input.getText().toString();

        if(DrinkInput.length() > 0)
        {
            Intent ToHome = new Intent(TodaysDrink.this, HomeScreen.class);
            ToHome.putExtra("DrinkOfChoice", DrinkInput);
            finish();
            startActivity(ToHome);
        }
        else
        {
            Toast.makeText(TodaysDrink.this, "Please enter a drink", Toast.LENGTH_SHORT).show();
        }

    }
}