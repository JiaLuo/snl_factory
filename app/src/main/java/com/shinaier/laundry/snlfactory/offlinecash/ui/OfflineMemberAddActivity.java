package com.shinaier.laundry.snlfactory.offlinecash.ui;

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
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineAddVisitorEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberNumEntity;
import com.shinaier.laundry.snlfactory.network.entity.StoreInfoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.view.OnDateSetListener;
import com.shinaier.laundry.snlfactory.offlinecash.view.TimePickerDialog;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;


/**
 * 新增会员
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberAddActivity extends ToolBarActivity implements View.OnClickListener, OnDateSetListener {
    private static final int REQUEST_CODE_ADD_MEMBER_NUM = 0x1;
    private static final int REQUEST_CODE_ADD_MEMBER = 0x2;
    private static final int REQUEST_CODE_BUY_MERCHANT_CARD = 0x3;
    public static final int SCAN_SUCCESS = 0x5;
    private static final int REQUEST_CODE_MERCHANT_INFO = 0x6;

    @ViewInject(R.id.tv_member_num)
    private TextView tvMemberNum;
    @ViewInject(R.id.ed_input_member_name)
    private EditText edInputMemberName;
    @ViewInject(R.id.sex_man)
    private TextView sexMan;
    @ViewInject(R.id.sex_women)
    private TextView sexWomen;
    @ViewInject(R.id.tv_member_mobile)
    private TextView tvMemberMobile;
    @ViewInject(R.id.tv_member_birth)
    private TextView tvMemberBirth;
    @ViewInject(R.id.ed_input_member_address)
    private EditText edInputMemberAddress;
    @ViewInject(R.id.ed_input_member_mark)
    private EditText edInputMemberMark;
    @ViewInject(R.id.iv_member_normal_discount)
    private ImageView ivMemberNormalDiscount;
    @ViewInject(R.id.iv_member_nine_discount)
    private ImageView ivMemberNineDiscount;
    @ViewInject(R.id.iv_member_eight_discount)
    private ImageView ivMemberEightDiscount;
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
    @ViewInject(R.id.tv_member_normal)
    private TextView tvMemberNormal;
    @ViewInject(R.id.tv_member_gold)
    private TextView tvMemberGold;
    @ViewInject(R.id.tv_member_diamond)
    private TextView tvMemberDiamond;
    @ViewInject(R.id.member_normal_recharge_money)
    private TextView memberNormalRechargeMoney;
    @ViewInject(R.id.member_gold_recharge_money)
    private TextView memberGoldRechargeMoney;
    @ViewInject(R.id.member_diamond_recharge_money)
    private TextView memberDiamondRechargeMoney;
    @ViewInject(R.id.member_normal_discount)
    private TextView memberNormalDiscount;
    @ViewInject(R.id.member_gold_discount)
    private TextView memberGoldDiscount;
    @ViewInject(R.id.member_diamond_discount)
    private TextView memberDiamondDiscount;


    private String phoneNum; //从上一界面传来的电话号码
    private String ucode; //获取的会员编号
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    TimePickerDialog mDialogYearMonthDay;
    private String memberBirth;
    private boolean isAliPay,isWxPay,isCashPay,isNineDiscount,isEightDiscount,isNormalDiscount,isClickTime,isMan,isWoman = false;
    private String user;
    // 自定dialog
    private CommonDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_member_add_act);
        ViewInjectUtils.inject(this);
        phoneNum = getIntent().getStringExtra("phone_num");
        loadMemberNumData();// 获取会员编号
        loadDiscountData();
        initView();
    }

    private void loadDiscountData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MERCHANT_INFO,REQUEST_CODE_MERCHANT_INFO,
                FProtocol.HttpMethod.POST,params);
    }

    private void loadMemberNumData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_ADD_MEMBER_NUM,REQUEST_CODE_ADD_MEMBER_NUM,
                                    FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("新增会员");
        tvMemberMobile.setText(phoneNum);
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        confirmPay.setOnClickListener(this);
        sexMan.setOnClickListener(this);
        sexWomen.setOnClickListener(this);
        tvMemberBirth.setOnClickListener(this);
        ivMemberNormalDiscount.setOnClickListener(this);
        ivMemberNineDiscount.setOnClickListener(this);
        ivMemberEightDiscount.setOnClickListener(this);
        cashPaySelector.setOnClickListener(this);
        wxPaySelector.setOnClickListener(this);
        aliPaySelector.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        if (!isMan && !isWoman){
            sexWomen.setSelected(true);
        }
        //初始化时间选择器
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(this)
                .build();

        dialog = new CommonDialog(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD_MEMBER_NUM:
                if(data != null){
                    OfflineMemberNumEntity offlineMemberNumEntity = Parsers.getOfflineMemberNumEntity(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if (offlineMemberNumEntity != null){
                        if (offlineMemberNumEntity.getRetcode() == 0){
                            if (offlineMemberNumEntity.getDatas() != null){
                                //获取会员卡号
                                ucode = offlineMemberNumEntity.getDatas().getUcode();
                                tvMemberNum.setText(ucode);
                            }
                        }else {
                            ToastUtil.shortShow(this,offlineMemberNumEntity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ADD_MEMBER:
                if (data != null){
                    OfflineAddVisitorEntity offlineAddVisitorEntity = Parsers.getOfflineAddVisitorEntity(data);
//                    setLoadingStatus(LoadingStatus.GONE);
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if (offlineAddVisitorEntity != null){
                        if (offlineAddVisitorEntity.getRetcode() == 0){
                            if (offlineAddVisitorEntity.getDatas() != null){
                                user = offlineAddVisitorEntity.getDatas().getUser();
                                if (isCashPay){
                                    confirmAdd("");
                                }else {
                                    startActivityForResult(new Intent(this,ScanActivity.class),SCAN_SUCCESS);
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this,offlineAddVisitorEntity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_BUY_MERCHANT_CARD:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
//                    setLoadingStatus(LoadingStatus.GONE);
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            finish();
                            ToastUtil.shortShow(this,"支付成功");
                        }else {
                            finish();
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_MERCHANT_INFO:
                if (data != null){
                    StoreInfoEntity storeInfoEntity = Parsers.getStoreInfoEntity(data);
                    if (storeInfoEntity != null){
                        if (storeInfoEntity.getCards() != null && storeInfoEntity.getCards().size() > 0){
                            //设置普通会员信息
                            tvMemberNormal.setText(storeInfoEntity.getCards().get(0).getCardName());
                            if (storeInfoEntity.getCards().get(0).getDiscount().equals("10.0")){
                                memberNormalDiscount.setText("无折扣");
                            }else {
                                memberNormalDiscount.setText(storeInfoEntity.getCards().get(0).getDiscount() + "折");
                            }
                            memberNormalRechargeMoney.setText("充值" + storeInfoEntity.getCards().get(0).getPrice());
                            //设置黄金会员信息
                            tvMemberGold.setText(storeInfoEntity.getCards().get(1).getCardName());
                            memberGoldDiscount.setText(storeInfoEntity.getCards().get(1).getDiscount() + "折");
                            memberGoldRechargeMoney.setText("充值" + storeInfoEntity.getCards().get(1).getPrice());
                            //设置钻石会员信息
                            tvMemberDiamond.setText(storeInfoEntity.getCards().get(2).getCardName());
                            memberDiamondDiscount.setText(storeInfoEntity.getCards().get(2).getDiscount() + "折");
                            memberDiamondRechargeMoney.setText("充值" + storeInfoEntity.getCards().get(2).getPrice());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm_pay:
                String memberName = edInputMemberName.getText().toString();
                String memberAddress = edInputMemberAddress.getText().toString();
                String memberMark = edInputMemberMark.getText().toString();
                LogUtil.e("zhang","name = " + memberName);
                if (!TextUtils.isEmpty(memberName)){
                    if (isNormalDiscount || isNineDiscount || isEightDiscount){
                        if (isCashPay || isWxPay || isAliPay){
//                            setLoadingStatus(LoadingStatus.LOADING);
                            dialog.setContent("加载中");
                            dialog.show();
                            IdentityHashMap<String,String> params = new IdentityHashMap<>();
                            params.put("token",UserCenter.getToken(this));
                            params.put("ucode",ucode);
                            params.put("uname",memberName);
                            if (isMan){
                                params.put("sex","1");

                            }else if (isWoman){
                                params.put("sex","2");
                            }else {
                                params.put("sex","2");
                            }
                            params.put("mobile",phoneNum);
                            if (isClickTime){
                                params.put("birthday",memberBirth);
                            }else {
                                params.put("birthday","1980-01-01");
                            }

                            params.put("address",memberAddress);
                            params.put("remark",memberMark);
                            requestHttpData(Constants.Urls.URL_POST_ADD_MEMBER,REQUEST_CODE_ADD_MEMBER, FProtocol.HttpMethod.POST,params);
                        }else {
                            ToastUtil.shortShow(this,"请选择支付方式");
                        }
                    }else {
                        ToastUtil.shortShow(this,"请选择充值会员类型");
                    }
                }else {
                    ToastUtil.shortShow(this,"请输入名字");
                }

                break;
            case R.id.sex_man:
                if (sexMan.isSelected()){
                    sexMan.setSelected(false);
                }else {
                    sexMan.setSelected(true);
                    if (sexWomen.isSelected()){
                        sexWomen.setSelected(false);
                    }
                    isMan = true;
                    isWoman = false;
                }

                break;
            case R.id.sex_women:
                if (sexWomen.isSelected()){
                    sexWomen.setSelected(false);
                }else {
                    sexWomen.setSelected(true);
                    if (sexMan.isSelected()){
                        sexMan.setSelected(false);
                    }
                    isWoman = true;
                    isMan = false;
                }
                break;
            case R.id.tv_member_birth:
                isClickTime = true;
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.iv_member_normal_discount:
                if (ivMemberNormalDiscount.isSelected()){
                    ivMemberNormalDiscount.setSelected(false);
                }else {
                    ivMemberNormalDiscount.setSelected(true);
                    isNormalDiscount = true;
                    isNineDiscount = false;
                    isEightDiscount = false;
                    if (ivMemberNineDiscount.isSelected() || ivMemberEightDiscount.isSelected()){
                        ivMemberNineDiscount.setSelected(false);
                        ivMemberEightDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.iv_member_nine_discount:
                if (ivMemberNineDiscount.isSelected()){
                    ivMemberNineDiscount.setSelected(false);
                }else {
                    ivMemberNineDiscount.setSelected(true);
                    isNineDiscount = true;
                    isNormalDiscount = false;
                    isEightDiscount = false;
                    if (ivMemberNormalDiscount.isSelected() || ivMemberEightDiscount.isSelected()){
                        ivMemberNormalDiscount.setSelected(false);
                        ivMemberEightDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.iv_member_eight_discount:
                if (ivMemberEightDiscount.isSelected()){
                    ivMemberEightDiscount.setSelected(false);
                }else {
                    ivMemberEightDiscount.setSelected(true);
                    isEightDiscount = true;
                    isNineDiscount = false;
                    isNormalDiscount = false;
                    if (ivMemberNormalDiscount.isSelected() || ivMemberNineDiscount.isSelected()){
                        ivMemberNormalDiscount.setSelected(false);
                        ivMemberNineDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.cash_pay_selector:
                if (cashPaySelector.isSelected()){
                    cashPaySelector.setSelected(false);
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
            case R.id.left_button:
                finish();
                break;
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        memberBirth = getDateToString(millseconds);
        tvMemberBirth.setText(memberBirth);
    }

    //格式化时间
    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SCAN_SUCCESS:
                if (resultCode == RESULT_OK){
                    if (data != null){
//                        setLoadingStatus(LoadingStatus.LOADING);
                        dialog.setContent("加载中");
                        dialog.show();
                        String payCode = data.getStringExtra("pay_code");
                        confirmAdd(payCode);
                    }
                }
                break;
        }
    }

    private void confirmAdd(String payCode) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        if (isNormalDiscount){
            String memberNormal = tvMemberNormal.getText().toString();
            String normalRechargeMoney = memberNormalRechargeMoney.getText().toString();
            params.put("card_name",memberNormal);
            params.put("balance",normalRechargeMoney.substring(2,normalRechargeMoney.length()));
            params.put("discount","10");
        }else if (isNineDiscount){
            String memberGold = tvMemberGold.getText().toString();
            String goldRechargeMoney = memberGoldRechargeMoney.getText().toString();
            String goldDiscount = memberGoldDiscount.getText().toString();
            params.put("card_name",memberGold);
            params.put("balance",goldRechargeMoney.substring(2,goldRechargeMoney.length()));
            params.put("discount",goldDiscount.substring(0,1));
        }else {
            String memberDiamond = tvMemberDiamond.getText().toString();
            String diamondRechargeMoney = memberDiamondRechargeMoney.getText().toString();
            String diamondDiscount = memberDiamondDiscount.getText().toString();
            params.put("card_name",memberDiamond);
            params.put("balance",diamondRechargeMoney.substring(2,diamondRechargeMoney.length()));
            params.put("discount",diamondDiscount.substring(0,1));
        }
        params.put("uid",user);
        if (!TextUtils.isEmpty(payCode)){
            params.put("auth_code",payCode);
        }
        if (isCashPay){
            params.put("pay_type","CASH");
        }else if (isWxPay){
            params.put("pay_type","WECHAT");
        }else {
            params.put("pay_type","ALI");
        }
        requestHttpData(Constants.Urls.URL_POST_BUY_MERCHANT_CARD,REQUEST_CODE_BUY_MERCHANT_CARD, FProtocol.HttpMethod.POST,params);
    }

}
