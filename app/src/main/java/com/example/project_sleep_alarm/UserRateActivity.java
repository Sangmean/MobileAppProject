package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UserRateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rate);

        Button GoBack = findViewById(R.id.btnFeeling);
        EditText BedTime = findViewById(R.id.editTxtTime1);
        EditText WakeUpTime = findViewById(R.id.editTxtTIme2);

    }
}
