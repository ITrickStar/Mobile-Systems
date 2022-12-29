package com.example.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch extends AppCompatActivity {
    int second = 0;
    int minute = 0;
    int hour = 0;
    boolean isStart = true;
    Timer Stopwatch = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        Button bStart = findViewById(R.id.Start);
        TextView tStopwatch = findViewById(R.id.Stopwatch);
        CustomAnalogClock clock = findViewById(R.id.analog_clock);
        clock.setTime(0);
        bStart.setOnClickListener(view -> {
            if (isStart) {
                bStart.setText("Stop");
                Stopwatch = new Timer();
                Stopwatch.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {
                            String time = (hour < 10) ? "0" + String.valueOf(hour) + ":" : String.valueOf(hour) + ":";
                            time += (minute < 10) ? "0" + String.valueOf(minute) + ":" : String.valueOf(minute) + ":";
                            time += (second < 10) ? "0" + String.valueOf(second) : String.valueOf(second);
                            second++;
                            hour = (hour + (minute + second / 60) / 60) % 24;
                            minute = (minute + second / 60) % 60;
                            second %= 60;
                            tStopwatch.setText(time);
                            clock.setTime(1000L *60*(second-1));
                        });
                    }
                }, 0L, 1000L);
                isStart = false;
            } else {
                Stopwatch.cancel();
                bStart.setText("Start");
                isStart = true;
            }
        });
        Button bPreviousPage = findViewById(R.id.PreviousPage);
        bPreviousPage.setOnClickListener(view -> {
            Intent intent = new Intent(Stopwatch.this, ButtonsCreator.class);
            startActivity(intent);
        });
        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(view -> {
            Intent intent = new Intent(Stopwatch.this, PageStack.class);
            startActivity(intent);
        });
    }
}
