package com.shinaier.laundry.snlfactory.view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.common.utils.DeviceUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.TakeTimeEntity;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.LeftDistanceAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.RightAdapter;
import com.shinaier.laundry.snlfactory.util.CommonTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacktian on 15/9/6.
 */
public class CheckDistanceSpinnerView {

    LayoutInflater inflater = null;
    View view1 = null;	//选择区域的view
    //第一组
    ListView listView1 = null;
    ListView listView2= null;
    LeftDistanceAdapter leftAdapter = null;
    RightAdapter rightAdapter = null;
    private OnSpinnerItemClickListener mOnSpinnerItemClickListener;
    private PoupWindowListener mPoupWindowListener;
    public static int screen_w = 0;
    public static int screen_h = 0;

    private List<String> rightList = new ArrayList<>();


    PopupWindow mPopupWindow = null;
    private Context mContext;
    private String dayString;

    public CheckDistanceSpinnerView(){

    }

    public CheckDistanceSpinnerView(Activity activity){
        mContext = activity;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
        screen_w = dm.widthPixels;
        screen_h = dm.heightPixels - DeviceUtil.dp_to_px(activity, 118);
    }
    public CheckDistanceSpinnerView(Activity activity, OnSpinnerItemClickListener listener){
        mContext = activity;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
        screen_w = dm.widthPixels;
        screen_h = dm.heightPixels;
        mOnSpinnerItemClickListener = listener;
    }

    public void addMenuShowListener(PoupWindowListener poupWindowListener){
        mPoupWindowListener = poupWindowListener;
    }
    /**
     * 初始化 PopupWindow
     * @param view
     */
    private void initPopuWindow(View view) {
		/* 第一个参数弹出显示view 后两个是窗口大小 */
        mPopupWindow = new PopupWindow(view, screen_w, screen_h);
		/* 设置背景显示 */
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.buy_pop_bg));
//        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.cell_white_bg));
		/* 设置触摸外面时消失 */
        mPopupWindow.setOutsideTouchable(true);


        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
		/* 设置点击menu以外其他地方以及返回键退出 */
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mPoupWindowListener != null){
                    mPoupWindowListener.poupWindowDismiss(false);
                }
            }
        });
        /**
         * 1.解决再次点击MENU键无反应问题 2.sub_view是PopupWindow的子View
         */
        view.setFocusableInTouchMode(true);
    }

    private String dayTime = "";
    private String durTime = "";
    private int dayPosition = 0;
    private int durPosition = 0;



    /**
     * 展示区域选择的对话框
     */
    public void showSpinnerPop(View view, Animation animation, final List<TakeTimeEntity> leftList, String editTime) {

        if(mPoupWindowListener != null){
            mPoupWindowListener.poupWindowDismiss(true);
        }

//        if (editTime != null){
            try{
                String[] split = editTime.split(" ");
                dayTime = split[0].trim();
                durTime = split[1].trim();
            }catch (Exception e){
                e.printStackTrace();
            }
//        }

        dayPosition = 0;
        durPosition = 0;

        for (int i = 0; i < leftList.size(); i++) {
            if (leftList.get(i).getDate().contains(dayTime)){
                dayPosition = i;
            }
        }

        for (int i = 0; i< leftList.get(dayPosition).getTimes().size(); i++){
            if ( leftList.get(dayPosition).getTimes().get(i).contains(durTime)){
                durPosition = i;
            }
        }

        if(mPopupWindow == null){
            view1 = inflater.from(mContext).inflate(R.layout.buy_distance_spinner_view_double, null);
            view1.findViewById(R.id.rllll).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                }
            });
            initPopuWindow(view1);
            listView1= (ListView) view1.findViewById(R.id.listView1);
            listView2= (ListView) view1.findViewById(R.id.listView2);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView1.getLayoutParams();
        params.height = CommonTools.dp2px(mContext,353);
        listView1.setLayoutParams(params);
        listView2.setLayoutParams(params);
        leftAdapter = new LeftDistanceAdapter(mContext, leftList);

        listView1.setAdapter(leftAdapter);
        leftAdapter.setSelectedPosition(dayPosition);

        rightList.clear();
        rightList.addAll(leftList.get(dayPosition).getTimes());
        rightAdapter = new RightAdapter(mContext, rightList);
        listView2.setAdapter(rightAdapter);
        rightAdapter.setSelectedPosition(durPosition);

        mPopupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0 );


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                dayString = leftList.get(position).getDate();
                rightList.clear();
                rightList.addAll(leftList.get(position).getTimes());
                rightAdapter.notifyDataSetChanged();
                rightAdapter.setSelectedPosition(0);
//
                leftAdapter.setSelectedPosition(position);
                leftAdapter.notifyDataSetChanged();

            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private String getTime;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dayString == null){
                    dayString = leftList.get(dayPosition).getDate();
                }
                getTime = dayString + " " + rightList.get(position);

                if (mOnSpinnerItemClickListener != null) {
                    mOnSpinnerItemClickListener.onItemClickListener2(getTime);
                }
                mPopupWindow.dismiss();
            }
        });
    }

    public void close(){
        mPopupWindow.dismiss();
    }
    public interface PoupWindowListener{
        void poupWindowDismiss(boolean isShow);
    }
    public interface OnSpinnerItemClickListener{
        public void onItemClickListener1(AdapterView<?> parent, View view, int position, long id);
        public void onItemClickListener2(String time);
    }

}
