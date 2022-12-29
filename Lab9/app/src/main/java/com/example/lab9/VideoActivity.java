package com.example.lab9;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        VideoView MyVideo = findViewById(R.id.MyVideo);
        String videoSource = "android.resource://com.example.lab9/" + R.raw.funniest_thing_ever;
        MyVideo.setVideoURI(Uri.parse(videoSource));

        MediaController mediaController = new MediaController(this);
        MyVideo.setMediaController(mediaController);
        mediaController.setAnchorView(MyVideo);

        Button bPreviousPage = findViewById(R.id.PreviousPage);
        bPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoActivity.this, MusicActivity.class);
                startActivity(intent);
            }
        });
    }
}