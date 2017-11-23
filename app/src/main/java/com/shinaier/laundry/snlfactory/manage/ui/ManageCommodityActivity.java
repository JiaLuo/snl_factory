package com.shinaier.laundry.snlfactory.manage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.ManageCommodityAdapter;
import com.shinaier.laundry.snlfactory.manage.view.EditCommodityDialog;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.ManageCommodityEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/2/14.
 */

public class ManageCommodityActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_COMMODITY_SHOW = 0x1;
    private static final int REQUEST_CODE_EDIT_COMMODITY_INFO = 0x2;
    private static final int REQUEST_CODE_DELETE_COMMODITY_INFO = 0x3;

    @ViewInject(R.id.ll_add_commodity)
    private LinearLayout llAddCommodity;
    @ViewInject(R.id.commodity_manage_list)
    private WrapHeightListView commodityManageList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Bundle data = msg.getData();
                    String price = data.getString("price");
                    String cycle = data.getString("cycle");
                    if(!TextUtils.isEmpty(price)){
                        if(!TextUtils.isEmpty(cycle)){
                            IdentityHashMap<String,String> params = new IdentityHashMap<>();
                            params.put("token", UserCenter.getToken(ManageCommodityActivity.this));
                            params.put("id",editItem.getId());
                            params.put("price",price);
                            params.put("cycle",cycle);
                            requestHttpData(Constants.Urls.URL_POST_EDIT_COMMODITY_INFO,REQUEST_CODE_EDIT_COMMODITY_INFO,
                                    FProtocol.HttpMethod.POST,params);
                        }else {
                            ToastUtil.shortShow(ManageCommodityActivity.this,"请输入洗护周期");
                        }
                    }else {
                        ToastUtil.shortShow(ManageCommodityActivity.this,"请输入价格");
                    }
                    break;
                case 2:
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token",UserCenter.getToken(ManageCommodityActivity.this));
                    params.put("id",editItem.getId());
                    requestHttpData(Constants.Urls.URL_POST_DEL_COMMODITY_INFO,REQUEST_CODE_DELETE_COMMODITY_INFO,
                            FProtocol.HttpMethod.POST,params);
                    break;
            }
        }
    };
    private ManageCommodityEntities.ItemType.Item editItem;
    private EditCommodityDialog commodityDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_commodity_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MANAGE_COMMODITY_SHOW,REQUEST_CODE_COMMODITY_SHOW, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("商品管理");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        llAddCommodity.setOnClickListener(this);
        leftButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_add_commodity:
                startActivity(new Intent(this,AddCommodityActivity.class));
                break;
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;

        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_COMMODITY_SHOW:
                if(data != null){
                    final ManageCommodityEntities manageCommodityEntities = Parsers.getManageCommodityEntities(data);
                    if(manageCommodityEntities != null && manageCommodityEntities.getItemType() != null &&
                            manageCommodityEntities.getItemType().size() > 0){
                        setLoadingStatus(LoadingStatus.GONE);
                        ManageCommodityAdapter manageCommodityAdapter = new ManageCommodityAdapter(this,manageCommodityEntities.getItemType());
                        commodityManageList.setAdapter(manageCommodityAdapter);
                        manageCommodityAdapter.setPositionListener(new ManageCommodityAdapter.PositionListener() {
                            @Override
                            public void onClick(int position, int innerPosition) {
                                editItem = manageCommodityEntities.getItemType().get(position).getItem().get(innerPosition);
                                commodityDialog = new EditCommodityDialog(ManageCommodityActivity.this,
                                        R.style.timerDialog,handler, editItem);
                                commodityDialog.setView();
                                commodityDialog.show();
                            }
                        });
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_EDIT_COMMODITY_INFO:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        commodityDialog.dismiss();
                        setLoadingStatus(LoadingStatus.LOADING);
                        loadData();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_DELETE_COMMODITY_INFO:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        commodityDialog.dismiss();
                        setLoadingStatus(LoadingStatus.LOADING);
                        loadData();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;

        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_COMMODITY_SHOW:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
