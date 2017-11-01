package com.example.afentanes.twitprinter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by afentanes on 11/1/17.
 */

public class TwitImagesLoadingAdapter extends RecyclerView.Adapter <TwitImagesLoadingAdapter.TwitImagesViewHolder>{


    private String[] twitFiles;

    public TwitImagesLoadingAdapter(String [] files){
        twitFiles= files;
    }


    public static class TwitImagesViewHolder  extends RecyclerView.ViewHolder{
        LinearLayout twitView;
        public TwitImagesViewHolder(LinearLayout itemView) {
            super(itemView);
            twitView= itemView;
        }

    }
    @Override
    public TwitImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.twit_image, parent, false);
        TwitImagesViewHolder imagesViewHolder= new TwitImagesViewHolder(linearLayout);
        return imagesViewHolder;
    }

    @Override
    public void onBindViewHolder(TwitImagesViewHolder holder, int position) {

        ContextWrapper wrapper = new ContextWrapper(holder.twitView.getContext().getApplicationContext());
        File file = new File(wrapper.getDir(holder.twitView.getContext().getResources().getString(R.string.twit_images_dir), Context.MODE_PRIVATE), twitFiles[position]);
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(file));
            ImageView img= holder.twitView.findViewById(R.id.image_example);
            img.setImageBitmap(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return twitFiles.length;
    }
}
