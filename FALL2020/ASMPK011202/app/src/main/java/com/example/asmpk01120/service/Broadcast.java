package com.example.asmpk01120.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class Broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String off =  intent.getExtras().getString("extra");
        int notifi = intent.getIntExtra("notifi",0);
        String mess = intent.getStringExtra("mess");

        Log.d(TAG, "Broastcast.java: RUN!" );
        Intent  intent2 = new Intent(context, MyService.class);
        intent2.putExtra("extra", off);
        intent2.putExtra("notifi", notifi);
        intent2.putExtra("mess", mess);
        context.startService(intent2);
    }
}
