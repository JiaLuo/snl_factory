package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.StatisticsNoPayEntity;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.List;


/**
 * Created by 张家洛 on 2017/8/1.
 */

public class StatisticsNoDoneAdapter extends BaseAdapterNew<StatisticsNoPayEntity.StatisticsResult> {
    private Context context;

    public interface PositionListener{
        void onClick(int position, int innerPosition);
    }
    private PositionListener listener;
    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }

    public StatisticsNoDoneAdapter(Context context, List<StatisticsNoPayEntity.StatisticsResult> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.statistics_no_pay_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        final int outSide = position;
        StatisticsNoPayEntity.StatisticsResult item = getItem(position);
        TextView statisticsItemHead = ViewHolder.get(convertView,R.id.statistics_item_head);
        WrapHeightListView statisticsNoPayInner = ViewHolder.get(convertView,R.id.statistics_no_pay_inner);

        if (item != null){
            statisticsItemHead.setText(item.getDate());
            if (item.getInnerOrders() != null && item.getInnerOrders().size() > 0){
                StatisticsNoDoneInnerAdapter statisticsNoDoneInnerAdapter = new StatisticsNoDoneInnerAdapter(context,item.getInnerOrders());
                statisticsNoPayInner.setAdapter(statisticsNoDoneInnerAdapter);
                statisticsNoDoneInnerAdapter.setInnerPositionListener(new StatisticsNoDoneInnerAdapter.InnerPositionListener() {
                    @Override
                    public void onInnerClick(int position) {
                        if(listener != null){
                            listener.onClick(outSide,position);
                        }
                    }
                });
            }
        }
    }
}
