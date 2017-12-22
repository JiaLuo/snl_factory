package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.common.utils.DeviceUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleanEntities;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张家洛 on 2017/2/25.
 */

public class CategoryCleanAdapter extends BaseAdapterNew<OrderCleanEntities.OrderCleanResult> {
    private Context context;
    private RelativeLayout rlOrderCleanShowMore;
    private TextView cleanShowSurplus;
    private WrapHeightListView orderCleanListMore;
    private List<OrderCleanEntities.OrderCleanResult> mDatas;
    private ImageView cleanShowSurplusImg;
    private CleanShowMoreListener cleanShowMoreListener;
    private RelativeLayout rlCleanPriceItem;
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
        void onCheck(int position);
        void onChecked(int position);
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }

    public interface CleanShowMoreListener{
        void onClick(int position, ImageView iv, TextView tv);
    }

   public void setCleanShowMoreListener(CleanShowMoreListener cleanShowMoreListener){
       this.cleanShowMoreListener = cleanShowMoreListener;
   }

    public interface GotoDetailListener{
        void onClick(int position);
    }

    public void setGotoDetailListener(GotoDetailListener gotoDetailListener){
        this.gotoDetailListener = gotoDetailListener;
    }

    public CategoryCleanAdapter(Context context, List<OrderCleanEntities.OrderCleanResult> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    public void setCountNum(String countNum){
        this.countNum = countNum;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.category_clean_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OrderCleanEntities.OrderCleanResult item = getItem(position);
        TextView orderCleanNumber = ViewHolder.get(convertView,R.id.order_clean_number);
        orderCleanListMore = ViewHolder.get(convertView, R.id.order_clean_list_more);
        rlOrderCleanShowMore = ViewHolder.get(convertView, R.id.rl_order_clean_show_more);
        cleanShowSurplus = ViewHolder.get(convertView, R.id.clean_show_surplus);
        cleanShowSurplusImg = ViewHolder.get(convertView, R.id.clean_show_surplus_img);
        rlCleanPriceItem = ViewHolder.get(convertView, R.id.rl_clean_price_item);
        TextView orderCleanFreightNum = ViewHolder.get(convertView,R.id.order_clean_freight_num);
        TextView orderCleanSpecialCraftworkNum = ViewHolder.get(convertView,R.id.order_clean_special_craftwork_num);
        TextView takeOrderMaintainCleanNum = ViewHolder.get(convertView,R.id.take_order_maintain_clean_num);
        TextView takeOrderFavourableNum = ViewHolder.get(convertView,R.id.take_order_favourable_num);
        TextView takeOrderTotalNum = ViewHolder.get(convertView,R.id.take_order_total_num);
        TextView takeOrderOutOfPocket = ViewHolder.get(convertView,R.id.take_order_out_of_pocket);
        TextView takeOrderOrderName = ViewHolder.get(convertView,R.id.take_order_order_name);
        TextView takeOrderPhoneNum = ViewHolder.get(convertView,R.id.take_order_phone_num);
        TextView takeOrderAddress = ViewHolder.get(convertView,R.id.take_order_address);
        TextView takeOrderNowTime = ViewHolder.get(convertView,R.id.take_order_now_time);
        TextView takeOrderCancel = ViewHolder.get(convertView,R.id.take_order_cancel);
        TextView takeOrderContactStore = ViewHolder.get(convertView,R.id.take_order_contact_store);
        LinearLayout llCleanItem = ViewHolder.get(convertView,R.id.ll_clean_item);
        TextView employeeLineNum = ViewHolder.get(convertView,R.id.employee_line_num);
        LinearLayout llTakeOrderPhone = ViewHolder.get(convertView,R.id.ll_take_order_phone);

        if(item != null){
            orderCleanNumber.setText("订单号：" + item.getOrdersn());
            List<OrderCleanEntities.OrderCleanResult.OrderCleanItems> cleanItems = item.getItemses();
            initInnerList(cleanItems,position);

            takeOrderOrderName.setText(item.getuName());
            takeOrderPhoneNum.setText(item.getuMobile());
            takeOrderAddress.setText(item.getuAddress());
            takeOrderNowTime.setText("时间：" + item.getoTime());
//            employeeLineNum.setText(String.valueOf(Integer.valueOf(countNum) - position));
            takeOrderCancel.setText("检查衣物");
            takeOrderContactStore.setText("检查完成");

            if (item.getChecked().equals("0")){ //是否可以检查完成:1-是;然否
                takeOrderContactStore.setBackgroundResource(R.drawable.check_not);
                takeOrderContactStore.setTextColor(context.getResources().getColor(R.color.white));
                takeOrderContactStore.setPadding(DeviceUtil.dp_to_px(context,7), DeviceUtil.dp_to_px(context,5),
                        DeviceUtil.dp_to_px(context,7), DeviceUtil.dp_to_px(context,5));
                takeOrderContactStore.setOnClickListener(null);
            }else {
                takeOrderContactStore.setBackgroundResource(R.drawable.login);
                takeOrderContactStore.setTextColor(context.getResources().getColor(R.color.white));
                takeOrderContactStore.setPadding(DeviceUtil.dp_to_px(context,7), DeviceUtil.dp_to_px(context,5),
                        DeviceUtil.dp_to_px(context,7), DeviceUtil.dp_to_px(context,5));
                takeOrderContactStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listener != null){
                            listener.onChecked(position);
                        }
                    }
                });
            }

            orderCleanFreightNum.setText("￥" + item.getFreightPrice());
            orderCleanSpecialCraftworkNum.setText("￥" + item.getCraftPrice());
            takeOrderMaintainCleanNum.setText("￥" + (item.getKeepPrice()));
            takeOrderFavourableNum.setText("￥" + item.getReducePrice());
            takeOrderTotalNum.setText(String.valueOf(cleanItems.size()));
            takeOrderOutOfPocket.setText("￥" + item.getPayAmount());

            llCleanItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(gotoDetailListener != null){
                        gotoDetailListener.onClick(position);
                    }
                }
            });
            takeOrderCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onCheck(position);
                    }
                }
            });

            llTakeOrderPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(telPhoneListener != null){
                        telPhoneListener.onTelPhone(position);
                    }
                }
            });
        }
    }

    private void initInnerList(List<OrderCleanEntities.OrderCleanResult.OrderCleanItems> cleanItems, final int position) {
        if(cleanItems != null){
            if (cleanItems.size() <= 2){
                rlOrderCleanShowMore.setVisibility(View.GONE);
            }else{
                rlOrderCleanShowMore.setVisibility(View.VISIBLE);
            }
            cleanShowSurplus.setText("查看更多");

            final CategoryCleanInnerAdapter categoryCleanInnerAdapter = new CategoryCleanInnerAdapter(context,cleanItems);
            orderCleanListMore.setAdapter(categoryCleanInnerAdapter);

            if (mDatas.get(position).isOpen){
                cleanShowSurplusImg.setBackgroundResource(R.drawable.ic_up_arrow);
                cleanShowSurplus.setText("隐藏更多");
                categoryCleanInnerAdapter.setType(1);
                categoryCleanInnerAdapter.notifyDataSetChanged();
            }else {
                cleanShowSurplusImg.setBackgroundResource(R.drawable.ic_down_arrow);
                cleanShowSurplus.setText("查看更多");
            }

            rlOrderCleanShowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).isOpen){
                        categoryCleanInnerAdapter.setType(0);
                        categoryCleanInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =false;
                    }else{
                        categoryCleanInnerAdapter.setType(1);
                        categoryCleanInnerAdapter.notifyDataSetChanged();
                        mDatas.get(position).isOpen =true;
                    }
                    if (cleanShowMoreListener != null){
                        cleanShowMoreListener.onClick(position,cleanShowSurplusImg,cleanShowSurplus);
                    }
                }
            });
            rlCleanPriceItem.setVisibility(View.VISIBLE);
        }else {
            List<OrderCleanEntities.OrderCleanResult.OrderCleanItems> item1 = new ArrayList<>();
            final CategoryCleanInnerAdapter categoryCleanInnerAdapter = new CategoryCleanInnerAdapter(context,item1);
            orderCleanListMore.setAdapter(categoryCleanInnerAdapter);

            rlOrderCleanShowMore.setVisibility(View.GONE);
            rlCleanPriceItem.setVisibility(View.GONE);
        }
    }
}
