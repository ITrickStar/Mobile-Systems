package com.example.lab8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StringListActivity extends AppCompatActivity {

    static {
        System.loadLibrary("lab8_list_array");
    }

    public JStringList list = new JStringList();

    public native static long createStringList();

    public native static long pushStringList(String input, long nativePointer);

    public native static long popStringList(long nativePointer);

    public native static String getStringList(int position, long nativePointer);

    public native static String getAllStringList(long nativePointer);

    public native static boolean getIsAdded(long nativePointer);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_list);

        ListView listView = findViewById(R.id.listView);
        ArrayList<String> Notes = new ArrayList<>();

        Notes.add("");
        list.JPush("hElLo");
        ListAdapter adapter = new ListAdapter(this, R.layout.list_items, Notes);
        listView.setAdapter(adapter);
        EditText Item = findViewById(R.id.TypeNote);
        Item.bringToFront();
        TextView listOneString = findViewById(R.id.ListOneString);
        listOneString.setText(list.JGetALL());

        Button bAddNewItem = findViewById(R.id.AddItem);
        bAddNewItem.setOnClickListener(view -> {
            String str = String.valueOf(Item.getText());
            list.JPush(str);
            if (list.JGetIsAdded()) {
                Notes.add(str);
                listOneString.setText(list.JGetALL());
                adapter.notifyDataSetChanged();
            }
        });
        Button bDeleteLastItem = findViewById(R.id.DeleteLastItem);
        bDeleteLastItem.setOnClickListener(view -> {
            if (Notes.size() != 0) {
                Notes.remove(Notes.size() - 1);
                list.JPop();
                listOneString.setText(list.JGetALL());
                adapter.notifyDataSetChanged();
            }
        });
        Button bPrevious = findViewById(R.id.PreviousPage);
        bPrevious.setOnClickListener(view -> {
            Intent intent = new Intent(StringListActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public class JStringList {
        private long nativePointer;

        public JStringList() {
            nativePointer = createStringList();
        }

        public void JPush(String element) {
            nativePointer = pushStringList(element, nativePointer);
        }

        public void JPop() {
            nativePointer = popStringList(nativePointer);
        }

        public String JGet(int position) {
            return getStringList(position, nativePointer);
        }

        public String JGetALL() {
            return getAllStringList(nativePointer);
        }

        public boolean JGetIsAdded() {
            return getIsAdded(nativePointer);
        }
    }

    public class ListAdapter extends ArrayAdapter<String> {

        private final int resourceLayout;
        private final Context mContext;

        public ListAdapter(Context context, int resource, ArrayList<String> tmp) {
            super(context, resource, tmp);
            this.resourceLayout = resource;
            this.mContext = context;
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
            item.setText(list.JGet(position));

            return v;
        }
    }
}
