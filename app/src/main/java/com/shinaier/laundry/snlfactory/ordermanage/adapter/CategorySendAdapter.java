package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderSendEntities;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by 张家洛 on 2017/2/28.
 */

public class CategorySendAdapter extends BaseAdapterNew<OrderSendEntities.SendData> {
    private Context context;
    private List<OrderSendEntities.SendData> mDatas;
    private RelativeLayout rlOrderSendShowMore;
    private TextView sendShowSurplus;
    private WrapHeightListView orderSendListMore;
    private ImageView sendShowSurplusImg;
    private GotoDetailListener gotoDetailListener;
    private SendShowMoreListener sendShowMoreListener;
    private RelativeLayout rlSendPriceItem;
    private PositionListener positionListener;
    private TelPhoneListener telPhoneListener;
    private String countNum;

    public interface TelPhoneListener{
        void onTelPhone(int position);
    }

    public void setTelPhoneListener(TelPhoneListener telPhoneListener){
        this.telPhoneListener = telPhoneListener;
    }

    public interface PositionListener{
        void onPositionClick(int position);
    }

    public void setPositionListener(PositionListener positionListener){
        this.positionListener = positionListener;
    }

    public interface SendShowMoreListener{
        void onClick(int position, ImageView iv, TextView tv);
    }

    public void setSendShowMoreListener(SendShowMoreListener sendShowMoreListener){
        this.sendShowMoreListener = sendShowMoreListener;
    }

    public interface GotoDetailListener{
        void onClick(int position);
    }

    public void setGotoDetailListener(GotoDetailListener gotoDetailListener){
        this.gotoDetailListener = gotoDetailListener;
    }

    public CategorySendAdapter(Context context, List<OrderSendEntities.SendData> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    public void setCountNum(String countNum){
        this.countNum = countNum;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.category_send_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OrderSendEntities.SendData item = getItem(position);
        TextView orderSendNumber = ViewHolder.get(convertView,R.id.order_send_number);
        orderSendListMore = ViewHolder.get(convertView, R.id.order_send_list_more);
        rlOrderSendShowMore = ViewHolder.get(convertView, R.id.rl_order_send_show_more);
        sendShowSurplus = ViewHolder.get(convertView, R.id.send_show_surplus);
        sendShowSurplusImg = ViewHolder.get(convertView, R.id.send_show_surplus_img);
        rlSendPriceItem = ViewHolder.get(convertView, R.id.rl_send_price_item);
        TextView orderSendFreightNum = ViewHolder.get(convertView,R.id.order_send_freight_num);
        TextView orderSendSpecialCraftworkNum = ViewHolder.get(convertView,R.id.order_send_special_craftwork_num);
        TextView takeOrderMaintainSendNum = ViewHolder.get(convertView,R.id.take_order_maintain_send_num);
        TextView takeOrderFavourableSendNum = ViewHolder.get(convertView,R.id.take_order_favourable_send_num);
        TextView sendTotalNum = ViewHolder.get(convertView,R.id.send_total_num);
        TextView sendChineseTotalFirst = ViewHolder.get(convertView,R.id.send_chinese_total_first);
        TextView sendChineseTotalNum = ViewHolder.get(convertView,R.id.send_chinese_total_num);
        TextView sendChineseTotalSecond = ViewHolder.get(convertView,R.id.send_chinese_total_second);
        TextView sendOutOfPocket = ViewHolder.get(convertView,R.id.send_out_of_pocket);
        TextView sendBespeakTime = ViewHolder.get(convertView,R.id.send_bespeak_time);
        TextView sendBespeakTimeDetail = ViewHolder.get(convertView,R.id.send_bespeak_time_detail);
        TextView sendOrderName = ViewHolder.get(convertView,R.id.send_order_name);
        TextView sendPhoneNum = ViewHolder.get(convertView,R.id.send_phone_num);
        TextView sendAddress = ViewHolder.get(convertView,R.id.send_address);
        TextView sendNowTime = ViewHolder.get(convertView,R.id.send_now_time);
        TextView sendCancel = ViewHolder.get(convertView,R.id.send_cancel);
        TextView sendContactStore = ViewHolder.get(convertView,R.id.send_contact_store);
        LinearLayout llSendItem = ViewHolder.get(convertView,R.id.ll_send_item);
        TextView employeeLineNum = ViewHolder.get(convertView,R.id.employee_line_num);
        LinearLayout llSendPhone = ViewHolder.get(convertView,R.id.ll_send_phone);

        if(item != null ){
            orderSendNumber.setText("订单号：" + item.getOrdersn());
            List<OrderSendEntities.SendData.SendItem> sendItems = item.getSendItems();
            initInnerList(sendItems,position);

            if(item.getSendItems() != null){
                sendBespeakTime.setVisibility(View.GONE);
                sendBespeakTimeDetail.setVisibility(View.GONE);
            }else {
                sendBespeakTimeDetail.setVisibility(View.VISIBLE);
                sendBespeakTime.setVisibility(View.VISIBLE);
                sendBespeakTimeDetail.setText(item.getTime());
            }

            sendOrderName.setText(item.getName());
            sendPhoneNum.setText(item.getPhone());
            sendAddress.setText(item.getAdr());
            sendNowTime.setText("时间：" + item.getCreateTime());
            sendCancel.setVisibility(View.GONE);
            employeeLineNum.setText(String.valueOf(Integer.valueOf(countNum) - position));
            sendContactStore.setText("送达完成");
            sendContactStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(positionListener != null){
                        positionListener.onPositionClick(position);
                    }
                }
            });

            llSendPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(telPhoneListener != null){
                        telPhoneListener.onTelPhone(position);
                    }
                }
            });

            orderSendFreightNum.setText("￥" + item.getFreight());
            orderSendSpecialCraftworkNum.setText("￥" + item.getSpecial());
            takeOrderMaintainSendNum.setText("￥" + item.getHedging());
            if(item.getCouponPrice() == null){
                takeOrderFavourableSendNum.setText("￥0.00");
            }else {
                takeOrderFavourableSendNum.setText("￥" + item.getCouponPrice());
            }
            sendTotalNum.setText(item.getSum());

            if(item.getPayState() == 1){
                sendChineseTotalFirst.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                sendChineseTotalNum.setText("￥" +item.getAmount() + "，");
                sendChineseTotalNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                sendChineseTotalSecond.setVisibility(View.VISIBLE);
                sendOutOfPocket.setVisibility(View.VISIBLE);
                if(item.getPayAmount() != null){
                    sendOutOfPocket.setVisibility(View.VISIBLE);
                    sendOutOfPocket.setText("￥" + item.getPayAmount());
                }else {
                    sendOutOfPocket.setVisibility(GONE);
                }
            }else {
                sendChineseTotalFirst.getPaint().setFlags(0);
                sendChineseTotalNum.setText("￥" +item.getAmount() );
                sendChineseTotalNum.getPaint().setFlags(0);
                sendChineseTotalSecond.setVisibility(GONE);
                sendOutOfPocket.setVisibility(GONE);
            }

            llSendItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(gotoDetailListener != null){
                        gotoDetailListener.onClick(position);
                    }
                }
            });
        }
    }

    private void initInnerList(List<OrderSendEntities.SendData.SendItem> sendItems, final int position) {
        if(sendItems != null){
            if (sendItems.size() <= 2){
                rlOrderSendShowMore.setVisibility(View.GONE);
            }else{
                rlOrderSendShowMore.setVisibility(View.VISIBLE);
            }
            sendShowSurplus.setText("查看更多");

            final CategorySendInnerAdapter categorySendInnerAdapter = new CategorySendInnerAdapter(context,sendItems);
            orderSendListMore.setAdapter(categorySendInnerAdapter);

            if (mDatas.get(position).isOpen){
                sendShowSurplusImg.setBackgroundResource(R.drawable.ic_up_arrow);
                sendShowSurplus.setText("隐藏更多");
                categorySendInnerAdapter.setType(1);
                categorySendInnerAdapter.notifyDataSetChanged();
            }else {
                sendShowSurplusImg.setBackgroundResource(R.drawable.ic_down_arrow);
                sendShowSurplus.setText("查看更多");
            }

            rlOrderSendShowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).isOpen){
                        categorySendInnerAdapter.setType(0);
                        categorySendInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =false;
                    }else{
                        categorySendInnerAdapter.setType(1);
                        categorySendInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =true;
                    }
                    if (sendShowMoreListener != null){
                        sendShowMoreListener.onClick(position,sendShowSurplusImg,sendShowSurplus);
                    }
                }
            });
            rlSendPriceItem.setVisibility(View.VISIBLE);
        }else {
            List<OrderSendEntities.SendData.SendItem> item1 = new ArrayList<>();
            CategorySendInnerAdapter categorySendInnerAdapter = new CategorySendInnerAdapter(context,item1);
            orderSendListMore.setAdapter(categorySendInnerAdapter);

            rlOrderSendShowMore.setVisibility(View.GONE);
            rlSendPriceItem.setVisibility(View.GONE);
        }
    }
}
