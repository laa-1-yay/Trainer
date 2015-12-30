package com.laav.trainer.video;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.laav.trainer.R;


public class VideoQuiz extends Activity  {

    static  long playerPosition = 0;
    ImageView fullscreen;
    static MediaPlayer mediaPlayer;
    static VideoView vidView;
    static MediaController mediaController;
    static ProgressBar progressBar;
    String videoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_quiz);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        vidView = (VideoView)findViewById(R.id.myVideo);

        Intent intent = getIntent();
        videoID = intent.getStringExtra("uri");

        fullscreen=(ImageView) findViewById(R.id.fullscreen);
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), FullscreenPlayerActivity.class);
                intent.putExtra("videoID",videoID);
                if (vidView!=null)
                    intent.putExtra("position",vidView.getCurrentPosition());
                else intent.putExtra("position",0);
                startActivity(intent);
            }
        });

        play(videoID);
    }

    @Override
    public void onPause() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d("vidErr", "12");

        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer=new MediaPlayer();
        Log.d("vidErr", "13");
    }

    public void play(String uri){

        mediaController = new MediaController(VideoQuiz.this);
        mediaController.setAnchorView(vidView);
        mediaController.setMediaPlayer(vidView);

        vidView.setMediaController(mediaController);
        Log.d("urlrryo", uri);
        vidView.setVideoURI(Uri.parse(uri));
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
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1, int arg2) {
                        // TODO Auto-generated method stub
                        progressBar.setVisibility(View.GONE);
                        mp.start();
                    }
                });
            }
        });
    }

}