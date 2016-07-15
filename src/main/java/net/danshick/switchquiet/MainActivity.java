package net.danshick.switchquiet;

import android.app.Activity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;

public class MainActivity extends Activity
{
    
    private TextView txtView;
    private NotificationReceiver nReceiver;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        startService(new Intent(this, SQService.class));
        
        txtView = new TextView(this);
        setContentView(txtView);
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("net.danshick.switchquiet.INTERRUPTION_FILTER_CHANGED");
        registerReceiver(nReceiver,filter);
        
    }
    
    class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("if_notification");
            txtView.setText(temp);
        }
    }

}
