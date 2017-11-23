package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.MessageNoticeEntity;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/16.
 */

public class MessageNoticeAdapter extends BaseAdapterNew<MessageNoticeEntity> {
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener{
        void onclick(int position);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener){
        this.onNoticeListener = onNoticeListener;
    }

    public MessageNoticeAdapter(Context context, List<MessageNoticeEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.message_notice_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        MessageNoticeEntity item = getItem(position);
        RelativeLayout rlMsgNotice = ViewHolder.get(convertView,R.id.rl_msg_notice);
        final ImageView ivMsgNotice = ViewHolder.get(convertView,R.id.iv_msg_notice);
        TextView msgNoticeTitle = ViewHolder.get(convertView,R.id.msg_notice_title);
        TextView msgNoticeTime = ViewHolder.get(convertView,R.id.msg_notice_time);
        TextView msgNoticeContent = ViewHolder.get(convertView,R.id.msg_notice_content);
        if(item != null){
            msgNoticeTitle.setText(item.getTitle());
            msgNoticeTime.setText(item.getTime());
            msgNoticeContent.setText(item.getContent());
            if(item.getState() == 0){
                ivMsgNotice.setVisibility(View.VISIBLE);
            }else {
                ivMsgNotice.setVisibility(View.GONE);
            }
        }
        rlMsgNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ivMsgNotice.setVisibility(View.GONE);
                if(onNoticeListener != null){
                    onNoticeListener.onclick(position);
                }
            }
        });

    }
}
