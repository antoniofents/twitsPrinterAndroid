package com.example.afentanes.twitprinter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class TwitsPrintedViewAdapter extends Adapter <TwitsPrintedViewAdapter.TwitImagesViewHolder>{

    private Bitmap[] twitFiles;

    public TwitsPrintedViewAdapter(Bitmap [] files){
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

        try {
            ImageView img= holder.twitView.findViewById(R.id.image_example);
            img.setImageBitmap(twitFiles[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return twitFiles.length;
    }
}
