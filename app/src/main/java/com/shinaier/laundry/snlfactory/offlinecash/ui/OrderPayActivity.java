package com.shinaier.laundry.snlfactory.offlinecash.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OfflineOrderPayEntity;
import com.shinaier.laundry.snlfactory.network.entity.OrderPrintEntity;
import com.shinaier.laundry.snlfactory.network.entity.PlatformPaySuccessEntity;
import com.shinaier.laundry.snlfactory.network.entity.VerifyCodeEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.entities.PrintEntity;
import com.shinaier.laundry.snlfactory.offlinecash.view.PlatformPayDialog;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * Created by 张家洛 on 2017/7/20.
 */

public class OrderPayActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_OFFLINE_ORDER_PAY_DETAIL = 0x1;
    private static final int REQUEST_CODE_SEND_CODE = 0x2;
    private static final int REQUEST_CODE_OFFLINE_ORDER_PAY = 0x3;
    public static final int ORDERPAY = 0x4;
    public static final int FROM_ORDER_PAY_ACT = 0x5;
    private static final int REQUEST_CODE_ORDER_PRINT = 0x6;

    @ViewInject(R.id.cash_pay_selector)
    private ImageView cashPaySelector;
    @ViewInject(R.id.wx_pay_selector)
    private ImageView wxPaySelector;
    @ViewInject(R.id.ali_pay_selector)
    private ImageView aliPaySelector;
    @ViewInject(R.id.vip_member_selector)
    private ImageView vipMemberSelector;
    @ViewInject(R.id.platform_member_selector)
    private ImageView platformMemberSelector;
    @ViewInject(R.id.rl_cash_discount)
    private RelativeLayout rlCashDiscount;
    @ViewInject(R.id.rl_wxpay_discount)
    private RelativeLayout rlWxpayDiscount;
    @ViewInject(R.id.rl_alipay_discount)
    private RelativeLayout rlAlipayDiscount;
    @ViewInject(R.id.nine_cash_discount)
    private TextView nineCashDiscount;
    @ViewInject(R.id.eight_cash_discount)
    private TextView eightCashDiscount;
    @ViewInject(R.id.seven_cash_discount)
    private TextView sevenCashDiscount;
    @ViewInject(R.id.special_cash_discount)
    private TextView specialCashDiscount;
    @ViewInject(R.id.rounding_cash_discount)
    private TextView roundingCashDiscount;
    @ViewInject(R.id.nine_wxpay_discount)
    private TextView nineWxpayDiscount;
    @ViewInject(R.id.eight_wxpay_discount)
    private TextView eightWxpayDiscount;
    @ViewInject(R.id.seven_wxpay_discount)
    private TextView sevenWxpayDiscount;
    @ViewInject(R.id.special_wxpay_discount)
    private TextView specialWxpayDiscount;
    @ViewInject(R.id.rounding_wxpay_discount)
    private TextView roundingWxpayDiscount;
    @ViewInject(R.id.nine_alipay_discount)
    private TextView nineAlipayDiscount;
    @ViewInject(R.id.eight_alipay_discount)
    private TextView eightAlipayDiscount;
    @ViewInject(R.id.seven_alipay_discount)
    private TextView sevenAlipayDiscount;
    @ViewInject(R.id.special_alipay_discount)
    private TextView specialAlipayDiscount;
    @ViewInject(R.id.rounding_alipay_discount)
    private TextView roundingAlipayDiscount;
    @ViewInject(R.id.tv_order_sum)
    private TextView tvOrderSum; // 订单金额
    @ViewInject(R.id.tv_brand_discount_num)
    private TextView tvBrandDiscountNum; //品项折扣数
    @ViewInject(R.id.tv_net_receipts)
    private TextView tvNetReceipts; //实收
    @ViewInject(R.id.ll_go_pay)
    private LinearLayout llGoPay; // 购买会员卡
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.platform_balance_num)
    private TextView platformBalanceNum;
    @ViewInject(R.id.rl_vip_member)
    private RelativeLayout rlVipMember;
    @ViewInject(R.id.vip_balance_num)
    private TextView vipBalanceNum;
    @ViewInject(R.id.vip_member_card_line)
    private View vipMemberCardLine;
    @ViewInject(R.id.rebates_balance_platformPay) //平台会员卡余额不足的情况
    private TextView rebatesBalancePlatformPay;
    @ViewInject(R.id.confirm_pay)
    private TextView confirmPay;


    private CountDownTimer countDownTimer;

    private CollectClothesDialog collectClothesDialog;
