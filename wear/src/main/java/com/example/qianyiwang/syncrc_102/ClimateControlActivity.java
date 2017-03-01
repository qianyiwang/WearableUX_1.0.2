package com.example.qianyiwang.syncrc_102;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class ClimateControlActivity extends FragmentActivity implements View.OnClickListener {

    GridViewPager pager;
    ImageView backBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climate_control);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Bundle bundle = getIntent().getExtras();

        pager = (GridViewPager) findViewById(R.id.pager);
//        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        com.example.qianyiwang.syncrc_102.PagerAdapter pagerAdapter = new com.example.qianyiwang.syncrc_102.PagerAdapter(getFragmentManager());
        pager.setAdapter(pagerAdapter);

        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);

        backBT = (ImageView)findViewById(R.id.backButton);
        backBT.setOnClickListener(this);
        GlobalValues.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        GlobalValues.msg = "SYNC RC_Climate clicked";

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.backButton:
                GlobalValues.vibrator.vibrate(50);
                GlobalValues.msg = "home";
                finish();
                break;
        }
    }
}
