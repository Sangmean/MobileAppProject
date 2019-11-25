package com.example.project_sleep_alarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
    String time;
    ProgressHandler handler;
    UsedAsync asyncTask;
    EditText CurrentTime;

    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mediaPlayer = MediaPlayer.create(this,R.raw.alarm);
        mediaPlayer.start();

        CurrentTime = findViewById(R.id.txtTime);
        Button stop = findViewById(R.id.stopBtn);
        Button snooze = findViewById(R.id.snoozeBtn);

        handler = new ProgressHandler();
        runTime();

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(returnMain);
            }
        });

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm();
                finish();
//                Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(returnMain);
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }

    public void alarm(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),AlarmActivity.class));
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,5000);
    }

    public void runTime(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try{
                        time = sdf.format(new Date(System.currentTimeMillis()));
                        Message message = handler.obtainMessage();
                        handler.sendMessage(message);

                        Thread.sleep(1000);

                    } catch (Exception ex){
                        Log.e(ex.getMessage(),"Wrong");
                    }
                }
            }
        });
        thread.start();

        asyncTask = new AlarmActivity.UsedAsync();
        asyncTask.execute();
    }

    class ProgressHandler extends Handler {
        public void handleMessage(Message msg){
            CurrentTime.setText(time);
            CurrentTime.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        }
    }

    class UsedAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            return null;
        }

        protected void onPostExecute(Integer integer){
            super.onPostExecute(integer);
        }
    }
}
