package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.ManageFinanceEntities;

import java.util.List;


/**
 * Created by 张家洛 on 2017/2/13.
 */

public class ManageDetailAdapter extends BaseAdapterNew<ManageFinanceEntities.ManageFinanceResult.ManageFinanceRecord> {
    private Context context;
    public ManageDetailAdapter(Context context, List<ManageFinanceEntities.ManageFinanceResult.ManageFinanceRecord> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.manage_detail_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        ManageFinanceEntities.ManageFinanceResult.ManageFinanceRecord item = getItem(position);
        TextView tvIncome = ViewHolder.get(convertView,R.id.tv_income);
        TextView incomeTime = ViewHolder.get(convertView,R.id.income_time);
        TextView tvRevenue = ViewHolder.get(convertView,R.id.tv_revenue);
        TextView tvRevenueNum = ViewHolder.get(convertView,R.id.tv_revenue_num);
        TextView tvCashback = ViewHolder.get(convertView,R.id.tv_cashback);
        TextView tvCashbackNum = ViewHolder.get(convertView,R.id.tv_cashback_num);
        TextView financeBalance = ViewHolder.get(convertView,R.id.finance_balance);

        if(item != null){
            if(item.getType().equals("0")){
                tvIncome.setText("入账");
                tvRevenue.setText("营收");
                tvCashback.setVisibility(View.VISIBLE);
                tvCashbackNum.setVisibility(View.VISIBLE);
                tvCashback.setText("平台服务费");
                tvRevenueNum.setText(item.getAmount() + "-");
                tvCashbackNum.setText(item.getPlatformGain());
                financeBalance.setText("+" +item.getRealAmount());
            }else if(item.getType().equals("1")){
                tvIncome.setText("入账");
                tvRevenue.setText("邀请好友");
                tvCashback.setVisibility(View.INVISIBLE);
                tvCashbackNum.setVisibility(View.INVISIBLE);
                tvRevenueNum.setText("+" +item.getAmount());
                financeBalance.setText("+" + item.getPlatformGain());
            }else if(item.getType().equals("10")){
                tvIncome.setText("出账");
                tvRevenue.setText("平台打款");
                tvRevenueNum.setText(item.getAmount());
                financeBalance.setText(item.getPlatformGain());
                financeBalance.setTextColor(context.getResources().getColor(R.color.base_color));
                tvCashback.setVisibility(View.INVISIBLE);
                tvCashbackNum.setVisibility(View.INVISIBLE);
            }else if (item.getType().equals("2")){
                tvIncome.setText("入账");
                tvRevenue.setText("专店会员卡充值");
                tvCashback.setVisibility(View.INVISIBLE);
                tvCashbackNum.setVisibility(View.INVISIBLE);
                tvRevenueNum.setText("+" +item.getRealAmount());
                financeBalance.setText("+" + item.getRealAmount());
            } else {
                tvIncome.setText("");
                tvRevenue.setText("");
            }
            incomeTime.setText(item.getTradeTime());
        }
    }
}
