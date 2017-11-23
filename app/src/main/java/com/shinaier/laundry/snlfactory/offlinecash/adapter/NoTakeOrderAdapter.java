package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;

import java.util.List;


/**
 * Created by 张家洛 on 2017/7/19.
 */

public class NoTakeOrderAdapter extends BaseAdapterNew<String> {
    public NoTakeOrderAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.no_take_order_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        String item = getItem(position);
        TextView orderNumber = ViewHolder.get(convertView,R.id.order_number);
        if(item != null) {
            orderNumber.setText(item);
        }
    }
}
