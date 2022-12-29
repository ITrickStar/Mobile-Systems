package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;


public class Foreground extends Application {
    public static final String CHANNEL_ID = "MyServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("Foreground", "creating notification");
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "MyService Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        } else {
            Log.d("Foreground", " not creating notification");
        }
    }
}