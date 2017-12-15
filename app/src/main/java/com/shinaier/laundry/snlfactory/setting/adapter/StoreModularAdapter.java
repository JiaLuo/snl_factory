package com.shinaier.laundry.snlfactory.setting.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.StoreInfoEntity;

import java.util.List;

/**
 * Created by 张家洛 on 2017/12/14.
 */

public class StoreModularAdapter extends BaseAdapterNew<StoreInfoEntity.StoreInfoResult.StoreInfoModule> {
    public StoreModularAdapter(Context context, List<StoreInfoEntity.StoreInfoResult.StoreInfoModule> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.store_modular_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        StoreInfoEntity.StoreInfoResult.StoreInfoModule item = getItem(position);
        TextView storeModularTxt = ViewHolder.get(convertView,R.id.store_modular_txt);
        if (item != null){
            storeModularTxt.setText(item.getModuleName());
        }
    }
}
