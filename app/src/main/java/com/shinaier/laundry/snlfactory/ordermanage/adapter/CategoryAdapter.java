package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.StoreDetailEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/12/30.
 */

public class CategoryAdapter extends RecyclerView.Adapter {

    private int checked;
    private List<StoreDetailEntity.StoreDetailResult> data;
    public boolean fromClick;
    private String typeStr;
    private HashMap<String, Long> badges = new HashMap<>();
    private Context context;
    private RecyclerView recyclerView;

    private OnMyItemClickListener itemClickListener;

    //点击事件接口
    public interface OnMyItemClickListener{
        void onMyItemClick(StoreDetailEntity.StoreDetailResult result, int position);
    }
    //设置点击事件的方法
    public void setMyItemClickListener(OnMyItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    public CategoryAdapter(List<StoreDetailEntity.StoreDetailResult> data, RecyclerView recyclerView, Context context) {
        this.data = data;
        if (data != null && data.size() > 0) {
            typeStr = data.get(0).getCate_name();
        }
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void updateBadge(HashMap<String, Long> badges) {
        this.badges = badges;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewCategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewCategoryHolder myViewCategoryHolder = (MyViewCategoryHolder) holder;
        myViewCategoryHolder.tv_name.setText(data.get(position).getCate_name());
//        helper.setText(R.id.tv_name, item.getCate_name())
//                .setTag(R.id.item_main, item.getCate_name());
        if (myViewCategoryHolder.getAdapterPosition() == checked) {
            myViewCategoryHolder.item_main.setBackgroundColor(Color.WHITE);
            myViewCategoryHolder.tv_name.setTextColor( Color.BLACK);
            myViewCategoryHolder.tv_name.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            myViewCategoryHolder.item_main.setBackgroundColor(ContextCompat.getColor(context, R.color.type_gray));
            myViewCategoryHolder.tv_name.setTextColor(ContextCompat.getColor(context, R.color.type_normal));
            myViewCategoryHolder.tv_name.setTypeface(Typeface.DEFAULT);
        }

        if (badges.containsKey(data.get(position).getCate_name()) && badges.get(data.get(position).getCate_name()) > 0) {
            myViewCategoryHolder.item_badge.setVisibility(View.VISIBLE);
            myViewCategoryHolder.item_badge.setText(String.valueOf(badges.get(data.get(position).getCate_name())));
        } else {
            myViewCategoryHolder.item_badge.setVisibility(View.GONE);
        }
//        myViewCategoryHolder.item_main.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                itemClickListener.onMyItemClick(data.get(position), position);
//                return false;
//            }
//        });
        myViewCategoryHolder.item_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClickListener!=null)
                    itemClickListener.onMyItemClick(data.get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewCategoryHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,item_badge;
        private FrameLayout item_main;

        public MyViewCategoryHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            item_main = (FrameLayout) itemView.findViewById(R.id.item_main);
            item_badge = (TextView) itemView.findViewById(R.id.item_badge);
        }
    }


//    public void setChecked(int checked) {
//        this.checked = checked;
//        typeStr = data.get(checked).getCate_name();
//        notifyDataSetChanged();
//    }
//
//    public void setType(String type) {
//        if (fromClick) {
//            fromClick = !type.equals(typeStr);
//            return;
//        }
//        if (type.equals(typeStr)) {
//            return;
//        }
//        for (int i = 0; i < data.size(); i++) {
//            if (data.get(i).getCate_name().equals(type) && i != checked) {
//                setChecked(i);
//                moveToPosition(i);
//                break;
//            }
//        }
//    }

    public void moveToPosition(int i) {
        checked = i;
        LinearLayoutManager inlm = (LinearLayoutManager)recyclerView.getLayoutManager();
        inlm.scrollToPositionWithOffset(i,0);
        notifyDataSetChanged();
//        LinearLayoutManager linlm = (LinearLayoutManager) recyclerView.getLayoutManager();
//        int firstItem = linlm.findFirstVisibleItemPosition();
//        int lastItem = linlm.findLastVisibleItemPosition();
//        if (getItemCount() > 5) {//提前把9滑出来
//            lastItem -= 3;
//        }
//        if (i <= firstItem) {
//            recyclerView.scrollToPosition(i);
//        } else if (i <= lastItem) {
//            //当要置顶的项已经在屏幕上显示时不处理
//        } else {
//            //当要置顶的项在当前显示的最后一项的后面时
//            recyclerView.scrollToPosition(i);
//            int n = i - linlm.findFirstVisibleItemPosition();
//            if (0 <= n && n < recyclerView.getChildCount()) {
//                int top = recyclerView.getChildAt(n).getTop();
//                recyclerView.smoothScrollBy(0, top);
//            }
//        }
    }
}
