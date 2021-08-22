package com.example.corseworkapp;

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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText UserEmail;
    private  EditText UserPassword;
    private EditText Username;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
    }


    public void RegisterAccount(View view)
    {
        UserEmail = findViewById(R.id.EmailSignUp);
        String EmailToRegister = UserEmail.getText().toString();
        UserPassword = findViewById(R.id.PasswordSignUp);
        String PasswordToRegister = UserPassword.getText().toString();
        Username = findViewById(R.id.UsernameSignUp);
        String UsernameToRegister = Username.getText().toString();


        if(EmailToRegister.length() > 0  && PasswordToRegister.length() >0 && UsernameToRegister.length() >0)
        {
            mAuth.createUserWithEmailAndPassword(EmailToRegister, PasswordToRegister)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                              SavetoDatabase();
                            }
                            else
                            {
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Toast.makeText(SignUp.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    });
        }
        else
        {
            Toast.makeText(SignUp.this, "Please enter a Username, email and password", Toast.LENGTH_SHORT).show();
        }

    }

    public void SavetoDatabase ()
    {
            if (mAuth.getCurrentUser() != null) {
            Username = findViewById(R.id.UsernameSignUp);
            String UsernameToRegister = Username.getText().toString();
            UserEmail = findViewById(R.id.EmailSignUp);
            String EmailToRegister = UserEmail.getText().toString();

           // Toast.makeText(SignUp.this, "In save to database", Toast.LENGTH_SHORT).show(); Used for testing

            User UserD = new User(UsernameToRegister, EmailToRegister);
            FirebaseDatabase.getInstance("https://corseworkapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(UserD).addOnCompleteListener(new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //Toast.makeText(SignUp.this, "In Listener", Toast.LENGTH_SHORT).show(); Used in testing
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, "User created but failed to save", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}