package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.EvaluateEntities;
import com.shinaier.laundry.snlfactory.util.TimeUtils;

import java.util.List;


/**
 * Created by 张家洛 on 2017/2/16.
 */

public class UserEvaluateAdapter extends BaseAdapterNew<EvaluateEntities.EvaluateResult> {
    private PositionListener listener;

    public interface PositionListener{
        void onClick(int position);
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }

    public UserEvaluateAdapter(Context context, List<EvaluateEntities.EvaluateResult> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.user_evaluate_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        EvaluateEntities.EvaluateResult item = getItem(position);

        TextView personPhoneNum = ViewHolder.get(convertView,R.id.person_phone_num);
        TextView personEvaluateTime = ViewHolder.get(convertView,R.id.person_evaluate_time);
        TextView assessNum = ViewHolder.get(convertView,R.id.assess_num);
        RatingBar ratingBarPublishComment = ViewHolder.get(convertView,R.id.rating_bar_publish_comment);
        TextView evaluateContent = ViewHolder.get(convertView,R.id.evaluate_content);
        LinearLayout llStoreReply = ViewHolder.get(convertView,R.id.ll_store_reply);
        TextView storeReplyContent = ViewHolder.get(convertView,R.id.store_reply_content);
        ImageView ivEvaluateBt = ViewHolder.get(convertView,R.id.iv_evaluate_bt);

        if(item != null){
            personPhoneNum.setText(item.getuName());
            personEvaluateTime.setText(TimeUtils.formatTime(item.getaTime()));
            assessNum.setText(item.getLevel() + "分");
            ratingBarPublishComment.setRating(Float.parseFloat(item.getLevel()));
            evaluateContent.setText(item.getuComment());
            if(item.getHasAnswer().equals("1")){
                llStoreReply.setVisibility(View.VISIBLE);
                storeReplyContent.setText(item.getmAnswer());
                ivEvaluateBt.setVisibility(View.GONE);
            }else {
                llStoreReply.setVisibility(View.GONE);
                ivEvaluateBt.setVisibility(View.VISIBLE);
                ivEvaluateBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listener != null){
                            listener.onClick(position);
                        }
                    }
                });
            }
        }
    }
}
