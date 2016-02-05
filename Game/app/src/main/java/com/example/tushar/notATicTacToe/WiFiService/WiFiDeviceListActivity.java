package com.example.tushar.notATicTacToe.WiFiService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tushar.notATicTacToe.GameScreen.MainGameActivity;
import com.example.tushar.notATicTacToe.R;
import com.example.tushar.notATicTacToe.Utils.AlertDialog;
import com.example.tushar.notATicTacToe.Utils.Constants;
import com.example.tushar.notATicTacToe.Utils.IprogressCancelListener;
import com.example.tushar.notATicTacToe.Utils.LogUtil;
import com.example.tushar.notATicTacToe.WiFiService.WiFiPlayService.WiFiServiceBinder;

public class WiFiDeviceListActivity extends Activity implements View.OnClickListener, ConnectionInfoListener {
    private static final String TAG = WiFiDeviceListActivity.class.getSimpleName();

    private ListView mWiFiDevicesListView = null;
    private WiFiDevicesListAdapter mWiFiDevicesListAdapter = null;

    private Button mSearchWiFiDevicesButton = null;

    private TextView mNoDeviceTextView = null;
    private ProgressDialog mProgressDialog = null;

    WiFiPlayService mWiFiPlayServiceObj = null;

    private int mSelectedDevicePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi_device_list);

        initView();
    }

    private void initView() {
        mWiFiDevicesListView = (ListView) findViewById(R.id.devies_lv);

        mNoDeviceTextView = (TextView) findViewById(R.id.no_devices_tv);
        mSearchWiFiDevicesButton = (Button) findViewById(R.id.search_wiFi_device_btn);
        mSearchWiFiDevicesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.search_wiFi_device_btn:
                startServiceSearchDevices();
                break;
            default:
                break;
        }
    }

    private void startServiceSearchDevices() {
        mProgressDialog = AlertDialog.showProgressDialog(this,
                getResources().getString(R.string.searching_devices), mSearchWiFionOnCancelListener);

        Intent serviceIntent = new Intent(this, WiFiPlayService.class);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    IprogressCancelListener mSearchWiFionOnCancelListener = new IprogressCancelListener() {
        @Override
        public void onCancel() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

        }
    };

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d("ServiceConnection", "bind service success");
            WiFiServiceBinder binder = (WiFiServiceBinder) service;
            mWiFiPlayServiceObj = binder.getService();

            mWiFiPlayServiceObj.bindAcitivity(WiFiDeviceListActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        LogUtil.d(TAG, "onDestroy   unbindService service.");
        if (mConnection != null) {
            unbindService(mConnection);
            mConnection = null;
        }

        super.onDestroy();
    }

   /* public class WiFiServiceListener implements WifiP2pManager.PeerListListener {

        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {
            mProgressDialog.dismiss();

            if (peers == null || peers.getDeviceList().size() == 0) {
                onNoDeviceFound();
                return;
            }

            List<WifiP2pDevice> deviceList = new ArrayList<>();
            deviceList.addAll(peers.getDeviceList());

            mSearchWiFiDevicesButton.setVisibility(View.GONE);
            mNoDeviceTextView.setVisibility(View.GONE);
            mWiFiDevicesListView.setVisibility(View.VISIBLE);

            mWiFiDevicesListAdapter = new WiFiDevicesListAdapter(WiFiDeviceListActivity.this,
                    deviceList);
            mWiFiDevicesListView.setAdapter(mWiFiDevicesListAdapter);
            mWiFiDevicesListView.setOnItemClickListener(mDeviceClickListener);
        }
    }*/

    private void onNoDeviceFound() {

        mWiFiDevicesListView.setVisibility(View.GONE);
        mNoDeviceTextView.setVisibility(View.VISIBLE);
        mSearchWiFiDevicesButton.setText(this.getResources().getString(R.string.search_again));
    }

    OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mProgressDialog = AlertDialog.showProgressDialog(WiFiDeviceListActivity.this,
                    getResources().getString(R.string.connecting_to_device),
                    mDeviceConnectIprogressCancelListener);
            mSelectedDevicePosition = position;

            //obtain a peer from the WifiP2pDeviceList
            WifiP2pDevice device = mWiFiDevicesListAdapter.getItem(position);
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = device.deviceAddress;

            mWiFiPlayServiceObj.connect(config);
        }
    };

    IprogressCancelListener mDeviceConnectIprogressCancelListener = new IprogressCancelListener() {
        @Override
        public void onCancel() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            LogUtil.e(TAG, "cancelDisconnect.");
            if (mWiFiPlayServiceObj.isWifiP2pEnabled()) {
                if (mSelectedDevicePosition != -1) {
                    mWiFiPlayServiceObj.disConnect(mWiFiDevicesListAdapter.getItem(mSelectedDevicePosition).status);
                }
            }
        }
    };

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        mProgressDialog.dismiss();
        LogUtil.d(this.getClass().getName(), "info:" + info);

        if (info.groupFormed && info.isGroupOwner) {
            startServerThread();
        } else if (info.groupFormed) {
            startClientThread();
        }
    }

    public void startServerThread() {
        Intent intent = new Intent(this, MainGameActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_FOR_SERVER_OR_CLIENT,
                Constants.START_SERVER_THREAD);
        intent.putExtra(Constants.INTENT_SELECTED_DEVICE,
                mWiFiDevicesListAdapter.getItem(mSelectedDevicePosition).deviceName);
        startActivity(intent);
    }

    public void startClientThread() {
        Intent intent = new Intent(this, MainGameActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_FOR_SERVER_OR_CLIENT,
                Constants.START_CLIENT_THREAD);
        startActivity(intent);
    }

}
