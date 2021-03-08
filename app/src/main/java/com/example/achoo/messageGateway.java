package com.example.achoo;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;

public class messageGateway {
    private static final String TAG = MainActivity.class.getName();
    private static Message mActiveMessage;

    public static MessageListener getMessageListener(Activity activity) {
        return new MessageListener() {
            @Override
            public void onFound(Message message) {
                Log.d(TAG, "Found message: " + new String(message.getContent()));
                Log.i(TAG, "Found message via PendingIntent: " + message);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, "test")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("HELLO")
                        .setContentText("You have been in proximity with another device")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity);
                notificationManager.notify(1, builder.build());
            }

            @Override
            public void onLost(Message message) {
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };
    }


    public static Message getNewMessage() {
        return new Message("Hello World".getBytes());
    }

    public static SubscribeOptions setSubscribeOptions(){
        return new SubscribeOptions.Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .setCallback(new SubscribeCallback())
                .build();
    }

    public static PendingIntent getPendingIntent(Activity activity) {
        return PendingIntent.getBroadcast(activity, 0, new Intent(activity, BeaconMessageReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    // Subscribe to messages in the background.
    public static void backgroundSubscribe(Activity activity, MessageListener mMessageListener) {
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(Strategy.DEFAULT)
                .build();
        Nearby.getMessagesClient(activity).subscribe(mMessageListener, options);
        Log.i(TAG, "Subscribing for background updates.");
    }

    public static void publish(String message, Activity activity) {
        mActiveMessage = new Message(message.getBytes());
        Nearby.getMessagesClient(activity).publish(mActiveMessage);
        Log.i(TAG, "Publishing message: " + message);
    }

    public static void unpublish(Activity activity) {
        Log.i(TAG, "Unpublishing.");
        if (mActiveMessage != null) {
            Nearby.getMessagesClient(activity).unpublish(mActiveMessage);
            mActiveMessage = null;
        }
    }


    // Subscribe to receive messages.
    private void subscribeForeground(MessageListener mMessageListener, Activity activity) {
        Log.i(TAG, "Subscribing.");
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .build();
        Nearby.getMessagesClient(activity).subscribe(mMessageListener, options);
    }
    private void unsubscribeForeGround(Activity activity, MessageListener mMessageListener) {
        Log.i(TAG, "Unsubscribing.");
        Nearby.getMessagesClient(activity).unsubscribe(mMessageListener);
    }

}
