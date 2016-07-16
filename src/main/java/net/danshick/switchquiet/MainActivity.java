package net.danshick.switchquiet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.content.SharedPreferences;

public class MainActivity extends Activity
{
    
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
        maxVolSwitch = (Switch) findViewById(R.id.maxvol_switch);
        
        runSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

          @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
              if(isChecked){
                edit.putBoolean("runSwitch", true);
                edit.commit();
              }
              else{
                edit.putBoolean("runSwitch", false);
                edit.commit();
              }
            }
        });
        
        if(preferences.getBoolean("runSwitch", true)){  
          runSwitch.setChecked(true);
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
        
    }

}
