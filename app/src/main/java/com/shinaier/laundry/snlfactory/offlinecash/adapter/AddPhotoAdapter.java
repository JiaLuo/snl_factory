package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.werb.pickphotoview.util.PickConfig;
import com.werb.pickphotoview.util.PickUtils;

import java.util.List;


/**
 * 添加图片的adapter
 * Created by 张家洛 on 2017/3/10.
 */

public class AddPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AddPhotoListener listener;
    private Context context;
    private List<String> photos;
    private SimpleDraweeView ivClothesImg;

    public interface AddPhotoListener{
        void onAddPhoto();
    }
    public void setAddPhotoListener(AddPhotoListener listener){
        this.listener = listener;
    }

    public AddPhotoAdapter(Context context, List<String> photos){
        this.context = context;
        this.photos = photos;
    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.check_clothes_img_item, null);
        GridImageViewHolder holder = new GridImageViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(photos != null){
            if (photos.size() == 0){
                ivClothesImg.setImageResource(R.drawable.add_photo);
            }else if(photos.size() >= 13){
                if (position < 12){
                    GridImageViewHolder gridImageViewHolder = (GridImageViewHolder) holder;
                    gridImageViewHolder.bindItem(photos.get(position));
                }else if(position == 3){
                    ivClothesImg.setImageResource(R.drawable.edit_photo);
                }
            } else{
                if (position < photos.size()){
                    GridImageViewHolder gridImageViewHolder = (GridImageViewHolder) holder;
                    gridImageViewHolder.bindItem(photos.get(position));
                }else if(position == photos.size()){
                    ivClothesImg.setImageResource(R.drawable.add_photo);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if(photos != null){
            if(photos.size() < 13){
                return photos.size() + 1;
            }else {
                return 13;
            }
        }else {
            return photos.size() + 1;
        }

    }

    public class GridImageViewHolder extends RecyclerView.ViewHolder{
        private int scaleSize;

        public GridImageViewHolder(View itemView) {
            super(itemView);
            ivClothesImg = itemView.findViewById(R.id.iv_clothes_img);

            int screenWidth = PickUtils.getInstance(context).getWidthPixels();
            int space = PickUtils.getInstance(context).dp2px(PickConfig.ITEM_SPACE);
            scaleSize = (screenWidth - (4 + 1) * space) / 4;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivClothesImg.getLayoutParams();
            params.width = scaleSize;
            params.height = scaleSize;

            ivClothesImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onAddPhoto();
                    }
                }
            });
        }

        void bindItem(final String path) {
                Glide.with(context).load(Uri.parse("file://" + path)).
                        diskCacheStrategy(DiskCacheStrategy.NONE).thumbnail(0.3f).
                        dontAnimate().into(ivClothesImg);
        }
    }
}
