package com.shinaier.laundry.snlfactory.setting.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.BaseAdapterNew;
import com.common.adapter.ViewHolder;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.setting.entity.StoreModular;

import java.util.List;



/**
 * Created by 张家洛 on 2017/12/13.
 */

public class EditStoreModularAdapter extends BaseAdapterNew<StoreModular> {
    public interface SelectModularListener{
        void onClick(int position);
    }

    private SelectModularListener listener;
    public void setSelectModularListener(SelectModularListener listener){
        this.listener = listener;
    }
    public EditStoreModularAdapter(Context context, List<StoreModular> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected int getResourceId(int Position) {
        return R.layout.edit_store_modular_item;
    }

    @Override
    protected void setViewData(View convertView, final int position) {
        StoreModular item = getItem(position);
        TextView storeModularTxt = ViewHolder.get(convertView,R.id.store_modular_txt);
        ImageView ivStoreModular = ViewHolder.get(convertView,R.id.iv_store_modular);
        if (item != null){
            storeModularTxt.setText(item.getModuleName());
            if (item.isSelect == 0){
                ivStoreModular.setSelected(true);
            }else {
                ivStoreModular.setSelected(false);
            }
        }


        ivStoreModular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });

    }
}
