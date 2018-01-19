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
import com.shinaier.laundry.snlfactory.network.entity.OfflineCustomInfoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.NoTakeOrderAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.AddProjectsActivity;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.IdentityHashMap;


/**
 * 客户信息
 * Created by 张家洛 on 2017/7/19.
 */

public class MemberInfoActivity extends ToolBarActivity implements View.OnClickListener {
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
    @ViewInject(R.id.member_category)
    private TextView memberCategory;
    @ViewInject(R.id.member_balance)
    private TextView memberBalance;
    @ViewInject(R.id.rl_take_clothes_bt)
    private RelativeLayout rlTakeClothesBt;
    @ViewInject(R.id.vip_member_category)
    private TextView vipMemberCategory;
    @ViewInject(R.id.vip_member_balance)
    private TextView vipMemberBalance;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

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
        rlTakeClothesBt.setOnClickListener(this);
        leftButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_take_clothes_bt:
                Intent intent = new Intent(this, AddProjectsActivity.class);
                intent.putExtra("extraFrom",FROM_MEMBER_INFO_ACT);
                intent.putExtra("user_id",offlineCustomInfoEntity.getResult().getId());
                startActivity(intent);
                break;
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                loadData(phoneNum);
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
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
                                    if (offlineCustomInfoEntity.getResult().getPlatformCard().getcName() != null){
                                        memberCategory.setText("会员类型：" + offlineCustomInfoEntity.getResult().getPlatformCard().getcName());
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
