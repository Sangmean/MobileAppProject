package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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


import java.util.ArrayList;

public class SleeplogActivity extends AppCompatActivity {

    private BarChart barChart;
    private LineChart lineChartBed;
    private LineChart lineChartWakeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeplog);

        barChart = findViewById(R.id.barchart);
        lineChartBed = findViewById(R.id.linechartbed);
        lineChartWakeup = findViewById(R.id.linechartwakeup);

        DrawBarChart();
        DrawLineChart(lineChartBed);
        DrawLineChart(lineChartWakeup);
    }

    private ArrayList getData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 70f));
        entries.add(new BarEntry(5f, 60f));
        return entries;
    }

    private ArrayList getLineData(){
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 30f));
        entries.add(new Entry(1f, 80f));
        entries.add(new Entry(2f, 60f));
        entries.add(new Entry(3f, 50f));
        entries.add(new Entry(4f, 70f));
        entries.add(new Entry(5f, 60f));
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
        barChart.animateXY(3000, 3000);
        barChart.invalidate();
    }

    private void DrawLineChart(LineChart lineChart){
        /* https://namget.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-MPAndroidChart-LineChart-%EC%86%8D%EC%84%B1-%EC%A0%95%EB%A6%AC-Example*/
        LineDataSet lineDataSet = new LineDataSet(getLineData(),null);


        LineData lineData = new LineData(lineDataSet);
//        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

//        /*dataset.setDrawCubic(true); //선 둥글게 만들기
        lineDataSet.setDrawFilled(true);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        String[] days = getLables();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(days);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        lineChart.setData(lineData);
        lineChart.animateY(3000);
    }

}
