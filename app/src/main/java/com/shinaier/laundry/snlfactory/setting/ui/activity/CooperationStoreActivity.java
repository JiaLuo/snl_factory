package com.shinaier.laundry.snlfactory.setting.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.shinaier.laundry.snlfactory.network.entity.CooperativeStoreEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.setting.adapter.CooperationStoreAdapter;
import com.shinaier.laundry.snlfactory.setting.ui.fragment.SettingsManageFragment;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * Created by 张家洛 on 2017/10/30.
 */

public class CooperationStoreActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_COOPERATIVE_STORE = 0x1;
    private static final int REQUEST_CODE_DEL_COOPERATIVE_STORE = 0x2;

    @ViewInject(R.id.ll_empty_datas)
    private LinearLayout llEmptyDatas;
    @ViewInject(R.id.rl_data_list)
    private RelativeLayout rlDataList;
    @ViewInject(R.id.cooperation_store_list)
    private ListView cooperationStoreList;
    @ViewInject(R.id.cooperation_store_edit_bt)
    private TextView cooperationStoreEditBt;
    @ViewInject(R.id.cooperation_store_add_bt)
    private TextView cooperationStoreAddBt;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private CooperationStoreAdapter cooperationStoreAdapter;
    private boolean isClickEdit = false;
    private List<CooperativeStoreEntities.CooperativeStoreResults> resultses;
    private List<String> ids = new ArrayList<>();
    private StringBuffer stringBuffer = new StringBuffer();
    private int isChain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooperation_store_act);
        ViewInjectUtils.inject(this);
        isChain = getIntent().getIntExtra("is_chain", 0);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        String url = "";
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        if (isChain == SettingsManageFragment.ISCHAIN){
            url =  Constants.Urls.URL_POST_CHAIN_STORE;
        }else {
            url =  Constants.Urls.URL_POST_COOPERATIVE_STORE;
        }
        requestHttpData(url,REQUEST_CODE_COOPERATIVE_STORE, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        if (isChain == SettingsManageFragment.ISCHAIN){
            setCenterTitle("连锁门店");
        }else {
            setCenterTitle("合作门店");
        }
        leftButton.setOnClickListener(this);
        cooperationStoreEditBt.setOnClickListener(this);
        cooperationStoreAddBt.setOnClickListener(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_COOPERATIVE_STORE:
                if (data != null){
                    CooperativeStoreEntities cooperativeFactoryEntities = Parsers.getCooperativeStoreEntities(data);
                    if (cooperativeFactoryEntities != null){
                        if (cooperativeFactoryEntities.getCode() == 0){
                             resultses = cooperativeFactoryEntities.getResultses();
                            if (this.resultses != null && this.resultses.size() > 0){
                                llEmptyDatas.setVisibility(View.GONE);
                                rlDataList.setVisibility(View.VISIBLE);
                                cooperationStoreAdapter = new CooperationStoreAdapter(this,resultses,false);
                                cooperationStoreList.setAdapter(cooperationStoreAdapter);

                            }else {
                                llEmptyDatas.setVisibility(View.VISIBLE);
                                rlDataList.setVisibility(View.GONE);
                            }
                        }else {
                            ToastUtil.shortShow(this,cooperativeFactoryEntities.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_DEL_COOPERATIVE_STORE:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            cooperationStoreEditBt.setText("编辑");
                            loadData();
                            ToastUtil.shortShow(this,"操作成功");
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
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
            case R.id.cooperation_store_edit_bt:
                if (cooperationStoreAdapter == null) return;
                if (resultses.size() == 0) return;
                //编辑
                if (isClickEdit){
                    isClickEdit = false;
                    //选择按钮隐藏
                    cooperationStoreAdapter.isDelete(false);

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
                            url = Constants.Urls.URL_POST_DEL_CHAIN_STORE;
                            LogUtil.e("zhang","aaaaaaa");
                            LogUtil.e("zhang","stringBuffer = " + stringBuffer.toString());
                        }else {
                            url = Constants.Urls.URL_POST_DEL_COOPERATIVE_STORE;
                        }
                        requestHttpData(url,REQUEST_CODE_DEL_COOPERATIVE_STORE,
                                FProtocol.HttpMethod.POST,params);
                    }


                }else {
                    isClickEdit = true;

                    //选择按钮显示
                    cooperationStoreAdapter.isDelete(true);
                    cooperationStoreAdapter.setShowView(new CooperationStoreAdapter.ShowViewListener() {

                        @Override
                        public void onSelected(ImageView view, int position) {
                            ids.clear();
                            if (resultses.get(position).isSelect){
                                cooperationStoreEditBt.setText("编辑");
                                resultses.get(position).isSelect = false;
                                ids.remove(resultses.get(position).getId());
                                view.setSelected(false);
                            }else{

                                cooperationStoreEditBt.setText("删除");
                                resultses.get(position).isSelect = true;
                                ids.add(resultses.get(position).getId());
                                view.setSelected(true);
                            }
                        }
                    });

                }


                break;
            case R.id.cooperation_store_add_bt:
                //添加
                Intent intent = new Intent(this,AddCooperativeStoreActivity.class);
                intent.putExtra("is_chain",isChain);
                startActivity(intent);
                break;

        }
    }
}
