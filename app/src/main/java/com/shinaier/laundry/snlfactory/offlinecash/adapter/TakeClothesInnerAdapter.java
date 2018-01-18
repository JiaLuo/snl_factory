package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.TakeClothesEntity;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/25.
 */

public class TakeClothesInnerAdapter extends BaseAdapterNew<TakeClothesEntity.TakeClothesResult.TakeClothesList.TakeClothesItems> {
    private int type = 0;
    private List<TakeClothesEntity.TakeClothesResult.TakeClothesList.TakeClothesItems> mDatas;
    private Context context;

    public TakeClothesInnerAdapter(Context context, List<TakeClothesEntity.TakeClothesResult.TakeClothesList.TakeClothesItems> mDatas) {
        super(context, mDatas);
        this.mDatas = mDatas;
        this.context = context;
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
        return R.layout.take_clothes_inner_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        TakeClothesEntity.TakeClothesResult.TakeClothesList.TakeClothesItems item = getItem(position);
        TextView takeClothesName = ViewHolder.get(convertView,R.id.take_clothes_name);
        TextView takeClothesStatus = ViewHolder.get(convertView,R.id.take_clothes_status);
        TextView takeClothesNumber = ViewHolder.get(convertView,R.id.take_clothes_number);

        if(item != null){
            takeClothesName.setText(item.getItemName());
            if(item.getStatus().equals("1")){
                takeClothesStatus.setText("已收衣");
                takeClothesName.setTextColor(context.getResources().getColor(R.color.black_text));
                takeClothesStatus.setTextColor(context.getResources().getColor(R.color.black_text));
            }else if(item.getStatus().equals("3")){
                takeClothesStatus.setText("清洗中");
                takeClothesName.setTextColor(context.getResources().getColor(R.color.black_text));
                takeClothesStatus.setTextColor(context.getResources().getColor(R.color.black_text));
            }else if(item.getStatus().equals("5")){
                takeClothesStatus.setText("熨烫中");
                takeClothesName.setTextColor(context.getResources().getColor(R.color.black_text));
                takeClothesStatus.setTextColor(context.getResources().getColor(R.color.black_text));
            }
            else if(item.getStatus().equals("7")){
                takeClothesStatus.setText("已上挂");
                takeClothesName.setTextColor(context.getResources().getColor(R.color.black_text));
                takeClothesStatus.setTextColor(context.getResources().getColor(R.color.red));
            }else if(item.getStatus().equals("9")){
                takeClothesStatus.setText("已取走");
                takeClothesName.setTextColor(context.getResources().getColor(R.color.black_text));
                takeClothesStatus.setTextColor(context.getResources().getColor(R.color.store_switch_off));
            }
            if(item.getPutSn() != null){
                takeClothesNumber.setText(item.getPutSn());
            }else {
                takeClothesNumber.setText("");
            }
        }
    }
}
