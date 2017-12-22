package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.ordermanage.entities.AssessSettingEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/3/9.
 */

public class AssessesAdapter extends BaseAdapterNew<AssessSettingEntity> {

    public AssessesAdapter(Context context, List<AssessSettingEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.assesses_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        AssessSettingEntity item = getItem(position);
        TextView tvAssess = ViewHolder.get(convertView,R.id.tv_assess);
        ImageView ivAssessInfoSelect = ViewHolder.get(convertView,R.id.iv_assess_info_select);
        if(item != null){
            tvAssess.setText(item.getAssesses());
            if(item.getIscheck() == 0){
                ivAssessInfoSelect.setSelected(false);
            }else {
                ivAssessInfoSelect.setSelected(true);
            }
        }
    }
}
