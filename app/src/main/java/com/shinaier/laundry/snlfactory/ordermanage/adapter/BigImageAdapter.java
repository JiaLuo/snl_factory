package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.common.utils.DeviceUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OrderDetailEntity;
import com.shinaier.laundry.snlfactory.util.CommonTools;

import java.util.List;


/**
 * Created by 张家洛 on 2017/3/8.
 */

public class BigImageAdapter extends BaseAdapterNew<OrderDetailEntity.OrderDetailItem.Img> {
    private int mImageWidth;
    private int mImageHeight;

    public BigImageAdapter(Context context, List<OrderDetailEntity.OrderDetailItem.Img> mDatas) {
        super(context, mDatas);
        mImageWidth = DeviceUtil.getWidth(context);
//        mImageHeight = (int) (mImageWidth * ((float) 300 / (float) 480));
        mImageHeight = DeviceUtil.getHeight(context) - CommonTools.getStatusBarHeight(context) - CommonTools.dp2px(context,53);
    }

    @Override
    protected int getResourceId(int Position) {
        return  R.layout.big_image_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OrderDetailEntity.OrderDetailItem.Img item = getItem(position);
        ImageView imgSideslip = ViewHolder.get(convertView,R.id.img_sideslip);
        ViewGroup.LayoutParams layoutParams = imgSideslip.getLayoutParams();
        layoutParams.width = mImageWidth;
        layoutParams.height = mImageHeight;
        imgSideslip.setPadding(0, 0, 0, 0);
        imgSideslip.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String path = Constants.Urls.URL_BASE_DOMAIN + item.getImg();
        Uri uri = Uri.parse(path);
        imgSideslip.setImageURI(uri);
    }
}
