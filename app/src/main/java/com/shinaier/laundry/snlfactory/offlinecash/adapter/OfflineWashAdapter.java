package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.common.utils.LogUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.WashEntity;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/11.
 */

public class OfflineWashAdapter extends BaseAdapterNew<WashEntity.WashResult> {
    private Context context;
    public boolean isTrue = false;
    private SelectListener selectListener;

    public interface SelectListener{
        void onSelect(int position, ImageView imageView);
    }
    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener;
    }
    public OfflineWashAdapter(Context context, List<WashEntity.WashResult> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    public void allSelect(boolean isTrue){
        this.isTrue = isTrue;
        this.notifyDataSetChanged();
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_wash_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        WashEntity.WashResult item = getItem(position);
        final ImageView ivItemSelect = ViewHolder.get(convertView,R.id.iv_item_select);
        TextView tvClothesCleanNum = ViewHolder.get(convertView,R.id.tv_clothes_clean_num);
        TextView itemClothesName = ViewHolder.get(convertView,R.id.item_clothes_name);
        LinearLayout llItemFlowList = ViewHolder.get(convertView,R.id.ll_item_flow_list);

        if (item != null){
            tvClothesCleanNum.setText(item.getCleanSn());
            itemClothesName.setText(item.getItemName());
            if (item.getAssist() != null || item.getCleanState() != null){
                if (item.getAssist().equals("1")){
                    tvClothesCleanNum.setTextColor(context.getResources().getColor(R.color.txt_gray));
                    itemClothesName.setTextColor(context.getResources().getColor(R.color.txt_gray));
                    ivItemSelect.setVisibility(View.INVISIBLE);
                }else {
                    tvClothesCleanNum.setTextColor(context.getResources().getColor(R.color.black_text));
                    itemClothesName.setTextColor(context.getResources().getColor(R.color.black_text));
                    ivItemSelect.setVisibility(View.VISIBLE);
                }
            }
            llItemFlowList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectListener != null){
                        selectListener.onSelect(position,ivItemSelect);
                    }
                }
            });
            if(isTrue){
                if (item.getAssist().equals("0")){
                    ivItemSelect.setSelected(true);
                    item.isSelect = true;
                    LogUtil.e("zhang","bbbbbbbbbbbbbbbbbbb");
                }else {
                    ivItemSelect.setSelected(false);
                    item.isSelect = false;
                    LogUtil.e("zhang","cccccccccccccccccc");
                }
            }else {
                ivItemSelect.setSelected(false);
                item.isSelect = false;
            }
        }

    }
}
