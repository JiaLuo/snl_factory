package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleaningEntities;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张家洛 on 2017/2/28.
 */

public class CategoryCleaningAdapter extends BaseAdapterNew<OrderCleaningEntities.OrderCleaningResult> {
    private RelativeLayout rlOrderCleaningShowMore;
    private TextView cleaningShowSurplus;
    private Context context;
    private List<OrderCleaningEntities.OrderCleaningResult> mDatas;
    private WrapHeightListView orderCleaningListMore;
    private ImageView cleaningShowSurplusImg;
    private CleaningShowMoreListener cleaningShowMoreListener;
    private RelativeLayout rlCleaningPriceItem;
    private GotoDetailListener gotoDetailListener;
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
        void onPositionListener(int position);
    }

    public void setPositionListener(PositionListener positionListener){
        this.positionListener = positionListener;
    }

    public interface CleaningShowMoreListener{
        void onClick(int position, ImageView iv, TextView tv);
    }

    public void setCleaningShowMoreListener(CleaningShowMoreListener cleaningShowMoreListener){
        this.cleaningShowMoreListener = cleaningShowMoreListener;
    }

    public interface GotoDetailListener{
        void onClick(int position);
    }

    public void setGotoDetailListener(GotoDetailListener gotoDetailListener){
        this.gotoDetailListener = gotoDetailListener;
    }

    public CategoryCleaningAdapter(Context context, List<OrderCleaningEntities.OrderCleaningResult> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    public void setCountNum(String countNum){
        this.countNum = countNum;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.category_cleaning_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OrderCleaningEntities.OrderCleaningResult item = getItem(position);
        TextView orderCleaningNumber = ViewHolder.get(convertView,R.id.order_cleaning_number);
        orderCleaningListMore = ViewHolder.get(convertView, R.id.order_cleaning_list_more);
        rlOrderCleaningShowMore = ViewHolder.get(convertView, R.id.rl_order_cleaning_show_more);
        cleaningShowSurplus = ViewHolder.get(convertView, R.id.cleaning_show_surplus);
        cleaningShowSurplusImg = ViewHolder.get(convertView, R.id.cleaning_show_surplus_img);
        rlCleaningPriceItem = ViewHolder.get(convertView, R.id.rl_cleaning_price_item);
        TextView orderCleaningFreightNum = ViewHolder.get(convertView,R.id.order_cleaning_freight_num);
        TextView orderCleaningSpecialCraftworkNum = ViewHolder.get(convertView,R.id.order_cleaning_special_craftwork_num);
        TextView takeOrderMaintainCleaningNum = ViewHolder.get(convertView,R.id.take_order_maintain_cleaning_num);
        TextView takeOrderFavourableCleaningNum = ViewHolder.get(convertView,R.id.take_order_favourable_cleaning_num);
        TextView cleaningTotalNum = ViewHolder.get(convertView,R.id.cleaning_total_num);
//        TextView cleaningChineseTotalFirst = ViewHolder.get(convertView,R.id.cleaning_chinese_total_first);
//        TextView cleaningChineseTotalNum = ViewHolder.get(convertView,R.id.cleaning_chinese_total_num);
//        TextView cleaningChineseTotalSecond = ViewHolder.get(convertView,R.id.cleaning_chinese_total_second);
        TextView cleaningOutOfPocket = ViewHolder.get(convertView,R.id.cleaning_out_of_pocket);
//        TextView cleaningBespeakTime = ViewHolder.get(convertView,R.id.cleaning_bespeak_time);
//        TextView cleaningBespeakTimeDetail = ViewHolder.get(convertView,R.id.cleaning_bespeak_time_detail);
        TextView cleaningOrderName = ViewHolder.get(convertView,R.id.cleaning_order_name);
        TextView cleaningPhoneNum = ViewHolder.get(convertView,R.id.cleaning_phone_num);
        TextView cleaningAddress = ViewHolder.get(convertView,R.id.cleaning_address);
        TextView cleaningNowTime = ViewHolder.get(convertView,R.id.cleaning_now_time);
        TextView cleaningCancel = ViewHolder.get(convertView,R.id.cleaning_cancel);
        TextView cleaningContactStore = ViewHolder.get(convertView,R.id.cleaning_contact_store);
        LinearLayout llCleaningItem = ViewHolder.get(convertView,R.id.ll_cleaning_item);
        TextView employeeLineNum = ViewHolder.get(convertView,R.id.employee_line_num);
        LinearLayout llCleaningPhone = ViewHolder.get(convertView,R.id.ll_cleaning_phone);

        if(item != null){
            orderCleaningNumber.setText("订单号：" + item.getOrdersn());
            List<OrderCleaningEntities.OrderCleaningResult.OrderCleaningItems> cleaningItems = item.getItemses();
            initInnerList(cleaningItems,position);

//            if(item.getCleaningItems() != null){
//                cleaningBespeakTime.setVisibility(View.GONE);
//                cleaningBespeakTimeDetail.setVisibility(View.GONE);
//            }else {
//                cleaningBespeakTimeDetail.setVisibility(View.VISIBLE);
//                cleaningBespeakTime.setVisibility(View.VISIBLE);
//                cleaningBespeakTimeDetail.setText(item.getTime());
//            }

            cleaningOrderName.setText(item.getuName());
            cleaningPhoneNum.setText(item.getuMobile());
            cleaningAddress.setText(item.getuAddress());
            cleaningNowTime.setText("时间：" + item.getoTime());
            cleaningCancel.setVisibility(View.GONE);

//            employeeLineNum.setText(String.valueOf(Integer.valueOf(countNum) - position));

            // TODO: 2017/12/22 需要后台一个参数来判断按钮是否置灰

//            if (item.getThrough().equals("1")){
//                cleaningContactStore.setBackgroundResource(R.drawable.login);
//                cleaningContactStore.setTextColor(context.getResources().getColor(R.color.white));
//                cleaningContactStore.setPadding(DeviceUtil.dp_to_px(context,5), DeviceUtil.dp_to_px(context,3),
//                        DeviceUtil.dp_to_px(context,5), DeviceUtil.dp_to_px(context,3));
//            }else {
//                cleaningContactStore.setBackgroundResource(R.drawable.check_not);
//                cleaningContactStore.setTextColor(context.getResources().getColor(R.color.white));
//                cleaningContactStore.setPadding(DeviceUtil.dp_to_px(context,5), DeviceUtil.dp_to_px(context,3),
//                        DeviceUtil.dp_to_px(context,5), DeviceUtil.dp_to_px(context,3));
//            }
            cleaningContactStore.setText("清洗完成");
            cleaningContactStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(positionListener != null){
                        positionListener.onPositionListener(position);
                    }
                }
            });

            llCleaningPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(telPhoneListener != null){
                        telPhoneListener.onTelPhone(position);
                    }
                }
            });

            orderCleaningFreightNum.setText("￥" + item.getFreightPrice());
            orderCleaningSpecialCraftworkNum.setText("￥" + item.getCraftPrice());
            takeOrderMaintainCleaningNum.setText("￥" + item.getKeepPrice());
            takeOrderFavourableCleaningNum.setText("￥" + item.getReducePrice());
            cleaningTotalNum.setText(String.valueOf(cleaningItems.size()));
            cleaningOutOfPocket.setText("￥" + item.getPayAmount());

