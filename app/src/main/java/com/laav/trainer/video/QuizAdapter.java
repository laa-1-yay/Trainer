package com.laav.trainer.video;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;


public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QVHolder> {

    private static final String TAG = "QuizAdapter";
    Context context;
    ArrayList<String> answers;
    int videoNum;

    public static class QVHolder extends RecyclerView.ViewHolder {

        ImageButton trueA;
        ImageButton falseA;
        ImageView qPic;
        LinearLayout rootLayout;

        QVHolder(final View itemView) {
            super(itemView);

            trueA = (ImageButton) itemView.findViewById(R.id.trueA);
            falseA = (ImageButton) itemView.findViewById(R.id.falseA);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.root_layout);
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

        int drawableResourceId = context.getResources().getIdentifier("v"+videoNum+"p"+position+1, "drawable", context.getPackageName());
        holder.qPic.setImageResource(drawableResourceId);

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

    }

    @Override
    public int getItemCount() {
        return answers.size();
    }



}

