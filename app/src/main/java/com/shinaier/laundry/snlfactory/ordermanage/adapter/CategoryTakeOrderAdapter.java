package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderTakeOrderEntities;

import java.util.List;


/**
 * Created by 张家洛 on 2017/2/25.
 */

public class CategoryTakeOrderAdapter extends BaseAdapterNew<OrderTakeOrderEntities.OrderTakeOrderResult> {
    private List<OrderTakeOrderEntities.OrderTakeOrderResult> mDatas;
    private PositionListener listener;
    public interface PositionListener{
        void cancelOnClick(int position);
        void confirmOnClick(int position);
        void onTellClick(int position);
        void onGotoDetail(int position);
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }

    public CategoryTakeOrderAdapter(Context context, List<OrderTakeOrderEntities.OrderTakeOrderResult> mDatas) {
        super(context, mDatas);
        this.mDatas = mDatas;
    }


    @Override
    protected int getResourceId(int Position) {
        return R.layout.category_take_order_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OrderTakeOrderEntities.OrderTakeOrderResult item = getItem(position);
        TextView takeOrderNumber = ViewHolder.get(convertView,R.id.take_order_number);
        TextView takeOrderBespeakTimeDetail = ViewHolder.get(convertView,R.id.take_order_bespeak_time_detail);
        TextView takeOrderName = ViewHolder.get(convertView,R.id.take_order_name);
        TextView takeOrderPhoneNum = ViewHolder.get(convertView,R.id.take_order_phone_num);
        TextView takeOrderAddress = ViewHolder.get(convertView,R.id.take_order_address);
        TextView takeOrderNowTime = ViewHolder.get(convertView,R.id.take_order_now_time);
        TextView takeOrderCancelOrder = ViewHolder.get(convertView,R.id.take_order_cancel_order);
        TextView takeOrderContactStore = ViewHolder.get(convertView,R.id.take_order_contact_store);
        LinearLayout llTakeOrder = ViewHolder.get(convertView,R.id.ll_take_order);
        TextView employeeLineNum = ViewHolder.get(convertView,R.id.employee_line_num);
        LinearLayout llTakeOrderPhone = ViewHolder.get(convertView,R.id.ll_take_order_phone);

        if(item != null){
            takeOrderNumber.setText("订单号：" + item.getOrdersn());
            takeOrderBespeakTimeDetail.setText(item.getTime());
            takeOrderName.setText(item.getuName());
            takeOrderPhoneNum.setText(item.getuMobile());
            takeOrderAddress.setText(item.getuAddress());
            takeOrderNowTime.setText("时间:" + item.getTime());
            takeOrderCancelOrder.setText("取消订单");
            employeeLineNum.setText(mDatas.size() - position + "");
            takeOrderCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.cancelOnClick(position);
                    }
                }
            });

            takeOrderContactStore.setText("添加项目");
            takeOrderContactStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.confirmOnClick(position);
                    }
                }
            });
            llTakeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onGotoDetail(position);
                    }
                }
            });
            llTakeOrderPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onTellClick(position);
                    }
                }
            });
        }
    }
}
