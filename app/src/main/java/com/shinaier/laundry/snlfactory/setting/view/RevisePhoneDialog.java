package com.shinaier.laundry.snlfactory.setting.view;

import android.app.Dialog;
import android.content.Context;
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
 * Created by 张家洛 on 2017/3/3.
 */

public class RevisePhoneDialog extends Dialog {
    private Context context;
    private Handler handler;

    public RevisePhoneDialog(@NonNull Context context) {
        super(context);
//        this.context = context;
    }

    public RevisePhoneDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler) {
        super(context, themeResId);
        this.context = context;
        this.handler = handler;
    }

    protected RevisePhoneDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public void setView(){
        View view = View.inflate(context, R.layout.revise_phone_view,null);
        setContentView(view);
        final EditText edRevisePhone = (EditText) view.findViewById(R.id.ed_revise_phone);
        TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
        TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm);
        revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = handler.obtainMessage();
                msg.obj = edRevisePhone.getText().toString();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
        revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
