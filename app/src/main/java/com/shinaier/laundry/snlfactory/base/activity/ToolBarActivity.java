package com.shinaier.laundry.snlfactory.base.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;


/**
 * Created by 张家洛 on 2016/12/4.
 */

public class ToolBarActivity extends BaseActivity {
    private ViewGroup root;
    protected View mTitlebar;
    protected TextView mTitleLeft;
    protected ImageView mBtnTitleLeft;
    protected TextView toolbarTitleCenter;
    protected ImageView mBtnTitleRight;
    protected TextView rightText;
    private TextView mTxtLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_toolbar_frame);
        root = (ViewGroup) findViewById(R.id.frame_container);
        View.inflate(this, layoutResID, root);
        initToolBar();
    }

    private void initToolBar() {
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setTitleTextColor(this.getResources().getColor(R.color.white));
        mTitlebar = findViewById(R.id.base_titlebar);
        mTitleLeft = (TextView) findViewById(R.id.toolbar_left_title);
        mBtnTitleLeft = (ImageView) findViewById(R.id.left_button);
        mTxtLeft = (TextView) findViewById(R.id.left_text);
//        mBtnTitleLeft.setImageDrawable(this.getResources().getDrawable(R.drawable.btn_ic_back_bg));

        toolbarTitleCenter = (TextView) findViewById(R.id.toolbar_title);
        mBtnTitleRight = (ImageView) findViewById(R.id.right_button);
        rightText = (TextView) findViewById(R.id.rigth_text);

//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /*public void setToolbarNavigation(){
        mToolbar.setNavigationIcon(this.getResources().getDrawable(R.drawable.btn_ic_back_bg));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/

    public void setLeftTitle(String title) {
        mTitleLeft.setVisibility(View.VISIBLE);
        mTitleLeft.setText(title);
        toolbarTitleCenter.setVisibility(View.GONE);
        mBtnTitleLeft.setVisibility(View.VISIBLE);
        mBtnTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setCenterTitle(String title) {
        mTitleLeft.setVisibility(View.GONE);
        toolbarTitleCenter.setVisibility(View.VISIBLE);
        toolbarTitleCenter.setText(title);
    }

    public void setCenterTitleAndLeftText(String title) {
        mTitleLeft.setVisibility(View.GONE);
        mBtnTitleLeft.setVisibility(View.GONE);
        mTxtLeft.setVisibility(View.VISIBLE);
        mTxtLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarTitleCenter.setVisibility(View.VISIBLE);
        toolbarTitleCenter.setText(title);
    }
}
