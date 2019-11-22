package com.example.project_sleep_alarm;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RelaxingMusicFrag extends Fragment {

    List<String> userDataList2 = new ArrayList<>();
    List<Integer> userDataListIcon2 = new ArrayList<>();
    List<Integer> userRelaxSongs = new ArrayList<>();
    MediaPlayer songPlay = new MediaPlayer();

    private void addItems(){
        userDataList2.add("Rain Sound");
        userDataList2.add("Storm Sound");
        userDataList2.add("Bird Sound");
        userDataList2.add("Ocean Sound");
     //   userDataList2.add("Forest Sound");
        userDataList2.add("Waterfall Sound");
        userDataList2.add("Wind Sound");

        userDataListIcon2.add(R.drawable.rainicon);
        userDataListIcon2.add(R.drawable.stormicon);
        userDataListIcon2.add(R.drawable.birdicon);
        userDataListIcon2.add(R.drawable.oceanicon);
       // userDataListIcon2.add(R.drawable.foresticon);
        userDataListIcon2.add(R.drawable.waterfallicon);
        userDataListIcon2.add(R.drawable.windicon);

        userRelaxSongs = Arrays.asList(R.raw.rain,R.raw.thunderstorm,R.raw.bird,R.raw.ocean,R.raw.rain,R.raw.thunderstorm);

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

        final MyListViewAdapter2 myAdapter = new MyListViewAdapter2(this.getActivity(),userDataList2,userDataListIcon2,userRelaxSongs);
        listViewItems2.setAdapter(myAdapter);

        listViewItems2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(songPlay != null && songPlay.isPlaying()){
                   songPlay.stop();
               }
               if(position == myAdapter.getCurrentPlay()){
                   myAdapter.setCurrentPlay(-1);
               }else{
                   try{
                       songPlay = MediaPlayer.create(RelaxingMusicFrag.this.getActivity(),userRelaxSongs.get(position));
                       songPlay.start();
                   }catch (Exception ex){
                       Log.e("Song is play? ", ex.getMessage());
                   }
                   myAdapter.setCurrentPlay(position);
               }
            }
        });

        return v;
    }
}
