package com.example.corseworkapp;

/*Reference
*
* Material Design, 2021. Bottom navigation. [Online]
Available at: https://material.io/components/bottom-navigation#usage
[Accessed 20 April 2021].

*
* */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth mAuth;
    TextView UsernameDisplay;
    String Drink;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mAuth = FirebaseAuth.getInstance();
        UsernameDisplay =  findViewById(R.id.UserDisplay);
        editText= findViewById(R.id.SearchFor);

        BottomNavigationView NavBot = findViewById(R.id.NavigationBar);

        Drink = getIntent().getStringExtra("DrinkOfChoice");

        Menu Menu = NavBot.getMenu();
        MenuItem MenuItems = Menu.getItem(0);
        MenuItems.setChecked(true);

        GetName();

        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.Home_Icon:
                        break;

                    case R.id.Map_Icon:
                        overridePendingTransition(0, 0);
                        Intent MapsIntent= new Intent(HomeScreen.this, MapsActivity.class);
                        startActivity(MapsIntent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Calander_Icon:
                        overridePendingTransition(0, 0);
                        Intent CalanderIntent= new Intent(HomeScreen.this, Callander.class);
                        startActivity(CalanderIntent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Timer_Icon:
                        overridePendingTransition(0, 0);
                        Intent TimerIntent= new Intent(HomeScreen.this, timer.class);
                        startActivity(TimerIntent);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });
    }

    private void GetName()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://corseworkapp-default-rtdb.europe-west1.firebasedatabase.app/");
        String ID = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = database.getReference("Users").child(ID).child("Dusername");
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String username = dataSnapshot.getValue().toString();
                UsernameDisplay.setText("Welcome " + username.toString() + ". Today you are drinking: " + Drink);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ToCocktails:
                Intent CocktailIntent = new Intent(this, CocktailPage.class);
                startActivity(CocktailIntent);
                break;

            case R.id.ToTaxis:
                Intent TaxiIntent = new Intent(this, PhoneTaxiPage.class);
                startActivity(TaxiIntent);
                break;

            case R.id.Search:
                String BarInput = editText.getText().toString();
                if(BarInput.length() >0) {
                    Intent SearchBar = new Intent(HomeScreen.this, Internet.class);
                    SearchBar.putExtra("BarName", BarInput);
                    startActivity(SearchBar);
                    break;
                }
                else
                {
                    Toast.makeText(HomeScreen.this,"Please enter a bar name", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

    public void ClickLogout(View view)
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            FirebaseAuth.getInstance().signOut();
        }
        finish();
    }
}