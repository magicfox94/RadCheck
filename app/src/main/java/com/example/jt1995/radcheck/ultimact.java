package com.example.jt1995.radcheck;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jt1995 on 24/08/2016.
 */
public class ultimact extends Activity {
    MyReceiver x = new MyReceiver();
    final ArrayList<String> list = new ArrayList<String>();
    GridView grid=null;


    private class MyReceiver extends BroadcastReceiver {
        JSONObject jsonObject=null;

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            list.clear();
            list.add("ID_DET");
            list.add("ID_ROOM");
            list.add("DATE");
            list.add("VALUE");
            list.add("ACTION");
            String datapas= arg1.getStringExtra("LAST");
            JSONArray json=null;
            try {
                json=new JSONArray(datapas);
                for(int i=0;i<json.length();i++){
                    JSONObject json_data = json.getJSONObject(i);
                    list.add(json_data.getString("ID_DET"));
                    list.add(json_data.getString("ID_ROOM"));
                    list.add(json_data.getString("DATE"));
                    list.add(json_data.getString("VALUE"));
                    list.add(json_data.getString("ACTION"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ultimact.this,android.R.layout.simple_list_item_1,list);
            grid.setAdapter(adapter);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ultima);
        grid=(GridView)findViewById(R.id.gridViewultima);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ultimaservice.MY_ACTION);
        registerReceiver(x, intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(x);

    }
}
