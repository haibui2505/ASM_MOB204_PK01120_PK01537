package com.example.asmpk01120.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.asmpk01120.MainActivity;
import com.example.asmpk01120.R;

import static android.content.ContentValues.TAG;

public class MyService extends Service {
    MediaPlayer mediaPlayer;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String off = intent.getExtras().getString("extra");
//        if (off != null){
//            if (off.equals("off")){
//            }
//        }else {
        Log.d(TAG, "MyService.java: RUN!" );

        int notifi = intent.getIntExtra("notifi",0);
            String mess = intent.getStringExtra("mess");
            Intent intent1 = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("It's time!")
                    .setContentText(mess)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_ALL);

            //notifi
            notificationManager.notify(notifi, builder.build());
            mediaPlayer = MediaPlayer.create(this, R.raw.ambao);
            mediaPlayer.start();
//        }



        return START_STICKY;
    }
}
