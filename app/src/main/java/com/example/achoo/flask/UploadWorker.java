package com.example.achoo.flask;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.achoo.MainActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class UploadWorker extends Worker {
    private static final String uniqueID = UUID.randomUUID().toString();
    public static String currKey;
    private static final String TAG = MainActivity.class.getName();


    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        String key = getNewKey();
        //call backend to modify key here.
        currKey = key;
        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }

    public static String getCurrKey() {
        return currKey;
    }

    public static void createFirstKey(){
        String key = getNewKey();
        currKey = key;
        Log.i(TAG, "Going to flask Gateway");
        new NetworkAsyncTask().execute();
    }

    private static String getNewKey(){
        //generate random key based on device fingerprint???
        Date currentTime = Calendar.getInstance().getTime();
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(currentTime.toString() + uniqueID);
    }
}
