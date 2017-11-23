package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.Constants;

import java.util.List;


/**
 * Created by 张家洛 on 2017/3/13.
 */

public class EditPhotoAdapter extends BaseAdapterNew<String> {
    private DeletePhotoListener listener;
    public interface DeletePhotoListener{
        void onDelete(int position);
    }

    public void setDeletePhotoListener(DeletePhotoListener listener){
        this.listener = listener;
    }

    public EditPhotoAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.edit_photo_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        String item = getItem(position);
        SimpleDraweeView editClothesImg = ViewHolder.get(convertView,R.id.edit_clothes_img);
        ImageView editPhotoDelete = ViewHolder.get(convertView,R.id.edit_photo_delete);

        if(item != null){
            String imgPath = Constants.Urls.URL_BASE_DOMAIN + item;
            editClothesImg.setImageURI(Uri.parse(imgPath));
            editPhotoDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onDelete(position);
                    }
                }
            });
        }
    }
}
