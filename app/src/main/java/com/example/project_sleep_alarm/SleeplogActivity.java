package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class SleeplogActivity extends AppCompatActivity {

    SQLiteDatabase db;
    StringBuilder outputText = new StringBuilder();

    private BarChart barChart;
    private LineChart lineChartBed;
    private LineChart lineChartWakeup;
    private TextView avgHrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeplog);

        openDB();

        getQuery();
        barChart = findViewById(R.id.barchart);
        lineChartBed = findViewById(R.id.linechartbed);
        lineChartWakeup = findViewById(R.id.linechartwakeup);
        avgHrs = findViewById(R.id.txtViewAvgHour);



        DrawBarChart();
        DrawLineChart(lineChartBed, 1);
        DrawLineChart(lineChartWakeup, 2);
    }


    private ArrayList getQuery() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Float sleepHrs = 0f;
        Float sleepTemp = 0f;
        Float sleepNum = 0f;
        String sleepHrsS = "";
        String queryStr = "SELECT * FROM cycle ORDER BY date;";
        try {
            Cursor cursor = db.rawQuery(queryStr, null);
            if (cursor != null){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    sleepNum += 1;
                    Float bedtime = Timeconverter(cursor.getString(1));
                    Float wakeuptime = Timeconverter(cursor.getString(2));
                    float sleepHour;
                    if (bedtime < wakeuptime) {
                        sleepHour = wakeuptime - bedtime;
                    } else {
                        sleepHour = 24 - bedtime + wakeuptime;
                    }
                    sleepTemp += sleepHour;
                    entries.add(new BarEntry(Integer.parseInt(cursor.getString(0)), sleepHour));
                    cursor.moveToNext();
                }

            }
            if (sleepNum != 0){
                sleepHrs = sleepTemp / sleepNum;

                sleepHrsS = TimeconverterS(String.valueOf(sleepHrs));
            }

            avgHrs.setText("Average Sleep Hour: " + sleepHrsS);


        } catch (Exception ex){
            Log.e("DB DEMO", ex.getMessage());
        }
        return entries;
    }


    private ArrayList getLineData(int idxCase){
        ArrayList<Entry> entries = new ArrayList<>();
        int callTime = 1;
        switch (idxCase){
            case 1:
                callTime = 1;
                break;
            case 2:
                callTime = 2;
                break;
        }
        String queryStr = "SELECT * FROM cycle ORDER BY date;";
        try {
            Cursor cursor = db.rawQuery(queryStr, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        Float time2 = Timeconverter(cursor.getString(callTime));
                        entries.add(new Entry(Integer.parseInt(cursor.getString(0)), time2));
                        cursor.moveToNext();
                    }
                }

        } catch (Exception ex){
            Log.e("Draw bar error",ex.getMessage());
        }
        return entries;
    }

    private String[] getLables(){
        String[] labels = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        return labels;
    }

    private void DrawBarChart(){
        BarDataSet barDataSet = new BarDataSet(getQuery(), "Inducesmile");
        barDataSet.setBarBorderWidth(0.9f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);

        String[] days = getLables();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(days);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateY(2000);
    }


    private float Timeconverter(String time){
        String[] parts = time.split("\\:");
        float hour = Float.parseFloat((parts[0]));
        float minute = Float.parseFloat(parts[1])/60;
        float timeI = hour + minute;
        return timeI;
    }


    private String TimeconverterS(String time){
        String[] parts = time.split("\\.");
        float hour = Float.parseFloat((parts[0]));
        float minute = Float.parseFloat(parts[1])*60/100;
        String timeI = String.valueOf(Math.round(hour)) + " h " + String.valueOf(Math.round(minute)) + " m";
        return timeI;
    }



    private void DrawLineChart(LineChart lineChart, int rowIdx){
        /* https://namget.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-MPAndroidChart-LineChart-%EC%86%8D%EC%84%B1-%EC%A0%95%EB%A6%AC-Example*/
        LineDataSet lineDataSet = new LineDataSet(getLineData(rowIdx),null);

        //properties of line chart
        lineDataSet.setColors(R.color.colorPrimary);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(4);
        lineDataSet.setDrawFilled(true);

        //set xaxis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);

        String[] days = getLables();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(days);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        LineData lineData = new LineData(lineDataSet);

        lineChart.setData(lineData);
        lineChart.animateY(2000);
    }

    private void openDB(){
        try {
            db = openOrCreateDatabase("SleepCycle.db", MODE_PRIVATE, null);
            Toast.makeText(this,"Database opened", Toast.LENGTH_SHORT).show();
        } catch (Exception ex){
            Log.e("DB DEMO", ex.getMessage());
        }
    }


    private List ReadCSV(){
        List<String[]> resultList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.recordedtime);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                resultList.add(row);
            }
        } catch (IOException ex){
            throw new RuntimeException("Error reading csv file "+ ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                throw new RuntimeException("Error in closing stream "+ ex);
            }
        }


        return resultList;
    }


}
