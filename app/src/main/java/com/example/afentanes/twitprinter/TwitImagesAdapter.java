package com.example.afentanes.twitprinter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;



public class TwitImagesAdapter extends ArrayAdapter <String> {
    public TwitImagesAdapter(@NonNull Context context, @NonNull String[] objects) {
        super(context, R.layout.twit_image, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(getContext());
        View inflate = inflater.inflate(R.layout.twit_image, parent, false);
        try {
            ContextWrapper wrapper = new ContextWrapper(getContext().getApplicationContext());
            File file = new File(wrapper.getDir(getContext().getResources().getString(R.string.twit_images_dir), Context.MODE_PRIVATE), this.getItem(position));
              Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
               ImageView img=inflate.findViewById(R.id.image_example);
               img.setImageBitmap(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inflate;
    }
}
