package com.example.lab6;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

public class StartActivity extends AppCompatActivity implements ColorPickerDialogListener {
    private LinearLayout rect_1;
    private LinearLayout rect_2;
    private LinearLayout rect_3;
    private TextView rect1;
    private TextView rect2;
    private TextView rect3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        rect_1 = findViewById(R.id.Rectangle_1);
        rect_1.setOnClickListener(view -> createColorPickerDialog(view.getId()));
        rect_2 = findViewById(R.id.Rectangle_2);
        rect_2.setOnClickListener(view -> createColorPickerDialog(view.getId()));
        rect_3 = findViewById(R.id.Rectangle_3);
        rect_3.setOnClickListener(view -> createColorPickerDialog(view.getId()));
        rect1 = findViewById(R.id.Rectangle1);
        rect1.setOnClickListener(view -> createColorPickerDialog(view.getId()));
        rect2 = findViewById(R.id.Rectangle2);
        rect2.setOnClickListener(view -> createColorPickerDialog(view.getId()));
        rect3 = findViewById(R.id.Rectangle3);
        rect3.setOnClickListener(view -> createColorPickerDialog(view.getId()));

        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, Test.class);
            startActivity(intent);
        });
    }

    private void createColorPickerDialog(int id) {
        ColorPickerDialog.newBuilder()
                .setColor(Color.RED)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowCustom(true)
                .setAllowPresets(true)
                .setColorShape(ColorShape.CIRCLE)
                .setDialogId(id)
                .show(this);
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case R.id.Rectangle_1:
                rect_1.setBackgroundColor(color);
                rect1.setText(String.format("%d", color));
                break;
            case R.id.Rectangle_2:
                rect_2.setBackgroundColor(color);
                rect2.setText(String.format("%d", color));
                break;
            case R.id.Rectangle_3:
                rect_3.setBackgroundColor(color);
                rect3.setText(String.format("%d", color));
                break;
            case R.id.Rectangle1:
                rect1.setTextColor(color);
                break;
            case R.id.Rectangle2:
                rect2.setTextColor(color);
                break;
            case R.id.Rectangle3:
                rect3.setTextColor(color);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dialogId);
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {
    }
}
