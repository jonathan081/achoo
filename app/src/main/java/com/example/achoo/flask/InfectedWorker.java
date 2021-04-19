package com.example.achoo.flask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.achoo.MainActivity;

public class InfectedWorker extends Worker {
    public static String currKey;

    public InfectedWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @SuppressLint("WrongThread")
    @Override
    public Result doWork() {
        String currKey = UploadWorker.getCurrKey();
        new InfCheckAsyncTask(getApplicationContext()).execute();
        //call backend to modify key here.
        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}
