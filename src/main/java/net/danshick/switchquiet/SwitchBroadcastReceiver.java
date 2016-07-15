package net.danshick.switchquiet;

import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.widget.Toast;
import android.os.Bundle;
import android.util.Log;

public class SwitchBroadcastReceiver extends BroadcastReceiver {   

    @Override
    public void onReceive(Context context, Intent intent) {

     int myState = intent.getIntExtra("switch_state", -1);
     Toast.makeText(context, Integer.toString(myState), Toast.LENGTH_LONG).show();

    }
}