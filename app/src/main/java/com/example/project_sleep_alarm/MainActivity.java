package com.example.project_sleep_alarm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
        implements SetAlarmFrag.OnTimePickerSetListener{


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

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Set Alarm"));
        tabLayout.addTab(tabLayout.newTab().setText("My Cycle"));
        tabLayout.getTabAt(0).setIcon(R.drawable.alarmclockicon);
        tabLayout.getTabAt(1).setIcon(R.drawable.sleepicon);
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

}