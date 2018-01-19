package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.LeaveFactoryEntities;

import java.util.List;

/**
 * 出厂adapter
 * Created by 张家洛 on 2018/1/18.
 */

public class LeaveFactoryAdapter extends BaseExpandableListAdapter {
    private Context context;
    private SelectListener listener;
    public interface SelectListener{
        void onSelect(int groupPosition, int chilePosition);
    }

    public void setSelectListener(SelectListener listener){
        this.listener = listener;
    }
    private List<LeaveFactoryEntities.IntoFactoryResult.IntoFactoryData> groupData;
    private LayoutInflater inflater;

    public LeaveFactoryAdapter(Context context, List<LeaveFactoryEntities.IntoFactoryResult.IntoFactoryData> groupData) {
        this.groupData = groupData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return groupData.get(i).getLists().size();
    }

    @Override
    public Object getGroup(int i) {
        return groupData.get(i).getDate();
    }

    @Override
    public Object getChild(int i, int i1) {
        return groupData.get(i).getLists().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = inflater.inflate(R.layout.leave_factory_item, null);
        }
        TextView leave_factory_time = view.findViewById(R.id.leave_factory_time);
        leave_factory_time.setText(groupData.get(i).getDate());
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.leave_factory_inner_item, null);
        }
        LinearLayout llLeaveFactoryList = convertView.findViewById(R.id.ll_leave_factory_list);
        ImageView leaveFactorySelect = convertView.findViewById(R.id.leave_factory_select);
        TextView leaveFactoryClothesNum = convertView.findViewById(R.id.leave_factory_clothes_num);
        TextView leaveFactoryClothesName = convertView.findViewById(R.id.leave_factory_clothes_name);
        TextView leaveFactoryClothesPutSn = convertView.findViewById(R.id.leave_factory_clothes_put_sn);

        leaveFactoryClothesNum.setText(groupData.get(i).getLists().get(i1).getCleanSn());
        leaveFactoryClothesName.setText(groupData.get(i).getLists().get(i1).getItemName());
        leaveFactoryClothesPutSn.setText(groupData.get(i).getLists().get(i1).getPutSn());

        if (groupData.get(i).getLists().get(i1).getAssist().equals("1")){
            leaveFactoryClothesNum.setTextColor(context.getResources().getColor(R.color.txt_gray));
            leaveFactoryClothesName.setTextColor(context.getResources().getColor(R.color.txt_gray));
            leaveFactoryClothesPutSn.setTextColor(context.getResources().getColor(R.color.txt_gray));
            leaveFactorySelect.setVisibility(View.INVISIBLE);
        }else {
            leaveFactoryClothesNum.setTextColor(context.getResources().getColor(R.color.black_text));
            leaveFactoryClothesName.setTextColor(context.getResources().getColor(R.color.black_text));
            leaveFactoryClothesPutSn.setTextColor(context.getResources().getColor(R.color.black_text));
            leaveFactorySelect.setVisibility(View.VISIBLE);
        }

        llLeaveFactoryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onSelect(i,i1);
                }
            }
        });

        if (groupData.get(i).getLists().get(i1).isSelect){
            groupData.get(i).getLists().get(i1).isSelect = true;
            leaveFactorySelect.setSelected(true);
        }else {
            groupData.get(i).getLists().get(i1).isSelect = false;
            leaveFactorySelect.setSelected(false);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
