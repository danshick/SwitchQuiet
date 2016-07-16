package net.danshick.switchquiet;

import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;

public class SwitchBroadcastReceiver extends BroadcastReceiver {   
    
    private String TAG = this.getClass().getSimpleName();
    
    @Override
    public void onReceive(Context context, Intent intent) {
      
      int swState = intent.getIntExtra("switch_state", -1);
      Log.i(TAG, "********** SQService Switch State Changed: " + swState);
      Intent i = new Intent("net.danshick.switchquiet.SWITCH_STATE_CHANGED");
      i.putExtra("switch_state_value", swState);
      context.sendBroadcast(i);

    }
    
}