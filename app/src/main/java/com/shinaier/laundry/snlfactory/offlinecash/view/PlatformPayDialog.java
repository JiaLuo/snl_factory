package com.shinaier.laundry.snlfactory.offlinecash.view;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;


/**
 * Created by 张家洛 on 2017/8/9.
 */

public class PlatformPayDialog extends Dialog {
    private Context context;
    private CountDownTimer countDownTimer;
    private TextView tvSendCode;
    private Handler handler;
    private int which;

    public PlatformPayDialog(@NonNull Context context) {
        super(context);
    }

    public PlatformPayDialog(@NonNull Context context, @StyleRes int themeResId,Handler handler,int which) {
        super(context, themeResId);
        this.context = context;
        this.handler = handler;
        this.which = which;
    }

    protected PlatformPayDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setview(){
        View view = View.inflate(context, R.layout.pay_order_send_code,null);
        setContentView(view);
        final EditText edPhoneOrderNum = (EditText) view.findViewById(R.id.ed_phone_order_num);
        TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
        TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm);
        tvSendCode = (TextView) view.findViewById(R.id.tv_send_code);

        tvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.obj = countDownTimer;
                msg.what = 5;
                handler.sendMessage(msg);
            }
        });

        revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                if (which == 1){
                    msg.what = 2;
                    handler.sendMessage(msg);
                }else {
                    msg.what = 4;
                    handler.sendMessage(msg);
                }
            }
        });

        revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputCode = edPhoneOrderNum.getText().toString();
                Message msg = new Message();
                msg.obj = inputCode;
                msg.what = 6;
                handler.sendMessage(msg);
            }
        });

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                tvSendCode.setEnabled(false);
                tvSendCode.setText(time + "秒");
            }

            @Override
            public void onFinish() {
                tvSendCode.setEnabled(true);
                tvSendCode.setText("重新获取");
            }
        };
    }
}
