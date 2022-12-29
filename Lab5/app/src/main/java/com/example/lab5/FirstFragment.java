package com.example.lab5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FirstFragment extends Fragment {
    public interface onSomeEventListener {
        public void someEvent(String a);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener= (onSomeEventListener) activity;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + "INFO");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        Button next_btn = view.findViewById(R.id.next_btn7);
        Button det = view.findViewById(R.id.back_btn);
        Button att = view.findViewById(R.id.next_btn2);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                someEventListener.someEvent("next");
            }
        });
        det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                someEventListener.someEvent("det");
            }
        });
        att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                someEventListener.someEvent("att");
            }
        });
        return view;
    }
}