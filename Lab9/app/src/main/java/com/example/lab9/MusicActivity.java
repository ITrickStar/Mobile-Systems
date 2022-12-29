package com.example.lab9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noMusicTextView;
    ArrayList<AudioModel> songList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        recyclerView = findViewById(R.id.recyclerView);

        String song1 = "/storage/emulated/0/Download/na_zare.mp3";
        String song2 = "/storage/emulated/0/Download/dream_on.mp3";
        String song3 = "/storage/emulated/0/Download/the_perfect_girl.mp3";


        AudioModel songData = new AudioModel(song1, "Na Zare");
        songList.add(songData);
        songData = new AudioModel(song2, "Dream On");
        songList.add(songData);
        songData = new AudioModel(song3, "The Perfect Girl");
        songList.add(songData);

        if (songList.size() == 0) {
            noMusicTextView.setText("There is no music");
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MusicListAdapter(songList, getApplicationContext()));
        }

        Button bPreviousPage = findViewById(R.id.PreviousPage);
        bPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicActivity.this, ParticlesActivity.class);
                startActivity(intent);
            }
        });

        Button bNext = findViewById(R.id.NextPage);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });
    }

    public static class MyMediaPlayer {
        public static int currentIndex = -1;
        static MediaPlayer instance;

        public static MediaPlayer getInstance() {
            if (instance == null) {
                instance = new MediaPlayer();
            }
            return instance;
        }
    }

    public class AudioModel implements Serializable {
        String path;
        String title;

        public AudioModel(String path, String title) {
            this.path = path;
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public String getTitle() {
            return title;
        }
    }

    public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {
        ArrayList<AudioModel> songList;
        Context context;

        public MusicListAdapter(ArrayList<AudioModel> songList, Context context) {
            this.songList = songList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
            return new MusicListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            AudioModel songData = songList.get(position);
            holder.titleSong.setText(songData.getTitle());

            if (MyMediaPlayer.currentIndex == position) {
                holder.titleSong.setTextColor(Color.GRAY);
            } else {
                holder.titleSong.setTextColor(Color.BLACK);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyMediaPlayer.getInstance().reset();
                    MyMediaPlayer.currentIndex = position;
                    setMusic();

                    MusicActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
                            if (mediaPlayer != null) {
                                ImageView PlayMusic = findViewById(R.id.PauseResume);
                                if (mediaPlayer.isPlaying()) {
                                    PlayMusic.setImageResource(R.drawable.ic_baseline_pause_24);
                                } else {
                                    PlayMusic.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                                }
                            }
                            new Handler().postDelayed(this, 100);
                        }
                    });
                }
            });
        }

        private void setMusic() {
            TextView titleSong = findViewById(R.id.Title);

            AudioModel currentSong = songList.get(MyMediaPlayer.currentIndex);
            titleSong.setText(currentSong.getTitle());

            ImageView PlayMusic = findViewById(R.id.PauseResume);
            ImageView PlayNext = findViewById(R.id.SkipNext);
            ImageView PlayPrevious = findViewById(R.id.SkipPrevious);

            MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();

            PlayMusic.setOnClickListener(v -> playMusic(currentSong));
            PlayNext.setOnClickListener(v -> playNextSong());
            PlayPrevious.setOnClickListener(v -> playPreviousSong());
        }

        private void playMusic(AudioModel currentSong) {
            MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            else {
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(currentSong.getPath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void playNextSong() {
            if (MyMediaPlayer.currentIndex == songList.size() - 1)
                return;
            MyMediaPlayer.currentIndex += 1;
            MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
            mediaPlayer.reset();
            setMusic();
        }

        private void playPreviousSong() {
            if (MyMediaPlayer.currentIndex == 0)
                return;
            MyMediaPlayer.currentIndex -= 1;
            MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
            mediaPlayer.reset();
            setMusic();
        }

        private void pauseMusic() {
            MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }

        @Override
        public int getItemCount() {
            return songList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView titleSong;

            public ViewHolder(View itemView) {
                super(itemView);
                titleSong = itemView.findViewById(R.id.TitleSong);
            }
        }
    }
}
