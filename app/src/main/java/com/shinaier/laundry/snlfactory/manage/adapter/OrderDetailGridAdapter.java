package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;

import java.util.List;



/**
 * Created by 张家洛 on 2016/12/16.
 */

public class OrderDetailGridAdapter extends BaseAdapterNew<String> {

    public OrderDetailGridAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.order_detail_grid;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        String item = getItem(position);
        if(item != null){
            SimpleDraweeView orderDetailGridImg = ViewHolder.get(convertView,R.id.order_detail_grid_img);
//            String img = item.getImg();
//            String imgPath = Constants.Urls.URL_BASE_DOMAIN + img;
            orderDetailGridImg.setImageURI(Uri.parse(item));
        }
    }
}
