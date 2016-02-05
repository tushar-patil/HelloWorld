package com.example.tushar.notATicTacToe.WiFiService;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.example.tushar.notATicTacToe.R;
import com.example.tushar.notATicTacToe.Utils.LogUtil;
import com.example.tushar.notATicTacToe.WiFiService.WiFiMainGameActivity.WiFiServiceListener;

public class WiFiPlayService extends Service implements ChannelListener, PeerListListener {
    private static final String TAG = WiFiPlayService.class.getSimpleName();

    private WiFiGameServerThread mServerThread = null;
    private WiFiClientThread mClientThread = null;

    private ServerThreadListener mServerThreadListener = null;

    private final IBinder mBinder = new WiFiServiceBinder();
    private WiFiMainGameActivity activity = null;

    private WiFiMainGameActivity.WiFiServiceListener mServiceListener = null;
    private final IntentFilter intentFilter = new IntentFilter();
    private boolean isWifiP2pEnabled = false;

    private WifiP2pManager mWiFiManager = null;
    private Channel mChannel = null;

    private WiFiBroadCastReceiver mReceiver = null;

    private boolean retryChannel = false;

    public WiFiMainGameActivity getActivity() {
        return activity;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class WiFiServiceBinder extends Binder {
        WiFiPlayService getService() {
            return WiFiPlayService.this;
        }
    }

    public void bindAcitivity(Activity activity) {
        LogUtil.d(TAG, "Binding Activity");

        this.activity = (WiFiMainGameActivity) activity;
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


        mWiFiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        LogUtil.d(TAG, "WiFi Manager initialized");

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

    public WiFiServiceListener getmServiceListener() {
        return mServiceListener;
    }

    final public void bindListener(WiFiServiceListener listener) {
        mServiceListener = listener;
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, "WiFiPlayService Destroyed");
        unregisterReceiver(mReceiver);

        if (mClientThread != null && (mClientThread.getStatus() == AsyncTask.Status.RUNNING
                || mClientThread.getStatus() == AsyncTask.Status.PENDING)) {
            mClientThread.cancel(true);
        }

        if (mServerThread != null && (mServerThread.getStatus() == AsyncTask.Status.RUNNING
                || mServerThread.getStatus() == AsyncTask.Status.PENDING)) {
            mServerThread.cancel(true);
        }
        super.onDestroy();
    }

    public void requestConnectionInfo(
            WifiP2pManager.ConnectionInfoListener listener) {
        mWiFiManager.requestConnectionInfo(mChannel, listener);
    }

    public void connect(WifiP2pConfig config) {
        mWiFiManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                LogUtil.d(TAG, " Device Connection success");
                // WiFiBroadCastReceiver will notify us. Ignore for now.
            }

            @Override
            public void onFailure(int reason) {
                LogUtil.d(TAG, " Device Connection Failed");

                Toast.makeText(WiFiPlayService.this,
                        WiFiPlayService.this.getResources().
                                getString(R.string.connection_failed_retry),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void disConnect(int status) {
        switch (status) {
            case WifiP2pDevice.CONNECTED:
                removeGroup();
                break;
            case WifiP2pDevice.AVAILABLE:
            case WifiP2pDevice.INVITED:
                cancelConnect();
                break;
            default:
                break;
        }
        mWiFiManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onFailure(int reasonCode) {
                LogUtil.e(TAG, "Disconnect failed. Reason :" + reasonCode);
                //reason  The reason for failure could be one of P2P_UNSUPPORTED 1, ERROR 0 or BUSY 2.
            }

            @Override
            public void onSuccess() {
                LogUtil.d(TAG, "Disconnect Success");

                Toast.makeText(WiFiPlayService.this, "DisConnected Successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void removeGroup() {
        mWiFiManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                LogUtil.d(TAG, "Remove Group Success");

                Toast.makeText(WiFiPlayService.this, "Aborting connection",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reasonCode) {
                LogUtil.d(TAG, "Remove Group failed");

                Toast.makeText(WiFiPlayService.this,
                        "Connect abort request failed. Reason Code: "
                                + reasonCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelConnect() {
        mWiFiManager.cancelConnect(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                LogUtil.d(TAG, "Aborting connection Success");
                Toast.makeText(WiFiPlayService.this, "Aborting connection",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reasonCode) {
                LogUtil.d(TAG, "Aborting connection Failed");

                Toast.makeText(WiFiPlayService.this,
                        "Connect abort request failed. Reason Code: "
                                + reasonCode, Toast.LENGTH_SHORT).show();
            }
        });
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

    public void startServerThread(WifiP2pDevice device) {
       /* Intent intent = new Intent(this, MainGameActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_FOR_SERVER_OR_CLIENT,
                Constants.START_SERVER_THREAD);
        intent.putExtra(Constants.INTENT_SELECTED_DEVICE,
                mWiFiDevicesListAdapter.getItem(mSelectedDevicePosition).deviceName);
        startActivity(intent);*/
        LogUtil.d(TAG, "starting WiFiGameServerThread");
        mServerThreadListener = new ServerThreadListener();

        mServerThread = new WiFiGameServerThread(this, mServerThreadListener);
        mServerThread.execute(device);

    }

    public void startClientThread(WifiP2pInfo info) {
      /*  Intent intent = new Intent(this, MainGameActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_FOR_SERVER_OR_CLIENT,
                Constants.START_CLIENT_THREAD);
        startActivity(intent);*/
        LogUtil.d(TAG, "starting WiFiClientThread");
        mServerThreadListener = new ServerThreadListener();

        mClientThread = new WiFiClientThread(this, mServerThreadListener);
        mClientThread.execute(info);
    }

    public class ServerThreadListener {

        private volatile boolean mSendMessageNow = false;
        private String mMessageToBeSent = null;

        public String getmMessageToBeSent() {
            return mMessageToBeSent;
        }

        public void setmMessageToBeSent(String mMessageToBeSent) {
            this.mMessageToBeSent = mMessageToBeSent;
        }

        public boolean ismSendMessageNow() {
            synchronized (this) {
                return mSendMessageNow;
            }
        }

        public void setmSendMessageNow(boolean mSendMessageNow) {
            synchronized (this) {
                this.mSendMessageNow = mSendMessageNow;
            }
        }

        public void onSocketConnectionCompleted() {
            activity.onSocketConnectionCompleted();
        }

        public void onMessageReceived(String message) {
            activity.onMessageReceived(message);
        }
    }

    public void sendMessage(String message) {
        LogUtil.d(TAG, "Message Send" + message);

        mServerThreadListener.setmMessageToBeSent(message);
        mServerThreadListener.setmSendMessageNow(true);
    }
}
