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
 * Created by 张家洛 on 2017/3/6.
 */

public class ReviseServiceDialog extends Dialog {
    private Context context;
    private Handler handler;

    public ReviseServiceDialog(@NonNull Context context) {
        super(context);
    }

    public ReviseServiceDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler) {
        super(context, themeResId);
        this.context = context;
        this.handler = handler;
    }

    protected ReviseServiceDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(){
        View view = View.inflate(context, R.layout.revise_service_view,null);
        setContentView(view);
        final EditText edReviseServiceRange = (EditText) view.findViewById(R.id.ed_revise_service_range);
        TextView reviseRangeCancel = (TextView) view.findViewById(R.id.revise_range_cancel);
        TextView reviseRangeConfirm = (TextView) view.findViewById(R.id.revise_range_confirm);
        reviseRangeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        reviseRangeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviseRange = edReviseServiceRange.getText().toString();
                Message msg = new Message();
                msg.obj = reviseRange;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        });
    }
}
