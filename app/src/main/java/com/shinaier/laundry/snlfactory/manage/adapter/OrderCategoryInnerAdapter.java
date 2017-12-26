package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderSearchEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/12/25.
 */

public class OrderCategoryInnerAdapter extends BaseAdapterNew<OrderSearchEntity.OrderSearchResult.OrderSearchItems> {
    private int type = 0;
    private List<OrderSearchEntity.OrderSearchResult.OrderSearchItems> mDatas;
    public OrderCategoryInnerAdapter(Context context, List<OrderSearchEntity.OrderSearchResult.OrderSearchItems> mDatas) {
        super(context, mDatas);
        this.mDatas = mDatas;
    }

    public void setType(int type){
        this.type = type;
    }

    @Override
    public int getCount() {
        if (type == 0){
            if (mDatas.size() > 2){
                return 2;
            }else {
                return mDatas.size();
            }
        }else{
            return mDatas.size();
        }
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.order_category_inner_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OrderSearchEntity.OrderSearchResult.OrderSearchItems item = getItem(position);
        TextView clothesName = ViewHolder.get(convertView,R.id.clothes_name);
        TextView clothesNum = ViewHolder.get(convertView,R.id.clothes_num);
        TextView clothesPrice = ViewHolder.get(convertView,R.id.clothes_price);

        if(item != null){
            clothesName.setText(item.getItemName());
//            clothesNum.setText("x" +item.getNumber());
            clothesPrice.setText("￥" + item.getItemPrice());
        }
    }
}
