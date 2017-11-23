package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.common.utils.LogUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.CheckClothesEntities;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/2.
 */

public class CheckClothesAdapter extends BaseAdapterNew<CheckClothesEntities> {
    private Context context;
    private PositionListener listener;
    private EditPositionListener editPositionListener;
    private AddPhotoListener addPhotoListener;
    private EditPhotoListener editPhotoListener;
    private ShowBigPhotoListener showBigPhotoListener;

    public interface ShowBigPhotoListener{
        void onShowBigPhoto(int position, int innerPosition);
    }

    public void setShowBigPhotoListener(ShowBigPhotoListener showBigPhotoListener){
        this.showBigPhotoListener = showBigPhotoListener;
    }

    public interface EditPhotoListener{
        void onEditPhotoClick(int position);
    }

    public void setEditPhotoListener(EditPhotoListener editPhotoListener){
        this.editPhotoListener = editPhotoListener;
    }

    public interface AddPhotoListener{
        void onAddPhotoClick(int position);
    }

    public void setAddPhotoListener(AddPhotoListener addPhotoListener){
        this.addPhotoListener = addPhotoListener;
    }

    public interface EditPositionListener{
        void onEditQuestion(int position);
        void onEditColor(int position);

    }
    public void setEditPositionListener(EditPositionListener editPositionListener){
        this.editPositionListener = editPositionListener;
    }

    public interface PositionListener{
        void onColorSetting(int position);
        void onQuestionInfo(int position);
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }
    public CheckClothesAdapter(Context context, List<CheckClothesEntities> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.check_clothes_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        LogUtil.e("zhang","aaaaaaaaaaaaaaaaaaaaaaaaa");
        CheckClothesEntities item = getItem(position);
        TextView checkClothesName = ViewHolder.get(convertView,R.id.check_clothes_name);
        TextView checkClothesNum = ViewHolder.get(convertView,R.id.check_clothes_num);
        WrapHeightGridView checkClothesImg = ViewHolder.get(convertView,R.id.check_clothes_img);
        LinearLayout llColorAndQuestion = ViewHolder.get(convertView,R.id.ll_color_and_question); // 两个按钮的父布局
        TextView colorSettingBt = ViewHolder.get(convertView,R.id.color_setting_bt);
        TextView questionInfoBt = ViewHolder.get(convertView,R.id.question_info_bt);
        LinearLayout llColorInfo = ViewHolder.get(convertView,R.id.ll_color_info);
        TextView colorInfo = ViewHolder.get(convertView,R.id.color_info);
        TextView colorEdit = ViewHolder.get(convertView,R.id.color_edit);
        LinearLayout llQuestionInfo = ViewHolder.get(convertView,R.id.ll_question_info);
        TextView questionInfo = ViewHolder.get(convertView,R.id.question_info);
        TextView questionInfoEdit = ViewHolder.get(convertView,R.id.question_info_edit);

        if(item != null) {
            checkClothesName.setText(item.getgName());
            checkClothesNum.setText("数量：x" + item.getNumber());
            final List<String> imgs = item.getImgs();
            CheckClothesImgAdapter checkClothesImgAdapter = new CheckClothesImgAdapter(context,imgs);
            checkClothesImg.setAdapter(checkClothesImgAdapter);
            checkClothesImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (imgs.size() == 0){
                        if(addPhotoListener != null){
                            addPhotoListener.onAddPhotoClick(position);
                        }
                    }else if(imgs.size()>=4){
                        if (i == 3){
                            if(editPhotoListener != null){
                                editPhotoListener.onEditPhotoClick(position);
                            }
                        }else{
                            if(showBigPhotoListener != null){
                                showBigPhotoListener.onShowBigPhoto(position,i);
                            }
                        }
                    }else{
                        if (i < imgs.size()){
                            if(showBigPhotoListener != null){
                                showBigPhotoListener.onShowBigPhoto(position,i);
                            }
                        }else if(i == imgs.size()){
                            if(editPhotoListener != null){
                                editPhotoListener.onEditPhotoClick(position);
                            }
                        }
                    }
                }
            });

            if(!TextUtils.isEmpty(item.getColor()) && TextUtils.isEmpty(item.getItemNote())){
                llColorAndQuestion.setVisibility(View.VISIBLE);
                colorSettingBt.setVisibility(View.GONE);
                llColorInfo.setVisibility(View.VISIBLE);
                colorInfo.setText(item.getColor());

                llQuestionInfo.setVisibility(View.GONE);
                questionInfoBt.setVisibility(View.VISIBLE);
                questionInfoBt.setText("问题描述");
            }else if(!TextUtils.isEmpty(item.getItemNote()) && TextUtils.isEmpty(item.getColor())){
                llColorAndQuestion.setVisibility(View.VISIBLE);
                colorSettingBt.setVisibility(View.VISIBLE);
                llColorInfo.setVisibility(View.GONE);
                llQuestionInfo.setVisibility(View.VISIBLE);
                questionInfo.setText(item.getItemNote());
                questionInfoBt.setVisibility(View.GONE);
                colorSettingBt.setText("颜色设置");
            }else if(!TextUtils.isEmpty(item.getColor()) && !TextUtils.isEmpty(item.getItemNote())){
                llColorAndQuestion.setVisibility(View.GONE);
                llColorInfo.setVisibility(View.VISIBLE);
                llQuestionInfo.setVisibility(View.VISIBLE);
                colorInfo.setText(item.getColor());
                questionInfo.setText(item.getItemNote());
            }else {
                llColorAndQuestion.setVisibility(View.VISIBLE);
                llColorInfo.setVisibility(View.GONE);
                llQuestionInfo.setVisibility(View.GONE);
                colorSettingBt.setVisibility(View.VISIBLE);
                questionInfoBt.setVisibility(View.VISIBLE);
                colorSettingBt.setText("颜色设置");
                questionInfoBt.setText("问题描述");
            }
            questionInfoBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onQuestionInfo(position);
                    }
                }
            });

            colorSettingBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onColorSetting(position);
                    }
                }
            });

            colorEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editPositionListener != null){
                        editPositionListener.onEditColor(position);
                    }
                }
            });

            questionInfoEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editPositionListener != null){
                        editPositionListener.onEditQuestion(position);
                    }
                }
            });
        }
    }
}
