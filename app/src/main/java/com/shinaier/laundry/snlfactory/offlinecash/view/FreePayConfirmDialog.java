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
 * Created by 张家洛 on 2018/1/10.
 */

public class FreePayConfirmDialog extends Dialog {
    private Context context;
    private CountDownTimer countDownTimer;
    private Handler handler;
    private String phoneNum;
    private EditText edInputVerifyNum;
    private TextView gainVerifyCode;

    public FreePayConfirmDialog(@NonNull Context context) {
        super(context);
    }

    public FreePayConfirmDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler, String phoneNum) {
        super(context, themeResId);
        this.handler = handler;
        this.phoneNum = phoneNum;
        this.context = context;
    }

    protected FreePayConfirmDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(){
        View view = View.inflate(context, R.layout.free_discount_confirm,null);
        setContentView(view);

        TextView originalPhoneNum = (TextView) view.findViewById(R.id.original_phone_num);
        edInputVerifyNum = (EditText) view.findViewById(R.id.ed_input_verify_num);
        gainVerifyCode = (TextView) view.findViewById(R.id.gain_verify_code);

        TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
        TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm);
        originalPhoneNum.setText(phoneNum);

        revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = 11;
                handler.sendMessage(msg);
            }
        });

        revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputVerifyCode = edInputVerifyNum.getText().toString();
                Message msg = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putString("phone",newPhoneNum);
//                bundle.putString("verifyCode",inputVerifyCode);
                msg.obj = inputVerifyCode;
                msg.what = 10;
//                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });

        gainVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.obj = countDownTimer;
                msg.what = 9;
                handler.sendMessage(msg);
            }
        });

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                gainVerifyCode.setEnabled(false);
                gainVerifyCode.setText(time + "秒");
            }

            @Override
            public void onFinish() {
//                if (!TextUtils.isEmpty(etNewPhoneNum.getText())){
                gainVerifyCode.setEnabled(true);
//                }
                gainVerifyCode.setText("重新获取");
            }
        };
    }
}
