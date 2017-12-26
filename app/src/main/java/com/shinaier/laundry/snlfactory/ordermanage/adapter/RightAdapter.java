package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;

import java.util.List;



public class RightAdapter extends BaseAdapterNew<String> {

	private int pos;
	private Context context;

	public RightAdapter(Context context, List<String> mDatas) {
		super(context, mDatas);
		this.context = context;
	}


	@Override
	protected int getResourceId(int Position) {
		return R.layout.distance_spinner_right_item;
	}

	@Override
	protected void setViewData(View convertView, int position) {
		String item = this.getItem(position);
//
		TextView tv = ViewHolder.get(convertView, R.id.tv);
		tv.setText(item);
//		if (position == 0){
//			tv.setText(String.format(context.getResources().getString(R.string.all),entity.getName()));
//		}else {
//			tv.setText(entity.getName());
//		}
//
		if(pos == position){
			tv.setTextColor(this.getContext().getResources().getColor(R.color.bbbbb));
		}else{
			tv.setTextColor(this.getContext().getResources().getColor(android.R.color.black));
		}
//
	}
	public void setSelectedPosition(int pos) {
		this.pos = pos;
	}
}
