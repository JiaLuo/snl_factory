package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineRefluxEntity;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/15.
 */

public class OfflineRefluxAdapter extends BaseAdapterNew<OfflineRefluxEntity.OfflineRefluxResult> {
    private OnSelectListener listener;
    public interface OnSelectListener{
        void onSelect(int position);
    }

    public void setOnSelectListener(OnSelectListener listener){
        this.listener = listener;
    }

    private Context context;
    public OfflineRefluxAdapter(Context context, List<OfflineRefluxEntity.OfflineRefluxResult> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.reflux_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OfflineRefluxEntity.OfflineRefluxResult item = getItem(position);
        SimpleDraweeView refluxItemImg = ViewHolder.get(convertView,R.id.reflux_item_img);
        TextView refluxItemName = ViewHolder.get(convertView,R.id.reflux_item_name);
        TextView refluxItemNumber = ViewHolder.get(convertView,R.id.reflux_item_number);
        ImageView selectRefluxItem = ViewHolder.get(convertView,R.id.select_reflux_item);
        WrapHeightGridView refluxItemGv = ViewHolder.get(convertView,R.id.reflux_item_gv);
        TextView refluxItemClothesInfo = ViewHolder.get(convertView,R.id.reflux_item_clothes_info);
        TextView refluxItemIsNormal = ViewHolder.get(convertView,R.id.reflux_item_is_normal);
        TextView refluxItemStep = ViewHolder.get(convertView,R.id.reflux_item_step);


        if (item != null){
            refluxItemImg.setImageURI(Uri.parse(item.getImage()));
            refluxItemName.setText(item.getItemName());
            refluxItemNumber.setText("衣物编码" + item.getCleanSn());

            if (item.getBackImg() != null && item.getBackImg().size() > 0){
                OfflineRefluxInnerAdapter offlineRefluxInnerAdapter = new OfflineRefluxInnerAdapter(context,item.getBackImg());
                refluxItemGv.setAdapter(offlineRefluxInnerAdapter);
            }

            refluxItemClothesInfo.setText(item.getBackContent());
            if (item.getIsBack().equals("1")){
                refluxItemIsNormal.setText("是");
            }else {
                refluxItemIsNormal.setText("否");
            }
            refluxItemStep.setText(item.getBackName());

            selectRefluxItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onSelect(position);
                    }
                }
            });

            if (item.isSelect){
                selectRefluxItem.setSelected(true);
            }else {
                selectRefluxItem.setSelected(false);
            }
        }
    }
}
