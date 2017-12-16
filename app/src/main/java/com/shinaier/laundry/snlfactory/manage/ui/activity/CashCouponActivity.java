package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.CashCouponAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CashCouponEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * 代金券码
 * Created by 张家洛 on 2017/10/31.
 */

public class CashCouponActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CASH_COUPON_DETAIL = 0x1;

    @ViewInject(R.id.cash_coupon_list)
    private ListView cashCouponList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.copy_cash_coupon)
    private TextView copyCashCoupon;

    private ArrayList<CashCouponEntity.CashCouponResult> cashCouponResults;
    private StringBuffer buffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_coupon_act);
        ViewInjectUtils.inject(this);
        ExitManager.instance.addCashCouponActivity(this);
//        cashCoupon = getIntent().getStringExtra("cash_coupon");
        Intent intent = getIntent();
        cashCouponResults = intent.getParcelableArrayListExtra("cash_coupon_results");
        String id = intent.getStringExtra("id");
        String type = intent.getStringExtra("type");
        if (cashCouponResults == null){
            loadData(id,type);
        }
        initView();
    }

    private void loadData(String id, String type) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("mrid",id);
        params.put("type",type);
        requestHttpData(Constants.Urls.URL_POST_CASH_COUPON_DETAIL,
                REQUEST_CODE_CASH_COUPON_DETAIL, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("卡券详情");
        leftButton.setOnClickListener(this);
        copyCashCoupon.setOnClickListener(this);
        if (cashCouponResults != null){
            CashCouponAdapter cashCouponAdapter = new CashCouponAdapter(this,cashCouponResults);
            cashCouponList.setAdapter(cashCouponAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                ExitManager.instance.exitCahsCouponActivity();
                break;
            case R.id.copy_cash_coupon:
                //一键复制
                if (cashCouponResults != null){
                    for (int i = 0; i < cashCouponResults.size(); i++) {
                        if(i == 0){
                            if(cashCouponResults.size() == 1){
                                buffer.append(cashCouponResults.get(i).getSn());
                            }else {
                                buffer.append(cashCouponResults.get(i).getSn()).append("\r\n");
                            }
                        }else if(i > 0 && i < cashCouponResults.size() -1){
                            buffer.append(cashCouponResults.get(i).getSn()).append("\r\n");
                        }else {
                            buffer.append(cashCouponResults.get(i).getSn());
                        }
                    }
                }

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("copy_cash_coupon",buffer.toString());
                cm.setPrimaryClip(clipData);
                ToastUtil.shortShow(this,"复制成功");
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CASH_COUPON_DETAIL:
                if (data != null){
                    CashCouponEntity cashCouponEntity = Parsers.getCashCouponEntity(data);
                    if (cashCouponEntity != null){
                        if (cashCouponEntity.getCode() == 0){
                            if (cashCouponEntity.getResult() != null && cashCouponEntity.getResult().size() > 0){
                                CashCouponAdapter cashCouponAdapter = new CashCouponAdapter(this,cashCouponEntity.getResult());
                                cashCouponList.setAdapter(cashCouponAdapter);
                            }
                        }else {
                            ToastUtil.shortShow(this,cashCouponEntity.getMsg());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ExitManager.instance.exitCahsCouponActivity();
    }
}
