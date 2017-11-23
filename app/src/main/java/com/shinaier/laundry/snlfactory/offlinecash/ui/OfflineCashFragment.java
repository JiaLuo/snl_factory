package com.shinaier.laundry.snlfactory.offlinecash.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineCustomInfoEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineHomeEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.util.IdentityHashMap;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 张家洛 on 2017/7/18.
 */

public class OfflineCashFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_CUSTOM_INFO = 0x1;
    public static final int HANGON = 0X2;
    private static final int REQUEST_CODE_TAKE_CLOTHES_LIST = 0x2;
    private static final int REQUEST_CODE_OFFLINE_HOME = 0x3;
    private static final int REQUEST_CODE_ORDER_QRCODE = 0x4;

    private Context context;
    private CollectClothesDialog collectClothesDialog;
    private EditText edPhoneOrderNum;
    private int position; //判断收件还是取衣
    private String number;
    // 自定dialog
    private CommonDialog dialog;
    private OfflineHomeEntity offlineHomeEntity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.offline_cash_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        loadData();
        initView(view);
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        params.put("uid",UserCenter.getUid(context));
        requestHttpData(Constants.Urls.URL_POST_OFFLINE_HOME,REQUEST_CODE_OFFLINE_HOME, FProtocol.HttpMethod.POST,params);
    }

    private void initView(View view) {
        LinearLayout llCollect = view.findViewById(R.id.ll_collect);
        LinearLayout llLaundry = view.findViewById(R.id.ll_laundry);
        LinearLayout llQualityChecking = view.findViewById(R.id.ll_quality_checking);
        LinearLayout llLeaveFactory = view.findViewById(R.id.ll_leave_factory);
        LinearLayout llBusinessCount = view.findViewById(R.id.ll_business_count);
        LinearLayout llIntoFactory = view.findViewById(R.id.ll_into_factory);
        LinearLayout llHangOn = view.findViewById(R.id.ll_hang_on);
        LinearLayout llMemberManage = view.findViewById(R.id.ll_member_manage);
        LinearLayout llTakeClothes = view.findViewById(R.id.ll_take_clothes);
        LinearLayout llDrying = view.findViewById(R.id.ll_drying);
        LinearLayout llIroning = view.findViewById(R.id.ll_ironing);

        llCollect.setOnClickListener(this);
        llHangOn.setOnClickListener(this);
        llQualityChecking.setOnClickListener(this);
        llLeaveFactory.setOnClickListener(this);
        llBusinessCount.setOnClickListener(this);
        llIntoFactory.setOnClickListener(this);
        llMemberManage.setOnClickListener(this);
        llLaundry.setOnClickListener(this);
        llTakeClothes.setOnClickListener(this);
        llDrying.setOnClickListener(this);
        llIroning.setOnClickListener(this);
        dialog = new CommonDialog(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_collect:
                //收件
                inputNumOrScan(1);
                break;
            case R.id.ll_hang_on:
                //上挂
                Intent intent = new Intent(context,SendLaundryActivity.class);
                intent.putExtra("extra_from",HANGON);
                startActivity(intent);
                break;
            case R.id.ll_member_manage:
                //会员管理
                if (offlineHomeEntity != null){
                    Intent memberIntent = new Intent(context,OfflineMemberManageActivity.class);
                    memberIntent.putExtra("store_logo",offlineHomeEntity.getDatas().getCircleLogo());
                    memberIntent.putExtra("store_name",offlineHomeEntity.getDatas().getmName());
                    startActivity(memberIntent);
                }
                break;
            case R.id.ll_laundry:
                //送洗
                startActivity(new Intent(context,SendLaundryActivity.class));
                break;
            case R.id.ll_take_clothes:
                //取衣
                inputNumOrScan(2);
                break;
            case R.id.ll_business_count:
                //业务统计
                startActivity(new Intent(context,StatisticsActivity.class));
                break;
            case R.id.ll_into_factory:
                //入厂
                startActivity(new Intent(context,IntoFactoryActivity.class));
                break;
            case R.id.ll_quality_checking:
                //质检

                break;
            case R.id.ll_leave_factory:
                //出厂
                startActivity(new Intent(context,LeaveFactoryActivity.class));
                break;
            case R.id.ll_drying:
                //烘干

                break;
            case R.id.ll_ironing:
                //熨烫

                break;

        }
    }

    private void inputNumOrScan(int witch) {
        position = witch;
        View view = View.inflate(context, R.layout.collect_clothes_view,null);
        TextView tvInputNum = (TextView) view.findViewById(R.id.tv_input_num);  //显示标题
        edPhoneOrderNum = (EditText) view.findViewById(R.id.ed_phone_order_num);
        ImageView ivScan = (ImageView) view.findViewById(R.id.iv_scan);
        if(witch == 1){
            ivScan.setVisibility(View.GONE);
            tvInputNum.setText("请输入客户手机号/会员卡号");
            edPhoneOrderNum.setHint("请输入客户手机号/会员卡号");
        }else if(witch == 2){
            tvInputNum.setText("请输入客户手机号/扫描订单号");
            edPhoneOrderNum.setHint("请输入客户手机号/扫描订单号");
            ivScan.setVisibility(View.VISIBLE);
            ivScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(context,ScanActivity.class),REQUEST_CODE_ORDER_QRCODE);
                }
            });
        }
        TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel); //取消按钮
        TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm); //确定按钮
        collectClothesDialog = new CollectClothesDialog(context, R.style.DialogTheme,view);
        collectClothesDialog.show();

        revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectClothesDialog.dismiss();
            }
        });
        revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = edPhoneOrderNum.getText().toString();
                if(!TextUtils.isEmpty(number)){
                    if (position == 1){ // 收件

                        dialog.setContent("加载中");
                        dialog.show();
                        collectData();
                    }else if(position == 2){ // 取衣
                        dialog.setContent("加载中");
                        dialog.show();
                        takeOrderData(number); //请求网络判断订单号手机号是否存在
                    }
                }else {
                    ToastUtil.shortShow(context,"请输入手机号/会员卡号");
                }
            }
        });

    }

    private void takeOrderData(String number) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(context));
        params.put("number",number);
        params.put("order_exist","1");
        requestHttpData(Constants.Urls.URL_POST_TAKE_CLOTHES_LIST,REQUEST_CODE_TAKE_CLOTHES_LIST, FProtocol.HttpMethod.POST,params);
    }

    private void collectData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        params.put("number",number);
        requestHttpData(Constants.Urls.URL_POST_CUSTOM_INFO,REQUEST_CODE_CUSTOM_INFO, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CUSTOM_INFO:
                if(data != null){
                    OfflineCustomInfoEntity offlineCustomInfoEntity = Parsers.getOfflineCustomInfoEntity(data);
                    if(offlineCustomInfoEntity != null){
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if(offlineCustomInfoEntity.getRetcode() == 0){
                            //有会员存在
                            Intent intent = new Intent(context,MemberInfoActivity.class);
                            intent.putExtra("phone_num",number);
                            startActivity(intent);
                            collectClothesDialog.dismiss();

                        }else if (offlineCustomInfoEntity.getRetcode() == 1){
                            //会员不存在 新增散客
                            if (CommonTools.checkPhoneNumber(context,true,number)){
                                Intent intent = new Intent(context,OfflineAddVisitorActivity.class);
                                intent.putExtra("phone_num",number);
                                startActivity(intent);
                                collectClothesDialog.dismiss();
                            }
                        }else {
                            ToastUtil.shortShow(context,offlineCustomInfoEntity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_TAKE_CLOTHES_LIST:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if(entity.getRetcode() == 0){
                            Intent intent = new Intent(context,TakeClothesActivity.class);
                            intent.putExtra("phoneNum",number);
                            startActivity(intent); //进入取衣页面
                            collectClothesDialog.dismiss(); //加载dialog显示
                        }else {
                            ToastUtil.shortShow(context,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_OFFLINE_HOME:
                if (data != null){
                    offlineHomeEntity = Parsers.getOfflineHomeEntity(data);
                    if (offlineHomeEntity != null){
                        if(offlineHomeEntity.getRetcode() == 0){

                        }else {
                            ToastUtil.shortShow(context, offlineHomeEntity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ORDER_QRCODE:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String payCode = data.getStringExtra("pay_code");
                        Intent intent = new Intent(context,TakeClothesActivity.class);
                        intent.putExtra("phoneNum",payCode);
                        startActivity(intent); //进入取衣页面
                        collectClothesDialog.dismiss(); //加载dialog显示
                    }
                }
                break;
        }
    }
}
