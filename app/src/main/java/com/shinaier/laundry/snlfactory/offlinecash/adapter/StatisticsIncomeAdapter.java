package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.StatisticsIncomeEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/8/25.
 */

public class StatisticsIncomeAdapter extends BaseAdapterNew<StatisticsIncomeEntity.StatisticsIncomeDatas.StatisticsIncomeRecord> {
    public StatisticsIncomeAdapter(Context context, List<StatisticsIncomeEntity.StatisticsIncomeDatas.StatisticsIncomeRecord> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.statistics_income_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        StatisticsIncomeEntity.StatisticsIncomeDatas.StatisticsIncomeRecord item = getItem(position);
        TextView statisticsItemHead = ViewHolder.get(convertView,R.id.statistics_item_head);
        TextView statisticsItemMoneyPlatform = ViewHolder.get(convertView,R.id.statistics_item_money_platform);
        TextView statisticsItemMoneyWxpay = ViewHolder.get(convertView,R.id.statistics_item_money_wxpay);
        TextView statisticsItemMoneyAlipay = ViewHolder.get(convertView,R.id.statistics_item_money_alipay);
        TextView statisticsItemMoneyMerchant = ViewHolder.get(convertView,R.id.statistics_item_money_merchant);
        TextView statisticsItemMoneyCash = ViewHolder.get(convertView,R.id.statistics_item_money_cash);

        if (item != null){
            statisticsItemHead.setText(item.getNowDate());
            statisticsItemMoneyPlatform.setText("￥" + item.getPlatformCard());
            statisticsItemMoneyWxpay.setText("￥" + item.getWechat());
            statisticsItemMoneyAlipay.setText("￥" + item.getAlipay());
            statisticsItemMoneyMerchant.setText("￥" + item.getMerchantCard());
            statisticsItemMoneyCash.setText("￥" + item.getCash());
        }
    }
}
