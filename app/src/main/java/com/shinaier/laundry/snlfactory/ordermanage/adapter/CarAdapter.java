package com.shinaier.laundry.snlfactory.ordermanage.adapter;


import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.ordermanage.entities.GoodsBean;
import com.shinaier.laundry.snlfactory.ordermanage.view.AddWidget;

import java.math.BigDecimal;
import java.util.List;



public class CarAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
	private AddWidget.OnAddClick onAddClick;

	public CarAdapter(@Nullable List<GoodsBean> data, AddWidget.OnAddClick onAddClick) {
		super(R.layout.item_car, data);
		this.onAddClick = onAddClick;
	}

	@Override
	protected void convert(BaseViewHolder helper, GoodsBean item) {
		SimpleDraweeView car_bg = helper.getView(R.id.car_bg);
        car_bg.setImageURI(Uri.parse(item.getImage()));
        TextView car_discount = helper.getView(R.id.car_discount);
        if (item.getItem_discount().equals("10.00")){
            car_discount.setVisibility(View.GONE);
        }else {
            car_discount.setVisibility(View.VISIBLE);
            car_discount.setText(item.getItem_discount() + "折");
        }

		helper.setText(R.id.car_name, item.getItem_name())
				.setText(R.id.car_price, item.getStrPrice(mContext, item.getItem_price().multiply(BigDecimal.valueOf(item.getSelectCount()))))
		;
		AddWidget addWidget = helper.getView(R.id.car_addwidget);
//		addWidget.setData(this, helper.getAdapterPosition(), onAddClick);
		addWidget.setData(onAddClick,item);
	}
}
