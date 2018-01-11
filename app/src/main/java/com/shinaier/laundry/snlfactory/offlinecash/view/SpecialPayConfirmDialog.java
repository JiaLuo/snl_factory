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
import com.shinaier.laundry.snlfactory.util.CommonTools;


/**
 * Created by 张家洛 on 2017/3/6.
 */

public class SpecialPayConfirmDialog extends Dialog {
    private Context context;
    private CountDownTimer countDownTimer;
    private Handler handler;
    private String phoneNum;
    private TextView gainVerifyCode;
//    private EditText etNewPhoneNum;
    private EditText edInputVerifyNum;
    private String payNum;
    private TextView textView;

    public SpecialPayConfirmDialog(@NonNull Context context) {
        super(context);
    }

    public SpecialPayConfirmDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler, String phoneNum, String payNum, TextView textView) {
        super(context, themeResId);
        this.context = context;
        this.handler = handler;
        this.phoneNum = phoneNum;
        this.payNum = payNum;
        this.textView = textView;
    }

    protected SpecialPayConfirmDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(){
        View view = View.inflate(context, R.layout.special_discount_confirm,null);
        setContentView(view);

        TextView payNumber = (TextView) view.findViewById(R.id.pay_number);
        TextView originalPhoneNum = (TextView) view.findViewById(R.id.original_phone_num);
        edInputVerifyNum = (EditText) view.findViewById(R.id.ed_input_verify_num);
        gainVerifyCode = (TextView) view.findViewById(R.id.gain_verify_code);

        TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
        TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm);
        originalPhoneNum.setText(phoneNum);
        payNumber.setText("¥" + CommonTools.formatMoney(Double.parseDouble(payNum)));

        revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                textView.setSelected(false);
            }
        });

        revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputVerifyCode = edInputVerifyNum.getText().toString();
                Message msg = new Message();
                msg.what = 8;
                msg.obj = inputVerifyCode;
                handler.sendMessage(msg);
            }
        });

        gainVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.obj = countDownTimer;
                msg.what = 7;
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
