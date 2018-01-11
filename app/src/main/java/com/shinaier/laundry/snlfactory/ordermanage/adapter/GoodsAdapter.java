package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.ordermanage.entities.GoodsBean;
import com.shinaier.laundry.snlfactory.ordermanage.view.AddWidget;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/12/30.
 */

public class GoodsAdapter extends RecyclerView.Adapter {

    /**
     * 商品标题
     */
    public static final int GOODS_HEADER = 0xff01;
    /**
     * 商品
     */
    public static final int GOODS_INFO = 0xff02;

    /**
     * footer
     */
    public static final int FOOTER = 0xff03;

    private List<GoodsBean> data;
    private AddWidget.OnAddClick onAddClick;
    private Context context;
    private HashMap<String, Long> badges = new HashMap<>();

    private OnGoodsItemClickListener itemClickListener;

    //点击事件接口
    public interface OnGoodsItemClickListener {
        void onGoodsItemClick(View view, AddWidget.OnAddClick onAddClick, GoodsBean goodsBean, int position);
    }

    //设置点击事件的方法
    public void setGoodsItemClickListener(OnGoodsItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public GoodsAdapter(Context context, List<GoodsBean> data, AddWidget.OnAddClick onAddClick) {
        this.context = context;
        this.data = data;
        this.onAddClick = onAddClick;
    }

    public GoodsBean getItem(int position) {
        return data.get(position);
    }

    public void updateBadge(HashMap<String, Long> badges) {
        this.badges = badges;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getType() == 0) {
            return GOODS_HEADER;
        } else if (data.get(position).getType() == 1) {
            return GOODS_INFO;
        } else {
            return FOOTER;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == GOODS_HEADER) {
            return new MyViewGoodsHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_view_header, parent, false));
        } else if (viewType == GOODS_INFO) {
            return new MyViewGoodsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false));
        } else {
            return new MyViewGoodsFooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == GOODS_HEADER) {
            MyViewGoodsHeaderHolder myViewGoodsHeaderHolder = (MyViewGoodsHeaderHolder) holder;
            myViewGoodsHeaderHolder.view_header_title.setText(data.get(position).getTypeName());
            myViewGoodsHeaderHolder.view_header_title_en.setText(data.get(position).getTypeNameEn());
        } else if (getItemViewType(position) == GOODS_INFO) {
            MyViewGoodsHolder myViewGoodsHolder = (MyViewGoodsHolder) holder;
            myViewGoodsHolder.tv_name.setText(data.get(position).getItem_name());
            myViewGoodsHolder.iv_food.setImageURI(Uri.parse(data.get(position).getImage()));
            myViewGoodsHolder.tv_price.setText(data.get(position).getStrPrice(context,data.get(position).getItem_price()));
            if (data.get(position).getItem_discount().equals("10.00")){
                myViewGoodsHolder.item_clothes_discount.setVisibility(View.GONE);
            }else {
                myViewGoodsHolder.item_clothes_discount.setVisibility(View.VISIBLE);
                myViewGoodsHolder.item_clothes_discount.setText(data.get(position).getItem_discount());
            }
            if (badges.containsKey(data.get(position).getItem_name()) && badges.get(data.get(position).getItem_name()) > 0) {
                myViewGoodsHolder.item_clothes_num.setVisibility(View.VISIBLE);
                myViewGoodsHolder.item_clothes_num.setText(String.valueOf(badges.get(data.get(position).getItem_name())));
            } else {
                myViewGoodsHolder.item_clothes_num.setVisibility(View.GONE);
            }

//            myViewGoodsHolder.item_clothes_num.setText(data.get(position).getSelectCount() + "");
            myViewGoodsHolder.addwidget.setData(onAddClick, data.get(position));
            myViewGoodsHolder.goods_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onGoodsItemClick(view,onAddClick,data.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewGoodsHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price, tv_sale, tv_header,item_clothes_discount,item_clothes_num;
        private LinearLayout goods_main;
        private AddWidget addwidget;
        private SimpleDraweeView iv_food;

        public MyViewGoodsHolder(View itemView) {
            super(itemView);
            iv_food = (SimpleDraweeView) itemView.findViewById(R.id.iv_food);
            item_clothes_discount = (TextView) itemView.findViewById(R.id.item_clothes_discount);
            item_clothes_num = (TextView) itemView.findViewById(R.id.item_clothes_num);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_sale = (TextView) itemView.findViewById(R.id.tv_sale);
            goods_main = (LinearLayout) itemView.findViewById(R.id.food_main);
            tv_header = (TextView) itemView.findViewById(R.id.tv_header);
            addwidget = (AddWidget) itemView.findViewById(R.id.addwidget);
        }
    }

    class MyViewGoodsHeaderHolder extends RecyclerView.ViewHolder {
        private TextView view_header_title,view_header_title_en;

        public MyViewGoodsHeaderHolder(View itemView) {
            super(itemView);
            view_header_title = (TextView) itemView.findViewById(R.id.view_header_title);
            view_header_title_en = (TextView)itemView.findViewById(R.id.view_header_title_en);
        }
    }

    class MyViewGoodsFooterHolder extends RecyclerView.ViewHolder {

        public MyViewGoodsFooterHolder(View itemView) {
            super(itemView);
        }
    }

}
