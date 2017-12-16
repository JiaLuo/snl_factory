package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.CashCouponEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/10/31.
 */

public class CashCouponAdapter extends BaseAdapterNew<CashCouponEntity.CashCouponResult> {
    public CashCouponAdapter(Context context, List<CashCouponEntity.CashCouponResult> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.cash_coupon_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        CashCouponEntity.CashCouponResult item = getItem(position);
        TextView tvNum = ViewHolder.get(convertView,R.id.tv_num);
        TextView tvCardNum = ViewHolder.get(convertView,R.id.tv_card_num);
        TextView tvCardTime = ViewHolder.get(convertView,R.id.tv_card_time);

        if (item != null){
            tvNum.setText(position + 1 + "");
            tvCardNum.setText(item.getSn());
            tvCardTime.setText(item.getEndTime());
        }

    }
}
