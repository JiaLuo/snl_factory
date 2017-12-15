package com.shinaier.laundry.snlfactory.setting.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.StoreInfoEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/12/14.
 */

public class MerchantCardDiscountInfoAdapter extends BaseAdapterNew<StoreInfoEntity.StoreInfoResult.StoreInfoCards> {
    public MerchantCardDiscountInfoAdapter(Context context, List<StoreInfoEntity.StoreInfoResult.StoreInfoCards> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.merchant_card_discount_info_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        StoreInfoEntity.StoreInfoResult.StoreInfoCards item = getItem(position);
        TextView merchantCardDiscount = ViewHolder.get(convertView,R.id.merchant_card_discount);

        if (item != null){
            merchantCardDiscount.setText(item.getCardName() + "：" + item.getDiscount() + "折 充值"
            + item.getPrice() + "元");

        }
    }
}
