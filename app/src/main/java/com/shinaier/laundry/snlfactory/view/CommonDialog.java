package com.shinaier.laundry.snlfactory.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;


/**
 * 公用封装的加载的显示框
 * Created by Leno_Zhang on 2016/10/9.
 */
public class CommonDialog extends Dialog {
    private Context mContext;
    private ImageView icon;
    private String contentString = "loading...";
    private TextView tvcontent;
    private Animation animation = null;

    public CommonDialog(Context context) {
        super(context, R.style.CommonDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.common_dialog);
        initWidget();
        //加载动画
        init();
    }

    private void initWidget() {
        this.icon = ((ImageView) findViewById(R.id.dialog_wait_icon));
        this.tvcontent = ((TextView) findViewById(R.id.dialog_wait_content));
    }

    private void init() {
        animation = AnimationUtils.loadAnimation(mContext, R.anim.common_dialog_loading);
    }

    private void setAnimation() {
        this.icon.startAnimation(this.animation);
    }

    public void setContent(String paramString) {
        this.contentString = paramString;
        if (this.tvcontent != null)
            this.tvcontent.setText(this.contentString);
    }

    public void show() {
        super.show();
        setAnimation();
        this.tvcontent.setText(this.contentString);
    }
}
