package com.example.lab7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TextMotion extends AppCompatActivity {
    @SuppressLint({"ClickableViewAccessibility", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_motion);
        TextView txt = findViewById(R.id.textView);
        txt.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    txt.startAnimation(AnimationUtils.loadAnimation(TextMotion.this, R.anim.text_descend));
                    txt.setTextColor(Color.parseColor(getString(R.color.blue)));
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    txt.startAnimation(AnimationUtils.loadAnimation(TextMotion.this, R.anim.text_ascend));
                    txt.setTextColor(Color.parseColor(getString(R.color.black)));
                    break;
                }
            }
            return true;
        });

        Button bPreviousPage = findViewById(R.id.PreviousPage);
        bPreviousPage.setOnClickListener(view -> {
            Intent intent = new Intent(TextMotion.this, TrafficLight.class);
            startActivity(intent);
        });
        Button bNextPage = findViewById(R.id.NextPage);
        bNextPage.setOnClickListener(view -> {
            Intent intent = new Intent(TextMotion.this, ButtonsCreator.class);
            startActivity(intent);
        });
    }
}
