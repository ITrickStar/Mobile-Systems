package com.example.lab7;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import java.util.ArrayList;

public class ButtonsCreator extends AppCompatActivity implements ColorPickerDialogListener {
    private static Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons_creator);

        ListView listView = findViewById(R.id.listView);
        LinearLayout linearLayout = findViewById(R.id.ButtonsStack);
        ArrayList<String> list = new ArrayList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");
        ListAdapter adapter = new ListAdapter(this, R.layout.list_items_buttons_creator, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            myButton = new Button(ButtonsCreator.this);
            myButton.setText(list.get(position));
            createColorPickerDialog(view.getId());
            linearLayout.addView(myButton);
        });

        Button bPreviousPage = findViewById(R.id.PreviousPage);
        bPreviousPage.setOnClickListener(view -> {
            Intent intent = new Intent(ButtonsCreator.this, TextMotion.class);
            startActivity(intent);
        });
        Button bNextPage = findViewById(R.id.NextPage);
        bNextPage.setOnClickListener(view -> {
            Intent intent = new Intent(ButtonsCreator.this, Stopwatch.class);
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
        myButton.setBackgroundColor(color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {
    }

    public static class ListAdapter extends ArrayAdapter<String> {
        private final int resourceLayout;
        private final Context mContext;
        private final ArrayList<String> source;

        public ListAdapter(Context context, int resource, ArrayList<String> Notes) {
            super(context, resource, Notes);
            this.resourceLayout = resource;
            this.mContext = context;
            this.source = Notes;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(mContext);
                v = vi.inflate(resourceLayout, null);
            }
            TextView item = v.findViewById(R.id.Item);
            item.setText(source.get(position));

            return v;
        }
    }
}
