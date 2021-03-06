package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberDetailEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberBalanceInfoAdapter extends BaseAdapterNew<OfflineMemberDetailEntity.OfflineMemberDetailResult.OfflineMemberDetailRecord> {
    public OfflineMemberBalanceInfoAdapter(Context context, List<OfflineMemberDetailEntity.OfflineMemberDetailResult.OfflineMemberDetailRecord> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_member_balance_info_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OfflineMemberDetailEntity.OfflineMemberDetailResult.OfflineMemberDetailRecord item = getItem(position);
        TextView tvBalanceType = ViewHolder.get(convertView,R.id.tv_balance_type);
        TextView tvBalanceTime = ViewHolder.get(convertView,R.id.tv_balance_time);
        TextView tvBalanceMoney = ViewHolder.get(convertView,R.id.tv_balance_money);

        if(item != null){
            if (item.getType().equals("1")){
                tvBalanceType.setText("会员洗衣消费");
                tvBalanceMoney.setText("-￥" + item.getAmount());
            }else {
                tvBalanceType.setText("会员充值");
                tvBalanceMoney.setText("￥" + item.getAmount() + "+" + item.getGive());
            }
            tvBalanceTime.setText(item.getLogTime());
        }
    }
}
