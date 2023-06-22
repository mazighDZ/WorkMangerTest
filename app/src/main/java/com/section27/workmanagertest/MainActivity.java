package com.section27.workmanagertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            button = findViewById(R.id.btn);


            //user Worker manger

        //add constraints
        //Running the worker under certain condition : Device is connecting to wifi, or battery above 60%
        // this exmple we require charging device
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        // send data and receiving data

        //1- Data Creation

        Data data = new Data.Builder().putInt("myKey" ,200).build();

        // however we choose to schedule the work , always use a WorkRequest
        // a WorkRequest (and its subclass ) define how and when it should be run
        WorkRequest countWorkRequest = new OneTimeWorkRequest
                .Builder(DemoWorker.class)
               // .setConstraints(constraints)// this workRequest it will work only if device on charging
                .setInputData(data)
                .build();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This tells WorkManager to schedule and execute the background task defined by the DemoWorker class.
                WorkManager.getInstance(getApplicationContext()).enqueue(countWorkRequest);
            }
        });

        //display status of Worker
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(countWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null){
                            Toast.makeText(getApplicationContext(),"status "+workInfo.getState().name(),Toast.LENGTH_SHORT).show();
                        }

                        // when work finished display data that passed
                        if(workInfo.getState().isFinished()){

                            Data data1 = workInfo.getOutputData();
                            String msg =  data1.getString(DemoWorker.KEY_WORKER );
                            Toast.makeText(getApplicationContext(),"finish data "+msg,Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }
}