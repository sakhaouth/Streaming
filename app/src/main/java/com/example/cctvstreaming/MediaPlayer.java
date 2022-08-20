package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;


public class MediaPlayer extends AppCompatActivity {
    private ExoPlayer player;
    private PlayerView playerView;
    private String uri;
    private ImageButton playButton;
    private ImageButton sound_button;
    private ImageButton rotate_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        playButton = (ImageButton) findViewById(R.id.exo_play);
        sound_button = (ImageButton) findViewById(R.id.player_sound);
        rotate_button = (ImageButton) findViewById(R.id.rotate);
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
                public void onPlayerError(@NonNull PlaybackException error) {

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
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MediaPlayer.this,"Hello",Toast.LENGTH_SHORT).show();
                player.seekToDefaultPosition();
                player.setPlayWhenReady(true);

                player.getPlaybackState();


            }
        });
        sound_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {

                if(player.getVolume() == 0)
                {
                    sound_button.setImageDrawable(ContextCompat.getDrawable(MediaPlayer.this,R.drawable.ic_baseline_volume_mute_24));
                    player.setVolume(player.getDeviceVolume());
                }
                else
                {
                    sound_button.setImageDrawable(ContextCompat.getDrawable(MediaPlayer.this,R.drawable.ic_baseline_volume_off_24));
                    player.setVolume(0);
                }
            }
        });
        rotate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                }
                else
                {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                }

            }
        });

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
    @Override
    protected void onResume() {
        super.onResume();
        player.play();

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.pause();
    }

}