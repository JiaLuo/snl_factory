package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.ManageCommodityEntities;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/14.
 */

public class ManageCommodityAdapter extends BaseAdapterNew<ManageCommodityEntities.ItemType> {
    private PositionListener listener;
    public interface PositionListener{
        void onClick(int position, int innerPosition);
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }

    private Context context;
    public ManageCommodityAdapter(Context context, List<ManageCommodityEntities.ItemType> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.manage_commodity_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        ManageCommodityEntities.ItemType item = getItem(position);
        TextView serveName = ViewHolder.get(convertView,R.id.serve_name);
        TextView priceNum = ViewHolder.get(convertView,R.id.price_num);
        TextView washTime = ViewHolder.get(convertView,R.id.wash_time);
        WrapHeightListView commodityManageInner = ViewHolder.get(convertView,R.id.commodity_manage_inner);
        if(item != null){
            serveName.setText(item.getErName());
            priceNum.setText("价格");
            washTime.setText("洗护周期");

            CommodityInnerAdapter commodityInnerAdapter = new CommodityInnerAdapter(context,item.getItem());
            commodityManageInner.setAdapter(commodityInnerAdapter);

            commodityInnerAdapter.setInnerPositionListener(new CommodityInnerAdapter.InnerPositionListener() {
                @Override
                public void onInnerClick(int innerPosition) {
                    if(listener != null){
                        listener.onClick(position,innerPosition);
                    }
                }
            });
        }
    }
}
