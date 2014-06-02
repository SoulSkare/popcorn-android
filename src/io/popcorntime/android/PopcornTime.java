/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

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