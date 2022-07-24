package com.example.cctvstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class MediaPlayer extends AppCompatActivity {
    private VideoView videoView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        videoView = findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        String stringURI = getIntent().getStringExtra("uri");
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(stringURI));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //videoView.setVideoPath("/media/sakhaouth/New Volume/Movie/Into.The.Wild.2007.720p.BrRip.x264.YIFY.mp4");
        videoView.requestFocus();
        videoView.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(android.media.MediaPlayer mediaPlayer) {
                videoView.start();
                progressBar.setVisibility(View.GONE);
            }
        });
        videoView.start();
    }
}