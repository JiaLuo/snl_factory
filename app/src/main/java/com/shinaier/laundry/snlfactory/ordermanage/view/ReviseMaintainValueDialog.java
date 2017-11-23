package com.shinaier.laundry.snlfactory.ordermanage.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;


/**
 * Created by 张家洛 on 2017/3/17.
 */

public class ReviseMaintainValueDialog extends Dialog {
    private Context context;
    private Handler handler;

    public ReviseMaintainValueDialog(@NonNull Context context) {
        super(context);
    }

    public ReviseMaintainValueDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler) {
        super(context, themeResId);
        this.context = context;
        this.handler = handler;
    }

    protected ReviseMaintainValueDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(){
        View view = View.inflate(context, R.layout.revise_maintain_value_view,null);
        setContentView(view);
        final TextView edReviseMaintainValue = (TextView) view.findViewById(R.id.ed_revise_maintain_value);
        TextView reviseValueCancel = (TextView) view.findViewById(R.id.revise_value_cancel);
        TextView reviseValueConfirm = (TextView) view.findViewById(R.id.revise_value_confirm);
        reviseValueCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        reviseValueConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviseMaintainValue = edReviseMaintainValue.getText().toString();
                Message msg = new Message();
                msg.obj = reviseMaintainValue;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        });
    }
}
