package com.example.project_sleep_alarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
        implements SetAlarmFrag.OnTimePickerSetListener{
    SQLiteDatabase db;

    //data from setAlarmFrag
    int wakeupHour, wakeupMinute;
    @Override
    public void onTimePickerSet(int hour, int min){
        wakeupHour = hour;
        wakeupMinute = min;
    }

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
        createTables();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Set Alarm"));
        tabLayout.addTab(tabLayout.newTab().setText("My Cycle"));
        tabLayout.addTab(tabLayout.newTab().setText("Relaxing"));
        tabLayout.getTabAt(0).setIcon(R.drawable.clockicon);
        tabLayout.getTabAt(1).setIcon(R.drawable.img_zz);
        tabLayout.getTabAt(2).setIcon(R.drawable.musicicon);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyTabAdapter adapter = new MyTabAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    //create DB
    private void createDB(){
        try {
            db = openOrCreateDatabase("SleepCycle.db", Context.MODE_PRIVATE, null);
        } catch (Exception ex) {
            Log.e("DB CREATE: ", ex.getMessage());
        }
    }

    //create Tables
    private void createTables() {
        try {
            String dropCycleTable = "DROP TABLE IF EXISTS cycle;";

            String createCycle = "CREATE TABLE cycle (date TEXT PRIMARY KEY, bedtime STRING, wakeuptime STRING, rate STRING);";

            db.execSQL(dropCycleTable);
            db.execSQL(createCycle);
            Log.e("DB TABLE:", "Create Tables");
        } catch (Exception ex) {
            Log.e("DB TABLE: ","Error in creating tables "+ ex.getMessage());
        }
    }

}