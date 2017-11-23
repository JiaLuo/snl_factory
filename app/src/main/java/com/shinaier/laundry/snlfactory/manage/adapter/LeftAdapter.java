package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OptionEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/9/13.
 */

public class LeftAdapter extends BaseAdapterNew<OptionEntity> {
    private int pos;
    public LeftAdapter(Context context, List<OptionEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.distance_spinner_left_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        OptionEntity entity = this.getItem(position);

        TextView tv = ViewHolder.get(convertView, R.id.tv);
        tv.setText(entity.getName());
    }

    public void setSelectedPosition(int pos) {
        this.pos = pos;
        for (int i = 0; i < getCount(); i++){
            if (i == pos){
                getItem(i).setIsSelected(true);
            }else {
                getItem(i).setIsSelected(false);
            }
        }
        notifyDataSetChanged();
    }
}
