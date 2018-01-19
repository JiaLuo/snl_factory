package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.WashingEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2018/1/11.
 */

public class OfflineWashingAdapter extends BaseAdapterNew<WashingEntity.WashingResult> {
    private Context context;

    public boolean isTrue = false;
    private SelectWashingListener selectWashingListener;
    public interface SelectWashingListener{
        void onSelect(int position, ImageView imageView);
    }

    public void setSelectListener(SelectWashingListener selectWashingListener){
        this.selectWashingListener = selectWashingListener;
    }
    public OfflineWashingAdapter(Context context, List<WashingEntity.WashingResult> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_washing_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        WashingEntity.WashingResult item = getItem(position);
        LinearLayout llItemWashingList = ViewHolder.get(convertView,R.id.ll_item_washing_list);
        final ImageView ivItemSelectWashing = ViewHolder.get(convertView,R.id.iv_item_select_washing);
        TextView tvClothesCleanNumWashing = ViewHolder.get(convertView,R.id.tv_clothes_clean_num_washing);
        TextView itemClothesNameWashing = ViewHolder.get(convertView,R.id.item_clothes_name_washing);

        if (item != null){
            tvClothesCleanNumWashing.setText(item.getCleanSn());
            itemClothesNameWashing.setText(item.getItemName());
            if (item.getAssist() != null){
                if (item.getAssist().equals("1")){
                    tvClothesCleanNumWashing.setTextColor(context.getResources().getColor(R.color.txt_gray));
                    itemClothesNameWashing.setTextColor(context.getResources().getColor(R.color.txt_gray));
                    ivItemSelectWashing.setVisibility(View.INVISIBLE);
                }else {
                    tvClothesCleanNumWashing.setTextColor(context.getResources().getColor(R.color.black_text));
                    itemClothesNameWashing.setTextColor(context.getResources().getColor(R.color.black_text));
                    ivItemSelectWashing.setVisibility(View.VISIBLE);
                }
            }
            llItemWashingList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectWashingListener != null){
                        selectWashingListener.onSelect(position,ivItemSelectWashing);
                    }
                }
            });

            if (item.getAssist().equals("0")){
                if (item.isSelect){
                    ivItemSelectWashing.setSelected(true);
                }else{
                    ivItemSelectWashing.setSelected(false);
                }
            }else{
                ivItemSelectWashing.setSelected(false);
            }
        }

    }
}
