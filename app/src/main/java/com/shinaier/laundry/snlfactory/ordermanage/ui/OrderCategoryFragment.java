package com.shinaier.laundry.snlfactory.ordermanage.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.ui.OrderDetailActivity;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleanEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleaningEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderDisposeEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderSendEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderTakeOrderEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategoryCleanAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategoryCleaningAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategoryDisposeAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategorySendAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategoryTakeOrderAdapter;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Created by 张家洛 on 2017/10/25.
 */

public class OrderCategoryFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_ORDER_MANAGE = 0x1;
    private static final int REQUEST_CODE_ORDER_CONFRIM = 0x2;
    private static final int REQUEST_CODE_ORDER_MANAGE_MORE = 0x3;
    private static final int REQUEST_CODE_TAKE_ORDER_MORE = 0x4;
    private static final int REQUEST_CODE_CLEAN_MORE = 0x5;
    private static final int REQUEST_CODE_CLEANING_MORE = 0x6;
    private static final int REQUEST_CODE_SEND_ORDER_MORE = 0x7;
    private static final int REQUEST_CODE_ORDER_SEND =0x8;
    private static final int REQUEST_CODE_ORDER_CLEANED = 0x9;
    private static final int REQUEST_CODE_CHECKED_CLOTHES = 0x10;
    public static final int ONLINE_ADDITEM = 0x11;

    private Context context;
    private int status;
    private FootLoadingListView orderManageList;
    private CategoryCleanAdapter categoryCleanAdapter;
    private CategoryDisposeAdapter manageCategoryDisposeAdapter;
    private OrderDisposeEntities orderDisposeEntities;
    private CategoryTakeOrderAdapter categoryTakeOrderAdapter;
    private OrderTakeOrderEntities orderTakeOrderEntities;
    private OrderCleanEntities orderCleanEntities;
    private CategoryCleaningAdapter categoryCleaningAdapter;
    private CategorySendAdapter categorySendAdapter;

    public void setArgs(Context context, int status){
        this.context = context;
        this.status = status;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_category_frag, null);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setLoadingStatus(LoadingStatus.LOADING);
        loadData(false);
    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        mImgLoadingRetry.setOnClickListener(this);
        orderManageList = (FootLoadingListView) view.findViewById(R.id.order_manage_list);
        orderManageList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

    private void loadData(boolean isMore) {
        int code = 0;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        params.put("state",String.valueOf(status));
        params.put("limit","20");
        if(isMore){
            if(status == 0){
                params.put("page",manageCategoryDisposeAdapter.getPage() + 1 +"");
                code = REQUEST_CODE_ORDER_MANAGE_MORE;
            }else if(status == 1) {
                params.put("page",categoryTakeOrderAdapter.getPage() + 1 +"");
                code = REQUEST_CODE_TAKE_ORDER_MORE;
            }else if(status == 2){
                params.put("page",categoryCleanAdapter.getPage() + 1 +"");
                code = REQUEST_CODE_CLEAN_MORE;
            }else if(status == 3){
                params.put("page",categoryCleaningAdapter.getPage() + 1 +"");
                code = REQUEST_CODE_CLEANING_MORE;
            }else {
                params.put("page",categorySendAdapter.getPage() + 1 +"");
                code = REQUEST_CODE_SEND_ORDER_MORE;
            }

        }else {
            params.put("page","1");
            code = REQUEST_CODE_ORDER_MANAGE;
        }
        requestHttpData(Constants.Urls.URL_POST_ORDER_MANAGE,code, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loading_img_refresh:
                loadData(false);
                setLoadingStatus(LoadingStatus.LOADING);

                break;
        }
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_ORDER_MANAGE:
                if(data != null){
                    orderManageList.onRefreshComplete();
                    if(status == 0){
                        orderDisposeEntities = Parsers.getOrderManageEntities(data);
                        if(orderDisposeEntities != null && orderDisposeEntities.getDatas() != null &&
                                orderDisposeEntities.getDatas().size() > 0){
                            setLoadingStatus(LoadingStatus.GONE);
                            orderManageList.setVisibility(View.VISIBLE);
                            manageCategoryDisposeAdapter = new CategoryDisposeAdapter(context, orderDisposeEntities.getDatas());
                            manageCategoryDisposeAdapter.setCountNum(orderDisposeEntities.getDataCount());
                            orderManageList.setAdapter(manageCategoryDisposeAdapter);
                            manageCategoryDisposeAdapter.setPositionListener(new CategoryDisposeAdapter.PositionListener() {
                                @Override
                                public void cancelOnClick(int position) {
                                    Intent intent = new Intent(context,CancelOrderActivity.class);
                                    intent.putExtra("id", orderDisposeEntities.getDatas().get(position).getId());
                                    startActivity(intent);
                                }

                                @Override
                                public void confirmOnClick(int position) {
                                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                    params.put("token",UserCenter.getToken(context));
                                    params.put("state",String.valueOf(status));
                                    params.put("id", orderDisposeEntities.getDatas().get(position).getId());
                                    requestHttpData(Constants.Urls.URL_POST_ORDER_MANAGE,REQUEST_CODE_ORDER_CONFRIM, FProtocol.HttpMethod.POST,params);
                                }
                            });

                            manageCategoryDisposeAdapter.setTelPhoneListener(new CategoryDisposeAdapter.TelPhoneListener() {
                                @Override
                                public void onTelPhone(int position) {
                                    tell(orderDisposeEntities.getDatas().get(position).getPhone());
                                }
                            });

                            manageCategoryDisposeAdapter.setGotoDetailListener(new CategoryDisposeAdapter.GotoDetailListener() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(context,OrderDetailActivity.class);
                                    intent.putExtra("id",orderDisposeEntities.getDatas().get(position).getId());
                                    startActivity(intent);
                                }
                            });

                            if( manageCategoryDisposeAdapter.getPage() < orderDisposeEntities.getCount()){
                                orderManageList.setCanAddMore(true);
                            }else {
                                orderManageList.setCanAddMore(false);
                            }
                        }else {
                            setLoadingStatus(LoadingStatus.EMPTY);
                            manageCategoryDisposeAdapter = new CategoryDisposeAdapter(context, new ArrayList<OrderDisposeEntities.Data>());
                            orderManageList.setAdapter(manageCategoryDisposeAdapter);
                            orderManageList.setCanAddMore(false);
                        }
                    }else if(status == 1){
                        orderTakeOrderEntities = Parsers.getOrderTakeOrderEntities(data);
                        if(orderTakeOrderEntities != null && orderTakeOrderEntities.getTakeOrderDataList() != null &&
                                orderTakeOrderEntities.getTakeOrderDataList().size() > 0){
                            setLoadingStatus(LoadingStatus.GONE);
                            orderManageList.setVisibility(View.VISIBLE);
                            categoryTakeOrderAdapter = new CategoryTakeOrderAdapter(context, orderTakeOrderEntities.getTakeOrderDataList());
                            categoryTakeOrderAdapter.setCountNum(orderTakeOrderEntities.getDataCount());
                            orderManageList.setAdapter(categoryTakeOrderAdapter);
                            categoryTakeOrderAdapter.setGotoDetailListener(new CategoryTakeOrderAdapter.GotoDetailListener() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(context,OrderDetailActivity.class);
                                    intent.putExtra("id",orderTakeOrderEntities.getTakeOrderDataList().get(position).getId());
                                    startActivity(intent);
                                }
                            });

                            categoryTakeOrderAdapter.setPositionListener(new CategoryTakeOrderAdapter.PositionListener() {
                                @Override
                                public void cancelOnClick(int position) {
                                    Intent intent = new Intent(context,CancelOrderActivity.class);
                                    intent.putExtra("id", orderTakeOrderEntities.getTakeOrderDataList().get(position).getId());
                                    startActivity(intent);
                                }

                                @Override
                                public void confirmOnClick(int position) {
                                    String id = orderTakeOrderEntities.getTakeOrderDataList().get(position).getId();
                                    if (orderTakeOrderEntities.getTakeOrderDataList().get(position).getItemState() == 1){
                                        LogUtil.e("zhang","3333333333333333");
                                        Intent intent = new Intent(context,CraftworkAddPriceActivity.class);
                                        intent.putExtra("id",id);
                                        startActivity(intent);
                                    } else {
                                        LogUtil.e("zhang","4444444444444444");
                                        Intent intent = new Intent(context,AddProjectActivity.class);
                                        intent.putExtra("orderId",id);
                                        intent.putExtra("extra_from",ONLINE_ADDITEM);
                                        startActivity(intent);
                                    }
                                }
                            });

                            categoryTakeOrderAdapter.setTelNumListener(new CategoryTakeOrderAdapter.TelNumListener() {
                                @Override
                                public void onTelPhone(int position) {
                                    tell(orderTakeOrderEntities.getTakeOrderDataList().get(position).getPhone());
                                }
                            });

                            if( categoryTakeOrderAdapter.getPage() < orderTakeOrderEntities.getCount()){
                                orderManageList.setCanAddMore(true);
                            }else {
                                orderManageList.setCanAddMore(false);
                            }
                        }else {
                            setLoadingStatus(LoadingStatus.EMPTY);
                            categoryTakeOrderAdapter = new CategoryTakeOrderAdapter(context, new ArrayList<OrderTakeOrderEntities.TakeOrderData>());
                            orderManageList.setAdapter(categoryTakeOrderAdapter);
                            orderManageList.setCanAddMore(false);
                        }
                    }else if(status == 2){
                        orderCleanEntities = Parsers.getOrderCleanEntities(data);
                        if(orderCleanEntities != null && orderCleanEntities.getDatas() != null &&
                                orderCleanEntities.getDatas().size() > 0){
                            setLoadingStatus(LoadingStatus.GONE);
                            orderManageList.setVisibility(View.VISIBLE);
                            categoryCleanAdapter = new CategoryCleanAdapter(context, orderCleanEntities.getDatas());
                            categoryCleanAdapter.setCountNum(orderCleanEntities.getDataCount());
                            orderManageList.setAdapter(categoryCleanAdapter);

                            categoryCleanAdapter.setGotoDetailListener(new CategoryCleanAdapter.GotoDetailListener() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(context,OrderDetailActivity.class);
                                    intent.putExtra("id",orderCleanEntities.getDatas().get(position).getId());
                                    startActivity(intent);
                                }
                            });

                            categoryCleanAdapter.setCleanShowMoreListener(new CategoryCleanAdapter.CleanShowMoreListener() {
                                @Override
                                public void onClick(int position, ImageView iv, TextView tv) {
                                    OrderCleanEntities.CleanData cleanData = orderCleanEntities.getDatas().get(position);
                                    if (cleanData.isOpen){
                                        iv.setBackgroundResource(R.drawable.ic_up_arrow);
                                        tv.setText("隐藏更多选项");
                                    }else{
                                        iv.setBackgroundResource(R.drawable.ic_down_arrow);
                                        tv.setText("查看更多");
                                    }
                                    categoryCleanAdapter.notifyDataSetChanged();
                                }
                            });

                            categoryCleanAdapter.setPositionListener(new CategoryCleanAdapter.PositionListener() {
                                @Override
                                public void onCheck(int position) {
                                    Intent intent = new Intent(context,CheckClothesActivity.class);
                                    intent.putExtra("id", orderCleanEntities.getDatas().get(position).getId());
                                    startActivity(intent);
                                }

                                @Override
                                public void onChecked(int position) {
                                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                    params.put("token",UserCenter.getToken(context));
                                    params.put("orderid",orderCleanEntities.getDatas().get(position).getId());
                                    requestHttpData(Constants.Urls.URL_POST_CHECKED_CLOTHES,REQUEST_CODE_CHECKED_CLOTHES,
                                            FProtocol.HttpMethod.POST,params);
                                }
                            });

                            categoryCleanAdapter.setTelPhoneListener(new CategoryCleanAdapter.TelPhoneListener() {
                                @Override
                                public void onTelPhone(int position) {
                                    tell(orderCleanEntities.getDatas().get(position).getPhone());
                                }
                            });

                            if( categoryCleanAdapter.getPage() < orderCleanEntities.getCount()){
                                orderManageList.setCanAddMore(true);
                            }else {
                                orderManageList.setCanAddMore(false);
                            }

                        }else {
                            setLoadingStatus(LoadingStatus.EMPTY);
                            categoryCleanAdapter = new CategoryCleanAdapter(context, new ArrayList<OrderCleanEntities.CleanData>());
                            orderManageList.setAdapter(categoryCleanAdapter);
                            orderManageList.setCanAddMore(false);
                        }
                    }else if(status == 3){
                        final OrderCleaningEntities orderCleaningEntities = Parsers.getOrderCleaningEntities(data);
                        if(orderCleaningEntities != null && orderCleaningEntities.getCleaningDatas() != null &&
                                orderCleaningEntities.getCleaningDatas().size() > 0){
                            setLoadingStatus(LoadingStatus.GONE);
                            orderManageList.setVisibility(View.VISIBLE);
                            categoryCleaningAdapter = new CategoryCleaningAdapter(context,orderCleaningEntities.getCleaningDatas());
                            categoryCleaningAdapter.setCountNum(orderCleaningEntities.getDataCount());
                            orderManageList.setAdapter(categoryCleaningAdapter);
                            categoryCleaningAdapter.setPositionListener(new CategoryCleaningAdapter.PositionListener() {
                                @Override
                                public void onPositionListener(int position) {
                                    if (orderCleaningEntities.getCleaningDatas().get(position).getThrough().equals("1")){
                                        final String id = orderCleaningEntities.getCleaningDatas().get(position).getId();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("提示");
                                        builder.setMessage("是否确认清洗完成？");
                                        builder.setNegativeButton("取消", null);
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                                params.put("token",UserCenter.getToken(context));
                                                params.put("id", id);
                                                requestHttpData(Constants.Urls.URL_POST_CLEANED_ORDER,REQUEST_CODE_ORDER_CLEANED, FProtocol.HttpMethod.POST,params);

                                            }
                                        });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }else {
                                        ToastUtil.shortShow(context,"订单中还有未挂号项目。");
                                    }
                                }
                            });

                            categoryCleaningAdapter.setGotoDetailListener(new CategoryCleaningAdapter.GotoDetailListener() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(context,OrderDetailActivity.class);
                                    intent.putExtra("id",orderCleaningEntities.getCleaningDatas().get(position).getId());
                                    startActivity(intent);
                                }
                            });

                            categoryCleaningAdapter.setCleaningShowMoreListener(new CategoryCleaningAdapter.CleaningShowMoreListener() {
                                @Override
                                public void onClick(int position, ImageView iv, TextView tv) {
                                    OrderCleaningEntities.CleaningData cleaningData = orderCleaningEntities.getCleaningDatas().get(position);
                                    if (cleaningData.isOpen){

                                        iv.setBackgroundResource(R.drawable.ic_up_arrow);
                                        tv.setText("隐藏更多选项");
                                    }else{
                                        iv.setBackgroundResource(R.drawable.ic_down_arrow);
                                        tv.setText("查看更多");
                                    }
                                    categoryCleaningAdapter.notifyDataSetChanged();
                                }
                            });

                            categoryCleaningAdapter.setTelPhoneListener(new CategoryCleaningAdapter.TelPhoneListener() {
                                @Override
                                public void onTelPhone(int position) {
                                    tell(orderCleaningEntities.getCleaningDatas().get(position).getPhone());
                                }
                            });

                            if( categoryCleaningAdapter.getPage() < orderCleaningEntities.getCount()){
                                orderManageList.setCanAddMore(true);
                            }else {
                                orderManageList.setCanAddMore(false);
                            }
                        }else {
                            setLoadingStatus(LoadingStatus.EMPTY);
                            categoryCleaningAdapter = new CategoryCleaningAdapter(context, new ArrayList<OrderCleaningEntities.CleaningData>());
                            orderManageList.setAdapter(categoryCleaningAdapter);
                            orderManageList.setCanAddMore(false);
                        }
                    }else {
                        final OrderSendEntities orderSendEntities = Parsers.getOrderSendEntities(data);
                        if(orderSendEntities != null && orderSendEntities.getSendDatas() != null &&
                                orderSendEntities.getSendDatas().size() > 0){
                            setLoadingStatus(LoadingStatus.GONE);
                            orderManageList.setVisibility(View.VISIBLE);
                            categorySendAdapter = new CategorySendAdapter(context,orderSendEntities.getSendDatas());
                            categorySendAdapter.setCountNum(orderSendEntities.getDataCount());
                            orderManageList.setAdapter(categorySendAdapter);
                            categorySendAdapter.setPositionListener(new CategorySendAdapter.PositionListener() {
                                @Override
                                public void onPositionClick(final int position) {
                                    final String id = orderSendEntities.getSendDatas().get(position).getId();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("提示");
                                    builder.setMessage("是否确认配送成功？");
                                    builder.setNegativeButton("取消", null);
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                            params.put("token",UserCenter.getToken(context));
                                            params.put("id", id);
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
                                    Intent intent = new Intent(context,OrderDetailActivity.class);
                                    intent.putExtra("id",orderSendEntities.getSendDatas().get(position).getId());
                                    startActivity(intent);
                                }
                            });

                            categorySendAdapter.setSendShowMoreListener(new CategorySendAdapter.SendShowMoreListener() {
                                @Override
                                public void onClick(int position, ImageView iv, TextView tv) {
                                    OrderSendEntities.SendData sendData = orderSendEntities.getSendDatas().get(position);
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
                                    tell(orderSendEntities.getSendDatas().get(position).getPhone());
                                }
                            });


                            if( categorySendAdapter.getPage() < orderSendEntities.getCount()){
                                orderManageList.setCanAddMore(true);
                            }else {
                                orderManageList.setCanAddMore(false);
                            }
                        }else {
                            setLoadingStatus(LoadingStatus.EMPTY);
                            categorySendAdapter = new CategorySendAdapter(context, new ArrayList<OrderSendEntities.SendData>());
                            orderManageList.setAdapter(categorySendAdapter);
                            orderManageList.setCanAddMore(false);
                        }
                    }

                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
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
                            loadData(false);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
            case REQUEST_CODE_ORDER_MANAGE_MORE:
                orderManageList.onRefreshComplete();
                if(data != null){
                    OrderDisposeEntities orderManageEntities = Parsers.getOrderManageEntities(data);
                    manageCategoryDisposeAdapter.addDatas(orderManageEntities.getDatas());
                    if( manageCategoryDisposeAdapter.getPage() < orderManageEntities.getCount()){
                        orderManageList.setCanAddMore(true);
                    }else {
                        orderManageList.setCanAddMore(false);
                    }
                }
                break;
            case REQUEST_CODE_TAKE_ORDER_MORE:
                orderManageList.onRefreshComplete();
                if(data != null){
                    OrderTakeOrderEntities orderTakeOrderEntities = Parsers.getOrderTakeOrderEntities(data);
                    categoryTakeOrderAdapter.addDatas(orderTakeOrderEntities.getTakeOrderDataList());
                    if( categoryTakeOrderAdapter.getPage() < orderTakeOrderEntities.getCount()){
                        orderManageList.setCanAddMore(true);
                    }else {
                        orderManageList.setCanAddMore(false);
                    }
                }
                break;
            case REQUEST_CODE_CLEAN_MORE:
                orderManageList.onRefreshComplete();
                if(data != null){
                    OrderCleanEntities orderCleanEntities = Parsers.getOrderCleanEntities(data);
                    categoryCleanAdapter.addDatas(orderCleanEntities.getDatas());
                    if( categoryCleanAdapter.getPage() < orderCleanEntities.getCount()){
                        orderManageList.setCanAddMore(true);
                    }else {
                        orderManageList.setCanAddMore(false);
                    }
                }
                break;
            case REQUEST_CODE_CLEANING_MORE:
                orderManageList.onRefreshComplete();
                if(data != null){
                    OrderCleaningEntities orderCleaningEntities = Parsers.getOrderCleaningEntities(data);
                    categoryCleaningAdapter.addDatas(orderCleaningEntities.getCleaningDatas());
                    if( categoryCleaningAdapter.getPage() < orderCleaningEntities.getCount()){
                        orderManageList.setCanAddMore(true);
                    }else {
                        orderManageList.setCanAddMore(false);
                    }
                }
                break;
            case REQUEST_CODE_SEND_ORDER_MORE:
                orderManageList.onRefreshComplete();
                if(data != null){
                    OrderSendEntities orderSendEntities = Parsers.getOrderSendEntities(data);
                    categorySendAdapter.addDatas(orderSendEntities.getSendDatas());
                    if( categorySendAdapter.getPage() < orderSendEntities.getCount()){
                        orderManageList.setCanAddMore(true);
                    }else {
                        orderManageList.setCanAddMore(false);
                    }
                }
                break;
            case REQUEST_CODE_ORDER_SEND:
                if(data != null){
                    Entity entity1 = Parsers.getEntity(data);
                    if(entity1 != null){
                        if(entity1.getRetcode() == 0){
                            ToastUtil.shortShow(context,"配送成功");
                            loadData(false);
                        }else {
                            ToastUtil.shortShow(context,entity1.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ORDER_CLEANED:
                if(data != null){
                    Entity entity2 = Parsers.getEntity(data);
                    if(entity2 != null){
                        if(entity2.getRetcode() == 0){
                            ToastUtil.shortShow(context,"清洗完成");
                            loadData(false);
                        }else {
                            ToastUtil.shortShow(context,entity2.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_CHECKED_CLOTHES:
                if(data != null){
                    Entity checkedEntity = Parsers.getEntity(data);
                    if(checkedEntity != null){
                        if(checkedEntity.getRetcode() == 2){
                            ToastUtil.shortShow(context,checkedEntity.getStatus());
                        }else if(checkedEntity.getRetcode() == 1){
                            ToastUtil.shortShow(context,checkedEntity.getStatus());
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("提示");
                            builder.setMessage("检查完成操作成功");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loadData(false);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }

                }
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_ORDER_MANAGE:
                setLoadingStatus(LoadingStatus.RETRY);
                orderManageList.setVisibility(View.GONE);
                break;
            case REQUEST_CODE_ORDER_CONFRIM:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_ORDER_MANAGE_MORE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_TAKE_ORDER_MORE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_CLEAN_MORE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_CLEANING_MORE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_SEND_ORDER_MORE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_ORDER_SEND:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_ORDER_CLEANED:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
