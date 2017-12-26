package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.viewinject.annotation.ViewInject;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.OrderCategoryAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OrderSearchEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.ClearEditText;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/2/24.
 */

public class OrderInquiryResultActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_INQUIRY_RESULT = 0x1;

    @ViewInject(R.id.inquiry_result_list)
    private FootLoadingListView inquiryResultList;
    @ViewInject(R.id.edit_search_result)
    private ClearEditText editSearchResult;
    @ViewInject(R.id.btn_start_search)
    private TextView btnStartSearch;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private String orderNum;
    private boolean isOnline;
    private OrderSearchEntity orderSearchEntity;
    private OrderCategoryAdapter orderCategoryAdapter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_inquiry_result_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        orderNum = intent.getStringExtra("orderNum");
        isOnline = intent.getBooleanExtra("is_online", false);
        date = intent.getStringExtra("date");
        loadData(orderNum,isOnline);
        initView();
    }

    private void loadData(String args,boolean isOnline) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("number",args);
        if (isOnline){
            params.put("is_online","1");
        }else {
            params.put("is_online","0");
        }

        params.put("limit","100");
        params.put("date",date);
        requestHttpData(Constants.Urls.URL_POST_ORDER_SEARCH,REQUEST_CODE_INQUIRY_RESULT, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("订单查询");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        editSearchResult.setText(orderNum);
        btnStartSearch.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        inquiryResultList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(orderNum,isOnline);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_INQUIRY_RESULT:
                inquiryResultList.onRefreshComplete();
                if(data != null){
                    orderSearchEntity = Parsers.getOrderSearchEntity(data);
                    if(orderSearchEntity != null && orderSearchEntity.getResults() != null &&
                            orderSearchEntity.getResults().size() > 0){
                        setLoadingStatus(LoadingStatus.GONE);
                        orderCategoryAdapter = new OrderCategoryAdapter(this, orderSearchEntity.getResults());
                        inquiryResultList.setAdapter(orderCategoryAdapter);
                        orderCategoryAdapter.setPositionListener(new OrderCategoryAdapter.PositionListener() {
                            @Override
                            public void onPositionClick(int position) {
                                Intent intent = new Intent(OrderInquiryResultActivity.this,OrderDetailActivity.class);
                                intent.putExtra("id", orderSearchEntity.getResults().get(position).getId());
                                startActivity(intent);
                            }
                        });

                        orderCategoryAdapter.setShowMoreClickListener(new OrderCategoryAdapter.ShowMoreClickListener() {
                            @Override
                            public void onClick(int position, ImageView iv, TextView tv) {
                                OrderSearchEntity.OrderSearchResult orderSearchResult = orderSearchEntity.getResults().get(position);
                                if (orderSearchResult.isOpen){
                                    iv.setBackgroundResource(R.drawable.ic_up_arrow);
                                    tv.setText("隐藏更多选项");
                                }else{
                                    iv.setBackgroundResource(R.drawable.ic_down_arrow);
                                    tv.setText("查看更多");
                                }
                                orderCategoryAdapter.notifyDataSetChanged();
                            }
                        });
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                        orderCategoryAdapter = new OrderCategoryAdapter(this, new ArrayList<OrderSearchEntity.OrderSearchResult>());
                        inquiryResultList.setAdapter(orderCategoryAdapter);
                        inquiryResultList.setCanAddMore(false);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                    orderCategoryAdapter = new OrderCategoryAdapter(this, new ArrayList<OrderSearchEntity.OrderSearchResult>());
                    inquiryResultList.setAdapter(orderCategoryAdapter);
                    inquiryResultList.setCanAddMore(false);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start_search:
                String orderResult = editSearchResult.getText().toString();
                setLoadingStatus(LoadingStatus.LOADING);
                loadData(orderResult,isOnline);
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData(orderNum,isOnline);
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
            case REQUEST_CODE_INQUIRY_RESULT:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
