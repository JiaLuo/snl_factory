package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;

import java.util.List;


/**
 * Created by 张家洛 on 2018/1/15.
 */

public class OfflineRefluxInnerAdapter extends BaseAdapterNew<String> {
    public OfflineRefluxInnerAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_reflux_inner_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        String item = getItem(position);

        SimpleDraweeView ivClothesImg = ViewHolder.get(convertView,R.id.iv_clothes_img);

        if (item != null){
            ivClothesImg.setImageURI(Uri.parse(item));
        }
    }
}
