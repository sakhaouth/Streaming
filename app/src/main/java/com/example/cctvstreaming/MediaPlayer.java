package com.example.cctvstreaming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;


public class MediaPlayer extends AppCompatActivity {
    private ExoPlayer player;
    private PlayerView playerView;
    private String uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        try {
            uri = (String) getIntent().getStringExtra("uri");
            player = new ExoPlayer.Builder(this).build();
            playerView = (PlayerView) findViewById(R.id.player_view);
            playerView.setPlayer(player);
            Log.d("link",uri);
            MediaItem mediaItem = MediaItem.fromUri(uri);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
            player.addListener(new Player.Listener() {
                @Override
                public void onPlayerError(PlaybackException error) {
                    Toast.makeText(MediaPlayer.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRenderedFirstFrame() {
                    Toast.makeText(MediaPlayer.this,"Streaming Started",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPlayerErrorChanged(@Nullable PlaybackException error) {

                }



            });


        }
        catch (Exception exception)
        {
            Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.pause();
        player.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.pause();
    }

}