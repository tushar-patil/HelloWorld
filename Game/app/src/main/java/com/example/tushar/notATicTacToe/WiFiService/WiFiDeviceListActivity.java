package com.example.tushar.notATicTacToe.WiFiService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tushar.notATicTacToe.R;
import com.example.tushar.notATicTacToe.Utils.AlertDialog;
import com.example.tushar.notATicTacToe.Utils.IprogressCancelListener;
import com.example.tushar.notATicTacToe.Utils.LogUtil;
import com.example.tushar.notATicTacToe.WiFiService.WiFiPlayService.WiFiServiceBinder;

import java.util.ArrayList;
import java.util.List;

public class WiFiDeviceListActivity extends Activity implements View.OnClickListener {
    private static final String TAG = WiFiDeviceListActivity.class.getSimpleName();

    private ListView mWiFiDevicesListView = null;
    private WiFiDevicesListAdapter mWiFiDevicesListAdapter = null;

    private Button mSearchWiFiDevicesButton = null;

    private TextView mNoDeviceTextView = null;
    private ProgressDialog mProgressDialog = null;

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
            WiFiPlayService wiFiPlayServiceObj = binder.getService();

            wiFiPlayServiceObj.bindAcitivity(WiFiDeviceListActivity.this);
            wiFiPlayServiceObj.bindListener(new WiFiServiceListener());
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

    public class WiFiServiceListener implements WifiP2pManager.PeerListListener {

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
        }
    }

    private void onNoDeviceFound() {

        mWiFiDevicesListView.setVisibility(View.GONE);
        mNoDeviceTextView.setVisibility(View.VISIBLE);
        mSearchWiFiDevicesButton.setText(this.getResources().getString(R.string.search_again));
    }

     
}
