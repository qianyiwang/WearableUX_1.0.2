package com.example.qianyiwang.syncrc_102;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import static com.example.qianyiwang.syncrc_102.R.id.mcs_control;


public class WatchMainActivity extends Activity implements View.OnClickListener{

    ImageButton climateBt, mcsBt, musicBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_watch_main);
        Log.v("test","main create");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        startService(new Intent(this, PhoneSender.class));
        startService(new Intent(this, HeartRateService.class));

        climateBt = (ImageButton)findViewById(R.id.climate_control);
        climateBt.setOnClickListener(this);
        mcsBt = (ImageButton)findViewById(mcs_control);
        mcsBt.setOnClickListener(this);
        musicBt = (ImageButton)findViewById(R.id.bluetooth_audio_button);
        musicBt.setOnClickListener(this);

        GlobalValues.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("test","main resume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(), PhoneSender.class));
        stopService(new Intent(getBaseContext(), HeartRateService.class));
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.climate_control:
                intent = new Intent(this, ClimateControlActivity.class);
                startActivity(intent);
                GlobalValues.vibrator.vibrate(50);
                break;
            case R.id.mcs_control:
                GlobalValues.msg = "mcs_control";
                GlobalValues.vibrator.vibrate(50);
                break;
            case R.id.bluetooth_audio_button:
                GlobalValues.msg = "bluetooth_audio";
                intent = new Intent(this, AudioActivity.class);
                startActivity(intent);
                GlobalValues.vibrator.vibrate(50);
                break;

        }
    }
}
