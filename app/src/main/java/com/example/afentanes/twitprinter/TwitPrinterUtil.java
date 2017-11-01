package com.example.afentanes.twitprinter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**
 * Created by afentanes on 10/31/17.
 */

public class TwitPrinterUtil {


    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context.getPackageName(), TwitsPrintService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);

        builder.setPersisted(true);
        builder.setOverrideDeadline(5*1000);

        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        int resultCode = jobScheduler.schedule(builder.build());


        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("a", "Job scheduled!");
        } else {
            Log.d("a", "Job not scheduled");
        }
    }


    public static void sendNotification (Context context, String message){


        String CHANNEL_ID = "twit_print_notifications";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("Twit printed")
                        .setContentText(message)
                        .setChannel(CHANNEL_ID);
        Intent resultIntent = new Intent(context, MainActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(0, mBuilder.build());

    }
}