//    private double ordernum = 124;
    private double brandDiscountNum;
    private double netReceipts;
    private OfflineOrderPayEntity offlineOrderPayEntity;
    private double totalAmount;
    private double discountValue;
    private double cardSum;
    private double afterDiscount;
    private TextView tvSendCode;
    private CommonDialog dialog;
    private String verifyCodeEntityData;
    private String orderId;
    private PrintEntity printEntity = new PrintEntity();

    private Handler handler = new Handler(){

        private String inputCode;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    countDownTimer = (CountDownTimer) msg.obj;
                    countDownTimer.start();
                    sendCode(offlineOrderPayEntity.getDatas().getOfflineOrderPayPlatformCard().getMobileNumber());
                    break;
                case 2:
                    platformPayDialog.dismiss();
                    platformMemberSelector.setSelected(false);
                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                    break;
                case 3:
                    inputCode = (String)msg.obj;
                    if (!TextUtils.isEmpty(inputCode)){
                        if (MD5(inputCode).equals(verifyCodeEntityData)){
//                            orderPay(formatMoney(totalAmount));
                            confirmOrderPay("",formatMoney(totalAmount));
                        }else {
                            ToastUtil.shortShow(OrderPayActivity.this,"请填写正确的验证码");
                        }
                    }else {
                        ToastUtil.shortShow(OrderPayActivity.this,"请填写验证码");
                    }
                    break;
                case 4:
                    merchantPayDialog.dismiss();
                    vipMemberSelector.setSelected(false);
                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                    break;
                case 5:
                    countDownTimer = (CountDownTimer) msg.obj;
                    countDownTimer.start();
                    sendCode(offlineOrderPayEntity.getDatas().getOfflineOrderPayMerchantCard().getMobileNumber());
                    break;
                case 6:
                    inputCode = (String)msg.obj;
                    if (!TextUtils.isEmpty(inputCode)){
                        if (MD5(inputCode).equals(verifyCodeEntityData)){
//                            orderPay(formatMoney(totalAmount - afterDiscount));
                            confirmOrderPay("",formatMoney(totalAmount - afterDiscount));
                        }else {
                            ToastUtil.shortShow(OrderPayActivity.this,"请填写正确的验证码");
                        }
                    }else {
                        ToastUtil.shortShow(OrderPayActivity.this,"请填写验证码");
                    }
                    break;
            }
        }
    };
    private PlatformPayDialog platformPayDialog;
    private String cardDiscount;
    private PlatformPayDialog merchantPayDialog;
    private double merchantDiscount;
    private boolean isClickMerchant,isCLickPlatform = false;
    private int extraFrom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay_act);
        ViewInjectUtils.inject(this);
        ExitManager.instance.addOfflineCollectActivity(this);
        orderId = getIntent().getStringExtra("order_id");
        extraFrom = getIntent().getIntExtra("extra_from", 0);
