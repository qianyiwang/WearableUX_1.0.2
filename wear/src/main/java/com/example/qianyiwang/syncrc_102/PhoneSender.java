package com.example.qianyiwang.syncrc_102;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by qianyiwang on 2/7/17.
 */

public class PhoneSender extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient googleApiClient;
    public static String START_ACTIVITY_PATH = "/from-watch";
    private Node mNode = null;
    SendMessageToPhone sendMessageToPhone;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
        // get connected nodes
        Wearable.NodeApi.getConnectedNodes(googleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(@NonNull NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                for(Node node: getConnectedNodesResult.getNodes()){
                    mNode = node;
                    Log.v("Node", String.valueOf(mNode));
                }
            }
        });

        Toast.makeText(this, "phone sender service", 0).show();
        sendMessageToPhone = new SendMessageToPhone();
        sendMessageToPhone.execute();
        return super.onStartCommand(intent, flags, startId);
    }

//    public void sendMessage(String msg){
//        Wearable.MessageApi.sendMessage(googleApiClient, mNode.getId(), START_ACTIVITY_PATH, msg.getBytes()).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
//            @Override
//            public void onResult(@NonNull MessageApi.SendMessageResult sendMessageResult) {
//                if(!sendMessageResult.getStatus().isSuccess()){
//                    Log.e("GoogleApi","Failed to send message with status code: "
//                            + sendMessageResult.getStatus().getStatusCode());
//                }
//                else{
//                    Log.e("GoogleApi","success");
//                }
//            }
//        });
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        sendMessageToPhone.cancel(true);
        Toast.makeText(this,"stop service",0).show();
        super.onDestroy();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("GoogleApi", "onConnected "+ bundle);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("GoogleApi", "onConnectionSuspended:" + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("GoogleApi", "onConnectionFailed:" + connectionResult);
    }

    public class SendMessageToPhone extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            while (true){
//                Log.v("server val", globalValues.msg);
                if (isCancelled())
                {
                    break;
                }
                if(mNode!=null && GlobalValues.msg!=""){

                    Wearable.MessageApi.sendMessage(googleApiClient, mNode.getId(), START_ACTIVITY_PATH, GlobalValues.msg.getBytes()).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(@NonNull MessageApi.SendMessageResult sendMessageResult) {
                            if(!sendMessageResult.getStatus().isSuccess()){
                                Log.e("GoogleApi","Failed to send message with status code: "
                                        + sendMessageResult.getStatus().getStatusCode());
                            }
                            else{
                                Log.e("GoogleApi","success");
                                GlobalValues.msg = "";
                            }
                        }
                    });

                }
                else if(mNode!=null && GlobalValues.hrVal!=""){

                    Wearable.MessageApi.sendMessage(googleApiClient, mNode.getId(), START_ACTIVITY_PATH, ("hr:"+GlobalValues.hrVal).getBytes()).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(@NonNull MessageApi.SendMessageResult sendMessageResult) {
                            if(!sendMessageResult.getStatus().isSuccess()){
                                Log.e("GoogleApi","Failed to send message with status code: "
                                        + sendMessageResult.getStatus().getStatusCode());
                            }
                            else{
                                Log.e("GoogleApi","success");
                                GlobalValues.hrVal = "";
                            }
                        }
                    });

                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
