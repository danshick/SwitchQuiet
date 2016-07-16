package net.danshick.switchquiet;

import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {   

    @Override
    public void onReceive(Context context, Intent intent) {
      
      Log.i(this.getClass().getSimpleName(), "********** BootReceiver Reached");
      
      SharedPreferences prefs = context.getSharedPreferences("sqPrefs", Context.MODE_PRIVATE);
      if(prefs.getBoolean("startOnBoot", true)){
        context.startService(new Intent(context, SQService.class));
      }
    }
}