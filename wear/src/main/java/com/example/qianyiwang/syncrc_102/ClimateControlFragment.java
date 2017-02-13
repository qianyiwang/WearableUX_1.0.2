package com.example.qianyiwang.syncrc_102;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClimateControlFragment extends Fragment implements View.OnClickListener{


    ImageButton fanUp, fanDown;
    int fan_level, rear_fan_level;
    ImageView fan_1, fan_2, fan_3, fan_4, fan_5, fan_6,
    rear_fan_1,rear_fan_2, rear_fan_3, rear_fan_4, rear_fan_5, rear_fan_6;
    int msg;
    TextView front_tempVal, rear_tempVal, label;
    ImageButton tempUp, tempDown;
    Typeface custom_font;
    Boolean clickable = true;
    TimerTask timerTask;
    Timer timer;
    public ClimateControlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        msg = bundle.getInt("count");
        String front_str = "23.0";
        String rear_str = "22.0";
//        custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/FordAntennaWGL-Medium.otf");
        if(msg == 1)
        {
            View view = inflater.inflate(R.layout.frount_climate_layout, container, false);

            label = (TextView)view.findViewById(R.id.label);
//            label.setTypeface(custom_font);

            tempDown = (ImageButton)view.findViewById(R.id.temp_down);
            tempDown.setOnClickListener(this);
            tempUp = (ImageButton)view.findViewById(R.id.temp_up);
            tempUp.setOnClickListener(this);
            front_tempVal = (TextView)view.findViewById(R.id.front_temp_val);
//            front_tempVal.setTypeface(custom_font);
            front_tempVal.setText(front_str);

            fanUp = (ImageButton)view.findViewById(R.id.fan_up);
            fanUp.setOnClickListener(this);
            fanDown = (ImageButton)view.findViewById(R.id.fan_down);
            fanDown.setOnClickListener(this);

            fan_1 = (ImageView)view.findViewById(R.id.fan_1);
            fan_2 = (ImageView)view.findViewById(R.id.fan_2);
            fan_3 = (ImageView)view.findViewById(R.id.fan_3);
            fan_4 = (ImageView)view.findViewById(R.id.fan_4);
            fan_5 = (ImageView)view.findViewById(R.id.fan_5);
            fan_6 = (ImageView)view.findViewById(R.id.fan_6);

            fan_level = 3;
            setFunLabel(fan_level);

            return view;
        }
        else
        {
            View view = inflater.inflate(R.layout.rear_climate_layout, container, false);

            label = (TextView)view.findViewById(R.id.label);
//            label.setTypeface(custom_font);
            tempDown = (ImageButton)view.findViewById(R.id.temp_down);
            tempDown.setOnClickListener(this);
            tempUp = (ImageButton)view.findViewById(R.id.temp_up);
            tempUp.setOnClickListener(this);
            rear_tempVal = (TextView)view.findViewById(R.id.rear_temp_val);
//            rear_tempVal.setTypeface(custom_font);
            rear_tempVal.setText(rear_str);

            fanUp = (ImageButton)view.findViewById(R.id.fan_up);
            fanUp.setOnClickListener(this);
            fanDown = (ImageButton)view.findViewById(R.id.fan_down);
            fanDown.setOnClickListener(this);
            rear_fan_level = 3;

            rear_fan_1 = (ImageView)view.findViewById(R.id.rear_fan_1);
            rear_fan_2 = (ImageView)view.findViewById(R.id.rear_fan_2);
            rear_fan_3 = (ImageView)view.findViewById(R.id.rear_fan_3);
            rear_fan_4 = (ImageView)view.findViewById(R.id.rear_fan_4);
            rear_fan_5 = (ImageView)view.findViewById(R.id.rear_fan_5);
            rear_fan_6 = (ImageView)view.findViewById(R.id.rear_fan_6);

            setRearFunLabel(rear_fan_level);
            return view;
        }
    }


    @Override
    public void onClick(View v) {
        double val;
        switch (v.getId())
        {
            case R.id.temp_up:

                if(msg==1)
                {
                    if(clickable){
                        GlobalValues.vibrator.vibrate(50);
                        val = Double.parseDouble((String) front_tempVal.getText());
                        val = val+0.5;
                        GlobalValues.msg = "front_temp_up_popup";
                        front_tempVal.setText(val + "");
                        clickable = false;
                        timer = new Timer();
                        initializeTimerTask();
                        timer.schedule(timerTask, 1000);
                    }
                    else{
                    }

                }
                else
                {
                    if(clickable){
                        GlobalValues.vibrator.vibrate(50);
                        val = Double.parseDouble((String) rear_tempVal.getText());
                        val = val+0.5;
                        GlobalValues.msg = "rear_temp_up_popup";
                        rear_tempVal.setText(val + "");
                        clickable = false;
                        timer = new Timer();
                        initializeTimerTask();
                        timer.schedule(timerTask, 1000);
                    }
                    else{
                    }
                }
                break;
            case R.id.temp_down:

                if(msg==1)
                {
                    if(clickable){
                        GlobalValues.vibrator.vibrate(50);
                        val = Double.parseDouble((String) front_tempVal.getText());
                        val = val-0.5;
                        GlobalValues.msg = "front_temp_down_popup";
                        front_tempVal.setText(val + "");
                        clickable = false;
                        timer = new Timer();
                        initializeTimerTask();
                        timer.schedule(timerTask, 1000);
                    }
                    else{
                    }

                }
                else
                {
                    if(clickable)
                    {
                        GlobalValues.vibrator.vibrate(50);
                        val = Double.parseDouble((String) rear_tempVal.getText());
                        val = val-0.5;
                        GlobalValues.msg = "rear_temp_down_popup";
                        rear_tempVal.setText(val + "");
                        clickable = false;
                        timer = new Timer();
                        initializeTimerTask();
                        timer.schedule(timerTask, 1000);
                    }
                    else{
                    }
                }
                break;

            case R.id.fan_up:
                if(msg==1)
                {
                    if(fan_level<6)
                    {
                        fan_level++;
                        GlobalValues.msg = "fan_up_popup";
                    }
                    setFunLabel(fan_level);
                }
                else
                {
                    if(rear_fan_level<6)
                    {
                        rear_fan_level++;
                        setRearFunLabel(rear_fan_level);
                        GlobalValues.msg = "fan_up_popup";
                    }
                }

                GlobalValues.vibrator.vibrate(50);
                break;
            case R.id.fan_down:
                if(msg==1)
                {
                    if(fan_level>1)
                    {
                        fan_level--;
                        setFunLabel(fan_level);
                        GlobalValues.msg = "fan_down_popup";
                    }
                }
                else
                {
                    if(rear_fan_level>1)
                    {
                        rear_fan_level--;
                        setRearFunLabel(rear_fan_level);
                        GlobalValues.msg = "fan_down_popup";
                    }
                }

                GlobalValues.vibrator.vibrate(50);
                break;

        }
    }

    private void setFunLabel(int fan_level) {
        switch(fan_level)
        {
            case 1:
                fan_1.setImageResource(R.drawable.fan_ind_active);
                fan_2.setImageResource(R.drawable.fan_ind_def);
                fan_3.setImageResource(R.drawable.fan_ind_def);
                fan_4.setImageResource(R.drawable.fan_ind_def);
                fan_5.setImageResource(R.drawable.fan_ind_def);
                fan_6.setImageResource(R.drawable.fan_ind_def);
                break;
            case 2:
                fan_1.setImageResource(R.drawable.fan_ind_active);
                fan_2.setImageResource(R.drawable.fan_ind_active);
                fan_3.setImageResource(R.drawable.fan_ind_def);
                fan_4.setImageResource(R.drawable.fan_ind_def);
                fan_5.setImageResource(R.drawable.fan_ind_def);
                fan_6.setImageResource(R.drawable.fan_ind_def);
                break;

            case 3:
                fan_1.setImageResource(R.drawable.fan_ind_active);
                fan_2.setImageResource(R.drawable.fan_ind_active);
                fan_3.setImageResource(R.drawable.fan_ind_active);
                fan_4.setImageResource(R.drawable.fan_ind_def);
                fan_5.setImageResource(R.drawable.fan_ind_def);
                fan_6.setImageResource(R.drawable.fan_ind_def);
                break;
            case 4:
                fan_1.setImageResource(R.drawable.fan_ind_active);
                fan_2.setImageResource(R.drawable.fan_ind_active);
                fan_3.setImageResource(R.drawable.fan_ind_active);
                fan_4.setImageResource(R.drawable.fan_ind_active);
                fan_5.setImageResource(R.drawable.fan_ind_def);
                fan_6.setImageResource(R.drawable.fan_ind_def);
                break;

            case 5:
                fan_1.setImageResource(R.drawable.fan_ind_active);
                fan_2.setImageResource(R.drawable.fan_ind_active);
                fan_3.setImageResource(R.drawable.fan_ind_active);
                fan_4.setImageResource(R.drawable.fan_ind_active);
                fan_5.setImageResource(R.drawable.fan_ind_active);
                fan_6.setImageResource(R.drawable.fan_ind_def);
                break;

            case 6:
                fan_1.setImageResource(R.drawable.fan_ind_active);
                fan_2.setImageResource(R.drawable.fan_ind_active);
                fan_3.setImageResource(R.drawable.fan_ind_active);
                fan_4.setImageResource(R.drawable.fan_ind_active);
                fan_5.setImageResource(R.drawable.fan_ind_active);
                fan_6.setImageResource(R.drawable.fan_ind_active);
                break;
        }
    }

    private void setRearFunLabel(int rear_fan_level) {
        switch(rear_fan_level)
        {
            case 1:
                rear_fan_1.setImageResource(R.drawable.fan_ind_active);
                rear_fan_2.setImageResource(R.drawable.fan_ind_def);
                rear_fan_3.setImageResource(R.drawable.fan_ind_def);
                rear_fan_4.setImageResource(R.drawable.fan_ind_def);
                rear_fan_5.setImageResource(R.drawable.fan_ind_def);
                rear_fan_6.setImageResource(R.drawable.fan_ind_def);
                break;
            case 2:
                rear_fan_1.setImageResource(R.drawable.fan_ind_active);
                rear_fan_2.setImageResource(R.drawable.fan_ind_active);
                rear_fan_3.setImageResource(R.drawable.fan_ind_def);
                rear_fan_4.setImageResource(R.drawable.fan_ind_def);
                rear_fan_5.setImageResource(R.drawable.fan_ind_def);
                rear_fan_6.setImageResource(R.drawable.fan_ind_def);
                break;

            case 3:
                rear_fan_1.setImageResource(R.drawable.fan_ind_active);
                rear_fan_2.setImageResource(R.drawable.fan_ind_active);
                rear_fan_3.setImageResource(R.drawable.fan_ind_active);
                rear_fan_4.setImageResource(R.drawable.fan_ind_def);
                rear_fan_5.setImageResource(R.drawable.fan_ind_def);
                rear_fan_6.setImageResource(R.drawable.fan_ind_def);
                break;
            case 4:
                rear_fan_1.setImageResource(R.drawable.fan_ind_active);
                rear_fan_2.setImageResource(R.drawable.fan_ind_active);
                rear_fan_3.setImageResource(R.drawable.fan_ind_active);
                rear_fan_4.setImageResource(R.drawable.fan_ind_active);
                rear_fan_5.setImageResource(R.drawable.fan_ind_def);
                rear_fan_6.setImageResource(R.drawable.fan_ind_def);
                break;

            case 5:
                rear_fan_1.setImageResource(R.drawable.fan_ind_active);
                rear_fan_2.setImageResource(R.drawable.fan_ind_active);
                rear_fan_3.setImageResource(R.drawable.fan_ind_active);
                rear_fan_4.setImageResource(R.drawable.fan_ind_active);
                rear_fan_5.setImageResource(R.drawable.fan_ind_active);
                rear_fan_6.setImageResource(R.drawable.fan_ind_def);
                break;

            case 6:
                rear_fan_1.setImageResource(R.drawable.fan_ind_active);
                rear_fan_2.setImageResource(R.drawable.fan_ind_active);
                rear_fan_3.setImageResource(R.drawable.fan_ind_active);
                rear_fan_4.setImageResource(R.drawable.fan_ind_active);
                rear_fan_5.setImageResource(R.drawable.fan_ind_active);
                rear_fan_6.setImageResource(R.drawable.fan_ind_active);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //sendDataMap(MESSAGE_KEY_2, hrVal);
                clickable = true;
                stopTimerTask();
            }
        };
    }
    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
