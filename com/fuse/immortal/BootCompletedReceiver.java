package com.fuse.immortal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class BootCompletedReceiver extends BroadcastReceiver {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 31337;

    public static boolean hasSystemAlertWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(com.fuse.Activity.getRootActivity());
        }
        return true;
    }

    public static void askForSystemAlertWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AppCompatActivity activity = com.fuse.Activity.getRootActivity();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                       Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i("immortal", "Starting activity @(Activity.Package).@(Activity.Name) [BootCompletedReceiver]");
            Intent activityIntent = new Intent(context, @(Activity.Package).@(Activity.Name).class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);
        }
    }
}
