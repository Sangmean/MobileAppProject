package com.example.project_sleep_alarm;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyTabAdapter extends FragmentPagerAdapter {
    Context context;
    int numTabs;
    public MyTabAdapter(Context anyContext, FragmentManager fm, int numTabs) {
        super(fm);
        context = anyContext;
        this.numTabs = numTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SetAlarmFrag alarmFragment = new SetAlarmFrag();
                return alarmFragment;
            case 1:
                UserDataFrag dataFragment = new UserDataFrag();
                return dataFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return numTabs;
    }
}
