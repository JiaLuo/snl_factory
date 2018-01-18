package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
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
import com.shinaier.laundry.snlfactory.manage.adapter.CashBackAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CashBackEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.text.DecimalFormat;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/2/18.
 */

public class CashBackActivity extends ToolBarActivity {
    private static final int REQUEST_CODE_CASH_BACK = 0x1;

    @ViewInject(R.id.cash_back_record_money)
    private TextView cashBackRecordMoney;
    @ViewInject(R.id.cash_back_record_person)
    private TextView cashBackRecordPerson;
    @ViewInject(R.id.cash_back_record_list)
    private FootLoadingListView cashBackRecordList;
    @ViewInject(R.id.cash_back_info)
    private TextView cashBackInfo;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_back_act);
        ViewInjectUtils.inject(this);
        loadData();
        initView();
    }

    private void initView() {
        setCenterTitle("返现记录");
        SpannableStringBuilder ssb1 = new SpannableStringBuilder();
        ssb1.append("邀请好友成功下单且订单达成，即享受该单支付金额的1%返现奖励");
        ssb1.setSpan(new ForegroundColorSpan(res.getColor(R.color.red)),24,26, Spanned.SPAN_INCLUSIVE_EXCLUSIVE );
        cashBackInfo.setText(ssb1);

        cashBackRecordList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_CASH_BACK,REQUEST_CODE_CASH_BACK, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CASH_BACK:
                cashBackRecordList.onRefreshComplete();
                if(data != null){
                    CashBackEntity cashBackEntity = Parsers.getCashBackEntity(data);
                    if (cashBackEntity != null){
                        if (cashBackEntity.getCode() == 0){
                            if (cashBackEntity.getResult() != null){
                                CashBackEntity.CashBackResult result = cashBackEntity.getResult();
                                if (result != null){
                                    setData(result.getBackBalance(),result.getShareTotal());
                                    if (result.getLists() != null && result.getLists().size() > 0){
                                        CashBackAdapter cashBackAdapter = new CashBackAdapter(this,result.getLists());
                                        cashBackRecordList.setAdapter(cashBackAdapter);
                                    }
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this,cashBackEntity.getMsg());
                        }
                    }
                }
                break;
        }
    }

    private void setData(double backBalance,String shareTotal) {
        cashBackRecordMoney.setText(formatMoney(backBalance));
        cashBackRecordPerson.setText(shareTotal);
    }

    public String formatMoney(double money){
        DecimalFormat df = new DecimalFormat("###0.00");
        return df.format(money);
    }
}
