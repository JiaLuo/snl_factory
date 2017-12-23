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
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.fragment.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleaningEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategoryCleaningAdapter;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/12/16.
 */

public class OrderManageWillCleaningFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_WILL_CLEANING = 0x1;
    private static final int REQUEST_CODE_ORDER_CLEANED = 0x2;
    private static final int REQUEST_CODE_WILL_CLEANING_MORE = 0x3;

    private Context context;
    private FootLoadingListView orderManageWillCleaningList;
    private CategoryCleaningAdapter categoryCleaningAdapter;
    private OrderCleaningEntities orderCleaningEntities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_manage_will_cleaning_frag,container,false);
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

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        orderManageWillCleaningList = (FootLoadingListView) view.findViewById(R.id.order_manage_will_cleaning_list);
        orderManageWillCleaningList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        int code;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        if (isMore){
            params.put("page",categoryCleaningAdapter.getPage() + 1 + "");
            code = REQUEST_CODE_WILL_CLEANING_MORE;
        }else {
            params.put("page","1");
            code = REQUEST_CODE_WILL_CLEANING;
        }
        params.put("limit","10");
        requestHttpData(Constants.Urls.URL_POST_WILL_CLEANING,code,
                FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_WILL_CLEANING:
                if (data != null){
                    orderManageWillCleaningList.onRefreshComplete();
                    orderCleaningEntities = Parsers.getOrderCleaningEntities(data);
                    if (orderCleaningEntities != null){
                        if (orderCleaningEntities.getCode() == 0){
                            if (orderCleaningEntities.getResults() != null && orderCleaningEntities.getResults().size() > 0){
                                setLoadingStatus(LoadingStatus.GONE);
                                orderManageWillCleaningList.setVisibility(View.VISIBLE);
                                categoryCleaningAdapter = new CategoryCleaningAdapter(context,
                                        orderCleaningEntities.getResults());
                                orderManageWillCleaningList.setAdapter(categoryCleaningAdapter);

                                categoryCleaningAdapter.setGotoDetailListener(new CategoryCleaningAdapter.GotoDetailListener() {
                                    @Override
                                    public void onClick(int position) {
                                        LogUtil.e("zhang","进入订单详情");
                                    }
                                });

                                categoryCleaningAdapter.setTelPhoneListener(new CategoryCleaningAdapter.TelPhoneListener() {
                                    @Override
                                    public void onTelPhone(int position) {
                                        tell(orderCleaningEntities.getResults().get(position).getuMobile());
                                    }
                                });

                                categoryCleaningAdapter.setCleaningShowMoreListener(new CategoryCleaningAdapter.CleaningShowMoreListener() {
                                    @Override
                                    public void onClick(int position, ImageView iv, TextView tv) {
                                        OrderCleaningEntities.OrderCleaningResult orderCleaningResult = orderCleaningEntities.getResults().get(position);
                                        if (orderCleaningResult.isOpen){
                                            iv.setBackgroundResource(R.drawable.ic_up_arrow);
                                            tv.setText("隐藏更多选项");
                                        }else{
                                            iv.setBackgroundResource(R.drawable.ic_down_arrow);
                                            tv.setText("查看更多");
                                        }
                                        categoryCleaningAdapter.notifyDataSetChanged();
                                    }
                                });

                                categoryCleaningAdapter.setPositionListener(new CategoryCleaningAdapter.PositionListener() {
                                    @Override
                                    public void onPositionListener(final int position) {
                                        final String id = orderCleaningEntities.getResults().get(position).getId();

                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("提示");
                                        builder.setMessage("是否确认清洗完成？");
                                        builder.setNegativeButton("取消", null);
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
                                                params.put("token",UserCenter.getToken(context));
                                                params.put("oid",id);
                                                requestHttpData(Constants.Urls.URL_POST_CLEANED_ORDER,REQUEST_CODE_ORDER_CLEANED, FProtocol.HttpMethod.POST,params);

                                            }
                                        });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                });
                                if( categoryCleaningAdapter.getPage() < orderCleaningEntities.getCount()){
                                    orderManageWillCleaningList.setCanAddMore(true);
                                }else {
                                    orderManageWillCleaningList.setCanAddMore(false);
                                }
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                                categoryCleaningAdapter = new CategoryCleaningAdapter(context, new ArrayList<OrderCleaningEntities.OrderCleaningResult>());
                                orderManageWillCleaningList.setAdapter(categoryCleaningAdapter);
                                orderManageWillCleaningList.setCanAddMore(false);
                            }
                        }else {
                            ToastUtil.shortShow(context, orderCleaningEntities.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ORDER_CLEANED:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity.getRetcode() == 0){
                        ToastUtil.shortShow(context,"清洗完成");
                        loadData(false);
                    }else {
                        ToastUtil.shortShow(context,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_WILL_CLEANING_MORE:
                orderManageWillCleaningList.onRefreshComplete();
                if(data != null){
                    OrderCleaningEntities orderCleaningEntities = Parsers.getOrderCleaningEntities(data);
                    categoryCleaningAdapter.addDatas(orderCleaningEntities.getResults());
                    if( categoryCleaningAdapter.getPage() < orderCleaningEntities.getCount()){
                        orderManageWillCleaningList.setCanAddMore(true);
                    }else {
                        orderManageWillCleaningList.setCanAddMore(false);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loading_img_refresh:
                loadData(false);
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_WILL_CLEANING:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
