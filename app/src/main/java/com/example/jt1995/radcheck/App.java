package com.example.jt1995.radcheck;

import android.app.Application;
import android.content.Intent;

/**
 * Created by Jt1995 on 24/08/2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startActivity(new Intent(this,MainActivity.class));
    }
}
