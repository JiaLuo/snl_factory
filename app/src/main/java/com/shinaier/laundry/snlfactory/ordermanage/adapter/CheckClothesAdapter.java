package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.CheckClothesEntities;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/2.
 */

public class CheckClothesAdapter extends BaseAdapterNew<CheckClothesEntities.CheckClothesResult> {
    private Context context;
    private EditPositionListener editPositionListener;
    private AddPhotoListener addPhotoListener;
    private EditPhotoListener editPhotoListener;
    private ShowBigPhotoListener showBigPhotoListener;

    public interface ShowBigPhotoListener{
        void onShowBigPhoto(int position, int innerPosition);
    }

    public interface EditPhotoListener{
        void onEditPhotoClick(int position);
    }

    public interface AddPhotoListener{
        void onAddPhotoClick(int position);
    }

    public void setShowBigPhotoListener(ShowBigPhotoListener showBigPhotoListener){
        this.showBigPhotoListener = showBigPhotoListener;
    }

    public void setEditPhotoListener(EditPhotoListener editPhotoListener){
        this.editPhotoListener = editPhotoListener;
    }

    public void setAddPhotoListener(AddPhotoListener addPhotoListener){
        this.addPhotoListener = addPhotoListener;
    }

    public interface EditPositionListener{
        void onEditQuestion(int position);
        void onEditColor(int position);
        void onEditAssess(int position);

    }
    public void setEditPositionListener(EditPositionListener editPositionListener){
        this.editPositionListener = editPositionListener;
    }

    public CheckClothesAdapter(Context context, List<CheckClothesEntities.CheckClothesResult> mDatas) {
        super(context, mDatas);
        this.context = context;
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.check_clothes_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        CheckClothesEntities.CheckClothesResult item = getItem(position);
        TextView checkClothesName = ViewHolder.get(convertView,R.id.check_clothes_name);
        WrapHeightGridView checkClothesImg = ViewHolder.get(convertView,R.id.check_clothes_img);
        TextView colorInfo = ViewHolder.get(convertView,R.id.color_info);
        TextView colorEdit = ViewHolder.get(convertView,R.id.color_edit);
        TextView questionInfo = ViewHolder.get(convertView,R.id.question_info);
        TextView questionInfoEdit = ViewHolder.get(convertView,R.id.question_info_edit);
        TextView cleanedAssessmentInfo = ViewHolder.get(convertView,R.id.cleaned_assessment_info);
        TextView cleanedAssessmentInfoEdit = ViewHolder.get(convertView,R.id.cleaned_assessment_info_edit);

        if(item != null) {
            checkClothesName.setText(item.getItemName());
//            checkClothesNum.setText("数量：x" + item.getNumber());
            final List<String> imgs = item.getItemImages();
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


            colorInfo.setText(item.getColor());
            questionInfo.setText(item.getProblem());
            cleanedAssessmentInfo.setText(item.getForecast());

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

            cleanedAssessmentInfoEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editPositionListener != null){
                        editPositionListener.onEditAssess(position);
                    }
                }
            });
        }
    }
}
