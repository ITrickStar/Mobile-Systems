package com.example.lab6;

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

public class Test extends AppCompatActivity implements ColorPickerDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ArrayList<String> str = new ArrayList<>();
        str.add("Белый");
        str.add("Красный");
        str.add("Синий");
        ListView listView = (ListView) findViewById(R.id.listView1);
        ListAdapter2 listAdapter = new ListAdapter2(this, R.layout.list_items_test_activity, str);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            createColorPickerDialog(view.getId());
            listAdapter.notifyDataSetChanged();
        });

        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(view -> {
            Intent intent = new Intent(Test.this, ElementsList.class);
            startActivity(intent);
        });
        Button bPrevious = findViewById(R.id.PreviousPage);
        bPrevious.setOnClickListener(view -> {
            Intent intent = new Intent(Test.this, StartActivity.class);
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
        findViewById(dialogId).setBackgroundColor(color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {
    }

    private static class ListAdapter2 extends ArrayAdapter<String> {

        private final int resourceLayout;
        private final Context mContext;
        private final ArrayList<String> source;

        public ListAdapter2(Context context, int resource, ArrayList<String> str) {
            super(context, resource, str);
            this.resourceLayout = resource;
            this.mContext = context;
            this.source = str;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(mContext);
                v = vi.inflate(resourceLayout, null);
            }

            LinearLayout row = v.findViewById(R.id.Rectangle_1);
            row.setBackgroundColor(Color.WHITE);
            TextView item = v.findViewById(R.id.Rectangle1);
            item.setText(source.get(position));

            return v;
        }
    }
}

