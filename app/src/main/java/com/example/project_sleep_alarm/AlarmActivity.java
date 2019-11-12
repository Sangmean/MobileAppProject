package com.example.project_sleep_alarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AlarmActivity extends AppCompatActivity {

    MainActivity mainActivity = new MainActivity();

    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mediaPlayer = MediaPlayer.create(this,R.raw.jig);
        mediaPlayer.start();
        alarmDialog();

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

    public void alarmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("get up");
        builder.setPositiveButton("take a nape", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarm();
                finish();
            }
        });
        builder.setNegativeButton("cancel alarm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    public void alarm(){
        //get system alarm service
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //triggerTime(ms)
        long triggerTime = 2000;
        Intent intent = new Intent(getApplicationContext(),AlarmActivity.class);
        PendingIntent op = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //excute only once
        am.set(AlarmManager.RTC,triggerTime,op);

        //am.setRepeating(AlarmManager.RTC,triggerTime,2000,op);


    }
}
