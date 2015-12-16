package com.laav.trainer.video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.laav.trainer.R;

public class FullscreenPlayerActivity extends AppCompatActivity {

    VideoView vidView;
    MediaController mediaController;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_player_activity);

        vidView = (VideoView)findViewById(R.id.myVideo);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        mediaController = new MediaController(FullscreenPlayerActivity.this);
        mediaController.setAnchorView(vidView);
        mediaController.setMediaPlayer(vidView);

        vidView.setMediaController(mediaController);
        vidView.setVideoURI(Uri.parse(getIntent().getStringExtra("url")));
        vidView.seekTo(getIntent().getIntExtra("position",0));

        vidView.start();
        progressBar.setVisibility(View.VISIBLE);
        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                progressBar.setVisibility(View.GONE);
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
                        progressBar.setVisibility(View.GONE);
                        mp.start();
                    }
                });
            }
        });
    }
}
