package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderInquiryEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderSearchEntity;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by 张家洛 on 2017/12/25.
 */

public class OrderCategoryAdapter extends BaseAdapterNew<OrderSearchEntity.OrderSearchResult> {
    private Context context;
    private List<OrderSearchEntity.OrderSearchResult> mDatas;
    private PositionListener positionListener;
    private RelativeLayout rlShowMore;
    private TextView showSurplusNum;
    private ImageView showSurplusNumImg;
    private WrapHeightListView orderListMore;
    private ShowMoreClickListener listener;
    private RelativeLayout rlPriceItem;

    public interface ShowMoreClickListener{
        void onClick(int position, ImageView iv, TextView tv);
    }

    public void setShowMoreClickListener(ShowMoreClickListener listener){
        this.listener = listener;
    }

    public interface PositionListener{
        void onPositionClick(int position);
    }

    public void setPositionListener(PositionListener positionListener){
        this.positionListener = positionListener;
    }

    public OrderCategoryAdapter(Context context, List<OrderSearchEntity.OrderSearchResult> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.order_category_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        OrderSearchEntity.OrderSearchResult item = getItem(position);

        TextView orderNumber = ViewHolder.get(convertView, R.id.order_number);
        TextView orderStatus = ViewHolder.get(convertView, R.id.order_status);
        orderListMore = ViewHolder.get(convertView, R.id.order_list_more);
        rlShowMore = ViewHolder.get(convertView, R.id.rl_show_more);
        showSurplusNum = ViewHolder.get(convertView, R.id.show_surplus_num);
        showSurplusNumImg = ViewHolder.get(convertView, R.id.show_surplus_num_img);
        rlPriceItem = ViewHolder.get(convertView, R.id.rl_price_item);
        TextView freightNum = ViewHolder.get(convertView, R.id.freight_num);
        TextView specialCraftworkNum = ViewHolder.get(convertView, R.id.special_craftwork_num);
        TextView maintainCleanNum = ViewHolder.get(convertView, R.id.maintain_clean_num);
        TextView favourableNum = ViewHolder.get(convertView, R.id.favourable_num);
        TextView totalNum = ViewHolder.get(convertView, R.id.total_num);
        TextView outOfPocket = ViewHolder.get(convertView, R.id.out_of_pocket);
        TextView orderName = ViewHolder.get(convertView, R.id.order_name);
        TextView orderPhoneNum = ViewHolder.get(convertView, R.id.order_phone_num);
        TextView orderAddress = ViewHolder.get(convertView, R.id.order_address);
        TextView orderNowTime = ViewHolder.get(convertView, R.id.order_now_time);
        LinearLayout llOrderDetailItem = ViewHolder.get(convertView, R.id.ll_order_detail_item);

        if (item != null) {
            orderNumber.setText("订单号:" + item.getOrdersn());
            switch (item.getoStatus()) {
                case "0":
                    orderStatus.setText("预约下单");
                    break;
                case "1":
                    orderStatus.setText("店铺确认收单");
                    break;
                case "2":
                    orderStatus.setText("已收衣");
                    break;
                case "3":
                    orderStatus.setText("清洗中");
                    break;
                case "4":
                    orderStatus.setText("完成清洗");
                    break;
                case "5":
                    orderStatus.setText("送件完成");
                    break;
                case "10":
                    orderStatus.setText("烘干中");
                    break;
                case "11":
                    orderStatus.setText("熨烫中");
                    break;
                case "12":
                    orderStatus.setText("质检中");
                    break;
                case "13":
                    orderStatus.setText("已上挂");
                    break;
                case "99":
                    orderStatus.setText("订单完成");
                    break;
                case "101":
                    orderStatus.setText("用户取消单");
                    break;
                case "102":
                    orderStatus.setText("店铺取消单");
                    break;
            }



            List<OrderSearchEntity.OrderSearchResult.OrderSearchItems> itemList = item.getItemses();
            initInnerList(itemList, position);

            orderName.setText(item.getuName());
            orderPhoneNum.setText(item.getuMobile());
            orderAddress.setText(item.getuAddress());
            orderNowTime.setText("时间：" + item.getoTime());
            freightNum.setText("￥" + item.getFreightPrice());
            specialCraftworkNum.setText("￥" + item.getCraftPrice());
            maintainCleanNum.setText("￥" + item.getKeepPrice());
            favourableNum.setText("￥" + item.getReducePrice());
            totalNum.setText(item.getItemCount());
            outOfPocket.setText("￥" + item.getPayAmount());


            llOrderDetailItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (positionListener != null) {
                        positionListener.onPositionClick(position);
                    }
                }
            });
        }
    }

        private void initInnerList(List<OrderSearchEntity.OrderSearchResult.OrderSearchItems> itemList, final int position) {
            if(itemList.size() > 0){
                if (itemList.size() <= 2){
                    rlShowMore.setVisibility(View.GONE);
                }else{
                    rlShowMore.setVisibility(View.VISIBLE);

                }
                showSurplusNum.setText("查看更多");

                final OrderCategoryInnerAdapter orderCategoryInnerAdapter = new OrderCategoryInnerAdapter(context,itemList);
                orderListMore.setAdapter(orderCategoryInnerAdapter);

                if (mDatas.get(position).isOpen){
                    showSurplusNumImg.setBackgroundResource(R.drawable.ic_up_arrow);
                    showSurplusNum.setText("隐藏更多");
                    orderCategoryInnerAdapter.setType(1);
                    orderCategoryInnerAdapter.notifyDataSetChanged();
                }else {
                    showSurplusNumImg.setBackgroundResource(R.drawable.ic_down_arrow);
                    showSurplusNum.setText("查看更多");
                }

                rlShowMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDatas.get(position).isOpen){
                            orderCategoryInnerAdapter.setType(0);
                            orderCategoryInnerAdapter.notifyDataSetChanged();
                            mDatas.get(position).isOpen =false;
                        }else{
                            orderCategoryInnerAdapter.setType(1);
                            orderCategoryInnerAdapter.notifyDataSetChanged();
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

