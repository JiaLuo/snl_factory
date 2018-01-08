package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberConsumeListEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberConsumeAdapter extends BaseAdapterNew<OfflineMemberConsumeListEntity.OfflineMemberConsumeResult.OfflineMemberConsumeRecord> {
    public OfflineMemberConsumeAdapter(Context context, List<OfflineMemberConsumeListEntity.OfflineMemberConsumeResult.OfflineMemberConsumeRecord> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_member_consume_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OfflineMemberConsumeListEntity.OfflineMemberConsumeResult.OfflineMemberConsumeRecord item = getItem(position);
        TextView tvMemberNum = ViewHolder.get(convertView,R.id.tv_member_num);
        TextView tvDealNum = ViewHolder.get(convertView,R.id.tv_deal_num);
        TextView tvDealMoney = ViewHolder.get(convertView,R.id.tv_deal_money);
        TextView tvDealTime = ViewHolder.get(convertView,R.id.tv_deal_time);

        if (item != null){
            if (item.getuMobile() != null){
                tvMemberNum.setText("手机号：" + item.getuMobile());
            }else {
                tvMemberNum.setText("手机号：");
            }
            tvDealNum.setText("交易单号：" +item.getOrdersn());
            tvDealMoney.setText("￥" + item.getPayAmount());
            tvDealTime.setText(item.getPayTime());
        }
    }
}
