package com.example.project;

import static android.icu.lang.UCharacter.toLowerCase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity {

    private ArrayList<String> steps;
    private int currentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String destination = getIntent().getStringExtra("destination");

        steps = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM STEPS WHERE LOCATION = ?", new String[]{destination});

        while (cursor.moveToNext()) {
            steps.add(cursor.getString(cursor.getColumnIndex("STEP")));
        }

        cursor.close();

        final TextView textView = findViewById(R.id.textView);
        final ImageView imageView = findViewById(R.id.imageView);
        Button prevButton = findViewById(R.id.prevButton);
        Button nextButton = findViewById(R.id.nextButton);
        Button homeButton = findViewById(R.id.homeButton);

        // Initialize to the first step
        currentStep = 0;
        updateView(imageView, textView, destination);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentStep > 0) {
                    currentStep--;
                    updateView(imageView, textView, destination);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (currentStep < steps.size() - 1) {
                    currentStep++;
                    updateView(imageView, textView, destination);
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    private void updateView(ImageView imageView, TextView textView, String destination) {
        textView.setText(String.format("Step %d: %s", currentStep + 1, steps.get(currentStep)));

        int resId = getResources().getIdentifier("step" + (currentStep + 1) + toLowerCase(destination), "drawable", getPackageName());
        Drawable stepImage = getResources().getDrawable(resId);
        imageView.setImageDrawable(stepImage);
    }
}