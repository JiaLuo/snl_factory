package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineHangOnEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/8/3.
 */

public class OfflineHangOnAdapter extends BaseAdapterNew<OfflineHangOnEntity.OfflineHangOnResult> {
    private Context context;
    private SelectListener selectListener;

    public interface SelectListener{
        void onSelect(int position,ImageView imageView);
    }

    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener;
    }
    public OfflineHangOnAdapter(Context context, List<OfflineHangOnEntity.OfflineHangOnResult> mDatas) {
        super(context, mDatas);
        this.context = context;
    }


    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_hang_on_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OfflineHangOnEntity.OfflineHangOnResult item = getItem(position);
        final ImageView ivItemSelectHangOn = ViewHolder.get(convertView,R.id.iv_item_select_hang_on);
        TextView tvOrderNumHangOn = ViewHolder.get(convertView,R.id.tv_order_num_hang_on);
        TextView itemClothesNameHangOn = ViewHolder.get(convertView,R.id.item_clothes_name_hang_on);
        LinearLayout llItemSelectHangOn = ViewHolder.get(convertView,R.id.ll_item_select_hang_on);

        if (item != null){
            tvOrderNumHangOn.setText(item.getCleanSn());
            itemClothesNameHangOn.setText(item.getItemName());

            if (item.getState().equals("1") && item.getAssist().equals("0")){
                //提醒
                llItemSelectHangOn.setBackgroundColor(context.getResources().getColor(R.color.clothes_warn));
                tvOrderNumHangOn.setTextColor(context.getResources().getColor(R.color.black_text));
                itemClothesNameHangOn.setTextColor(context.getResources().getColor(R.color.black_text));
                ivItemSelectHangOn.setVisibility(View.VISIBLE);
            }else if (item.getState().equals("2") && item.getAssist().equals("0")){
                //预警
                llItemSelectHangOn.setBackgroundColor(context.getResources().getColor(R.color.clothes_early_warn));
                tvOrderNumHangOn.setTextColor(context.getResources().getColor(R.color.black_text));
                itemClothesNameHangOn.setTextColor(context.getResources().getColor(R.color.black_text));
                ivItemSelectHangOn.setVisibility(View.VISIBLE);
            } else if (item.getState().equals("0") && item.getAssist().equals("0")){
                llItemSelectHangOn.setBackgroundColor(context.getResources().getColor(R.color.white));
                tvOrderNumHangOn.setTextColor(context.getResources().getColor(R.color.black_text));
                itemClothesNameHangOn.setTextColor(context.getResources().getColor(R.color.black_text));
                ivItemSelectHangOn.setVisibility(View.VISIBLE);
            } else if (item.getAssist().equals("1")){
                if (item.getState().equals("1")){
                    llItemSelectHangOn.setBackgroundColor(context.getResources().getColor(R.color.clothes_warn));
                }else if (item.getState().equals("2")){
                    llItemSelectHangOn.setBackgroundColor(context.getResources().getColor(R.color.clothes_early_warn));
                }else {
                    llItemSelectHangOn.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
                tvOrderNumHangOn.setTextColor(context.getResources().getColor(R.color.txt_gray));
                itemClothesNameHangOn.setTextColor(context.getResources().getColor(R.color.txt_gray));
                ivItemSelectHangOn.setVisibility(View.INVISIBLE);
            }else {
                llItemSelectHangOn.setBackgroundColor(context.getResources().getColor(R.color.white));
                tvOrderNumHangOn.setTextColor(context.getResources().getColor(R.color.black_text));
                itemClothesNameHangOn.setTextColor(context.getResources().getColor(R.color.black_text));
                ivItemSelectHangOn.setVisibility(View.VISIBLE);
            }
            llItemSelectHangOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectListener != null){
                        selectListener.onSelect(position,ivItemSelectHangOn);
                    }
                }
            });
            if (item.getAssist().equals("0")){
                if (item.isSelect){
                    ivItemSelectHangOn.setSelected(true);
                }else{
                    ivItemSelectHangOn.setSelected(false);
                }
            }else{
                ivItemSelectHangOn.setSelected(false);
            }
        }
    }
}
