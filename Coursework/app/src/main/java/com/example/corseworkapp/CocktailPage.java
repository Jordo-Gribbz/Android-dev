package com.example.corseworkapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CocktailPage extends AppCompatActivity
{

    LinearLayout ExpandedViewSOTB;
    TextView TextViewSOTB;
    CardView CardViewSOTB;
    LinearLayout ExpandedViewScrew;
    TextView TextViewScrew;
    CardView CardViewScrew;

    private static final String NameOfFile = "Cocktail.txt";
    private static final String NameOfBodyFile = "CocktailBody.txt";
    EditText editText;
    TextView header;
    TextView body;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_page);

        ExpandedViewSOTB = findViewById(R.id.ExpandedView);
        TextViewSOTB = findViewById(R.id.ShowText);
        CardViewSOTB = findViewById(R.id.expandable_cardview);

        ExpandedViewScrew = findViewById(R.id.ExpandedViewScrewdriver);
        TextViewScrew = findViewById(R.id.ShowTextScrewdriver);
        CardViewScrew = findViewById(R.id.expandable_cardview_screwdriver);

        editText = findViewById(R.id.EditCocktail);
        header =  findViewById(R.id.CocktailHeader);
        body = findViewById(R.id.CocktailBody);
    }

    public void expand(View view)
    {
        if(ExpandedViewSOTB.getVisibility()==View.GONE)
        {
            TextViewSOTB.setText("Show less");
            TransitionManager.beginDelayedTransition(CardViewSOTB,new AutoTransition());
            ExpandedViewSOTB.setVisibility(View.VISIBLE);
        }
        else
        {
            TextViewSOTB.setText("Read more");
            TransitionManager.beginDelayedTransition(CardViewSOTB,new AutoTransition());
            ExpandedViewSOTB.setVisibility(View.GONE);
        }
    }

    public void ExpandScrewdriver(View view)
    {
        if(ExpandedViewScrew.getVisibility()==View.GONE)
        {
            TextViewScrew.setText("Show less");
            TransitionManager.beginDelayedTransition(CardViewScrew,new AutoTransition());
            ExpandedViewScrew.setVisibility(View.VISIBLE);
        }
        else
        {
            TextViewScrew.setText("Read more");
            TransitionManager.beginDelayedTransition(CardViewScrew,new AutoTransition());
            ExpandedViewScrew.setVisibility(View.GONE);
        }
    }

    public void save (View v)
    {
        String text = editText.getText().toString();
        FileOutputStream FileOutput = null;
        if(v.getId() == R.id.SaveCocktail) {
            try {
                FileOutput = openFileOutput(NameOfFile, MODE_PRIVATE);
                FileOutput.write(text.getBytes());

                editText.getText().clear();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (FileOutput != null) {
                    try {
                        FileOutput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            try {
                FileOutput = openFileOutput(NameOfBodyFile, MODE_PRIVATE);
                FileOutput.write(text.getBytes());

                editText.getText().clear();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (FileOutput != null) {
                    try {
                        FileOutput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void load (View v) {
        if (v.getId() == R.id.LoadCocktail)
        {
            FileInputStream FileInput = null;
            try {
                String text;
                StringBuilder BuildString = new StringBuilder();
                FileInput = openFileInput((NameOfFile));
                InputStreamReader StreamRead = new InputStreamReader(FileInput);
                BufferedReader BuffRead = new BufferedReader(StreamRead);

                while ((text = BuffRead.readLine()) != null) {
                    BuildString.append(text).append("\n");
                }

                header.setText(BuildString.toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (FileInput != null) {
                    try {
                        FileInput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            FileInputStream FileInput = null;
            try {
                String text;
                StringBuilder BuildString = new StringBuilder();
                FileInput = openFileInput((NameOfBodyFile));
                InputStreamReader StreamRead = new InputStreamReader(FileInput);
                BufferedReader BuffRead = new BufferedReader(StreamRead);

                while ((text = BuffRead.readLine()) != null) {
                    BuildString.append(text).append("\n");
                }

                body.setText(BuildString.toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (FileInput != null) {
                    try {
                        FileInput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void ReturnHome(View view)
    {
        finish();
    }
}