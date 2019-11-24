package com.example.project_sleep_alarm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.BarEntry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class UserDataFrag extends Fragment {

    SQLiteDatabase db;

    List<String> userDataList = new ArrayList<>();
    List<Integer> userDataListIcon = new ArrayList<>();
//    private TextView avgRecHrs;

    private void addItems(){
        userDataList.add("Record Feeling");
        userDataList.add("Sleep Graph");
//        userDataList.add("Record Voice");
        userDataListIcon.add(R.drawable.recordfeel);
        userDataListIcon.add(R.drawable.graphicon);
//        userDataListIcon.add(R.drawable.voiceicon);
    }
    public UserDataFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_data, container, false);

        addItems();
        ListView listViewItems = v.findViewById(R.id.listViewItem);
//        avgRecHrs = v.findViewById(R.id.textViewrRecommend);
//        avgRecHrs.setText(getQuery());
//        onResume();

        listViewItems.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, userDataList));

        listViewItems.setAdapter(new MyListviewAdapter(this.getActivity(), userDataList,userDataListIcon));

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(UserDataFrag.this.getActivity(), UserRateActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(UserDataFrag.this.getActivity(), SleeplogActivity.class));
                        break;
                }
            }
        });

        return v;
    }





//
//    private String getQuery() {
//        float bedtime = 0f;
//        float wakeuptime = 0f;
//        float newbedtime = 0f;
//        float newwakeuptime = 0f;
//        float sleepHour = 0f;
//        float avgSleepHr = 0f;
//        String val = "";
//        Integer highRate = 0;
//        Integer highRateDiv = 1;
//        String queryStr = "SELECT * FROM cycle ORDER BY date;";
//        Log.v("kisoon","1");
//
//        try {
//            Log.v("kisoon","123");
//            Cursor cursor = db.rawQuery(queryStr, null);
//            Log.v("kisoon","1234");
//
//            if (cursor != null){
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
//                    if (highRate < Integer.parseInt(cursor.getString(3))){
//                        bedtime = Timeconverter(cursor.getString(1));
//                        wakeuptime = Timeconverter(cursor.getString(2));
//                        highRate = Integer.valueOf(cursor.getString(3));
//                        if (bedtime < wakeuptime) {
//                            sleepHour = wakeuptime - bedtime;
//                        } else {
//                            sleepHour = 24 - bedtime + wakeuptime;
//                        }
//                        highRateDiv = 1;
//                    }
//                    else if (highRate == Integer.parseInt(cursor.getString(3))) {
//                        bedtime = Timeconverter(cursor.getString(1));
//                        wakeuptime = Timeconverter(cursor.getString(2));
//                        if (bedtime < wakeuptime) {
//                            sleepHour += wakeuptime - bedtime;
//                        } else {
//                            sleepHour += 24 - bedtime + wakeuptime;
//                        }
//                        highRateDiv++;
//                    }
//                    cursor.moveToNext();
//                }
//                sleepHour += 7.50;
//                highRateDiv++;
//                avgSleepHr = sleepHour / highRate;
//                val = TimeconverterS(String.valueOf(avgSleepHr));
//                Log.v("kisoon",val);
//            }
//
//
//        } catch (Exception ex){
//            Log.e("DB DEMO", ex.getMessage());
//        }
//
//        return val;
//    }

//
//    private float Timeconverter(String time){
//        String[] parts = time.split("\\:");
//        float hour = Float.parseFloat((parts[0]));
//        float minute = Float.parseFloat(parts[1])/60;
//        float timeI = hour + minute;
//        return timeI;
//    }
//
//
//    private String TimeconverterS(String time){
//        String[] parts = time.split("\\.");
//        float hour = Float.parseFloat((parts[0]));
//        float minute = Float.parseFloat(parts[1])*60/100;
//        String timeI = ("Recommended Sleep Hour is" + String.valueOf(Math.round(hour)) + " h " + String.valueOf(Math.round(minute)) + " m");
//        return timeI;
//    }


}
