package com.example.qianyiwang.syncrc_102;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class WatchMainActivity extends Activity implements View.OnClickListener{

    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_watch_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        startService(new Intent(this, PhoneSender.class));
        startService(new Intent(this, HeartRateService.class));

        bt = (Button)findViewById(R.id.bt);
        bt.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(), PhoneSender.class));
        stopService(new Intent(getBaseContext(), HeartRateService.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt:
                GlobalValues.msg = "hello world";
                break;
        }
    }
}
