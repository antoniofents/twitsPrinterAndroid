package com.example.afentanes.twitprinter;

import android.app.IntentService;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;


public class TwitsPrintService extends IntentService {


    public TwitsPrintService() {
        super("TwitsPrintService");
    }

    private Bitmap textAsBitmap(String text, String author, int textColor) {
        String printText= text +"\n -" +author;
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(100);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(printText) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(printText, 0, baseline, paint);
        return image;
    }

    private void saveImageToInternal(Bitmap image, String name){
        ContextWrapper wrapper= new ContextWrapper(getApplicationContext());

        File file = new File( wrapper.getDir(getResources().getString(R.string.twit_images_dir) , MODE_PRIVATE), name);
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            TwitPrinterUtil.sendNotification(this, name + " printed");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {

                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bitmap image = textAsBitmap(intent.getStringExtra("twit"),intent.getStringExtra("author"), Color.BLUE);
        saveImageToInternal(image, "twitImage"+intent.getStringExtra("id") +".png" );
    }
}
