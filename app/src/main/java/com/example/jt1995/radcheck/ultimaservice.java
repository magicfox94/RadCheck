package com.example.jt1995.radcheck;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Jt1995 on 24/08/2016.
 */
public class ultimaservice extends Service {
    final static String MY_ACTION = "ULTIMA";

    class mythread2 extends Thread {
        @Override
        public void run() {
            while (true) {
                new HttpGetTask().execute(" ");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class HttpGetTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            InputStream is = null;
            StringBuilder sb = null;
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            //http post

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://radcheck.altervista.org/ultimaril.php");
                httppost.setEntity((HttpEntity) new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch (Exception e) {
                Log.e("log_tag", "Errore nella connessione http" + e.toString());
            }


            if (is != null) {
                //converto la risposta in stringa
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line = "0";

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    is.close();
                    result = sb.toString();

                } catch (Exception e) {
                    Log.e("log_tag", "Errore su result " + e.toString());
                }
            } else {//is è null e non ho avuto risposta
            }
            Intent intent = new Intent();
            intent.setAction(MY_ACTION);
            intent.putExtra("LAST", result);
            sendBroadcast(intent);
            return result;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mythread2 thr = new mythread2();
        thr.start();
        return super.onStartCommand(intent, flags, startId);
    }
}

