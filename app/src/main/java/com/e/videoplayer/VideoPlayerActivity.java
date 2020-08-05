package com.e.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    VideoView video_view;
    int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        video_view = findViewById(R.id.video_view);

        position = getIntent().getIntExtra("position",-1);
        playVideo();

    }

    private void playVideo() {


        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video_view);
        video_view.setMediaController(mediaController);
        video_view.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position)));
        video_view.requestFocus();
        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                video_view.start();
            }
        });
        video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                video_view.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position+1)));
                video_view.start();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        video_view.stopPlayback();
    }
}