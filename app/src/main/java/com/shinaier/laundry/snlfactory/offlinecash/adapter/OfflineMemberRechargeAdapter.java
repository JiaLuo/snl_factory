package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberRechargeListEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/8/5.
 */

public class OfflineMemberRechargeAdapter extends BaseAdapterNew<OfflineMemberRechargeListEntity.OfflineMemberRechargeResult.OfflineMemberRechargeRecord> {
    public OfflineMemberRechargeAdapter(Context context, List<OfflineMemberRechargeListEntity.OfflineMemberRechargeResult.OfflineMemberRechargeRecord> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_member_recharge_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OfflineMemberRechargeListEntity.OfflineMemberRechargeResult.OfflineMemberRechargeRecord item = getItem(position);
        TextView tvMemberNum = ViewHolder.get(convertView,R.id.tv_member_num);
        TextView tvDealNum = ViewHolder.get(convertView,R.id.tv_deal_num);
        TextView memberChargeMoney = ViewHolder.get(convertView,R.id.member_charge_money);
        TextView memberRewardMoney = ViewHolder.get(convertView,R.id.member_reward_money);

        if (item != null){
            if (item.getuMobile() != null){
                tvMemberNum.setText("手机号：" + item.getuMobile());
            }else {
                tvMemberNum.setText("手机号：");
            }
            tvDealNum.setText(item.getLogTime());
            memberChargeMoney.setText("￥" + item.getAmount());
            memberRewardMoney.setText( " + " + item.getGive());
        }
    }
}
