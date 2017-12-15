package com.shinaier.laundry.snlfactory.manage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.AddCommodityAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.AddCommodityEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/2/14.
 */

public class AddCommodityActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ADD_COMMODITY_SHOW = 0x1;
    private static final int REQUEST_CODE_ADD_COMMODITY_ADD = 0x2;

    @ViewInject(R.id.add_commodity_name)
    private TextView addCommodityName;
    @ViewInject(R.id.add_commodity_list)
    private WrapHeightListView addCommodityList;
    @ViewInject(R.id.add_commodity_line)
    private View addCommodityLine;
    @ViewInject(R.id.rl_confirm)
    private RelativeLayout rlConfirm;
    @ViewInject(R.id.ll_cate_name)
    private LinearLayout llCateName;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<String> stringList = new ArrayList<>();
    private StringBuffer stringBuffer = new StringBuffer();
    private String cateId;
    private AddCommodityEntities addCommodityEntities;
    private AddCommodityAdapter addCommodityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_commodity_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        cateId = intent.getStringExtra("cate_id");
        String cateName = intent.getStringExtra("cate_name");
        loadData();
        initView(cateName);
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("cate_id",cateId);
        requestHttpData(Constants.Urls.URL_POST_ADD_COMMODITY_SHOW,REQUEST_CODE_ADD_COMMODITY_SHOW, FProtocol.HttpMethod.POST,params);
    }

    private void initView(String cateName) {
        setCenterTitle("添加商品");
        addCommodityName.setText(cateName);
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        rlConfirm.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        addCommodityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(addCommodityEntities.getResult().get(position).isChanged){
                    addCommodityEntities.getResult().get(position).isChanged = false;
                }else{
                    addCommodityEntities.getResult().get(position).isChanged = true;
                    String itemId = addCommodityEntities.getResult().get(position).getId();
                    stringList.add(itemId);
                }
                addCommodityAdapter.notifyDataSetChanged();
            }
        });
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
                    params.put("item_json",s);
                }else {
                    ToastUtil.shortShow(this,"您还没有选择要添加的项目");
                    return;
                }
                requestHttpData(Constants.Urls.URL_POST_ADD_COMMODITY,REQUEST_CODE_ADD_COMMODITY_ADD, FProtocol.HttpMethod.POST,params);
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
                    addCommodityEntities = Parsers.getAddCommodityEntities(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if (addCommodityEntities != null){
                        if (addCommodityEntities.getCode() == 0){
                            if (addCommodityEntities.getResult() != null && addCommodityEntities.getResult().size() > 0){
                                addCommodityLine.setVisibility(View.VISIBLE); //listview下面的线
                                addCommodityList.setVisibility(View.VISIBLE); //listview
                                rlConfirm.setVisibility(View.VISIBLE); //确认按钮
                                llCateName.setVisibility(View.VISIBLE); //衣服类别的标题
                                addCommodityAdapter = new AddCommodityAdapter(this, addCommodityEntities.getResult());
                                addCommodityList.setAdapter(addCommodityAdapter);
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                                llCateName.setVisibility(View.GONE);
                                addCommodityList.setVisibility(View.GONE);
                                addCommodityLine.setVisibility(View.GONE);
                                rlConfirm.setVisibility(View.GONE);
                            }
                        }else {
                            ToastUtil.shortShow(this, addCommodityEntities.getMsg());
                        }
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                    llCateName.setVisibility(View.GONE);
                    addCommodityList.setVisibility(View.GONE);
                    addCommodityLine.setVisibility(View.GONE);
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
