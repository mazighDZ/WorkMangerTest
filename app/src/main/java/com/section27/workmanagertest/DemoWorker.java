package com.section27.workmanagertest;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DemoWorker extends Worker {
    public static final String KEY_WORKER ="sent";

    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // do the work here , count until 1000 in foreground , start from passed data number
        //getting data from InputData
        Data data = getInputData();

        int countDataReceived = data.getInt("myKey",0);

        for (int i = countDataReceived ; i<1000;i++ ){
            Log.i("MyTag" , "count is : "+ i);
        }
        //Sending Data and Done info

        Data dataToSend =new Data.Builder()
                .putString(KEY_WORKER, "Task Done Successfully ").build();

        // Set the output data before returning Result.success()


        //Result.success() the work finished successfully
        //Result.failure() the work failed
        //Result.retry() the work failed and should be tried at another time accourding to its
        //retry policy

        return Result.success(dataToSend);// passing data after success
    }
}
