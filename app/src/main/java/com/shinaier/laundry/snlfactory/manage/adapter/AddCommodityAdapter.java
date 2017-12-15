package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.AddCommodityEntities;

import java.util.List;

/**
 * 添加商品item
 * Created by 张家洛 on 2017/12/14.
 */

public class AddCommodityAdapter extends BaseAdapterNew<AddCommodityEntities.AddCommodityResult> {
    public AddCommodityAdapter(Context context, List<AddCommodityEntities.AddCommodityResult> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.add_commodity_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        AddCommodityEntities.AddCommodityResult item = getItem(position);
        ImageView ivAddCommodity = ViewHolder.get(convertView,R.id.iv_add_commodity);
        TextView tvAddCommodityName = ViewHolder.get(convertView,R.id.tv_add_commodity_name);
        if (item != null){
            tvAddCommodityName.setText(item.getItemName());
            if (item.isChanged){
                ivAddCommodity.setSelected(true);
            }else {
                ivAddCommodity.setSelected(false);
            }
        }

    }
}
