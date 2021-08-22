package com.example.corseworkapp;

/*reference
*
* Google, 2021. Get Started with Firebase Authentication on Android. [Online]
Available at: https://firebase.google.com/docs/auth/android/start
[Accessed 10 April 2021].

* This was also used to help create the registration page. The firebase google tutorials were also used to help create and connect the real time database
*
* */


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText UserEmail;
    private  EditText UserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        UserEmail = findViewById(R.id.Username);
        UserPassword = findViewById(R.id.Password);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            FirebaseAuth.getInstance().signOut();
        }
    }

    public void GetStrings()
    {
        String EmailLogin = UserEmail.getText().toString();
        String PasswordLogin = UserPassword.getText().toString();

        if(UserEmail.length() > 0 && UserPassword.length() >0) {
            mAuth.signInWithEmailAndPassword(EmailLogin, PasswordLogin).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        UserEmail.getText().clear();
                        UserPassword.getText().clear();
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent LoggedIn = new Intent(MainActivity.this, TodaysDrink.class);
                        startActivity(LoggedIn);
                    } else
                        {
                        Toast.makeText(MainActivity.this, "Incorrect Credentials.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(MainActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.RegisterButton:
                Intent SignUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(SignUp);
                break;

            case R.id.LoginButton:
                GetStrings();
            break;
        }
    }
}