package com.example.asmpk01120.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.asmpk01120.DangNhap;
import com.example.asmpk01120.MainActivity;
import com.example.asmpk01120.R;

public class Broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfoWF = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean WIFI = networkInfoWF.isConnected();
        NetworkInfo networkInfomGPRS = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean GPRS = networkInfomGPRS.isConnected();

        if (WIFI == false && GPRS == false) {
            MainActivity.logincheck = false;
            Intent i = new Intent(context, DangNhap.class);
            context.startActivity(i);
        }
    }
}
