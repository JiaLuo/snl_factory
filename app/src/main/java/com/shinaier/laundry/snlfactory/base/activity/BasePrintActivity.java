package com.shinaier.laundry.snlfactory.base.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;

import com.common.utils.ToastUtil;
import com.shinaier.laundry.snlfactory.util.BluetoothUtil;

import java.io.IOException;

/**
 * Created by 张家洛 on 2017/8/16.
 */

public abstract class BasePrintActivity extends ToolBarActivity {
    private ProgressDialog mProgressDialog;
    private AsyncTask mConnectTask;
    private BluetoothSocket mSocket;

    /**
     * 蓝牙连接成功后回调，该方法在子线程执行，可执行耗时操作
     */
    public abstract void onConnected(BluetoothSocket socket, int taskType);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        cancelConnectTask();
        closeSocket();
        super.onStop();

    }

    public boolean checkBluetoothState() {
        if (BluetoothUtil.isBluetoothOn()) {
            return true;
        } else {
            BluetoothUtil.openBluetooth(this);

            return false;
        }
    }


    public void connectDevice(BluetoothDevice device, int taskType) {
        if (checkBluetoothState() && device != null) {
            mConnectTask = new ConnectBluetoothTask(taskType).execute(device);
        }
    }


    class ConnectBluetoothTask extends AsyncTask<BluetoothDevice, Integer, BluetoothSocket> {

        int mTaskType;

        public ConnectBluetoothTask(int taskType) {
            this.mTaskType = taskType;
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog("请稍候...");
            super.onPreExecute();
        }

        @Override
        protected BluetoothSocket doInBackground(BluetoothDevice... params) {
            if(mSocket != null){
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mSocket = BluetoothUtil.connectDevice(params[0]);
            onConnected(mSocket, mTaskType);
            return mSocket;
        }

        @Override
        protected void onPostExecute(BluetoothSocket socket) {
            mProgressDialog.dismiss();
            if (socket == null || !socket.isConnected()) {
                ToastUtil.shortShow(BasePrintActivity.this,"连接打印机失败");
            } else {
                ToastUtil.shortShow(BasePrintActivity.this,"成功！");
            }

            super.onPostExecute(socket);
        }
    }

    protected void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(message);
//        if (!mProgressDialog.isShowing()) {
//            mProgressDialog.show();
//        }
    }


    protected void closeSocket() {
        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (IOException e) {
                mSocket = null;
                e.printStackTrace();
            }
        }
    }

    protected void cancelConnectTask() {
        if (mConnectTask != null) {
            mConnectTask.cancel(true);
            mConnectTask = null;
        }
    }
}
