package com.example.qianyiwang.syncrc_102;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class AudioActivity extends Activity implements View.OnClickListener{

    ImageView homeBT, rewindBT, forwardBT, play_pauseBT;
    ImageButton volumeUp, volumeDown;
    SeekBar volumeSeekBar;
    TextView track_name;
    Vibrator vibrator;
    boolean playStatus = false;
    int track_idx, volumeVal;
    String[] trackName = {"Kalimba", "Maid with the Flaxen Hair", "Sleep Away"};
    TextToSpeech t1;
    SharedPreferences prefs;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Boolean gestureOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        homeBT = (ImageView)findViewById(R.id.backButton);
        homeBT.setOnClickListener(this);

        rewindBT = (ImageView)findViewById(R.id.previousSong);
        rewindBT.setOnClickListener(this);

        forwardBT = (ImageView)findViewById(R.id.nextSong);
        forwardBT.setOnClickListener(this);

        play_pauseBT = (ImageView)findViewById(R.id.run_pause);
        play_pauseBT.setOnClickListener(this);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        track_name = (TextView)findViewById(R.id.musicName);

        volumeDown = (ImageButton)findViewById(R.id.volumeDown);
        volumeDown.setOnClickListener(this);

        volumeUp = (ImageButton)findViewById(R.id.volumeUp);
        volumeUp.setOnClickListener(this);

        volumeVal = 5;
        volumeSeekBar = (SeekBar)findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setMax(10);
        volumeSeekBar.setProgress(volumeVal);
        track_idx = 0;//prefs.getInt("track_name", 0);


        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        gestureOn = prefs.getBoolean("gesture_status", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.run_pause:
                if(playStatus)
                {
                    vibrator.vibrate(50);
                    play_pauseBT.setImageResource(R.drawable.ic_play_v2);
                    GlobalValues.msg = "pause";
                    playStatus = false;
                }
                else
                {
                    vibrator.vibrate(50);
                    play_pauseBT.setImageResource(R.drawable.ic_pause_v2);
                    GlobalValues.msg = "play";
                    playStatus = true;
                }
                break;
            case R.id.nextSong:
                vibrator.vibrate(50);
                if(track_idx<2)
                {
                    GlobalValues.msg = "seek_up";
                    play_pauseBT.setImageResource(R.drawable.ic_pause_v2);
                    playStatus = true;
                    track_idx++;
                    track_name.setText(trackName[track_idx]);
//                    setTrackName(track_idx);
//                    editor.putInt("track_name",track_idx).commit();
                }
                else{
                    Toast.makeText(this, "No more songs", 0).show();
                }
                break;
            case R.id.previousSong:
                vibrator.vibrate(50);
                if(track_idx>0)
                {
                    GlobalValues.msg = "seek_down";
                    play_pauseBT.setImageResource(R.drawable.ic_pause_v2);
                    playStatus = true;
                    track_idx--;
                    track_name.setText(trackName[track_idx]);
                }
                else{
                    Toast.makeText(this, "No more songs", 0).show();
                }
                break;
            case R.id.backButton:
                vibrator.vibrate(50);
                GlobalValues.msg = "home";
                finish();
                break;

            case R.id.volumeDown:
                vibrator.vibrate(50);
                if(volumeVal>1){
                    GlobalValues.msg = "volume_down";
                    volumeSeekBar.setProgress(--volumeVal);
                }

                break;
            case R.id.volumeUp:
                vibrator.vibrate(50);
                if(volumeVal<10){
                    GlobalValues.msg = "volume_up";
                    volumeSeekBar.setProgress(++volumeVal);
                }
                break;
        }
    }
}
