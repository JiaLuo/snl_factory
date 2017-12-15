package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.ManageCommodityEntities;

import java.util.List;

/**
 * 商品管理item
 * Created by 张家洛 on 2017/2/14.
 */

public class ManageCommoditysAdapter extends BaseExpandableListAdapter {
    private AddCommodityListener addCommodityListener;
    private EditCommodityListener editCommodityListener;
    private Context context;
    private List<ManageCommodityEntities.ManageCommodityResult> groupData;
    private LayoutInflater inflater;

    //添加按钮的回调
    public interface AddCommodityListener{
        void onAddClick(int groupPosition);
    }
    //编辑或者删除的回调
    public interface EditCommodityListener{
        void onEditClick(int groupPosition, int childPosition);
        void onDeleteClick(int groupPosition, int childPosition);
    }

    public void setEditCommodityListener(EditCommodityListener editCommodityListener){
        this.editCommodityListener = editCommodityListener;
    }

    public void setAddCommodityListener(AddCommodityListener addCommodityListener){
        this.addCommodityListener = addCommodityListener;
    }

    public ManageCommoditysAdapter(Context context, List<ManageCommodityEntities.ManageCommodityResult> groupData){
        this.groupData = groupData;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupData.get(groupPosition).getItemses().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition).getCateName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupData.get(groupPosition).getItemses().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createGroupView();
        }
        TextView commodityClothesName = (TextView) view.findViewById(R.id.commodity_clothes_name);
        TextView tvCommodityAdd = (TextView) view.findViewById(R.id.tv_commodity_add);
        commodityClothesName.setText(groupData.get(groupPosition).getCateName());
        tvCommodityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addCommodityListener != null){
                    addCommodityListener.onAddClick(groupPosition);
                }
            }

        });
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.commoditys_item_childview, null);
        }
        TextView clothesName = (TextView) convertView.findViewById(R.id.clothes_name);
        TextView clothesPrice = (TextView) convertView.findViewById(R.id.clothes_price);
        ImageView ivCommodityDelete = (ImageView) convertView.findViewById(R.id.iv_commodity_delete);
        ImageView ivCommodityEdit = (ImageView) convertView.findViewById(R.id.iv_commodity_edit);

        clothesName.setText(groupData.get(groupPosition).getItemses().get(childPosition).getItemName());
        clothesPrice.setText(groupData.get(groupPosition).getItemses().get(childPosition).getItemPrice());

        ivCommodityDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (editCommodityListener != null){
                   editCommodityListener.onDeleteClick(groupPosition,childPosition);
               }
            }
        });
        ivCommodityEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editCommodityListener != null){
                    editCommodityListener.onEditClick(groupPosition,childPosition);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.commoditys_item_head, null);
    }
}
