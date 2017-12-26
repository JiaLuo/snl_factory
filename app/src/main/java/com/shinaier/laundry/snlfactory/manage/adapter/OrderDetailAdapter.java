package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OrderDetailEntity;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;

import java.util.List;


/**
 * Created by 张家洛 on 2017/3/7.
 */

public class OrderDetailAdapter extends BaseAdapterNew<OrderDetailEntity.OrderDetailResult.OrderDetailItems> {
    private CheckPhotoListener listener;
    private Context context;
    private String isOnline;

    public interface CheckPhotoListener{
        void onClick(int position, int photoPosition);
    }

    public void getPhotoListener(CheckPhotoListener listener){
        this.listener = listener;
    }

    public OrderDetailAdapter(Context context, List<OrderDetailEntity.OrderDetailResult.OrderDetailItems> mDatas,String isOnline) {
        super(context, mDatas);
        this.context = context;
        this.isOnline = isOnline;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.order_detail_item;
    }

    @Override
    protected void setViewData(View convertView, final int ListviewPosition) {
        OrderDetailEntity.OrderDetailResult.OrderDetailItems item = getItem(ListviewPosition);
        SimpleDraweeView orderDetailImg = ViewHolder.get(convertView, R.id.order_detail_img);
        TextView tvClothesName = ViewHolder.get(convertView, R.id.tv_clothes_name); //衣服名字
        TextView tvClothesPrice = ViewHolder.get(convertView, R.id.tv_clothes_price); //衣服价格
        TextView clothesNumber = ViewHolder.get(convertView, R.id.clothes_number); //衣物编码
        TextView tvTechnologyPrice = ViewHolder.get(convertView, R.id.tv_technology_price); //特殊工艺加价
        TextView editorMaintainValue = ViewHolder.get(convertView, R.id.editor_maintain_value); //保值金额
        TextView maintainValueCleanNum = ViewHolder.get(convertView, R.id.maintain_value_clean_num); //保值清洗费
        TextView tvTakeClothesTime = ViewHolder.get(convertView, R.id.tv_take_clothes_time); //取衣时间
        TextView tvRemarks = ViewHolder.get(convertView, R.id.tv_remarks); //备注
        WrapHeightGridView orderDetailGv = ViewHolder.get(convertView, R.id.order_detail_gv); //显示图片的GridView
        TextView orderDetailColourInfo = ViewHolder.get(convertView, R.id.order_detail_colour_info); //颜色描述
        TextView questionDetailInfo = ViewHolder.get(convertView, R.id.question_detail_info); //问题描述
        TextView orderCleanedAssess = ViewHolder.get(convertView, R.id.order_cleaned_assess); //洗后预估

        if (item != null){
            if(item.getImage() != null){
                orderDetailImg.setImageURI(Uri.parse(item.getImage()));
            }else {
                orderDetailImg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.not_available_img));
            }

            tvClothesName.setText(item.getItemName());
            tvClothesPrice.setText(item.getItemPrice());
            if (!TextUtils.isEmpty(item.getCleanSn())){
                clothesNumber.setText("衣物编码:" + item.getCleanSn());
            } else {
                clothesNumber.setText("衣物编码:******");
            }
            tvTechnologyPrice.setText("特殊工艺加价：￥" + item.getCraftPrice());

            if (item.getKeepPrice() == 0) { //判断保值金额等于0的处理，不然报错
                maintainValueCleanNum.setText("保值清洗费：￥0.00");
                editorMaintainValue.setText("保值金额：￥0.00");
            } else {
                maintainValueCleanNum.setText("保值清洗费：￥" + CommonTools.formatMoney(item.getKeepPrice()));
                editorMaintainValue.setText("保值金额：￥" + (CommonTools.formatMoney(item.getKeepPrice() * 200)));
            }

            if (isOnline.equals("1")) {
                tvTakeClothesTime.setVisibility(View.GONE);
            }

            if (item.getCraftDes() != null) {
                tvRemarks.setText("备注：" + item.getCraftDes());
            } else {
                tvRemarks.setText("备注：");
            }


            if (item.getColor() != null) { //判断颜色是否为空
                orderDetailColourInfo.setVisibility(View.VISIBLE);
                orderDetailColourInfo.setText(item.getColor());
            } else {
                orderDetailColourInfo.setVisibility(View.GONE);
            }

            if (item.getProblem() != null) { //判断问题描述是否为空
                questionDetailInfo.setVisibility(View.VISIBLE);
                questionDetailInfo.setText(item.getProblem());
            } else {
                questionDetailInfo.setVisibility(View.GONE);
            }

            if (item.getForecast() != null) {
                orderCleanedAssess.setVisibility(View.VISIBLE);
                orderCleanedAssess.setText(item.getForecast());
            } else {
                orderCleanedAssess.setVisibility(View.GONE);
            }


            if (item.getItemImages() != null && item.getItemImages().size() > 0) {
                orderDetailGv.setVisibility(View.VISIBLE);
                OrderDetailGridAdapter orderDetailGridAdapter = new OrderDetailGridAdapter(context, item.getItemImages());
                orderDetailGv.setAdapter(orderDetailGridAdapter);
                orderDetailGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (listener != null) {
                            listener.onClick(ListviewPosition, position);
                        }
                    }
                });
            } else {
                orderDetailGv.setVisibility(View.GONE);
            }
        }
    }
}
