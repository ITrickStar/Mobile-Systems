package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

    public static int counter = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase("ADD")) {
            counter++;

            Log.d("Foreground", "Added" + String.valueOf(counter));
        } else {
            counter--;
            if (counter <= 0)
                counter = 0;
            Log.d("Foreground", "Sub" + String.valueOf(counter));
        }
        MyService.UpdateCounter(counter);
    }
}