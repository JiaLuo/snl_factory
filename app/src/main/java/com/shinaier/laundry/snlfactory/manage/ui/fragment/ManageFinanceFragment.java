package com.shinaier.laundry.snlfactory.manage.ui.fragment;

import android.content.Context;
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
import com.shinaier.laundry.snlfactory.manage.adapter.ManageDetailAdapter;
import com.shinaier.laundry.snlfactory.manage.ui.activity.ManageFinanceActivity;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.ManageFinanceEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/2/10.
 */

public class ManageFinanceFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_FINANCE_DETAIL = 0x1;
    private static final int REQUEST_CODE_FINANCE_DETAIL_MORE = 0x2;

    private String status;
    private Context context;
    private FootLoadingListView financeList;
    private ManageDetailAdapter adapter;

    public void setArgs(Context context, String status) {
        this.status = status;
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_finance_frag, null);
        initView(view);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        financeList = (FootLoadingListView) view.findViewById(R.id.finance_list);
        financeList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        params.put("token",UserCenter.getToken(context));
        if(status.equals(ManageFinanceActivity.INCOME)|| status.equals(ManageFinanceActivity.WITHDRAW)){
            params.put("type",status);
        }
        requestHttpData(Constants.Urls.URL_POST_MANAGE_FINANCE_DETAIL,REQUEST_CODE_FINANCE_DETAIL, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_FINANCE_DETAIL:
                if(data != null){
                    financeList.onRefreshComplete();
                    ManageFinanceEntities manageFinanceEntities = Parsers.getManageFinanceEntities(data);
                    if (manageFinanceEntities != null){
                        if (manageFinanceEntities.getCode() == 0){
                            if (manageFinanceEntities.getResult() != null && manageFinanceEntities.getResult().getRecords() != null &&
                                    manageFinanceEntities.getResult().getRecords().size() > 0){
                                setLoadingStatus(LoadingStatus.GONE);
                                financeList.setVisibility(View.VISIBLE);
                                adapter = new ManageDetailAdapter(context,manageFinanceEntities.getResult().getRecords());
                                financeList.setAdapter(adapter);
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                                financeList.setVisibility(View.GONE);
                                ManageDetailAdapter adapter = new ManageDetailAdapter(context,new ArrayList<ManageFinanceEntities
                                        .ManageFinanceResult.ManageFinanceRecord>());
                                financeList.setAdapter(adapter);
                            }
                        }else {
                            ToastUtil.shortShow(context,manageFinanceEntities.getMsg());
                        }
                    }

                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                    financeList.setVisibility(View.GONE);
                    ManageDetailAdapter adapter = new ManageDetailAdapter(context,new ArrayList<ManageFinanceEntities
                            .ManageFinanceResult.ManageFinanceRecord>());
                    financeList.setAdapter(adapter);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_img_refresh:
                loadData();
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_FINANCE_DETAIL:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
