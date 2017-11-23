package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.ordermanage.entities.ColorSettingEntity;

import java.util.List;



/**
 * Created by 张家洛 on 2017/3/9.
 */

public class ColorsAdapter extends BaseAdapterNew<ColorSettingEntity> {

    public ColorsAdapter(Context context, List<ColorSettingEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.colors_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        ColorSettingEntity item = getItem(position);
        TextView tvColor = ViewHolder.get(convertView,R.id.tv_color);
        ImageView ivQuestionInfoSelect = ViewHolder.get(convertView,R.id.iv_question_info_select);
        if(item != null){
            tvColor.setText(item.getColor());
            if(item.getIscheck() == 0){
                ivQuestionInfoSelect.setSelected(false);
            }else {
                ivQuestionInfoSelect.setSelected(true);
            }
        }
    }
}
