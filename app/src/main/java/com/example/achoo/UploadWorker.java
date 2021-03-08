package com.example.achoo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;
import java.util.Date;

public class UploadWorker extends Worker {
    public static String currKey;

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

    public void createFirstKey(){
        String key = getNewKey();
        //call backend func for keygen.
    }

    private String getNewKey(){
        //generate random key based on device fingerprint???
        Date currentTime = Calendar.getInstance().getTime();
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex("filler text to be changed");
    }
}
