package com.example.afentanes.twitprinter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by afentanes on 10/31/17.
 */

public class TwitsPrinterReceiver extends BroadcastReceiver {

    private static final String PROVIDER_NAME = "afentanes.twitsaver.twitssprovider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/twit/id");

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("RECEIVED FROM OTHER APP", "yeeiiiiiii");
        refreshValuesFromContentProvider(context, intent.getExtras().getLong("id"));

    }


    private void refreshValuesFromContentProvider(Context context, long id) {

        if(id>0){

            CursorLoader cursorLoader = new CursorLoader(context,  CONTENT_URI,
                    null, null, new  String [] {String.valueOf(id)}, null);
            Cursor c = cursorLoader.loadInBackground();
            c.moveToFirst();
            CharSequence text = "printing twit  " + c.getString(3);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }




    }

    public void onClickAddImage(View view) {
       /* Uri uri = getContentResolver().insert(CONTENT_URI, contentValues);
        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        refreshValuesFromContentProvider();*/
    }
}
