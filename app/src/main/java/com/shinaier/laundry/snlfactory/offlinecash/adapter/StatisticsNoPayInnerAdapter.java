package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.StatisticsNoPayEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/8/1.
 */

public class StatisticsNoPayInnerAdapter extends BaseAdapterNew<StatisticsNoPayEntity.StatisticsData.StatisticsOrders.StatisticsInnerOrders> {
    public interface InnerPositionListener{
        void onInnerClick(int position);
    }
    private InnerPositionListener listener;
    public void setInnerPositionListener(InnerPositionListener listener){
        this.listener = listener;
    }
    public StatisticsNoPayInnerAdapter(Context context, List<StatisticsNoPayEntity.StatisticsData.StatisticsOrders.StatisticsInnerOrders> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.statistics_no_pay_inner_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        StatisticsNoPayEntity.StatisticsData.StatisticsOrders.StatisticsInnerOrders item = getItem(position);
        TextView statisticsItemPayMode = ViewHolder.get(convertView,R.id.statistics_item_pay_mode);
        TextView statisticsItemMoney = ViewHolder.get(convertView,R.id.statistics_item_money);
        LinearLayout llStatisticsNoPayInner = ViewHolder.get(convertView,R.id.ll_statistics_no_pay_inner);

        if (item != null){
            statisticsItemPayMode.setText("订单号：" + item.getOrdersn());
            if (item.getAmount() != null){
                statisticsItemMoney.setText("￥" + item.getAmount());
            }else {
                statisticsItemMoney.setText("￥0.00");
            }

            llStatisticsNoPayInner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onInnerClick(position);
                    }
                }
            });
        }
    }
}
