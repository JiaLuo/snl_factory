package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineSendLaundryEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/8/3.
 */

public class OfflineSendLaundryAdapter extends BaseAdapterNew<OfflineSendLaundryEntity.OfflineSendLaundryDatas> {
    private SelectListener selectListener;
    public interface SelectListener{
        void onSelect(int position);
    }

    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener;
    }

    public OfflineSendLaundryAdapter(Context context, List<OfflineSendLaundryEntity.OfflineSendLaundryDatas> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_send_laundry_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OfflineSendLaundryEntity.OfflineSendLaundryDatas item = getItem(position);
        ImageView ivItemSelect = ViewHolder.get(convertView,R.id.iv_item_select);
        TextView tvOrderNum = ViewHolder.get(convertView,R.id.tv_order_num);
        TextView itemClothesName = ViewHolder.get(convertView,R.id.item_clothes_name);
        TextView itemClothesNum = ViewHolder.get(convertView,R.id.item_clothes_num);

        if (item != null){
            tvOrderNum.setText(item.getOrdersn());
            itemClothesName.setText(item.getName());
            itemClothesNum.setText(item.getNumber());
            ivItemSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectListener != null){
                        selectListener.onSelect(position);
                    }
                }
            });
            if(item.isSelect){
                ivItemSelect.setSelected(true);
            }else {
                ivItemSelect.setSelected(false);
            }
        }
    }
}
