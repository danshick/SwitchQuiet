package net.danshick.switchquiet;

import android.content.Context;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.util.Log;

public class SQService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private AudioManager am;
    private int vol;
    private int prevIF;
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "********** SQService Started");
        
        am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        vol = am.getStreamVolume(AudioManager.STREAM_RING);
        
    }
    
    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.i(TAG, "********** SQService Listener Connected");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    public void onInterruptionFilterChanged(int interruptionFilter){
      super.onInterruptionFilterChanged(interruptionFilter);
      Log.i(TAG, "********** SQService onInterruptionFilterChanged: " + interruptionFilter);
            
      switch(interruptionFilter){
        
        case INTERRUPTION_FILTER_PRIORITY:
          
          if(prevIF == INTERRUPTION_FILTER_ALL){
            vol = am.getStreamVolume(AudioManager.STREAM_RING);
          }
          am.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
          break;
          
        case INTERRUPTION_FILTER_ALL: 
          am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
          am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          //am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          //am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          //am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          //am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          //am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          //am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          //am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          //am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          //am.setStreamVolume(AudioManager.STREAM_RING, vol, 0);
          break;
          
      }
      
      Intent i = new Intent("net.danshick.switchquiet.INTERRUPTION_FILTER_CHANGED");
      i.putExtra("if_notification", Integer.toString(interruptionFilter));
      sendBroadcast(i);
      
      prevIF = interruptionFilter;
      
    }

}