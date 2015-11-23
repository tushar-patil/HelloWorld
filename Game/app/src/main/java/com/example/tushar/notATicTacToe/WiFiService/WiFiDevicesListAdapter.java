package com.example.tushar.notATicTacToe.WiFiService;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tushar.notATicTacToe.MenuScreen.GameMenuAdapter;
import com.example.tushar.notATicTacToe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tushar on 19/11/15.
 */
public class WiFiDevicesListAdapter extends BaseAdapter {
    private static String TAG = GameMenuAdapter.class.getSimpleName();

    private List<WifiP2pDevice> mDevicList = new ArrayList<>();
    private Context mContext = null;
    private LayoutInflater inflater = null;

    public WiFiDevicesListAdapter(Context context, List<WifiP2pDevice> mDevicList) {
        this.mDevicList = mDevicList;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDevicList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDevicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            ViewGroup viewGroup = null;
            convertView = inflater.inflate(R.layout.adapter_wifi_devices_list, viewGroup);
            holder.DevicName = (TextView) convertView.findViewById(R.id.devic_name);
            holder.DeviceDetails = (TextView) convertView.findViewById(R.id.devic_details);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WifiP2pDevice device = mDevicList.get(position);
        String devicName = "";
        String deviceDetails = "";
        if (device != null) {
            devicName = device.deviceName;
            if (device.deviceName.contains("Android_3f82"))
                devicName = ("SamSung");
            else if (device.deviceName.contains("Android_c023"))
                devicName = ("htc");
            else if (device.deviceName.contains("Android_e8bf"))
                devicName = ("HTC");
            else if (device.deviceName.contains("Android_1bf5"))
                devicName = ("HUAWEI");
            else if (device.deviceName.contains("Android_a38e"))
                devicName = ("HTC-W");
            else if (device.deviceName.contains("Android_bc2d"))
                devicName = ("HTC-ONE");
            else {
                devicName = "UNKNOWN DEVICE";
            }
            deviceDetails = device.deviceName;
        }
        holder.DevicName.setText(devicName);
        holder.DeviceDetails.setText(deviceDetails);

        return convertView;
    }

    private static class ViewHolder {
        TextView DevicName;
        TextView DeviceDetails;
    }
}