//        loadData(orderId);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(orderId);
    }

    private void loadData(String orderId) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("order_id",orderId);
        requestHttpData(Constants.Urls.URL_POST_OFFLINE_ORDER_PAY_DETAIL,REQUEST_CODE_OFFLINE_ORDER_PAY_DETAIL, FProtocol.HttpMethod.POST,params);
    }


    private void initView() {
        setCenterTitle("订单支付");
        leftButton.setOnClickListener(this);
        cashPaySelector.setOnClickListener(this);
        wxPaySelector.setOnClickListener(this);
        aliPaySelector.setOnClickListener(this);
        vipMemberSelector.setOnClickListener(this);
        nineCashDiscount.setOnClickListener(this);
        eightCashDiscount.setOnClickListener(this);
        sevenCashDiscount.setOnClickListener(this);
        specialCashDiscount.setOnClickListener(this);
        roundingCashDiscount.setOnClickListener(this);
        nineWxpayDiscount.setOnClickListener(this);
        eightWxpayDiscount.setOnClickListener(this);
        sevenWxpayDiscount.setOnClickListener(this);
        specialWxpayDiscount.setOnClickListener(this);
        roundingWxpayDiscount.setOnClickListener(this);
        nineAlipayDiscount.setOnClickListener(this);
        eightAlipayDiscount.setOnClickListener(this);
        sevenAlipayDiscount.setOnClickListener(this);
        specialAlipayDiscount.setOnClickListener(this);
        roundingAlipayDiscount.setOnClickListener(this);
        confirmPay.setOnClickListener(this);

        dialog = new CommonDialog(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_OFFLINE_ORDER_PAY_DETAIL:
                if (data != null){
                    offlineOrderPayEntity = Parsers.getOfflineOrderPayEntity(data);
                    if (offlineOrderPayEntity != null){
                        if (offlineOrderPayEntity.getRetcode() == 0){
                            if (offlineOrderPayEntity.getDatas() != null){
                                if (offlineOrderPayEntity.getDatas().getOfflineOrderPayOrder() != null){
                                    //设置应收 品项折扣 实收
                                    totalAmount = offlineOrderPayEntity.getDatas().getOfflineOrderPayOrder().getTotalAmount();
//                                    totalAmount = 11.20;
//                                    offlineOrderPayEntity.getDatas().getOfflineOrderPayPlatformCard().setCardSum(7.0);
//                                    offlineOrderPayEntity.getDatas().getOfflineOrderPayPlatformCard().setCardExist(0);
                                    cardSum = offlineOrderPayEntity.getDatas().getOfflineOrderPayPlatformCard().getCardSum();
                                    tvOrderSum.setText("￥" + formatMoney(totalAmount));
                                    tvBrandDiscountNum.setText("￥" + offlineOrderPayEntity.getDatas().getOfflineOrderPayOrder().getReducePrice());
                                    tvNetReceipts.setText("￥" +  formatMoney(totalAmount));
                                }

                                if (offlineOrderPayEntity.getDatas().getOfflineOrderPayPlatformCard() != null){
                                    if (offlineOrderPayEntity.getDatas().getOfflineOrderPayPlatformCard().getCardExist() == 1){
                                        cardDiscount = offlineOrderPayEntity.getDatas().getOfflineOrderPayPlatformCard().getCardDiscount();
                                        if (cardDiscount.equals("7.0")){
                                            discountValue = 0.7;
                                        }else if (cardDiscount.equals("8.0")){
                                            discountValue = 0.8;
                                        }else {
                                            discountValue = 0.0;
                                        }
                                        llGoPay.setVisibility(View.GONE);
                                        platformMemberSelector.setVisibility(View.VISIBLE);
                                        platformBalanceNum.setText("可用余额：￥" + cardSum);

//                                        platformMemberSelector.setOnClickListener(this);
                                        llGoPay.setOnClickListener(null);

                                    }else {
                                        llGoPay.setVisibility(View.VISIBLE);
                                        platformMemberSelector.setVisibility(View.GONE);
                                        platformBalanceNum.setText("可用余额：￥0.00");
//                                        platformMemberSelector.setOnClickListener(null);
                                        llGoPay.setOnClickListener(this);
                                    }
                                }

                                if (offlineOrderPayEntity.getDatas().getOfflineOrderPayMerchantCard() != null){
                                    if (offlineOrderPayEntity.getDatas().getOfflineOrderPayMerchantCard().getCardExist() == 1){ //如果有专店卡
                                        rlVipMember.setVisibility(View.VISIBLE); //就显示
                                        vipMemberCardLine.setVisibility(View.VISIBLE);// 专店卡下面的分割线
                                        vipBalanceNum.setText("可用余额：￥" + offlineOrderPayEntity.getDatas().getOfflineOrderPayMerchantCard().getBalance());
                                        merchantDiscount = offlineOrderPayEntity.getDatas().getOfflineOrderPayMerchantCard().getDiscount();
                                    }else {
                                        rlVipMember.setVisibility(View.GONE); //否则隐藏
                                        vipMemberCardLine.setVisibility(View.GONE);
                                    }
                                }
                                afterDiscount = totalAmount - totalAmount * discountValue; //品项折扣的钱。
                                //判断支付金额是否大于卡余额
                                if (offlineOrderPayEntity.getDatas().getOfflineOrderPayPlatformCard().getCardExist() == 1){
                                    LogUtil.e("zhang","cardSum = " + cardSum);
                                    LogUtil.e("zhang","totalAmount * discountValue  = " + totalAmount * discountValue );
                                    if (cardSum - (totalAmount * discountValue) > cardSum){
                                        platformMemberSelector.setOnClickListener(null);
                                        rebatesBalancePlatformPay.setVisibility(View.VISIBLE);
                                    }else {
                                        platformMemberSelector.setOnClickListener(this);
                                        rebatesBalancePlatformPay.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineOrderPayEntity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_SEND_CODE:
                if (data != null){
                    VerifyCodeEntity verifyCodeEntity = Parsers.getVerifyCodeEntity(data);
                    if (verifyCodeEntity != null){
                        if (verifyCodeEntity.getRetcode() == 0){
                            if (verifyCodeEntity.getData() != null){
//                                countDownTimer.start();
                                verifyCodeEntityData = verifyCodeEntity.getData();
                            }
                        }else {
                            ToastUtil.shortShow(this,verifyCodeEntity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_OFFLINE_ORDER_PAY:
                if (data != null){
                    PlatformPaySuccessEntity platformPaySuccessEntity = Parsers.getPlatformPaySuccessEntity(data);
                    if (platformPaySuccessEntity != null){
                        if (platformPaySuccessEntity.getRetcode() == 0){
                            //这里返回要好好检查一下
//                            finish();

                            // TODO: 2017/8/23  忘了这里价格判断是干嘛的了
//                            if (platformPaySuccessEntity.getDatas().getCode() != null){
//                                if (platformPaySuccessEntity.getDatas().getCode().equals("success")){
//                                    orderPrint(orderId);
//                                }
//                            }
                            if (extraFrom == TakeClothesActivity.FROM_TAKE_CLOTHES){ //如果从取衣界面进来的，不打印
                                ExitManager.instance.exitOfflineCollectActivity();
                            }else {
                                orderPrint(orderId);
                            }
//                            LogUtil.e("zhang","平台会员卡走到这里了。");

                        }else {
                            ToastUtil.shortShow(this,platformPaySuccessEntity.getStatus());
                        }
                    }
                }
                break;

            case REQUEST_CODE_ORDER_PRINT:
                if (data != null){
                    OrderPrintEntity orderPrintEntity = Parsers.getOrderPrintEntity(data);
                    if (orderPrintEntity != null){
                        if (orderPrintEntity.getRetcode() == 0){
                            if (orderPrintEntity.getDatas() != null){
                                if (orderPrintEntity.getDatas().getInfo() != null &&
                                        orderPrintEntity.getDatas().getItems() != null &&
                                        orderPrintEntity.getDatas().getItems().size() > 0){

                                    setOrderPrint(orderPrintEntity);
                                    //链接打印机 打印条子
                                    Intent intent = new Intent(this,PrintActivity.class);
                                    intent.putExtra("print_entity",printEntity);
                                    intent.putExtra("extra_from",FROM_ORDER_PAY_ACT);
                                    startActivity(intent);
                                    if (dialog.isShowing()){
                                        dialog.dismiss();
                                    }
                                    ExitManager.instance.exitOfflineCollectActivity();
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this,orderPrintEntity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    private void setOrderPrint(OrderPrintEntity orderPrintEntity) {
        PrintEntity.PayOrderPrintEntity payOrderPrintEntity = printEntity.new PayOrderPrintEntity();
        PrintEntity.PayOrderPrintEntity.PayOrderPrintInfo payOrderPrintInfo = payOrderPrintEntity.new PayOrderPrintInfo();
        List<PrintEntity.PayOrderPrintEntity.PayOrderPrintItems> payOrderPrintItemses = new ArrayList<>();
        PrintEntity.PayOrderPrintEntity.PayOrderPrintItems payOrderPrintItems = null;


        payOrderPrintInfo.setOrdersn(orderPrintEntity.getDatas().getInfo().getOrdersn());
        payOrderPrintInfo.setMobile(orderPrintEntity.getDatas().getInfo().getMobile());
        payOrderPrintInfo.setPieceNum(orderPrintEntity.getDatas().getInfo().getPieceNum());
        payOrderPrintInfo.setHedging(orderPrintEntity.getDatas().getInfo().getHedging());
        payOrderPrintInfo.setPayAmount(orderPrintEntity.getDatas().getInfo().getPayAmount());
        payOrderPrintInfo.setReducePrice(orderPrintEntity.getDatas().getInfo().getReducePrice());
        payOrderPrintInfo.setPayChannel(orderPrintEntity.getDatas().getInfo().getPayChannel());
        payOrderPrintInfo.setUserId(orderPrintEntity.getDatas().getInfo().getUserid());
        payOrderPrintInfo.setAddress(orderPrintEntity.getDatas().getInfo().getAddress());
        payOrderPrintInfo.setPhone(orderPrintEntity.getDatas().getInfo().getPhone());
        payOrderPrintInfo.setMid(orderPrintEntity.getDatas().getInfo().getMid());
        payOrderPrintInfo.setClerkName(orderPrintEntity.getDatas().getInfo().getClerkName());
        payOrderPrintInfo.setPayState(orderPrintEntity.getDatas().getInfo().getPayState());
        payOrderPrintInfo.setAmount(orderPrintEntity.getDatas().getInfo().getAmount());
        payOrderPrintInfo.setCardNumber(orderPrintEntity.getDatas().getInfo().getCardNumber());
        payOrderPrintInfo.setCardBalance(orderPrintEntity.getDatas().getInfo().getCardBalance());

        List<OrderPrintEntity.OrderPrintDatas.OrderPrintItems> items = orderPrintEntity.getDatas().getItems();
        for (int i = 0; i < items.size(); i++) {
            payOrderPrintItems = payOrderPrintEntity.new PayOrderPrintItems();
            payOrderPrintItems.setItemNote(items.get(i).getItemNote());
            payOrderPrintItems.setName(items.get(i).getName());
            payOrderPrintItems.setPrice(items.get(i).getPrice());
            payOrderPrintItems.setColor(items.get(i).getColor());
            payOrderPrintItemses.add(payOrderPrintItems);
        }

        printEntity.setPayOrderPrintEntity(payOrderPrintEntity);
        printEntity.getPayOrderPrintEntity().setPayOrderPrintInfo(payOrderPrintInfo);
        printEntity.getPayOrderPrintEntity().setPayOrderPrintItems(payOrderPrintItemses);

    }

    private void orderPrint(String orderId) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("order_id",orderId);
        params.put("clerk_id", UserCenter.getUid(this));
        params.put("token",UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_ORDER_PRINT,REQUEST_CODE_ORDER_PRINT, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否需要打印订单？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExitManager.instance.exitOfflineCollectActivity();
            }
        });
        builder.setPositiveButton("确定",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vip_member_selector:
                if(vipMemberSelector.isSelected()){
                    vipMemberSelector.setSelected(false);
                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount- 0.00));
                }else {
                    if(platformMemberSelector.isSelected() ||cashPaySelector.isSelected() ||wxPaySelector.isSelected() ||aliPaySelector.isSelected()){
                        platformMemberSelector.setSelected(false);
                        cashPaySelector.setSelected(false);
                        wxPaySelector.setSelected(false);
                        aliPaySelector.setSelected(false);
                        rlCashDiscount.setVisibility(View.GONE);
                        rlWxpayDiscount.setVisibility(View.GONE);
                        rlAlipayDiscount.setVisibility(View.GONE);
                        anotherSetFalse();
                        tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                    }
                    merchantPayDialog = new PlatformPayDialog(this, R.style.DialogTheme,handler,2);
                    merchantPayDialog.setview();
                    merchantPayDialog.setCanceledOnTouchOutside(false);
                    if(!merchantPayDialog.isShowing()){
                        vipMemberSelector.setSelected(true);
                        isClickMerchant = true;
                        isCLickPlatform = false;
                        merchantPayDialog.show();
                        //计算折扣
                        if (merchantDiscount == 10){
                            afterDiscount = totalAmount - totalAmount * 1;
                        }else {
                            afterDiscount = totalAmount - totalAmount * merchantDiscount;
                        }
                        tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount - afterDiscount));
                    }
                }
                break;
            case R.id.platform_member_selector:
                if(platformMemberSelector.isSelected()){
                    platformMemberSelector.setSelected(false);
                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                }else {
                    if(vipMemberSelector.isSelected() ||cashPaySelector.isSelected() ||wxPaySelector.isSelected() ||aliPaySelector.isSelected()){
                        vipMemberSelector.setSelected(false);
                        cashPaySelector.setSelected(false);
                        wxPaySelector.setSelected(false);
                        aliPaySelector.setSelected(false);
                        rlCashDiscount.setVisibility(View.GONE);
                        rlWxpayDiscount.setVisibility(View.GONE);
                        rlAlipayDiscount.setVisibility(View.GONE);
                        anotherSetFalse();
                        tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                    }
                    platformPayDialog = new PlatformPayDialog(this, R.style.DialogTheme,handler,1);
                    platformPayDialog.setview();
                    platformPayDialog.setCanceledOnTouchOutside(false);
                    if(!platformPayDialog.isShowing()){
                        platformMemberSelector.setSelected(true);
                        isClickMerchant = false;
                        isCLickPlatform = true;
                        platformPayDialog.show();
                        //计算折扣
                        afterDiscount = totalAmount - totalAmount * discountValue;
                        tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount - afterDiscount));
                    }
                }
                break;
            case R.id.cash_pay_selector:
                if(cashPaySelector.isSelected()){
                    cashPaySelector.setSelected(false);
                    rlCashDiscount.setVisibility(View.GONE);
                    anotherSetFalse();
                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                }else {
                    cashPaySelector.setSelected(true);
                    rlCashDiscount.setVisibility(View.VISIBLE);
                    if(wxPaySelector.isSelected() || aliPaySelector.isSelected() ||platformMemberSelector.isSelected() ||vipMemberSelector.isSelected()){
                        wxPaySelector.setSelected(false);
                        rlWxpayDiscount.setVisibility(View.GONE);
                        aliPaySelector.setSelected(false);
                        rlAlipayDiscount.setVisibility(View.GONE);
                        platformMemberSelector.setSelected(false);
                        vipMemberSelector.setSelected(false);
                        tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                    }
                }
                break;
            case R.id.wx_pay_selector:
                if(wxPaySelector.isSelected()){
                    wxPaySelector.setSelected(false);
                    rlWxpayDiscount.setVisibility(View.GONE);
                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                }else {
                    wxPaySelector.setSelected(true);
                    rlWxpayDiscount.setVisibility(View.VISIBLE);
                    if (aliPaySelector.isSelected() || wxPaySelector.isSelected()||platformMemberSelector.isSelected() ||vipMemberSelector.isSelected()){
                        cashPaySelector.setSelected(false);
                        rlCashDiscount.setVisibility(View.GONE);
                        aliPaySelector.setSelected(false);
                        rlAlipayDiscount.setVisibility(View.GONE);
                        platformMemberSelector.setSelected(false);
                        vipMemberSelector.setSelected(false);
                        anotherSetFalse();
                        tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                    }
                }
                break;
            case R.id.ali_pay_selector:
                if(aliPaySelector.isSelected()){
                    aliPaySelector.setSelected(false);
                    rlAlipayDiscount.setVisibility(View.GONE);
                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                }else {
                    aliPaySelector.setSelected(true);
                    rlAlipayDiscount.setVisibility(View.VISIBLE);
                    if (cashPaySelector.isSelected() || wxPaySelector.isSelected()||platformMemberSelector.isSelected() ||vipMemberSelector.isSelected()){
                        cashPaySelector.setSelected(false);
                        rlCashDiscount.setVisibility(View.GONE);
                        wxPaySelector.setSelected(false);
                        rlWxpayDiscount.setVisibility(View.GONE);
                        platformMemberSelector.setSelected(false);
                        vipMemberSelector.setSelected(false);
                        anotherSetFalse();
                        tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));
                    }
                }
                break;
            case R.id.nine_cash_discount:
                if(nineCashDiscount.isSelected()){
                    nineCashDiscount.setSelected(false);
                    if (roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(totalAmount))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
//                    tvNetReceipts.setText("￥" + formatMoney(totalAmount));
//                    tvBrandDiscountNum.setText("-￥0.00");
                }else {
                    nineCashDiscount.setSelected(true);
                    //计算折扣
                     afterDiscount = totalAmount - totalAmount * 0.9;
                    netReceipts = totalAmount - afterDiscount;
                    if(roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(netReceipts))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }

                    if(eightCashDiscount.isSelected() || sevenCashDiscount.isSelected() || specialCashDiscount.isSelected()){
                        eightCashDiscount.setSelected(false);
                        sevenCashDiscount.setSelected(false);
                        specialCashDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.eight_cash_discount:
                if(eightCashDiscount.isSelected()){
                    eightCashDiscount.setSelected(false);
                    if (roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(totalAmount))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
//                    tvNetReceipts.setText("￥" + formatMoney(totalAmount));
//                    tvBrandDiscountNum.setText("-￥0.00");
                }else {
                    eightCashDiscount.setSelected(true);
                    //计算折扣
                    afterDiscount = totalAmount - totalAmount * 0.8;
//                    tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                    netReceipts = totalAmount - afterDiscount;
                    if(roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(netReceipts))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }

                    if(nineCashDiscount.isSelected() || sevenCashDiscount.isSelected() || specialCashDiscount.isSelected()){
                        nineCashDiscount.setSelected(false);
                        sevenCashDiscount.setSelected(false);
                        specialCashDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.seven_cash_discount:
                if(sevenCashDiscount.isSelected()){
                    sevenCashDiscount.setSelected(false);
                    if (roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(totalAmount))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    sevenCashDiscount.setSelected(true);
                    //计算折扣
                    afterDiscount = totalAmount - totalAmount * 0.7;
//                    tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                    netReceipts = totalAmount - afterDiscount;
                    if(roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(netReceipts))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }

                    if(nineCashDiscount.isSelected() || eightCashDiscount.isSelected() || specialCashDiscount.isSelected()){
                        nineCashDiscount.setSelected(false);
                        eightCashDiscount.setSelected(false);
                        specialCashDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.special_cash_discount:
                if(specialCashDiscount.isSelected()){
                    specialCashDiscount.setSelected(false);
                }else {
                    specialCashDiscount.setSelected(true);

                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));

                    if(nineCashDiscount.isSelected() || eightCashDiscount.isSelected() || sevenCashDiscount.isSelected() ||
                            roundingCashDiscount.isSelected()){
                        nineCashDiscount.setSelected(false);
                        eightCashDiscount.setSelected(false);
                        sevenCashDiscount.setSelected(false);
                        roundingCashDiscount.setSelected(false);
                    }
                    View view = View.inflate(this, R.layout.input_special_num,null);
                    final EditText edPhoneOrderNum = (EditText) view.findViewById(R.id.ed_phone_order_num);
                    TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
                    TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm);


                    final CollectClothesDialog collectClothesCashDialog = new CollectClothesDialog(this, R.style.DialogTheme,view);
                    collectClothesCashDialog.setCanceledOnTouchOutside(false);
                    collectClothesCashDialog.show();
                    revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            collectClothesCashDialog.dismiss();
                            specialCashDiscount.setSelected(false);
                        }
                    });
                    revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String inputSpecial = edPhoneOrderNum.getText().toString();
                            if (!TextUtils.isEmpty(inputSpecial)){
                                if (Double.parseDouble(inputSpecial) >= 0){
//                                    LogUtil.e("zhang","StringFilter(inputSpecial) = " + StringFilter(inputSpecial));
                                    confirmOrderPay("",StringFilter(inputSpecial));
                                    collectClothesCashDialog.dismiss();
                                }else {
                                    ToastUtil.shortShow(OrderPayActivity.this,"金额不能小于0");
                                }

                            }else {
                                ToastUtil.shortShow(OrderPayActivity.this,"请输入金额");
                            }
                        }
                    });

                }
                break;
            case R.id.rounding_cash_discount:
                if(roundingCashDiscount.isSelected()){
                    roundingCashDiscount.setSelected(false);
                    if(nineCashDiscount.isSelected() || eightCashDiscount.isSelected() || sevenCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    roundingCashDiscount.setSelected(true);
                    if(nineCashDiscount.isSelected() || eightCashDiscount.isSelected() || sevenCashDiscount.isSelected()){
//                        LogUtil.e("zhang","netReceipts" + netReceipts);
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - Math.floor(netReceipts)));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - Math.floor(totalAmount)));
                    }
                    if(specialCashDiscount.isSelected()){
                        specialCashDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.nine_wxpay_discount:
                if(nineWxpayDiscount.isSelected()){
                    nineWxpayDiscount.setSelected(false);
                    if (roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(totalAmount))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    nineWxpayDiscount.setSelected(true);
                    //计算折扣
                    afterDiscount = totalAmount - totalAmount * 0.9;
//                    tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                    netReceipts = totalAmount - afterDiscount;
                    if(roundingWxpayDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(netReceipts))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }

                    if(eightWxpayDiscount.isSelected() || sevenWxpayDiscount.isSelected() || specialWxpayDiscount.isSelected()){
                        eightWxpayDiscount.setSelected(false);
                        sevenWxpayDiscount.setSelected(false);
                        specialWxpayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.eight_wxpay_discount:
                if(eightWxpayDiscount.isSelected()){
                    eightWxpayDiscount.setSelected(false);
                    if (roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(totalAmount))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    eightWxpayDiscount.setSelected(true);

                    //计算折扣
                    afterDiscount = totalAmount - totalAmount * 0.8;
//                    tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                    netReceipts = totalAmount - afterDiscount;
                    if(roundingWxpayDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(netReceipts))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }
                    if(nineWxpayDiscount.isSelected() || sevenWxpayDiscount.isSelected() || specialWxpayDiscount.isSelected()){
                        nineWxpayDiscount.setSelected(false);
                        sevenWxpayDiscount.setSelected(false);
                        specialWxpayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.seven_wxpay_discount:
                if(sevenWxpayDiscount.isSelected()){
                    sevenWxpayDiscount.setSelected(false);
                    if (roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(totalAmount))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    sevenWxpayDiscount.setSelected(true);
                    //计算折扣
                    afterDiscount = totalAmount - totalAmount * 0.7;
//                    tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                    netReceipts = totalAmount - afterDiscount;
                    if(roundingWxpayDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(netReceipts))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }
                    if(nineWxpayDiscount.isSelected() || eightWxpayDiscount.isSelected() || specialWxpayDiscount.isSelected()){
                        nineWxpayDiscount.setSelected(false);
                        eightWxpayDiscount.setSelected(false);
                        specialWxpayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.special_wxpay_discount:
                if(specialWxpayDiscount.isSelected()){
                    specialWxpayDiscount.setSelected(false);
                }else {
                    specialWxpayDiscount.setSelected(true);

                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));


                    View view = View.inflate(this, R.layout.input_special_num,null);
                    final EditText edPhoneOrderNum = (EditText) view.findViewById(R.id.ed_phone_order_num);
                    TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
                    TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm);
                    collectClothesDialog = new CollectClothesDialog(this, R.style.DialogTheme,view);
                    collectClothesDialog.setCanceledOnTouchOutside(false);
                    collectClothesDialog.show();

                    revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            collectClothesDialog.dismiss();
                            specialWxpayDiscount.setSelected(false);
                        }
                    });
                    revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String inputSpecial = edPhoneOrderNum.getText().toString();
                            if (!TextUtils.isEmpty(inputSpecial)){
                                if (Double.parseDouble(inputSpecial) >= 0){
                                    confirmOrderPay("",StringFilter(inputSpecial));
                                    collectClothesDialog.dismiss();
                                }else {
                                    ToastUtil.shortShow(OrderPayActivity.this,"金额不能小于0");
                                }

                            }else {
                                ToastUtil.shortShow(OrderPayActivity.this,"请输入金额");
                            }
                        }
                    });

                    if(nineWxpayDiscount.isSelected() || eightWxpayDiscount.isSelected() || sevenWxpayDiscount.isSelected() ||
                            roundingWxpayDiscount.isSelected()){
                        nineWxpayDiscount.setSelected(false);
                        eightWxpayDiscount.setSelected(false);
                        sevenWxpayDiscount.setSelected(false);
                        roundingWxpayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.rounding_wxpay_discount:
                if(roundingWxpayDiscount.isSelected()){
                    roundingWxpayDiscount.setSelected(false);
                    if(nineWxpayDiscount.isSelected() || eightWxpayDiscount.isSelected() || sevenWxpayDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    roundingWxpayDiscount.setSelected(true);
                    if(nineWxpayDiscount.isSelected() || eightWxpayDiscount.isSelected() || sevenWxpayDiscount.isSelected()){
//                        LogUtil.e("zhang","netReceipts" + netReceipts);
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - Math.floor(netReceipts)));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - Math.floor(totalAmount)));
                    }
                    if(specialWxpayDiscount.isSelected()){
                        specialWxpayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.nine_alipay_discount:
                if(nineAlipayDiscount.isSelected()){
                    nineAlipayDiscount.setSelected(false);
                    if (roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(totalAmount))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    nineAlipayDiscount.setSelected(true);
                    //计算折扣
                    afterDiscount = totalAmount - totalAmount * 0.9;
//                    tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                    netReceipts = totalAmount - afterDiscount;
                    if(roundingAlipayDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(netReceipts))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }

                    if(eightAlipayDiscount.isSelected() || sevenAlipayDiscount.isSelected() || specialAlipayDiscount.isSelected()){
                        eightAlipayDiscount.setSelected(false);
                        sevenAlipayDiscount.setSelected(false);
                        specialAlipayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.eight_alipay_discount:
                if(eightAlipayDiscount.isSelected()){
                    eightAlipayDiscount.setSelected(false);
                    if (roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(totalAmount))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    eightAlipayDiscount.setSelected(true);
                    //计算折扣
                    afterDiscount = totalAmount - totalAmount * 0.8;
//                    tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                    netReceipts = totalAmount - afterDiscount;
                    if(roundingAlipayDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(netReceipts))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }

                    if(nineAlipayDiscount.isSelected() || sevenAlipayDiscount.isSelected() || specialAlipayDiscount.isSelected()){
                        nineAlipayDiscount.setSelected(false);
                        sevenAlipayDiscount.setSelected(false);
                        specialAlipayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.seven_alipay_discount:
                if(sevenAlipayDiscount.isSelected()){
                    sevenAlipayDiscount.setSelected(false);
                    if (roundingCashDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(totalAmount))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    sevenAlipayDiscount.setSelected(true);

                    //计算折扣
                    afterDiscount = totalAmount - totalAmount * 0.7;
//                    tvBrandDiscountNum.setText("-￥" + formatMoney(afterDiscount));
                    netReceipts = totalAmount - afterDiscount;
                    if(roundingAlipayDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + (formatMoney(totalAmount - Math.floor(netReceipts))));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }

                    if(nineAlipayDiscount.isSelected() || eightAlipayDiscount.isSelected() || specialAlipayDiscount.isSelected()){
                        nineAlipayDiscount.setSelected(false);
                        eightAlipayDiscount.setSelected(false);
                        specialAlipayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.special_alipay_discount:
                if(specialAlipayDiscount.isSelected()){
                    specialAlipayDiscount.setSelected(false);
                }else {
                    specialAlipayDiscount.setSelected(true);

                    tvBrandDiscountNum.setText("-￥" + formatMoney(0.00));
                    tvNetReceipts.setText("￥" + formatMoney(totalAmount - 0.00));

                    if(nineAlipayDiscount.isSelected() || eightAlipayDiscount.isSelected() || sevenAlipayDiscount.isSelected() ||
                            roundingAlipayDiscount.isSelected()){
                        nineAlipayDiscount.setSelected(false);
                        eightAlipayDiscount.setSelected(false);
                        sevenAlipayDiscount.setSelected(false);
                        roundingAlipayDiscount.setSelected(false);
                    }

                    View view = View.inflate(this, R.layout.input_special_num,null);
                    EditText edPhoneOrderNum = (EditText) view.findViewById(R.id.ed_phone_order_num);
                    TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
                    revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            collectClothesDialog.dismiss();
                            specialAlipayDiscount.setSelected(false);
                        }
                    });

                    collectClothesDialog = new CollectClothesDialog(this, R.style.DialogTheme,view);
                    collectClothesDialog.setCanceledOnTouchOutside(false);
                    collectClothesDialog.show();
                }
                break;
            case R.id.rounding_alipay_discount:
                if(roundingAlipayDiscount.isSelected()){
                    roundingAlipayDiscount.setSelected(false);
                    if(nineAlipayDiscount.isSelected() || eightAlipayDiscount.isSelected() || sevenAlipayDiscount.isSelected()){
                        tvNetReceipts.setText("￥" + formatMoney(netReceipts));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - netReceipts));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(totalAmount));
                        tvBrandDiscountNum.setText("-￥0.00");
                    }
                }else {
                    roundingAlipayDiscount.setSelected(true);
                    if(nineAlipayDiscount.isSelected() || eightAlipayDiscount.isSelected() || sevenAlipayDiscount.isSelected()){
//                        LogUtil.e("zhang","netReceipts" + netReceipts);
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(netReceipts)));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - Math.floor(netReceipts)));
                    }else {
                        tvNetReceipts.setText("￥" + formatMoney(Math.floor(totalAmount)));
                        tvBrandDiscountNum.setText("-￥" + formatMoney(totalAmount - Math.floor(totalAmount)));
                    }
                    if(specialAlipayDiscount.isSelected()){
                        specialAlipayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.ll_go_pay:
                Intent intent = new Intent(this,BuyMemberCardActivity.class);
                intent.putExtra("mobile_num",offlineOrderPayEntity.getDatas().getOfflineOrderPayOrder().getMobileNumber());
                intent.putExtra("user_id",offlineOrderPayEntity.getDatas().getOfflineOrderPayOrder().getUserid());
                intent.putExtra("invite_code",offlineOrderPayEntity.getDatas().getOfflineOrderPayOrder().getInviteCode());
                startActivity(intent);
                break;
            case R.id.left_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("是否需要打印订单？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExitManager.instance.exitOfflineCollectActivity();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;
            case R.id.confirm_pay:
                if (cashPaySelector.isSelected()){
                    LogUtil.e("zhang","走到这里了。");
                    dialog.setContent("加载中");
                    dialog.show();
                    cashPayConfirm();

                }else if (wxPaySelector.isSelected() || aliPaySelector.isSelected()){
                    startActivityForResult(new Intent(this,ScanActivity.class), ORDERPAY);
                }

                break;
        }
    }


    private void cashPayConfirm() {
        String orderPayAmount = tvNetReceipts.getText().toString();
        String result = orderPayAmount.substring(1,orderPayAmount.length());
        confirmOrderPay("",result);
    }


    private void sendCode(String mobileNum) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        params.put("mobile",mobileNum);
        requestHttpData(Constants.Urls.URL_POST_SEND_CODE,REQUEST_CODE_SEND_CODE, FProtocol.HttpMethod.POST,params);
    }

    /**
     * 把其他所有全置为没有选中
     */
    private void anotherSetFalse() {
        nineCashDiscount.setSelected(false);
        eightCashDiscount.setSelected(false);
        sevenCashDiscount.setSelected(false);
        specialCashDiscount.setSelected(false);
        roundingCashDiscount.setSelected(false);
        nineWxpayDiscount.setSelected(false);
        eightWxpayDiscount.setSelected(false);
        sevenWxpayDiscount.setSelected(false);
        specialWxpayDiscount.setSelected(false);
        roundingWxpayDiscount.setSelected(false);
        nineAlipayDiscount.setSelected(false);
        eightAlipayDiscount.setSelected(false);
        sevenAlipayDiscount.setSelected(false);
        specialAlipayDiscount.setSelected(false);
        roundingAlipayDiscount.setSelected(false);
    }

    public String formatMoney(double money){
        DecimalFormat df = new DecimalFormat("###0.00");
        return df.format(money);
    }

    public static String MD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(data.getBytes());
            return bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
        }
        return data;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ORDERPAY:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String payCode = data.getStringExtra("pay_code");
                        dialog.setContent("加载中");
                        dialog.show();

                        String orderPayAmount = tvNetReceipts.getText().toString();
                        String result = orderPayAmount.substring(1,orderPayAmount.length());
