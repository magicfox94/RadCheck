package com.example.jt1995.radcheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,ultimaservice.class));
        startService(new Intent(this,Storico.class));
        startService(new Intent(this,alert.class));
        Button storico=(Button)findViewById(R.id.button);
        Button ultima=(Button)findViewById(R.id.button2);
        ultima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,StoricoAct.class);
                MainActivity.this.startActivity(intent);
            }
        });
        storico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ultimact.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
