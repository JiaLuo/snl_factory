package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.BuyPlateformCardEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/7/22.
 */

public class BuyMemberCardActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int GOLD = 1;
    private static final int DIAMOND = 2;
    private static final int WXPAY = 3;
    private static final int ALIPAY = 4;
    private static final int REQUEST_CODE_BUY_CARD_ENTITY = 0x5;
    private static final int BUY_CARD_CODE = 0x6;
    private static final int REQUEST_CODE_BUY_CARD = 0x7;

    @ViewInject(R.id.manager_name)
    private EditText managerName;
    @ViewInject(R.id.tv_phone_num)
    private TextView tvPhoneNum;
    @ViewInject(R.id.tv_invite_code)
    private TextView tvInviteCode;
    @ViewInject(R.id.iv_member_gold)
    private ImageView ivMemberGold;
    @ViewInject(R.id.gold_money_num)
    private TextView goldMoneyNum;
    @ViewInject(R.id.iv_member_diamond)
    private ImageView ivMemberDiamond;
    @ViewInject(R.id.diamond_money_num)
    private TextView diamondMoneyNum;
    @ViewInject(R.id.iv_wxpay_mode)
    private ImageView ivWxpayMode;
    @ViewInject(R.id.iv_alipay_mode)
    private ImageView ivAlipayMode;
    @ViewInject(R.id.confirm_pay)
    private TextView confirmPay;
    @ViewInject(R.id.gold_member)
    private TextView goldMember;
    @ViewInject(R.id.gold_favourable_num)
    private TextView goldFavourableNum;
    @ViewInject(R.id.diamond_member)
    private TextView diamondMember;
    @ViewInject(R.id.diamond_favourable_num)
    private TextView diamondFavourableNum;

    private int witchMember;
    private int witchPayMode;
    private String goldNum;
    private String diamondNum;
    private String name;
    private List<BuyPlateformCardEntity.BuyPlateformCardDatas> datas;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_member_card_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        String mobileNum = intent.getStringExtra("mobile_num");
        String inviteCode = intent.getStringExtra("invite_code");
        initView(mobileNum,inviteCode);
        loadData();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("uid",UserCenter.getUid(this));
        requestHttpData(Constants.Urls.URL_POST_GET_PLATFORM_CARD_,REQUEST_CODE_BUY_CARD_ENTITY, FProtocol.HttpMethod.POST,params);
    }

    private void initView(String mobile, String inviteCode) {
        setCenterTitle("购买方式");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        ivMemberGold.setOnClickListener(this);
        ivMemberDiamond.setOnClickListener(this);
        ivWxpayMode.setOnClickListener(this);
        ivAlipayMode.setOnClickListener(this);
        confirmPay.setOnClickListener(this);
        tvPhoneNum.setText(mobile);
        tvInviteCode.setText(inviteCode);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_BUY_CARD_ENTITY:
                if (data != null){
                    setLoadingStatus(LoadingStatus.GONE);
                    BuyPlateformCardEntity buyPlateformCardEntity = Parsers.getBuyPlateformCardEntity(data);
                    if (buyPlateformCardEntity != null){
                        if (buyPlateformCardEntity.getRetcode() == 0){
                            datas = buyPlateformCardEntity.getDatas();
                            setMemberInfo(datas);
                        }else {
                            ToastUtil.shortShow(this,buyPlateformCardEntity.getStatus());
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_BUY_CARD:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            ToastUtil.shortShow(this,"支付成功");
                            finish();
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }

                break;
        }
    }

    private void setMemberInfo(List<BuyPlateformCardEntity.BuyPlateformCardDatas> datas) {
        goldMember.setText(datas.get(0).getCardname());
        goldFavourableNum.setText("享" + datas.get(0).getCarddiscount() + "折优惠");
        goldMoneyNum.setText("￥" + datas.get(0).getCardsum());

        diamondMember.setText(datas.get(1).getCardname());
        diamondFavourableNum.setText("享" + datas.get(1).getCarddiscount() + "折优惠");
        diamondMoneyNum.setText("￥" + datas.get(1).getCardsum());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_layout:
                loadData();
                break;
            case R.id.iv_member_gold:
                ivMemberDiamond.setSelected(false);
                if(ivMemberGold.isSelected()){
                    ivMemberGold.setSelected(false);
                    witchMember = 0;
                }else {
                    ivMemberGold.setSelected(true);
                    goldNum = goldMoneyNum.getText().toString();
                    witchMember = GOLD;
                }
                break;
            case R.id.iv_member_diamond:
                ivMemberGold.setSelected(false);
                if(ivMemberDiamond.isSelected()){
                    ivMemberDiamond.setSelected(false);
                    witchMember = 0;
                }else {
                    ivMemberDiamond.setSelected(true);
                    diamondNum = diamondMoneyNum.getText().toString();
                    witchMember = DIAMOND;
                }
                break;
            case R.id.iv_wxpay_mode:
                ivAlipayMode.setSelected(false);
                if(ivWxpayMode.isSelected()){
                    ivWxpayMode.setSelected(false);
                    witchPayMode = 0;
                }else {
                    ivWxpayMode.setSelected(true);
                    witchPayMode = WXPAY;
                }
                break;
            case R.id.iv_alipay_mode:
                ivWxpayMode.setSelected(false);
                if(ivAlipayMode.isSelected()){
                    ivAlipayMode.setSelected(false);
                    witchPayMode = 0;
                }else {
                    ivAlipayMode.setSelected(true);
                    witchPayMode = ALIPAY;
                }
                break;
            case R.id.confirm_pay:
                name = managerName.getText().toString();
                if(!TextUtils.isEmpty(name)){
                    if (witchMember == GOLD){
                        if(witchPayMode == WXPAY){
                            LogUtil.e("zhang","微信支付 = " + name + "," + goldNum);
                            startActivityForResult(new Intent(this,ScanActivity.class), BUY_CARD_CODE);
                        }else if(witchPayMode == ALIPAY){
                            LogUtil.e("zhang","支付宝支付 = " + name + "," + goldNum);
                            startActivityForResult(new Intent(this,ScanActivity.class), BUY_CARD_CODE);
                        }else {
                            ToastUtil.shortShow(this,"请选择支付方式");
                        }
                    }else if(witchMember == DIAMOND){
                        if(witchPayMode == WXPAY){
                            LogUtil.e("zhang","微信支付 = " + name + "," + diamondNum);
                            startActivityForResult(new Intent(this,ScanActivity.class), BUY_CARD_CODE);
                        }else if(witchPayMode == ALIPAY){
                            LogUtil.e("zhang","支付宝支付 = " + name + "," + diamondNum);
                            startActivityForResult(new Intent(this,ScanActivity.class), BUY_CARD_CODE);
                        }else {
                            ToastUtil.shortShow(this,"请选择支付方式");
                        }
                    }else {
                        ToastUtil.shortShow(this,"请选择会员卡类型");
                    }
                }else {
                    ToastUtil.shortShow(this,"请输入您的姓名");
                }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case BUY_CARD_CODE:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String payCode = data.getStringExtra("pay_code");
                        IdentityHashMap<String,String> params = new IdentityHashMap<>();
                        params.put("token",UserCenter.getToken(this));
                        params.put("user",userId);
                        if (!TextUtils.isEmpty(name)){
                            params.put("manager",name);
                        }

                        params.put("auth_code",payCode);
                        if (witchMember == GOLD){
                            params.put("cardType",datas.get(0).getCardtype());
                        }else {
                            params.put("cardType",datas.get(1).getCardtype());
                        }

                        if (witchPayMode == WXPAY){
                            params.put("payType","WECHAT");
                        }else {
                            params.put("payType","ALI");
                        }



                        requestHttpData(Constants.Urls.URL_POST_BUY_PLATFORM_CARD,REQUEST_CODE_BUY_CARD, FProtocol.HttpMethod.POST,params);
                    }
                }
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_BUY_CARD_ENTITY:
                loadData();
                break;
        }
    }
}
