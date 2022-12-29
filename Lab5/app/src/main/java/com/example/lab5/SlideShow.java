package com.example.lab5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SlideShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        Button nextPageButton = findViewById(R.id.NextPage);
        Button button_browser = findViewById(R.id.button_browser);
        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SlideShow.this, ButtonActivity.class);
                startActivity(intent);
            }
            public void setListenerOnButton() {
                button_browser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org"));
                        startActivity(browserIntent);
                    }
        });
    }
});
}
}