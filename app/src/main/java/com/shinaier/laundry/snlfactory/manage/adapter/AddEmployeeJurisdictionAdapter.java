package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.manage.entities.AddEmployeeJurisdictionEntity;

import java.util.List;

/**
 * Created by 张家洛 on 2017/10/30.
 */

public class AddEmployeeJurisdictionAdapter extends BaseAdapterNew<AddEmployeeJurisdictionEntity> {
    public AddEmployeeJurisdictionAdapter(Context context, List<AddEmployeeJurisdictionEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.add_employee_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        AddEmployeeJurisdictionEntity item = getItem(position);
        ImageView ivJurisdictionSelect = ViewHolder.get(convertView,R.id.iv_jurisdiction_select);
        TextView jurisdictionName = ViewHolder.get(convertView,R.id.jurisdiction_name);
        if (item != null){
            jurisdictionName.setText(item.getName());
            if (item.isSelect){
                ivJurisdictionSelect.setSelected(true);
            }else {
                ivJurisdictionSelect.setSelected(false);
            }
        }
    }
}
