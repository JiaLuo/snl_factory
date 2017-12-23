package com.shinaier.laundry.snlfactory.ordermanage.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OrderSendEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategorySendAdapter;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/12/16.
 */

public class OrderManageWillSendFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_WILL_SEND = 0x1;
    private static final int REQUEST_CODE_ORDER_SEND = 0x2;
    private static final int REQUEST_CODE_WILL_SEND_MORE = 0x3;

    private Context context;
    private FootLoadingListView orderManageWillSendList;
    private OrderSendEntities orderSendEntities;
    private CategorySendAdapter categorySendAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_manage_will_send_frag,container,false);
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
        loadData(false);
    }

    private void loadData(boolean isMore) {
        int code;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        if (isMore){
            params.put("page",categorySendAdapter.getPage() + 1 + "");
            code = REQUEST_CODE_WILL_SEND_MORE;
        }else {
            params.put("page","1");
            code = REQUEST_CODE_WILL_SEND;
        }
        params.put("limit","10");
        requestHttpData(Constants.Urls.URL_POST_WILL_SEND,code,
                FProtocol.HttpMethod.POST,
                params);
    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        orderManageWillSendList = (FootLoadingListView) view.findViewById(R.id.order_manage_will_send_list);
        orderManageWillSendList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode) {
            case REQUEST_CODE_WILL_SEND:
                if (data != null) {
                    orderManageWillSendList.onRefreshComplete();
                    orderSendEntities = Parsers.getOrderSendEntities(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if (orderSendEntities != null) {
                        if (orderSendEntities.getCode() == 0) {
                            if (orderSendEntities.getResults() != null && orderSendEntities.getResults().size() > 0) {
                                setLoadingStatus(LoadingStatus.GONE);
                                orderManageWillSendList.setVisibility(View.VISIBLE);
                                categorySendAdapter = new CategorySendAdapter(context, orderSendEntities.getResults());
//                                categorySendAdapter.setCountNum(orderSendEntities.getDataCount());
                                orderManageWillSendList.setAdapter(categorySendAdapter);
                                categorySendAdapter.setPositionListener(new CategorySendAdapter.PositionListener() {
                                    @Override
                                    public void onPositionClick(final int position) {
                                        final String id = orderSendEntities.getResults().get(position).getId();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("提示");
                                        builder.setMessage("是否确认配送成功？");
                                        builder.setNegativeButton("取消", null);
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                                params.put("token",UserCenter.getToken(context));
                                                params.put("oid", id);
                                                requestHttpData(Constants.Urls.URL_POST_SEND_ORDER,REQUEST_CODE_ORDER_SEND, FProtocol.HttpMethod.POST,params);

                                            }
                                        });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                });

                                categorySendAdapter.setGotoDetailListener(new CategorySendAdapter.GotoDetailListener() {
                                    @Override
                                    public void onClick(int position) {
//                                        Intent intent = new Intent(context,OrderDetailActivity.class);
//                                        intent.putExtra("extra_from", OrderCategoryFragment.FROM_ONLIEN);
//                                        intent.putExtra("id", orderSendEntities.getResults().get(position).getId());
//                                        startActivity(intent);
                                    }
                                });

                                categorySendAdapter.setSendShowMoreListener(new CategorySendAdapter.SendShowMoreListener() {
                                    @Override
                                    public void onClick(int position, ImageView iv, TextView tv) {
                                        OrderSendEntities.OrderSendResult sendData = orderSendEntities.getResults().get(position);
                                        if (sendData.isOpen){
                                            iv.setBackgroundResource(R.drawable.ic_up_arrow);
                                            tv.setText("隐藏更多选项");
                                        }else{
                                            iv.setBackgroundResource(R.drawable.ic_down_arrow);
                                            tv.setText("查看更多");
                                        }
                                        categorySendAdapter.notifyDataSetChanged();
                                    }
                                });

                                categorySendAdapter.setTelPhoneListener(new CategorySendAdapter.TelPhoneListener() {
                                    @Override
                                    public void onTelPhone(int position) {
                                        tell(orderSendEntities.getResults().get(position).getuMobile());
                                    }
                                });


                                if( categorySendAdapter.getPage() < orderSendEntities.getCount()){
                                    orderManageWillSendList.setCanAddMore(true);
                                }else {
                                    orderManageWillSendList.setCanAddMore(false);
                                }
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                                categorySendAdapter = new CategorySendAdapter(context, new ArrayList<OrderSendEntities.OrderSendResult>());
                                orderManageWillSendList.setAdapter(categorySendAdapter);
                                orderManageWillSendList.setCanAddMore(false);
                            }

                        } else {
                            ToastUtil.shortShow(context, orderSendEntities.getMsg());
                        }
                    }

                }
                break;
            case REQUEST_CODE_ORDER_SEND:

                break;
            case REQUEST_CODE_WILL_SEND_MORE:
                orderManageWillSendList.onRefreshComplete();
                if(data != null){
                    OrderSendEntities orderSendEntities = Parsers.getOrderSendEntities(data);
                    categorySendAdapter.addDatas(orderSendEntities.getResults());
                    if( categorySendAdapter.getPage() < orderSendEntities.getCount()){
                        orderManageWillSendList.setCanAddMore(true);
                    }else {
                        orderManageWillSendList.setCanAddMore(false);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_img_refresh:
                loadData(false);
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_WILL_SEND:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
