package com.laav.trainer.video;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.laav.trainer.R;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import kuchbhilabs.chestream.R;
import kuchbhilabs.chestream.activities.FullscreenPlayerActivityNoExo;

public class VideoQuiz extends Activity implements SurfaceHolder.Callback  {

    static  long playerPosition = 0;
    ImageView fullscreen;
    SurfaceView surfaceView;
    SurfaceHolder holder;
    static MediaPlayer mediaPlayer;
    private boolean isMediaPlayerInitialized = false;
    private boolean isSurfaceCreated = false;
    private boolean videoStarted = false;
    static VideoView vidView;
    static MediaController mediaController;
    static ProgressBar progressBar;
    String videoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        vidView = (VideoView)findViewById(R.id.myVideo);

        Intent intent = getIntent();
        videoID = intent.getStringExtra("videoID");

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

        play2(videoID);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isSurfaceCreated = true;
        if (isMediaPlayerInitialized) {
            startMediaPlayer(videoID);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void startMediaPlayer(final String uri) {
        new Thread(new Runnable() {

            public void run(){
                Log.d("vidErr", "2");

                synchronized (this) {
                    Log.d("vidErr", "3");

                    if (!videoStarted) {
                        try {
                            Log.d("vidErr", "4");

                            if (mediaPlayer == null) {
                                mediaPlayer = new MediaPlayer();
                            }
                            Log.d("vidErr", "5");

                            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(uri));
                            mediaPlayer.setLooping(false);
                            mediaPlayer.prepare();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                            Log.d("vidErr", "6");

                            mediaPlayer.setDisplay(holder);
//                            mediaPlayer.prepareAsync();

                            Log.d("vidErr", "7");

                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    Log.d("vidErr", "8");
                                    mediaPlayer.start();
                                    Log.d("vidErr", "9");
                                    videoStarted = true;
                                }
                            });
                        } catch (IOException e) {
                            Log.d("vidErr", "10");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        Log.d("vidErr", "11");

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

    public void play2(String uri){

        mediaController = new MediaController(getApplicationContext());
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