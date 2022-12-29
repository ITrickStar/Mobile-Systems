package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TextDialog extends AppCompatActivity{
    private int score = 0;
    final Context context1 = this;
    public final String ACTION_PLUS = "action_plus";
    public final String ACTION_MINUS = "action_minus";
    public final String SCORE = "score";
    public final String BROADCAST_ACTION = "com.example.lab5_tryservicebackbroadcast";
//    public final String ACTION_MINUS = "action_minus";
    private Button button, next_btn;
    private TextView final_text;
    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_dialog);
        button = findViewById(R.id.prompt_button);
        next_btn = findViewById(R.id.next_btn3);
        final_text = (TextView) findViewById(R.id.final_text);

        IntentFilter filt = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, filt);

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int op1 = intent.getIntExtra(ACTION_PLUS, 0);
                int op2 = intent.getIntExtra(ACTION_PLUS, 0);
                switch (op1) {
                    case 0:
                        if (score > 0) {
                            score--;
                        }
                        break;
                    case 1:
                        if (score < 100) {
                            score++;
                        }
                        break;
                }
            }
        };

        setListenerOnButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }

    public void setListenerOnButton() {
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_next = new Intent(TextDialog.this, Date_Time.class);
                startActivity(intent_next);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context1);
                View promptsView = li.inflate(R.layout.text, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context1);
                mDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        final_text.setText(userInput.getText().toString());
                                    }
                                })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
    public void setDate(View v) {}
}