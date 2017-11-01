package com.example.afentanes.twitprinter;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
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
}
