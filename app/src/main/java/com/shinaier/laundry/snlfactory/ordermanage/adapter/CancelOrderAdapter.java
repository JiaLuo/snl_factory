package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.CancelOrderEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/2/28.
 */

public class CancelOrderAdapter extends BaseAdapterNew<CancelOrderEntity> {
    public CancelOrderAdapter(Context context, List<CancelOrderEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.cancle_order_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        CancelOrderEntity item = getItem(position);
        TextView notWantWashInfo = ViewHolder.get(convertView,R.id.not_want_wash_info);
        ImageView notWantWashSelect = ViewHolder.get(convertView,R.id.not_want_wash_select);
        if(item != null){
            notWantWashInfo.setText(item.getOrderInfo());
            if(item.getIsVisibled() == 0){
                notWantWashSelect.setVisibility(View.VISIBLE);
            }else {
                notWantWashSelect.setVisibility(View.GONE);
            }
        }
    }
}
