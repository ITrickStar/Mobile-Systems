package com.example.lab5;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class ListTaskAdapter extends BaseAdapter {
    private final Activity activity;
    private final ArrayList<HashMap<String, String>> data;
    private final SQLite database;

    public ListTaskAdapter(Activity a, ArrayList<HashMap<String, String>> d, SQLite mydb) {
        activity = a;
        data = d;
        database = mydb;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ListTaskViewHolder holder;
        if (convertView == null) {
            holder = new ListTaskViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.task_list_row, parent, false);
            holder.task_name = convertView.findViewById(R.id.task_name);
            holder.checkBtn = convertView.findViewById(R.id.checkBtn);
            convertView.setTag(holder);
        } else {
            holder = (ListTaskViewHolder) convertView.getTag();
        }


        final HashMap<String, String> singleTask = data.get(position);
        final ListTaskViewHolder tmpHolder = holder;

        holder.task_name.setId(position);
        holder.checkBtn.setId(position);

        try {


            holder.checkBtn.setOnCheckedChangeListener(null);
            if (Objects.requireNonNull(singleTask.get("status")).contentEquals("1")) {
                holder.task_name.setText(Html.fromHtml("<strike>" + singleTask.get("task") + "</strike>"));
                holder.checkBtn.setChecked(true);
            } else {
                holder.task_name.setText(singleTask.get("task"));
                holder.checkBtn.setChecked(false);
            }

            holder.checkBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    database.updateTaskStatus(singleTask.get("id"), 1);
                    tmpHolder.task_name.setText(Html.fromHtml("<strike>" + singleTask.get("task") + "</strike>"));
                } else {
                    database.updateTaskStatus(singleTask.get("id"), 0);
                    tmpHolder.task_name.setText(singleTask.get("task"));
                }

            });


        } catch (Exception ignored) {
        }
        return convertView;
    }
}

class ListTaskViewHolder {
    TextView task_name;
    CheckBox checkBtn;
}