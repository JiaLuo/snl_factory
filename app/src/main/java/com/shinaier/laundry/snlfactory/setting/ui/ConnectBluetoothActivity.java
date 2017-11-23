package com.shinaier.laundry.snlfactory.setting.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.common.utils.LogUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.offlinecash.ui.PrintActivity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张家洛 on 2017/8/17.
 */

public class ConnectBluetoothActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_ENABLE_BT = 1;

    @ViewInject(R.id.lv_paired_devices)
    private ListView lvPairedDevices;
    @ViewInject(R.id.rigth_text)
    private TextView rigthText;

    private List<BluetoothDevice> datasource;
    private BluetoothAdapter mBluetoothAdapter;
    private BroadcastReceiver mDiscoveryReceiver;
    private DeviceListAdapter mAdapter;
    private int mSelectedPosition = -1;
    private int extraFrom;
    private CommonDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_bluetooth_act);
        ViewInjectUtils.inject(this);
        extraFrom = getIntent().getIntExtra("extra_from", 0);
        initView();
        loadData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        removeDiscoveryReceiver();
    }

    private void removeDiscoveryReceiver() {
        if (mDiscoveryReceiver != null) {
            unregisterReceiver(mDiscoveryReceiver);
            mDiscoveryReceiver = null;
        }
    }

    private void loadData() {
        mAdapter.clear();
        mBluetoothAdapter.startDiscovery();
    }

    private void initView() {
        setCenterTitle("连接蓝牙设备");
        rightText.setVisibility(View.VISIBLE);
        rigthText.setText("重新扫描");
        dialog = new CommonDialog(this);
        dialog.setContent("搜索中");
        dialog.show();
        rigthText.setOnClickListener(this);
        datasource = new ArrayList<>();
        mAdapter = new DeviceListAdapter(this);
        lvPairedDevices.setAdapter(mAdapter);
        lvPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedPosition = position;
                mAdapter.notifyDataSetChanged();
                BluetoothDevice item = mAdapter.getItem(position);
                LogUtil.e("zhang","item = " + item);
                if (extraFrom == PrintActivity.FROM_PRINT_ACT){
                    Intent intent = new Intent(ConnectBluetoothActivity.this, PrintActivity.class);
                    intent.putExtra("device",item);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //打开蓝牙的操作
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        addDiscoveryReceiver();
    }

    private void addDiscoveryReceiver() {
        // Create a BroadcastReceiver for ACTION_FOUND
        mDiscoveryReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                Log.e("zhang","bbbbbbbb");
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    datasource.add(device);
                    mAdapter.addAll(datasource);
                    print(device.getName() + "\n" + device.getAddress());
                }
            }
        };
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mDiscoveryReceiver, filter); // Don't forget to unregister during onDestroy

    }


    private void print(String msg) {
        Log.e("zhang", msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rigth_text:
                dialog.show();
                loadData();
                break;
        }
    }



    class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

        public DeviceListAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            BluetoothDevice device = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.data_device_item, parent, false);
            }

            TextView tvDeviceName = (TextView) convertView.findViewById(R.id.tv_device_name);
            CheckBox cbDevice = (CheckBox) convertView.findViewById(R.id.cb_device);

            tvDeviceName.setText(device.getName());

            cbDevice.setChecked(position == mSelectedPosition);

            return convertView;
        }
    }
}
