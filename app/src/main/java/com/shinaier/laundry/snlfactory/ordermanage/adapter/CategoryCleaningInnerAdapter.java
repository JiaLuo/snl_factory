package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleaningEntities;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/28.
 */

public class CategoryCleaningInnerAdapter extends BaseAdapterNew<OrderCleaningEntities.OrderCleaningResult.OrderCleaningItems> {
    private int type = 0;
    private List<OrderCleaningEntities.OrderCleaningResult.OrderCleaningItems> mDatas;

    public void setType(int type){
        this.type = type;
    }

    public CategoryCleaningInnerAdapter(Context context, List<OrderCleaningEntities.OrderCleaningResult.OrderCleaningItems> mDatas) {
        super(context, mDatas);
        this.mDatas = mDatas;
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
        return R.layout.category_cleaning_inner_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OrderCleaningEntities.OrderCleaningResult.OrderCleaningItems item = getItem(position);
        TextView cleaningClothesName = ViewHolder.get(convertView,R.id.cleaning_clothes_name);
        TextView cleaningClothesNum = ViewHolder.get(convertView,R.id.cleaning_clothes_num);
        TextView cleaningClothesPrice = ViewHolder.get(convertView,R.id.cleaning_clothes_price);
        if(item != null){
            cleaningClothesName.setText(item.getItemName());
            if (item.getPutSn() != null){
                cleaningClothesNum.setText(item.getPutSn());
            }else {
                cleaningClothesNum.setText("");
            }
            cleaningClothesPrice.setText("￥" + item.getItemPrice());
        }
    }
}
