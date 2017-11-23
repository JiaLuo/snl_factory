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

public class CategoryDisposeAdapter extends BaseAdapterNew<OrderDisposeEntities.Data> {
    private PositionListener listener;
    private GotoDetailListener gotoDetailListener;
    private TelPhoneListener telPhoneListener;
    private String countNum;
    public interface TelPhoneListener{
        void onTelPhone(int position);
    }

    public void setTelPhoneListener(TelPhoneListener telPhoneListener){
        this.telPhoneListener = telPhoneListener;
    }

    public interface PositionListener{
        void cancelOnClick(int position);
        void confirmOnClick(int position);
    }

    public interface GotoDetailListener{
        void onClick(int position);
    }

    public void setGotoDetailListener(GotoDetailListener gotoDetailListener){
        this.gotoDetailListener = gotoDetailListener;
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }

    private Context context;
    private List<OrderDisposeEntities.Data> mDatas;
    public CategoryDisposeAdapter(Context context, List<OrderDisposeEntities.Data> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    public void setCountNum(String countNum){
        this.countNum = countNum;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.category_dispose_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OrderDisposeEntities.Data item = getItem(position);
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
            orderDisposeNumber.setText("订单号：" + item.getOrdersn());
            orderDisposeName.setText(item.getName());
            orderDisposeBespeakTimeDetail.setText(item.getTime());
            orderDisposePhoneNum.setText(item.getPhone());
            orderDisposeAddress.setText(item.getAdr());
            orderDisposeNowTime.setText(item.getCreateTime());
            employeeLineNum.setText(String.valueOf(Integer.valueOf(countNum) - position));
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
                    if(gotoDetailListener != null){
                        gotoDetailListener.onClick(position);
                    }
                }
            });

            llOrderDisposePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(telPhoneListener != null){
                        telPhoneListener.onTelPhone(position);
                    }
                }
            });
        }
    }
}
