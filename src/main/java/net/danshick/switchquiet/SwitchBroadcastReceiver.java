package net.danshick.switchquiet;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;

import android.media.AudioManager;
import android.widget.Toast;

import android.util.Log;

public class SwitchBroadcastReceiver extends BroadcastReceiver {   
    
    private String TAG = this.getClass().getSimpleName();
    
    private AudioManager am;
    
    private int vol;
    private int maxVol;
    private int prevSwState;
    
    @Override
    public void onReceive(Context context, Intent intent) {
      
      int swState = intent.getIntExtra("switch_state", -1);
      Log.i(TAG, "********** SQService Switch State Changed: " + swState);
      
      SharedPreferences prefs = context.getSharedPreferences("sqPrefs", Context.MODE_PRIVATE);
      SharedPreferences.Editor edit = prefs.edit();
      
      if(!prefs.getBoolean("runSwitch", false)) return;
      
      am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
      maxVol = am.getStreamMaxVolume(AudioManager.STREAM_RING);
      vol = prefs.getInt("lastVolume", maxVol);
      prevSwState = prefs.getInt("lastState", -1);
        
      switch(swState){
  
        case 2:
          Log.i(TAG, "********** SQService Previous State " + prevSwState);
          if( prevSwState == -1 || prevSwState == 3 ){
            vol = am.getStreamVolume(AudioManager.STREAM_RING);
            edit.putInt("lastVolume", vol);
            edit.commit();
          }
          Log.i(TAG, "********** SQService Volume " + vol);
          am.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
          Toast.makeText(context, "Vibrate Mode Active", Toast.LENGTH_SHORT).show();
          break;
          
        case 3:
          am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
          if(prefs.getBoolean("useMaxVol", false)){
            am.setStreamVolume(AudioManager.STREAM_RING, maxVol, 0);
          }
          else{
            am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          }
          Toast.makeText(context, "Ring Mode Active", Toast.LENGTH_SHORT).show();
          break;
          
      }
  
      edit.putInt("lastState", swState);
      edit.commit();
      
      Intent i = new Intent("net.danshick.switchquiet.SWITCH_STATE_CHANGED");
      i.putExtra("switch_state_value", swState);
      context.sendBroadcast(i);

    }
    
}