//            if(item.getPayState() == 1){
//                cleaningChineseTotalFirst.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                cleaningChineseTotalNum.setText("￥" +item.getAmount() + "，");
//                cleaningChineseTotalNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                cleaningChineseTotalSecond.setVisibility(View.VISIBLE);
//                cleaningOutOfPocket.setVisibility(View.VISIBLE);
//                if(item.getPayAmount() != null){
//                    cleaningOutOfPocket.setVisibility(View.VISIBLE);
//                    cleaningOutOfPocket.setText("￥" + item.getPayAmount());
//                }else {
//                    cleaningOutOfPocket.setVisibility(GONE);
//                }
//            }else {
//                cleaningChineseTotalFirst.getPaint().setFlags(0);
//                cleaningChineseTotalNum.setText("￥" +item.getAmount() );
//                cleaningChineseTotalNum.getPaint().setFlags(0);
//                cleaningChineseTotalSecond.setVisibility(GONE);
//                cleaningOutOfPocket.setVisibility(GONE);
//            }

            llCleaningItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(gotoDetailListener != null){
                        gotoDetailListener.onClick(position);
                    }
                }
            });
        }
    }

    private void initInnerList(List<OrderCleaningEntities.OrderCleaningResult.OrderCleaningItems> cleaningItems, final int position) {
        if(cleaningItems != null){
            if (cleaningItems.size() <= 2){
                rlOrderCleaningShowMore.setVisibility(View.GONE);
            }else{
                rlOrderCleaningShowMore.setVisibility(View.VISIBLE);
            }
            cleaningShowSurplus.setText("查看更多");

            final CategoryCleaningInnerAdapter categoryCleaningInnerAdapter = new CategoryCleaningInnerAdapter(context,cleaningItems);
            orderCleaningListMore.setAdapter(categoryCleaningInnerAdapter);

            if (mDatas.get(position).isOpen){
                cleaningShowSurplusImg.setBackgroundResource(R.drawable.ic_up_arrow);
                cleaningShowSurplus.setText("隐藏更多");
                categoryCleaningInnerAdapter.setType(1);
                categoryCleaningInnerAdapter.notifyDataSetChanged();
            }else {
                cleaningShowSurplusImg.setBackgroundResource(R.drawable.ic_down_arrow);
                cleaningShowSurplus.setText("查看更多");
            }

            rlOrderCleaningShowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).isOpen){
                        categoryCleaningInnerAdapter.setType(0);
                        categoryCleaningInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =false;
                    }else{
                        categoryCleaningInnerAdapter.setType(1);
                        categoryCleaningInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =true;
                    }
                    if (cleaningShowMoreListener != null){
                        cleaningShowMoreListener.onClick(position,cleaningShowSurplusImg,cleaningShowSurplus);
                    }
                }
            });
            rlCleaningPriceItem.setVisibility(View.VISIBLE);

        }else {
            List<OrderCleaningEntities.OrderCleaningResult.OrderCleaningItems> item1 = new ArrayList<>();
            CategoryCleaningInnerAdapter categoryCleaningInnerAdapter = new CategoryCleaningInnerAdapter(context,item1);
            orderCleaningListMore.setAdapter(categoryCleaningInnerAdapter);
            rlOrderCleaningShowMore.setVisibility(View.GONE);
            rlCleaningPriceItem.setVisibility(View.GONE);
        }
    }
}
