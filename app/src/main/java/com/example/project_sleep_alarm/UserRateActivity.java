package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserRateActivity extends AppCompatActivity {
    UserDataFrag fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rate);

        fragment1 = new UserDataFrag();

        Button GoBack = (Button) findViewById(R.id.btnFeeling);
        EditText BedTime = (EditText) findViewById(R.id.editTxtTime1);
        EditText WakeUpTime = (EditText) findViewById(R.id.editTxtTIme2);

        GoBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

            replaceFragment(fragment1);
            }

        });

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}


