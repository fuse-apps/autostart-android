package com.fuse.immortal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

// AlarmReceiver doesn't trigger consistently without this service,
// so we'll need to keep it around to not break things.

public class ImmortalService extends Service {
    public static Intent serviceIntent = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("immortal", "ImmortalService.onStartCommand");

        serviceIntent = intent;
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("immortal", "ImmortalService.onDestroy");
        super.onDestroy();

        serviceIntent = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("immortal", "ImmortalService.onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("immortal", "ImmortalService.onUnbind");
        return super.onUnbind(intent);
    }
}