//                        confirmOrderPay("",result);
                        LogUtil.e("zhang","orderPayAmount = " + orderPayAmount);
                        confirmOrderPay(payCode,"0.01");


                    }
                }
                break;
        }
    }

    private void confirmOrderPay(String payCode, String amount) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        if (!TextUtils.isEmpty(payCode)){
            params.put("auth_code",payCode);
        }
        params.put("order_id",orderId);
        params.put("amount",amount);
        if (platformMemberSelector.isSelected()){
            params.put("discount",cardDiscount);
            params.put("type","MEMBER_CARD");
            params.put("card_id",offlineOrderPayEntity.getDatas().getOfflineOrderPayPlatformCard().getId());
        }else if (vipMemberSelector.isSelected()){
            params.put("discount",merchantDiscount + "");
            params.put("type","MERCHANT_CARD");
        } else if (nineCashDiscount.isSelected() || nineWxpayDiscount.isSelected() ||
                nineAlipayDiscount.isSelected()){
            params.put("discount","9");
        }else if (eightCashDiscount.isSelected() || eightWxpayDiscount.isSelected()
                || eightAlipayDiscount.isSelected()){
            params.put("discount","8");
        }else if (sevenCashDiscount.isSelected() || sevenWxpayDiscount.isSelected()
                || sevenAlipayDiscount.isSelected()){
            params.put("discount","7");
        }else {
            params.put("discount","10");
        }

        if (cashPaySelector.isSelected()){
            params.put("type","CASH");
        }else if (wxPaySelector.isSelected()){
            params.put("type","WECHAT");
        }else if (aliPaySelector.isSelected()){
            params.put("type","ALI");
        }

        requestHttpData(Constants.Urls.URL_POST_OFFLINE_ORDER_PAY,REQUEST_CODE_OFFLINE_ORDER_PAY, FProtocol.HttpMethod.POST,params);
    }

    public static String StringFilter(String str)throws PatternSyntaxException {
        String regEx = "[/\\:*?<>|\"\n\t]"; //要过滤掉的字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
