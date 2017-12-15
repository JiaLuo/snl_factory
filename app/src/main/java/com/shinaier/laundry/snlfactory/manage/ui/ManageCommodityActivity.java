package com.shinaier.laundry.snlfactory.manage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.ManageCommoditysAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.ManageCommodityEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/2/14.
 */

public class ManageCommodityActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_COMMODITY_SHOW = 0x1;
    private static final int REQUEST_CODE_DELETE_COMMODITY = 0x4;

//    @ViewInject(R.id.commodity_manage_list)
//    private WrapHeightListView commodityManageList;
    @ViewInject(R.id.commodity_manage_list)
    private ExpandableListView commodityManageList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;


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
        leftButton.setOnClickListener(this);
        commodityManageList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //默认return false 设为true的话 不让收缩
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
                    if (manageCommodityEntities != null){
                        if (manageCommodityEntities.getCode() == 0){
                            if (manageCommodityEntities.getResult() != null && manageCommodityEntities.getResult().size() > 0){
                                setLoadingStatus(LoadingStatus.GONE);
                                ManageCommoditysAdapter manageCommoditysAdapter = new ManageCommoditysAdapter(this,manageCommodityEntities.getResult());
                                commodityManageList.setAdapter(manageCommoditysAdapter);

                                for (int i = 0; i < manageCommodityEntities.getResult().size(); i++) {
                                    commodityManageList.expandGroup(i);
                                }
                                manageCommoditysAdapter.setAddCommodityListener(new ManageCommoditysAdapter.AddCommodityListener() {
                                    @Override
                                    public void onAddClick(int groupPosition) {
                                        Intent intent = new Intent(ManageCommodityActivity.this,AddCommodityActivity.class);
                                        intent.putExtra("cate_id",manageCommodityEntities.getResult().get(groupPosition).getId());
                                        intent.putExtra("cate_name",manageCommodityEntities.getResult().get(groupPosition).getCateName());
                                        startActivity(intent);
                                    }
                                });

                                manageCommoditysAdapter.setEditCommodityListener(new ManageCommoditysAdapter.EditCommodityListener() {
                                    @Override
                                    public void onEditClick(int groupPosition, int childPosition) {
                                        Intent intent = new Intent(ManageCommodityActivity.this,ManageCommodityEditActivity.class);
                                        intent.putExtra("item_id",manageCommodityEntities.getResult().get(groupPosition).getItemses()
                                                .get(childPosition).getId());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onDeleteClick(int groupPosition, int childPosition) {
                                        IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
                                        params.put("token",UserCenter.getToken(ManageCommodityActivity.this));
                                        params.put("item_id",manageCommodityEntities.getResult().get(groupPosition)
                                                .getItemses().get(childPosition).getId());
                                        requestHttpData(Constants.Urls.URL_POST_DELETE_COMMODITY,REQUEST_CODE_DELETE_COMMODITY
                                                , FProtocol.HttpMethod.POST,params);
                                    }
                                });
                                // TODO: 2017/12/15 暂时先不删除。因为最下面的bottom线显示不出来
//                                ManageCommodityAdapter manageCommodityAdapter = new ManageCommodityAdapter(this,manageCommodityEntities.getResult());
//                                commodityManageList.setAdapter(manageCommodityAdapter);
//                                manageCommodityAdapter.setPositionListener(new ManageCommodityAdapter.PositionListener() {
//                                    @Override
//                                    public void onEditClick(int position, int innerPosition) {
//                                        LogUtil.e("zhang","onEditClick position = " + position);
//                                        LogUtil.e("zhang","onEditClick innerPosition = " + innerPosition);
//                                    }
//
//                                    @Override
//                                    public void onDeleteClick(int position, int innerPosition) {
//                                        LogUtil.e("zhang","onDeleteClick position = " + position);
//                                        LogUtil.e("zhang","onDeleteClick innerPosition = " + innerPosition);
//                                    }
//                                });
                            }else{
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(this,manageCommodityEntities.getMsg());
                        }
                    }

                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_DELETE_COMMODITY:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            ToastUtil.shortShow(this,"删除成功");
                            loadData();

                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
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
            case REQUEST_CODE_COMMODITY_SHOW:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
