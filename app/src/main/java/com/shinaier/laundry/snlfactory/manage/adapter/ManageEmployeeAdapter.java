package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.EmployeeEntity;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/16.
 */

public class ManageEmployeeAdapter extends BaseAdapterNew<EmployeeEntity.EmployeeResult> {
    private OnEditorListener onEditorListener;

    public interface OnEditorListener{
        void edit(int position);
        void delete(int position);
    }

    public void setOnEditorListener(OnEditorListener onEditorListener){
        this.onEditorListener = onEditorListener;
    }

    public ManageEmployeeAdapter(Context context, List<EmployeeEntity.EmployeeResult> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.manage_employee_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        EmployeeEntity.EmployeeResult item = getItem(position);
        TextView employeeLineNum = ViewHolder.get(convertView,R.id.employee_line_num);
        TextView employeeName = ViewHolder.get(convertView,R.id.employee_name);
        TextView employeePhone = ViewHolder.get(convertView,R.id.employee_phone);
        TextView employeeEdit = ViewHolder.get(convertView,R.id.employee_edit);
        TextView employeeDelete = ViewHolder.get(convertView,R.id.employee_delete);

        if(item != null){
            employeeLineNum.setText(String.valueOf(position + 1));
            employeeName.setText(item.getaName());
            employeePhone.setText(item.getAccount());
        }

        employeeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onEditorListener != null){
                    onEditorListener.edit(position);
                }
            }
        });

        employeeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onEditorListener != null){
                    onEditorListener.delete(position);
                }
            }
        });
    }
}
