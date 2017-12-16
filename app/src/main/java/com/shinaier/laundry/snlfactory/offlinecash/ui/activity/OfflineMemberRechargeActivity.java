package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberRechargeEntity;
import com.shinaier.laundry.snlfactory.network.entity.PrintRechargeEntity;
import com.shinaier.laundry.snlfactory.network.entity.RechargeSuccessEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.entities.PrintEntity;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.IdentityHashMap;



/**
 * Created by 张家洛 on 2017/7/29.
 */

public class OfflineMemberRechargeActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_OFFLINE_VIP_RECHARGE = 0x2;
    private static final int REQUEST_CODE_RECHARGE_MERCHANT_CARD = 0x3;
    private static final int RECHARGESCANSUCCESS = 0x4;
    public static final int FROM_MEMBER_RECHARGE_ACT = 0x6;
    private static final int REQUEST_CODE_RECHARGE_PRINT = 0x7;
    private static final int IS_SUCCESS = 0x8;
    private static final int IS_FAIL = 0x9;

    @ViewInject(R.id.member_num_info)
    private TextView memberNumInfo;
    @ViewInject(R.id.member_name_info)
    private TextView memberNameInfo;
    @ViewInject(R.id.member_type_info)
    private TextView memberTypeInfo;
    @ViewInject(R.id.member_mobile_info)
    private TextView memberMobileInfo;
    @ViewInject(R.id.member_balance_info)
    private TextView memberBalanceInfo;
    @ViewInject(R.id.ed_recharge_money)
    private EditText edRechargeMoney;
    @ViewInject(R.id.tv_one_member_recharge_prompt)
    private TextView tvOneMemberRechargePrompt;
    @ViewInject(R.id.tv_two_member_recharge_prompt)
    private TextView tvTwoMemberRechargePrompt;
    @ViewInject(R.id.tv_three_member_recharge_prompt)
    private TextView tvThreeMemberRechargePrompt;
    @ViewInject(R.id.cash_pay_selector)
    private ImageView cashPaySelector;
    @ViewInject(R.id.wx_pay_selector)
    private ImageView wxPaySelector;
    @ViewInject(R.id.ali_pay_selector)
    private ImageView aliPaySelector;
    @ViewInject(R.id.confirm_pay)
    private TextView confirmPay;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.ed_give_money)
    private TextView edGiveMoney;

    private String userId;
    private boolean isAliPay,isWxPay,isCashPay = false;
    private String rechargeMoney;
    private String giveMoney;
    // 自定dialog
    private CommonDialog dialog;
    private PrintEntity printEntity = new PrintEntity();
    private PrintRechargeEntity printRechargeEntity;
    private Bitmap qrCodeBitmap;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IS_SUCCESS:
                    byte[] bytes = (byte[]) msg.obj;
                    qrCodeBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (qrCodeBitmap != null){
                        Intent intent = new Intent(OfflineMemberRechargeActivity.this,PrintActivity.class);
                        intent.putExtra("extra_from",FROM_MEMBER_RECHARGE_ACT);
                        intent.putExtra("print_entity",printEntity);
                        intent.putExtra("qrCode_bitmap",qrCodeBitmap);
                        startActivity(intent);
                        ToastUtil.shortShow(OfflineMemberRechargeActivity.this,"支付成功");
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                        finish();
                    }
                    break;
                case IS_FAIL:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_member_recharge_act);
        ViewInjectUtils.inject(this);
        userId = getIntent().getStringExtra("user_id");
        loadData(userId);
        initView();
    }

    private void loadData(String userId) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("member_id",userId);
        requestHttpData(Constants.Urls.URL_POST_OFFLINE_VIP_RECHARGE,REQUEST_CODE_OFFLINE_VIP_RECHARGE, FProtocol.HttpMethod.POST,params);

    }

    private void initView() {
        setCenterTitle("会员充值");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        dialog = new CommonDialog(this);//初始化自定义dialog
        leftButton.setOnClickListener(this);
        cashPaySelector.setOnClickListener(this);
        wxPaySelector.setOnClickListener(this);
        aliPaySelector.setOnClickListener(this);
        confirmPay.setOnClickListener(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_OFFLINE_VIP_RECHARGE:
                if (data != null){
                    OfflineMemberRechargeEntity offlineMemberRechargeEntity = Parsers.getOfflineMemberRechargeEntity(data);
                    if (offlineMemberRechargeEntity != null){
                        if (offlineMemberRechargeEntity.getRetcode() == 0){
                            if (offlineMemberRechargeEntity.getData() != null){
                                if (offlineMemberRechargeEntity.getData().getMember() != null){
                                    setLoadingStatus(LoadingStatus.GONE);
                                    setMemberInfo(offlineMemberRechargeEntity);
                                }else {
                                    setLoadingStatus(LoadingStatus.EMPTY);
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this,offlineMemberRechargeEntity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_RECHARGE_MERCHANT_CARD:
                if (data != null){
                    RechargeSuccessEntity rechargeSuccessEntity = Parsers.getRechargeSuccessEntity(data);
                    if (rechargeSuccessEntity != null){
                        if (rechargeSuccessEntity.getRetcode() == 0){
                            dialog.setContent("加载中");
                            dialog.show();
                            memberRecharge(rechargeSuccessEntity.getDatas().getRechargeId());
                        }else {
                            ToastUtil.shortShow(this,rechargeSuccessEntity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_RECHARGE_PRINT:
                if (data != null){
                    printRechargeEntity = Parsers.getPrintRechargeEntity(data);
                    if (printRechargeEntity != null){
                        if (printRechargeEntity.getRetcode() == 0){
                            if (printRechargeEntity.getDatas() != null){
                                setRechargePrint();
                            }
                        }else {
                            ToastUtil.shortShow(this, printRechargeEntity.getStatus());
                        }
                    }

                }
                break;
        }
    }

    private void setRechargePrint() {
        PrintEntity.RechargePrintEntity rechargePrintEntity = printEntity.new RechargePrintEntity();
        rechargePrintEntity.setMobile(printRechargeEntity.getDatas().getMobile());
        rechargePrintEntity.setRechargeAmount(printRechargeEntity.getDatas().getRechargeAmount());
        rechargePrintEntity.setGive(printRechargeEntity.getDatas().getGive());
        rechargePrintEntity.setUcode(printRechargeEntity.getDatas().getUcode());
        rechargePrintEntity.setBalance(printRechargeEntity.getDatas().getBalance());
        rechargePrintEntity.setAddress(printRechargeEntity.getDatas().getAddress());
        rechargePrintEntity.setPhone(printRechargeEntity.getDatas().getPhone());
        rechargePrintEntity.setMid(printRechargeEntity.getDatas().getMid());
        rechargePrintEntity.setPayType(printRechargeEntity.getDatas().getPayType());
        rechargePrintEntity.setClerkName(printRechargeEntity.getDatas().getClerkName());
        rechargePrintEntity.setQrcodeUrl(printRechargeEntity.getDatas().getQrcodeUrl());
        rechargePrintEntity.setOrderNumber(printRechargeEntity.getDatas().getOrderNumber());
        printEntity.setRechargePrintEntity(rechargePrintEntity);

        String qrcodeUrl = printRechargeEntity.getDatas().getQrcodeUrl();
        asyncGetImage(qrcodeUrl); //从图片链接 byte数组，然后转成bitmap对象
    }

    private void asyncGetImage(String qrcodeUrl) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().get()
                .url(qrcodeUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Message message = handler.obtainMessage();
                if (response.isSuccessful()) {
                    message.what = IS_SUCCESS;
                    message.obj = response.body().bytes();
                    handler.sendMessage(message);
                } else {
                    handler.sendEmptyMessage(IS_FAIL);
                }
            }


        });
    }

    private void memberRecharge(String rechargeId) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        params.put("clerk_id",UserCenter.getUid(this));
        params.put("recharge_id",rechargeId);
        requestHttpData(Constants.Urls.URL_POST_RECHARGE_PRINT,REQUEST_CODE_RECHARGE_PRINT, FProtocol.HttpMethod.POST,params);
    }

    private void setMemberInfo(OfflineMemberRechargeEntity offlineMemberRechargeEntity) {
        memberNumInfo.setText(offlineMemberRechargeEntity.getData().getMember().getUcode());
        memberNameInfo.setText(offlineMemberRechargeEntity.getData().getMember().getUserName());
        memberTypeInfo.setText(offlineMemberRechargeEntity.getData().getMember().getCardName());
        memberMobileInfo.setText(offlineMemberRechargeEntity.getData().getMember().getMobileNumber());
        memberBalanceInfo.setText("￥" + offlineMemberRechargeEntity.getData().getMember().getBalance());

        int price = offlineMemberRechargeEntity.getData().getMerchantCardsRules().get(0).getPrice();
        int discount = offlineMemberRechargeEntity.getData().getMerchantCardsRules().get(0).getDiscount();
        String cardName = offlineMemberRechargeEntity.getData().getMerchantCardsRules().get(0).getCardName();

        int goldPrice = offlineMemberRechargeEntity.getData().getMerchantCardsRules().get(1).getPrice();
        int goldDiscount = offlineMemberRechargeEntity.getData().getMerchantCardsRules().get(1).getDiscount();
        String goldCardName = offlineMemberRechargeEntity.getData().getMerchantCardsRules().get(1).getCardName();

        int diamondPrice = offlineMemberRechargeEntity.getData().getMerchantCardsRules().get(2).getPrice();
        int diamondDiscount = offlineMemberRechargeEntity.getData().getMerchantCardsRules().get(2).getDiscount();
        String diamondCardName = offlineMemberRechargeEntity.getData().getMerchantCardsRules().get(2).getCardName();

        String oneLine = "充值金额≥" + price + "且<" + diamondPrice + "元，可升级为黄金会员，享受" +goldDiscount +"折优惠；";
        String twoLine = "充值金额≥" + diamondPrice + ",可升级为钻石会员，享受" + diamondDiscount + "折优惠；";
        CommonTools.StringInterceptionChangeRed(tvOneMemberRechargePrompt,oneLine,"≥" + price + "且<" + diamondPrice + "","" +goldDiscount +"折");
        CommonTools.StringInterceptionChangeRed(tvTwoMemberRechargePrompt,twoLine,"≥" + diamondPrice + "","" + diamondDiscount + "折");
        tvThreeMemberRechargePrompt.setText("如果已经是钻石会员，无论充值多少，仍享受当前优惠");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.cash_pay_selector:
                if (cashPaySelector.isSelected()){
                    cashPaySelector.setSelected(false);
                    isCashPay = false;
                }else {
                    cashPaySelector.setSelected(true);
                    isCashPay = true;
                    isWxPay = false;
                    isAliPay = false;
                    if (wxPaySelector.isSelected() || aliPaySelector.isSelected()){
                        wxPaySelector.setSelected(false);
                        aliPaySelector.setSelected(false);
                    }
                }
                break;
            case R.id.wx_pay_selector:
                if (wxPaySelector.isSelected()){
                    wxPaySelector.setSelected(false);
                    isWxPay = false;
                }else {
                    wxPaySelector.setSelected(true);
                    isWxPay = true;
                    isCashPay = false;
                    isAliPay = false;
                    if (cashPaySelector.isSelected() || aliPaySelector.isSelected()){
                        cashPaySelector.setSelected(false);
                        aliPaySelector.setSelected(false);
                    }
                }
                break;
            case R.id.ali_pay_selector:
                if (aliPaySelector.isSelected()){
                    aliPaySelector.setSelected(false);
                    isAliPay = false;
                }else {
                    aliPaySelector.setSelected(true);
                    isAliPay = true;
                    isCashPay = false;
                    isWxPay = false;
                    if (cashPaySelector.isSelected() || wxPaySelector.isSelected()){
                        cashPaySelector.setSelected(false);
                        wxPaySelector.setSelected(false);
                    }
                }
                break;
            case R.id.confirm_pay:
                rechargeMoney = edRechargeMoney.getText().toString();
                giveMoney = edGiveMoney.getText().toString();
                if (isCashPay){
                    dialog.setContent("加载中");
                    dialog.show();
                    confirmRecharge("");
                }else {

                    if (!TextUtils.isEmpty(rechargeMoney)){
                        if (isCashPay || isWxPay || isAliPay){
                            startActivityForResult(new Intent(this,ScanActivity.class), RECHARGESCANSUCCESS);
                        }else {
                            ToastUtil.shortShow(this,"请选择支付方式");
                        }
                    }else {
                        ToastUtil.shortShow(this,"请输入充值金额");
                    }
                }
                break;
            case R.id.loading_layout:
                loadData(userId);
                setLoadingStatus(LoadingStatus.LOADING);
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_OFFLINE_VIP_RECHARGE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RECHARGESCANSUCCESS:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String payCode = data.getStringExtra("pay_code");
                        dialog.setContent("加载中");
                        dialog.show();
                        confirmRecharge(payCode);

                    }
                }
                break;
        }
    }

    private void confirmRecharge(String payCode) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        String memberType = memberTypeInfo.getText().toString();
        params.put("card_name",memberType);
        params.put("balance",rechargeMoney);
        params.put("uid",userId);
        if (!TextUtils.isEmpty(payCode)){
            params.put("auth_code",payCode);
        }
        params.put("type","1");
        params.put("give",giveMoney);
        if (isCashPay){
            params.put("pay_type","CASH");
        }else if (isWxPay){
            params.put("pay_type","WECHAT");
        }else {
            params.put("pay_type","ALI");
        }
        requestHttpData(Constants.Urls.URL_POST_BUY_MERCHANT_CARD,REQUEST_CODE_RECHARGE_MERCHANT_CARD, FProtocol.HttpMethod.POST,params);
    }

}
