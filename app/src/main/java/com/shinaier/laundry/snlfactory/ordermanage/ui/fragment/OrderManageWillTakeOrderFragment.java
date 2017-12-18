package com.shinaier.laundry.snlfactory.ordermanage.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.fragment.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.ui.activity.OrderDetailActivity;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OrderTakeOrderEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategoryTakeOrderAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.CancelOrderActivity;

import java.util.IdentityHashMap;


/**
 * 待收件
 * Created by 张家洛 on 2017/12/16.
 */

public class OrderManageWillTakeOrderFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_WILL_TAKE_ORDER = 0x1;
    private Context context;
    private FootLoadingListView orderManageWillTakeOrderList;
    private CategoryTakeOrderAdapter categoryTakeOrderAdapter;
    private OrderTakeOrderEntities orderTakeOrderEntities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_manage_will_take_order_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        requestHttpData(Constants.Urls.URL_POST_WILL_TAKE_ORDER,REQUEST_CODE_WILL_TAKE_ORDER,
                FProtocol.HttpMethod.POST,
                params);
    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        orderManageWillTakeOrderList = view.findViewById(R.id.order_manage_will_take_order_list);
        orderManageWillTakeOrderList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_WILL_TAKE_ORDER:
                if (data != null){
                    orderManageWillTakeOrderList.onRefreshComplete();
                    orderTakeOrderEntities = Parsers.getOrderTakeOrderEntities(data);
                    if (orderTakeOrderEntities != null){
                        if (orderTakeOrderEntities.getCode() == 0){
                            if (orderTakeOrderEntities.getResults() != null && orderTakeOrderEntities.getResults().size() > 0){
                                setLoadingStatus(LoadingStatus.GONE);
                                orderManageWillTakeOrderList.setVisibility(View.VISIBLE);
                                categoryTakeOrderAdapter = new CategoryTakeOrderAdapter(context, orderTakeOrderEntities.getResults());
                                orderManageWillTakeOrderList.setAdapter(categoryTakeOrderAdapter);
                                categoryTakeOrderAdapter.setPositionListener(new CategoryTakeOrderAdapter.PositionListener() {
                                    @Override
                                    public void cancelOnClick(int position) {
                                        Intent intent = new Intent(context,CancelOrderActivity.class);
                                        intent.putExtra("id", orderTakeOrderEntities.getResults().get(position).getId());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void confirmOnClick(int position) {
                                        // TODO: 2017/12/18 添加项目
                                    }

                                    @Override
                                    public void onTellClick(int position) {
                                        tell(orderTakeOrderEntities.getResults().get(position).getuMobile());
                                    }

                                    @Override
                                    public void onGotoDetail(int position) {
                                        // TODO: 2017/12/16 页面有问题
                                        Intent intent = new Intent(context,OrderDetailActivity.class);
                                        intent.putExtra("id",orderTakeOrderEntities.getResults().get(position).getId());
                                        startActivity(intent);
                                    }
                                });
                            }
                        }else {
                            ToastUtil.shortShow(context, orderTakeOrderEntities.getMsg());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_img_refresh:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_WILL_TAKE_ORDER:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
