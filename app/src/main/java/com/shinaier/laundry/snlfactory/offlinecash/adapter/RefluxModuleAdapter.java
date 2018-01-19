package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.RefluxEditEntity;

import java.util.List;



/**
 * Created by 张家洛 on 2017/10/30.
 */

public class RefluxModuleAdapter extends BaseAdapterNew<RefluxEditEntity.RefluxEditResult.RefluxEditModule> {
    public RefluxModuleAdapter(Context context, List<RefluxEditEntity.RefluxEditResult.RefluxEditModule> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.reflux_module_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        RefluxEditEntity.RefluxEditResult.RefluxEditModule item = getItem(position);
        ImageView ivModuleSelect = ViewHolder.get(convertView,R.id.iv_module_select);
        TextView moduleName = ViewHolder.get(convertView,R.id.module_name);
        if (item != null){
            moduleName.setText(item.getModuleName());
            if (item.isSelect){
                ivModuleSelect.setSelected(true);
            }else {
                ivModuleSelect.setSelected(false);
            }
        }
    }
}
