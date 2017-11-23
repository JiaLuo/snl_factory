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
import android.widget.ImageView;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.ManageCommodityEntities;


/**
 * Created by 张家洛 on 2017/3/6.
 */

public class EditCommodityDialog extends Dialog {
    private Context context;
    private Handler handler;
    private ManageCommodityEntities.ItemType.Item item;
    public EditCommodityDialog(@NonNull Context context) {
        super(context);
    }

    public EditCommodityDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler, ManageCommodityEntities.ItemType.Item item) {
        super(context, themeResId);
        this.context = context;
        this.handler = handler;
        this.item = item;
    }

    protected EditCommodityDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(){
        View view = View.inflate(context, R.layout.edit_commodity_view,null);
        setContentView(view);
        TextView editCommodityClothesName = (TextView) view.findViewById(R.id.edit_commodity_clothes_name);
        final EditText edEditCommodityPrice = (EditText) view.findViewById(R.id.ed_edit_commodity_price);
        final EditText edEditCommodityCycle = (EditText) view.findViewById(R.id.ed_edit_commodity_cycle);
        TextView edEditCommodityDelete = (TextView) view.findViewById(R.id.ed_edit_commodity_delete);
        TextView edEditCommodityConfirm = (TextView) view.findViewById(R.id.ed_edit_commodity_confirm);
        ImageView ivEditCommodityDelete = (ImageView) view.findViewById(R.id.iv_edit_commodity_delete);
        editCommodityClothesName.setText(item.getName());
        edEditCommodityDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            }
        });
        ivEditCommodityDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        edEditCommodityConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editCommodityPrice = edEditCommodityPrice.getText().toString();
                String editCommodityCycle = edEditCommodityCycle.getText().toString();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("price",editCommodityPrice);
                bundle.putString("cycle",editCommodityCycle);
                msg.setData(bundle);
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }
}
