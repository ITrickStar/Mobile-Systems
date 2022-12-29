package com.example.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TrafficLight extends AppCompatActivity {
    int light_id = 0;
    boolean isMoving = false;
    boolean flagLight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traffic_lights);

        TextView redLight = findViewById(R.id.RedLight);
        TextView yellowLight = findViewById(R.id.YellowLight);
        TextView greenLight = findViewById(R.id.GreenLight);
        ImageView Human = findViewById(R.id.Human);

        TextView[] lights = {redLight, yellowLight, greenLight};
        int[] lightsSource = {R.drawable.red_light, R.drawable.yellow_light, R.drawable.green_light};
        Thread thread = new Thread(() -> {
            while (true) {
                if (light_id == 2) {
                    if (isMoving) {
                        Animation forwardAnim = AnimationUtils.loadAnimation(this, R.anim.human_go_forward);
                        Human.startAnimation(forwardAnim);
                    } else {
                        Animation backwardAnim = AnimationUtils.loadAnimation(this, R.anim.human_go_backward);
                        Human.startAnimation(backwardAnim);
                    }
                    flagLight = false;
                } else if (light_id == 0) {
                    flagLight = true;
                }
                lights[light_id].setBackgroundResource(lightsSource[light_id]);
                if (light_id == 0) {
                    if (isMoving) {
                        Human.setRotationY(180);
                        isMoving = false;
                    } else {
                        Human.setRotationY(0);
                        isMoving = true;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lights[light_id].setBackgroundResource(R.drawable.black_circle);

                if (flagLight)
                    light_id++;
                else light_id = 0;
            }
        });
        thread.start();


        Button bNextPage = findViewById(R.id.NextPage);
        bNextPage.setOnClickListener(view -> {
            Intent intent = new Intent(TrafficLight.this, TextMotion.class);
            startActivity(intent);
        });
    }
}
