package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberRechargeListEntity;
import com.shinaier.laundry.snlfactory.util.TimeUtils;

import java.util.List;


/**
 * Created by 张家洛 on 2017/8/5.
 */

public class OfflineMemberRechargeAdapter extends BaseAdapterNew<OfflineMemberRechargeListEntity.OfflineMemberRechargeDatas.OfflineMemberRechargeRecord> {
    public OfflineMemberRechargeAdapter(Context context, List<OfflineMemberRechargeListEntity.OfflineMemberRechargeDatas.OfflineMemberRechargeRecord> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_member_recharge_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OfflineMemberRechargeListEntity.OfflineMemberRechargeDatas.OfflineMemberRechargeRecord item = getItem(position);
        TextView tvMemberNum = ViewHolder.get(convertView,R.id.tv_member_num);
        TextView tvDealNum = ViewHolder.get(convertView,R.id.tv_deal_num);
        TextView memberChargeMoney = ViewHolder.get(convertView,R.id.member_charge_money);
        TextView memberRewardMoney = ViewHolder.get(convertView,R.id.member_reward_money);

        if (item != null){
            if (item.getUcode() != null){
                tvMemberNum.setText("会员卡号：" + item.getUcode());
            }else {
                tvMemberNum.setText("会员卡号：");
            }
            tvDealNum.setText(TimeUtils.formatTime(item.getRechargeTime()));
            memberChargeMoney.setText("￥" + item.getRechargeAmount());
            memberRewardMoney.setText( " + " + item.getGive());
        }
    }
}
