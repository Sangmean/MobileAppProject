package com.example.project_sleep_alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

public class SetAlarmFrag extends Fragment {


    public SetAlarmFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_set_alarm, container, false);
        final TimePicker timepicker = v.findViewById(R.id.datePicker1);

        final Button GetTime = v.findViewById(R.id.btnGoToBed);

        GetTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int hour = timepicker.getCurrentHour();
                int minutes = timepicker.getCurrentMinute();


                // We don't have to show this
//                Toast.makeText(.this, "Selected time: " + hour +
//                        ":" + minutes , Toast.LENGTH_LONG).show();

                Intent intent = new Intent (SetAlarmFrag.this.getActivity(), NightmodeActivity.class);
                SetAlarmFrag.this.getActivity().startActivity(intent);

            }
        });
        return v;
    }

}
