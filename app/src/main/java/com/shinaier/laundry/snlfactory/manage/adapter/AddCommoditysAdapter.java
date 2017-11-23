package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.AddCommodityEntities;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/21.
 */

public class AddCommoditysAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<AddCommodityEntities.Item> groupData;
    private LayoutInflater inflater;
    private SelectListener listener;

    public interface SelectListener{
        void onSelect(int groupPosition, int childPosition);
    }

    public void setSelectListener(SelectListener listener){
        this.listener = listener;
    }

    public AddCommoditysAdapter(List<AddCommodityEntities.Item> groupData
            , Context context){
        this.groupData = groupData;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return groupData.get(i).getGoods().size();
    }

    @Override
    public Object getGroup(int i) {
        return  groupData.get(i).getName();
    }

    @Override
    public Object getChild(int i, int i1) {
        return groupData.get(i).getGoods().get(i1);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createGroupView();
        }
        TextView serveName = (TextView) view.findViewById(R.id.serve_name);
        serveName.setText(groupData.get(groupPosition).getName());
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.add_commodity_item_inner, null);
        }

        ImageView ivAddCommodity = (ImageView) convertView.findViewById(R.id.iv_add_commodity);
        TextView clothesName = (TextView) convertView.findViewById(R.id.clothes_name);
        LinearLayout llAddCommodityInnerItem = (LinearLayout) convertView.findViewById(R.id.ll_add_commodity_inner_item);

        clothesName.setText(groupData.get(groupPosition).getGoods().get(childPosition).getName());
        ivAddCommodity.setSelected(groupData.get(groupPosition).getGoods().get(childPosition).isChanged);
        llAddCommodityInnerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onSelect(groupPosition,childPosition);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.add_commodity_item_head, null);
    }
}
