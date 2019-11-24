package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserRateActivity extends AppCompatActivity {
    UserDataFrag fragment1;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rate);

        fragment1 = new UserDataFrag();

        Button OKButton = (Button) findViewById(R.id.btnFeeling);
        final EditText BedTime = (EditText) findViewById(R.id.editTxtTime1);
        final EditText WakeUpTime = (EditText) findViewById(R.id.editTxtTIme2);
        final EditText Date = (EditText) findViewById(R.id.editTxtTIme3);
        final EditText Rate = (EditText) findViewById(R.id.editTxtTIme4);

        final Spinner spinnerSelectDate =(Spinner)findViewById(R.id.spinnerSelectDate);
        RatingBar ratingSelectFeel = (RatingBar)findViewById(R.id.ratBarFeeling);

        OKButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                openDB();
                addUserCycle(Date.getText().toString(),BedTime.getText().toString(),WakeUpTime.getText().toString(),Rate.getText().toString()); //put values in db
                finish();
            }

        });

        spinnerSelectDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Date.setText(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ratingSelectFeel.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Rate.setText(String.valueOf(rating));
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

    private void addUserCycle(String date, String bedtime, String wakeuptime, String rate){
        long result;
        ContentValues val = new ContentValues();
        val.put("date", date);
        val.put("bedtime", bedtime);
        val.put("wakeuptime", wakeuptime);
        val.put("rate", rate);
        result = db.replace("cycle",null, val);
        if (result != -1) {
            Log.e("DB Demo: ", "Added item "+ date);
        } else {
            Log.e("DB Demo: ", "Error adding "+date);
        }
    }


    private void openDB(){
        try {
            db = openOrCreateDatabase("SleepCycle.db", MODE_PRIVATE, null);
            Toast.makeText(this,"Database opened", Toast.LENGTH_SHORT).show();
        } catch (Exception ex){
            Log.e("DB DEMO", ex.getMessage());
        }
    }


}


