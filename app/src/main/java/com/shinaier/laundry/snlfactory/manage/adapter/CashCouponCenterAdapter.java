package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.CashCouponCenterEntity;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.List;


/**
 * Created by 张家洛 on 2017/12/15.
 */

public class CashCouponCenterAdapter extends BaseAdapterNew<CashCouponCenterEntity.CashCouponCenterResult> {
    private Context context;
    public interface PositionListener{
        void onClick(int position, int innerPosition);
    }
    private PositionListener listener;
    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }
    public CashCouponCenterAdapter(Context context, List<CashCouponCenterEntity.CashCouponCenterResult> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.cash_coupon_center_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        final int outSide = position;
        CashCouponCenterEntity.CashCouponCenterResult item = getItem(position);
        TextView cashCouponCenterItemHead = ViewHolder.get(convertView,R.id.cash_coupon_center_item_head);
        WrapHeightListView cashCouponCenterItemInner = ViewHolder.get(convertView,R.id.cash_coupon_center_item_inner);

        if (item != null){
            cashCouponCenterItemHead.setText(item.getDate());
            if (item.getRecordses() != null && item.getRecordses().size() > 0){
                CashCouponCenterInnerAdapter cashCouponCenterInnerAdapter = new CashCouponCenterInnerAdapter(context,item.getRecordses());
                cashCouponCenterItemInner.setAdapter(cashCouponCenterInnerAdapter);
                cashCouponCenterInnerAdapter.setInnerPositionListener(new CashCouponCenterInnerAdapter.InnerPositionListener() {
                    @Override
                    public void onInnerClick(int position) {
                        if(listener != null){
                            listener.onClick(outSide,position);
                        }
                    }
                });
            }

        }
    }
}
