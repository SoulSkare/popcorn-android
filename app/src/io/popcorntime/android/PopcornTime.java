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
   boolean mBounded;
   NodeJSService mServer;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);
    
        super.init();
        Intent mIntent = new Intent(this, NodeJSService.class);
        startService(mIntent);
  
        Log.d("Popcormtime", "App created!");
        // Set by <content src="index.html" /> in config.xml
        super.loadUrl(Config.getStartUrl());
        //super.loadUrl("file:///android_asset/www/index.html");
    } 
    ServiceConnection mConnection = new ServiceConnection() {
        
        public void onServiceDisconnected(ComponentName name) {
         Toast.makeText(PopcornTime.this, "Service is disconnected", 1000).show();
         mBounded = false;
         mServer = null;
        }
        
        public void onServiceConnected(ComponentName name, IBinder service) {
         Toast.makeText(PopcornTime.this, "Service is connected", 1000).show();
         mBounded = true;
         LocalBinder mLocalBinder = (LocalBinder)service;
         mServer = mLocalBinder.getActivity();
        }
       };
}