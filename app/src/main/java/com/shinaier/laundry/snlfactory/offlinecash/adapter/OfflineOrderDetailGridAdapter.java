package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.Constants;

import java.util.List;


/**
 * Created by 张家洛 on 2017/8/1.
 */

public class OfflineOrderDetailGridAdapter extends BaseAdapterNew<String> {
    public OfflineOrderDetailGridAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_order_detail_grid_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        String item = getItem(position);
        if(item != null){
            SimpleDraweeView orderDetailGridImg = ViewHolder.get(convertView,R.id.order_detail_grid_img);
            String imgPath = Constants.Urls.URL_BASE_DOMAIN + item;
            orderDetailGridImg.setImageURI(Uri.parse(imgPath));
        }
    }
}
