package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderDisposeEntities;

import java.util.List;


/**
 * Created by 张家洛 on 2017/2/24.
 */

public class CategoryDisposeAdapter extends BaseAdapterNew<OrderDisposeEntities.OrderDisposeResult> {
    private List<OrderDisposeEntities.OrderDisposeResult> mDatas;
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

    public CategoryDisposeAdapter(Context context, List<OrderDisposeEntities.OrderDisposeResult> mDatas) {
        super(context, mDatas);
        this.mDatas = mDatas;
    }


    @Override
    protected int getResourceId(int Position) {
        return R.layout.category_dispose_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OrderDisposeEntities.OrderDisposeResult item = getItem(position);
        TextView orderDisposeNumber = ViewHolder.get(convertView,R.id.order_dispose_number);
        TextView orderDisposeName = ViewHolder.get(convertView,R.id.order_dispose_name);
        TextView orderDisposeBespeakTimeDetail = ViewHolder.get(convertView,R.id.order_dispose_bespeak_time_detail);
        TextView orderDisposePhoneNum = ViewHolder.get(convertView,R.id.order_dispose_phone_num);
        TextView orderDisposeAddress = ViewHolder.get(convertView,R.id.order_dispose_address);
        TextView orderDisposeNowTime = ViewHolder.get(convertView,R.id.order_dispose_now_time);
        TextView orderDisposeCancel = ViewHolder.get(convertView,R.id.order_dispose_cancel);
        TextView orderDisposeContactStore = ViewHolder.get(convertView,R.id.order_dispose_contact_store);
        LinearLayout llOrderDispose = ViewHolder.get(convertView,R.id.ll_order_dispose);
        TextView employeeLineNum = ViewHolder.get(convertView,R.id.employee_line_num);
        LinearLayout llOrderDisposePhone = ViewHolder.get(convertView,R.id.ll_order_dispose_phone);

        if(item != null){
            orderDisposeNumber.setText("订单号：" + item.getOrderSn());
            orderDisposeName.setText(item.getuName());
            orderDisposeBespeakTimeDetail.setText(item.getTime());
            orderDisposePhoneNum.setText(item.getuMobile());
            orderDisposeAddress.setText(item.getuAddress());
            orderDisposeNowTime.setText(item.getTime());
            employeeLineNum.setText(mDatas.size() - position + "");
            orderDisposeCancel.setText("取消订单");

            orderDisposeCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.cancelOnClick(position);
                    }
                }
            });
            orderDisposeContactStore.setText("确定订单");
            orderDisposeContactStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.confirmOnClick(position);
                    }
                }
            });

            llOrderDispose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onGotoDetail(position);
                    }
                }
            });

            llOrderDisposePhone.setOnClickListener(new View.OnClickListener() {
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
