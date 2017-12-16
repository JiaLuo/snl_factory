package com.shinaier.laundry.snlfactory.ordermanage.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OrderDisposeEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategoryDisposeAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.CancelOrderActivity;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * 待处理fragment
 * Created by 张家洛 on 2017/12/16.
 */

public class OrderManageWillDisposeFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_WILL_DISPOSE = 0x1;
    private static final int REQUEST_CODE_ORDER_CONFRIM = 0x2;

    private Context context;
    private FootLoadingListView orderManageWillDisposeList;
    private OrderDisposeEntities orderDisposeEntities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_manage_will_dispose_frag,container,false);
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

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        orderManageWillDisposeList = view.findViewById(R.id.order_manage_will_dispose_list);
        orderManageWillDisposeList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        requestHttpData(Constants.Urls.URL_POST_WILL_DISPOSE,REQUEST_CODE_WILL_DISPOSE,
                FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_WILL_DISPOSE:
                if (data != null){
                    orderManageWillDisposeList.onRefreshComplete();
                    orderDisposeEntities = Parsers.getOrderDisposeEntities(data);
                    if (orderDisposeEntities != null){
                        if (orderDisposeEntities.getCode() == 0){
                            CategoryDisposeAdapter categoryDisposeAdapter;
                            if (orderDisposeEntities.getResults() != null &&
                                    orderDisposeEntities.getResults().size() > 0){
                                setLoadingStatus(LoadingStatus.GONE);

                                orderManageWillDisposeList.setVisibility(View.VISIBLE);
                                categoryDisposeAdapter = new CategoryDisposeAdapter(context, orderDisposeEntities.getResults());
                                orderManageWillDisposeList.setAdapter(categoryDisposeAdapter);
                                categoryDisposeAdapter.setPositionListener(new CategoryDisposeAdapter.PositionListener() {
                                    @Override
                                    public void cancelOnClick(int position) {
                                        Intent intent = new Intent(context,CancelOrderActivity.class);
                                        intent.putExtra("id", orderDisposeEntities.getResults().get(position).getId());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void confirmOnClick(int position) {
                                        IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                        params.put("token",UserCenter.getToken(context));
                                        params.put("oid", orderDisposeEntities.getResults().get(position).getId());
                                        requestHttpData(Constants.Urls.URL_POST_CONFRIM_ORDER,REQUEST_CODE_ORDER_CONFRIM, FProtocol.HttpMethod.POST,params);
                                    }

                                    @Override
                                    public void onTellClick(int position) {
                                        tell(orderDisposeEntities.getResults().get(position).getuMobile());
                                    }

                                    @Override
                                    public void onGotoDetail(int position) {
                                        // TODO: 2017/12/16 页面有问题
                                        Intent intent = new Intent(context,OrderDetailActivity.class);
                                        intent.putExtra("id",orderDisposeEntities.getResults().get(position).getId());
                                        startActivity(intent);
                                    }
                                });
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                                categoryDisposeAdapter = new CategoryDisposeAdapter(context, new ArrayList<OrderDisposeEntities.OrderDisposeResult>());
                                orderManageWillDisposeList.setAdapter(categoryDisposeAdapter);
                            }
                        }else {
                            ToastUtil.shortShow(context, orderDisposeEntities.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ORDER_CONFRIM:
                Entity entity = Parsers.getEntity(data);
                if(entity.getRetcode() == 0 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("确认订单成功");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loadData();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else {
                    ToastUtil.shortShow(context,entity.getStatus());
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
            case REQUEST_CODE_WILL_DISPOSE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
