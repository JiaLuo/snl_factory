package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.ordermanage.entities.QuestionSettingEntity;

import java.util.List;


/**
 * Created by 张家洛 on 2017/3/9.
 */

public class QuestionsAdapter extends BaseAdapterNew<QuestionSettingEntity> {
    public QuestionsAdapter(Context context, List<QuestionSettingEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.questions_item;
    }

    @Override
    protected void setViewData(View convertView, int position) {
        QuestionSettingEntity item = getItem(position);
        TextView tvQuestion = ViewHolder.get(convertView,R.id.tv_question);
        ImageView ivQuestionInfoSelect = ViewHolder.get(convertView,R.id.iv_question_info_select);
        if(item != null){
            tvQuestion.setText(item.getQuestion());
            if(item.getIscheck() == 0){
                ivQuestionInfoSelect.setSelected(false);
            }else {
                ivQuestionInfoSelect.setSelected(true);
            }
        }
    }
}
