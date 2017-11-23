package com.shinaier.laundry.snlfactory.manage.adapter;

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
import com.shinaier.laundry.snlfactory.network.entity.OrderInquiryEntities;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张家洛 on 2017/2/22.
 */

public class InquiryCategoryAdapter extends BaseAdapterNew<OrderInquiryEntities.Data> {
    private ShowMoreClickListener listener;
    private RelativeLayout rlPriceItem;
    private TextView inquiryBespeakTimeDetail;
    private TextView inquiryBespeakTime;
    private PositionListener positionListener;

    public interface PositionListener{
        void onPositionClick(int position);
    }

    public void setPositionListener(PositionListener positionListener){
        this.positionListener = positionListener;
    }

    public interface ShowMoreClickListener{
        void onClick(int position, ImageView iv, TextView tv);
    }

    public void setShowMoreClickListener(ShowMoreClickListener listener){
        this.listener = listener;
    }

    private List<OrderInquiryEntities.Data> mDatas;
    private RelativeLayout rlShowMore;
    private TextView showSurplusNum;
    private Context context;
    private WrapHeightListView orderListMore;
    private ImageView showSurplusNumImg;

    public InquiryCategoryAdapter(Context context, List<OrderInquiryEntities.Data> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.inquiry_category_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OrderInquiryEntities.Data item = getItem(position);
        TextView orderNumber = ViewHolder.get(convertView,R.id.order_number);
        TextView orderStatus = ViewHolder.get(convertView,R.id.order_status);
        inquiryBespeakTimeDetail = ViewHolder.get(convertView, R.id.inquiry_bespeak_time_detail);
        inquiryBespeakTime = ViewHolder.get(convertView, R.id.inquiry_bespeak_time);
        TextView orderName = ViewHolder.get(convertView,R.id.order_name);
        TextView orderPhoneNum = ViewHolder.get(convertView,R.id.order_phone_num);
        TextView orderAddress = ViewHolder.get(convertView,R.id.order_address);
        TextView orderNowTime = ViewHolder.get(convertView,R.id.order_now_time);
        orderListMore = ViewHolder.get(convertView, R.id.order_list_more);
        rlShowMore = ViewHolder.get(convertView, R.id.rl_show_more);
        showSurplusNum = ViewHolder.get(convertView, R.id.show_surplus_num);
        showSurplusNumImg = ViewHolder.get(convertView, R.id.show_surplus_num_img);
        rlPriceItem = ViewHolder.get(convertView, R.id.rl_price_item);
        TextView freightNum = ViewHolder.get(convertView,R.id.freight_num);
        TextView specialCraftworkNum = ViewHolder.get(convertView,R.id.special_craftwork_num);
        TextView maintainCleanNum = ViewHolder.get(convertView,R.id.maintain_clean_num);
        TextView favourableNum = ViewHolder.get(convertView,R.id.favourable_num);
        TextView totalNum = ViewHolder.get(convertView,R.id.total_num);
        TextView chineseTotalFirst = ViewHolder.get(convertView,R.id.chinese_total_first);
        TextView chineseTotalNum = ViewHolder.get(convertView,R.id.chinese_total_num);
        TextView chineseTotalSecond = ViewHolder.get(convertView,R.id.chinese_total_second);
        TextView outOfPocket = ViewHolder.get(convertView,R.id.out_of_pocket);
        LinearLayout llOrderDetailItem = ViewHolder.get(convertView,R.id.ll_order_detail_item);

        if(item != null){
            orderNumber.setText("订单号:" + item.getOrdersn());
            if(item.getState() == 1){
                orderStatus.setText("预约成功");
            }else if(item.getState() == 0){
                orderStatus.setText("预约");
            }else if(item.getState() == 2){
                orderStatus.setText("已取件");
            }else if(item.getState() == 3){
                orderStatus.setText("清洗中");
            }else if(item.getState() == 4){
                orderStatus.setText("清洗完成");
            }else if(item.getState() == 5){
                orderStatus.setText("送件完成");
            }else if(item.getState() == 6){
                orderStatus.setText("完成订单");
            }else if(item.getState() == 12){
                orderStatus.setText("用户取消");
            }else if(item.getState() == 11){
                orderStatus.setText("店家取消");
            }

            List<OrderInquiryEntities.Data.Item> itemList = item.getItemList();
            initInnerList(item,itemList,position);

            if(item.getItemList() != null){
                if(item.getState() == 11 || item.getState() == 12){
                    orderListMore.setVisibility(View.GONE);
                    rlPriceItem.setVisibility(View.GONE);
                    inquiryBespeakTimeDetail.setVisibility(View.VISIBLE);
                    inquiryBespeakTime.setVisibility(View.VISIBLE);
                }else {
                    rlPriceItem.setVisibility(View.VISIBLE);
                    orderListMore.setVisibility(View.VISIBLE);
                    inquiryBespeakTimeDetail.setVisibility(View.GONE);
                    inquiryBespeakTime.setVisibility(View.GONE);
                }
            }else {
                inquiryBespeakTimeDetail.setVisibility(View.VISIBLE);
                inquiryBespeakTime.setVisibility(View.VISIBLE);
                inquiryBespeakTimeDetail.setText(item.getTime());
            }
            orderName.setText(item.getName());
            orderPhoneNum.setText(item.getPhone());
            orderAddress.setText(item.getAdr());
            orderNowTime.setText("时间：" + item.getCreateTime());
            freightNum.setText("￥" + item.getFreight());
            specialCraftworkNum.setText("￥" + item.getSpecial());
            maintainCleanNum.setText("￥"+ item.getHedging());
            favourableNum.setText("￥" + item.getCouponPrice());
            totalNum.setText(item.getSum());
            if(item.getPayState() == 1){
                chineseTotalFirst.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                chineseTotalNum.setText("￥" +item.getAmount() + "，");
                chineseTotalNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                chineseTotalSecond.setVisibility(View.VISIBLE);
                outOfPocket.setVisibility(View.VISIBLE);
                if(item.getPayAmount() != null){
                    outOfPocket.setVisibility(View.VISIBLE);
                    outOfPocket.setText("￥" + item.getPayAmount());
                }else {
                    outOfPocket.setVisibility(View.GONE);
                }
            }else {
                chineseTotalFirst.getPaint().setFlags(0);
                chineseTotalNum.setText("￥" +item.getAmount() );
                chineseTotalNum.getPaint().setFlags(0);
                chineseTotalSecond.setVisibility(View.GONE);
                outOfPocket.setVisibility(View.GONE);
            }

            llOrderDetailItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(positionListener != null){
                        positionListener.onPositionClick(position);
                    }
                }
            });
        }
    }

    private void initInnerList(OrderInquiryEntities.Data item, List<OrderInquiryEntities.Data.Item> itemList, final int position) {
        if(itemList != null){
            if (itemList.size() <= 2){
                rlShowMore.setVisibility(View.GONE);
            }else{
                if(item.getState() == 11 || item.getState() == 12){
                    rlShowMore.setVisibility(View.GONE);
                }else {
                    rlShowMore.setVisibility(View.VISIBLE);
                }
            }
            showSurplusNum.setText("查看更多");

            final InquiryCategoryInnerAdapter inquiryCategoryInnerAdapter = new InquiryCategoryInnerAdapter(context,itemList);
            orderListMore.setAdapter(inquiryCategoryInnerAdapter);

            if (mDatas.get(position).isOpen){
                showSurplusNumImg.setBackgroundResource(R.drawable.ic_up_arrow);
                showSurplusNum.setText("隐藏更多");
                inquiryCategoryInnerAdapter.setType(1);
                inquiryCategoryInnerAdapter.notifyDataSetChanged();
            }else {
                showSurplusNumImg.setBackgroundResource(R.drawable.ic_down_arrow);
                showSurplusNum.setText("查看更多");
            }

            rlShowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).isOpen){
                        inquiryCategoryInnerAdapter.setType(0);
                        inquiryCategoryInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =false;
                    }else{
                        inquiryCategoryInnerAdapter.setType(1);
                        inquiryCategoryInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =true;
                    }
                    if (listener != null){
                        listener.onClick(position,showSurplusNumImg,showSurplusNum);
                    }
                }
            });
            rlPriceItem.setVisibility(View.VISIBLE);
        }else {
            List<OrderInquiryEntities.Data.Item> item1 = new ArrayList<>();
            final InquiryCategoryInnerAdapter inquiryCategoryInnerAdapter = new InquiryCategoryInnerAdapter(context, item1);
            orderListMore.setAdapter(inquiryCategoryInnerAdapter);

            rlShowMore.setVisibility(View.GONE);
            rlPriceItem.setVisibility(View.GONE);
        }
    }
}
