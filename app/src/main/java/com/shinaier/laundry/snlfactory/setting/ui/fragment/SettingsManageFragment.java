package com.shinaier.laundry.snlfactory.setting.ui.fragment;

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
import com.shinaier.laundry.snlfactory.base.fragment.BaseFragment;
import com.shinaier.laundry.snlfactory.download.DownloadApk;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.main.ui.LoginActivity;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.SettingsEntity;
import com.shinaier.laundry.snlfactory.network.entity.UpdataEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.setting.ui.activity.StoreDetailActivity;
import com.shinaier.laundry.snlfactory.setting.ui.activity.ConnectBluetoothActivity;
import com.shinaier.laundry.snlfactory.setting.ui.activity.CooperationStoreActivity;
import com.shinaier.laundry.snlfactory.setting.ui.activity.FeedbackActivity;
import com.shinaier.laundry.snlfactory.setting.ui.activity.RevisePasswordActivity;
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
    private static final int REQUEST_CODE_QUIT_LOGIN = 0x3;
    public static final int ISCHAIN = 0x4;

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
    private SettingsEntity.SettingsResult result;
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
        RelativeLayout rlChainStore = view.findViewById(R.id.rl_chain_store);

        rlFeedback.setOnClickListener(this);
        rlRevisePhone.setOnClickListener(this);
        quitLoginBt.setOnClickListener(this);
        rlUpdateVersion.setOnClickListener(this);
        rlStoreInfo.setOnClickListener(this);
        rlConnectBluetooth.setOnClickListener(this);
        rlCooperationStore.setOnClickListener(this);
        rlChainStore.setOnClickListener(this);
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
                tell(result.getService());
                break;
            case R.id.quit_login_bt:

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("是否确认退出登录？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //退出登录 清除registrationID 防止退出登录之后还有消息推送
                        IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
                        params.put("token",UserCenter.getToken(context));
                        requestHttpData(Constants.Urls.URL_POST_QUIT_LOGIN,REQUEST_CODE_QUIT_LOGIN, FProtocol.HttpMethod.POST,params);
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
            case R.id.rl_chain_store:
                //连锁门店
                Intent intent = new Intent(context,CooperationStoreActivity.class);
                intent.putExtra("is_chain",ISCHAIN);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_SETTINGS:
                if(data != null){
                    SettingsEntity settingsEntity = Parsers.getSettingsEntity(data);
                    if (settingsEntity != null){
                        if (settingsEntity.getCode() == 0){
                            result = settingsEntity.getResult();
                            setInfo();

                        }else {
                            ToastUtil.shortShow(context,settingsEntity.getMsg());
                        }
                    }

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
            case REQUEST_CODE_QUIT_LOGIN:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            UserCenter.cleanLoginInfo(context);
                            startActivity(new Intent(context,LoginActivity.class));
                            ExitManager.instance.exit();
                        }else {
                            ToastUtil.shortShow(context,"退出登录失败，请重新再试");
                        }
                    }
                }
                break;
        }
    }

    /**
     * 设置基本信息
     */
    private void setInfo() {
        ivStoreImg.setImageURI( Uri.parse(result.getmLogo()));
        storeName.setText(result.getmName());
        storePhoneNumber.setText(result.getMobileNumber());
        storeCardNum.setText(result.getBankCard());
        areaManageName.setText(result.getManager());
        customerServicePhoneNum.setText(result.getService());
        rlCustomerService.setOnClickListener(this);
    }
}
