package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class UserRateActivity extends AppCompatActivity {
    UserDataFrag fragment1;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rate);

        fragment1 = new UserDataFrag();

        Button GoBack = findViewById(R.id.btnGoBack);
        final EditText BedTime = findViewById(R.id.editTxtTime1);
        final EditText WakeUpTime = findViewById(R.id.editTxtTIme2);
        final EditText Date = findViewById(R.id.editTxtTIme3);
        final EditText Rate = findViewById(R.id.editTxtTIme4);

        final Spinner spinnerSelectDate = findViewById(R.id.spinnerSelectDate);
        RatingBar ratingSelectFeel = findViewById(R.id.ratBarFeeling);

        Button submitbtn = findViewById(R.id.btnSubmit);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDB();
                addUserCycle(Date.getText().toString(),BedTime.getText().toString(),WakeUpTime.getText().toString(),Rate.getText().toString()); //put values in db
                Toast.makeText(UserRateActivity.this,"Complet to Update your Sleep graph",Toast.LENGTH_SHORT).show();
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
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
            Log.e("DB add table ", "Added item "+ date);
        } else {
            Log.e("DB add table:", "Error adding "+date);
        }
    }


    private void openDB(){
        try {
            db = openOrCreateDatabase("SleepCycle.db", MODE_PRIVATE, null);
        } catch (Exception ex){
            Log.e("DB DEMO", ex.getMessage());
        }
    }


}


