package com.example.tushar.notATicTacToe.WiFiService;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.example.tushar.notATicTacToe.R;
import com.example.tushar.notATicTacToe.Utils.LogUtil;
public class WiFiPlayService extends Service implements ChannelListener, WifiP2pManager.PeerListListener {
    private static final String TAG = WiFiPlayService.class.getSimpleName();

    private final IBinder mBinder = new WiFiServiceBinder();
    private WiFiDeviceListActivity activity = null;

    private WiFiDeviceListActivity.WiFiServiceListener mServiceListener = null;
    private final IntentFilter intentFilter = new IntentFilter();
    private boolean isWifiP2pEnabled = false;

    private WifiP2pManager mWiFiManager = null;
    private Channel mChannel = null;

    private WiFiBroadCastReceiver mReceiver = null;

    private boolean retryChannel = false;

    public WiFiDeviceListActivity getActivity() {
        return activity;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onChannelDisconnected() {
        LogUtil.d(TAG, "onChannelDisconnected");
// we will try once more
        if (mWiFiManager != null && !retryChannel) {
            Toast.makeText(this, "Channel lost. Trying again",
                    Toast.LENGTH_LONG).show();
            resetPeers();
            retryChannel = true;
            mChannel = initialize(this, getMainLooper(), this);
        } else {
            Toast.makeText(
                    this,
                    "Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.",
                    Toast.LENGTH_LONG).show();
        }
    }


    public class WiFiServiceBinder extends Binder {
        WiFiPlayService getService() {
            return WiFiPlayService.this;
        }
    }

    public void bindAcitivity(Activity activity) {
        LogUtil.d(TAG, "Binding Activity");

        this.activity = (WiFiDeviceListActivity) activity;
       /* if (localDevice != null)
            updateThisDevice(localDevice);*/
        discoverPeers();
    }

    public boolean discoverPeers() {
        LogUtil.d(TAG, "In discoverPeers");
        if (activity != null) {
            if (!isWifiP2pEnabled) {
                LogUtil.d(TAG, "WiFi not Enabled");
                Toast.makeText(this, R.string.p2p_off_warning,
                        Toast.LENGTH_SHORT).show();
            } else {
                LogUtil.d(TAG, "Calling discoverPeers");

                mWiFiManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        LogUtil.d(TAG, "Discovery Initiated");
                        Toast.makeText(WiFiPlayService.this,
                                "Discovery Initiated",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        LogUtil.e(TAG, "Discovery Failed");
                        Toast.makeText(WiFiPlayService.this,
                                "Discovery Failed : " + reasonCode,
                                Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        } else {
            LogUtil.d(TAG, "Activity Null");
        }
        return false;
    }

    @Override
    public void onCreate() {
        LogUtil.d(TAG, "start onCreate~~~");
        super.onCreate();

        LogUtil.d(TAG, "Adding Intent Filters");
        // add necessary intent values to be matched.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter
                .addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter
                .addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        LogUtil.d(TAG, "WiFi Manager initialized");

        mWiFiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        LogUtil.d(TAG, "Channel initialized");

        mChannel = initialize(this, getMainLooper(), this);

        LogUtil.d(TAG, "Channel initialized");

        mReceiver = new WiFiBroadCastReceiver(mWiFiManager, mChannel, this);
        registerReceiver(mReceiver, intentFilter);

       /* try {
            serviceThread = new ThreadPoolManager(this, ConfigInfo.LISTEN_PORT, 5);
        } catch (IOException ex) {
            Log.e("NetworkService", "onActivityCreated() IOException ex", ex);
        }*/
    }

    public boolean isWifiP2pEnabled() {
        return isWifiP2pEnabled;
    }

    final public void setIsWifiP2pEnabled(boolean isEnabled) {
        this.isWifiP2pEnabled = isEnabled;
        if (isWifiP2pEnabled) {
           /* initServiceThread();
        } else {
            uninitServiceThread();*/
        }
    }

    public void resetPeers() {

    }

    public WifiP2pManager.Channel initialize(Context srcContext,
                                             Looper srcLooper, WifiP2pManager.ChannelListener listener) {
        return mWiFiManager.initialize(srcContext, srcLooper, listener);
    }

    public void requestPeers() {
        LogUtil.d(TAG, "requestPeers Called");
        mWiFiManager.requestPeers(mChannel, mServiceListener);
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        mServiceListener.onPeersAvailable(peers);
    }

    public WiFiDeviceListActivity.WiFiServiceListener getmServiceListener() {
        return mServiceListener;
    }

    final public void bindListener(WiFiDeviceListActivity.WiFiServiceListener listener) {
        mServiceListener = listener;
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, "WiFiPlayService Destroyed");
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void requestConnectionInfo(
            WifiP2pManager.ConnectionInfoListener listener) {
        mWiFiManager.requestConnectionInfo(mChannel, listener);
    }

}
