package com.example.afentanes.twitprinter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by afentanes on 10/31/17.
 */

public class TwitsPrinterReceiver extends BroadcastReceiver {

    private static final String PROVIDER_NAME = "afentanes.twitsaver.twitssprovider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/twit/id");

    @Override
    public void onReceive(Context context, Intent intent) {
        printTwitFromApp(context, intent.getExtras().getLong("id"));
    }


    private void printTwitFromApp(Context context, long id) {

        if(id>0){
            Cursor c = TwitsDbReader.getTwit(context, id);
            if(c.getCount()>0){
                c.moveToFirst();
                Intent printIntent = new Intent(context, TwitsPrintService.class);
                Bundle bundle = new Bundle();
                bundle.putString("twit" , c.getString(3));
                bundle.putString("id", c.getString(1));
                bundle.putString("author", c.getString(2));
                printIntent.putExtras(bundle);
                context.startService(printIntent);
            }


        }

    }




}
