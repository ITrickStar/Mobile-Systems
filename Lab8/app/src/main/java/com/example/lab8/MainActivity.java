package com.example.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("lab8_counter");
    }

    public native static long createCounter();

    public native static int getCounter(long nativePointer);

    public native static long increaseCounter(long nativePointer);

    public native static long resetCounter(long nativePointer);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JCounter counter = new JCounter();
        TextView tCounter = findViewById(R.id.Counter);
        tCounter.setText(Integer.toString(counter.JGetCounter()));
        Button bIncrease = findViewById(R.id.Increase);
        Button bReset = findViewById(R.id.Reset);

        bIncrease.setOnClickListener(view -> {
            counter.JIncreaseCounter();
            tCounter.setText(Integer.toString(counter.JGetCounter()));
        });

        bReset.setOnClickListener(view -> {
            counter.JResetCounter();
            tCounter.setText(Integer.toString(counter.JGetCounter()));
        });

        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StringListActivity.class);
            startActivity(intent);
        });
    }

    public class JCounter {
        private long nativePointer;

        public JCounter() {
            nativePointer = createCounter();
        }

        public int JGetCounter() {
            return getCounter(nativePointer);
        }

        public void JIncreaseCounter() {
            nativePointer = increaseCounter(nativePointer);
        }

        public void JResetCounter() {
            nativePointer = resetCounter(nativePointer);
        }
    }

}
