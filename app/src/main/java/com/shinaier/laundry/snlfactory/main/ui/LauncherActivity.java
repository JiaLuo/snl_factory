package com.shinaier.laundry.snlfactory.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.BaseActivity;
import com.shinaier.laundry.snlfactory.main.MainActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.util.ConfigUtils;


/**
 * Created by 张家洛 on 2017/2/8.
 */

public class LauncherActivity extends BaseActivity {
    public static final int APP_ON_FOREGROUND_EXTRAS = 0x2;
    private static final long LAUNCH_MIN_TIME = 2000L;
    private int extraFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extraFrom = getIntent().getIntExtra("extra_from", 0);
        if (extraFrom == APP_ON_FOREGROUND_EXTRAS){
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            intent.putExtra("extra",extraFrom);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.launcher_act);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(ConfigUtils.isShowGuide(LauncherActivity.this)){
                        if(UserCenter.isLogin(LauncherActivity.this)){
                            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                            intent.putExtra("extra_from", extraFrom);
                            startActivity(intent);
                            finish();
                        }else {
                            startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                            finish();
                        }
                    }else {
                        startActivity(new Intent(LauncherActivity.this,GuideActivity.class));
                        finish();
                    }
                }
            },LAUNCH_MIN_TIME);
        }
    }
}
