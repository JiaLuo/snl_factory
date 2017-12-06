package com.shinaier.laundry.snlfactory.setting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.view.DoorToDoorServiceDialog;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.StoreInfoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.ui.VipCardDetailActivity;
import com.shinaier.laundry.snlfactory.setting.view.RevisePhoneDialog;
import com.shinaier.laundry.snlfactory.setting.view.ReviseServiceDialog;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;



/**
 * Created by 张家洛 on 2017/2/17.
 */

public class StoreDetailActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_STORE_INFO = 0x1;
    private static final int REQUEST_CODE_REVISE_PHONE = 0x2;
    private static final int REQUEST_CODE_REVISE_RANGE = 0x3;
    private static final int REQUEST_CODE_REVISE_MONEY = 0x4;

    @ViewInject(R.id.store_num)
    private TextView storeNum;
    @ViewInject(R.id.store_name)
    private TextView storeName;
//    @ViewInject(R.id.rl_store_qr_code)
//    private RelativeLayout rlStoreQrCode;
    @ViewInject(R.id.store_phone_num)
    private TextView storePhoneNum;
    @ViewInject(R.id.rl_service_scope)
    private RelativeLayout rlServiceScope;
    @ViewInject(R.id.store_address_detail)
    private TextView storeAddressDetail;
    @ViewInject(R.id.service_scope_num)
    private TextView serviceScopeNum;
    @ViewInject(R.id.home_service_num)
    private TextView homeServiceNum;
    @ViewInject(R.id.fulled_subtract_num)
    private TextView fulledSubtractNum;
    @ViewInject(R.id.fulled_subtract_money)
    private TextView fulledSubtractMoney;
    @ViewInject(R.id.rl_store_phone)
    private RelativeLayout rlStorePhone;
    @ViewInject(R.id.rl_door_to_door_service)
    private RelativeLayout rlDoorToDoorService;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.rl_vip_info)
    private RelativeLayout rlVipInfo;
//    @ViewInject(R.id.normal_vip_discount)
//    private TextView normalVipDiscount;
    @ViewInject(R.id.gold_vip_discount)
    private TextView goldVipDiscount;
    @ViewInject(R.id.diamond_vip_discount)
    private TextView diamondVipDiscount;


    private StoreInfoEntity storeInfoEntity;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String edRevisePhone = (String) msg.obj;
                    if(!TextUtils.isEmpty(edRevisePhone)){
                        IdentityHashMap<String,String> revisePhoneParams = new IdentityHashMap<>();
                        revisePhoneParams.put("token", UserCenter.getToken(StoreDetailActivity.this));
                        revisePhoneParams.put("phone", edRevisePhone);
                        requestHttpData(Constants.Urls.URL_POST_STORE_INFO,REQUEST_CODE_REVISE_PHONE, FProtocol.HttpMethod.POST,revisePhoneParams);
                    }else {
                        ToastUtil.shortShow(StoreDetailActivity.this,"填写内容不能为空");
                    }
                    break;
                case 2:
                    String reviseRange = (String)msg.obj;
                    if(!TextUtils.isEmpty(reviseRange)){
                        IdentityHashMap<String,String> reviseRangeParams = new IdentityHashMap<>();
                        reviseRangeParams.put("token", UserCenter.getToken(StoreDetailActivity.this));
                        reviseRangeParams.put("round",reviseRange);
                        requestHttpData(Constants.Urls.URL_POST_STORE_INFO,REQUEST_CODE_REVISE_RANGE, FProtocol.HttpMethod.POST,reviseRangeParams);
                    }else {
                        ToastUtil.shortShow(StoreDetailActivity.this,"填写内容不能为空");
                    }
                    break;
                case 3:
                    Bundle data = msg.getData();
                    String reviseDoorServiceMoney = data.getString("reviseDoorServiceMoney");
                    String reviseClothesNumber = data.getString("reviseClothesNumber");
                    String reviseMoney = data.getString("reviseMoney");
                    IdentityHashMap<String,String> reviseMoneyParams = new IdentityHashMap<>();
                    reviseMoneyParams.put("token", UserCenter.getToken(StoreDetailActivity.this));
                    reviseMoneyParams.put("fuwu_amount",reviseDoorServiceMoney);
                    reviseMoneyParams.put("fuwu_num",reviseClothesNumber);
                    reviseMoneyParams.put("fuwu_total",reviseMoney);
                    requestHttpData(Constants.Urls.URL_POST_STORE_INFO,REQUEST_CODE_REVISE_MONEY, FProtocol.HttpMethod.POST,reviseMoneyParams);
                    break;
            }
        }
    };
    private ReviseServiceDialog serviceDialog;
    private RevisePhoneDialog phoneDialog;
    private DoorToDoorServiceDialog doorToDoorServiceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("门店信息");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
