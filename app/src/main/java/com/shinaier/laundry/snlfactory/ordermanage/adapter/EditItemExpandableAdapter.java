package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.EditItemShowEntities;

import java.util.List;


/**
 * Created by 张家洛 on 2017/3/17.
 */

public class EditItemExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<EditItemShowEntities> groupData;
    private LayoutInflater inflater;
    private OnAddReduceClickListener listener;

    public EditItemExpandableAdapter(List<EditItemShowEntities> groupData
            , Context context){
        this.groupData = groupData;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public interface OnAddReduceClickListener{
        void onAdd(int groupPosition, int childPosition);
        void onReduce(int groupPosition, int childPosition);
        void onSelect(int groupPosition, int childPosition);
    }

    public void setOnAddReduceClickListener(OnAddReduceClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return groupData.get(i).getItems().size();
    }

    @Override
    public Object getGroup(int i) {
        return  groupData.get(i).getTypeName();
    }

    @Override
    public Object getChild(int i, int i1) {
        return groupData.get(i).getItems().get(i1);
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
        serveName.setText(groupData.get(groupPosition).getTypeName());
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.clothes_item_child, null);
        }
        ImageView ivAddItemSelect = (ImageView) convertView.findViewById(R.id.iv_add_item_select);
        TextView addItemClothesName = (TextView) convertView.findViewById(R.id.add_item_clothes_name);
        TextView addItemClothesPrice = (TextView) convertView.findViewById(R.id.add_item_clothes_price);
        ImageView ivAddItemMinus = (ImageView) convertView.findViewById(R.id.iv_add_item_minus);
        TextView addItemClothesNameNum = (TextView) convertView.findViewById(R.id.add_item_clothes_name_num);
        ImageView ivAddItemPlus = (ImageView) convertView.findViewById(R.id.iv_add_item_plus);

        addItemClothesName.setText(groupData.get(groupPosition).getItems().get(childPosition).getName());
        addItemClothesPrice.setText("￥" + groupData.get(groupPosition).getItems().get(childPosition).getPrice());
        if(groupData.get(groupPosition).getItems().get(childPosition).extraFrom){
            if(groupData.get(groupPosition).getItems().get(childPosition).getStateType() == 1){
                ivAddItemSelect.setSelected(true);
                addItemClothesNameNum.setText("" + groupData.get(groupPosition).getItems().get(childPosition).getNum());
            }else {
                ivAddItemSelect.setSelected(false);
                addItemClothesNameNum.setText(""+ groupData.get(groupPosition).getItems().get(childPosition).getNum());
            }
        }else {
            if(groupData.get(groupPosition).getItems().get(childPosition).isSelect){
                 ivAddItemSelect.setSelected(true);
            }else {
                ivAddItemSelect.setSelected(false);
             }
            addItemClothesNameNum.setText(""+ groupData.get(groupPosition).getItems().get(childPosition).getNum());
        }

        ivAddItemMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onReduce(groupPosition,childPosition);
                }
            }
        });

        ivAddItemPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onAdd(groupPosition,childPosition);
                }
            }
        });

        ivAddItemSelect.setOnClickListener(new View.OnClickListener() {
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
        return inflater.inflate(R.layout.clothes_item_head, null);
    }
}
