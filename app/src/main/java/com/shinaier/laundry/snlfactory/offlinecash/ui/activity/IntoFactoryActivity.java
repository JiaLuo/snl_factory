package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.IntoFactoryEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.IntoFactoryAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * 入厂
 * Created by 张家洛 on 2017/10/30.
 */

public class IntoFactoryActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_INTO_FACTORY_LIST = 0x1;
    private static final int REQUEST_CODE_GO_BACK = 0x2;

    @ViewInject(R.id.ll_empty_datas)
    private LinearLayout llEmptyDatas;
    @ViewInject(R.id.into_factory_clothes_list)
    private ExpandableListView intoFactoryClothesList;
    @ViewInject(R.id.rl_all_select)
    private RelativeLayout rlAllSelect;
    @ViewInject(R.id.tv_go_back_factory)
    private TextView tvGoBackFactory;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<IntoFactoryEntities.IntoFactoryResult> results;
    private  boolean isClickAll = false;
    private IntoFactoryAdapter intoFactoryAdapter;
    private List<String> goBackIds = new ArrayList<>(); //送洗的id list
    private StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.into_factory_act);
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
        requestHttpData(Constants.Urls.URL_POST_INTO_FACTORY_LIST,REQUEST_CODE_INTO_FACTORY_LIST, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("入厂");
        leftButton.setOnClickListener(this);
        rlAllSelect.setOnClickListener(this);
        tvGoBackFactory.setOnClickListener(this);
        intoFactoryClothesList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //默认return false 设为true的话 不让收缩
                return true;
            }
        });

    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_INTO_FACTORY_LIST:
                if (data != null){
                    IntoFactoryEntities intoFactoryEntities = Parsers.getIntoFactoryEntities(data);
                    if (intoFactoryEntities != null){
                        if (intoFactoryEntities.getCode() == 0){
                            results = intoFactoryEntities.getResults();
                            if (results != null && results.size() > 0){
                                llEmptyDatas.setVisibility(View.GONE);
                                intoFactoryClothesList.setVisibility(View.VISIBLE);
                                intoFactoryAdapter = new IntoFactoryAdapter(this, results);
                                intoFactoryClothesList.setAdapter(intoFactoryAdapter);
                                for (int i = 0; i < results.size(); i++) {
                                    intoFactoryClothesList.expandGroup(i);
                                }

                                intoFactoryAdapter.setSelectListener(new IntoFactoryAdapter.SelectListener() {
                                    @Override
                                    public void onSelect(int groupPosition, int chilePosition) {
                                        if (results.get(groupPosition).getLists().get(chilePosition).isSelect){
                                            results.get(groupPosition).getLists().get(chilePosition).isSelect = false;
                                        }else {
                                            results.get(groupPosition).getLists().get(chilePosition).isSelect = true;
                                        }
                                        intoFactoryAdapter.notifyDataSetChanged();

                                    }
                                });
                            }else {
                                intoFactoryClothesList.setVisibility(View.GONE);
                                llEmptyDatas.setVisibility(View.VISIBLE);
                            }
                        }else {
                            ToastUtil.shortShow(this,intoFactoryEntities.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_GO_BACK:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity.getRetcode() == 0){
                        ToastUtil.shortShow(this,"操作成功");
                        loadData();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.rl_all_select:
                //全选
                if (!isClickAll){
                    isClickAll = true;
                    rlAllSelect.setSelected(true);
                    for (int i = 0; i < results.size(); i++) {
                        for (int j = 0; j < results.get(i).getLists().size(); j++) {
                            results.get(i).getLists().get(j).isSelect = true;
                        }
                    }

                }else{
                    isClickAll = false;
                    rlAllSelect.setSelected(false);
                    for (int i = 0; i < results.size(); i++) {
                        for (int j = 0; j < results.get(i).getLists().size(); j++) {
                            results.get(i).getLists().get(j).isSelect = false;
                        }
                    }
                }

                intoFactoryAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_go_back_factory:
                //退回
                goBackIds.clear();
                stringBuffer.delete(0,stringBuffer.length());
                for (int i = 0; i < results.size(); i++) {
                    for (int j = 0; j < results.get(i).getLists().size(); j++) {
                        if (results.get(i).getLists().get(j).isSelect){
                            String id = results.get(i).getLists().get(j).getId();
                            goBackIds.add(id);
                        }
                    }
                }

                if (goBackIds.size() > 0){
                    for (int i = 0; i < goBackIds.size(); i++) {
                        if(i == 0){
                            if(goBackIds.size() == 1){
                                stringBuffer.append(goBackIds.get(i));
                            }else {
                                stringBuffer.append(goBackIds.get(i)).append(",");
                            }
                        }else if(i > 0 && i < goBackIds.size() -1){
                            stringBuffer.append(goBackIds.get(i)).append(",");
                        }else {
                            stringBuffer.append(goBackIds.get(i));
                        }
                    }

                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token",UserCenter.getToken(this));
                    params.put("itemids",stringBuffer.toString());
                    params.put("moduleid","22");
                    requestHttpData(Constants.Urls.URL_POST_GO_BACK,REQUEST_CODE_GO_BACK, FProtocol.HttpMethod.POST,params);
                }else {
                    ToastUtil.shortShow(this,"请选择退回衣物");
                }
                break;
        }
    }
}
