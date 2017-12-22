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
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.widget.FootLoadingListView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.fragment.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.ui.activity.OrderDetailActivity;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleanEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategoryCleanAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.CheckClothesActivity;

import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/12/16.
 */

public class OrderManageWillCleanFragment extends BaseFragment implements View.OnClickListener{
    private static final int REQUEST_CODE_WILL_CLEAN = 0x1;
    private static final int REQUEST_CODE_CHECKED_CLOTHES = 0x2;

    private Context context;
    private FootLoadingListView orderManageWillCleanList;
    private OrderCleanEntities orderCleanEntities;
    private CategoryCleanAdapter categoryCleanAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_manage_will_clean_frag,container,false);
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

        }else {
            params.put("page","1");
        }
        params.put("limit","10");
        requestHttpData(Constants.Urls.URL_POST_WILL_CLEAN,REQUEST_CODE_WILL_CLEAN, FProtocol.HttpMethod.POST,params);
    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        orderManageWillCleanList = (FootLoadingListView) view.findViewById(R.id.order_manage_will_clean_list);
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_WILL_CLEAN:
                if (data != null){
                    orderCleanEntities = Parsers.getOrderCleanEntities(data);
                    if (orderCleanEntities != null){
                        if (orderCleanEntities.getCode() == 0){
                            if (orderCleanEntities.getResults() != null && orderCleanEntities.getResults().size() > 0){
                                setLoadingStatus(LoadingStatus.GONE);
                                orderManageWillCleanList.setVisibility(View.VISIBLE);
                                categoryCleanAdapter = new CategoryCleanAdapter(context, orderCleanEntities.getResults());
                                categoryCleanAdapter.setCountNum(orderCleanEntities.getCount());
                                orderManageWillCleanList.setAdapter(categoryCleanAdapter);

                                categoryCleanAdapter.setGotoDetailListener(new CategoryCleanAdapter.GotoDetailListener() {
                                    @Override
                                    public void onClick(int position) {
                                        Intent intent = new Intent(context,OrderDetailActivity.class);
//                                        intent.putExtra("extra_from", OrderCategoryFragment.FROM_ONLIEN);
                                        intent.putExtra("id",orderCleanEntities.getResults().get(position).getId());
                                        startActivity(intent);
                                    }
                                });

                                categoryCleanAdapter.setCleanShowMoreListener(new CategoryCleanAdapter.CleanShowMoreListener() {
                                    @Override
                                    public void onClick(int position, ImageView iv, TextView tv) {
                                        OrderCleanEntities.OrderCleanResult cleanData = orderCleanEntities.getResults().get(position);
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
                                        intent.putExtra("id", orderCleanEntities.getResults().get(position).getId());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onChecked(int position) {
                                        IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                        params.put("token",UserCenter.getToken(context));
                                        params.put("oid",orderCleanEntities.getResults().get(position).getId());
                                        requestHttpData(Constants.Urls.URL_POST_CHECKED_CLOTHES,REQUEST_CODE_CHECKED_CLOTHES,
                                                FProtocol.HttpMethod.POST,params);
                                    }
                                });

                                categoryCleanAdapter.setTelPhoneListener(new CategoryCleanAdapter.TelPhoneListener() {
                                    @Override
                                    public void onTelPhone(int position) {
                                        tell(orderCleanEntities.getResults().get(position).getuMobile());
                                    }
                                });

                                if( categoryCleanAdapter.getPage() < orderCleanEntities.getPageCount()){
                                    orderManageWillCleanList.setCanAddMore(true);
                                }else {
                                    orderManageWillCleanList.setCanAddMore(false);
                                }
                            }
                        }else {
                            ToastUtil.shortShow(context, orderCleanEntities.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_CHECKED_CLOTHES:
                if(data != null){
                    Entity checkedEntity = Parsers.getEntity(data);
                    if(checkedEntity != null){
                        if(checkedEntity.getRetcode() == 0){
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
                        }else {
                            ToastUtil.shortShow(context,checkedEntity.getStatus());
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
                loadData(false);
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_WILL_CLEAN:

                break;
        }
    }
}
