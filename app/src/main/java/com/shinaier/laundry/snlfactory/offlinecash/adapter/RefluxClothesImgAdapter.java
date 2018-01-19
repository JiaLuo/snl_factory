package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;

import java.util.List;



/**
 * Created by 张家洛 on 2017/3/2.
 */

public class RefluxClothesImgAdapter extends BaseAdapterNew<String> {
    private DeletePhotoListener listener;
    public interface DeletePhotoListener{
        void onDelete(int position);
    }

    public void setDeletePhotoListener(DeletePhotoListener listener){
        this.listener = listener;
    }
    private List<String> mDatas;

    public RefluxClothesImgAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        if(mDatas.size() < 3){
            return mDatas.size() + 1;
        }else {
            return 3;
        }
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.reflux_clothes_img_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        SimpleDraweeView ivClothesImg = ViewHolder.get(convertView,R.id.iv_clothes_img);
        ImageView ivRefluxDeletePhoto = ViewHolder.get(convertView,R.id.iv_reflux_delete_photo);
        if (mDatas.size() == 0){
            ivClothesImg.setImageResource(R.drawable.plus_photo);
            ivRefluxDeletePhoto.setVisibility(View.GONE);
        }else{
            if (position < mDatas.size()){
                ivClothesImg.setImageURI(Uri.parse(getItem(position)));
                ivRefluxDeletePhoto.setVisibility(View.VISIBLE);
                ivRefluxDeletePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null){
                            listener.onDelete(position);
                        }
                    }
                });

            }else if(position == mDatas.size()){
                ivClothesImg.setImageResource(R.drawable.plus_photo);
                ivRefluxDeletePhoto.setVisibility(View.GONE);
            }
        }
    }
}
