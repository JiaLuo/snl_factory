package com.shinaier.laundry.snlfactory.main.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.utils.DeviceUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.main.adapter.GuideAdapter;
import com.shinaier.laundry.snlfactory.util.ConfigUtils;
import com.werb.permissionschecker.PermissionChecker;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张家洛 on 2016/12/3.
 */

public class GuideActivity extends Activity {

    private List<View> views;
    private int currentPage;

    // 底部小点图片
    private ImageView[] dots;
    // 记录当前选中位置
    private int currentIndex;
    private LinearLayout dotContainer;
    private int[] imgIds;

    private String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE

    };
    private PermissionChecker permissionChecker;
    private float startX;
    private float endX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_act);
        permissionChecker = new PermissionChecker(GuideActivity.this);
        if(permissionChecker.isLackPermissions(PERMISSIONS)){
            permissionChecker.requestPermissions();
        }else {
            initViews();
        }
        // 初始化底部小点
//        initDots();
    }

    private void initViews() {
        ViewPager guideViewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        initData();
        GuideAdapter guideAdapter = new GuideAdapter(this,views);
        guideViewPager.setAdapter(guideAdapter);
        guideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                    views.get(2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfigUtils.setShowGuide(GuideActivity.this,true);
                            startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                        }
                    });
//                setCurrentDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        guideViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = event.getX();
                            break;
                        case MotionEvent.ACTION_UP:
                            endX = event.getX();
                            //获取屏幕的宽度
                            int width = DeviceUtil.getWidth(getApplicationContext());
                            //根据滑动的距离来切换界面
                            if (currentPage == 2 && startX - endX >= (width / 5)) {
                                ConfigUtils.setShowGuide(GuideActivity.this,true);
                                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                            }

                            break;
                    }
                    return false;
                }
            });
    }

    private void initData() {
        imgIds = new int[]{
                R.drawable.launcher_img_android_1,
                R.drawable.launcher_img_android_2,
                R.drawable.launcher_img_android_3,
        };
        views = new ArrayList<>();
        for (int i = 0; i < imgIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imgIds[i]);
            views.add(imageView);
        }
    }


//    private void initDots() {
//        dotContainer = (LinearLayout) findViewById(R.id.dot_container);
//        dots = new ImageView[imgIds.length];
//
//        // 循环取得小点图片
//        for (int i = 0; i < imgIds.length; i++) {
//            dots[i] = new ImageView(this);
//            dots[i].setLayoutParams(new ViewGroup.LayoutParams(22, 22));
//            dots[i].setId(i);
//            dots[i].setImageDrawable(this.getResources().getDrawable(R.drawable.guide_dot_icon));
//            dots[i].setPadding(0, 0, 0, 0);
//            dots[i].setEnabled(false);
//            //添加焦点图间的间隔
//            View view = new View(this);
//            view.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
//            dotContainer.addView(dots[i]);
//            dotContainer.addView(view);
//        }
//
//        currentIndex = 0;
//        dots[currentIndex].setImageDrawable(this.getResources().getDrawable(R.drawable.guide_dot_icon_selected));
//    }

//    private void setCurrentDot(int position) {
//        if (position < 0 || position > imgIds.length - 1 || currentIndex == position) {
//            return;
//        }
//
//        currentIndex = position;
//        // 循环取得小点图片
//        for (int i = 0; i < imgIds.length; i++) {
//            if (i == currentIndex) {
//                dots[i].setImageDrawable(this.getResources().getDrawable(R.drawable.guide_dot_icon_selected));
//            } else {
//                dots[i].setImageDrawable(this.getResources().getDrawable(R.drawable.guide_dot_icon));
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
//                    //获取屏幕的宽度
//                    int width = DeviceUtil.getWidth(getApplicationContext());
//                    //根据滑动的距离来切换界面
//                    if (currentPage == 2 && startX - endX >= (width / 5)) {
//                        ConfigUtils.setShowGuide(GuideActivity.this,true);
//                        startActivity(new Intent(GuideActivity.this, LoginActivity.class));
//                    }
                    initViews();
                } else {
                    permissionChecker.showDialog();
                }
                break;
        }
    }
}
