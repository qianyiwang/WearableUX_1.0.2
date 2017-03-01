package com.example.qianyiwang.syncrc_102;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MCSActivity extends Activity implements View.OnClickListener{

    GridViewPager pager;
    ImageView backBT;
    MCS_PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        pager = (GridViewPager) findViewById(R.id.pager);
        pagerAdapter = new MCS_PagerAdapter(getFragmentManager());
        pager.setAdapter(pagerAdapter);

        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);

        backBT = (ImageView)findViewById(R.id.backButton);
        backBT.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalValues.msg = "SYNC RC_MCS clicked";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.backButton:
                GlobalValues.vibrator.vibrate(50);
                GlobalValues.msg = "home";
                finish();
                break;
        }
    }
}
