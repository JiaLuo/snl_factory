package com.shinaier.laundry.snlfactory.manage.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.common.network.FProtocol;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.ManageDetailAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.ManageFinanceDetailEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/2/10.
 */

public class ManageFinanceFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_FINANCE_DETAIL = 0x1;
    private static final int REQUEST_CODE_FINANCE_DETAIL_MORE = 0x2;

    private int status;
    private Context context;
    private FootLoadingListView financeList;
    private ManageDetailAdapter adapter;

    public void setArgs(Context context, int status) {
        this.status = status;
        this.context = context;
    }

    public void setArgs(Context context){
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
        loadData(false);
    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        financeList = (FootLoadingListView) view.findViewById(R.id.finance_list);
        financeList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        int requestCode;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));

        if(status == 1 || status == 3){
            params.put("state", String.valueOf(status));
        }

        if(isMore){
            params.put("page", String.valueOf(adapter.getPage() + 1));
            requestCode = REQUEST_CODE_FINANCE_DETAIL_MORE;
        }else {
            params.put("page","1");
            requestCode = REQUEST_CODE_FINANCE_DETAIL;
        }

        params.put("limit","20");
        requestHttpData(Constants.Urls.URL_POST_MANAGE_FINANCE_DETAIL,requestCode, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_FINANCE_DETAIL:
                if(data != null){
                    financeList.onRefreshComplete();
                    ManageFinanceDetailEntities manageFinanceDetailEntities = Parsers.getManageFinanceDetailEntities(data);

                    if(manageFinanceDetailEntities != null && manageFinanceDetailEntities.getData() != null &&
                            manageFinanceDetailEntities.getData().size() > 0){
                        setLoadingStatus(LoadingStatus.GONE);
                        financeList.setVisibility(View.VISIBLE);
                        adapter = new ManageDetailAdapter(context,manageFinanceDetailEntities.getData());
                        financeList.setAdapter(adapter);
                        if(adapter.getPage() < manageFinanceDetailEntities.getCount()){
                            financeList.setCanAddMore(true);
                        }else {
                            financeList.setCanAddMore(false);
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                        financeList.setVisibility(View.GONE);
                        ManageDetailAdapter adapter = new ManageDetailAdapter(context,new ArrayList<ManageFinanceDetailEntities.FinanceListEntities>());
                        financeList.setAdapter(adapter);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                    financeList.setVisibility(View.GONE);
                    ManageDetailAdapter adapter = new ManageDetailAdapter(context,new ArrayList<ManageFinanceDetailEntities.FinanceListEntities>());
                    financeList.setAdapter(adapter);
                }
                break;
            case REQUEST_CODE_FINANCE_DETAIL_MORE:
                financeList.onRefreshComplete();
                if(data != null){
                    ManageFinanceDetailEntities manageFinanceDetailEntities = Parsers.getManageFinanceDetailEntities(data);
                    adapter.addDatas(manageFinanceDetailEntities.getData());
                    if(adapter.getPage() < manageFinanceDetailEntities.getCount()){
                        financeList.setCanAddMore(true);
                    }else {
                        financeList.setCanAddMore(false);
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
            case REQUEST_CODE_FINANCE_DETAIL:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
