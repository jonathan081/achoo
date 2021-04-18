package com.example.achoo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.achoo.flask.UploadWorker;

import java.util.UUID;

public class InfectedWorker extends Worker {
    private static final String uniqueID = UUID.randomUUID().toString();
    public static String currKey;

    public InfectedWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        String currKey = UploadWorker.getCurrKey();
        //call backend to modify key here.
        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}
