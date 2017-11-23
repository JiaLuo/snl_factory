package com.shinaier.laundry.snlfactory.main;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.utils.DeviceUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.TabsActivity;
import com.shinaier.laundry.snlfactory.download.DownloadApk;
import com.shinaier.laundry.snlfactory.manage.ui.ManageFragment;
import com.shinaier.laundry.snlfactory.offlinecash.ui.OfflineCashFragment;
import com.shinaier.laundry.snlfactory.ordermanage.ui.OrderManageFragment;
import com.shinaier.laundry.snlfactory.receiver.MyReceiver;
import com.shinaier.laundry.snlfactory.service.JobSchedulerService;
import com.shinaier.laundry.snlfactory.setting.ui.SettingsManageFragment;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.view.CommonDialog;
import com.werb.permissionschecker.PermissionChecker;



public class MainActivity extends TabsActivity {
    private String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,

    };
    private CommonDialog dialog;
    private PermissionChecker permissionChecker;
    private CollectClothesDialog notificationDialog;
    public static final long DIFF_DEFAULT_BACK_TIME = 2000;
    private long mBackTime = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //初始化操作
        initialization();
    }

    private void initialization() {
        int extraFrom = getIntent().getIntExtra("extra_from", 0);
        //1.注册下载广播接收器
        DownloadApk.registerBroadcast(this);
        //2.删除已存在的Apk
        DownloadApk.removeFile(this);
        dialog = new CommonDialog(this);
        permissionChecker = new PermissionChecker(this);
        if(permissionChecker.isLackPermissions(PERMISSIONS)){
            permissionChecker.requestPermissions();
        }else {
            if (extraFrom == MyReceiver.FROM_JPUSH){
                mTabHost.setCurrentTab(0); //用户从状态栏点进入订单处理
            }else {
                mTabHost.setCurrentTab(2);//正常进入
            }
        }
        //保活进程
        JobScheduler jobScheduler = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), JobSchedulerService.class.getName()))
                    .setPeriodic(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            jobScheduler.schedule(jobInfo);
        }

        //如果通知权限没打开，用户手动打开。
        if (!DeviceUtil.isNotificationEnabled(this)){
            View view = View.inflate(this, R.layout.notification_bar_view,null);
            TextView onNotificationBar = (TextView) view.findViewById(R.id.on_notification_bar);
            TextView cancelView = (TextView) view.findViewById(R.id.cancel_view);
            notificationDialog = new CollectClothesDialog(this, R.style.DialogTheme,view);
            notificationDialog.show();
            onNotificationBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 9) {
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        localIntent.setAction(Intent.ACTION_VIEW);
                        localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
                        localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                    }
                    startActivity(localIntent);
                    notificationDialog.dismiss();
                }
            });

            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notificationDialog.dismiss();
                }
            });
        }
    }

    @Override
    protected void addTabs() {
        addTab(initTabView(R.drawable.navigation_order_manage_selector, R.string.order_manage),
                OrderManageFragment.class, null);
        addTab(initTabView(R.drawable.navigation_order_inquiry_selector, R.string.offline_cash),
                OfflineCashFragment.class, null);
        if(!UserCenter.getRole(this).equals("2")){
            addTab(initTabView(R.drawable.navigation_manage_selector, R.string.manage),
                    ManageFragment.class, null);
        }
        addTab(initTabView(R.drawable.navigation_settings_selector, R.string.settings),
                SettingsManageFragment.class, null);
    }

    private View initTabView(int tabIcon, int tabText) {
        ViewGroup tab = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.main_tab_item, null);
        ImageView imageView = (ImageView) tab.findViewById(R.id.navigation);
        imageView.setImageResource(tabIcon);

        TextView textView = (TextView) tab.findViewById(R.id.txt_navigation);
        textView.setText(tabText);

        return tab;
    }

    @Override
    public void onBackPressed() {
        long nowTime = System.currentTimeMillis();
        long diff = nowTime - mBackTime;
        if (diff >= DIFF_DEFAULT_BACK_TIME) {
            mBackTime = nowTime;
            Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

}
