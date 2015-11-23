package com.example.tushar.notATicTacToe.WiFiService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;

import com.example.tushar.notATicTacToe.Utils.LogUtil;

public class WiFiBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG = WiFiBroadCastReceiver.class.getSimpleName();

    private WifiP2pManager mManager;
    private Channel mChannel;
    private WiFiPlayService mService;

    public WiFiBroadCastReceiver(WifiP2pManager manager, Channel channel,
                                 WiFiPlayService service) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mService = service;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity

            // UI update to indicate wifi p2p status.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi Direct mode is enabled
                mService.setIsWifiP2pEnabled(true);
                // TODO let's go and test ...
                mService.discoverPeers();
            } else {
                mService.setIsWifiP2pEnabled(false);
                mService.resetPeers();

            }
            LogUtil.d(TAG, "P2P state changed - state : " + state);

            LogUtil.d(TAG, "isWifiEnable = " + mService.isWifiP2pEnabled());
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            LogUtil.d(TAG, "P2P peers changed");
            LogUtil.d(TAG, "isWifiEnable = " + mService.isWifiP2pEnabled());
            if (mService.isWifiP2pEnabled()) {
                mService.requestPeers();
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
            LogUtil.d(TAG, "P2P connection changed ");

            if (!mService.isWifiP2pEnabled()) {
                return;
            }


            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            LogUtil.d(TAG, "P2P connection changed - networkInfo:" + networkInfo.toString());
            if (networkInfo.isConnected()) {

                // we are connected with the other device, request connection
                // info to find group owner IP
               /* mService.requestConnectionInfo(serviceListener);*/
            } else {
                // It's a disconnect
                mService.resetPeers();
                // TODO let's go and test ...
                mService.discoverPeers();
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
            LogUtil.d(TAG, "P2P This device state changed");
            WifiP2pDevice wifiP2pDevice = (WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
           /* mService.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));*/
            LogUtil.d(TAG, "P2P this device changed - wifiP2pDevice:" + wifiP2pDevice.toString());
//        } else if (WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)) {
//        	// TODO ...
//            Broadcast intent action indicating that peer discovery has either started or stopped.
//            One extra EXTRA_DISCOVERY_STATE indicates whether discovery has started or stopped.
//            Note that discovery will be stopped during a connection setup.
//            If the application tries to re-initiate discovery during this time, it can fail.
            // 1. WIFI_P2P_DISCOVERY_STARTED
            // 2. WIFI_P2P_DISCOVERY_STOPPED
        } else {
            LogUtil.d(TAG, "Other P2P change action - " + action);
        }
    }

}