package com.example.lab5;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static androidx.core.app.NotificationCompat.PRIORITY_MIN;
import static com.example.lab5.Foreground.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class MyService extends Service {

    public static NotificationCompat.Builder builder;
    public static Notification notification;
    private static NotificationManager mNotificationManager;

    public static void UpdateCounter(int counter) {
        notification = builder.setContentText(String.valueOf(counter)).build();
        mNotificationManager.notify(1, notification);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Foreground", "starting service");


        Intent addIntent = new Intent(this, NotificationReceiver.class);
        addIntent.setAction("ADD");
        PendingIntent addPendingIntent = PendingIntent.getBroadcast(this, 0, addIntent, FLAG_IMMUTABLE);


        Intent subIntent = new Intent(this, NotificationReceiver.class);
        subIntent.setAction("SUB");
        PendingIntent subPendingIntent = PendingIntent.getBroadcast(this, 1, subIntent, FLAG_IMMUTABLE);


        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setOngoing(true)
                .setPriority(PRIORITY_MIN)
                .setContentTitle("Counter")
                .setContentText(String.valueOf(0))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .addAction(R.drawable.ic_launcher_foreground, "+", addPendingIntent)
                .addAction(R.drawable.ic_launcher_foreground, "-", subPendingIntent);

        notification = builder.build();

        startForeground(1, notification);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}