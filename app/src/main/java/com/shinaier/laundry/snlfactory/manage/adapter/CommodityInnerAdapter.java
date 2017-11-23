package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.ManageCommodityEntities;

import java.util.List;


/**
 * Created by 张家洛 on 2017/2/14.
 */

public class CommodityInnerAdapter extends BaseAdapterNew<ManageCommodityEntities.ItemType.Item> {
    private InnerPositionListener listener;

    public interface InnerPositionListener{
        void onInnerClick(int position);
    }

    public void setInnerPositionListener(InnerPositionListener listener){
        this.listener = listener;
    }

    public CommodityInnerAdapter(Context context, List<ManageCommodityEntities.ItemType.Item> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.commodity_inner_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        ManageCommodityEntities.ItemType.Item item = getItem(position);
        TextView clothesName = ViewHolder.get(convertView,R.id.clothes_name);
        TextView clothesPrice = ViewHolder.get(convertView,R.id.clothes_price);
        TextView clothesTime = ViewHolder.get(convertView,R.id.clothes_time);
        ImageView ivCommodityEdit = ViewHolder.get(convertView,R.id.iv_commodity_edit);

        if(item != null){
            clothesName.setText(item.getName());
            clothesPrice.setText(item.getPrice() + "元");
            if(!TextUtils.isEmpty(item.getCycle())){
                clothesTime.setText("预计" + item.getCycle() + "天");
            }else {
                clothesTime.setText("预计0天");
            }
            ivCommodityEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onInnerClick(position);
                    }
                }
            });
        }
    }
}
