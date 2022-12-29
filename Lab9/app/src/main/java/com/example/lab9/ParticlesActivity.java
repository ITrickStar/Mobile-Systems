package com.example.lab9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tutorials.android.particles.ParticlesGenerator;
import com.tutorials.android.particles.ParticlesManager;
import com.tutorials.android.particles.ParticlesSource;
import com.tutorials.android.particles.Utils;
import com.tutorials.android.particles.particles.BitmapParticles;
import com.tutorials.android.particles.particles.Particles;

import java.util.Random;

public class ParticlesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particles_on_touch);

        ViewGroup main = findViewById(R.id.ParticlesOnTouch);

        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        final Bitmap allPossibleParticles = Utils.createCircleBitmap(Color.WHITE, 20);
                        final ParticlesGenerator particlesGenerator = new ParticlesGenerator() {
                            @Override
                            public Particles generateParticles(Random random) {
                                final Bitmap bitmap = allPossibleParticles;
                                return new BitmapParticles(bitmap);
                            }
                        };
                        final int containerMiddleX = x;
                        final int containerMiddleY = y;
                        final ParticlesSource particlesSource = new ParticlesSource(containerMiddleX, containerMiddleY);
                        float length = (float) Math.sqrt(180 * 180 + 180 * 180);
                        for (int i = 0; i < 360; i++) {
                            new ParticlesManager(ParticlesActivity.this, particlesGenerator, particlesSource, main)
                                    .setNumInitialCount(1)
                                    .setVelocityX((float) (length * Math.cos(i)))
                                    .setVelocityY((float) (length * Math.sin(i)))
                                    .animate();
                        }
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });

        Button bPreviousPage = findViewById(R.id.PreviousPage);
        bPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParticlesActivity.this, SpriteActivity.class);
                startActivity(intent);
            }
        });

        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParticlesActivity.this, FountainActivity.class);
                startActivity(intent);
            }
        });
    }
}