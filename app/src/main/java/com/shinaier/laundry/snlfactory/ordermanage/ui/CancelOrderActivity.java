package com.shinaier.laundry.snlfactory.ordermanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CancelOrderEntities;
import com.shinaier.laundry.snlfactory.network.entity.CancelOrderEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CancelOrderAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/2/28.
 */

public class CancelOrderActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CANCEL_ORDER_INFO = 0x1;
    private static final int REQUEST_CODE_CANCEL_ORDER = 0x2;

    @ViewInject(R.id.cancel_order_list)
    private ListView cancelOrderList;
    @ViewInject(R.id.another_reason)
    private RelativeLayout anotherReason;
    @ViewInject(R.id.another_reason_detail)
    private RelativeLayout anotherReasonDetail;
    @ViewInject(R.id.cancel_order_content)
    private EditText cancelOrderContent;
    @ViewInject(R.id.cancel_edit_max_num)
    private TextView cancelEditMaxNum;
    @ViewInject(R.id.cancel_order_bt)
    private TextView cancelOrderBt;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<CancelOrderEntity> entities = new ArrayList<>();
    private CancelOrderAdapter cancelOrderAdapter;
    private String orderInfo;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancle_order_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_CANCEL_ORDER,REQUEST_CODE_CANCEL_ORDER_INFO, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("取消订单");
        anotherReason.setOnClickListener(this);
        cancelOrderBt.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        cancelOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CancelOrderEntity cancelOrderEntity = entities.get(position);
                orderInfo = cancelOrderEntity.getOrderInfo();
                for(int i = 0;i < entities.size();i++){
                    entities.get(i).setIsVisibled(1);
                }
                cancelOrderEntity.setIsVisibled(0);
                cancelOrderAdapter.notifyDataSetChanged();
                anotherReasonDetail.setVisibility(View.GONE);
            }
        });

        cancelOrderContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cancelEditMaxNum.setText(s.length()+ "/30");
            }

            @Override
            public void afterTextChanged(Editable s) {
                orderInfo = s.toString();
            }
        });
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CANCEL_ORDER_INFO:
                if(data != null){
                    CancelOrderEntities cancelOrderEntities = Parsers.getCancelOrderEntities(data);
                    if(cancelOrderEntities.getRetcode() == 0){
                        if(cancelOrderEntities.getData() != null){
                            for(int i = 0; i < cancelOrderEntities.getData().size(); i++){
                                CancelOrderEntity cancelOrderEntity = new CancelOrderEntity();
                                cancelOrderEntity.setOrderInfo(cancelOrderEntities.getData().get(i));
                                entities.add(cancelOrderEntity);
                            }
                            cancelOrderAdapter = new CancelOrderAdapter(this,entities);
                            cancelOrderList.setAdapter(cancelOrderAdapter);
                        }
                    }else {
                        ToastUtil.shortShow(this,cancelOrderEntities.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_CANCEL_ORDER:
                Entity entity = Parsers.getEntity(data);
                if(entity.getRetcode() == 0){
                    ToastUtil.shortShow(this,"取消订单成功");
                    finish();

                }else {
                    ToastUtil.shortShow(this,entity.getStatus());
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.another_reason:
                if(anotherReasonDetail.getVisibility() == View.GONE){
                    anotherReasonDetail.setVisibility(View.VISIBLE);
                }else {
                    anotherReasonDetail.setVisibility(View.GONE);
                }
                for(int i = 0;i <entities.size(); i++){
                    entities.get(i).setIsVisibled(1);
                }
                cancelOrderAdapter.notifyDataSetChanged();
                break;
            case R.id.cancel_order_bt:
                if(orderInfo != null){
                    cancelData();
                }else {
                    ToastUtil.shortShow(this,"取消订单原因不能空");
                }
                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    private void cancelData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("id",id);
        params.put("quxiao",orderInfo);
        requestHttpData(Constants.Urls.URL_POST_CANCEL_ORDER,REQUEST_CODE_CANCEL_ORDER, FProtocol.HttpMethod.POST,params);
    }
}
