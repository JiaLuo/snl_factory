package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineAuthorityEntity;
import com.shinaier.laundry.snlfactory.util.CommonTools;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张家洛 on 2017/10/31.
 */

public class OfflineAuthorityAdapter extends RecyclerView.Adapter<OfflineAuthorityAdapter.MasonryView>{
    private List<OfflineAuthorityEntity.OfflineAuthorityDatas.OfflineAuthorities> offlineAuthorityEntities;
    private List<Integer> heightList;//每个item高度的集合
    private Context context;
    private OnRecyclerItemClickListener mOnItemClickListener;//单击事件
    /**
     * 处理item的点击事件,因为recycler没有提供单击事件,所以只能自己写了
     */
    public interface OnRecyclerItemClickListener {
         void onItemClick(TextView textView, int position);
    }

    /**
     * 暴露给外面的设置单击事件
     */
    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public OfflineAuthorityAdapter(Context context, List<OfflineAuthorityEntity.OfflineAuthorityDatas.OfflineAuthorities> offlineAuthorityEntities) {
        this.offlineAuthorityEntities = offlineAuthorityEntities;
        this.context = context;
        heightList = new ArrayList<>();
        //遍历传进来的数据集合 给每个item写出高度
        for (int i = 0; i < offlineAuthorityEntities.size(); i++) {
            if (offlineAuthorityEntities.get(i).getValue().equals("1")){
                heightList.add(CommonTools.dp2px(context,(float)111.5));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("2")){
                heightList.add(CommonTools.dp2px(context,(float)102.5));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("3")){
                heightList.add(CommonTools.dp2px(context,(float)120));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("4")){
                heightList.add(CommonTools.dp2px(context,(float)105.5));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("5")){
                heightList.add(CommonTools.dp2px(context,(float)119));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("6")){
                heightList.add(CommonTools.dp2px(context,(float)104.5));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("7")){
                heightList.add(CommonTools.dp2px(context,(float)103.5));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("8")){
                heightList.add(CommonTools.dp2px(context,(float)97));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("9")){
                heightList.add(CommonTools.dp2px(context,(float)96.5));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("10")){
                heightList.add(CommonTools.dp2px(context,(float)93));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("11")){
                heightList.add(CommonTools.dp2px(context,(float)116.5));
            }else if (offlineAuthorityEntities.get(i).getValue().equals("12")){
                heightList.add(CommonTools.dp2px(context,(float)112.5));
            } else {
                heightList.add(CommonTools.dp2px(context,(float)0));
            }
        }
//        for (int i = 0; i < offlineAuthorityEntities.size(); i++) {
//            int height = new Random().nextInt(CommonTools.dp2px(context,(float) 20.5)) + CommonTools.dp2px(context,(float) 112);
//            heightList.add(height);
//        }
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offline_authority_item, viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(final MasonryView masonryView, int position) {
        masonryView.textView.setText(offlineAuthorityEntities.get(position).getName());
        //设置每个item的高度
        ViewGroup.LayoutParams params = masonryView.llAuthority.getLayoutParams();
        params.height = heightList.get(position);
        masonryView.llAuthority.setLayoutParams(params);
        //给每个item设置背景颜色和图标
//        String s = products.get(position);
//        if (s.equals(""))

        if (offlineAuthorityEntities.get(position).getValue().equals("1")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.collect_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4e94dc"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("2")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.laundry_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#dc974e"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("3")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.laundry_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#f74f3d"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("4")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.laundry_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4fd3df"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("5")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.ironing_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#d791a3"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("6")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.hang_on_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#e58d78"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("7")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.hang_on_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4edc8d"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("8")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.hang_on_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#f65bf1"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("9")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.take_clothes_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#9577d1"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("10")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.business_count_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#33bbdd"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("11")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.member_manager_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#fa6a56"));
        }else if (offlineAuthorityEntities.get(position).getValue().equals("12")){
            masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.member_manager_bg));
            masonryView.llAuthority.setBackgroundColor(Color.parseColor("#eb446f"));
        }

        //设置单击事件
        if(mOnItemClickListener !=null){
            masonryView.llAuthority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里是为textView设置了单击事件,回调出去
                    //mOnItemClickListener.onItemClick(v,position);这里需要获取布局中的position,不然乱序
                    mOnItemClickListener.onItemClick(masonryView.textView,masonryView.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return offlineAuthorityEntities.size();
    }

    public static class MasonryView extends  RecyclerView.ViewHolder{

        TextView textView;
        LinearLayout llAuthority;
        ImageView ivAuthorityBg;

        public MasonryView(View itemView){
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.authority_name);
            llAuthority = (LinearLayout) itemView.findViewById(R.id.ll_authority);
            ivAuthorityBg = (ImageView) itemView.findViewById(R.id.iv_authority_bg);
        }

    }

}
