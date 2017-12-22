package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.common.utils.DeviceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.util.CommonTools;

import java.util.List;


/**
 * Created by 张家洛 on 2017/3/15.
 */

public class BigPhotoAdapter extends BaseAdapterNew<String> {
    private int mImageWidth;
    private int mImageHeight;
    public BigPhotoAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
        mImageWidth = DeviceUtil.getWidth(context);
//        mImageHeight = (int) (mImageWidth * ((float) 300 / (float) 480));
        mImageHeight = DeviceUtil.getHeight(context) - CommonTools.getStatusBarHeight(context) - CommonTools.dp2px(context,44);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.big_photo_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        SimpleDraweeView imgSideslip = ViewHolder.get(convertView,R.id.img_sideslip);
        ViewGroup.LayoutParams layoutParams = imgSideslip.getLayoutParams();
        layoutParams.width = mImageWidth;
        layoutParams.height = mImageHeight;
        imgSideslip.setPadding(0, 0, 0, 0);
//        imgSideslip.setScaleType(SimpleDraweeView.ScaleType.FIT_CENTER);
        imgSideslip.setImageURI(Uri.parse(getItem(position)));
    }
}
