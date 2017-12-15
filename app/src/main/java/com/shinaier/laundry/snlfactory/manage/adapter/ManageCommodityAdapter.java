package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.ManageCommodityEntities;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.List;


/**
 * Created by 张家洛 on 2017/2/14.
 */

public class ManageCommodityAdapter extends BaseAdapterNew<ManageCommodityEntities.ManageCommodityResult> {
    // TODO: 2017/12/15 暂时先不删
    private PositionListener listener;
    private AddCommodityListener addCommodityListener;
    public interface AddCommodityListener{
        void onAddCommodityClick(int position);
    }
    public void setAddCommodityListener(AddCommodityListener addCommodityListener){
        this.addCommodityListener = addCommodityListener;
    }
    public interface PositionListener{
        void onEditClick(int position,int innerPosition);
        void onDeleteClick(int position,int innerPosition);
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }

    private Context context;
    public ManageCommodityAdapter(Context context, List<ManageCommodityEntities.ManageCommodityResult> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.manage_commodity_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        ManageCommodityEntities.ManageCommodityResult item = getItem(position);
        TextView commodityClothesName = ViewHolder.get(convertView,R.id.commodity_clothes_name);
        TextView tvCommodityAdd = ViewHolder.get(convertView,R.id.tv_commodity_add);
        View listviewBottomView = ViewHolder.get(convertView,R.id.listview_bottom_view);

        WrapHeightListView commodityManageInner = ViewHolder.get(convertView,R.id.commodity_manage_inner);
        if(item != null){
            if (item.getItemses().size() > 0){
                listviewBottomView.setVisibility(View.VISIBLE);
            }else {
                listviewBottomView.setVisibility(View.GONE);
            }
            commodityClothesName.setText(item.getCateName());
            CommodityInnerAdapter commodityInnerAdapter = new CommodityInnerAdapter(context,item.getItemses());
            commodityManageInner.setAdapter(commodityInnerAdapter);
            tvCommodityAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (addCommodityListener != null){
                        addCommodityListener.onAddCommodityClick(position);
                    }
                }
            });

            commodityInnerAdapter.setInnerPositionListener(new CommodityInnerAdapter.InnerPositionListener() {
                @Override
                public void onInnerDelete(int innerPosition) {
                    if(listener != null){
                        listener.onDeleteClick(position,innerPosition);
                    }
                }

                @Override
                public void onInnerEdit(int innerPosition) {
                    if(listener != null){
                        listener.onEditClick(position,innerPosition);
                    }
                }
            });
        }
    }
}
