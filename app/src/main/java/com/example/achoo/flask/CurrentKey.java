package com.example.achoo.flask;

import android.util.Log;

import com.example.achoo.MainActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CurrentKey {
    private static final String uniqueID = UUID.randomUUID().toString();
    public static String currKey;
    private static final String TAG = MainActivity.class.getName();
    public static void createFirstKey(){
        String key = getNewKey();
        currKey = key;
        Log.i(TAG, "Going to flask Gateway");
        new NetworkAsyncTask().execute();
    }

    public static void uploadPair (String otherKey) {
        new UploadPairAsyncTask(otherKey).execute();
    }

    private static String getNewKey(){
        //generate random key based on device fingerprint???
        Date currentTime = Calendar.getInstance().getTime();
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(currentTime.toString() + uniqueID);
    }
    public static String getCurrKey() {
        return currKey;
    }


}
