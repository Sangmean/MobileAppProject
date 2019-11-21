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

public class MyListviewAdapter extends BaseAdapter {

    List<String> myList = new ArrayList<>();
    List<Integer> myPics = new ArrayList<>();

    Context context;

    public MyListviewAdapter(Context anyContext, List<String> anyList, List<Integer> anyPics){
        myList = anyList;
        myPics = anyPics;
        context = anyContext;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater myLayoutInflater = LayoutInflater.from(context);
            convertView = myLayoutInflater.inflate(R.layout.layout_external,parent,false);
        }

        TextView txtViewItem = convertView.findViewById(R.id.txtViewListItem);

            txtViewItem.setTextColor(Color.WHITE);
            txtViewItem.setText(myList.get(position));
            txtViewItem.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        Drawable img = parent.getResources().getDrawable(myPics.get(position));
        img.setBounds(0,0,90,90);
        txtViewItem.setCompoundDrawables(img,null,null,null);
        txtViewItem.setCompoundDrawablePadding(32);


        return convertView;
    }
}
