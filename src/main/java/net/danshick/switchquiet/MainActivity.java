package net.danshick.switchquiet;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.content.SharedPreferences;

public class MainActivity extends Activity
{
    
    private SwitchStateReceiver sReceiver;
    private Switch runSwitch;
    private Switch bootSwitch;
    private Switch maxVolSwitch;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
      
        super.onCreate(savedInstanceState);
        
        SharedPreferences preferences = getSharedPreferences("sqPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor edit = preferences.edit();
        
        setContentView(R.layout.main_activity);
        runSwitch = (Switch) findViewById(R.id.run_switch);
        bootSwitch = (Switch) findViewById(R.id.boot_switch);
        maxVolSwitch = (Switch) findViewById(R.id.maxvol_switch);
        
        runSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

          @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
              if(isChecked){
                startService(new Intent(getApplicationContext(), SQService.class));
              }
              else{
                stopService(new Intent(getApplicationContext(), SQService.class));
              }
            }
        });
        
        if(isMyServiceRunning(SQService.class)){  
          runSwitch.setChecked(true);
        }
        
        bootSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
          @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
              if(isChecked){
                edit.putBoolean("startOnBoot", true);
                edit.commit();
              }
              else{
                edit.putBoolean("startOnBoot", false);
                edit.commit();
              }
            }
        });
        
        if(preferences.getBoolean("startOnBoot", true)){  
          bootSwitch.setChecked(true);
        }
        
        maxVolSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
          @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
              if(isChecked){
                edit.putBoolean("useMaxVol", true);
                edit.commit();
              }
              else{
                edit.putBoolean("useMaxVol", false);
                edit.commit();
              }
            }
        });
        
        if(preferences.getBoolean("useMaxVol", false)){  
          maxVolSwitch.setChecked(true);
        }
        
        sReceiver = new SwitchStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("net.danshick.switchquiet.SWITCH_STATE_CHANGED");
        registerReceiver(sReceiver,filter);
        
    }
    
    private boolean isMyServiceRunning(Class<?> serviceClass) {
      ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
      for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.getName().equals(service.service.getClassName())) {
          return true;
        }
      }
      return false;
    }
    
    class SwitchStateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int swStateVal = intent.getIntExtra("switch_state_value", -1);
        }
    }

}
