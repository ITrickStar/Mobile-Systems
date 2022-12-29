package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int count;
    private Button next_btn1;
    private Button next_btn;
    private Button prev_btn1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        next_btn1 = findViewById(R.id.forw_btn);
        next_btn = findViewById(R.id.next_btn1);
        prev_btn1 = findViewById(R.id.backward_btn);
        TextView txt_view = findViewById(R.id.txt_view);
        intent = new Intent(MainActivity.this, MainActivity.class);
        count = getIntent().getIntExtra("count", 0);
        txt_view.setText("Глубина стека: " + count);

        setListenerOnButton();
    }

    public void setListenerOnButton() {
        next_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                intent.putExtra("count", count);
                startActivity(intent);
            }
        });
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    Intent intent_next = new Intent(MainActivity.this, PageActivity.class);
                    startActivity(intent_next);
                } else {
                    Toast.makeText(getApplicationContext(), "Очистите предыдущие страницы",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        prev_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count != 0) {
                    count--;
                    intent.putExtra("count", count);
                    startActivity(intent);
                }
            }
        });
    }
}
