package com.shinaier.laundry.snlfactory.manage.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.EmployeeEntity;


/**
 * Created by 张家洛 on 2017/3/6.
 */

public class AddEmployeeDialog extends Dialog {
    private Context context;
    private CountDownTimer countDownTimer;
    private Handler handler;
    private TextView gainVerifyCode;
    private EditText edAddEmployeePhone;
    private EmployeeEntity entity;

    public AddEmployeeDialog(@NonNull Context context) {
        super(context);
    }

    public AddEmployeeDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler , EmployeeEntity entity) {
        super(context, themeResId);
        this.context = context;
        this.handler = handler;
        this.entity = entity;
    }

    protected AddEmployeeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(){
        View view = View.inflate(context, R.layout.add_employee_view,null);
        setContentView(view);
        final EditText edAddEmployeeName = (EditText) view.findViewById(R.id.ed_add_employee_name);
        edAddEmployeePhone = (EditText) view.findViewById(R.id.ed_add_employee_phone);
         final EditText edAddEmployeeVerifyCode = (EditText) view.findViewById(R.id.ed_add_employee_verify_code);
        gainVerifyCode = (TextView) view.findViewById(R.id.gain_verify_code);
        final EditText edAddEmployeePass = (EditText) view.findViewById(R.id.ed_add_employee_pass);
        TextView addEmployeeCancel = (TextView) view.findViewById(R.id.add_employee_cancel);
        TextView addEmployeeConfirm = (TextView) view.findViewById(R.id.add_employee_confirm);

        if(entity != null){
            edAddEmployeeName.setText(entity.getNickname());
            edAddEmployeePhone.setText(entity.getUsername());
        }
        addEmployeeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        addEmployeeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addEmployeeName = edAddEmployeeName.getText().toString();
                String addEmployeePhone = edAddEmployeePhone.getText().toString();
                String addEmployeeVerifyCode = edAddEmployeeVerifyCode.getText().toString();
                String addEmployeePass = edAddEmployeePass.getText().toString();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("name",addEmployeeName);
                bundle.putString("phone",addEmployeePhone);
                bundle.putString("verifyCode",addEmployeeVerifyCode);
                bundle.putString("password",addEmployeePass);
                if(entity != null){
                    msg.what = 3;
                    bundle.putInt("isEdit",3);
                }else {
                    msg.what = 2;
                }
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });

        gainVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("phone",edAddEmployeePhone.getText().toString());
                msg.setData(bundle);
                msg.obj = countDownTimer;
                msg.what = 1;
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
                if (!TextUtils.isEmpty(edAddEmployeePhone.getText())){
                    gainVerifyCode.setEnabled(true);
                }
                gainVerifyCode.setText("重新获取");
            }
        };
    }
}
