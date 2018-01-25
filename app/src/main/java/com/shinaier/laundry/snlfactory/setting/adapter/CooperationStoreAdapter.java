package com.shinaier.laundry.snlfactory.setting.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.CooperativeStoreEntities;

import java.util.List;

/**
 * Created by 张家洛 on 2017/10/30.
 */

public class CooperationStoreAdapter extends BaseAdapterNew<CooperativeStoreEntities.CooperativeStoreResults> {
    private ShowViewListener showViewListener;
    private boolean isDel;
    private boolean isAdd;//如果是搜索页面 用到这个adapter 选择按钮就常显示 否则。。。

    public interface ShowViewListener {
        void onSelected(ImageView view, int position);
    }
    public void setShowView(ShowViewListener showViewListener){
        this.showViewListener = showViewListener;
    }

    public CooperationStoreAdapter(Context context, List<CooperativeStoreEntities.CooperativeStoreResults> mDatas,boolean isAdd) {
        super(context, mDatas);
        this.isAdd = isAdd;
    }

    public void isDelete(boolean isDel){
        this.isDel = isDel;
        notifyDataSetChanged();
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.cooperation_store_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        CooperativeStoreEntities.CooperativeStoreResults item = getItem(position);

        final ImageView cooperativeStoreStatus = ViewHolder.get(convertView,R.id.cooperative_store_status);
        SimpleDraweeView cooperativeStoreImg = ViewHolder.get(convertView,R.id.cooperative_store_img);
        TextView cooperativeStoreName = ViewHolder.get(convertView,R.id.cooperative_store_name);
        TextView cooperativeStoreNum = ViewHolder.get(convertView,R.id.cooperative_store_num);
        TextView cooperativeStorePhone = ViewHolder.get(convertView,R.id.cooperative_store_phone);
        TextView cooperativeStoreAddress = ViewHolder.get(convertView,R.id.cooperative_store_address);


        if (isDel || isAdd){
            cooperativeStoreStatus.setVisibility(View.VISIBLE);
        }else {
            cooperativeStoreStatus.setVisibility(View.GONE);
        }

        cooperativeStoreStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showViewListener != null){
                    showViewListener.onSelected(cooperativeStoreStatus,position);
                }
            }
        });

        if (item != null){
            cooperativeStoreImg.setImageURI(Uri.parse(item.getmLogo()));
            cooperativeStoreName.setText(item.getmName());
            cooperativeStoreNum.setText(item.getId());
            cooperativeStorePhone.setText(item.getPhoneNumber());
            cooperativeStoreAddress.setText(item.getmAddress());

            if (item.isSelect){
                cooperativeStoreStatus.setSelected(true);
            }else {
                cooperativeStoreStatus.setSelected(false);
            }
        }
    }
}
