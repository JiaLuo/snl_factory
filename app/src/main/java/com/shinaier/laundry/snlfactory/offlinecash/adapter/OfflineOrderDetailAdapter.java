package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OfflineOrderDetailEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/8/1.
 */

public class OfflineOrderDetailAdapter extends BaseAdapterNew<OfflineOrderDetailEntity.OfflineOrderDetailDatas.OfflineOrderDetailItems> {
    private Context context;
    private CheckPhotoListener listener;
    public interface CheckPhotoListener{
        void onClick(int position, int photoPosition);
    }

    public void getPhotoListener(CheckPhotoListener listener){
        this.listener = listener;
    }
    public OfflineOrderDetailAdapter(Context context, List<OfflineOrderDetailEntity.OfflineOrderDetailDatas.OfflineOrderDetailItems> mDatas) {
        super(context, mDatas);
        this.context = context;

    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.offline_order_detail_item;
    }

    @Override
    protected void setViewData(View convertView, final int ListviewPosition) {
        OfflineOrderDetailEntity.OfflineOrderDetailDatas.OfflineOrderDetailItems item = getItem(ListviewPosition);
        SimpleDraweeView orderDetailImg = ViewHolder.get(convertView,R.id.order_detail_img);
        TextView clothesName = ViewHolder.get(convertView,R.id.clothes_name);
        TextView laundryNum = ViewHolder.get(convertView,R.id.laundry_num);
        TextView laundryPrice = ViewHolder.get(convertView,R.id.laundry_price);
        TextView technologyPriceMarkup = ViewHolder.get(convertView,R.id.technology_price_markup);
        TextView maintainValueCleanDetail = ViewHolder.get(convertView,R.id.maintain_value_clean_detail);
        TextView maintainValueMoneyDetail = ViewHolder.get(convertView,R.id.maintain_value_money_detail);
        GridView orderDetailGv = ViewHolder.get(convertView,R.id.order_detail_gv);
        LinearLayout llOrderDetailInfo = ViewHolder.get(convertView,R.id.ll_order_detail_info);
        TextView orderDetailRemarkInfo = ViewHolder.get(convertView,R.id.order_detail_remark_info);
        LinearLayout llOrderDetailColour = ViewHolder.get(convertView,R.id.ll_order_detail_colour);
        TextView orderDetailColourInfo = ViewHolder.get(convertView,R.id.order_detail_colour_info);
        LinearLayout llQuestionDetail = ViewHolder.get(convertView,R.id.ll_question_detail);
        TextView questionDetailInfo = ViewHolder.get(convertView,R.id.question_detail_info);

        if (item != null){
            if(item.getUrl() != null){
                String imgPath = Constants.Urls.URL_BASE_DOMAIN + item.getUrl();
                Uri uri = Uri.parse(imgPath);
                orderDetailImg.setImageURI(uri);
            }else {
                orderDetailImg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.not_available_img));
            }

            clothesName.setText(item.getgName());
            laundryNum.setText("数量：X" + item.getNumber());
            laundryPrice.setText("￥" + item.getPrice());
            technologyPriceMarkup.setText("特殊工艺加价：￥" + item.getSpecial());
            maintainValueCleanDetail.setText("保值清洗费：￥" + item.getHedging());
            maintainValueMoneyDetail.setText("保值金额：￥" + (item.getHedging() * 20));

            if(item.getItemNote() != null){
                llQuestionDetail.setVisibility(View.VISIBLE);
                questionDetailInfo.setText(item.getItemNote());
            }else {
                llQuestionDetail.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(item.getColor())){
                llOrderDetailColour.setVisibility(View.VISIBLE);
                orderDetailColourInfo.setText(item.getColor());
            }else {
                llOrderDetailColour.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(item.getSpecialComment())){
                llOrderDetailInfo.setVisibility(View.VISIBLE);
                orderDetailRemarkInfo.setText(item.getSpecialComment());
            }else {
                llOrderDetailInfo.setVisibility(View.GONE);
            }
            if(item.getImgs() != null && item.getImgs().size() > 0){
                orderDetailGv.setVisibility(View.VISIBLE);
                OfflineOrderDetailGridAdapter offlineOrderDetailGridAdapter = new OfflineOrderDetailGridAdapter(context,item.getImgs());
                orderDetailGv.setAdapter(offlineOrderDetailGridAdapter);
                orderDetailGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(listener != null){
                            listener.onClick(ListviewPosition,position);
                        }
                    }
                });
            }else {
                orderDetailGv.setVisibility(View.GONE);
            }
        }
    }
}
