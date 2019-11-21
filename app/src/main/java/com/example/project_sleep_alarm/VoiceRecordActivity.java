package com.example.project_sleep_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class VoiceRecordActivity extends AppCompatActivity {
    MediaRecorder mRecorder;
    MediaPlayer mPlayer;
    String mPath = null;
    boolean isRecording = false;
    boolean isPlaying = false;
    Button BtnRecord;
    Button BtnPlay;
    Button BtnGoBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_record);

        mRecorder = new MediaRecorder();

        BtnGoBack = (Button)findViewById(R.id.btnGoBack);
        BtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        BtnRecord = (Button) findViewById(R.id.btnRecordVoice);
        BtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording == false) {
                    // 이거를 고쳐야 펑션을 고쳐야 하는것 같음
                    initAudioRecorder();
                    mRecorder.start();

                    isRecording = true;
                    BtnRecord.setText("Stop Recording");
                } else {
                    mRecorder.stop();

                    isRecording = false;
                    BtnRecord.setText("Start Recording");
                }
            }
        });


        BtnPlay = (Button) findViewById(R.id.btnStartVoice);
        BtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying == false) {
                    try {
                        mPlayer.setDataSource(mPath);
                        mPlayer.prepare();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    mPlayer.start();

                    isPlaying = true;
                    BtnPlay.setText("Stop Playing");
                }
                else {
                    mPlayer.stop();

                    isPlaying = false;
                    BtnPlay.setText("Start Playing");
                }
            }
        });

        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                isPlaying = false;
                BtnPlay.setText("Start Playing");
            }
        });

    }


    private void initAudioRecorder() {
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/record.aac";
        Log.e("Checking file path", "file path is " + mPath);
        mRecorder.setOutputFile(mPath);
        try {
            mRecorder.prepare();
        } catch (Exception ex) {
            Log.e("WHat is wrong :", ex.getMessage());
        }
    }
}