package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderSendEntities;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/28.
 */

public class CategorySendInnerAdapter extends BaseAdapterNew<OrderSendEntities.SendData.SendItem> {
    private int type = 0;
    private List<OrderSendEntities.SendData.SendItem> mDatas;

    public void setType(int type){
        this.type = type;
    }

    public CategorySendInnerAdapter(Context context, List<OrderSendEntities.SendData.SendItem> mDatas) {
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
        return R.layout.category_send_inner_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OrderSendEntities.SendData.SendItem item = getItem(position);
        TextView sendClothesName = ViewHolder.get(convertView,R.id.send_clothes_name);
        TextView sendClothesNum = ViewHolder.get(convertView,R.id.send_clothes_num);
        TextView sendClothesPrice = ViewHolder.get(convertView,R.id.send_clothes_price);
        if(item != null){
            sendClothesName.setText(item.getgName());
            if (item.getPutNumber() != null){
                sendClothesNum.setText(item.getPutNumber());
            }else {
                sendClothesNum.setText("");
            }
            sendClothesPrice.setText("￥" + item.getPrice());
        }
    }
}
