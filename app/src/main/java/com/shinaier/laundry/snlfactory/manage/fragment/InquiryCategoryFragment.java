package com.shinaier.laundry.snlfactory.manage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.InquiryCategoryAdapter;
import com.shinaier.laundry.snlfactory.manage.ui.OrderDetailActivity;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OrderInquiryEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/2/22.
 */

public class InquiryCategoryFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_ORDER_INQUIRY = 0x7;
    private static final int REQUEST_CODE_ORDER_INQUIRY_MORE = 0x8;

    private int status;
    private Context context;
    private FootLoadingListView orderList;
    private InquiryCategoryAdapter inquiryCategoryAdapter;
    private OrderInquiryEntities orderInquiryEntities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inquiry_category_frag, null);
        setLoadingStatus(LoadingStatus.LOADING);
        loadData(false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        orderList = (FootLoadingListView) view.findViewById(R.id.order_list);
        orderList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(true);
            }
        });

    }

    public void setArgs(Context context, int status) {
        this.status = status;
        this.context = context;
    }

    private void loadData(boolean isMore) {
        int code;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        params.put("state", String.valueOf(status));
        params.put("limit","20");
        if(isMore){
            params.put("page",inquiryCategoryAdapter.getPage() + 1 +"");
            code = REQUEST_CODE_ORDER_INQUIRY_MORE;
        }else {
            params.put("page","1");
            code = REQUEST_CODE_ORDER_INQUIRY;
        }
        requestHttpData(Constants.Urls.URL_POST_ORDER_INQUIRY,code, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_ORDER_INQUIRY:
                orderList.onRefreshComplete();
                if(data != null) {
                    orderInquiryEntities = Parsers.getOrderInquiryEntities(data);
                    if(orderInquiryEntities != null && orderInquiryEntities.getDataList() != null &&
                            orderInquiryEntities.getDataList().size() > 0){
                        setLoadingStatus(LoadingStatus.GONE);
                        orderList.setVisibility(View.VISIBLE);
                        inquiryCategoryAdapter = new InquiryCategoryAdapter(context, orderInquiryEntities.getDataList());
                        orderList.setAdapter(inquiryCategoryAdapter);

                        inquiryCategoryAdapter.setPositionListener(new InquiryCategoryAdapter.PositionListener() {
                            @Override
                            public void onPositionClick(int position) {
                                Intent intent = new Intent(context,OrderDetailActivity.class);
                                intent.putExtra("id",orderInquiryEntities.getDataList().get(position).getId());
                                startActivity(intent);
                            }
                        });

                        if( inquiryCategoryAdapter.getPage() <  (int)(Math.ceil(orderInquiryEntities.getCount() / 20))){
                            orderList.setCanAddMore(true);
                        }else {
                            orderList.setCanAddMore(false);
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                        inquiryCategoryAdapter = new InquiryCategoryAdapter(context, new ArrayList<OrderInquiryEntities.Data>());
                        orderList.setAdapter(inquiryCategoryAdapter);
                        orderList.setCanAddMore(false);
                    }

                    if (inquiryCategoryAdapter != null && orderInquiryEntities != null){
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
                    }
                }
                break;
            case REQUEST_CODE_ORDER_INQUIRY_MORE:
                orderList.onRefreshComplete();
                if(data != null){
                    OrderInquiryEntities orderInquiryEntities = Parsers.getOrderInquiryEntities(data);
                    inquiryCategoryAdapter.addDatas(orderInquiryEntities.getDataList());
                    if( inquiryCategoryAdapter.getPage() < orderInquiryEntities.getCount()){
                        orderList.setCanAddMore(true);
                    }else {
                        orderList.setCanAddMore(false);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loading_img_refresh:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData(false);
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_ORDER_INQUIRY:
                setLoadingStatus(LoadingStatus.RETRY);
                orderList.setVisibility(View.GONE);
                break;
            case REQUEST_CODE_ORDER_INQUIRY_MORE:
                setLoadingStatus(LoadingStatus.RETRY);
                orderList.setVisibility(View.GONE);
                break;
        }
    }
}
