package com.shinaier.laundry.snlfactory.manage.ui.fragment;

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
import com.common.utils.ToastUtil;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.fragment.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.OrderCategoryAdapter;
import com.shinaier.laundry.snlfactory.manage.ui.activity.OrderDetailActivity;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OrderSearchEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/12/23.
 */

public class OrderCategoryFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_ORDER_CATEGORY = 0x1;
    private static final int REQUEST_CODE_ORDER_CATEGORY_MORE = 0x2;

    private Context context;
    private boolean isOnline;
    private String date;
    private FootLoadingListView orderCategoryList;
    private OrderCategoryAdapter orderCategoryAdapter;
    private OrderSearchEntity orderSearchEntity;

    public void setArgs(boolean isOnline,String date) {
        this.isOnline = isOnline;
        this.date = date;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.order_category_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initView(view);
        loadData(isOnline,"",false);
    }

    /**
     *  订单查询请求网络
     * @param isOnline 是否是线上订单
     * @param number 查询订单号
     * @param isMore 是否是加载更多
     */
    private void loadData(boolean isOnline, String number, boolean isMore) {
        int code;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        params.put("number",number);
        if (isOnline){ //是否为线上:1-是,0-否
            params.put("is_online","1");
        }else {
            params.put("is_online","0");
        }

        if (isMore){
            params.put("page", orderCategoryAdapter.getPage() + 1 + "");
            code = REQUEST_CODE_ORDER_CATEGORY_MORE;
        }else {
            params.put("page","1");
            code = REQUEST_CODE_ORDER_CATEGORY;
        }

        params.put("limit","10");
        params.put("date",date);
        requestHttpData(Constants.Urls.URL_POST_ORDER_SEARCH,code, FProtocol.HttpMethod.POST,params);

    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        orderCategoryList = (FootLoadingListView) view.findViewById(R.id.order_category_list);
        orderCategoryList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(isOnline,"",false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(isOnline,"",true);
            }
        });
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_ORDER_CATEGORY:
                if (data != null){
                    orderCategoryList.onRefreshComplete();
                    orderSearchEntity = Parsers.getOrderSearchEntity(data);
                    if (orderSearchEntity != null){
                        if (orderSearchEntity.getCode() == 0){
                            if (orderSearchEntity.getResults() != null && orderSearchEntity.getResults().size() > 0){
                                setLoadingStatus(LoadingStatus.GONE);
                                orderCategoryList.setVisibility(View.VISIBLE);
                                orderCategoryAdapter = new OrderCategoryAdapter(context, orderSearchEntity.getResults());
                                orderCategoryList.setAdapter(orderCategoryAdapter);

                                orderCategoryAdapter.setPositionListener(new OrderCategoryAdapter.PositionListener() {
                                    @Override
                                    public void onPositionClick(int position) {
                                        Intent intent = new Intent(context,OrderDetailActivity.class);
                                        intent.putExtra("id",orderSearchEntity.getResults().get(position).getId());
//                                        intent.putExtra("is_online",isOnline);
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

                                if( orderCategoryAdapter.getPage() <  orderSearchEntity.getPageCount()){
                                    orderCategoryList.setCanAddMore(true);
                                }else {
                                    orderCategoryList.setCanAddMore(false);
                                }
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                                orderCategoryAdapter = new OrderCategoryAdapter(context, new ArrayList<OrderSearchEntity.OrderSearchResult>());
                                orderCategoryList.setAdapter(orderCategoryAdapter);
                                orderCategoryList.setCanAddMore(false);
                            }
                        }else {
                            ToastUtil.shortShow(context, orderSearchEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ORDER_CATEGORY_MORE:
                orderCategoryList.onRefreshComplete();
                if(data != null){
                    OrderSearchEntity orderSearchEntity = Parsers.getOrderSearchEntity(data);
                    orderCategoryAdapter.addDatas(orderSearchEntity.getResults());
                    if( orderCategoryAdapter.getPage() < orderSearchEntity.getPageCount()){
                        orderCategoryList.setCanAddMore(true);
                    }else {
                        orderCategoryList.setCanAddMore(false);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_img_refresh:
                loadData(isOnline,"",false);
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_ORDER_CATEGORY:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
