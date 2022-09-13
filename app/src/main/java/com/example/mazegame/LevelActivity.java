package com.example.mazegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelActivity extends AppCompatActivity {

    private Button levelButton1;
    private Button levelButton2;
    private Button levelButton3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        levelButton1 = findViewById(R.id.levelButton1);
        levelButton2 = findViewById(R.id.levelButton2);
        levelButton3 = findViewById(R.id.levelButton3);

        levelButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);

            }
        });

       levelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);

            }
        });

        levelButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);

            }
        });
    }
}