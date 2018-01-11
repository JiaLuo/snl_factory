package com.shinaier.laundry.snlfactory.ordermanage.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.view.FlowLayout;

import java.util.List;


/**
 * 打赏popupwindow
 * Created by 张家洛 on 2017/5/11.
 */

public class AwardAuthorPop extends PopupWindow {
    private Context mContext;
    private View view;
    private ImageView close_view;
    private Handler handler;
    private List<String> remarkContent;

    public AwardAuthorPop(Context mContext, List<String> remarkContent, Handler handler){
        this.mContext = mContext;
        this.remarkContent = remarkContent;
        this.handler = handler;
         /* 设置弹出窗口特征 */

        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
//        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        // 设置弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);
    }

    public void setView(){
        view = LayoutInflater.from(mContext).inflate(R.layout.edit_price_remark_content, null);
        // 设置视图
        setContentView(view);
        FlowLayout flRemarkContent = (FlowLayout) view.findViewById(R.id.fl_remark_content);
        close_view = (ImageView) view.findViewById(R.id.close_view);

        if (remarkContent != null && remarkContent.size() > 0){
            for (int i = 0; i < remarkContent.size(); i++){
                final TextView textView = new TextView(mContext);
                textView.setBackgroundResource(R.drawable.login);
                textView.setText(remarkContent.get(i));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setGravity(Gravity.CENTER);
                flRemarkContent.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = new Message();
                        msg.obj = textView.getText();
                        msg.what = 1;
                        handler.sendMessage(msg);
                        dismiss();

                    }

                });
            }
            int padding = 24;
            flRemarkContent.setPadding(padding,padding,padding,padding);
        }

        // 取消按钮
        close_view.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.ll_popupwindow).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
