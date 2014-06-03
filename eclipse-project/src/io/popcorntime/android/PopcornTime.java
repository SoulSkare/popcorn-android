package io.popcorntime.android;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaActivity;
import org.nodejs.core.NodeJSService;
import org.nodejs.core.NodeJSService.LocalBinder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PopcornTime extends CordovaActivity
{
    boolean mBound;
    NodeJSService mServer;

    ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(PopcornTime.this, "Service is connected", Toast.LENGTH_LONG).show();

            mBound = true;
            mServer = ((LocalBinder) service).getActivity();
       }

       public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(PopcornTime.this, "Service is disconnected", Toast.LENGTH_LONG).show();

            mBound = false;
            mServer = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.init();

        startService(new Intent(this, NodeJSService.class));

        Log.d("PopcornTime", "App created!");
        super.loadUrl(Config.getStartUrl());
    }
}