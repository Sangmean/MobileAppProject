package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

import java.util.ArrayList;

public class SleeplogActivity extends AppCompatActivity {

    SQLiteDatabase db;

    private BarChart barChart;
    private LineChart lineChartBed;
    private LineChart lineChartWakeup;
    private TextView rcmHrs, avgHrs, avgBedHrs, avgWakeupHrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeplog);

        openDB();

        getQuery();

        rcmHrs = findViewById(R.id.txtViewShowTime);
        barChart = findViewById(R.id.barchart);
        avgHrs = findViewById(R.id.txtViewAvgHour);
        lineChartBed = findViewById(R.id.linechartbed);
        avgBedHrs = findViewById(R.id.txtViewAvgBed);
        lineChartWakeup = findViewById(R.id.linechartwakeup);
        avgWakeupHrs = findViewById(R.id.txtViewAvgWakeup);

        getRcmQuery();
//        rcmHrs.setText(rcmTime);

        DrawBarChart();
        DrawLineChart(lineChartBed, 1); //draw line chart for bed time
        DrawLineChart(lineChartWakeup, 2); //draw line chart for wakeup time

    }

    private void getRcmQuery() { //method about recommend sleep hour

        float bedtime;
        float wakeuptime;
        float sleepHour = 0f;
        float avgSleepHr=0f;
        String val = "";
        Integer highRate = 0;
        Integer highRateDiv = 1;

        String queryStr = "SELECT * FROM cycle ORDER BY date;";

        try {
            Cursor cursor = db.rawQuery(queryStr, null);

            if (cursor != null){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    if (highRate < Integer.parseInt(cursor.getString(3))){
                        bedtime = Timeconverter(cursor.getString(1));
                        wakeuptime = Timeconverter(cursor.getString(2));
                        highRate = Integer.valueOf(cursor.getString(3));
                        if (bedtime < wakeuptime) {
                            sleepHour = wakeuptime - bedtime;
                        } else {
                            sleepHour = 24 - bedtime + wakeuptime;
                        }
//                        highRateDiv = 1;
                    }
                    else if (highRate == Integer.parseInt(cursor.getString(3))) {
                        bedtime = Timeconverter(cursor.getString(1));
                        wakeuptime = Timeconverter(cursor.getString(2));
                        if (bedtime < wakeuptime) {
                            sleepHour += wakeuptime - bedtime;
                        } else {
                            sleepHour += 24 - bedtime + wakeuptime;
                        }
                        highRateDiv++;
                    }
                    cursor.moveToNext();
                }
                sleepHour += 7.50;
                highRateDiv++;
                avgSleepHr = sleepHour / highRateDiv;

                val = TimeconverterS(String.valueOf(avgSleepHr));
                if(sleepHour == 7.50){rcmHrs.setText("7h 30 m");} //normal ideal sleep hour
                else{
                    rcmHrs.setText(val);
                }

            }
        } catch (Exception ex){
            Log.e("Recommend time", ex.getMessage());
        }
    }


    private ArrayList getQuery() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Float sleepHrs;
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

            avgHrs.setText("Your Average Sleep Hour: " + sleepHrsS);

        } catch (Exception ex){
            Log.e("AVG sleep hour", ex.getMessage());
        }
        return entries;
    }


    private ArrayList getLineData(int idxCase){
        ArrayList<Entry> entries = new ArrayList<>();
        Float timeTemp = 0f;
        Float timeNum = 0f;
        Float avgTime;
        String avgTimeS ="";
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
                        timeNum += 1;
                        Float time2 = Timeconverter(cursor.getString(callTime));
                        timeTemp += time2;
                        entries.add(new Entry(Integer.parseInt(cursor.getString(0)), time2));
                        cursor.moveToNext();
                    }
                }
            if (timeNum != 0){
                avgTime = timeTemp / timeNum;
                avgTimeS = TimeconverterS2(String.valueOf(avgTime));
            }
            if(callTime == 1){
                avgBedHrs.setText("Your Average Bed Time -> " + avgTimeS);
            } else {
                avgWakeupHrs.setText("Your Average Wake up Time -> " + avgTimeS);
            }

        } catch (Exception ex){
            Log.e("Draw bar error",ex.getMessage());
        }
        return entries;
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
        float minute = (Float.parseFloat(time)-hour)*60;
        String timeI = String.valueOf(Math.round(hour)) + " h " + String.valueOf(Math.round(minute)) + " m";
        return timeI;
    }

    private String TimeconverterS2(String time){
        String[] parts = time.split("\\.");
        float hour = Float.parseFloat((parts[0]));
        float minute = (Float.parseFloat(time)-hour)*60;
        String timeI = String.valueOf(Math.round(hour)) + " : " + String.valueOf(Math.round(minute));
        return timeI;
    }

    private String[] getLables(){
        String[] labels = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        return labels;
    }

    private void DrawBarChart(){
        BarDataSet barDataSet = new BarDataSet(getQuery(), null);
        barDataSet.setBarBorderWidth(0.9f);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(12);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);

        String[] days = getLables();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(days);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        xAxis.setTextSize(12);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.animateY(1500);
//        barChart.setVisibleXRange(0f, 6.75f);
//        barChart.setVisibleYRange(0f,12f, YAxis.AxisDependency.LEFT);
    }

    private void DrawLineChart(LineChart lineChart, int rowIdx){
        LineDataSet lineDataSet = new LineDataSet(getLineData(rowIdx),null);

        //properties of line chart
        lineDataSet.setLineWidth(3);
        lineDataSet.setCircleRadius(4);
//        lineDataSet.setDrawFilled(true);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(12);

        //set xaxis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);

        String[] days = getLables();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(days);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        xAxis.setTextSize(12);

        //set yAxis
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextColor(R.color.colorWhite);
        yAxis.setGranularity(1f);
        yAxis.setTextSize(14);

        LineData lineData = new LineData(lineDataSet);

        lineChart.setData(lineData);
        lineChart.getLegend().setEnabled(false); //hide the legend
//        lineChart.setFitXsSystemWindows(true);
        lineChart.getDescription().setEnabled(false);
        lineChart.animateY(1500);
//        lineChart.setVisibleXRange(0f, 6f);

//        lineChart.setVisibleYRange(0f, 24f, YAxis.AxisDependency.LEFT);
    }

    private void openDB(){
        try {
            db = openOrCreateDatabase("SleepCycle.db", MODE_PRIVATE, null);
        } catch (Exception ex){
            Log.e("DB DEMO", ex.getMessage());
        }
    }


}
