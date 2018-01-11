package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

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
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CashConponEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineOrderPayEntity;
import com.shinaier.laundry.snlfactory.network.entity.OrderPrintEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.entities.PrintEntity;
import com.shinaier.laundry.snlfactory.offlinecash.view.FreePayConfirmDialog;
import com.shinaier.laundry.snlfactory.offlinecash.view.PlatformPayDialog;
import com.shinaier.laundry.snlfactory.offlinecash.view.SpecialPayConfirmDialog;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/7/20.
 */

public class OrderPayActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_OFFLINE_ORDER_PAY_DETAIL = 0x1;
    private static final int REQUEST_CODE_OFFLINE_ORDER_PAY = 0x3;
    public static final int ORDERPAY = 0x4;
    public static final int FROM_ORDER_PAY_ACT = 0x5;
    private static final int REQUEST_CODE_ORDER_PRINT = 0x6;
    public static final int REQUEST_CODE_CASH_CONPON_QRCODE = 0x7;
    private static final int REQUEST_CODE_CASH_CONPON_INFO = 0x8;
    private static final int WXPAY_CODE = 0x9;
    private static final int ALIPAY_CODE = 0x10;
    private static final int REQUEST_CODE_SPECIAL_PAY_SMS = 0x12;

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
    @ViewInject(R.id.ll_cash_discount)
    private LinearLayout llCashDiscount;
    @ViewInject(R.id.ll_wxpay_discount)
    private LinearLayout llWxpayDiscount;
    @ViewInject(R.id.ll_alipay_discount)
    private LinearLayout llAlipayDiscount;
    @ViewInject(R.id.special_cash_discount)
    private TextView specialCashDiscount;
    @ViewInject(R.id.rounding_cash_discount)
    private TextView roundingCashDiscount;
    @ViewInject(R.id.special_wxpay_discount)
    private TextView specialWxpayDiscount;
    @ViewInject(R.id.rounding_wxpay_discount)
    private TextView roundingWxpayDiscount;
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
    @ViewInject(R.id.rl_cash_coupon_info)
    private RelativeLayout rlCashCouponInfo;
    @ViewInject(R.id.tv_cash_coupon_value)
    private TextView tvCashCouponValue;
    @ViewInject(R.id.free_clean_discount)
    private TextView freeCleanDiscount;
    @ViewInject(R.id.rl_platform_member)
    private RelativeLayout rlPlatformMember; //平台会员卡
    @ViewInject(R.id.tv_vip_member_balance)
    private TextView tvVipMemberBalance;

    private CollectClothesDialog collectClothesDialog;

    private CommonDialog dialog;
    private String orderId;
    private  PrintEntity printEntity = new PrintEntity();
    private Handler handler = new Handler(){


        private CountDownTimer countDownTimer;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    platformPayDialog.dismiss();
                    platformMemberSelector.setSelected(false);
                    showBrandDiscountNumAndNetReceiptsNormal(reducePrice,defaultNetReceipts);
                    break;
                case 4:
                    merchantPayDialog.dismiss();
                    vipMemberSelector.setSelected(false);
                    showBrandDiscountNumAndNetReceiptsNormal(reducePrice,defaultNetReceipts);
                    break;
                case 5:
                    //会员卡支付发送验证码接口
                    countDownTimer = (CountDownTimer) msg.obj;
                    countDownTimer.start();
                    IdentityHashMap<String,String> parmas = new IdentityHashMap<>();
                    parmas.put("token",UserCenter.getToken(OrderPayActivity.this));
                    if (vipMemberSelector.isSelected()){
                        parmas.put("type","merchant");
                    }else {
                        parmas.put("type","platform");
                    }
                    parmas.put("uid",userId);
                    requestHttpData(Constants.Urls.URL_POST_MEMBER_CARD_PAY,REQUEST_CODE_SPECIAL_PAY_SMS,
                            FProtocol.HttpMethod.POST,parmas);

                    break;
                case 6:
                    inputCode = (String)msg.obj;
                    if (!TextUtils.isEmpty(inputCode)){
                        confirmOrderPay("");
                    }else {
                        ToastUtil.shortShow(OrderPayActivity.this,"请填写验证码");
                    }
                    break;
                case 7:

                    //特殊折扣发送验证码 验证店长手机号
                    countDownTimer = (CountDownTimer) msg.obj;
                    countDownTimer.start();
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token",UserCenter.getToken(OrderPayActivity.this));
                    requestHttpData(Constants.Urls.URL_POST_SPECIAL_PAY_SMS,REQUEST_CODE_SPECIAL_PAY_SMS, FProtocol.HttpMethod.POST,params);
                    break;
                case 8:
                    inputCode = (String)msg.obj;
                    if (!TextUtils.isEmpty(inputCode)){
                        if (specialWxpayDiscount.isSelected()){
                            startActivityForResult(new Intent(OrderPayActivity.this,ScanActivity.class),
                                    WXPAY_CODE);
                        }else if (specialAlipayDiscount.isSelected()){
                            startActivityForResult(new Intent(OrderPayActivity.this,ScanActivity.class),
                                    ALIPAY_CODE);
                        }else {
                            confirmOrderPay("");
                        }
                    }else {
                        ToastUtil.shortShow(OrderPayActivity.this,"请填写验证码");
                    }
                    break;
                case 9:
                    //免洗发送验证码 验证店长手机号
                    countDownTimer = (CountDownTimer) msg.obj;
                    countDownTimer.start();
                    IdentityHashMap<String,String> freeParams = new IdentityHashMap<>();
                    freeParams.put("token",UserCenter.getToken(OrderPayActivity.this));
                    requestHttpData(Constants.Urls.URL_POST_SPECIAL_PAY_SMS,REQUEST_CODE_SPECIAL_PAY_SMS, FProtocol.HttpMethod.POST,freeParams);
                    break;
                case 10:
                    inputCode = (String)msg.obj;
                    if (!TextUtils.isEmpty(inputCode)){
                        confirmOrderPay("");
                    }else {
                        ToastUtil.shortShow(OrderPayActivity.this,"请填写验证码");
                    }
                    break;
                case 11:
                    freePayConfirmDialog.dismiss();
                    freeCleanDiscount.setSelected(false);
                    rlCashCouponInfo.setOnClickListener(OrderPayActivity.this);
                    if (isGetCashConpon){
                        //设置代金券金额
                        tvCashCouponValue.setText(String.format(OrderPayActivity.this.getResources().getString(R.string.brandDiscount_value),formatMoney(cashCouponValue)));
                    }
                    payModeSelectShowBrandDiscountAndNetReceipts();
                    break;
            }
        }
    };
    private PlatformPayDialog platformPayDialog;
    private PlatformPayDialog merchantPayDialog;
    private boolean isGetCashConpon = false;
    private int extraFrom;
    private CollectClothesDialog bindingCashConponView;
    private String inputCashCouponNum;
    private double cashCouponValue;
    private double payAmount;
    private String inputSpecial;
    private String inputAlipaySpecial;
    private double totalAmount;

    private List<Double> doubles = new ArrayList<>();

    private List<OfflineOrderPayEntity.OfflineOrderPayResult.OfflineOrderPayItems> itemses;
    private double merchantCardDiscount;
    private String userId;
    private String userMobile;
    private double platformCardDiscount;
    private String masterPhone;
    private String cashInputSpecial;
    private String cashCouponSn;
    private String inputCode;
    private FreePayConfirmDialog freePayConfirmDialog;
    private SpecialPayConfirmDialog specialPayConfirmDialog;
    private double defaultNetReceipts;
    private double reducePrice;


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
        params.put("oid",orderId);
        requestHttpData(Constants.Urls.URL_POST_OFFLINE_ORDER_PAY_DETAIL,REQUEST_CODE_OFFLINE_ORDER_PAY_DETAIL, FProtocol.HttpMethod.POST,params);
    }


    private void initView() {
        setCenterTitle("订单支付");
        leftButton.setOnClickListener(this);
        cashPaySelector.setOnClickListener(this);
        wxPaySelector.setOnClickListener(this);
        aliPaySelector.setOnClickListener(this);
        vipMemberSelector.setOnClickListener(this);
        specialCashDiscount.setOnClickListener(this);
        roundingCashDiscount.setOnClickListener(this);
        specialWxpayDiscount.setOnClickListener(this);
        roundingWxpayDiscount.setOnClickListener(this);
        specialAlipayDiscount.setOnClickListener(this);
        roundingAlipayDiscount.setOnClickListener(this);
        confirmPay.setOnClickListener(this);
        rlCashCouponInfo.setOnClickListener(this);
        freeCleanDiscount.setOnClickListener(this);

        dialog = new CommonDialog(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_OFFLINE_ORDER_PAY_DETAIL:
                if (data != null){
                    OfflineOrderPayEntity offlineOrderPayEntity = Parsers.getOfflineOrderPayEntity(data);
                    if (offlineOrderPayEntity != null){
                        if (offlineOrderPayEntity.getCode() == 0){
                            if (offlineOrderPayEntity.getResult() != null){
                                OfflineOrderPayEntity.OfflineOrderPayResult result = offlineOrderPayEntity.getResult();
                                //获取用户id
                                userId = result.getuId();
                                //获取用户电话
                                userMobile = result.getuMobile();
                                //获取店长电话
                                masterPhone = result.getMasterPhone();
                                //订单优惠之后总价（后台计算过的价格）
                                payAmount = result.getPayAmount();
                                //订单实际总价
                                totalAmount = result.getTotalAmount();
                                //获取订单里的所有项目集合
                                itemses = result.getItemses();
                                //设置应收价格
                                tvOrderSum.setText(String.format(this.getResources().getString(R.string.show_money_value),
                                        formatMoney(totalAmount)));

                                //默认实收价格
                                defaultNetReceipts = computeAfterDiscount(itemses, false, 10);
                                //项目优惠价格
                                reducePrice = result.getReducePrice();


                                //默认显示品项折扣和实收价格
                                showBrandDiscountNumAndNetReceiptsNormal(reducePrice, defaultNetReceipts);

                                //是否有平台卡的标识
                                String hasPlatform = result.getHasPlatform();

                                if (hasPlatform.equals("1")){
                                    //平台卡的折扣
                                    platformCardDiscount = result.getOfflineOrderPayPlatform().getcDiscount();

                                    //平台卡余额
                                    double platformCardBalande = result.getOfflineOrderPayPlatform().getcBalance();

                                    platformBalanceNum.setText(String.format(this.getResources().getString(R.string.balance_value),platformCardBalande));
                                    llGoPay.setVisibility(View.GONE); //购买平台会员卡的按钮隐藏
                                    platformMemberSelector.setVisibility(View.VISIBLE);
                                    llGoPay.setOnClickListener(null);


                                    //计算平台会员卡折后的价格
                                    double usePlatformAmount = computeAfterDiscount(itemses, true, platformCardDiscount);
                                    //判断支付金额是否大于卡余额
                                    if (platformCardBalande >= usePlatformAmount){
                                        platformMemberSelector.setOnClickListener(this);
                                        rebatesBalancePlatformPay.setVisibility(View.GONE);
                                        platformMemberSelector.setVisibility(View.VISIBLE);
                                    }else {
                                        platformMemberSelector.setOnClickListener(null);
                                        platformMemberSelector.setVisibility(View.INVISIBLE);
                                        rebatesBalancePlatformPay.setVisibility(View.VISIBLE);
                                    }
                                }else {
                                    llGoPay.setVisibility(View.VISIBLE); //购买平台会员卡的按钮显示
                                    platformMemberSelector.setVisibility(View.GONE);
                                    platformBalanceNum.setText(String.format(this.getResources().getString(R.string.balance_value),formatMoney(0.00)));
                                    llGoPay.setOnClickListener(this);
                                }



                                //是否有赚点卡的标识
                                String hasMerchant = result.getHasMerchant();

                                if (hasMerchant.equals("1")){
                                    //专店卡的折扣
                                    merchantCardDiscount = result.getOfflineOrderPayMerchant().getcDiscount();
                                    //专店卡的余额
                                    double merchantCardBalance = result.getOfflineOrderPayMerchant().getcBalance();
                                    vipBalanceNum.setText(String.format(this.getResources().getString(R.string.balance_value),
                                            merchantCardBalance ));

                                    rlVipMember.setVisibility(View.VISIBLE); //就显示
                                    vipMemberCardLine.setVisibility(View.VISIBLE);// 专店卡下面的分割线

                                    //计算平台会员卡折后的价格

                                    double useMerchantAmount = computeAfterDiscount(itemses, true, merchantCardDiscount);
                                    // 如果会员卡余额等于0 就不让点击 选择专店会员卡按钮小时，余额不足 显示
                                    if (merchantCardBalance >= useMerchantAmount){
                                        tvVipMemberBalance.setVisibility(View.GONE);
                                        vipMemberSelector.setVisibility(View.VISIBLE);
                                    }else {
                                        tvVipMemberBalance.setVisibility(View.VISIBLE);
                                        vipMemberSelector.setVisibility(View.INVISIBLE);
                                    }
                                }else {
                                    rlVipMember.setVisibility(View.GONE); //否则隐藏
                                    vipMemberCardLine.setVisibility(View.GONE);
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineOrderPayEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_OFFLINE_ORDER_PAY:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            //这里返回要好好检查一下
//                            finish();

                            //点击特殊折扣后进入这个判断
//                            if (platformPaySuccessEntity.getDatas().getCode() != null){
//                                if (platformPaySuccessEntity.getDatas().getCode().equals("success")){
//                                    orderPrint(orderId);
//                                }
//                            }

                            if (extraFrom == TakeClothesActivity.FROM_TAKE_CLOTHES){ //如果从取衣界面进来的，不打印
                                ExitManager.instance.exitOfflineCollectActivity();
                            }else {
                                if (merchantPayDialog != null){
                                    if (merchantPayDialog.isShowing()){
                                        merchantPayDialog.dismiss();
                                    }
                                }

                                if (platformPayDialog != null){
                                    if (platformPayDialog.isShowing()){
                                        platformPayDialog.dismiss();
                                    }
                                }

                                if (freePayConfirmDialog != null){
                                    if (freePayConfirmDialog.isShowing()){
                                        freePayConfirmDialog.dismiss();
                                    }
                                }

                                if (specialPayConfirmDialog != null){
                                    if (specialPayConfirmDialog.isShowing()){
                                        specialPayConfirmDialog.dismiss();
                                    }
                                }
                                orderPrint(orderId);
                            }
                        }else {
                            if (dialog.isShowing()){
                                dialog.dismiss();
                            }
                            ToastUtil.shortShow(this,entity.getStatus());
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
            case REQUEST_CODE_CASH_CONPON_INFO:
                if (data != null){
                    CashConponEntity cashConponEntity = Parsers.getCashConponEntity(data);
                    if (cashConponEntity != null){
                        if (cashConponEntity.getCode() == 0){
                            if (bindingCashConponView.isShowing()){
                                bindingCashConponView.dismiss();
                            }
                            //专店会员卡 平台会员卡不让使用
                            vipMemberSelector.setSelected(false);
                            vipMemberSelector.setOnClickListener(null);
                            platformMemberSelector.setSelected(false);
                            platformMemberSelector.setOnClickListener(null);
                            rlVipMember.setBackgroundColor(this.getResources().getColor(R.color.ccccc));
                            rlPlatformMember.setBackgroundColor(this.getResources().getColor(R.color.ccccc));

                            //使用了优惠券。
                            isGetCashConpon = true;
                            cashCouponValue = cashConponEntity.getValue(); //获取代金券面值
                            tvCashCouponValue.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),cashCouponValue));//设置显示代金券

                            //代金券金额比订单总额大的情况
                            if (cashCouponValue >= payAmount){
                                tvNetReceipts.setText(String.format(this.getResources().getString(R.string.show_money_value),formatMoney(0.00))); //设置实收显示
                                tvBrandDiscountNum.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),formatMoney(payAmount))); //设置品项折扣显示
                                specialCashDiscount.setOnClickListener(null); //代金券金额比订单总额打的话 现金支付特殊折扣不让点
                                specialWxpayDiscount.setOnClickListener(null);//代金券金额比订单总额打的话 微信支付特殊折扣不让点
                                specialAlipayDiscount.setOnClickListener(null);//代金券金额比订单总额打的话 支付宝支付特殊折扣不让点
                            }else {
                                tvNetReceipts.setText(String.format(this.getResources().getString(R.string.show_money_value),formatMoney(defaultNetReceipts - cashCouponValue))); //设置实收显示
                                tvBrandDiscountNum.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),formatMoney(cashCouponValue + reducePrice))); //设置品项折扣显示
                                specialCashDiscount.setOnClickListener(this);
                                specialWxpayDiscount.setOnClickListener(this);
                                specialAlipayDiscount.setOnClickListener(this);
                            }
                        }else {
                            ToastUtil.shortShow(this,cashConponEntity.getMsg());
                        }
                    }

                }
                break;
            //特殊折扣发送短信返回码
            case REQUEST_CODE_SPECIAL_PAY_SMS:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){

                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }


    private double computeAfterDiscount(List<OfflineOrderPayEntity.OfflineOrderPayResult.OfflineOrderPayItems> itemses, boolean isDiscount, double cardDiscount) {
        double sum = 0;
        for (int i = 0; i < itemses.size(); i++) {
            //项目是否有折扣标识
            String hasDiscount = itemses.get(i).getHasDiscount();
            //项目的折扣
            double itemDiscount = itemses.get(i).getItemDiscount();
            //项目实际价格
            double itemPrice = itemses.get(i).getItemPrice();
            //项目打折后的价格
            double itemRealPrice = itemses.get(i).getItemRealPrice();
            if (hasDiscount.equals("1")){
                if (isDiscount){
                    if (itemDiscount > cardDiscount){
                        double v = itemPrice - discountFunc(itemPrice, cardDiscount);
                        doubles.add(v);
                    }else {
                        double v = itemPrice - discountFunc(itemPrice, itemDiscount);
                        doubles.add(v);
                    }
                } else {
                    doubles.add(itemRealPrice);
                }
            }else {
                doubles.add(itemPrice);
            }
        }

        //添加到集合里的数据做相加操作
        for (int i = 0; i < doubles.size(); i++) {
            sum += doubles.get(i);
        }
        return sum;
    }

    /**
     * 品项折扣和实收默认显示
     */
    private void showBrandDiscountNumAndNetReceiptsNormal(double brandDiscount,double netReceipts) {
        tvBrandDiscountNum.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),
                formatMoney(brandDiscount)));  //设置品项折扣
        tvNetReceipts.setText(String.format(this.getResources().getString(R.string.show_money_value),
                formatMoney(netReceipts))); //设置应收显示
    }

    /**
     * 显示品项折扣和实收金额 打折钱
     * @param beforeValue 打折前应收的金额
     * @param CashCouponValue 代金券金额
     */
    private void showBrandDiscountNumAndNetReceiptsBeforeDiscout(double beforeValue,double CashCouponValue) {
        tvBrandDiscountNum.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),
                formatMoney(CashCouponValue))); //设置品项折扣
        tvNetReceipts.setText(String.format(this.getResources().getString(R.string.show_money_value),
                formatMoney(beforeValue)));  //设置实收显示
    }

    /**
     * 显示品项折扣和实收金额 打折后
     *
     * @param value  打完折但没有使用代金券
     */
    private void showBrandDiscountNumAndNetReceiptsAfterDiscout(double value) {
        tvBrandDiscountNum.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),
                formatMoney(payAmount - value + reducePrice))); //设置应收显示
        tvNetReceipts.setText(String.format(this.getResources().getString(R.string.show_money_value),
                formatMoney(value)));
    }

    /**
     * 显示品项折扣和实收金额 取整抹零
     * @param value 打完折的值
     */
    private void showBrandDiscountNumAndNetReceiptsRoundingCashDiscount(double value) {
        tvBrandDiscountNum.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),
                formatMoney(payAmount - Math.floor(value) + reducePrice))); //设置应收显示
        tvNetReceipts.setText(String.format(this.getResources().getString(R.string.show_money_value),
                formatMoney(Math.floor(value))));
    }



    /**
     * 设置打印需要的数据
     * @param orderPrintEntity 打印实体
     */
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
        payOrderPrintInfo.setmName(orderPrintEntity.getDatas().getInfo().getmName());

        List<OrderPrintEntity.OrderPrintDatas.OrderPrintItems> items = orderPrintEntity.getDatas().getItems();
        for (int i = 0; i < items.size(); i++) {
            payOrderPrintItems = payOrderPrintEntity.new PayOrderPrintItems();
            payOrderPrintItems.setItemNote(items.get(i).getItemNote());
            payOrderPrintItems.setName(items.get(i).getName());
            payOrderPrintItems.setPrice(items.get(i).getPrice());
            payOrderPrintItems.setColor(items.get(i).getColor());
            payOrderPrintItems.setColorText(items.get(i).getColorText());
            payOrderPrintItems.setNoteText(items.get(i).getNoteText());
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
                doubles.clear();
                rlCashCouponInfo.setOnClickListener(this);
                if(platformMemberSelector.isSelected() ||cashPaySelector.isSelected() ||wxPaySelector.isSelected() ||aliPaySelector.isSelected()){
                    platformMemberSelector.setSelected(false);
                    cashPaySelector.setSelected(false);
                    wxPaySelector.setSelected(false);
                    aliPaySelector.setSelected(false);
                    llCashDiscount.setVisibility(View.GONE);
                    llWxpayDiscount.setVisibility(View.GONE);
                    llAlipayDiscount.setVisibility(View.GONE);
                    anotherSetFalse();
                }
                merchantPayDialog = new PlatformPayDialog(this, R.style.DialogTheme,handler,2);
                merchantPayDialog.setview();
                merchantPayDialog.setCanceledOnTouchOutside(false);
                if(!merchantPayDialog.isShowing()){
                    vipMemberSelector.setSelected(true);
                    merchantPayDialog.show();
                    //计算折扣
                    double afterDiscount = computeAfterDiscount(itemses, true, merchantCardDiscount);
                    //显示品项折扣和实收的价格
                    showBrandDiscountNumAndNetReceiptsAfterDiscout(afterDiscount);
                }
                break;
            case R.id.platform_member_selector:
                doubles.clear();
                rlCashCouponInfo.setOnClickListener(this);
                if(vipMemberSelector.isSelected() ||cashPaySelector.isSelected() ||wxPaySelector.isSelected() ||aliPaySelector.isSelected()){
                    vipMemberSelector.setSelected(false);
                    cashPaySelector.setSelected(false);
                    wxPaySelector.setSelected(false);
                    aliPaySelector.setSelected(false);
                    llCashDiscount.setVisibility(View.GONE);
                    llWxpayDiscount.setVisibility(View.GONE);
                    llAlipayDiscount.setVisibility(View.GONE);
                    anotherSetFalse();
                }
                platformPayDialog = new PlatformPayDialog(this, R.style.DialogTheme,handler,1);
                platformPayDialog.setview();
                platformPayDialog.setCanceledOnTouchOutside(false);
                if(!platformPayDialog.isShowing()){
                    platformMemberSelector.setSelected(true);
                    platformPayDialog.show();
                    //计算折扣
                    double afterDiscount = computeAfterDiscount(itemses, true, platformCardDiscount);
                    //显示品项折扣和实收的价格
                    showBrandDiscountNumAndNetReceiptsAfterDiscout(afterDiscount);
                }
                break;
            case R.id.cash_pay_selector:
                if(cashPaySelector.isSelected()){
                    cashPaySelector.setSelected(false);
                    llCashDiscount.setVisibility(View.GONE);
                    anotherSetFalse();
                    payModeSelectShowBrandDiscountAndNetReceipts();
                }else {
                    cashPaySelector.setSelected(true);
                    llCashDiscount.setVisibility(View.VISIBLE);
                    if(wxPaySelector.isSelected() || aliPaySelector.isSelected() ||platformMemberSelector.isSelected() ||vipMemberSelector.isSelected()){
                        wxPaySelector.setSelected(false);
                        llWxpayDiscount.setVisibility(View.GONE);
                        aliPaySelector.setSelected(false);
                        llAlipayDiscount.setVisibility(View.GONE);
                        platformMemberSelector.setSelected(false);
                        vipMemberSelector.setSelected(false);
                        payModeSelectShowBrandDiscountAndNetReceipts();
                    }
                }
                break;
            case R.id.wx_pay_selector:
                if(wxPaySelector.isSelected()){
                    wxPaySelector.setSelected(false);
                    llWxpayDiscount.setVisibility(View.GONE);
                    anotherSetFalse();
                    payModeSelectShowBrandDiscountAndNetReceipts();
                }else {
                    wxPaySelector.setSelected(true);
                    llWxpayDiscount.setVisibility(View.VISIBLE);

                    if (aliPaySelector.isSelected() || cashPaySelector.isSelected()||platformMemberSelector.isSelected() ||vipMemberSelector.isSelected()){
                        cashPaySelector.setSelected(false);
                        llCashDiscount.setVisibility(View.GONE);
                        aliPaySelector.setSelected(false);
                        llAlipayDiscount.setVisibility(View.GONE);
                        platformMemberSelector.setSelected(false);
                        vipMemberSelector.setSelected(false);

                        payModeSelectShowBrandDiscountAndNetReceipts();
                    }
                }
                break;
            case R.id.ali_pay_selector:
                if(aliPaySelector.isSelected()){
                    aliPaySelector.setSelected(false);
                    llAlipayDiscount.setVisibility(View.GONE);
                    anotherSetFalse();
                    payModeSelectShowBrandDiscountAndNetReceipts();
                }else {
                    aliPaySelector.setSelected(true);
                    llAlipayDiscount.setVisibility(View.VISIBLE);
                    if (cashPaySelector.isSelected() || wxPaySelector.isSelected()||platformMemberSelector.isSelected() ||vipMemberSelector.isSelected()){
                        cashPaySelector.setSelected(false);
                        llCashDiscount.setVisibility(View.GONE);
                        wxPaySelector.setSelected(false);
                        llWxpayDiscount.setVisibility(View.GONE);
                        platformMemberSelector.setSelected(false);
                        vipMemberSelector.setSelected(false);

                        payModeSelectShowBrandDiscountAndNetReceipts();
                    }
                }
                break;
            case R.id.special_cash_discount:
                if(specialCashDiscount.isSelected()){
                    specialCashDiscount.setSelected(false);
                }else {
                    specialCashDiscount.setSelected(true);
                    payModeSelectShowBrandDiscountAndNetReceipts();

                    if(roundingCashDiscount.isSelected() || freeCleanDiscount.isSelected()){
                        roundingCashDiscount.setSelected(false);
                        freeCleanDiscount.setSelected(false);
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
                            cashInputSpecial = edPhoneOrderNum.getText().toString();
                            if (!TextUtils.isEmpty(cashInputSpecial)){
                                if (Double.parseDouble(cashInputSpecial) >= 0){
                                    collectClothesCashDialog.dismiss();
                                    specialPayConfirmDialog = new SpecialPayConfirmDialog(OrderPayActivity.this, R.style.DialogTheme,
                                            handler,masterPhone, cashInputSpecial,specialCashDiscount);
                                    specialPayConfirmDialog.setCanceledOnTouchOutside(false);
                                    specialPayConfirmDialog.setView();
                                    specialPayConfirmDialog.show();



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
                if (isGetCashConpon){
                    //设置代金券设置金额数据
                    tvCashCouponValue.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),formatMoney(cashCouponValue)));
                    //如果代金券金额比支付金额小的话，改变品项折扣 和实收 的金额 反之就默认
                    if (cashCouponValue < payAmount){
                        useRoundingCashDiscount(roundingCashDiscount,cashCouponValue);
                    }else {
                        useRoundingCashDiscount(roundingCashDiscount,0);
                    }
                }else {
                    useRoundingCashDiscount(roundingCashDiscount,defaultNetReceipts);
                }
                if(specialCashDiscount.isSelected() || freeCleanDiscount.isSelected()){
                    specialCashDiscount.setSelected(false);
                    freeCleanDiscount.setSelected(false);
                }
                break;
            case R.id.free_clean_discount:
                freeCleanDiscount.setSelected(true);
                rlCashCouponInfo.setOnClickListener(null);
                if(specialCashDiscount.isSelected() || roundingCashDiscount.isSelected()){
                    specialCashDiscount.setSelected(false);
                    roundingCashDiscount.setSelected(false);
                }

                if (isGetCashConpon){
                    //设置代金券金额
                    tvCashCouponValue.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),formatMoney(0.00)));
                }
                tvBrandDiscountNum.setText(String.format(this.getResources().getString(R.string.brandDiscount_value),formatMoney(0.00)));
                tvNetReceipts.setText(String.format(this.getResources().getString(R.string.show_money_value),formatMoney(totalAmount)));

                freePayConfirmDialog = new FreePayConfirmDialog(OrderPayActivity.this, R.style.DialogTheme,
                        handler,masterPhone);
                freePayConfirmDialog.setCanceledOnTouchOutside(false);
                freePayConfirmDialog.setView();
                freePayConfirmDialog.show();
                break;
            case R.id.special_wxpay_discount:
                if(specialWxpayDiscount.isSelected()){
                    specialWxpayDiscount.setSelected(false);
                }else {
                    specialWxpayDiscount.setSelected(true);
                    payModeSelectShowBrandDiscountAndNetReceipts();

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
                            inputSpecial = edPhoneOrderNum.getText().toString();
                            if (!TextUtils.isEmpty(inputSpecial)){
                                if (Double.parseDouble(inputSpecial) >= 0){
                                    collectClothesDialog.dismiss();

                                    specialPayConfirmDialog = new SpecialPayConfirmDialog(OrderPayActivity.this, R.style.DialogTheme,
                                            handler,masterPhone, inputSpecial,specialWxpayDiscount);
                                    specialPayConfirmDialog.setCanceledOnTouchOutside(false);
                                    specialPayConfirmDialog.setView();
                                    specialPayConfirmDialog.show();

                                }else {
                                    ToastUtil.shortShow(OrderPayActivity.this,"金额不能小于0");
                                }

                            }else {
                                ToastUtil.shortShow(OrderPayActivity.this,"请输入金额");
                            }
                        }
                    });

                    if(roundingWxpayDiscount.isSelected()){
                        roundingWxpayDiscount.setSelected(false);
                    }
                }
                break;
            case R.id.rounding_wxpay_discount:
                if (isGetCashConpon){
                    //如果代金券金额比支付金额小的话，改变品项折扣 和实收 的金额 反之就默认
                    if (cashCouponValue < payAmount){
                        useRoundingCashDiscount(roundingWxpayDiscount,cashCouponValue);
                    }else {
                        useRoundingCashDiscount(roundingWxpayDiscount,0);
                    }
                }else {
                    useRoundingCashDiscount(roundingWxpayDiscount,payAmount);
                }
                if(specialWxpayDiscount.isSelected()){
                    specialWxpayDiscount.setSelected(false);
                }
                break;
            case R.id.special_alipay_discount:
                if(specialAlipayDiscount.isSelected()){
                    specialAlipayDiscount.setSelected(false);
                }else {
                    specialAlipayDiscount.setSelected(true);
                    payModeSelectShowBrandDiscountAndNetReceipts();

                    if(roundingAlipayDiscount.isSelected()){
                        roundingAlipayDiscount.setSelected(false);
                    }

                    View view = View.inflate(this, R.layout.input_special_num,null);
                    final EditText edPhoneOrderNum = (EditText) view.findViewById(R.id.ed_phone_order_num);
                    TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
                    TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm);
                    revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            collectClothesDialog.dismiss();
                            specialAlipayDiscount.setSelected(false);
                        }
                    });

                    revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inputAlipaySpecial = edPhoneOrderNum.getText().toString();
                            if (!TextUtils.isEmpty(inputAlipaySpecial)){
                                if (Double.parseDouble(inputAlipaySpecial) >= 0){
                                    collectClothesDialog.dismiss();
                                    collectClothesDialog.dismiss();

                                    specialPayConfirmDialog = new SpecialPayConfirmDialog(OrderPayActivity.this, R.style.DialogTheme,
                                            handler,masterPhone, inputAlipaySpecial,specialAlipayDiscount);
                                    specialPayConfirmDialog.setCanceledOnTouchOutside(false);
                                    specialPayConfirmDialog.setView();
                                    specialPayConfirmDialog.show();
                                }else {
                                    ToastUtil.shortShow(OrderPayActivity.this,"金额不能小于0");
                                }

                            }else {
                                ToastUtil.shortShow(OrderPayActivity.this,"请输入金额");
                            }
                        }
                    });
                    collectClothesDialog = new CollectClothesDialog(this, R.style.DialogTheme,view);
                    collectClothesDialog.setCanceledOnTouchOutside(false);
                    collectClothesDialog.show();
                }
                break;
            case R.id.rounding_alipay_discount:
                if (isGetCashConpon){
                    if (cashCouponValue < payAmount){
                        useRoundingCashDiscount(roundingAlipayDiscount,cashCouponValue);
                    }else {
                        useRoundingCashDiscount(roundingAlipayDiscount,0);
                    }
                }else {
                    useRoundingCashDiscount(roundingAlipayDiscount,payAmount);
                }
                if(specialAlipayDiscount.isSelected()){
                    specialAlipayDiscount.setSelected(false);
                }

                break;
            case R.id.ll_go_pay:
                Intent intent = new Intent(this,BuyMemberCardActivity.class);
                intent.putExtra("user_id",userId);
                intent.putExtra("mobile_num",userMobile);
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
                builder.setPositiveButton("确定", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;
            case R.id.confirm_pay:
                if (platformMemberSelector.isSelected() || vipMemberSelector.isSelected() || cashPaySelector.isSelected()
                        || wxPaySelector.isSelected() || aliPaySelector.isSelected()){
                    if (cashPaySelector.isSelected()){

                        dialog.setContent("加载中");
                        dialog.show();
//                        cashPayConfirm();
                        confirmOrderPay("");

                    }else if (wxPaySelector.isSelected() || aliPaySelector.isSelected()){
                        startActivityForResult(new Intent(this,ScanActivity.class), ORDERPAY);
                    }
                }else {
                    ToastUtil.shortShow(this,"请选择支付方式");
                }

                break;
            case R.id.rl_cash_coupon_info:
                View cashConponView = View.inflate(this, R.layout.binding_cash_conpon_view,null);
                final EditText edCashCouponNum = (EditText) cashConponView.findViewById(R.id.ed_cash_coupon_num);
                ImageView ivScan = (ImageView) cashConponView.findViewById(R.id.iv_scan);
                TextView cashCouponCancel = (TextView) cashConponView.findViewById(R.id.cash_coupon_cancel);
                TextView cashCouponConfirm = (TextView) cashConponView.findViewById(R.id.cash_coupon_confirm);

                cashCouponCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bindingCashConponView.dismiss();
                    }
                });

                cashCouponConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cashCouponSn = edCashCouponNum.getText().toString();
                        IdentityHashMap<String,String> params = new IdentityHashMap<>();
                        params.put("token",UserCenter.getToken(OrderPayActivity.this));
                        params.put("sn", cashCouponSn);
                        requestHttpData(Constants.Urls.URL_POST_GET_CASH_CONPON_INFO,
                                REQUEST_CODE_CASH_CONPON_INFO, FProtocol.HttpMethod.POST,params);
                    }
                });

                ivScan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(OrderPayActivity.this,ScanActivity.class),
                                REQUEST_CODE_CASH_CONPON_QRCODE);
                    }
                });
                bindingCashConponView = new CollectClothesDialog(this, R.style.DialogTheme,cashConponView);
                bindingCashConponView.show();

                edCashCouponNum.setText(inputCashCouponNum);
                break;
        }
    }


    private void payModeSelectShowBrandDiscountAndNetReceipts() {
        if (isGetCashConpon){
            if (cashCouponValue < payAmount){
                showBrandDiscountNumAndNetReceiptsBeforeDiscout(defaultNetReceipts - cashCouponValue,reducePrice + cashCouponValue);
            }else {
                showBrandDiscountNumAndNetReceiptsBeforeDiscout(reducePrice,defaultNetReceipts);
            }
        }else {
            showBrandDiscountNumAndNetReceiptsNormal(reducePrice,defaultNetReceipts);
        }
    }

    private void useRoundingCashDiscount(TextView roundingDiscount, double useCashConponAfter) {

        if (roundingDiscount.isSelected()){
            roundingDiscount.setSelected(false);
            showBrandDiscountNumAndNetReceiptsAfterDiscout(useCashConponAfter);

        }else {
            roundingDiscount.setSelected(true);
            showBrandDiscountNumAndNetReceiptsRoundingCashDiscount(useCashConponAfter);

        }
    }



    /**
     * 把其他所有全置为没有选中
     */
    private void anotherSetFalse() {
        specialCashDiscount.setSelected(false);
        roundingCashDiscount.setSelected(false);
        specialWxpayDiscount.setSelected(false);
        roundingWxpayDiscount.setSelected(false);
        specialAlipayDiscount.setSelected(false);
        roundingAlipayDiscount.setSelected(false);
        freeCleanDiscount.setSelected(false);
    }

    public  String formatMoney(double money){
        DecimalFormat df = new DecimalFormat("###0.00");
        return df.format(money);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ORDERPAY:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String payCode = data.getStringExtra("pay_code");
                        String orderPayAmount = tvNetReceipts.getText().toString();
                        String result = orderPayAmount.substring(1,orderPayAmount.length());
                        dialog.setContent("加载中");
                        dialog.show();
//                        confirmOrderPay("",result);
                        confirmOrderPay(payCode);
                    }
                }
                break;
            case WXPAY_CODE:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String payCode = data.getStringExtra("pay_code");
                        dialog.setContent("加载中");
                        dialog.show();
                        confirmOrderPay(payCode);
                    }
                }
                break;
            case ALIPAY_CODE:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String payCode = data.getStringExtra("pay_code");
                        dialog.setContent("加载中");
                        dialog.show();
                        confirmOrderPay(payCode);
                    }
                }
                break;
            case REQUEST_CODE_CASH_CONPON_QRCODE:
                //扫优惠券码回显
                if (resultCode == RESULT_OK){
                    if (data != null){
                        inputCashCouponNum = data.getStringExtra("pay_code");
                    }
                }
                break;
        }
    }


    private void confirmOrderPay(String payCode) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        LogUtil.e("zhang","pay token = " + UserCenter.getToken(this));
        params.put("token",UserCenter.getToken(this));
        //如果使用了优惠券 就传入优惠券号码
        if (isGetCashConpon){
            params.put("sn",cashCouponSn);
        }

        params.put("oid",orderId);
        if (cashPaySelector.isSelected()){
            //现金支付
            params.put("gateway","CASH");
            if (specialCashDiscount.isSelected()){
                params.put("reduce","special");
                params.put("sms_code",inputCode);
                params.put("amount",cashInputSpecial);
            }else if (roundingCashDiscount.isSelected()){
                params.put("reduce","floor");
            }else {
                params.put("sms_code",inputCode);
                params.put("reduce","free");
            }
        }else if (wxPaySelector.isSelected()){
            //微信支付
            params.put("gateway","WechatPay_Pos");
            if (specialWxpayDiscount.isSelected()){
                params.put("sms_code",inputCode);
                params.put("reduce","special");
                params.put("amount",inputSpecial);
            }else {
                params.put("reduce","floor");
            }
        }else if (aliPaySelector.isSelected()){
            //支付宝支付
            params.put("gateway","Alipay_AopF2F");
            if (specialAlipayDiscount.isSelected()){
                params.put("reduce","special");
                params.put("sms_code",inputCode);
                params.put("amount",inputAlipaySpecial);
            }else {
                params.put("reduce","floor");
            }
        }else if (platformMemberSelector.isSelected()){
            params.put("gateway","PLATFORM");
            params.put("sms_code",inputCode);
        }else {
            params.put("gateway","MERCHANT");
            params.put("sms_code",inputCode);
        }

        if (!TextUtils.isEmpty(payCode)){
            //有payCode的情况
            params.put("auth_code",payCode);
        }

        requestHttpData(Constants.Urls.URL_POST_OFFLINE_ORDER_PAY,REQUEST_CODE_OFFLINE_ORDER_PAY, FProtocol.HttpMethod.POST,params);
    }

    /**
     * 计算打折的钱 比如 一张卡打7折 之前10元，方法得出就是 3
     * @param amount
     * @param discount
     */
    private double discountFunc(double amount, double discount) {
        return (amount * 100 - Math.floor(amount * 100 / 10 * discount)) / 100;
    }
}
