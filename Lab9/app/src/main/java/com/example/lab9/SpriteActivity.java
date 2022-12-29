package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SpriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite);

        Button bPreviousPage = findViewById(R.id.PreviousPage);
        bPreviousPage.setOnClickListener(view -> {
            Intent intent = new Intent(SpriteActivity.this, MainActivity.class);
            startActivity(intent);
        });
        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(view -> {
            SurfaceView smt = findViewById(R.id.Smt);
            smt.onFinishTemporaryDetach();
            Intent intent = new Intent(SpriteActivity.this, ParticlesActivity.class);
            startActivity(intent);

        });
    }
}