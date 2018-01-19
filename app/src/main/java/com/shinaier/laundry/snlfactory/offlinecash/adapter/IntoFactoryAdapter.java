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
import com.shinaier.laundry.snlfactory.network.entity.IntoFactoryEntities;

import java.util.List;

/**
 * 入厂adapter
 * Created by 张家洛 on 2018/1/18.
 */

public class IntoFactoryAdapter extends BaseExpandableListAdapter {
    private SelectListener listener;
    public interface SelectListener{
        void onSelect(int groupPosition,int chilePosition);
    }

    public void setSelectListener(SelectListener listener){
        this.listener = listener;
    }
    private List<IntoFactoryEntities.IntoFactoryResult> groupData;
    private LayoutInflater inflater;

    public IntoFactoryAdapter(Context context, List<IntoFactoryEntities.IntoFactoryResult> groupData) {
        this.groupData = groupData;
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
            view = inflater.inflate(R.layout.into_factory_item, null);
        }
        TextView into_factory_time = view.findViewById(R.id.into_factory_time);
        into_factory_time.setText(groupData.get(i).getDate());
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.into_factory_inner_item, null);
        }
        LinearLayout llItemIntoFactoryList = convertView.findViewById(R.id.ll_item_into_factory_list);
        ImageView ivItemIntoFactorySelect = convertView.findViewById(R.id.iv_item_into_factory_select);
        TextView intoFactoryItemClothesNum = convertView.findViewById(R.id.into_factory_item_clothes_num);
        TextView intoFactoryItemClothesName = convertView.findViewById(R.id.into_factory_item_clothes_name);

        intoFactoryItemClothesNum.setText(groupData.get(i).getLists().get(i1).getCleanSn());
        intoFactoryItemClothesName.setText(groupData.get(i).getLists().get(i1).getItemName());

        llItemIntoFactoryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onSelect(i,i1);
                }
            }
        });

        if (groupData.get(i).getLists().get(i1).isSelect){
            groupData.get(i).getLists().get(i1).isSelect = true;
            ivItemIntoFactorySelect.setSelected(true);
        }else {
            groupData.get(i).getLists().get(i1).isSelect = false;
            ivItemIntoFactorySelect.setSelected(false);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
