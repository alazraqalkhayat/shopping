package com.example.firebase_prorject;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {


    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        System.out.println("successssssssssssssss");


        String title=message.getNotification().getTitle();
        String body=message.getNotification().getBody();


        final String channelID="FIREBASE_CHANEL_NOTIFICATION";

        NotificationChannel channel=new NotificationChannel(
                channelID,
                "Heads Up Notification",
                NotificationManager.IMPORTANCE_HIGH
        );

        getSystemService(NotificationManager.class).createNotificationChannel(channel);

        ////asdf adsf sdaf dsaf
        Notification.Builder notification=new Notification.Builder(this,channelID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_background).setAutoCancel(true);

        NotificationManagerCompat.from(this).notify(1,notification.build());
        super.onMessageReceived(message);
    }

}
