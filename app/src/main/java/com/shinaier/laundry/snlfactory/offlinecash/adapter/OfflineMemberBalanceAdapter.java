package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberBalanceEntity;
import com.shinaier.laundry.snlfactory.util.TimeUtils;

import java.util.List;


/**
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberBalanceAdapter extends BaseAdapterNew<OfflineMemberBalanceEntity.OfflineMemberBalanceDatas.MemberList> {
    public OfflineMemberBalanceAdapter(Context context, List<OfflineMemberBalanceEntity.OfflineMemberBalanceDatas.MemberList> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_member_balance_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OfflineMemberBalanceEntity.OfflineMemberBalanceDatas.MemberList item = getItem(position);
        TextView tvMemberNumInfo = ViewHolder.get(convertView,R.id.tv_member_num_info);
        TextView tvMemberNameInfo = ViewHolder.get(convertView,R.id.tv_member_name_info);
        TextView tvMemberPhoneInfo = ViewHolder.get(convertView,R.id.tv_member_phone_info);
        TextView tvMemberBalance = ViewHolder.get(convertView,R.id.tv_member_balance);
        TextView tvMemberBalanceType = ViewHolder.get(convertView,R.id.tv_member_balance_type);
        TextView tvMemberDealTime = ViewHolder.get(convertView,R.id.tv_member_deal_time);

        if (item != null){
            tvMemberNumInfo.setText(item.getUcode());
            tvMemberNameInfo.setText(item.getUserName());
            tvMemberPhoneInfo.setText(item.getMobile());
            tvMemberBalance.setText("￥" + item.getBalance());
            tvMemberBalanceType.setText(item.getCardName());
            tvMemberDealTime.setText("办理时间：" + TimeUtils.formatTime(item.getRegisterTime()));
        }
    }
}
