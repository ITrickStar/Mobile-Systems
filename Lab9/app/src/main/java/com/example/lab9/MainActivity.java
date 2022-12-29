package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bNextPage = findViewById(R.id.NextPage);
        bNextPage.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SpriteActivity.class);
            startActivity(intent);
        });
    }
}