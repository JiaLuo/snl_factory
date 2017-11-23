package com.shinaier.laundry.snlfactory.ordermanage.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
 * Created by 张家洛 on 2017/3/7.
 */

public class EditPriceDialog extends Dialog {
    private Context context;
    private Handler handler;
    private Editable temp ;
    public EditPriceDialog(@NonNull Context context) {
        super(context);
    }

    public EditPriceDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler) {
        super(context, themeResId);
        this.handler = handler;
        this.context = context;

    }

    protected EditPriceDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(){
        View view = View.inflate(context, R.layout.edit_price_view,null);
        setContentView(view);
        final EditText edEditPrice = (EditText) view.findViewById(R.id.ed_edit_price);
        final EditText edRemarks = (EditText) view.findViewById(R.id.ed_remarks);
        TextView editPriceCancel = (TextView) view.findViewById(R.id.edit_price_cancel);
        TextView editPriceConfirm = (TextView) view.findViewById(R.id.edit_price_confirm);
        final TextView editPriceMaxNum = (TextView) view.findViewById(R.id.edit_price_max_num);

        edRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editPriceMaxNum.setText(charSequence.length()+ "/30");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                temp = editable;
            }
        });

        editPriceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        editPriceConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editPrice = edEditPrice.getText().toString();
                String remarks = edRemarks.getText().toString();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("price",editPrice);
                bundle.putString("remarks",remarks);
                msg.setData(bundle);
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }
}
