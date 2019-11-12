package com.example.project_sleep_alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

public class SetAlarmFrag extends Fragment {


    private OnTimePickerSetListener onTimePickerSetListener;

    int hour;
    int minutes;

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

                hour = timepicker.getCurrentHour();
                minutes = timepicker.getCurrentMinute();

                //intent.putExtra("hour",hour);
                //intent.putExtra("min", minutes);



                onTimePickerSetListener.onTimePickerSet(hour,minutes);



                // We don't have to show this
//                Toast.makeText(.this, "Selected time: " + hour +
//                        ":" + minutes , Toast.LENGTH_LONG).show();


                Intent intent = new Intent (SetAlarmFrag.this.getActivity(), NightmodeActivity.class);

               // Intent intent = new Intent (SetAlarmFrag.this.getActivity(), NightmodeActivity.class);
                intent.putExtra("hour",hour);
                intent.putExtra("min", minutes);

                SetAlarmFrag.this.getActivity().startActivity(intent);

            }
        });
        return v;
    }


    public interface OnTimePickerSetListener {
        void onTimePickerSet(int hour, int min);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnTimePickerSetListener) {
            onTimePickerSetListener = (OnTimePickerSetListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onTimePickerSetListener = null;
    }


}
