package com.example.asmpk01120.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.asmpk01120.MainActivity;
import com.example.asmpk01120.R;

import static android.content.ContentValues.TAG;

public class MyService extends Service {
    private static final String CHANNEL_ID = "1";
    MediaPlayer mediaPlayer;
    String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        String mess = intent.getStringExtra("mess");
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

//            Notification newMessageNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
//                    .setContentTitle("Ting Tịnh!")
//                    .setStyle(new NotificationCompat.InboxStyle()
//                            .addLine("abc")
//                            .setBigContentTitle("AAA")
//                            .setSummaryText("BBB"))
//                    .setContentText("à nhong ha se o")
//                    .setGroup(GROUP_KEY_WORK_EMAIL)
//                    .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
//                    .setGroupSummary(true)
//                    .setAutoCancel(true)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .build();

            Intent intent1 = new Intent(this, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setContentText(mess)
//                    .setStyle(new NotificationCompat.InboxStyle()
//                            .addLine("123")
//                            .addLine("456")
//                            .setBigContentTitle("Tinh Tinh!")
//                            .setSummaryText("ABC"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setGroup(GROUP_KEY_WORK_EMAIL)
                    .setGroupSummary(true)
                    .setAutoCancel(true);
            startForeground(1, builder.build());
        }
        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
