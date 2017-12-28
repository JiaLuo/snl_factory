package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.MerchantCardListEntities;
import com.shinaier.laundry.snlfactory.network.entity.OfflineAddVisitorEntity;
import com.shinaier.laundry.snlfactory.network.entity.StoreInfoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.view.OnDateSetListener;
import com.shinaier.laundry.snlfactory.offlinecash.view.TimePickerDialog;
import com.shinaier.laundry.snlfactory.offlinecash.view.Type;
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
    private static final int REQUEST_CODE_ADD_MEMBER = 0x2;
    private static final int REQUEST_CODE_BUY_MERCHANT_CARD = 0x3;
    public static final int SCAN_SUCCESS = 0x5;
    private static final int REQUEST_CODE_MERCHANT_INFO = 0x6;
    private static final int REQUEST_CODE_RECHARGE_PRINT = 0x7;

    private static final int IS_SUCCESS = 0x8;
    private static final int IS_FAIL = 0x9;
    private static final int REQUEST_CODE_MERCHANT_CARD_LIST = 0x10;
    private static final int REQUEST_CODE_ADD_MEMBER_PERSONAL = 0x11;

    //卡号暂时不要
//    @ViewInject(R.id.tv_member_num)
//    private TextView tvMemberNum;
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
    //    @ViewInject(R.id.member_normal_discount) 普通折扣暂时不要
//    private TextView memberNormalDiscount;
    @ViewInject(R.id.member_gold_discount)
    private TextView memberGoldDiscount;
    @ViewInject(R.id.member_diamond_discount)
    private TextView memberDiamondDiscount;
    @ViewInject(R.id.member_name)
    private TextView memberName;
    @ViewInject(R.id.rl_member_sex)
    private RelativeLayout rlMemberSex;
    @ViewInject(R.id.rl_member_birth)
    private RelativeLayout rlMemberBirth;
    @ViewInject(R.id.rl_member_discount)
    private RelativeLayout rlMemberDiscount;
    @ViewInject(R.id.rl_member_recharge_money)
    private RelativeLayout rlMemberRechargeMoney;
    @ViewInject(R.id.ll_member_recharge_type)
    private LinearLayout llMemberRechargeType;
    @ViewInject(R.id.ll_member_recharge_normal)
    private LinearLayout llMemberRechargeNormal;
    @ViewInject(R.id.ll_member_recharge_gold)
    private LinearLayout llMemberRechargeGold;
    @ViewInject(R.id.ll_member_recharge_diamonds)
    private LinearLayout llMemberRechargeDiamonds;
    @ViewInject(R.id.ed_input_discout_num)
    private EditText edInputDiscoutNum;
    @ViewInject(R.id.ed_input_money_num)
    private EditText edInputMoneyNum;


    private String phoneNum; //从上一界面传来的电话号码
    private String ucode; //获取的会员编号
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    TimePickerDialog mDialogYearMonthDay;
    private String memberBirth;
    private boolean isAliPay,isWxPay,isCashPay,isNineDiscount,isEightDiscount,isNormalDiscount,isClickTime,isMan,isWoman = false;
    private String user;
    // 自定dialog
    private CommonDialog dialog;
    private int memberType; //会员类型 1 是个人会员 2 是企业会员
    private MerchantCardListEntities merchantCardListEntities;
    private String inputMemberName;
    private String inputMemberAddress;
    private String inputMemberMark;
    private String inputDiscountNum;
    private String inputMoneyNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_member_add_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
//        memberCode = intent.getStringExtra("member_code"); //从会员管理页面传下来会员卡号
        memberType = intent.getIntExtra("member_type", 0);//从会员管理页面传下来 1 是个人会员 2是企业会员
        phoneNum = intent.getStringExtra("phone_num"); //从会员管理页面传下来手机号
        if (memberType == 1 || memberType == 0){ // 默认没有点击企业会员还是个人会员
            loadDiscountData();
        }
        initView();
    }

    private void loadDiscountData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MERCHANT_CARD_LIST,REQUEST_CODE_MERCHANT_CARD_LIST,
                FProtocol.HttpMethod.POST,params);
    }

    private void loadMemberNumData() {
//        IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
//        params.put("token", UserCenter.getToken(this));
//        requestHttpData(Constants.Urls.URL_POST_ADD_MEMBER_NUM,REQUEST_CODE_ADD_MEMBER_NUM,
//                                    FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("新增会员");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        if (memberType == 2){ //如果是企业会员没有折扣接口 直接隐藏
            setLoadingStatus(LoadingStatus.GONE);
        }
