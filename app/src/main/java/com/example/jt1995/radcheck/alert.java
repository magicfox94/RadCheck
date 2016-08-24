package com.example.jt1995.radcheck;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jt1995 on 24/08/2016.
 */
public class alert extends Service {
    MyReceiver x = new MyReceiver();


    private class MyReceiver extends BroadcastReceiver {
        MediaPlayer player=null;

        class synchro {
            synchronized void suona(Boolean alert){
                if(alert){
                    player= MediaPlayer.create(alert.this,R.raw.alert);
                    player.start();
                    NotificationManager mManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                    Notification.Builder builder = new Notification.Builder(getBaseContext());
                    builder.setSmallIcon(R.drawable.ic_action_name);
                    builder.setContentTitle("RILEVATA RILEVAZIONE PERICOLOSA");
                    PendingIntent pending= PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
                    builder.setContentIntent(pending);
                    Notification notification = builder.getNotification();
                    mManager.notify(R.drawable.notification_template_icon_bg, notification);
                }
            }
        }

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            boolean alert=false;
            String datapas= arg1.getStringExtra("DATAPASSED");
            JSONArray json=null;
            try {
                json=new JSONArray(datapas);
                for(int i=0;i<json.length();i++){
                    JSONObject json_data = json.getJSONObject(i);
                    if(json_data.getString("ACTION").equals("ALERT")) alert=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                new synchro().suona(alert);
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Storico.MY_ACTION);
        registerReceiver(x, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(x);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
