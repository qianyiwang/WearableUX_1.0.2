package com.example.qianyiwang.syncrc_102;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PhoneMainActivity extends AppCompatActivity {
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listView;
    SimpleDateFormat sdf;
    ToggleButton adasToggle;
    Button notificationButton;
    TextView hr_text;
    BroadcastReceiver broadcastReceiver;
    long[] scVibrate = {50,50,50};


    // TCP & UDP
    ConnectTcp connectTcp;
    ConnectUdp connectUdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_main);

        listView = (ListView)findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);
        hr_text = (TextView)findViewById(R.id.hr_text);
        sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                receiveBroadcast(intent);
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(WatchListener.BROADCAST_ACTION));
        // connect tcp and udp
        connectTcp = new ConnectTcp();
        connectTcp.execute();
        connectUdp = new ConnectUdp();
        connectUdp.execute();

        adasToggle = (ToggleButton)findViewById(R.id.adas_toggle);
        adasToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    startService(new Intent(getBaseContext(), BluetoothService.class));
                }
                else{
                    stopService(new Intent(getBaseContext(), BluetoothService.class));
                }
            }
        });

        notificationButton = (Button)findViewById(R.id.notification_button);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send notification to watch
                postNotifications(getApplicationContext(), scVibrate, R.mipmap.speed_alert, "Coming Event", "Home - Weekly Staff Meeting");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void receiveBroadcast(Intent intent) {
        String msg_watch = intent.getStringExtra("msg_watch");
        if(msg_watch.contains("hr:")){
            hr_text.setText(msg_watch);
        }
        else{
            String currentTime = sdf.format(new Date());
            Log.e("received msg_watch",msg_watch);
            adapter.add(msg_watch+"@"+currentTime);
        }
    }

    public static void postNotifications(Context context, long[] vibration, int image, String title, String text) {
        String colorStr = "<font color=\"red\"><b>"+title+"</b></font>";
        NotificationCompat.Builder summaryBuilder = new
                NotificationCompat.Builder(context)
                .setContentTitle(Html.fromHtml(colorStr))
                .setContentText(text)
                .setSmallIcon(image)
                .setVibrate(vibration);
        Notification[] notifications = new android.app.Notification[]{summaryBuilder.build()};
        for (int i = 0; i < notifications.length; i++) {
            Notification not = notifications[i];
            NotificationManagerCompat.from(context).notify(i, not);
        }
    }

    //************* connect TCP socket in AsyncTask*************
    public class ConnectTcp extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                GlobalValues.socket = new Socket(GlobalValues.dstAddress, GlobalValues.dstPort);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"TCP CONNECTED",0).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"TCP CONNECTED FAILED",0).show();
                    }
                });

            }
        return null;
        }
    }

    //************* connect UDP socket in AsyncTask*************
    public class ConnectUdp extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                GlobalValues.udpAddress = InetAddress.getByName(GlobalValues.udp_address);
                GlobalValues.udp_socket = new DatagramSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
