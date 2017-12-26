package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
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
import com.shinaier.laundry.snlfactory.manage.adapter.OrderDetailAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OrderDetailEntity;
import com.shinaier.laundry.snlfactory.network.entity.OrderPrintEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.entities.PrintEntity;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.PrintActivity;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.BigPhotoActivity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import static com.shinaier.laundry.snlfactory.offlinecash.ui.activity.OrderPayActivity.FROM_ORDER_PAY_ACT;


/**
 * 订单详情
 * Created by 张家洛 on 2017/3/7.
 */

public class OrderDetailActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ORDER_DETAIL = 0x1;
    private static final int REQUEST_CODE_ORDER_PRINT = 0x3;

    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.order_detail_list)
    private WrapHeightListView orderDetailList;
    @ViewInject(R.id.order_total_num) //总价
    private TextView orderTotalNum;
    @ViewInject(R.id.total_clothes_num)
    private TextView totalClothesNum; //共多少件
    @ViewInject(R.id.order_status)
    private TextView orderStatus; //订单状态
    @ViewInject(R.id.user_name)
    private TextView userName; //用户姓名
    @ViewInject(R.id.user_phone_num)
    private TextView userPhoneNum; //用户手机号
    @ViewInject(R.id.order_number)
    private TextView orderNumber;//订单号
    @ViewInject(R.id.order_bespeak_time)
    private TextView orderBespeakTime; //下单时间
    @ViewInject(R.id.order_detail_address)
    private TextView orderDetailAddress; //订单地址
    @ViewInject(R.id.tv_print_order)
    private TextView tvPrintOrder;
    @ViewInject(R.id.ll_order_ellipsis) // 企业订单省略显示。。
    private LinearLayout llOrderEllipsis;
    @ViewInject(R.id.rl_offline_show_info)
    private RelativeLayout rlOfflineShowInfo; //线下订单详情显示项目信息
    @ViewInject(R.id.ll_project_info)
    private LinearLayout llProjectInfo;
    @ViewInject(R.id.ll_altogether)
    private LinearLayout llAltogether;
    @ViewInject(R.id.num_of_packages) //线上订单共几件
    private TextView numOfPackages;
    @ViewInject(R.id.order_detail_activity) //线上订单活动优惠
    private TextView orderDetailActivity;
    @ViewInject(R.id.favourable_num)  //活动优惠具体金额
    private TextView favourableNum;
    @ViewInject(R.id.service_charge) //上门服务费
    private TextView serviceCharge;
    @ViewInject(R.id.altogether_num) //总计多少钱
    private TextView altogetherNum;
    @ViewInject(R.id.actuall_paid_num) //实付多少钱
    private TextView actuallPaidNum;
    @ViewInject(R.id.bespeak_time) //线上订单预约上门时间
    private TextView bespeakTime;
    @ViewInject(R.id.rl_order_remarks) //线上订单 备注一行
    private RelativeLayout rlOrderRemarks;
    @ViewInject(R.id.order_detail_remarks) //线上订单 备注
    private TextView orderDetailRemarks;
    @ViewInject(R.id.tv_user_name)
    private TextView tvUserName;
    @ViewInject(R.id.rl_user_address)
    private RelativeLayout rlUserAddress;
    @ViewInject(R.id.ll_bespeak_time) //预约上门时间
    private RelativeLayout llBespeaTime;

    private String id;
    private PrintEntity printEntity = new PrintEntity();
    private CommonDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("oid",id);
        requestHttpData(Constants.Urls.URL_POST_ONLINE_ORDER_DETAIL,REQUEST_CODE_ORDER_DETAIL, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("订单详情");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        dialog = new CommonDialog(this);
        leftButton.setOnClickListener(this);
        tvPrintOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;
            case R.id.tv_print_order:
                dialog.setContent("加载中");
                dialog.show();
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("order_id",id);
                params.put("clerk_id", UserCenter.getUid(this));
                params.put("token",UserCenter.getToken(this));
                requestHttpData(Constants.Urls.URL_POST_ORDER_PRINT,REQUEST_CODE_ORDER_PRINT, FProtocol.HttpMethod.POST,params);
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_ORDER_DETAIL:
                if(data != null){
                    final OrderDetailEntity orderDetailEntity = Parsers.getOrderDetailEntity(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if(orderDetailEntity != null){
                        if (orderDetailEntity.getCode() == 0){
                            //设置订单信息
                            setOrderInfo(orderDetailEntity);
                            if(orderDetailEntity.getDetailResult().getOrderDetailItemses() != null && orderDetailEntity.getDetailResult()
                                    .getOrderDetailItemses().size() > 0){
                                orderDetailList.setVisibility(View.VISIBLE);
                                OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(this,orderDetailEntity.getDetailResult().getOrderDetailItemses(),
                                        orderDetailEntity.getDetailResult().getIsOnline());
                                orderDetailList.setAdapter(orderDetailAdapter);


                                orderDetailAdapter.getPhotoListener(new OrderDetailAdapter.CheckPhotoListener() {
                                    @Override
                                    public void onClick(int position, int photoPosition) {
                                        ArrayList<String> itemImages = (ArrayList<String>) orderDetailEntity.getDetailResult().getOrderDetailItemses().get(position).getItemImages();
                                        Intent intent = new Intent(OrderDetailActivity.this,BigPhotoActivity.class);
                                        intent.putExtra("imagePosition",photoPosition);
                                        intent.putStringArrayListExtra("imagePath",itemImages);
                                        startActivity(intent);
                                    }
                                });

                            }else {
                                orderDetailList.setVisibility(View.GONE);
                            }
                        }else {
                            ToastUtil.shortShow(this,orderDetailEntity.getMsg());
                        }

                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
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
//                                    ExitManager.instance.exitOfflineCollectActivity();
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

    /**
     * 设置订单信息
     * @param orderDetailEntity 线上数据
     */
    private void setOrderInfo(OrderDetailEntity orderDetailEntity) {
        if (orderDetailEntity.getDetailResult().getIsOnline().equals("0")){
            //线下订单只显示共几件，总价 订单状态
            rlOfflineShowInfo.setVisibility(View.VISIBLE);
            //预约上门时间
            llBespeaTime.setVisibility(View.GONE);
            //判断企业订单还是个人订单 企业订单最多显示五个，其他做省略处理
            if (orderDetailEntity.getDetailResult().getIsCompany() != null){
                if (orderDetailEntity.getDetailResult().getIsCompany().equals("0")){ //是否为企业会员:1-是;0-否
                    llOrderEllipsis.setVisibility(View.GONE);
                    tvUserName.setText("姓名：");
                    tvPrintOrder.setVisibility(View.VISIBLE); //显示打印按钮
                    rlUserAddress.setVisibility(View.VISIBLE); //显示用户地址
                }else {
                    llOrderEllipsis.setVisibility(View.VISIBLE);
                    tvUserName.setText("企业名称：");
                    tvPrintOrder.setVisibility(View.GONE);
                    rlUserAddress.setVisibility(View.GONE);
                }
            }else {
                llOrderEllipsis.setVisibility(View.GONE);
            }

            orderTotalNum.setText("总价：￥" + orderDetailEntity.getDetailResult().getPayAmount()); //设置总价
            totalClothesNum.setText("共" + orderDetailEntity.getDetailResult().getItemCount() + "件");
            userName.setText(orderDetailEntity.getDetailResult().getuName());
            userPhoneNum.setText(orderDetailEntity.getDetailResult().getuMobile());
            orderNumber.setText(orderDetailEntity.getDetailResult().getOrderSn());
            orderBespeakTime.setText(orderDetailEntity.getDetailResult().getoTime());
            orderDetailAddress.setText(orderDetailEntity.getDetailResult().getuAddress());
            if (orderDetailEntity.getDetailResult().getPayState().equals("0")){ //判断订单支付状态
                orderStatus.setText("未支付");
            }else {
                orderStatus.setText("已支付");
            }

            rlOrderRemarks.setVisibility(View.GONE);
        }else {
            if (orderDetailEntity.getDetailResult().getPayState().equals("0")){ //支付状态：0-代支付，1-已支
                //线上订单只显示共几件，活动优惠 ，上门服务费：
                llProjectInfo.setVisibility(View.GONE);
                //线上订单 显示总计 实付
                llAltogether.setVisibility(View.GONE);
                //预约上门时间
//                llBespeaTime.setVisibility(View.GONE);
            }else {
                //线上订单只显示共几件，活动优惠 ，上门服务费：
                llProjectInfo.setVisibility(View.VISIBLE);
                //线上订单 显示总计 实付
                llAltogether.setVisibility(View.VISIBLE);
                //预约上门时间
                llBespeaTime.setVisibility(View.VISIBLE);
                orderTotalNum.setText("总价：￥" + orderDetailEntity.getDetailResult().getAmount()); //设置总价
                numOfPackages.setText("共" + orderDetailEntity.getDetailResult().getItemCount() + "件");
            }


            //判断活动优惠是否显示
            if(orderDetailEntity.getDetailResult().getReducePrice() != null && !orderDetailEntity.getDetailResult().getReducePrice().equals("0.00")){
                orderDetailActivity.setVisibility(View.VISIBLE);
                favourableNum.setVisibility(View.VISIBLE);
                favourableNum.setText("-￥" + orderDetailEntity.getDetailResult().getReducePrice());
            }else {
                orderDetailActivity.setVisibility(View.GONE);
                favourableNum.setVisibility(View.GONE);
            }
            //显示上门服务费
            if(orderDetailEntity.getDetailResult().getFreightPrice() != null){
                serviceCharge.setVisibility(View.VISIBLE);
                serviceCharge.setText("￥" + orderDetailEntity.getDetailResult().getFreightPrice());
            }else {
                serviceCharge.setVisibility(View.GONE);
            }

            //显示总计处理
            if(!orderDetailEntity.getDetailResult().getPayAmount().equals("0")){
                altogetherNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                if(orderDetailEntity.getDetailResult().getAmount() != null){
                    altogetherNum.setVisibility(View.VISIBLE);
                    altogetherNum.setText("总计：￥" + orderDetailEntity.getDetailResult().getAmount());
                    altogetherNum.setTextColor(Color.parseColor("#999999"));
                }else {
                    altogetherNum.setVisibility(View.GONE);
                }
            }else {
                altogetherNum.getPaint().setFlags(0);
                if(orderDetailEntity.getDetailResult().getAmount() != null){
                    altogetherNum.setVisibility(View.VISIBLE);
                    altogetherNum.setText("总计：￥" + orderDetailEntity.getDetailResult().getAmount());
                    altogetherNum.setTextColor(this.getResources().getColor(R.color.black_text));
                }else {
                    altogetherNum.setVisibility(View.GONE);
                }
            }

            //显示实付的逻辑
            if(orderDetailEntity.getDetailResult().getPayAmount() != null){
                actuallPaidNum.setVisibility(View.VISIBLE);
                SpannableStringBuilder ssb1 = new SpannableStringBuilder();
                ssb1.append("实付：￥ " + orderDetailEntity.getDetailResult().getPayAmount());
                ssb1.setSpan(new ForegroundColorSpan(res.getColor(R.color.black_text)),0,3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                actuallPaidNum.setText(ssb1);
            }else {
                actuallPaidNum.setVisibility(View.GONE);
            }

            if(orderDetailEntity.getDetailResult().getTime() != null){
                bespeakTime.setText(orderDetailEntity.getDetailResult().getTime());
            }else {
                bespeakTime.setText("");
            }

            userName.setText(orderDetailEntity.getDetailResult().getuName());
            userPhoneNum.setText(orderDetailEntity.getDetailResult().getuMobile());
            orderNumber.setText(orderDetailEntity.getDetailResult().getOrderSn());
            //设置地址
            orderDetailAddress.setText(orderDetailEntity.getDetailResult().getuAddress());
            //判断下单时间
            if(orderDetailEntity.getDetailResult().getoTime() != null){
                orderBespeakTime.setText(orderDetailEntity.getDetailResult().getoTime());
            }else {
                orderBespeakTime.setText("");
            }

            //显示备注的处理
            if(orderDetailEntity.getDetailResult().getuRemark() != null){
                rlOrderRemarks.setVisibility(View.VISIBLE);
                orderDetailRemarks.setText(orderDetailEntity.getDetailResult().getuRemark());
            }else {
                rlOrderRemarks.setVisibility(View.GONE);
                orderDetailRemarks.setText("");
            }

            if (orderDetailEntity.getDetailResult().getIsOnline().equals("1")){
                tvPrintOrder.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 设置打印的实体
     * @param orderPrintEntity
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



//        payOrderPrintInfo.setmName(orderPrintEntity.getDatas().getInfo().getmName());

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


    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_ORDER_DETAIL:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
