package com.fuse.immortal;

import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private Intent serviceIntent;

    protected BaseActivity() {
        Log.d("immortal", "BaseActivity.ctor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("immortal", "BaseActivity.onCreate");
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

        if (RealService.serviceIntent == null) {
            serviceIntent = new Intent(this, RealService.class);
            startService(serviceIntent);
        } else {
            serviceIntent = RealService.serviceIntent;
            Toast.makeText(getApplicationContext(), "already", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("immortal", "BaseActivity.onDestroy");
        super.onDestroy();

        if (serviceIntent != null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }
}
