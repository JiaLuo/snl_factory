package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.offlinecash.entities.SingleTakeClothesEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/7/25.
 */

public class SingleTakeClothesAdapter extends BaseAdapterNew<SingleTakeClothesEntity> {
    private SelectListener selectListener;

    public interface SelectListener{
        void onSelect(int position);
    }

    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener;
    }


    public SingleTakeClothesAdapter(Context context, List<SingleTakeClothesEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.single_take_clothes_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        final SingleTakeClothesEntity item = getItem(position);
        ImageView singleTakeClothesSelect = ViewHolder.get(convertView, R.id.single_take_clothes_select);
        TextView singleTakeClothesName = ViewHolder.get(convertView,R.id.single_take_clothes_name);
        TextView singleTakeClothesStatus = ViewHolder.get(convertView,R.id.single_take_clothes_status);
        TextView singleTakeClothesNum = ViewHolder.get(convertView,R.id.single_take_clothes_num);

        if(item != null){
            singleTakeClothesName.setText(item.getName());
            switch (item.getStatus()) {
                case "0":
                    singleTakeClothesStatus.setText("已收衣");
                    break;
                case "1":
                    singleTakeClothesStatus.setText("清洗中");
                    break;
                case "2":
                    singleTakeClothesStatus.setText("已上挂");
                    break;
                case "3":
                    singleTakeClothesStatus.setText("已取走");
                    break;
            }
            singleTakeClothesNum.setText(item.getNumber());

            singleTakeClothesSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectListener != null){
                        selectListener.onSelect(position);
                    }
                }
            });

            if(item.isSelect){
                singleTakeClothesSelect.setSelected(true);
            }else {
                singleTakeClothesSelect.setSelected(false);
            }
        }
    }
}
