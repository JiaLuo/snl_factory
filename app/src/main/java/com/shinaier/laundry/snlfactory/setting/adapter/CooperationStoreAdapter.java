package com.shinaier.laundry.snlfactory.setting.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.setting.entities.CooperationStoreEntity;

import java.util.List;

/**
 * Created by 张家洛 on 2017/10/30.
 */

public class CooperationStoreAdapter extends BaseAdapterNew<CooperationStoreEntity> {
    private EditStoreListener listener;
    private boolean isShow = false;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public interface EditStoreListener{
        void onClick(int position);
    }
    public void setEditStoreListener(EditStoreListener listener){
        this.listener = listener;
    }

    public CooperationStoreAdapter(Context context, List<CooperationStoreEntity> mDatas) {
        super(context, mDatas);
    }

    public void setIsShow(boolean isShow){
        this.isShow = isShow;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.cooperation_store_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        CooperationStoreEntity item = getItem(position);
        final ImageView ivItemSelect = ViewHolder.get(convertView,R.id.iv_item_select);
        TextView tvStoreNum = ViewHolder.get(convertView,R.id.tv_store_num);
        TextView tvStoreName = ViewHolder.get(convertView,R.id.tv_store_name);
        LinearLayout llSelectStore = ViewHolder.get(convertView,R.id.ll_select_store);


        if (isShow){
            ivItemSelect.setVisibility(View.VISIBLE);
        }else {
            ivItemSelect.setVisibility(View.GONE);
        }
        llSelectStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });

        if (item != null){
            tvStoreNum.setText(item.getNum());
            tvStoreName.setText(item.getName());
            if(item.isSelect){
                ivItemSelect.setSelected(true);
            }else {
                ivItemSelect.setSelected(false);
            }
        }
    }
}
