package com.example.project_sleep_alarm;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyListViewAdapter2 extends BaseAdapter {

    List<String> myList2 = new ArrayList<>();
    List<Integer> myPics2 = new ArrayList<>();

    Context context;

    public MyListViewAdapter2(Context anyContext, List<String> anyList, List<Integer> anyPics){
        myList2 = anyList;
        myPics2 = anyPics;
        context = anyContext;
    }

    @Override
    public int getCount() {
        return myList2.size();
    }

    @Override
    public Object getItem(int position) {
        return myList2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater myLayoutInflater = LayoutInflater.from(context);
            convertView = myLayoutInflater.inflate(R.layout.layout_external2,parent,false);
        }

        TextView txtViewItem2 = convertView.findViewById(R.id.txtViewListItem2);

        txtViewItem2.setTextColor(Color.WHITE);
        txtViewItem2.setText(myList2.get(position));
        txtViewItem2.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        Drawable img = parent.getResources().getDrawable(myPics2.get(position));
        img.setBounds(0,0,150,150);

        txtViewItem2.setCompoundDrawables(null,null,img,null);
        txtViewItem2.setCompoundDrawablePadding(32);


        return convertView;
    }
}
