package com.example.achoo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
            }

            @Override
            public void onLost(Message message) {
                Log.i(TAG, "Lost message via PendingIntent: " + message);
            }
        });
    }
}