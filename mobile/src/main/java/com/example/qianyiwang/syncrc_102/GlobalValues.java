package com.example.qianyiwang.syncrc_102;

import android.bluetooth.BluetoothAdapter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by qianyiwang on 2/8/17.
 */

public class GlobalValues {
    public static Socket socket = null;
    public static DatagramSocket udp_socket = null;
    public static String dstAddress = "192.168.0.153";
    public static InetAddress udpAddress;
    public static String udp_address = "192.168.0.86";
    public static int dstPort = 1024;
    public static int udpPort = 8001;
    public static final String DEVICE_ADDRESS = "5C:F3:70:6C:7F:4E";//"A0:A8:CD:B3:F2:67";//"5C:F3:70:6C:7F:7B";
}
