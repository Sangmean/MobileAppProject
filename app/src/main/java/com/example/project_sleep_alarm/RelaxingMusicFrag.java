package com.example.project_sleep_alarm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class RelaxingMusicFrag extends Fragment {

    List<String> userDataList2 = new ArrayList<>();
    List<Integer> userDataListIcon2 = new ArrayList<>();

    private void addItems(){
        userDataList2.add("Rain Sound");
        userDataList2.add("Storm Sound");
        userDataList2.add("Bird Sound");
        userDataList2.add("Ocean Sound");
        userDataList2.add("Forest Sound");
        userDataList2.add("Waterfall Sound");
        userDataList2.add("Wind Sound");

        userDataListIcon2.add(R.drawable.rainicon);
        userDataListIcon2.add(R.drawable.stormicon);
        userDataListIcon2.add(R.drawable.birdicon);
        userDataListIcon2.add(R.drawable.oceanicon);
        userDataListIcon2.add(R.drawable.foresticon);
        userDataListIcon2.add(R.drawable.waterfallicon);
        userDataListIcon2.add(R.drawable.windicon);

    }
    public RelaxingMusicFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_relaxing_music, container, false);

        addItems();
        ListView listViewItems2 = v.findViewById(R.id.listViewItem2);
        listViewItems2.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, userDataList2));

        listViewItems2.setAdapter(new MyListViewAdapter2(this.getActivity(), userDataList2,userDataListIcon2));

        listViewItems2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
//                    case 0:
//                        startActivity(new Intent(RelaxingMusicFrag.this.getActivity(), UserRateActivity.class));
//                        break;
//                    case 1:
//                        startActivity(new Intent(RelaxingMusicFrag.this.getActivity(), SleeplogActivity.class));
//                        break;
//                    case 2:
//                        startActivity(new Intent(RelaxingMusicFrag.this.getActivity(), VoiceRecordActivity.class));
//                        break;
                }
            }
        });

        return v;
    }
}
