package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.MessageEntity;
import com.shinaier.laundry.snlfactory.util.TimeUtils;
import com.shinaier.laundry.snlfactory.view.SwipeMenuLayout;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/16.
 */

public class MessageAdapter extends BaseAdapterNew<MessageEntity.MessageResult> {
    private OnNoticeListener onNoticeListener;
    private DeletePositionListener deletePositionListener;
    public interface DeletePositionListener{
        void onDelete(SwipeMenuLayout swipeMenuLayout,int position);
    }

    public interface OnNoticeListener{
        void onclick(int position);
    }

    public void setDeletePositionListener(DeletePositionListener deletePositionListener){
        this.deletePositionListener = deletePositionListener;
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener){
        this.onNoticeListener = onNoticeListener;
    }

    public MessageAdapter(Context context, List<MessageEntity.MessageResult> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.message_item;
    }

    @Override
    protected void setViewData(final View convertView, final int position) {
        MessageEntity.MessageResult item = getItem(position);
        LinearLayout llMessageItem = ViewHolder.get(convertView,R.id.ll_message_item);
        ImageView ivMsgNotice = ViewHolder.get(convertView,R.id.iv_msg_notice);
        TextView msgNoticeTitle = ViewHolder.get(convertView,R.id.msg_notice_title);
        TextView msgNoticeTime = ViewHolder.get(convertView,R.id.msg_notice_time);
        TextView msgNoticeContent = ViewHolder.get(convertView,R.id.msg_notice_content);
        Button btnDelete = ViewHolder.get(convertView,R.id.btnDelete);

        if(item != null){
            msgNoticeTitle.setText(item.getTitle());
            msgNoticeTime.setText(TimeUtils.formatTime(item.getTime()));
            msgNoticeContent.setText(item.getContent());
            if(item.getState().equals("0")){//当type=0时，是用户未读的消息，当type=1时，是用户读过的消息。
                ivMsgNotice.setVisibility(View.VISIBLE);
            }else {
                ivMsgNotice.setVisibility(View.GONE);
            }

            llMessageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onNoticeListener != null){
                        onNoticeListener.onclick(position);
                    }
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deletePositionListener != null){
                        deletePositionListener.onDelete((SwipeMenuLayout) convertView,position);
                    }
                }
            });

        }

    }
}
