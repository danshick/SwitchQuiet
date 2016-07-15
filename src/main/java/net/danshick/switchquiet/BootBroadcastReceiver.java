package net.danshick.switchquiet;

import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;


public class BootBroadcastReceiver extends BroadcastReceiver {   

    @Override
    public void onReceive(Context context, Intent intent) {

     Intent myIntent = new Intent(context, SQService.class);
     context.startService(myIntent);

    }
}