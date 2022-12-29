package com.example.tomatoproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFY_ID = 101;
    private static final String CHANNEL_ID = "Timer";

    private TextView mTextWork;
    private TextView mTextBreak;
    private TextView mTextCountDown;
    private TextView mTextStatus;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;
    private ProgressBar mProgressBar;

    private boolean mTimerRunning;
    private boolean isWorking;

    private long mBreakTimeInMillis;
    private long mWorkTimeInMillis;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextWork = findViewById(R.id.text_view_work);
        mTextBreak = findViewById(R.id.text_view_break);
        mTextCountDown = findViewById(R.id.text_view_countdown);
        mTextStatus = findViewById(R.id.text_view_status);
        mProgressBar = findViewById(R.id.circular_progress_bar);

        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mTextWork.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(MainActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                String hour1 = Integer.toString(selectedHour);
                String minute1 = Integer.toString(selectedMinute);
                if (selectedHour < 10) hour1 = "0" + selectedHour;
                if (selectedMinute < 10) minute1 = "0" + selectedMinute;

                mTextWork.setText(hour1 + ":" + minute1);
            }, hour, minute, true);

            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        mTextBreak.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(MainActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                String hour12 = Integer.toString(selectedHour);
                String minute12 = Integer.toString(selectedMinute);
                if (selectedHour < 10) hour12 = "0" + selectedHour;
                if (selectedMinute < 10) minute12 = "0" + selectedMinute;

                mTextBreak.setText(hour12 + ":" + minute12);
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });


        mButtonSet.setOnClickListener(v -> {
            String inputW = mTextWork.getText().toString();
            String inputB = mTextBreak.getText().toString();

            if (inputW.length() == 0 || inputB.length() == 0) {
                Toast.makeText(MainActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            int quoteInd = inputW.indexOf(":");
            int hours = Integer.parseInt(inputW.substring(0, quoteInd));
            int minutes = Integer.parseInt(inputW.substring(++quoteInd));

            mWorkTimeInMillis = (hours * 60L + minutes) * 60000;
            if (mWorkTimeInMillis == 0) {
                Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                return;
            }

            quoteInd = inputW.indexOf(":");
            hours = Integer.parseInt(inputB.substring(0, quoteInd));
            minutes = Integer.parseInt(inputB.substring(++quoteInd));

            mBreakTimeInMillis = (hours * 60L + minutes) * 60000;
            if (mBreakTimeInMillis == 0) {
                Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                return;
            }

            isWorking = true;
            mTextStatus.setText("Work Time!");
            setTime(mWorkTimeInMillis);
        });

        mButtonStartPause.setOnClickListener(v -> {
            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        mButtonReset.setOnClickListener(v -> resetTimer());
    }

    public void Forw(View view) {
        startActivity(new Intent(this, TaskActivity.class));
    }

    private void setTime(long millis) {
        mStartTimeInMillis = millis;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                int mStartTimeInSeconds = (int) (mStartTimeInMillis / 1000);
                int progressPercentage = secondsRemaining * 100 / mStartTimeInSeconds;
                mProgressBar.setProgress(progressPercentage);
            }

            @Override
            public void onFinish() {
                createNotificationChannel();
                if (isWorking) {
                    notificationBreak();
                    setTime(mBreakTimeInMillis);
                    mTextStatus.setText("Break Time!");
                    isWorking = false;
                } else {
                    notificationWork();
                    setTime(mWorkTimeInMillis);
                    mTextStatus.setText("Work Time!");
                    isWorking = true;
                }

                startTimer();
                mProgressBar.setProgress(100);
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        mProgressBar.setProgress(100);
        updateCountDownText();
        updateWatchInterface();
    }

    @NonNull
    private String convertToString(long input) {
        int hours = (int) (input / 1000) / 3600;
        int minutes = (int) ((input / 1000) % 3600) / 60;
        int seconds = (int) (input / 1000) % 60;

        String timeFormatted;
        if (hours > 0) {
            timeFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        return timeFormatted;
    }

    private void updateCountDownText() {
        String timeLeft = convertToString(mTimeLeftInMillis);
        mTextCountDown.setText(timeLeft);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mTextWork.setVisibility(View.INVISIBLE);
            mTextBreak.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mTextWork.setVisibility(View.VISIBLE);
            mTextBreak.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonReset.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonReset.setVisibility(View.INVISIBLE);
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonReset.setVisibility(View.VISIBLE);
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }

    private void notificationWork() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Напоминание")
                        .setContentText("Пора приступать к работе")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    private void notificationBreak() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Напоминание")
                        .setContentText("Время сделать перерыв")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "notify";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}