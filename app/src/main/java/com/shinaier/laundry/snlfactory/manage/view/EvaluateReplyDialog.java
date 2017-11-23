package com.shinaier.laundry.snlfactory.manage.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;


/**
 * Created by 张家洛 on 2017/3/6.
 */

public class EvaluateReplyDialog extends Dialog {
    private Context context;
    private Handler handler;
    private Editable temp ;

    public EvaluateReplyDialog(@NonNull Context context) {
        super(context);
    }

    public EvaluateReplyDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler) {
        super(context, themeResId);
        this.handler = handler;
        this.context = context;
    }

    protected EvaluateReplyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(){
        View view = View.inflate(context, R.layout.evaluate_reply_view,null);
        setContentView(view);
        final EditText edReplyContent = (EditText) view.findViewById(R.id.ed_reply_content);
        TextView evaluateReplyCancel = (TextView) view.findViewById(R.id.evaluate_reply_cancel);
        TextView evaluateReplyConfirm = (TextView) view.findViewById(R.id.evaluate_reply_confirm);
        final TextView replyEditMaxNum = (TextView) view.findViewById(R.id.reply_edit_max_num);

        edReplyContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                replyEditMaxNum.setText(charSequence.length()+ "/50");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                temp = editable;
            }
        });

        evaluateReplyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        evaluateReplyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = edReplyContent.getText().toString();
                Message msg = new Message();
                msg.obj = replyContent;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }
}
