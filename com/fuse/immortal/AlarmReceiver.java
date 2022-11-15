package com.fuse.immortal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {
        Log.d("immortal", "AlarmReceiver.ctor");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("immortal", "AlarmReceiver.onReceive");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent in = new Intent(context, RestartService.class);
            context.startForegroundService(in);
        } else {
            Intent in = new Intent(context, RealService.class);
            context.startService(in);
        }
    }
}
