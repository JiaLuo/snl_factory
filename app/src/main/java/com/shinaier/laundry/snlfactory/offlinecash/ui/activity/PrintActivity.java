package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.google.zxing.BarcodeFormat;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.BasePrintActivity;
import com.shinaier.laundry.snlfactory.offlinecash.entities.PrintEntity;
import com.shinaier.laundry.snlfactory.setting.ui.activity.ConnectBluetoothActivity;
import com.shinaier.laundry.snlfactory.util.BluetoothUtil;
import com.shinaier.laundry.snlfactory.util.BuildQr;
import com.shinaier.laundry.snlfactory.util.PrintUtil;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.util.Set;



/**
 * Created by 张家洛 on 2017/8/18.
 */

public class PrintActivity extends BasePrintActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CONNECT_BLUE = 0x2;
    public static final int FROM_PRINT_ACT = 0x3;
    final static int TASK_TYPE_PRINT = 0x4;

    @ViewInject(R.id.print)
    private TextView print;
    @ViewInject(R.id.complete)
    private TextView complete;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice blueDevice;
    private BluetoothDevice device;
    private Bitmap qrCodeBitmap;
    private PrintEntity printEntity;
    private CommonDialog dialog;
    private BluetoothSocket bluetoothSocket;

    @Override
    public void onConnected(BluetoothSocket socket, int taskType) {
        switch (taskType){
            case TASK_TYPE_PRINT:
                if (printEntity != null){
                    if (printEntity.getPayOrderPrintEntity() != null){
                        Bitmap bitmap = BuildQr.setQvImageView(printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getOrdersn(), BarcodeFormat.QR_CODE,200,200);
                        if (socket != null){
                            PrintUtil.printTest(printEntity,socket, bitmap);
                        }else {
                            LogUtil.e("zhang","socket == null ");
                        }
                    }else {
                        if (socket != null){
                            LogUtil.e("zhang","充值成功打印");
                            PrintUtil.printTest(printEntity,socket, qrCodeBitmap);
                        }else {
                            LogUtil.e("zhang","socket == null ");
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        printEntity = (PrintEntity) intent.getSerializableExtra("print_entity");
        qrCodeBitmap = intent.getParcelableExtra("qrCode_bitmap");
        initView();
        conectBluetoothDevice(); //链接设备
    }

    private void conectBluetoothDevice() {
        //打开蓝牙的操作
        if (!mBluetoothAdapter.isEnabled()) {
            gotoConnectDevice();
        }else {
            Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
            for (BluetoothDevice device : bondedDevices) {
                if (BluetoothDevice.BOND_BONDED == device.getBondState()){
                    blueDevice = device;
                    break;
                }
            }
            if (blueDevice != null){

                ConnectBlueDeviceAsynvTask asynvTask = new ConnectBlueDeviceAsynvTask();
                asynvTask.execute(blueDevice);
            }else {
                LogUtil.e("zhang","11111111111111111111");
                gotoConnectDevice();

            }
        }
    }

    private void initView() {
        setCenterTitle("订单支付");
        dialog = new CommonDialog(this);
        print.setOnClickListener(this);
        complete.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.print:
                if (blueDevice != null){
                    ConnectBlueDeviceAsynvTask asynvTask = new ConnectBlueDeviceAsynvTask();
                    asynvTask.execute(blueDevice);
                }else {
                    ConnectBlueDeviceAsynvTask asynvTask = new ConnectBlueDeviceAsynvTask();
                    asynvTask.execute(device);
                }

                break;
            case R.id.complete:
            case R.id.left_button:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_CONNECT_BLUE:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                        device = data.getParcelableExtra("device");
                        ConnectBlueDeviceAsynvTask asynvTask = new ConnectBlueDeviceAsynvTask();
                        asynvTask.execute(device);
                    }
                }
                break;
        }
    }

    private void printDevice(BluetoothDevice device, int taskType){
          if(device!= null){
              super.connectDevice(device, taskType);
          }else {
              Toast.makeText(this, "还未选择打印设备", Toast.LENGTH_SHORT).show();
          }
    }


    private class ConnectBlueDeviceAsynvTask extends AsyncTask<BluetoothDevice,Integer,BluetoothSocket> {

        @Override
        protected void onPreExecute() {
            dialog.setContent("连接中");
            dialog.show();
//            super.onPreExecute();
        }

        @Override
        protected BluetoothSocket doInBackground(BluetoothDevice... params) {
            if (params != null){
                bluetoothSocket = BluetoothUtil.connectDevice(params[0]);
            }
            return bluetoothSocket;
        }

        @Override
        protected void onPostExecute(BluetoothSocket bluetoothSocket) {
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            if (bluetoothSocket != null){
                if (bluetoothSocket.isConnected()){
                        if (device != null){
                            LogUtil.e("zhang","device = " + device);
                            printDevice(device,TASK_TYPE_PRINT);
                        }else {
                            LogUtil.e("zhang","bluedevice = " + blueDevice);
                            printDevice(blueDevice,TASK_TYPE_PRINT);
                        }
                    LogUtil.e("zhang","连接成功");
                }else {
                    LogUtil.e("zhang","333333333333333333");
                    ToastUtil.shortShow(PrintActivity.this,"打印机未连接");
                    gotoConnectDevice();
                }
            }else {
                LogUtil.e("zhang","2222222222222222");
                ToastUtil.shortShow(PrintActivity.this,"打印机未连接");
                gotoConnectDevice();
            }
//            super.onPostExecute(bluetoothSocket);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    //未连接成功，到链接蓝牙界面重新寻找蓝牙设备
    private void gotoConnectDevice() {
        Intent intent = new Intent(this,ConnectBluetoothActivity.class);
        intent.putExtra("extra_from",FROM_PRINT_ACT);
        startActivityForResult(intent,REQUEST_CODE_CONNECT_BLUE);
    }


}
