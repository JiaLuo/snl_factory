package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.CashBackEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/2/18.
 */

public class CashBackAdapter extends BaseAdapterNew<CashBackEntity.CashBackResult.CashBackList> {
    public CashBackAdapter(Context context, List<CashBackEntity.CashBackResult.CashBackList> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.cash_back_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        CashBackEntity.CashBackResult.CashBackList item = getItem(position);
        TextView cashBackName = ViewHolder.get(convertView,R.id.cash_back_name);
        TextView cashBackTime = ViewHolder.get(convertView,R.id.cash_back_time);
        TextView cashBackNum = ViewHolder.get(convertView,R.id.cash_back_num);

        if(item != null){
            cashBackName.setText(item.getuName());
            cashBackTime.setText(item.getUpdateTime());
            cashBackNum.setText("￥" + item.getValue());
        }
    }
}
