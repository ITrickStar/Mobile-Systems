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

import java.util.ArrayList;

public class ElementsList extends AppCompatActivity {

    private static int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elem);

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> str = new ArrayList<>();
        str.add("Элемент" + String.format(" %d", counter));
        ListAdapter listAdapter = new ListAdapter(this, R.layout.list_items_note_activity, str);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            str.remove(position);
            listAdapter.notifyDataSetChanged();
        });

        Button bAddItem = findViewById(R.id.AddItem);
        bAddItem.setOnClickListener(view -> {
            str.add("Элемент" + String.format(" %d", ++counter));
            listAdapter.notifyDataSetChanged();
        });

        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(view -> {
            Intent intent = new Intent(ElementsList.this, Http.class);
            startActivity(intent);
        });
        Button bPrevious = findViewById(R.id.PreviousPage);
        bPrevious.setOnClickListener(view -> {
            Intent intent = new Intent(ElementsList.this, StartActivity.class);
            startActivity(intent);
        });
    }

    private static class ListAdapter extends ArrayAdapter<String> {

        private final int resourceLayout;
        private final Context mContext;
        private final ArrayList<String> source;

        public ListAdapter(Context context, int resource, ArrayList<String> str) {
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

            LinearLayout row = v.findViewById(R.id.Rectangle);
            row.setBackgroundColor(Color.WHITE);
            TextView item = v.findViewById(R.id.Item);
            item.setText(source.get(position));

            return v;
        }
    }
}
