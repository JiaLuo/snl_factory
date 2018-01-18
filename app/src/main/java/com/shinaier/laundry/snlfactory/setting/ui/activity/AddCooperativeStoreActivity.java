package com.shinaier.laundry.snlfactory.setting.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CooperativeStoreEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.setting.adapter.CooperationStoreAdapter;
import com.shinaier.laundry.snlfactory.setting.ui.fragment.SettingsManageFragment;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.ClearEditText;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * 合作门店或连锁门店
 * Created by 张家洛 on 2018/1/17.
 */

public class AddCooperativeStoreActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_SEARCH_COOPERATIVE_STORE = 0x1;
    private static final int REQUEST_CODE_ADD_COOPERATIVE_STORE = 0x2;

    @ViewInject(R.id.edit_search)
    private ClearEditText editSearch;
    @ViewInject(R.id.btn_start_search)
    private TextView btnStartSearch;
    @ViewInject(R.id.add_cooperation_store_list)
    private ListView addCooperationStoreList;
    @ViewInject(R.id.cooperation_store_add_bt)
    private TextView cooperationStoreAddBt;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<CooperativeStoreEntities.CooperativeStoreResults> resultses;
    private List<String> ids = new ArrayList<>();
    private StringBuffer stringBuffer  = new StringBuffer();
    private int isChain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cooperative_store_act);
        ViewInjectUtils.inject(this);
        isChain = getIntent().getIntExtra("is_chain", 0);
        initView();
    }

    private void initView() {
        if (isChain == SettingsManageFragment.ISCHAIN){
            setCenterTitle("连锁工厂");
        }else {
            setCenterTitle("合作工厂");
        }
        leftButton.setOnClickListener(this);
        btnStartSearch.setOnClickListener(this);
        cooperationStoreAddBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.btn_start_search:
                String inputStoreName = editSearch.getText().toString();
                if (!TextUtils.isEmpty(inputStoreName)){
                    String url = "";
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token", UserCenter.getToken(this));
                    params.put("keywords",inputStoreName);
                    if (isChain == SettingsManageFragment.ISCHAIN){
                        url = Constants.Urls.URL_POST_SEARCH_CHAIN_STORE;
                    }else {
                        url = Constants.Urls.URL_POST_SEARCH_COOPERATIVE_STORE;
                    }
                    requestHttpData(url,REQUEST_CODE_SEARCH_COOPERATIVE_STORE,
                            FProtocol.HttpMethod.POST,params);
                }else {
                    ToastUtil.shortShow(this,"请输入要添加商铺的名称");
                }
                break;
            case R.id.cooperation_store_add_bt:
                stringBuffer.delete(0,stringBuffer.length());
                if (ids.size() > 0){
                    for (int i = 0; i < ids.size(); i++) {
                        if(i == 0){
                            if(ids.size() == 1){
                                stringBuffer.append(ids.get(i));
                            }else {
                                stringBuffer.append(ids.get(i)).append(",");
                            }
                        }else if(i > 0 && i < ids.size() -1){
                            stringBuffer.append(ids.get(i)).append(",");
                        }else {
                            stringBuffer.append(ids.get(i));
                        }
                    }
                    String url = "";
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token",UserCenter.getToken(this));
                    params.put("ids",stringBuffer.toString());
                    if (isChain == SettingsManageFragment.ISCHAIN){
                        url = Constants.Urls.URL_POST_ADD_CHAIN_STORE;
                    }else {
                        url = Constants.Urls.URL_POST_ADD_COOPERATIVE_STORE;
                    }
                    requestHttpData(url,REQUEST_CODE_ADD_COOPERATIVE_STORE,
                            FProtocol.HttpMethod.POST,params);
                }else {
                    ToastUtil.shortShow(this,"请选择添加合作店铺");
                }
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case  REQUEST_CODE_SEARCH_COOPERATIVE_STORE:
                if (data != null){
                    CooperativeStoreEntities cooperativeFactoryEntities = Parsers.getCooperativeStoreEntities(data);
                    if (cooperativeFactoryEntities != null){
                        if (cooperativeFactoryEntities.getCode() == 0){
                            resultses = cooperativeFactoryEntities.getResultses();
                            if (resultses != null && resultses.size() > 0){
                                CooperationStoreAdapter cooperationStoreAdapter = new CooperationStoreAdapter(this, resultses,true);
                                addCooperationStoreList.setAdapter(cooperationStoreAdapter);
                                cooperationStoreAdapter.setShowView(new CooperationStoreAdapter.ShowViewListener() {
                                    @Override
                                    public void onSelected(ImageView view, int position) {
                                        if (resultses.get(position).isSelect){
                                            resultses.get(position).isSelect = false;
                                            view.setSelected(false);
                                            ids.remove(resultses.get(position).getId());
                                        }else {
                                            resultses.get(position).isSelect = true;
                                            view.setSelected(true);
                                            ids.add(resultses.get(position).getId());
                                        }

                                    }
                                });
                            }
                        }else {
                            ToastUtil.shortShow(this,cooperativeFactoryEntities.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ADD_COOPERATIVE_STORE:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            finish();
                            ToastUtil.shortShow(this,"操作成功");
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }
}
