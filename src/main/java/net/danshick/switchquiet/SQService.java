package net.danshick.switchquiet;

import android.app.Service;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.media.AudioManager;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.util.Log;

public class SQService extends Service {

    private String TAG = this.getClass().getSimpleName();
    private AudioManager am;
    private SwitchStateReceiver sReceiver;
    private int vol;
    private int maxVol;
    private int prevSwState;
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "********** SQService Started");
        
        am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        vol = am.getStreamVolume(AudioManager.STREAM_RING);
        maxVol = am.getStreamMaxVolume(AudioManager.STREAM_RING);
        prevSwState = -1;
        
        sReceiver = new SwitchStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("net.danshick.switchquiet.SWITCH_STATE_CHANGED");
        registerReceiver(sReceiver,filter);
        
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "********** SQService Destroyed");
        unregisterReceiver(sReceiver);
    }
        
    class SwitchStateReceiver extends BroadcastReceiver{
      
      public void onReceive(Context context, Intent intent) {
      
        int swState = intent.getIntExtra("switch_state_value", -1);
        Log.i(TAG, "********** SQService Received " + swState);
        
        SharedPreferences prefs = context.getSharedPreferences("sqPrefs", Context.MODE_PRIVATE);
        
        switch(swState){
    
          case 2:
            Log.i(TAG, "********** SQService Previous State " + prevSwState);
            if( prevSwState == -1 || prevSwState == 3 ){
              vol = am.getStreamVolume(AudioManager.STREAM_RING);
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
  
        prevSwState = swState;
        
      }
    
  }

}