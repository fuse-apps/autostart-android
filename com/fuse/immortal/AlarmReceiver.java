package com.fuse.immortal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("immortal", "Starting activity @(Activity.Package).@(Activity.Name) (via AlarmReceiver)");
        Intent activityIntent = new Intent(context, @(Activity.Package).@(Activity.Name).class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityIntent);
    }
}
