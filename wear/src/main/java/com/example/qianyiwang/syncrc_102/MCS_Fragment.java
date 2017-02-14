package com.example.qianyiwang.syncrc_102;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Qianyi on 7/24/2016.
 */
public class MCS_Fragment extends Fragment implements View.OnClickListener{

    int msg;

    TextView label;
    int massageIdx = 0;
    int settingIdx = 2;
    BroadcastReceiver broadcastReceiver;
    String gestureRecognition = "";
    View massageView, settingView;
    static boolean allowSwitch = true;
    public static final String BROADCAST_ACTION = "net.qianyiw.broadcasttest.returnresults";
    ImageView seat,massage_high, massage_low, massage_off, setting1, setting2, setting3;
    TextToSpeech t1;

//    Typeface custom_font;

    public MCS_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        msg = bundle.getInt("count");
//        custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/FordAntennaWGL-Medium.otf");

        if(msg == 1)
        {
            View view = inflater.inflate(R.layout.mcs_massage_layout, container, false);
            massageView = view;
            return view;
        }
        else
        {
            View view = inflater.inflate(R.layout.mcs_driver_adjust, container, false);
            settingView = view;
            return view;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(msg == 1){
            massage_high = (ImageView)view.findViewById(R.id.massage_high);
            massage_high.setOnClickListener(this);
            massage_low = (ImageView)view.findViewById(R.id.massage_low);
            massage_low.setOnClickListener(this);
            massage_off = (ImageView)view.findViewById(R.id.massage_off);
            massage_off.setOnClickListener(this);
            seat = (ImageView)view.findViewById(R.id.driver_seat);
            label = (TextView)view.findViewById(R.id.label);
//            label.setTypeface(custom_font);

        }
        else{
            label = (TextView)view.findViewById(R.id.label);
//            label.setTypeface(custom_font);
            setting1 = (ImageView)view.findViewById(R.id.setting1);
            setting1.setOnClickListener(this);
            setting1.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getActivity(), "Setting 1 is selected", 0).show();
                    return true;
                }
            });
            setting2 = (ImageView)view.findViewById(R.id.setting2);
            setting2.setOnClickListener(this);
            setting3 = (ImageView)view.findViewById(R.id.setting3);
            setting3.setOnClickListener(this);
            seat = (ImageView)view.findViewById(R.id.driver_seat);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void massageUpdateExecute(int idx){
        switch(idx){
            case 0:
                seat.setImageResource(R.drawable.mcs_driver);
                GlobalValues.msg = "massage close";
                massage_high.setImageResource(R.drawable.hi_unselect);
                massage_low.setImageResource(R.drawable.low_unselect);
                massage_off.setImageResource(R.drawable.off_select);
                t1.speak("Massage Off", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 1:
                seat.setImageResource(R.drawable.seat_massage);
                GlobalValues.msg = "massage low";
                massage_high.setImageResource(R.drawable.hi_unselect);
                massage_low.setImageResource(R.drawable.low_select);
                massage_off.setImageResource(R.drawable.off_unselect);
                t1.speak("Massage Low", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 2:
                seat.setImageResource(R.drawable.seat_massage);
                GlobalValues.msg = "massage high";
                massage_high.setImageResource(R.drawable.hi_select);
                massage_low.setImageResource(R.drawable.low_unselect);
                massage_off.setImageResource(R.drawable.off_unselect);
                t1.speak("Massage High", TextToSpeech.QUEUE_FLUSH, null);
                break;
        }
    }

    public void updateSetting(String s){
        if(((MCSActivity)getActivity()).pagerAdapter.currentIdx==1){
            switch (s){
                case "SINGLE OUTSIDE":
//                    Toast.makeText(getContext(),"update setting",0).show();
                    if(settingIdx==0){
                        settingIdx = 2;
                    }
                    else{
                        --settingIdx;
                    }
                    settingUpdateExecute(settingIdx);
                    break;
                case "SINGLE INSIDE":
                    if(settingIdx==2){
                        settingIdx = 0;
                    }
                    else{
                        ++settingIdx;
                    }
                    settingUpdateExecute(settingIdx);
                    break;
                case "DOUBLE OUTSIDE":
                    if(allowSwitch){
                    ((MCSActivity) getActivity()).pager.setCurrentItem(0, 0);
                    t1.speak("Massage segment", TextToSpeech.QUEUE_FLUSH, null);
//                    Toast.makeText(getContext(),String.valueOf(((MCSActivity)getActivity()).pagerAdapter.currentIdx),0).show();
                    }
                    break;
                case "DOUBLE INSIDE":
                    getActivity().finish();
                    break;
            }
        }
        allowSwitch = true;
    }

    public void settingUpdateExecute(int idx){
        switch(idx){
            case 0:
                setting1.setImageResource(R.drawable.m1_select);
                setting2.setImageResource(R.drawable.m2_unselect);
                setting3.setImageResource(R.drawable.m3_unselect);
                sendCommand("M1");
                t1.speak("Setting M1 Selected ", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 1:
                setting1.setImageResource(R.drawable.m1_unselect);
                setting2.setImageResource(R.drawable.m2_select);
                setting3.setImageResource(R.drawable.m3_unselect);
                sendCommand("M2");
                t1.speak("Setting M2 Selected ", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 2:
                setting1.setImageResource(R.drawable.m1_unselect);
                setting2.setImageResource(R.drawable.m2_unselect);
                setting3.setImageResource(R.drawable.m3_select);
                sendCommand("M3");
                t1.speak("Setting M3 Selected ", TextToSpeech.QUEUE_FLUSH, null);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.massage_high:
                seat.setImageResource(R.drawable.seat_massage);
                GlobalValues.msg = "massage high";
                massage_high.setImageResource(R.drawable.hi_select);
                massage_low.setImageResource(R.drawable.low_unselect);
                massage_off.setImageResource(R.drawable.off_unselect);
                break;
            case R.id.massage_low:
                seat.setImageResource(R.drawable.seat_massage);
                GlobalValues.msg = "massage low";
                massage_high.setImageResource(R.drawable.hi_unselect);
                massage_low.setImageResource(R.drawable.low_select);
                massage_off.setImageResource(R.drawable.off_unselect);
                break;

            case R.id.massage_off:
                seat.setImageResource(R.drawable.mcs_driver);
                GlobalValues.msg = "massage close";
                massage_high.setImageResource(R.drawable.hi_unselect);
                massage_low.setImageResource(R.drawable.low_unselect);
                massage_off.setImageResource(R.drawable.off_select);
                break;

            case R.id.setting1:
                setting1.setImageResource(R.drawable.m1_select);
                setting2.setImageResource(R.drawable.m2_unselect);
                setting3.setImageResource(R.drawable.m3_unselect);
                sendCommand("M1");
                break;
            case R.id.setting2:
                setting1.setImageResource(R.drawable.m1_unselect);
                setting2.setImageResource(R.drawable.m2_select);
                setting3.setImageResource(R.drawable.m3_unselect);
                sendCommand("M2");
                break;
            case R.id.setting3:
                setting1.setImageResource(R.drawable.m1_unselect);
                setting2.setImageResource(R.drawable.m2_unselect);
                setting3.setImageResource(R.drawable.m3_select);
                sendCommand("M3");
                break;
        }
    }

    private void sendCommand(String settingStatus) {
        switch (settingStatus)
        {
            case "M1":
                int m1_lumbarVal = 3;
                int m1_lowBolsterVal = 3;
                int m1_backBolsterVal = 3;
                GlobalValues.msg = "lumbar:" + m1_lumbarVal;
                GlobalValues.msg = "low_bolster:" + m1_lowBolsterVal;
                GlobalValues.msg = "high_bolster:" + m1_backBolsterVal;

                break;
            case "M2":
                int m2_lumbarVal = 5;
                int m2_lowBolsterVal = 7;
                int m2_backBolsterVal = 7;
                GlobalValues.msg = "lumbar:" + m2_lumbarVal;
                GlobalValues.msg = "low_bolster:" + m2_lowBolsterVal;
                GlobalValues.msg = "high_bolster:" + m2_backBolsterVal;
                break;
            case "M3":
                int m3_lumbarVal = 0;
                int m3_lowBolsterVal = 0;
                int m3_backBolsterVal = 0;
                GlobalValues.msg = "lumbar:" + m3_lumbarVal;
                GlobalValues.msg = "low_bolster:" + m3_lowBolsterVal;
                GlobalValues.msg = "high_bolster:" + m3_backBolsterVal;
                break;
        }
    }


}
