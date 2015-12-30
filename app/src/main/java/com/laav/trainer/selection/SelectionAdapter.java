package com.laav.trainer.selection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.laav.trainer.R;
import com.laav.trainer.ui.ProgressBarCircular;
import com.laav.trainer.video.VideoQuiz;

import java.util.ArrayList;


public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolderr> {

    private Activity mActivity;
    private int gridLayout;

    private ArrayList<Uri> entriesSett;

    SharedPreferences sharedPref ;

    public SelectionAdapter(Activity c, ArrayList<Uri> entries) {
        Log.d("tessst5", "hi");

        this.mActivity = c;
        this.entriesSett = entries;
        this.gridLayout = R.layout.gridview_selection_item;
        sharedPref = c.getSharedPreferences("com.laav.trainer.VIDEONUM", Context.MODE_PRIVATE);
    }

    @Override
    public ViewHolderr onCreateViewHolder(ViewGroup parent, int viewType) {

        View grid = LayoutInflater.from(parent.getContext()).inflate(gridLayout, parent, false);

        return new ViewHolderr(grid);
    }

    @Override
    public void onBindViewHolder(final ViewHolderr holder, final int position) {
        Log.d("teswww", entriesSett.get(position).toString());

        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(entriesSett.get(position).toString(), MediaStore.Video.Thumbnails.MICRO_KIND);

        final int videoNum = sharedPref.getInt("videonum",0);
        if(bitmap!=null){
            holder.videosImage.setImageBitmap(bitmap);
        }
        if(position>videoNum){
            holder.lock.setVisibility(View.VISIBLE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>videoNum){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setMessage("Video is locked");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{

                    Intent intent = new Intent(mActivity, VideoQuiz.class);
                    intent.putExtra("uri",entriesSett.get(position).toString());
                    intent.putExtra("vidnum",position+1);
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return entriesSett.size();
    }


    public static class ViewHolderr extends RecyclerView.ViewHolder {
        public ImageView videosImage;
        public ImageView lock;


        public ViewHolderr(View itemView) {
            super(itemView);
            videosImage = (ImageView) itemView.findViewById(R.id.gridview_image_view);
            lock = (ImageView) itemView.findViewById(R.id.lock);
        }
    }



}
