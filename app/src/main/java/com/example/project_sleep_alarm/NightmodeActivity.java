package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NightmodeActivity extends AppCompatActivity {

    TextView currentTime;
    UsedAsync asyncTask;
    ProgressHandler handler;
<<<<<<< HEAD
    SetAlarmFrag s = new SetAlarmFrag();
    Calendar calendar = Calendar.getInstance();
=======
>>>>>>> 5f8fc360c2373ec8fb53ce3666e1157ef334b25c

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nightmode);
<<<<<<< HEAD
=======

>>>>>>> 5f8fc360c2373ec8fb53ce3666e1157ef334b25c
        Intent intent = getIntent();
        int wakeupHour = intent.getIntExtra("hour", 0);
        int wakeupMinute = intent.getIntExtra("min",0);

<<<<<<< HEAD
        Toast.makeText(getApplicationContext(), "Selected time: " + s.hour +
                ":" + s.minutes ,Toast.LENGTH_LONG).show();
=======
        Toast.makeText(getApplicationContext(), "Selected time: " + wakeupHour +
                ":" + wakeupMinute ,Toast.LENGTH_LONG).show();
>>>>>>> 5f8fc360c2373ec8fb53ce3666e1157ef334b25c

        currentTime = findViewById(R.id.txtViewTime);
        handler = new ProgressHandler();
        runTime();

        Button btnCancel = findViewById(R.id.btnCancelAlarm);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewMain = new Intent(NightmodeActivity.this, MainActivity.class);
                startActivity(viewMain);
            }
        });

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
<<<<<<< HEAD

                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        //if (s.hour == hour && s.hour == minute){
                          if(true){
                            Intent intent = new Intent(getApplicationContext(),AlarmActivity.class);
                            startActivity(intent);
                        }


=======
>>>>>>> 5f8fc360c2373ec8fb53ce3666e1157ef334b25c
                        Thread.sleep(1000);

                    } catch (Exception ex){

                    }
                }
            }
        });
        thread.start();

        asyncTask = new UsedAsync();
        asyncTask.execute();
    }

    class ProgressHandler extends Handler{
        public void handleMessage(Message msg){
            currentTime.setText(time);
            currentTime.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        }
    }

    class UsedAsync extends AsyncTask<Integer, Integer, Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            return null;
        }

        protected void onPostExecute(Integer integer){
            super.onPostExecute(integer);
        }
    }

}

