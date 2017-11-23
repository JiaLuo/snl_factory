package com.shinaier.laundry.snlfactory.manage.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.StoreInfoEntity;


/**
 * Created by 张家洛 on 2017/3/6.
 */

public class DoorToDoorServiceDialog extends Dialog {
    private Context context;
    private Handler handler;
    private StoreInfoEntity storeInfoEntity;

    public DoorToDoorServiceDialog(@NonNull Context context) {
        super(context);
    }

    public DoorToDoorServiceDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler, StoreInfoEntity storeInfoEntity) {
        super(context, themeResId);
        this.context = context;
        this.handler = handler;
        this.storeInfoEntity = storeInfoEntity;
    }

    protected DoorToDoorServiceDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(){
        View view = View.inflate(context, R.layout.door_to_door_service_view,null);
        setContentView(view);
        final EditText edReviseDoorServiceMoney = (EditText) view.findViewById(R.id.ed_revise_door_service_money);
        final TextView edReviseClothesNumber = (TextView) view.findViewById(R.id.ed_revise_clothes_number);
        final TextView edReviseMoney = (TextView) view.findViewById(R.id.ed_revise_money);
        TextView reviseDoorCancel = (TextView) view.findViewById(R.id.revise_door_cancel);
        TextView reviseDoorConfirm = (TextView) view.findViewById(R.id.revise_door_confirm);
        edReviseDoorServiceMoney.setText(storeInfoEntity.getFuwuAmount());
        edReviseClothesNumber.setText(storeInfoEntity.getFuwuNum());
        edReviseMoney.setText(storeInfoEntity.getFuwuTotal());
        reviseDoorConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviseDoorServiceMoney = edReviseDoorServiceMoney.getText().toString();
                String reviseClothesNumber = edReviseClothesNumber.getText().toString();
                String reviseMoney = edReviseMoney.getText().toString();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("reviseDoorServiceMoney",reviseDoorServiceMoney);
                bundle.putString("reviseClothesNumber",reviseClothesNumber);
                bundle.putString("reviseMoney",reviseMoney);
                msg.setData(bundle);
                msg.what = 3;
                handler.sendMessage(msg);
            }
        });

        reviseDoorCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
