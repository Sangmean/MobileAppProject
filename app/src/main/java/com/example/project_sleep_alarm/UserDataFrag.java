package com.example.project_sleep_alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserDataFrag extends Fragment {

    List<String> userDataList = new ArrayList<>();

    private void addItems(){
        userDataList.add("User Rating");
        userDataList.add("Recent Bed & Wakeup Time");
        userDataList.add("Recorded Voice");
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
        listViewItems.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, userDataList));

        listViewItems.setAdapter(new MyListviewAdapter(this.getActivity(), userDataList));

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(UserDataFrag.this.getActivity(), UserRateActivity.class));
                        break;
                }
            }
        });

        return v;
    }
}