//        tvMemberNum.setText(memberCode);
        if (memberType == 1 || memberType == 0){   //会员信息变更：进去没有“变更”按钮 文档错误的问题修改
            memberName.setText("姓名");
            rlMemberSex.setVisibility(View.VISIBLE); //个人会员  性别的行显示
            sexMan.setOnClickListener(this);
            sexWomen.setOnClickListener(this);
            rlMemberBirth.setVisibility(View.VISIBLE); //个人会员  生日的行显示
            tvMemberBirth.setOnClickListener(this);
            rlMemberDiscount.setVisibility(View.GONE); // 企业会员  折扣行隐藏
            rlMemberRechargeMoney.setVisibility(View.GONE);//企业会员  充值金额隐藏
            llMemberRechargeType.setVisibility(View.VISIBLE); //会员类型行显示
            // TODO: 2017/12/27 特殊会员这里暂时删除 xml文件还没有删除
            llMemberRechargeNormal.setVisibility(View.GONE); //普通会员充值行显示

            llMemberRechargeGold.setVisibility(View.VISIBLE); //黄金会员充值行显示
            llMemberRechargeDiamonds.setVisibility(View.VISIBLE); //钻石会员充值行显示
            ivMemberNormalDiscount.setOnClickListener(this);
            ivMemberNineDiscount.setOnClickListener(this);
            ivMemberEightDiscount.setOnClickListener(this);
        }else if (memberType == 2){
            memberName.setText("企业名称");
            rlMemberSex.setVisibility(View.GONE);//个人会员  性别的行隐藏
            rlMemberBirth.setVisibility(View.GONE);//个人会员  生日的行隐藏
            rlMemberDiscount.setVisibility(View.VISIBLE);// 企业会员  折扣行显示
            rlMemberRechargeMoney.setVisibility(View.VISIBLE);//企业会员  充值金额显示
            llMemberRechargeType.setVisibility(View.GONE);//会员类型行隐藏
            llMemberRechargeNormal.setVisibility(View.GONE); //普通会员充值行隐藏
            llMemberRechargeGold.setVisibility(View.GONE); //黄金会员充值行隐藏
            llMemberRechargeDiamonds.setVisibility(View.GONE); //钻石会员充值行隐藏
        }else {
            memberName.setText("");
        }
        tvMemberMobile.setText(phoneNum);
        confirmPay.setOnClickListener(this);
        cashPaySelector.setOnClickListener(this);
        wxPaySelector.setOnClickListener(this);
        aliPaySelector.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        if (!isMan && !isWoman){
            sexWomen.setSelected(true);
        }
        //初始化时间选择器
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(this)
                .build();

        dialog = new CommonDialog(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
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
//                        if (storeInfoEntity.getCards() != null && storeInfoEntity.getCards().size() > 0){
//                            //设置普通会员信息
//                            tvMemberNormal.setText(storeInfoEntity.getCards().get(0).getCardName());
//                            if (storeInfoEntity.getCards().get(0).getDiscount().equals("10.0")){
//                                memberNormalDiscount.setText("无折扣");
//                            }else {
//                                memberNormalDiscount.setText(storeInfoEntity.getCards().get(0).getDiscount() + "折");
//                            }
//                            memberNormalRechargeMoney.setText("充值" + storeInfoEntity.getCards().get(0).getPrice());
//                            //设置黄金会员信息
//                            tvMemberGold.setText(storeInfoEntity.getCards().get(1).getCardName());
//                            memberGoldDiscount.setText(storeInfoEntity.getCards().get(1).getDiscount() + "折");
//                            memberGoldRechargeMoney.setText("充值" + storeInfoEntity.getCards().get(1).getPrice());
//                            //设置钻石会员信息
//                            tvMemberDiamond.setText(storeInfoEntity.getCards().get(2).getCardName());
//                            memberDiamondDiscount.setText(storeInfoEntity.getCards().get(2).getDiscount() + "折");
//                            memberDiamondRechargeMoney.setText("充值" + storeInfoEntity.getCards().get(2).getPrice());
//                        }
                    }
                }
                break;

            case REQUEST_CODE_MERCHANT_CARD_LIST:
                if (data != null){
                    merchantCardListEntities = Parsers.getMerchantCardListEntities(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if (merchantCardListEntities != null){
                        if (merchantCardListEntities.getCode() == 0){
                            if (merchantCardListEntities.getResult() != null && merchantCardListEntities.getResult().size() > 0){
                                //设置普通会员信息
//                            tvMemberNormal.setText(storeInfoEntity.getCards().get(0).getCardName());
//                            if (storeInfoEntity.getCards().get(0).getDiscount().equals("10.0")){
//                                memberNormalDiscount.setText("无折扣");
//                            }else {
//                                memberNormalDiscount.setText(storeInfoEntity.getCards().get(0).getDiscount() + "折");
//                            }
//                            memberNormalRechargeMoney.setText("充值" + storeInfoEntity.getCards().get(0).getPrice());
                                //设置黄金会员信息
                                tvMemberGold.setText(merchantCardListEntities.getResult().get(0).getCardName());
                                memberGoldDiscount.setText(merchantCardListEntities.getResult().get(0).getDiscount() + "折");
                                memberGoldRechargeMoney.setText("充值" + merchantCardListEntities.getResult().get(0).getPrice());
                                //设置钻石会员信息
                                tvMemberDiamond.setText(merchantCardListEntities.getResult().get(1).getCardName());
                                memberDiamondDiscount.setText(merchantCardListEntities.getResult().get(1).getDiscount() + "折");
                                memberDiamondRechargeMoney.setText("充值" + merchantCardListEntities.getResult().get(1).getPrice());
                            }
                        }else {
                            ToastUtil.shortShow(this, merchantCardListEntities.getMsg());
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm_pay:
                inputMemberName = edInputMemberName.getText().toString();
                inputMemberAddress = edInputMemberAddress.getText().toString();
                inputMemberMark = edInputMemberMark.getText().toString();
                inputDiscountNum = edInputDiscoutNum.getText().toString();
                inputMoneyNum = edInputMoneyNum.getText().toString();


                if (memberType == 1 || memberType == 0){
                    if (!TextUtils.isEmpty(inputMemberName)){
                        if (isNormalDiscount || isNineDiscount || isEightDiscount){
                            if (isCashPay || isWxPay || isAliPay){
                                if (isCashPay){
                                    confirmAdd("");
                                }else {
                                    startActivityForResult(new Intent(this,ScanActivity.class),SCAN_SUCCESS);
                                }
                            }else {
                                ToastUtil.shortShow(this,"请选择支付方式");
                            }
                        }else {
                            ToastUtil.shortShow(this,"请选择充值会员类型");
                        }
                    }else {
                        ToastUtil.shortShow(this,"请输入名字");
                    }
                }else {
                    if (!TextUtils.isEmpty(inputMemberName)){
                        if (!TextUtils.isEmpty(inputDiscountNum)){
                            double dDiscountNum = Double.parseDouble(inputDiscountNum);
                            if (dDiscountNum > 1 && dDiscountNum < 10){
                                if (!TextUtils.isEmpty(inputMoneyNum)){
                                    if (isCashPay || isWxPay || isAliPay){
                                        if (isCashPay){
                                            confirmAdd("");
                                        }else {
                                            startActivityForResult(new Intent(this,ScanActivity.class),SCAN_SUCCESS);
                                        }
                                    }else {
                                        ToastUtil.shortShow(this,"请选择支付方式");
                                    }
                                }else {
                                    ToastUtil.shortShow(this,"请输入充值金额");
                                }
                            }else {
                                ToastUtil.shortShow(this,"您输入的折扣不正确");
                            }


                        }else {
                            ToastUtil.shortShow(this,"请输入折扣");
                        }

                    }else {
                        ToastUtil.shortShow(this,"请输入名字");
                    }
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
        String url = "";
        dialog.setContent("加载中");
        dialog.show();
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        params.put("umobile",phoneNum);
        params.put("uname",inputMemberName);
        params.put("reg_from","2");
        if (isCashPay){
            params.put("gateway","CASH");
            params.put("auth_code","0");
        }else if (isWxPay){
            params.put("gateway","WechatPay_Pos");
            params.put("auth_code",payCode);
        }else {
            params.put("gateway","Alipay_AopF2F");
            params.put("auth_code",payCode);
        }

        params.put("address",inputMemberAddress);
        params.put("remark",inputMemberMark);
        if (memberType == 1 || memberType == 0){
            if (isMan){
                params.put("sex","1");
            }else if (isWoman){
                params.put("sex","2");
            }else {
                params.put("sex","2");
            }

            if (isClickTime){
                params.put("birthday",memberBirth);
            }else {
                params.put("birthday","1980-01-01");
            }
            if (isNineDiscount){
                params.put("cid",merchantCardListEntities.getResult().get(0).getId());
            }else {
                params.put("cid",merchantCardListEntities.getResult().get(1).getId());
            }
            url = Constants.Urls.URL_POST_ADD_MEMBER_PERSONAL;
//            requestHttpData(Constants.Urls.URL_POST_ADD_MEMBER_PERSONAL,REQUEST_CODE_ADD_MEMBER_PERSONAL, FProtocol.HttpMethod.POST,params);
        }else {
            params.put("cdiscount",inputDiscountNum);
            params.put("amount",inputMoneyNum);
            url = Constants.Urls.URL_POST_ADD_MEMBER_COMPANY;
        }

        requestHttpData(url,REQUEST_CODE_ADD_MEMBER_PERSONAL, FProtocol.HttpMethod.POST,params);
    }

}
