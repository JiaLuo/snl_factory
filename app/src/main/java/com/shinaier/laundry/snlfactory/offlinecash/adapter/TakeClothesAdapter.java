package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.TakeClothesEntity;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张家洛 on 2017/7/25.
 */

public class TakeClothesAdapter extends BaseAdapterNew<TakeClothesEntity.TakeClothesData> {
    private Context context;
    private List<TakeClothesEntity.TakeClothesData> mDatas;
    private RelativeLayout rlTakeClothesOrderShowMore;
    private TextView takeClothesShowSurplus;
    private WrapHeightListView takeClothesOrderListMore;
    private TakeClothesShowMoreListener takeClothesShowMoreListener;
    private ImageView ivTakeClothesShowSurplus;
    private RelativeLayout rlTakeClothesPriceItem;
    private String countNum;
    private PositionListener listener;
    private TelPhoneListener telPhoneListener;
    public interface TelPhoneListener{
        void onTelPhone(int position);
    }
    public void setTelPhoneListener(TelPhoneListener telPhoneListener){
        this.telPhoneListener = telPhoneListener;
    }
    public interface PositionListener{
        void onCheck(int position);
        void onChecked(int position);
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }

    public interface TakeClothesShowMoreListener{
        void onClick(int position, ImageView iv, TextView tv);
    }

    public void setTakeClothesShowMoreListener(TakeClothesShowMoreListener takeClothesShowMoreListener){
        this.takeClothesShowMoreListener = takeClothesShowMoreListener;
    }

    public TakeClothesAdapter(Context context, List<TakeClothesEntity.TakeClothesData> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    public void setCountNum(String countNum){
        this.countNum = countNum;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_take_clothes_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        TakeClothesEntity.TakeClothesData item = getItem(position);
        TextView takeClothesOrderNumber = ViewHolder.get(convertView,R.id.take_clothes_order_number);
        TextView takeClothesLineNum = ViewHolder.get(convertView,R.id.take_clothes_line_num);
        takeClothesOrderListMore = ViewHolder.get(convertView, R.id.take_clothes_order_list_more);
        rlTakeClothesOrderShowMore = ViewHolder.get(convertView, R.id.rl_take_clothes_order_show_more);
        takeClothesShowSurplus = ViewHolder.get(convertView, R.id.take_clothes_show_surplus);
        ivTakeClothesShowSurplus = ViewHolder.get(convertView, R.id.iv_take_clothes_show_surplus);
        rlTakeClothesPriceItem = ViewHolder.get(convertView, R.id.rl_take_clothes_price_item);
        TextView takeClothesOrderTotalNum = ViewHolder.get(convertView,R.id.take_clothes_order_total_num);
        TextView takeClothesOrderOutOfPocket = ViewHolder.get(convertView,R.id.take_clothes_order_out_of_pocket);
        TextView takeClothesOrderName = ViewHolder.get(convertView,R.id.take_clothes_order_name);
        TextView takeClothesOrderPhoneNum = ViewHolder.get(convertView,R.id.take_clothes_order_phone_num);
        TextView takeClothesPayStatus = ViewHolder.get(convertView,R.id.take_clothes_pay_status); //支付状态
        TextView takeClothesOrderNowTime = ViewHolder.get(convertView,R.id.take_clothes_order_now_time);
        TextView takeClothesOrderCancel = ViewHolder.get(convertView,R.id.take_clothes_order_cancel);
        TextView takeClothesOrderPay = ViewHolder.get(convertView,R.id.take_clothes_order_pay);

        if(item != null){
            takeClothesOrderNumber.setText("订单号：" + item.getOrdersn());
            takeClothesLineNum.setText(String.valueOf(Integer.valueOf(countNum) - position));
            //展示各个条目
            if(item.getItems() != null && item.getItems().size() > 0){
                initInnerList(item.getItems(),position);
                takeClothesOrderTotalNum.setText(item.getItemsCount());
                takeClothesOrderOutOfPocket.setText("￥" + item.getTotalAmount());
            }
            takeClothesOrderName.setText(item.getUserName());
            if (item.getMobile() != null && !TextUtils.isEmpty(item.getMobile())){
                takeClothesOrderPhoneNum.setText(item.getMobile());
                takeClothesOrderPhoneNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (telPhoneListener != null){
                            telPhoneListener.onTelPhone(position);
                        }
                    }
                });
            }
            takeClothesOrderNowTime.setText(item.getUpdateTime());
            if (item.getPayState().equals("0")){
                takeClothesPayStatus.setText("未支付");
                takeClothesPayStatus.setTextColor(context.getResources().getColor(R.color.red));
            }else if(item.getPayState().equals("1")){
                takeClothesPayStatus.setText("已支付");
                takeClothesPayStatus.setTextColor(context.getResources().getColor(R.color.store_switch_off));
            }

            takeClothesOrderCancel.setText("单件取衣");
            takeClothesOrderCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onCheck(position);
                    }
                }
            });
            if (item.getPayState().equals("0")){
                takeClothesOrderPay.setText("立即付款");
            }else {
                takeClothesOrderPay.setText("取衣结单");
            }
            takeClothesOrderPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onChecked(position);
                    }
                }
            });
        }

    }

    private void initInnerList(List<TakeClothesEntity.TakeClothesData.TakeClothesItems> cleanItems, final int position) {
        if(cleanItems != null){
            if (cleanItems.size() <= 2){
                rlTakeClothesOrderShowMore.setVisibility(View.GONE);
            }else{
                rlTakeClothesOrderShowMore.setVisibility(View.VISIBLE);
            }
            takeClothesShowSurplus.setText("查看更多");

            final TakeClothesInnerAdapter takeClothesInnerAdapter = new TakeClothesInnerAdapter(context,cleanItems);
            takeClothesOrderListMore.setAdapter(takeClothesInnerAdapter);

            if (mDatas.get(position).isOpen){
                ivTakeClothesShowSurplus.setBackgroundResource(R.drawable.ic_up_arrow);
                takeClothesShowSurplus.setText("隐藏更多");
                takeClothesInnerAdapter.setType(1);
                takeClothesInnerAdapter.notifyDataSetChanged();
            }else {
                ivTakeClothesShowSurplus.setBackgroundResource(R.drawable.ic_down_arrow);
                takeClothesShowSurplus.setText("查看更多");
            }

            rlTakeClothesOrderShowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).isOpen){
                        takeClothesInnerAdapter.setType(0);
                        takeClothesInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =false;
                    }else{
                        takeClothesInnerAdapter.setType(1);
                        takeClothesInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =true;
                    }
                    if (takeClothesShowMoreListener != null){
                        takeClothesShowMoreListener.onClick(position,ivTakeClothesShowSurplus,takeClothesShowSurplus);
                    }
                }
            });
            rlTakeClothesPriceItem.setVisibility(View.VISIBLE);
        }else {
            List<TakeClothesEntity.TakeClothesData.TakeClothesItems> item1 = new ArrayList<>();
            final TakeClothesInnerAdapter takeClothesInnerAdapter = new TakeClothesInnerAdapter(context,item1);
            takeClothesOrderListMore.setAdapter(takeClothesInnerAdapter);

            rlTakeClothesOrderShowMore.setVisibility(View.GONE);
            rlTakeClothesPriceItem.setVisibility(View.GONE);
        }
    }

}
