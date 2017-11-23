package com.shinaier.laundry.snlfactory.setting.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.utils.VersionInfoUtils;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.BaseFragment;
import com.shinaier.laundry.snlfactory.download.DownloadApk;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.main.ui.LoginActivity;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.SettingsEntity;
import com.shinaier.laundry.snlfactory.network.entity.UpdataEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.setting.view.UpdateDialog;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.util.IdentityHashMap;

/**
 * Created by 张家洛 on 2017/2/7.
 */

public class SettingsManageFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_SETTINGS = 0x1;
    private static final int REQUEST_CODE_UPDATE_VERSION = 0x2;

    private View view;
    private Context context;
    private TextView storeName;
    private TextView storePhoneNumber;
    private TextView storeCardNum;
    private TextView areaManageName;
    private TextView customerServicePhoneNum;
    private ImageView ivStoreImg;
    private SettingsEntity settingsEntity;
    private RelativeLayout rlCustomerService;
    private RelativeLayout rlUpdateVersion;
    private String updateUrl;
    private UpdateDialog updateDialog;
    private CommonDialog dialog;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    dialog.setContent("下载中");
                    dialog.show();
                    DownloadApk.downloadApk(context, updateUrl,
                            "软件更新", "诗奈尔商户端");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("设置");
        view = inflater.inflate(R.layout.settings_frag, null);
        context = getActivity();
        loadData();
        initView();
        return view;
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        requestHttpData(Constants.Urls.URL_POST_SETTINGS,REQUEST_CODE_SETTINGS, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        ivStoreImg =  view.findViewById(R.id.iv_store_img);
        storeName =  view.findViewById(R.id.store_name);
        storePhoneNumber =  view.findViewById(R.id.store_phone_number);
        storeCardNum =  view.findViewById(R.id.store_card_num);
        areaManageName =  view.findViewById(R.id.area_manage_name);
        customerServicePhoneNum =  view.findViewById(R.id.customer_service_phone_num);
        RelativeLayout rlFeedback =  view.findViewById(R.id.rl_feedback);
        RelativeLayout rlRevisePhone =  view.findViewById(R.id.rl_revise_phone);
        rlCustomerService =  view.findViewById(R.id.rl_customer_service);
        TextView quitLoginBt =  view.findViewById(R.id.quit_login_bt);
        rlUpdateVersion =  view.findViewById(R.id.rl_update_version);
        RelativeLayout rlStoreInfo =  view.findViewById(R.id.rl_store_info);
        RelativeLayout rlConnectBluetooth =  view.findViewById(R.id.rl_connect_bluetooth);
        RelativeLayout rlCooperationStore = view.findViewById(R.id.rl_cooperation_store);

        rlFeedback.setOnClickListener(this);
        rlRevisePhone.setOnClickListener(this);
        quitLoginBt.setOnClickListener(this);
        rlUpdateVersion.setOnClickListener(this);
        rlStoreInfo.setOnClickListener(this);
        rlConnectBluetooth.setOnClickListener(this);
        rlCooperationStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_feedback:
                //意见反馈
                startActivity(new Intent(context,FeedbackActivity.class));
                break;
            case R.id.rl_revise_phone:
                //修改密码
                startActivity(new Intent(context,RevisePasswordActivity.class));
                break;
            case R.id.rl_customer_service:
                tell(settingsEntity.getService());
                break;
            case R.id.quit_login_bt:

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("是否确认退出登录？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserCenter.cleanLoginInfo(context);
                        startActivity(new Intent(context,LoginActivity.class));
                        ExitManager.instance.exit();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.rl_update_version:
                int versionCode = VersionInfoUtils.getVersionCode(context);
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("version_code", versionCode + "");
                params.put("token",UserCenter.getToken(context));
                requestHttpData(Constants.Urls.URL_POST_UPDATE_VERSION,REQUEST_CODE_UPDATE_VERSION,
                        FProtocol.HttpMethod.POST,params);
                break;
            case R.id.rl_store_info:
                //门店信息
                startActivity(new Intent(context,StoreDetailActivity.class));
                break;
            case R.id.rl_connect_bluetooth:
                //连接蓝牙设备
                startActivity(new Intent(context,ConnectBluetoothActivity.class));
                break;
            case R.id.rl_cooperation_store:
                //合作门店
                startActivity(new Intent(context,CooperationStoreActivity.class));
                break;
        }
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_SETTINGS:
                if(data != null){
                    settingsEntity = Parsers.getSettingsEntity(data);
                    String imgPath = Constants.Urls.URL_BASE_DOMAIN + settingsEntity.getCircleLogo();
                    Uri uri = Uri.parse(imgPath);
                    ivStoreImg.setImageURI(uri);
                    storeName.setText(settingsEntity.getMname());
                    storePhoneNumber.setText(settingsEntity.getPhone());
                    storeCardNum.setText(settingsEntity.getCardNumber());
                    areaManageName.setText(settingsEntity.getManager());
                    customerServicePhoneNum.setText(settingsEntity.getService());
                    rlCustomerService.setOnClickListener(this);
                }
                break;
            case REQUEST_CODE_UPDATE_VERSION:
                if(data != null){
                    UpdataEntity updataEntity = Parsers.getUpdataEntity(data);
                    if(updataEntity != null){
                        if(updataEntity.getRetcode() == 1){
                            String versionContent = updataEntity.getDatas().getVersionContent();
                            String versionNum = updataEntity.getDatas().getVersionNum();
                            String versionSize = updataEntity.getDatas().getVersionSize();
                            updateDialog = new UpdateDialog(context, R.style.timerDialog,handler);
                            updateDialog.setView(versionContent,versionNum,versionSize);
                            updateDialog.show();
                            updateUrl = updataEntity.getDatas().getUrl();
                        }else {
                            ToastUtil.shortShow(context,"当前已是最新版本");
                        }
                    }
                }
                break;
        }
    }
}
