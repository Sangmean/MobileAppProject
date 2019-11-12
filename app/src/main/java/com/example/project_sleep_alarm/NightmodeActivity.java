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
    int wakeupHour;
    int wakeupMinute;

    MainActivity mainActivity = new MainActivity();
    Calendar calendar = Calendar.getInstance();
    SetAlarmFrag s = new SetAlarmFrag();

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nightmode);

        Intent intent = getIntent();
        wakeupHour = intent.getIntExtra("hour", 0);
        wakeupMinute = intent.getIntExtra("min",0);


//        Toast.makeText(getApplicationContext(), "Selected time: " + s.hour +
//                ":" + s.minutes ,Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "Selected time: " + wakeupHour +
                ":" + wakeupMinute ,Toast.LENGTH_LONG).show();


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


                        int hour = calendar.get(Calendar.HOUR);
                        int minute = calendar.get(Calendar.MINUTE);

                        if (wakeupHour == hour && wakeupMinute == minute){
                            Intent intent = new Intent(getApplicationContext(),AlarmActivity.class);
                            startActivity(intent);
                        }


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

