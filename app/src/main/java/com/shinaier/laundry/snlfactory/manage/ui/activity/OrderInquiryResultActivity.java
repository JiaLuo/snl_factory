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
import com.shinaier.laundry.snlfactory.manage.adapter.InquiryCategoryAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OrderInquiryEntities;
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
    private OrderInquiryEntities orderInquiryEntities;
    private InquiryCategoryAdapter inquiryCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_inquiry_result_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        orderNum = intent.getStringExtra("orderNum");
        loadData(orderNum);
        initView();
    }

    private void loadData(String args) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("condition",args);
        requestHttpData(Constants.Urls.URL_POST_INQUIRY_RESULT,REQUEST_CODE_INQUIRY_RESULT, FProtocol.HttpMethod.POST,params);
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
                loadData(orderNum);
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
                    orderInquiryEntities = Parsers.getOrderInquiryEntities(data);
                    if(orderInquiryEntities != null && orderInquiryEntities.getDataList() != null &&
                            orderInquiryEntities.getDataList().size() > 0){
                        setLoadingStatus(LoadingStatus.GONE);
                        inquiryCategoryAdapter = new InquiryCategoryAdapter(this, orderInquiryEntities.getDataList());
                        inquiryResultList.setAdapter(inquiryCategoryAdapter);
                        inquiryCategoryAdapter.setPositionListener(new InquiryCategoryAdapter.PositionListener() {
                            @Override
                            public void onPositionClick(int position) {
                                Intent intent = new Intent(OrderInquiryResultActivity.this,OrderDetailActivity.class);
                                intent.putExtra("id", orderInquiryEntities.getDataList().get(position).getId());
                                startActivity(intent);
                            }
                        });

                        inquiryCategoryAdapter.setShowMoreClickListener(new InquiryCategoryAdapter.ShowMoreClickListener() {
                            @Override
                            public void onClick(int position, ImageView iv, TextView tv) {
                                OrderInquiryEntities.Data data1 = orderInquiryEntities.getDataList().get(position);
                                if (data1.isOpen){
                                    iv.setBackgroundResource(R.drawable.ic_up_arrow);
                                    tv.setText("隐藏更多选项");
                                }else{
                                    iv.setBackgroundResource(R.drawable.ic_down_arrow);
                                    tv.setText("查看更多");
                                }
                                inquiryCategoryAdapter.notifyDataSetChanged();
                            }
                        });
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                        inquiryCategoryAdapter = new InquiryCategoryAdapter(this, new ArrayList<OrderInquiryEntities.Data>());
                        inquiryResultList.setAdapter(inquiryCategoryAdapter);
                        inquiryResultList.setCanAddMore(false);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                    inquiryCategoryAdapter = new InquiryCategoryAdapter(this, new ArrayList<OrderInquiryEntities.Data>());
                    inquiryResultList.setAdapter(inquiryCategoryAdapter);
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
                loadData(orderResult);
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData(orderNum);
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
