package com.fuse.immortal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ImmortalActivity extends AppCompatActivity {

    private static boolean restartOnDestroy;

    public static boolean isRestartOnDestroyEnabled() {
        return restartOnDestroy;
    }

    public static void enableRestartOnDestroy(boolean enabled) {
        Log.d("immortal", "enableRestartOnDestroy: " + (enabled ? "true" : "false"));
        restartOnDestroy = enabled;
    }

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        boolean isWhiteListing = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isWhiteListing = pm.isIgnoringBatteryOptimizations(getApplicationContext().getPackageName());
        }
        if (!isWhiteListing) {
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivity(intent);
        }

        if (ImmortalService.serviceIntent == null) {
            serviceIntent = new Intent(this, ImmortalService.class);
            startService(serviceIntent);
        } else {
            serviceIntent = ImmortalService.serviceIntent;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setAlarmTimer();

        if (serviceIntent != null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }

    protected void setAlarmTimer() {
        if (!restartOnDestroy) {
            Log.d("immortal", "restartOnDestroy is disabled. Goodbye.");
            return;
        }

        Log.d("immortal", "restartOnDestroy is enabled. Waiting for app to restart...");

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.SECOND, 1);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
    }
}
