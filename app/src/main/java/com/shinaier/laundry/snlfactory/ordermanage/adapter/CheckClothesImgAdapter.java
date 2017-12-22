package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.Constants;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/2.
 */

public class CheckClothesImgAdapter extends BaseAdapterNew<String> {
    private Context context;
    private List<String> mDatas;

    public CheckClothesImgAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        if(mDatas.size() < 4){
            return mDatas.size() + 1;
        }else {
            return 4;
        }
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.check_clothes_img_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        SimpleDraweeView ivClothesImg = ViewHolder.get(convertView,R.id.iv_clothes_img);

        RelativeLayout rlShowPhotoNum = ViewHolder.get(convertView,R.id.rl_show_photo_num);
        TextView photoNums = ViewHolder.get(convertView,R.id.photo_nums);
        if (mDatas.size() == 0){
            ivClothesImg.setBackground(context.getResources().getDrawable(R.drawable.plus_photo));
        }else if(mDatas.size() >= 4){
            if (position < 3){
                //                String imgPath = Constants.Urls.URL_BASE_DOMAIN + getItem(position);
//                Uri uri = Uri.parse(imgPath);
                ivClothesImg.setImageURI(Uri.parse(getItem(position)));
                if(position == 2){
                    rlShowPhotoNum.setVisibility(View.VISIBLE);
                    if(mDatas.size() >= 12){
                        photoNums.setText("3/12");
                    }else {
                        photoNums.setText("3/" + mDatas.size() );
                    }
                }
            }else if(position == 3){
                ivClothesImg.setImageResource(R.drawable.edit_photo);
            }
        } else{
            if (position < mDatas.size()){
                String imgPath = Constants.Urls.URL_BASE_DOMAIN + getItem(position);
                Uri uri = Uri.parse(imgPath);
                ivClothesImg.setImageURI(Uri.parse(getItem(position)));
            }else if(position == mDatas.size()){
                ivClothesImg.setImageResource(R.drawable.edit_photo);
            }
        }
    }
}
