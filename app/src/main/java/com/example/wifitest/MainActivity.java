package com.example.wifitest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wifitest.R;

public class MainActivity extends AppCompatActivity{
    TextView textView;
    ImageView imageView;
    int[] images= {R.drawable.centro,R.drawable.norte,R.drawable.sur};
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    private BroadcastReceiver wifiStateReceiver =new BroadcastReceiver() {

        String RedNorte= "48:d3:43:83:f0:e9";
        String RedSur= "4a:f1:7f:e7:96:13";
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN);
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            imageView=(ImageView)findViewById(R.id.imagenView);
            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
                    registerReceiver(wifiStateReceiver, intentFilter);
                    if(wifiInfo.getBSSID()!=null && wifiInfo.getBSSID().equals(RedNorte)) {
                        textView.setText("Bienvenido al área Norte");
                        imageView.setImageResource(images[1]);
                    }
                    else if(wifiInfo.getBSSID()!=null && wifiInfo.getBSSID().equals(RedSur)) {
                        textView.setText("Bienvenido al área Sur");
                        imageView.setImageResource(images[2]);
                    }
                    else {
                        textView.setText("Wifi No Válido");
                        imageView.setImageResource(images[0]);
                    }
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    textView.setText("Encienda el WIFI!!!");
                    imageView.setImageResource(images[0]);
                    break;
            }

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }


}