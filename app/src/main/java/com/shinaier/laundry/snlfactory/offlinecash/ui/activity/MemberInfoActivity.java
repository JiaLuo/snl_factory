package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.BuildOrderEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineCustomInfoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.NoTakeOrderAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.AddProjectActivity;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.IdentityHashMap;


/**
 * 客户信息
 * Created by 张家洛 on 2017/7/19.
 */

public class MemberInfoActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_BUILD_ORDER = 0x1;
    public static final int FROM_MEMBER_INFO_ACT = 0x2;
    private static final int REQUEST_CODE_CUSTOM_INFO = 0x3;

    @ViewInject(R.id.custom_name)
    private TextView customName;
    @ViewInject(R.id.custom_phone)
    private TextView customPhone;
    @ViewInject(R.id.custom_last_time)
    private TextView customLastTime;
    @ViewInject(R.id.not_take_order)
    private WrapHeightListView notTakeOrder;
//    @ViewInject(R.id.member_num)
//    private TextView memberNum;
    @ViewInject(R.id.member_category)
    private TextView memberCategory;
    @ViewInject(R.id.member_balance)
    private TextView memberBalance;
    @ViewInject(R.id.rl_take_clothes_bt)
    private RelativeLayout rlTakeClothesBt;
//    @ViewInject(R.id.vip_member_num)
//    private TextView vipMemberNum;
    @ViewInject(R.id.vip_member_category)
    private TextView vipMemberCategory;
    @ViewInject(R.id.vip_member_balance)
    private TextView vipMemberBalance;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    private CommonDialog dialog;
    private OfflineCustomInfoEntity offlineCustomInfoEntity;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_clothes_act);
        ViewInjectUtils.inject(this);

        ExitManager.instance.addOfflineCollectActivity(this);
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phone_num");
        loadData(phoneNum);
        initView();
    }

    private void loadData(String number) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("number",number);
        requestHttpData(Constants.Urls.URL_POST_CUSTOM_INFO,REQUEST_CODE_CUSTOM_INFO, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("客户信息");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        dialog = new CommonDialog(this);
        rlTakeClothesBt.setOnClickListener(this);
        leftButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_take_clothes_bt:
                dialog.setContent("加载中");
                dialog.show();
                takeOrderData();
                break;
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                loadData(phoneNum);
                break;
        }
    }

    private void takeOrderData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
//        params.put("uid",offlineCustomInfoEntity.getDatas().getId());
        requestHttpData(Constants.Urls.URL_POST_BUILD_ORDER,REQUEST_CODE_BUILD_ORDER, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_BUILD_ORDER:
                if(data != null){
                    BuildOrderEntity buildOrderEntity = Parsers.getBuildOrderEntity(data);
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if (buildOrderEntity != null){
                        if(buildOrderEntity.getRetcode() == 0){
                            if(buildOrderEntity.getDatas() != null){
                                String orderId = buildOrderEntity.getDatas().getOrderId();
                                Intent intent = new Intent(this, AddProjectActivity.class);
                                intent.putExtra("extraFrom",FROM_MEMBER_INFO_ACT);
                                intent.putExtra("orderId", orderId);
                                startActivity(intent);
                            }
                        }else {
                            ToastUtil.shortShow(this,buildOrderEntity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_CUSTOM_INFO:
                if (data != null){
                    offlineCustomInfoEntity = Parsers.getOfflineCustomInfoEntity(data);
                    if (offlineCustomInfoEntity != null){
                        if (offlineCustomInfoEntity.getCode() == 0){
                            if (offlineCustomInfoEntity.getResult() != null){
                                setLoadingStatus(LoadingStatus.GONE);
                                if(!TextUtils.isEmpty(offlineCustomInfoEntity.getResult().getuName())){
                                    customName.setText("客户姓名：" + offlineCustomInfoEntity.getResult().getuName());
                                }
                                if(!TextUtils.isEmpty(offlineCustomInfoEntity.getResult().getuMobile())){
                                    customPhone.setText("手机号：" + offlineCustomInfoEntity.getResult().getuMobile());
                                }
                                if(!TextUtils.isEmpty(offlineCustomInfoEntity.getResult().getLastTime())){
                                    customLastTime.setText("上次到店：" + offlineCustomInfoEntity.getResult().getLastTime());
                                }
                                if(offlineCustomInfoEntity.getResult().getOrders() != null && offlineCustomInfoEntity.getResult().getOrders().size() > 0){
                                    notTakeOrder.setVisibility(View.VISIBLE);
                                    NoTakeOrderAdapter noTakeOrderAdapter = new NoTakeOrderAdapter(this,offlineCustomInfoEntity.getResult().getOrders());
                                    notTakeOrder.setAdapter(noTakeOrderAdapter);
                                }else {
                                    notTakeOrder.setVisibility(View.GONE);
                                }
//                                    if(!TextUtils.isEmpty(offlineCustomInfoEntity.getResult().getMerchantCard().get)){
//                                        vipMemberNum.setText("会员卡号：" + offlineCustomInfoEntity.getDatas().getCardNumber());
//                                    }else {
//                                        vipMemberNum.setText("会员卡号：");
//                                    }
                                if(!TextUtils.isEmpty(offlineCustomInfoEntity.getResult().getMerchantCard().getcName())){
                                    vipMemberCategory.setText("会员类型：" + offlineCustomInfoEntity.getResult().getMerchantCard().getcName());
                                }else {
                                    vipMemberCategory.setText("会员类型：");
                                }
                                if(!TextUtils.isEmpty(offlineCustomInfoEntity.getResult().getMerchantCard().getcBalance())){
                                    vipMemberBalance.setText("会员卡余额：￥" + offlineCustomInfoEntity.getResult().getMerchantCard().getcBalance());
                                }else {
                                    vipMemberBalance.setText("会员卡余额：￥0.00");
                                }

                                if (offlineCustomInfoEntity.getResult().getPlatformCard() != null){
                                    // TODO: 2017/12/26 不知道显示不显示卡号
//                                        if (offlineCustomInfoEntity.getResult().getPlatformCard().get() != null){
//                                            memberNum.setText("会员卡号：" + offlineCustomInfoEntity.getDatas().getPlatformCard().getCardNumber());
//                                        }else {
//                                            memberNum.setText("会员卡号：");
//                                        }
                                    if (offlineCustomInfoEntity.getResult().getPlatformCard().getcName() != null){
//                                            if (offlineCustomInfoEntity.getResult().getPlatformCard().getcName().equals("1")){
                                        memberCategory.setText("会员类型：" + offlineCustomInfoEntity.getResult().getPlatformCard().getcName());
//                                            }else if (offlineCustomInfoEntity.getDatas().getPlatformCard().getCardType().equals("2")){
//                                                memberCategory.setText("会员类型：钻石会员");
                                    }else {
                                        memberCategory.setText("会员类型：");
                                    }

                                    if (offlineCustomInfoEntity.getResult().getPlatformCard().getcBalance() != null){
                                        memberBalance.setText("会员卡余额：￥" + offlineCustomInfoEntity.getResult().getPlatformCard().getcBalance());
                                    }else {
                                        memberBalance.setText("会员卡余额：" );
                                    }
                                }
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineCustomInfoEntity.getMsg());
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_CUSTOM_INFO:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
