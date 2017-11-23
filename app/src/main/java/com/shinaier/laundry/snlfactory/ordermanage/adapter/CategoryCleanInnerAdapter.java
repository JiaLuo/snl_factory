package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleanEntities;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/25.
 */

public class CategoryCleanInnerAdapter extends BaseAdapterNew<OrderCleanEntities.CleanData.CleanItem> {
    private int type = 0;
    private List<OrderCleanEntities.CleanData.CleanItem> mDatas;

    public CategoryCleanInnerAdapter(Context context, List<OrderCleanEntities.CleanData.CleanItem> mDatas) {
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
        return R.layout.category_clean_inner_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OrderCleanEntities.CleanData.CleanItem item = getItem(position);
        TextView cleanClothesName = ViewHolder.get(convertView,R.id.clean_clothes_name);
        TextView cleanClothesNum = ViewHolder.get(convertView,R.id.clean_clothes_num);
        TextView cleanClothesPrice = ViewHolder.get(convertView,R.id.clean_clothes_price);

        if(item != null){
            cleanClothesName.setText(item.getgName());
            cleanClothesNum.setText("x" + item.getNumber());
            cleanClothesPrice.setText("￥" + item.getPrice());
        }
    }
}
