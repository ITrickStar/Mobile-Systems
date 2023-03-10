package com.example.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NoteList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        EditText item = findViewById(R.id.TypeNote);
        ListView listView = findViewById(R.id.listView);
        ArrayList<String> Notes = new ArrayList<>();
        DataBaseHelper db = new DataBaseHelper(NoteList.this);
        Cursor cursor = db.readAllData();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                Notes.add(cursor.getString(1));
            }
        }
        ListAdapter adapter = new ListAdapter(this, R.layout.list_items_note_activity, Notes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor1 = db.readAllData();
            cursor1.moveToPosition(position);
            String _id = cursor1.getString(0);
            db.deleteOneRow(_id);
            Notes.remove(position);
            adapter.notifyDataSetChanged();
        });

        Button bAddNewItem = findViewById(R.id.AddItem);
        bAddNewItem.setOnClickListener(view -> {
            DataBaseHelper db1 = new DataBaseHelper(NoteList.this);
            String str = String.valueOf(item.getText());
            db1.addNote(str);
            Notes.add(str);
            adapter.notifyDataSetChanged();
        });
        Button bDeleteDataBase = findViewById(R.id.DeleteDataBase);
        bDeleteDataBase.setOnClickListener(view -> {
            db.deleteAllData();
            Notes.clear();
            adapter.notifyDataSetChanged();
        });
        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(view -> {
            Intent intent = new Intent(NoteList.this, CheckBox.class);
            startActivity(intent);
        });
        Button bPrevious = findViewById(R.id.PreviousPage);
        bPrevious.setOnClickListener(view -> {
            Intent intent = new Intent(NoteList.this, Http.class);
            startActivity(intent);
        });
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "MyDataBase.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "NoteList";
        private static final String COLUMN_ID = "note_id";
        private static final String COLUMN_NOTE = "note_text";
        private static Context context;

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            DataBaseHelper.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOTE + " TEXT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        void addNote(String noteText) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_NOTE, noteText);
            long result = db.insert(TABLE_NAME, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        }

        Cursor readAllData() {
            String query = "SELECT * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if (db != null) {
                cursor = db.rawQuery(query, null);
            }
            return cursor;
        }

        void updateData(String row_id, String noteText, String author, String pages) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NOTE, noteText);

            long result = db.update(TABLE_NAME, cv, "note_id=?", new String[]{row_id});
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        }

        void deleteOneRow(String row_id) {
            SQLiteDatabase db = this.getWritableDatabase();
            long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{row_id});
            if (result == -1) {
                Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
        }

        void deleteAllData() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_NAME);
        }
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