//        rlStoreQrCode.setOnClickListener(this);
        rlStorePhone.setOnClickListener(this);
        rlServiceScope.setOnClickListener(this);
        rlDoorToDoorService.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        rlVipInfo.setOnClickListener(this);
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MERCHANT_INFO,REQUEST_CODE_STORE_INFO, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_STORE_INFO:
                if(data != null){
                    StoreInfoEntity storeInfoEntity = Parsers.getStoreInfoEntity(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if (storeInfoEntity != null){
                        if (storeInfoEntity.getCode() == 0){
                            StoreInfoEntity.StoreInfoResult result = storeInfoEntity.getResult();
                            setStoreInfo(result);
                        }
                    }

                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_REVISE_PHONE:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        loadData();
                        ToastUtil.shortShow(this,"修改成功");
                        phoneDialog.dismiss();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_REVISE_RANGE:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        loadData();
                        ToastUtil.shortShow(this,"修改成功");
                        serviceDialog.dismiss();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_REVISE_MONEY:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        loadData();
                        ToastUtil.shortShow(this,"修改成功");
                        doorToDoorServiceDialog.dismiss();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
        }
    }

    private void setStoreInfo(StoreInfoEntity.StoreInfoResult result) {
        storeNum.setText(result.getMerchant().getId());
        storeName.setText(result.getMerchant().getmName());
        storePhoneNum.setText(result.getMerchant().getPhoneNumber());
        serviceScopeNum.setText(result.getMerchant().getmRange() + "km");
        storeAddressDetail.setText(result.getMerchant().getmAddress());
        homeServiceNum.setText("上门服务费:" + result.getMerchant().getFreightPrice() + "元");
        fulledSubtractNum.setText("满减件数:" + result.getMerchant().getFreightFreeNum() + "件");
        fulledSubtractMoney.setText("满减金额：" + result.getMerchant().getFreightFreeAmount() + "元");
//        normalVipDiscount.setText("普通会员：无折扣 充值" + storeInfoEntity.getCards().get(0).getPrice()+ "元");
        goldVipDiscount.setText("黄金会员：" + result.getCards().get(0).getDiscount()
                + "折 充值" + result.getCards().get(0).getPrice() + "元");
        diamondVipDiscount.setText("钻石会员：" + result.getCards().get(1).getDiscount()
                + "折 充值 " + result.getCards().get(1).getPrice() +" 元");
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.rl_store_qr_code:
//                Intent qrCodeIntent = new Intent(this, WebViewActivity.class);
//                qrCodeIntent.putExtra(WebViewActivity.EXTRA_URL, storeInfoEntity.getQrCode());
//                qrCodeIntent.putExtra(WebViewActivity.EXTRA_TITLE, "我的二维码");
//                startActivity(qrCodeIntent);
//                break;
            case R.id.rl_store_phone:
                phoneDialog = new RevisePhoneDialog(this, R.style.timerDialog,handler);
                phoneDialog.setView();
                phoneDialog.show();
                break;
            case R.id.rl_service_scope:
                serviceDialog = new ReviseServiceDialog(this, R.style.timerDialog,handler);
                serviceDialog.setView();
                serviceDialog.show();
                break;
            case R.id.rl_door_to_door_service:
//                doorToDoorServiceDialog = new DoorToDoorServiceDialog(this, R.style.timerDialog,handler,storeInfoEntity);
//                doorToDoorServiceDialog.setView();
//                doorToDoorServiceDialog.show();
                break;
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;
            case R.id.rl_vip_info:
                Intent intent = new Intent(this,VipCardDetailActivity.class);
//                ArrayList<StoreInfoEntity.StoreINfoCards> cards = (ArrayList<StoreInfoEntity.StoreINfoCards>) storeInfoEntity.getCards();
//                intent.putParcelableArrayListExtra("cards",cards);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_STORE_INFO:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
