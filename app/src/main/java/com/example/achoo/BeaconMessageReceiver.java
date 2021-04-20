package com.example.achoo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

public class BeaconMessageReceiver {
    private static final String TAG = MainActivity.class.getName();

    // @Override
    public void onReceive(Context context, Intent intent) {
        Nearby.getMessagesClient(context).handleIntent(intent, new MessageListener() {
            @Override
            public void onFound(Message message) {
                Log.i(TAG, "Found message via PendingIntent: " + message);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notif")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("HELLO")
                        .setContentText("You have received a ping from another device")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(1, builder.build());
            }

            @Override
            public void onLost(Message message) {
                Log.i(TAG, "Lost message via PendingIntent: " + message);
            }
        });
    }
}