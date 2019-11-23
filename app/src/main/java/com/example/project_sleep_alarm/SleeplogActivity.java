package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
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
import java.util.ArrayList;
import java.util.List;

public class SleeplogActivity extends AppCompatActivity {

    SQLiteDatabase db;
    StringBuilder outputText = new StringBuilder();

    private BarChart barChart;
    private LineChart lineChartBed;
    private LineChart lineChartWakeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeplog);

        //db ex
        createDB();
        createTables(); //ready to db

        List<String[]> MySleepCycle = ReadCSV(); //read csv file

        for (int i = 1; i < MySleepCycle.size(); i++) {
            String date = MySleepCycle.get(i)[0];
            Integer bedtime = Integer.parseInt(MySleepCycle.get(i)[1]);
            Integer wakeuptime = Integer.parseInt(MySleepCycle.get(i)[2]);
            Double rate = Double.parseDouble(MySleepCycle.get(i)[3]);
            addUserCycle(date,bedtime,wakeuptime,rate); //put values in db
        }

        barChart = findViewById(R.id.barchart);
        lineChartBed = findViewById(R.id.linechartbed);
        lineChartWakeup = findViewById(R.id.linechartwakeup);

        DrawBarChart();
        DrawLineChart(lineChartBed, 1);
        DrawLineChart(lineChartWakeup, 2);
    }

    //create DB
    private void createDB(){
        try {
            db = openOrCreateDatabase("SleepCycle.db", Context.MODE_PRIVATE, null);
            Toast.makeText(this,"Database ready",Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Log.e("DB DEMO: ", ex.getMessage());
        }
    }

    //create Tables
    private void createTables() {
        try {
            String dropCycleTable = "DROP TABLE IF EXISTS cycle;";

            String createCycle = "CREATE TABLE cycle (date TEXT PRIMARY KEY, bedtime INTEGER, wakeuptime INTEGER, rate DECIMAL);";

            db.execSQL(dropCycleTable);
            db.execSQL(createCycle);
            Log.e("DB DEMO:", "Create Tables");
        } catch (Exception ex) {
            Log.e("DB DEMO: ","Error in creating tables "+ ex.getMessage());
        }
    }

    private List ReadCSV() {
        List<String[]> userCycle = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.recordedtime);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                userCycle.add(row);
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
        return userCycle;
    }

    //add user information in table
    private void addUserCycle(String date, Integer bedtime, Integer wakeuptime, Double rate){
        long result;
        ContentValues val = new ContentValues();
        val.put("date", date);
        val.put("bedtime", bedtime);
        val.put("wakeuptime", wakeuptime);
        val.put("rate", rate);
        result = db.insert("cycle",null, val);
        if (result != -1) {
            Log.e("DB Demo: ", "Added item "+ date);
        } else {
            Log.e("DB Demo: ", "Error adding "+date);
        }
    }

    private ArrayList getData(){
        List<String[]> MySleepCycle = ReadCSV(); //read csv file
        ArrayList<BarEntry> entries = new ArrayList<>();

        try{
            for (int i = 1; i < MySleepCycle.size(); i++) {
                Integer bedtime = Integer.parseInt(MySleepCycle.get(i)[1]);
                Integer wakeuptime = Integer.parseInt(MySleepCycle.get(i)[2]);
                float sleepHour;
                if (bedtime < wakeuptime) {
                    sleepHour = wakeuptime - bedtime;
                } else {
                    sleepHour = 24 - bedtime + wakeuptime;
                }
                entries.add(new BarEntry(i, sleepHour));
            }

        } catch (Exception ex){
            Log.e("Draw bar error",ex.getMessage());
        }

        return entries;
    }

    private ArrayList getLineData(int idxCase){
        List<String[]> MySleepCycle = ReadCSV(); //read csv file
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
        try {
            for (int i = 1; i < MySleepCycle.size(); i++){
                entries.add(new Entry(i, Integer.parseInt(MySleepCycle.get(i)[callTime])));
            }

        } catch (Exception ex){
            Log.e("Draw bar error",ex.getMessage());
        }
        return entries;
    }

    private String[] getLables(){
        String[] labels = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        return labels;
    }

    private void DrawBarChart(){
        BarDataSet barDataSet = new BarDataSet(getData(), "Inducesmile");
        barDataSet.setBarBorderWidth(0.9f);
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        String[] days = getLables();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(days);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.getXAxis().setTextColor(android.R.color.white);
        barChart.setFitBars(true);
        barChart.animateY(2000);
        barChart.invalidate();
    }

    private void DrawLineChart(LineChart lineChart, int rowIdx){
        /* https://namget.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-MPAndroidChart-LineChart-%EC%86%8D%EC%84%B1-%EC%A0%95%EB%A6%AC-Example*/
        LineDataSet lineDataSet = new LineDataSet(getLineData(rowIdx),null);

        //properties of line chart
        lineDataSet.setColors(R.color.colorPrimary);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setDrawFilled(true);

        //set xaxis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        String[] days = getLables();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(days);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.animateY(2000);
    }

}
