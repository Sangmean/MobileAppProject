package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

    TimePicker timepicker;
    int hour, minutes;
    Button GetTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timepicker = (TimePicker)findViewById(R.id.datePicker1);
        //timepicker.setIs24HourView(true);


        GetTime = (Button)findViewById(R.id.button1);


        GetTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                hour = timepicker.getCurrentHour();
                minutes = timepicker.getCurrentMinute();

                Toast.makeText(MainActivity.this, "Selected time: " + hour +
                        ":" + minutes ,Toast.LENGTH_LONG).show();

                Intent viewNightMode = new Intent(MainActivity.this, NightmodeActivity.class);
                startActivity(viewNightMode);

            }
        });
    }
}