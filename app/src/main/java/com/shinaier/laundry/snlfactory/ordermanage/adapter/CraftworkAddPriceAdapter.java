package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.CraftworkAddPriceEntities;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 张家洛 on 2017/3/1.
 */

public class CraftworkAddPriceAdapter extends BaseAdapterNew<CraftworkAddPriceEntities.CraftworkAddPriceResult.CraftworkAddPriceItems> {
    private Context context;
    private String isOnline;

    public CraftworkAddPriceAdapter(Context context, List<CraftworkAddPriceEntities.CraftworkAddPriceResult.CraftworkAddPriceItems> mDatas,String isOnline) {
        super(context, mDatas);
        this.context = context;
        this.isOnline = isOnline;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.craftwork_add_price_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        CraftworkAddPriceEntities.CraftworkAddPriceResult.CraftworkAddPriceItems item = getItem(position);
        SimpleDraweeView ivClothesImg = ViewHolder.get(convertView,R.id.iv_clothes_img); //
        TextView tvClothesName = ViewHolder.get(convertView,R.id.tv_clothes_name);
        TextView tvClothesPrice = ViewHolder.get(convertView,R.id.tv_clothes_price);
        TextView clothesNumber = ViewHolder.get(convertView,R.id.clothes_number);
        TextView tvTechnologyPrice = ViewHolder.get(convertView,R.id.tv_technology_price);
        TextView editorMaintainValue = ViewHolder.get(convertView,R.id.editor_maintain_value);
        TextView maintainValueCleanNum = ViewHolder.get(convertView,R.id.maintain_value_clean_num);
        TextView tvTakeClothesTime = ViewHolder.get(convertView,R.id.tv_take_clothes_time);
        TextView tvRemarks = ViewHolder.get(convertView,R.id.tv_remarks);

        if(item != null){
            if (item.getImage() == null){
                ivClothesImg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.not_available_img));
            }else {
                ivClothesImg.setImageURI(Uri.parse(item.getImage()));
            }
            tvClothesName.setText(item.getItemName());
            tvClothesPrice.setText("￥" + item.getItemPrice());
            if (item.getCleanSn() != null){
                clothesNumber.setText("衣物编码：" + item.getCleanSn());
            }else {
                clothesNumber.setText("衣物编码：********");
            }
            if (isOnline.equals("1")){ // isline 参数 1 是线上 2 是线下
                tvTakeClothesTime.setVisibility(View.GONE);
            }else {
                tvTakeClothesTime.setVisibility(View.VISIBLE);
                tvTakeClothesTime.setText(item.getTakeTime());
            }
            if (TextUtils.isEmpty(item.getCraftDes())){
                tvRemarks.setText("备注：暂无备注");
            }else {
                tvRemarks.setText("备注：" + item.getCraftDes());
            }
            tvTechnologyPrice.setText("特殊工艺加价：￥" + item.getCraftPrice());
            maintainValueCleanNum.setText("保值清洗费: ￥" + formatMoney(item.getKeepPrice()));
            editorMaintainValue.setText("保值金额:￥" + formatMoney(item.getKeepPrice() * 200));
        }
    }

    public String formatMoney(double money){
        DecimalFormat df = new DecimalFormat("###0.00");
        return df.format(money);
    }
}
