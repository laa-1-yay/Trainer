package com.laav.trainer.selection;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.VideoView;

import com.laav.trainer.R;
import com.laav.trainer.ui.ProgressBarCircular;

import java.util.ArrayList;


public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolderr> {

    private Context mContext;
    private int gridLayout;

    private ArrayList<Uri> entriesSett;

    SharedPreferences sharedPref ;

    public SelectionAdapter(Context c, ArrayList<Uri> entries) {
        Log.d("tessst5", "hi");

        this.mContext = c;
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
        if(position<=videoNum){
            holder.lock.setVisibility(View.VISIBLE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<=videoNum){

                }else{

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
