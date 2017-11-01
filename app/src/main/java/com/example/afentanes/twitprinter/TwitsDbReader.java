package com.example.afentanes.twitprinter;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by afentanes on 11/1/17.
 */

public class TwitsDbReader {


    public static String[] TWITS_BASIC_PROJECTION = new String[]{"twit"};

    private static final String PROVIDER_NAME = "afentanes.twitsaver.twitssprovider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/twit/id");
    private static final Uri CONTENT_URI_ALL = Uri.parse("content://" + PROVIDER_NAME + "/twits/all");


    public static Cursor getTwit(Context context, long id) {

        if (id > 0) {
            CursorLoader cursorLoader = new CursorLoader(context, CONTENT_URI,
                    null, null, new String[]{String.valueOf(id)}, null);
            return cursorLoader.loadInBackground();

        }
        return null;

    }

    public static Cursor getTwits(Context context) {
        CursorLoader cursorLoader = new CursorLoader(context, CONTENT_URI_ALL,
                null, null, null, null);
        return  cursorLoader.loadInBackground();

    }

}
