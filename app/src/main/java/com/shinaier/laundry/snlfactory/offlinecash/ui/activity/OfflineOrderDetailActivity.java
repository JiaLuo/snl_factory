package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OfflineOrderDetailEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineOrderDetailAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.BigImageActivity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * 线下订单详情
 * Created by 张家洛 on 2017/7/27.
 */

public class OfflineOrderDetailActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ORDER_DETAIL = 0x1;

    @ViewInject(R.id.ll_item_info)
    private LinearLayout llItemInfo;
    @ViewInject(R.id.order_detail_list)
    private WrapHeightListView orderDetailList;
    @ViewInject(R.id.ll_project_info)
    private LinearLayout llProjectInfo;
    @ViewInject(R.id.num_of_packages)
    private TextView numOfPackages;
    @ViewInject(R.id.service_charge)
    private TextView serviceCharge;
    @ViewInject(R.id.offline_order_username)
    private TextView offlineOrderUsername;
    @ViewInject(R.id.offline_order_mobile)
    private TextView offlineOrderMobile;
    @ViewInject(R.id.offline_order_number)
    private TextView offlineOrderNumber;
    @ViewInject(R.id.offline_order_time)
    private TextView offlineOrderTime;
    @ViewInject(R.id.offline_order_take_clothes_time)
    private TextView offlineOrderTakeClothesTime;
    @ViewInject(R.id.pay_status)
    private TextView payStatus;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_order_detail_act);
        ViewInjectUtils.inject(this);
        orderId = getIntent().getStringExtra("order_id");
        initView();
        loadData();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("order_id",orderId);
        requestHttpData(Constants.Urls.URL_POST_ORDER_DETAIL_STATISTICS,REQUEST_CODE_ORDER_DETAIL, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("订单详情");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_ORDER_DETAIL:
                if(data != null){
                    final OfflineOrderDetailEntity offlineOrderDetailEntity = Parsers.getOfflineOrderDetailEntity(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if(offlineOrderDetailEntity != null){
                        numOfPackages.setText("共" + offlineOrderDetailEntity.getDatas().getItemsCount() + "件");
                        serviceCharge.setText("￥" + offlineOrderDetailEntity.getDatas().getTotalAmount());
                        if (offlineOrderDetailEntity.getDatas().getPayState().equals("0")){
                            payStatus.setText("未支付");
                        }else {
                            payStatus.setText("已支付");
                        }
                        offlineOrderUsername.setText(offlineOrderDetailEntity.getDatas().getUserName());
                        offlineOrderMobile.setText(offlineOrderDetailEntity.getDatas().getMobile());
                        offlineOrderNumber.setText(offlineOrderDetailEntity.getDatas().getOrdersn());
                        offlineOrderTime.setText(offlineOrderDetailEntity.getDatas().getCreateTime());  // 下单时间
                        offlineOrderTakeClothesTime.setText(offlineOrderDetailEntity.getDatas().getUpdateTime()); //预约取衣时间
                        if(offlineOrderDetailEntity.getDatas().getItems() != null && offlineOrderDetailEntity.getDatas().getItems().size() > 0){
                            llItemInfo.setVisibility(View.VISIBLE);
                            orderDetailList.setVisibility(View.VISIBLE);
                            llProjectInfo.setVisibility(View.VISIBLE);
                            OfflineOrderDetailAdapter offlineOrderDetailAdapter = new OfflineOrderDetailAdapter(this,offlineOrderDetailEntity.getDatas().getItems());
                            orderDetailList.setAdapter(offlineOrderDetailAdapter);

                            offlineOrderDetailAdapter.getPhotoListener(new OfflineOrderDetailAdapter.CheckPhotoListener() {
                                @Override
                                public void onClick(int position, int photoPosition) {
                                    ArrayList<String> imgs = (ArrayList<String>) offlineOrderDetailEntity.getDatas().getItems().get(position).getImgs();
                                    Intent intent = new Intent(OfflineOrderDetailActivity.this,BigImageActivity.class);
                                    intent.putExtra("imagePosition",photoPosition);
                                    intent.putStringArrayListExtra("imagePath",imgs);
                                    startActivity(intent);
                                }
                            });

                        }else {
                            llItemInfo.setVisibility(View.GONE);
                            orderDetailList.setVisibility(View.GONE);
                            llProjectInfo.setVisibility(View.GONE);
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
            case R.id.loading_layout:
                loadData();
                break;
            case R.id.left_button:
                finish();
                break;
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
