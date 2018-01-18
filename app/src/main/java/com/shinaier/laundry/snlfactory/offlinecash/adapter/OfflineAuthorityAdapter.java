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

import com.common.utils.ToastUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.OfflineAuthorityEntity;
import com.shinaier.laundry.snlfactory.util.CommonTools;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张家洛 on 2017/10/31.
 */

public class OfflineAuthorityAdapter extends RecyclerView.Adapter<OfflineAuthorityAdapter.MasonryView>{
    private List<OfflineAuthorityEntity.OfflineAuthorityResult.OfflineAuthorityMight> offlineAuthorityEntities;
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

    public OfflineAuthorityAdapter(Context context, List<OfflineAuthorityEntity.OfflineAuthorityResult.OfflineAuthorityMight> offlineAuthorityEntities) {
        this.offlineAuthorityEntities = offlineAuthorityEntities;
        this.context = context;
        heightList = new ArrayList<>();
        //遍历传进来的数据集合 给每个item写出高度
        for (int i = 0; i < offlineAuthorityEntities.size(); i++) {
            if (offlineAuthorityEntities.get(i).getModule().equals("1")){
                heightList.add(CommonTools.dp2px(context,(float)116.5));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("8")){
                heightList.add(CommonTools.dp2px(context,(float)97.5));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("2")){
                heightList.add(CommonTools.dp2px(context,(float)93));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("50")){
                heightList.add(CommonTools.dp2px(context,(float)120.5));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("51")){
                heightList.add(CommonTools.dp2px(context,(float)114));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("52")){
                heightList.add(CommonTools.dp2px(context,(float)109.5));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("3")){
                heightList.add(CommonTools.dp2px(context,(float)118.5));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("9")){
                heightList.add(CommonTools.dp2px(context,(float)102));
            } else if (offlineAuthorityEntities.get(i).getModule().equals("4")){
                heightList.add(CommonTools.dp2px(context,(float)101.5));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("5")){
                heightList.add(CommonTools.dp2px(context,(float)98));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("6")){
                heightList.add(CommonTools.dp2px(context,(float)97.5));
            }else if (offlineAuthorityEntities.get(i).getModule().equals("7")){
                heightList.add(CommonTools.dp2px(context,(float)97));
            }else {
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
    public void onBindViewHolder(final MasonryView masonryView, final int position) {
        masonryView.textView.setText(offlineAuthorityEntities.get(position).getModule_name());
        //设置每个item的高度
        ViewGroup.LayoutParams params = masonryView.llAuthority.getLayoutParams();
        params.height = heightList.get(position);
        masonryView.llAuthority.setLayoutParams(params);
        //给每个item设置背景颜色和图标
//        String s = products.get(position);
//        if (s.equals(""))
        switch (offlineAuthorityEntities.get(position).getModule()){
            case "1":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.collect_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4e94dc"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4d4e94dc"));
                }
                break;
            case "8":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.into_factory_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#dc974e"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4ddc974e"));
                }
                break;
            case "2":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.wash_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4fd3df"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4d4fd3df"));
                }

                break;
            case "50":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.drying));

                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#f74f3d"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4df74f3d"));
                }
                break;
            case "51":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.ironing_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#d791a3"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4dd791a3"));
                }
                break;
            case "52":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.quality_checking_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#e58d78"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4de58d78"));
                }
                break;
            case "3":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.hang_on_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4edc8d"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4d4edc8d"));
                }
                break;
            case "9":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.leave_factory_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#f65bf1"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4df65bf1"));
                }
                break;
            case "4":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.take_clothes_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#9577d1"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4d9577d1"));
                }
                break;
            case "5":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.business_count_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#33bbdd"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4d33bbdd"));
                }
                break;
            case "6":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.member_manager_bg));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#fa6a56"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4dfa6a56"));
                }
                break;
            case "7":
                masonryView.ivAuthorityBg.setBackground(context.getResources().getDrawable(R.drawable.reflux_to_examine));
                if (offlineAuthorityEntities.get(position).getState().equals("1")){
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#eb446f"));
                }else {
                    masonryView.llAuthority.setBackgroundColor(Color.parseColor("#4deb446f"));
                }
                break;
        }

        //设置单击事件
        if(mOnItemClickListener !=null){
            masonryView.llAuthority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里是为textView设置了单击事件,回调出去
                    //mOnItemClickListener.onItemClick(v,position);这里需要获取布局中的position,不然乱序
                    if (offlineAuthorityEntities.get(position).getState().equals("1")){
                        mOnItemClickListener.onItemClick(masonryView.textView,masonryView.getLayoutPosition());
                    }else {
                        ToastUtil.shortShow(context,"权限限制");
                    }
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
