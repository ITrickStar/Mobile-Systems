package com.example.lab6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class CheckBox extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        android.widget.CheckBox checkBox = findViewById(R.id.checkBox);
        final boolean[] checked = {PreferenceManager.getDefaultSharedPreferences(this).getBoolean("checkBox", false)};
        checkBox.setChecked(checked[0]);
        checkBox.setOnClickListener(view -> {
            checked[0] = checkBox.isChecked();
            PreferenceManager.getDefaultSharedPreferences(CheckBox.this).edit().putBoolean("checkBox", checked[0]).apply();
        });

        EditText editText = findViewById(R.id.SaveNote);
        final String[] text = {PreferenceManager.getDefaultSharedPreferences(this).getString("editText", "")};
        editText.setText(text[0]);
        editText.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            text[0] = String.valueOf(editText.getText());
            PreferenceManager.getDefaultSharedPreferences(CheckBox.this).edit().putString("editText", text[0]).apply();
        });

        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(view -> {
            Intent intent = new Intent(CheckBox.this, null);
            startActivity(intent);
        });
        Button bPrevious = findViewById(R.id.PreviousPage);
        bPrevious.setOnClickListener(view -> {
            Intent intent = new Intent(CheckBox.this, NoteList.class);
            startActivity(intent);
        });
    }
}
