package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class UserRateActivity extends AppCompatActivity {
    UserDataFrag fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rate);

        fragment1 = new UserDataFrag();

        Button GoBack = (Button) findViewById(R.id.btnFeeling);
        final EditText BedTime = (EditText) findViewById(R.id.editTxtTime1);
        final EditText WakeUpTime = (EditText) findViewById(R.id.editTxtTIme2);

        GoBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                finish();
            }

        });

        BedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog TimePicker;
                TimePicker = new TimePickerDialog(UserRateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        BedTime.setText( selectedHour + ":" + selectedMinute);

                    }
                }, hour, minute, false);
                TimePicker.setTitle("Select Time");
                TimePicker.show();

            }
        });

        WakeUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog TimePicker;
                TimePicker = new TimePickerDialog(UserRateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        WakeUpTime.setText( selectedHour + ":" + selectedMinute);

                    }
                }, hour, minute, false);
                TimePicker.setTitle("Select Time");
                TimePicker.show();

            }
        });

    }


}


