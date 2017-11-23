package com.shinaier.laundry.snlfactory.manage.ui;

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
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.OrderDetailAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OrderDetailEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.ui.BigImageActivity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.IdentityHashMap;



/**
 * 订单详情
 * Created by 张家洛 on 2017/3/7.
 */

public class OrderDetailActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ORDER_DETAIL = 0x1;

    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.order_num_detail)
    private TextView orderNumDetail;
    @ViewInject(R.id.place_order_time)
    private TextView placeOrderTime;
    @ViewInject(R.id.bespeak_time)
    private TextView bespeakTime;
    @ViewInject(R.id.order_detail_address)
    private TextView orderDetailAddress;
    @ViewInject(R.id.order_detail_remarks)
    private TextView orderDetailRemarks;
    @ViewInject(R.id.order_detail_list)
    private WrapHeightListView orderDetailList;
    @ViewInject(R.id.rl_order_remarks)
    private RelativeLayout rlOrderRemarks;
    @ViewInject(R.id.ll_item_info)
    private LinearLayout llItemInfo;
    @ViewInject(R.id.ll_altogether)
    private LinearLayout llAltogether;
    @ViewInject(R.id.ll_project_info)
    private LinearLayout llProjectInfo;
    @ViewInject(R.id.num_of_packages)
    private TextView numOfPackages;
    @ViewInject(R.id.favourable_num)
    private TextView favourableNum;
    @ViewInject(R.id.service_charge)
    private TextView serviceCharge;
    @ViewInject(R.id.altogether_num)
    private TextView altogetherNum;
    @ViewInject(R.id.actuall_paid_num)
    private TextView actuallPaidNum;
    @ViewInject(R.id.order_detail_activity)
    private TextView orderDetailActivity;
    private String id;

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
        params.put("id",id);
        requestHttpData(Constants.Urls.URL_POST_ORDER_DETAIL,REQUEST_CODE_ORDER_DETAIL, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("订单详情");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
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
                        if(orderDetailEntity.getOrdersn() != null){
                            orderNumDetail.setText(orderDetailEntity.getOrdersn());
                        }else {
                            orderNumDetail.setText("");
                        }
                        if(orderDetailEntity.getCreateTime() != null){
                            placeOrderTime.setText(orderDetailEntity.getCreateTime());
                        }else {
                            placeOrderTime.setText("");
                        }
                        if(orderDetailEntity.getTime() != null){
                            bespeakTime.setText(orderDetailEntity.getTime());
                        }else {
                            bespeakTime.setText("");
                        }
                        if(orderDetailEntity.getAdr() != null){
                            orderDetailAddress.setText(orderDetailEntity.getAdr());
                        }else {
                            orderDetailAddress.setText("");
                        }
                        if(orderDetailEntity.getContent() != null){
                            orderDetailRemarks.setText(orderDetailEntity.getContent());
                        }else {
                            orderDetailRemarks.setText("");
                        }

                        if(orderDetailEntity.getOrderDetailItemList() != null && orderDetailEntity.getOrderDetailItemList().size() > 0){
                            llItemInfo.setVisibility(View.VISIBLE);
                            orderDetailList.setVisibility(View.VISIBLE);
                            llProjectInfo.setVisibility(View.VISIBLE);
                            llAltogether.setVisibility(View.VISIBLE);
                            OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(this,orderDetailEntity.getOrderDetailItemList());
                            orderDetailList.setAdapter(orderDetailAdapter);

                            orderDetailAdapter.getPhotoListener(new OrderDetailAdapter.CheckPhotoListener() {
                                @Override
                                public void onClick(int position, int photoPosition) {
                                    ArrayList<OrderDetailEntity.OrderDetailItem.Img> imgs = (ArrayList<OrderDetailEntity.OrderDetailItem.Img>) orderDetailEntity.getOrderDetailItemList().get(position).getImgs();
                                    Intent intent = new Intent(OrderDetailActivity.this,BigImageActivity.class);
                                    intent.putExtra("imagePosition",photoPosition);
                                    intent.putParcelableArrayListExtra("imagePath",imgs);
                                    startActivity(intent);
                                }
                            });

                            orderNumDetail.setText(orderDetailEntity.getOrdersn());
                            placeOrderTime.setText(orderDetailEntity.getTime());
                            bespeakTime.setText(orderDetailEntity.getCreateTime());
                            if(orderDetailEntity.getContent() != null){
                                rlOrderRemarks.setVisibility(View.VISIBLE);
                                orderDetailRemarks.setText(orderDetailEntity.getContent());
                            }else {
                                rlOrderRemarks.setVisibility(View.GONE);
                            }
                            orderDetailAddress.setText(orderDetailEntity.getAdr());
                            numOfPackages.setText("共" + orderDetailEntity.getSum() + "件");
                            initAltogeterData(orderDetailEntity);
                        }else {
                            llItemInfo.setVisibility(View.GONE);
                            orderDetailList.setVisibility(View.GONE);
                            llProjectInfo.setVisibility(View.GONE);
                            llAltogether.setVisibility(View.GONE);
                        }
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
        }
    }

    private void initAltogeterData(OrderDetailEntity orderDetailEntities) {
        if(orderDetailEntities.getCouponPrice() != null && !orderDetailEntities.getCouponPrice().equals("0.00")){
            orderDetailActivity.setVisibility(View.VISIBLE);
            favourableNum.setVisibility(View.VISIBLE);
            favourableNum.setText("-￥" + orderDetailEntities.getCouponPrice());
        }else {
            orderDetailActivity.setVisibility(View.GONE);
            favourableNum.setVisibility(View.GONE);
        }

        if(orderDetailEntities.getFreight() != null){
            serviceCharge.setVisibility(View.VISIBLE);
            serviceCharge.setText("￥" + orderDetailEntities.getFreight());
        }else {
            serviceCharge.setVisibility(View.GONE);
        }

        if(orderDetailEntities.getPayAmount() != null){
            actuallPaidNum.setVisibility(View.VISIBLE);
            SpannableStringBuilder ssb1 = new SpannableStringBuilder();
            ssb1.append("实付：￥ " + orderDetailEntities.getPayAmount());
            ssb1.setSpan(new ForegroundColorSpan(res.getColor(R.color.black_text)),0,3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            actuallPaidNum.setText(ssb1);
        }else {
            actuallPaidNum.setVisibility(View.GONE);
        }

        if(!orderDetailEntities.getPayAmount().equals("0")){
            altogetherNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            if(orderDetailEntities.getAmount() != null){
                altogetherNum.setVisibility(View.VISIBLE);
                altogetherNum.setText("总计：￥" + orderDetailEntities.getAmount());
                altogetherNum.setTextColor(Color.parseColor("#999999"));
            }else {
                altogetherNum.setVisibility(View.GONE);
            }
        }else {
            altogetherNum.getPaint().setFlags(0);
            if(orderDetailEntities.getAmount() != null){
                altogetherNum.setVisibility(View.VISIBLE);
                altogetherNum.setText("总计：￥" + orderDetailEntities.getAmount());
                altogetherNum.setTextColor(this.getResources().getColor(R.color.black_text));
            }else {
                altogetherNum.setVisibility(View.GONE);
            }
        }
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
