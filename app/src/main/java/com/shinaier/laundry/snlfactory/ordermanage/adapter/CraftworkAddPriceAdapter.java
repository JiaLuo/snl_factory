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
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CraftworkAddPriceEntities;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 张家洛 on 2017/3/1.
 */

public class CraftworkAddPriceAdapter extends BaseAdapterNew<CraftworkAddPriceEntities.AddPriceItem> {
    private PositionListener listener;
    private Context context;
    private MaintainReviseListener maintainReviseListener;
    public interface MaintainReviseListener{
        void onMaintainRevise(int position);
    }
    public void setMaintainReviseListener(MaintainReviseListener maintainReviseListener){
        this.maintainReviseListener = maintainReviseListener;
    }

    public interface PositionListener{
        void onClick(int position);
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }

    public CraftworkAddPriceAdapter(Context context, List<CraftworkAddPriceEntities.AddPriceItem> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.craftwork_add_price_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        CraftworkAddPriceEntities.AddPriceItem item = getItem(position);
        SimpleDraweeView ivClothesImg = ViewHolder.get(convertView,R.id.iv_clothes_img);
        TextView addPriceClothesName = ViewHolder.get(convertView,R.id.add_price_clothes_name);
        TextView addPriceClothesPrice = ViewHolder.get(convertView,R.id.add_price_clothes_price);
        TextView addPriceRemarks = ViewHolder.get(convertView,R.id.add_price_remarks);
        TextView addPriceClothesNum = ViewHolder.get(convertView,R.id.add_price_clothes_num);
        TextView addPriceReviseRemark = ViewHolder.get(convertView,R.id.add_price_revise_remark);
        TextView addPriceCraftwork = ViewHolder.get(convertView,R.id.add_price_craftwork);
        TextView maintainValueCleanNum = ViewHolder.get(convertView,R.id.maintain_value_clean_num);//保值清洗费
        TextView maintainValueReviseRemark = ViewHolder.get(convertView,R.id.maintain_value_revise_remark);//保值金额修改按钮
        TextView maintainValue = ViewHolder.get(convertView,R.id.maintain_value);//保值金额

        if(item != null){
            if (item.getUrl() == null){
                ivClothesImg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.not_available_img));
            }else {
                String imgPath = Constants.Urls.URL_BASE_DOMAIN + item.getUrl();
                Uri uri = Uri.parse(imgPath);
                ivClothesImg.setImageURI(uri);
            }
            addPriceClothesName.setText(item.getName());
            addPriceClothesPrice.setText("￥" + item.getPrice());
            if (TextUtils.isEmpty(item.getSpecialComment())){
                addPriceRemarks.setText("暂无备注");
            }else {
                addPriceRemarks.setText("备注：" + item.getSpecialComment());
            }
            addPriceClothesNum.setText("数量：x" + item.getNumber());
            addPriceCraftwork.setText("特殊工艺加价：￥" + item.getSpecial());
            addPriceReviseRemark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onClick(position);
                    }
                }
            });

            maintainValueCleanNum.setText("保值清洗费: ￥" + formatMoney(item.getHedging()));
            maintainValue.setText("保值金额:￥" + formatMoney(item.getHedging() * 200));
            maintainValueReviseRemark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(maintainReviseListener != null){
                        maintainReviseListener.onMaintainRevise(position);
                    }
                }
            });
        }
    }

    public String formatMoney(double money){
        DecimalFormat df = new DecimalFormat("###0.00");
        return df.format(money);
    }
}
