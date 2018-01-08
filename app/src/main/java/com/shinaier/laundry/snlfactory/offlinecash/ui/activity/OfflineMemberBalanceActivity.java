package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberBalanceEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineMemberBalanceAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * 线下会员余额
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberBalanceActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_BALANCE_LIST = 0x1;
    private static final int REQUEST_CODE_BALANCE_LIST_MORE = 0x2;

    @ViewInject(R.id.tv_total_member_num)
    private TextView tvTotalMemberNum;
    @ViewInject(R.id.tv_total_member_balance_num)
    private TextView tvTotalMemberBalanceNum;
    @ViewInject(R.id.offline_member_balance_list)
    private FootLoadingListView offlineMemberBalanceList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private OfflineMemberBalanceAdapter offlineMemberBalanceAdapter;
    private OfflineMemberBalanceEntity offlineMemberBalanceEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_member_balance_act);
        ViewInjectUtils.inject(this);
        loadData(false);//请求数据
        initView();
    }

    private void loadData(boolean isMore) {
        int code = 0;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        if (isMore){
            params.put("page", offlineMemberBalanceAdapter.getPage() + 1 + "");
            code = REQUEST_CODE_BALANCE_LIST_MORE;
        }else {
            params.put("page","1");
            code = REQUEST_CODE_BALANCE_LIST;
        }
        params.put("limit","10");
        requestHttpData(Constants.Urls.URL_POST_OFFLINE_MEMBER_BALANCE,code, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("会员余额");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
        offlineMemberBalanceList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(true);
            }
        });

        offlineMemberBalanceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OfflineMemberBalanceActivity.this,OfflineMemberDetailActivity.class);
                intent.putExtra("member_number",offlineMemberBalanceEntity.getResult().getMemberLists().get(position).getuMobile());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_BALANCE_LIST:
                if(data != null){
                    offlineMemberBalanceEntity = Parsers.getOfflineMemberBalanceEntity(data);
                    if(offlineMemberBalanceEntity != null){
                        if(offlineMemberBalanceEntity.getCode() == 0){
                            if(offlineMemberBalanceEntity.getResult() != null){
                                //设置会员余额数据
                                setMemberBalanceData(offlineMemberBalanceEntity);
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineMemberBalanceEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_BALANCE_LIST_MORE:
                offlineMemberBalanceList.onRefreshComplete();
                if(data != null){
                    OfflineMemberBalanceEntity offlineMemberBalanceEntity = Parsers.getOfflineMemberBalanceEntity(data);
                    offlineMemberBalanceAdapter.addDatas(offlineMemberBalanceEntity.getResult().getMemberLists());
                    if( offlineMemberBalanceAdapter.getPage() < offlineMemberBalanceEntity.getResult().getPageCount()){
                        offlineMemberBalanceList.setCanAddMore(true);
                    }else {
                        offlineMemberBalanceList.setCanAddMore(false);
                    }
                }
                break;
        }
    }

    /**
     * 设置会员余额数据
     * @param offlineMemberBalanceEntity 会员余额数据
     */
    private void setMemberBalanceData(OfflineMemberBalanceEntity offlineMemberBalanceEntity) {
        //累计会员数
        if (offlineMemberBalanceEntity.getResult().getCount() != null &&
                !TextUtils.isEmpty(offlineMemberBalanceEntity.getResult().getCount())){
            tvTotalMemberNum.setText(offlineMemberBalanceEntity.getResult().getCount()); //累计会员数
        }else {
            tvTotalMemberNum.setText("0");
        }

        if (offlineMemberBalanceEntity.getResult().getSum() != null &&
                !TextUtils.isEmpty(offlineMemberBalanceEntity.getResult().getSum())){
            tvTotalMemberBalanceNum.setText(offlineMemberBalanceEntity.getResult().getSum()); // 累计会员余额
        }else {
            tvTotalMemberBalanceNum.setText("￥0.00");
        }

        offlineMemberBalanceList.onRefreshComplete();
        //设置余额明细
        if(offlineMemberBalanceEntity.getResult().getMemberLists() != null &&
                offlineMemberBalanceEntity.getResult().getMemberLists().size() > 0){
            setLoadingStatus(LoadingStatus.GONE);
            offlineMemberBalanceAdapter = new OfflineMemberBalanceAdapter(this,offlineMemberBalanceEntity.getResult().getMemberLists());
            offlineMemberBalanceList.setAdapter(offlineMemberBalanceAdapter);

            if( offlineMemberBalanceAdapter.getPage() < offlineMemberBalanceEntity.getResult().getPageCount()){
                offlineMemberBalanceList.setCanAddMore(true);
            }else {
                offlineMemberBalanceList.setCanAddMore(false);
            }
        }else {
            setLoadingStatus(LoadingStatus.EMPTY);
            OfflineMemberBalanceAdapter offlineMemberBalanceAdapter = new OfflineMemberBalanceAdapter(this,new ArrayList<OfflineMemberBalanceEntity.OfflineMemberBalanceResult
                    .OfflineMemberBalanceRecord>());
            offlineMemberBalanceList.setAdapter(offlineMemberBalanceAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData(false);
                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_BALANCE_LIST:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
