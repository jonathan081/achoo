package com.example.achoo;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;

public class messageGateway {
    private static final String TAG = MainActivity.class.getName();
    private Message mActiveMessage;

    public static MessageListener getMessageListener() {
        return new MessageListener() {
            @Override
            public void onFound(Message message) {
                Log.d(TAG, "Found message: " + new String(message.getContent()));
                // Probably want to add an action here
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
    // Subscribe to messages in the background.
    private void backgroundSubscribe(Activity activity) {
        Log.i(TAG, "Subscribing for background updates.");
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .build();
        Nearby.getMessagesClient(activity).subscribe(getPendingIntent(activity), options);
    }

    private PendingIntent getPendingIntent(Activity activity) {
        return PendingIntent.getBroadcast(activity, 0, new Intent(activity, BeaconMessageReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void publish(String message, Activity activity) {
        Log.i(TAG, "Publishing message: " + message);
        mActiveMessage = new Message(message.getBytes());
        Nearby.getMessagesClient(activity).publish(mActiveMessage);
    }

    private void unpublish(Activity activity) {
        Log.i(TAG, "Unpublishing.");
        if (mActiveMessage != null) {
            Nearby.getMessagesClient(activity).unpublish(mActiveMessage);
            mActiveMessage = null;
        }
    }

}
