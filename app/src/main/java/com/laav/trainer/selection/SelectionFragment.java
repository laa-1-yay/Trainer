package com.laav.trainer.selection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import com.laav.trainer.R;
import com.laav.trainer.ui.ProgressBarCircular;
import com.laav.trainer.video.VideoQuiz;


public class SelectionFragment extends android.support.v4.app.Fragment {
    private int mPosition;
    ProgressBarCircular progressBarCircular ;

    private String mParam1;
    private String mParam2;

    TextView noResults;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;

    private SelectionAdapter gifsridviewAdapter;

    ArrayList<Uri> arrayList= new ArrayList<>();

    private static final String ARG_POSITION = "position";
    private View selectedView;
    android.os.Handler handler;

    private static final String TRAINING_VIDEOS2 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Trainer/Videos";

    private static final String TRAINING_VIDEOS = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Trainer/Videos";

    Context context = getActivity();
    SharedPreferences sharedPref = context.getSharedPreferences(
            "com.laav.trainer.VIDEONUM", Context.MODE_PRIVATE);

//    SharedPreferences.Editor editor = sharedPref.edit();
//    editor.putInt(getString(R.string.saved_high_score), newHighScore);
//    editor.commit();

    public SelectionFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_selection, container, false);

        noResults=(TextView)v.findViewById(R.id.no_results);
        progressBarCircular=(ProgressBarCircular)v.findViewById(R.id.progressbar);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.videos_gridview);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        performSearch();

        return v;
    }



    public void performSearch(){

        clear_lists();
        progressBarCircular.setVisibility(View.GONE);

        String[] fileList=null;
        File videoFiles = new File(TRAINING_VIDEOS);

        if(videoFiles.isDirectory())
        {
            Log.d("Videos", "1");

            fileList=videoFiles.list();
            if(fileList!=null)
            {
                noResults.setVisibility(View.GONE);

                Log.d("Videos", "3");
                for(int i=0;i<fileList.length;i++)
                {
                    Log.d("Video:" + i + " File name", fileList[i]);
                    arrayList.add(Uri.parse(TRAINING_VIDEOS + "/" + fileList[i]));
                }
                gifsridviewAdapter = new SelectionAdapter(getActivity() ,  arrayList);
                gifsridviewAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(gifsridviewAdapter);
            }
            else{
                Log.d("Videos", "4");
            }
        }
        else{
            Log.d("Videos", "2");
        }
    }


    private void clear_lists() {
        arrayList.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 1 || requestCode == 2)
            {
                final Intent dataGet = data;
                Uri videoUri = dataGet.getData();

                Intent intent = new Intent(getActivity(), VideoQuiz.class);
                intent.putExtra("uri", getRealPathFromUri(getActivity(), videoUri));
                startActivity(intent);
            }
        }
    }

    private String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
