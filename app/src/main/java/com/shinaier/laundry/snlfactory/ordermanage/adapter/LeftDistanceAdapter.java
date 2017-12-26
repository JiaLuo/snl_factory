package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.TakeTimeEntity;

import java.util.List;



/**
 *
 * Created by jacktian on 15/9/14.
 */
public class LeftDistanceAdapter extends BaseAdapterNew<TakeTimeEntity> {

    public LeftDistanceAdapter(Context context, List<TakeTimeEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.distance_spinner_left_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        TakeTimeEntity item = this.getItem(position);
        TextView tv = ViewHolder.get(convertView, R.id.tv);
        tv.setText(item.getDate());
        View view = ViewHolder.get(convertView,R.id.left_view);
        View view1 = ViewHolder.get(convertView,R.id.right_view);
        if(item.isSelect()){
            view.setVisibility(View.VISIBLE);
            view1.setVisibility(View.INVISIBLE);
            tv.setTextColor(this.getContext().getResources().getColor(R.color.bbbbb));
            convertView.setBackgroundColor(this.getContext().getResources().getColor(R.color.white));
        }else{
            view.setVisibility(View.INVISIBLE);
            view1.setVisibility(View.VISIBLE);
            tv.setTextColor(this.getContext().getResources().getColor(android.R.color.black));
            convertView.setBackgroundColor(this.getContext().getResources().getColor(R.color.aaaaa));
        }
    }

    public void setSelectedPosition(int pos) {
        for (int i = 0; i < getCount(); i++){
            if (i == pos){
                getItem(i).setSelect(true);
            }else {
                getItem(i).setSelect(false);
            }
        }
        notifyDataSetChanged();
    }
}
