package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PageActivity extends AppCompatActivity implements
        FirstFragment.onSomeEventListener, SecondFragment.onSomeEventListener {
    final String LOG_TAG = "tag";
    SecondFragment secondFragment;
    FirstFragment firstFragment;
    FragmentManager myFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        myFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = myFragmentManager.beginTransaction();
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        ft.add(R.id.fragm, secondFragment);
        ft.replace(R.id.fragm, firstFragment);
        ft.commit();
    }
    public void someEvent(String a) {
        FragmentTransaction ft = myFragmentManager.beginTransaction();
        if (a.equals("next")) {
            Intent intent_next = new Intent(PageActivity.this, TextDialog.class);
            startActivity(intent_next);
        } else {
            switch (a) {
                case "att":
                    if (secondFragment.isDetached()) {
                        Log.d(LOG_TAG, "ATT 2");
                        ft.attach(secondFragment);
                        ft.detach(firstFragment);
                    } else {
                        Log.d(LOG_TAG, "ATT 1");
                        Toast.makeText(getApplicationContext(), "Page is attached",
                                Toast.LENGTH_SHORT).show();
                        secondFragment = new SecondFragment();
                        ft.add(R.id.fragm, secondFragment);
                        ft.detach(firstFragment);
                    }
                    break;
                case "det":
                    Log.d(LOG_TAG, "DET 1");
                    if (!(secondFragment.isRemoving())) {
                        Toast.makeText(getApplicationContext(), "Page is detached",
                                Toast.LENGTH_SHORT).show();
                        ft.remove(secondFragment);
                    }
                    break;
                case "back":
                    Log.d(LOG_TAG, "BACK");
                    ft.attach(firstFragment);
                    ft.detach(secondFragment);
                    break;
            }
            ft.commit();
        }
    }
}
