package com.example.lab5;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskActivity extends AppCompatActivity {

    SQLite myDB;
    LinearLayout empty;
    ScrollView scrollView;
    LinearLayout todayContainer, tomorrowContainer, otherContainer;
    mListView taskListToday, taskListTomorrow, taskListUpcoming;
    ArrayList<HashMap<String, String>> todayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> tomorrowList = new ArrayList<>();
    ArrayList<HashMap<String, String>> upcomingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        myDB = new SQLite(this);
        empty = findViewById(R.id.empty);
        scrollView = findViewById(R.id.scrollView);
        todayContainer = findViewById(R.id.todayContainer);
        tomorrowContainer = findViewById(R.id.tomorrowContainer);
        otherContainer = findViewById(R.id.otherContainer);
        taskListToday = findViewById(R.id.taskListToday);
        taskListTomorrow = findViewById(R.id.taskListTomorrow);
        taskListUpcoming = findViewById(R.id.taskListUpcoming);
    }
    public void Forw(View view) {
        startActivity(new Intent(this, SlideShow.class));
    }

    public void openAddModifyTask(View view) {
        startActivity(new Intent(this, AddTask.class));
    }

    public void Back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }


    public void populateData() {
        myDB = new SQLite(this);

        runOnUiThread(this::fetchDataFromDB);
    }


    public void fetchDataFromDB() {
        todayList.clear();
        tomorrowList.clear();
        upcomingList.clear();

        Cursor today = myDB.getTodayTask();
        Cursor tomorrow = myDB.getTomorrowTask();
        Cursor upcoming = myDB.getUpcomingTask();

        loadDataList(today, todayList);
        loadDataList(tomorrow, tomorrowList);
        loadDataList(upcoming, upcomingList);


        if (todayList.isEmpty() && tomorrowList.isEmpty() && upcomingList.isEmpty()) {
            empty.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

            if (todayList.isEmpty()) {
                todayContainer.setVisibility(View.GONE);
            } else {
                todayContainer.setVisibility(View.VISIBLE);
                loadListView(taskListToday, todayList);
            }

            if (tomorrowList.isEmpty()) {
                tomorrowContainer.setVisibility(View.GONE);
            } else {
                tomorrowContainer.setVisibility(View.VISIBLE);
                loadListView(taskListTomorrow, tomorrowList);
            }

            if (upcomingList.isEmpty()) {
                otherContainer.setVisibility(View.GONE);
            } else {
                otherContainer.setVisibility(View.VISIBLE);
                loadListView(taskListUpcoming, upcomingList);
            }
        }
    }


    public void loadDataList(Cursor cursor, ArrayList<HashMap<String, String>> dataList) {
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                HashMap<String, String> mapToday = new HashMap<>();
                mapToday.put("id", cursor.getString(0));
                mapToday.put("task", cursor.getString(1));
                mapToday.put("date", cursor.getString(2));
                mapToday.put("status", cursor.getString(3));
                dataList.add(mapToday);
                cursor.moveToNext();
            }
        }
    }

    public void loadListView(mListView listView, final ArrayList<HashMap<String, String>> dataList) {
        ListTaskAdapter adapter = new ListTaskAdapter(this, dataList, myDB);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(TaskActivity.this, AddTask.class);
            i.putExtra("isModify", true);
            i.putExtra("id", dataList.get(+position).get("id"));
            startActivity(i);
        });
    }
}