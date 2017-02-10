package com.example.qianyiwang.syncrc_102;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class WatchMainActivity extends Activity implements View.OnClickListener{

    private static ArrayList<Integer> mIcons;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_watch_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        startService(new Intent(this, PhoneSender.class));
        startService(new Intent(this, HeartRateService.class));

        // icons of the list
        mIcons = new ArrayList<>();
        mIcons.add(R.drawable.climate_icon_v2);
        mIcons.add(R.drawable.seat_icon);
        mIcons.add(R.drawable.dest_icon_v2);
        mIcons.add(R.drawable.bluetooth_icon);
        mIcons.add(R.drawable.switch_phone_icon);
        mIcons.add(R.drawable.temperature_switch);

        title = (TextView)findViewById(R.id.title);

        WearableListView wearableListView = (WearableListView) findViewById(R.id.wearable_List);
        wearableListView.setAdapter(new ListAdapter(this, mIcons));
        wearableListView.setClickListener(mClickListener);
        wearableListView.addOnScrollListener(mOnScrollListener);
    }

    // wearable list's click events
    private WearableListView.ClickListener mClickListener =
            new WearableListView.ClickListener() {
                @Override
                public void onClick(WearableListView.ViewHolder viewHolder) {
                    Toast.makeText(getBaseContext(),
                            String.format("You selected item #%s",
                                    viewHolder.getLayoutPosition()+1),
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTopEmptyRegionClick() {
                    Toast.makeText(getBaseContext(),
                            "Top empty area tapped", Toast.LENGTH_SHORT).show();
                }
            };

    // scroll functions
    private WearableListView.OnScrollListener mOnScrollListener =
            new WearableListView.OnScrollListener() {
                @Override
                public void onAbsoluteScrollChange(int i) {
                    // Only scroll the title up from its original base position
                    // and not down.
                    if (i > 0) {
                        title.setY(-i);
                    }
                }

                @Override
                public void onScroll(int i) {
                    // Placeholder
                }

                @Override
                public void onScrollStateChanged(int i) {
                    // Placeholder
                }

                @Override
                public void onCentralPositionChanged(int i) {
                    // Placeholder
                }
            };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(), PhoneSender.class));
        stopService(new Intent(getBaseContext(), HeartRateService.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
