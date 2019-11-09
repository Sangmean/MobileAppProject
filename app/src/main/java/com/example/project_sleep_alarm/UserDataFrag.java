package com.example.project_sleep_alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class UserDataFrag extends Fragment {
    public UserDataFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_data, container, false);

        Button GoBack = v.findViewById(R.id.btnFeeling);
        EditText BedTime = v.findViewById(R.id.editTxtTime1);
        EditText WakeUpTime = v.findViewById(R.id.editTxtTIme2);

        return v;
    }
}
