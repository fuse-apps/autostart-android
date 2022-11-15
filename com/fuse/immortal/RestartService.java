package com.fuse.immortal;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class RestartService extends Service {
    public RestartService() {
        Log.d("immortal", "RestartService.ctor");
    }

    @Override
    public void onCreate() {
        Log.d("immortal", "RestartService.onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("immortal", "RestartService.onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("immortal", "RestartService.onStartCommand");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        builder.setSmallIcon(@(Activity.Package).R.mipmap.icon);
        builder.setContentTitle(null);
        builder.setContentText(null);
        Intent notificationIntent = new Intent(this, @(Activity.Package).@(Activity.Name).class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_NONE));
        }

        Notification notification = builder.build();
        startForeground(9, notification);

        /////////////////////////////////////////////////////////////////////
        Intent in = new Intent(this, RealService.class);
        startService(in);

        stopForeground(true);
        stopSelf();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("immortal", "RestartService.onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("immortal", "RestartService.onUnbind");
        return super.onUnbind(intent);
    }

}
