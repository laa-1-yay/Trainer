package com.laav.trainer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreen extends Activity {

    TextView visitWebsite ;



    private final int SPLASH_DISPLAY_LENGHT = 5000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

//        visitWebsite=(TextView)findViewById(R.id.visitWebsite);
//        visitWebsite.setPaintFlags(visitWebsite.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        visitWebsite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www."));
//                startActivity(intent);
//            }
//        });

/* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);

    }



}