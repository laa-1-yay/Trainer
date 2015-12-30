package com.laav.trainer.video;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.laav.trainer.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QVHolder> {

    private static final String TAG = "QuizAdapter";
    Context context;
    ArrayList<String> answers;
    int videoNum;
    MediaPlayer mp = new MediaPlayer();

    public static class QVHolder extends RecyclerView.ViewHolder {

        ImageView trueA;
        ImageView falseA;
        ImageView askQ;
        ImageView qPic;
        LinearLayout rootLayout;

        QVHolder(final View itemView) {
            super(itemView);

            trueA = (ImageView) itemView.findViewById(R.id.trueA);
            falseA = (ImageView) itemView.findViewById(R.id.falseA);
            askQ = (ImageView) itemView.findViewById(R.id.askQ);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.rootLayout);
            qPic = (ImageView) itemView.findViewById(R.id.qPic);

        }
    }


    public QuizAdapter(Context context, ArrayList<String> answers, int videoNum ) {
        this.videoNum = videoNum;
        this.context = context;
        this.answers = answers ;

    }


    @Override
    public QVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
            QVHolder cvh = new QVHolder(v1);
            return cvh;

    }

    @Override
    public void onBindViewHolder(final QVHolder holder, final int position) {

        AlertDialog.Builder builderTrue = new AlertDialog.Builder(context);
        builderTrue.setMessage("Correct answer");
        builderTrue.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        final AlertDialog dialogTrue = builderTrue.create();

        AlertDialog.Builder builderFalse = new AlertDialog.Builder(context);
        builderFalse.setMessage("Wrong answer");
        builderFalse.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        final AlertDialog dialogFalse = builderFalse.create();

        final int newPosition = position+1;
        try
        {
            InputStream ims = context.getAssets().open("/Pictures/"+"v"+videoNum+"p"+newPosition+".jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            holder.qPic.setImageDrawable(d);
        } catch(IOException ex) {
            return;
        }


        holder.trueA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answers.get(position).equals("T")){
                    dialogTrue.show();
                }else{
                    dialogFalse.show();
                }
            }
        });

        holder.falseA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answers.get(position).equals("F")){
                    dialogTrue.show();
                }else{
                    dialogFalse.show();
                }
            }
        });

        final AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        holder.askQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mp.isPlaying())
                {
                    mp.stop();
                }

                try {
                    mp.reset();
                    AssetFileDescriptor afd;
                    afd = context.getAssets().openFd("/Audio/"+"v"+videoNum+"a"+newPosition+".mp3");
                    mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    mp.prepare();
                    mp.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return answers.size();
    }



}

