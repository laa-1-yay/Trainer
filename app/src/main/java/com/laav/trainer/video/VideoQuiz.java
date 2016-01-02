package com.laav.trainer.video;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.laav.trainer.R;
import com.laav.trainer.ui.RecyclerViewDisabler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class VideoQuiz extends Activity  {

    static  long playerPosition = 0;
    ImageView fullscreen;
    static MediaPlayer mediaPlayer;
    static VideoView vidView;
    static MediaController mediaController;
    static ProgressBar progressBar;
    String videoID;
    public static RecyclerView recyclerView;
    int videoNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_quiz);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        vidView = (VideoView)findViewById(R.id.myVideo);

        Intent intent = getIntent();
        videoID = intent.getStringExtra("uri");
        videoNum = intent.getIntExtra("vidnum", 1);


        fullscreen=(ImageView) findViewById(R.id.fullscreen);
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), FullscreenPlayerActivity.class);
                intent.putExtra("uri",videoID);
                if (vidView!=null)
                    intent.putExtra("position",vidView.getCurrentPosition());
                else intent.putExtra("position",0);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);

//        // Disable scrolling recycler view
//        RecyclerView.OnItemTouchListener disabler = new RecyclerViewDisabler();
//        recyclerView.addOnItemTouchListener(disabler);

        ArrayList<String> answers = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                if(jo.get("VNUM").toString().equals(String.valueOf(videoNum))){
                    answers.add(jo.get("ANS").toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        QuizAdapter adapter=new QuizAdapter(getApplicationContext(), VideoQuiz.this,answers,videoNum);
        recyclerView.setAdapter(adapter);

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

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("ans.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void move(){
     recyclerView.smoothScrollBy(900,0);
    }
}