package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.CashCouponCenterEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/12/15.
 */

public class CashCouponCenterInnerAdapter extends BaseAdapterNew<CashCouponCenterEntity.CashCouponCenterResult.CashCouponCenterRecords> {
    public interface InnerPositionListener{
        void onInnerClick(int position);
    }
    private InnerPositionListener listener;
    public void setInnerPositionListener(InnerPositionListener listener){
        this.listener = listener;
    }
    public CashCouponCenterInnerAdapter(Context context, List<CashCouponCenterEntity.CashCouponCenterResult.CashCouponCenterRecords> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.cash_coupon_center_inner_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        CashCouponCenterEntity.CashCouponCenterResult.CashCouponCenterRecords item = getItem(position);
        LinearLayout llCashCouponCenterInner = ViewHolder.get(convertView,R.id.ll_cash_coupon_center_inner);
        TextView cashCouponType = ViewHolder.get(convertView,R.id.cash_coupon_type);
        TextView cashCouponNum = ViewHolder.get(convertView,R.id.cash_coupon_num);
        TextView cashCouponTime = ViewHolder.get(convertView,R.id.cash_coupon_time);

        if (item != null){
            if (item.getType().equals("1")){//记录类型：1-充值卡；0-优惠券;
                cashCouponType.setText("类型：充值卡");
            }else {
                cashCouponType.setText("类型：优惠券");
            }
            cashCouponNum.setText("张数：" + item.getUsedCount() + "/" + item.getMakeCount());
            cashCouponTime.setText(item.getMakeTime());
            llCashCouponCenterInner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onInnerClick(position);
                    }
                }
            });
        }

    }
}
