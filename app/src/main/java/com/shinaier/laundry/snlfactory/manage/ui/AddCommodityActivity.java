package com.shinaier.laundry.snlfactory.manage.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.AddCommoditysAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.AddCommodityEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/2/14.
 */

public class AddCommodityActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ADD_COMMODITY_SHOW = 0x1;
    private static final int REQUEST_CODE_ADD_COMMODITY_ADD = 0x2;

    @ViewInject(R.id.aad_commodity_list)
    private ExpandableListView aadCommodityList;
    @ViewInject(R.id.rl_confirm)
    private RelativeLayout rlConfirm;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<String> stringList = new ArrayList<>();
    private StringBuffer stringBuffer = new StringBuffer();
    private List<AddCommodityEntities.Item.Goods> goods;
    private AddCommoditysAdapter addCommoditysAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_commodity_act);
        ViewInjectUtils.inject(this);
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_ADD_COMMODITY_SHOW,REQUEST_CODE_ADD_COMMODITY_SHOW, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("添加商品");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        rlConfirm.setOnClickListener(this);
        leftButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_confirm:
                for (int i = 0; i < stringList.size(); i++){
                    if(i == 0){
                        if(stringList.size() == 1){
                            stringBuffer.append("[").append('"').append(stringList.get(i)).append('"').append("]");
                        }else {
                            stringBuffer.append("[").append('"').append(stringList.get(i)).append('"').append(",");
                        }
                    }else if(i > 0 && i < stringList.size() -1){
                        stringBuffer.append('"').append(stringList.get(i)).append('"').append(",");
                    }else {
                        stringBuffer.append('"').append(stringList.get(i)).append('"').append("]");
                    }
                }

                String s = stringBuffer.toString();
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("token",UserCenter.getToken(this));
                if(!TextUtils.isEmpty(s)){
                    params.put("th_id",s);
                }else {
                    ToastUtil.shortShow(this,"您还没有选择要添加的项目");
                    return;
                }
                requestHttpData(Constants.Urls.URL_POST_ADD_COMMODITY_SHOW,REQUEST_CODE_ADD_COMMODITY_ADD, FProtocol.HttpMethod.POST,params);
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
            case REQUEST_CODE_ADD_COMMODITY_SHOW:
                if(data != null){
                    final AddCommodityEntities addCommodityEntities = Parsers.getAddCommodityEntities(data);
                    if(addCommodityEntities != null && addCommodityEntities.getItem() != null &&
                            addCommodityEntities.getItem().size() > 0){
                        setLoadingStatus(LoadingStatus.GONE);
                        aadCommodityList.setVisibility(View.VISIBLE);
                        rlConfirm.setVisibility(View.VISIBLE);

                        for (int i = 0; i < addCommodityEntities.getItem().size(); i++) {
                            goods = addCommodityEntities.getItem().get(i).getGoods();
                        }
                        addCommoditysAdapter = new AddCommoditysAdapter(addCommodityEntities.getItem(),this);
                        aadCommodityList.setAdapter(addCommoditysAdapter);

                        for (int i = 0; i < addCommodityEntities.getItem().size(); i++) {
                            aadCommodityList.expandGroup(i);
                        }
                        addCommoditysAdapter.setSelectListener(new AddCommoditysAdapter.SelectListener() {
                            @Override
                            public void onSelect(int groupPosition, int childPosition) {
                                if(addCommodityEntities.getItem().get(groupPosition).getGoods().get(childPosition).isChanged){
                                    addCommodityEntities.getItem().get(groupPosition).getGoods().get(childPosition).isChanged = false;
                                }else{
                                    addCommodityEntities.getItem().get(groupPosition).getGoods().get(childPosition).isChanged = true;
                                    String id = addCommodityEntities.getItem().get(groupPosition).getGoods().get(childPosition).getId();
                                   stringList.add(id);

                                }
                                addCommoditysAdapter.notifyDataSetChanged();
                            }
                        });
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                        aadCommodityList.setVisibility(View.GONE);
                        rlConfirm.setVisibility(View.GONE);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                    aadCommodityList.setVisibility(View.GONE);
                    rlConfirm.setVisibility(View.GONE);
                }
                break;
            case REQUEST_CODE_ADD_COMMODITY_ADD:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        finish();
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
            case REQUEST_CODE_ADD_COMMODITY_SHOW:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
