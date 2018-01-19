package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.shinaier.laundry.snlfactory.network.entity.AddExecEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.WashEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineWashAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.ClearEditText;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2018/1/11.
 */

public class OfflineWashActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_WASH_LIST = 0x1;
    private static final int REQUEST_CODE_INTO_FACTORY = 0x3;
    private static final int REQUEST_CODE_ADD_CLEANNING = 0x4;
    private static final int REQUEST_CODE_CLEANED_WASH = 0x5;
    private static final int SCAN_ADD_CLOTHES = 0x6;

    @ViewInject(R.id.edit_search)
    private ClearEditText editSearch;
    @ViewInject(R.id.btn_add)
    private TextView btnAdd;
    @ViewInject(R.id.iv_scan_qrcode)
    private ImageView ivScanQrcode;
    @ViewInject(R.id.ll_empty_datas)
    private LinearLayout llEmptyDatas;
    @ViewInject(R.id.rl_data_list)
    private RelativeLayout rlDataList;
    @ViewInject(R.id.wash_list)
    private ListView washList;
    @ViewInject(R.id.rl_add_select)
    private RelativeLayout rlAddSelect;
    @ViewInject(R.id.right_bt_operation)
    private TextView rightBtOperation;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<String> washIds = new ArrayList<>(); //送洗的id list
    private List<WashEntity.WashResult> results;
    private StringBuffer stringBuffer = new StringBuffer();
    private OfflineWashAdapter offlineWashAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_wash_act);
        ViewInjectUtils.inject(this);
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_WASH_LIST,REQUEST_CODE_WASH_LIST,
                FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("清洗");
        leftButton.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        rightBtOperation.setOnClickListener(this);
        ivScanQrcode.setOnClickListener(this);
        rlAddSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.btn_add:
                String inputCleanNum = editSearch.getText().toString();
                if (!TextUtils.isEmpty(inputCleanNum)){
                    addExec(inputCleanNum);
                }else {
                    ToastUtil.shortShow(this,"请输入衣物编码");
                }
                break;
            case R.id.right_bt_operation:
                //已清洗
                cleaned();
                break;
            case R.id.iv_scan_qrcode:
                startActivityForResult(new Intent(this,ScanActivity.class),
                        SCAN_ADD_CLOTHES);
                break;
            case R.id.rl_add_select:
                boolean isClickAll = false;
                for (int i = 0; i < results.size(); i++) {
                    String assist = results.get(i).getAssist();
                    if (assist.equals("0")){
                        isClickAll = true;
                    }
                }
                if (isClickAll){
                    if (rlAddSelect.isSelected()){
                        rlAddSelect.setSelected(false);
                        for (int i = 0; i < results.size(); i++) {
                            results.get(i).isSelect = false;
                        }
                    }else {
                        rlAddSelect.setSelected(true);
                        for (int i = 0; i < results.size(); i++) {
                            results.get(i).isSelect = true;
                        }
                    }
                }else {
                    ToastUtil.shortShow(this,"没有可选项目");
                }

                if (offlineWashAdapter != null){
                    if (offlineWashAdapter.isTrue){
                        offlineWashAdapter.allSelect(false);
                    }else{
                        offlineWashAdapter.allSelect(true);
                    }
                }
                break;
        }
    }

    private void cleaned() {
        washIds.clear();
        stringBuffer.delete(0,stringBuffer.length());
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).isSelect){
                String id = results.get(i).getId();
                washIds.add(id);
            }
        }

        if (washIds.size() > 0){
            for (int i = 0; i < washIds.size(); i++) {
                if(i == 0){
                    if(washIds.size() == 1){
                        stringBuffer.append(washIds.get(i));
                    }else {
                        stringBuffer.append(washIds.get(i)).append(",");
                    }
                }else if(i > 0 && i < washIds.size() -1){
                    stringBuffer.append(washIds.get(i)).append(",");
                }else {
                    stringBuffer.append(washIds.get(i));
                }
            }
            IdentityHashMap<String,String> params = new IdentityHashMap<>();
            params.put("token",UserCenter.getToken(this));
            params.put("itemids",stringBuffer.toString());
            params.put("moduleid","3");
            requestHttpData(Constants.Urls.URL_POST_CLEANED_WASH,REQUEST_CODE_CLEANED_WASH,
                    FProtocol.HttpMethod.POST,params);

        }else {
            ToastUtil.shortShow(this,"请选择清洗项目");
        }
    }

    private void addExec(String inputCleanNum) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        params.put("status","3");
        params.put("clean_sn",inputCleanNum);
        requestHttpData(Constants.Urls.URL_POST_ADD_CLEANNING,
                REQUEST_CODE_ADD_CLEANNING, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_WASH_LIST:
                if (data != null){
                    WashEntity washEntity = Parsers.getWashEntity(data);
                    if (washEntity != null){
                        if (washEntity.getCode() == 0){
                            results = washEntity.getResults();
                            if (results != null && results.size() > 0){
                                llEmptyDatas.setVisibility(View.GONE);
                                rlDataList.setVisibility(View.VISIBLE);

                                offlineWashAdapter = new OfflineWashAdapter(this, results);
                                washList.setAdapter(offlineWashAdapter);
                                offlineWashAdapter.setSelectListener(new OfflineWashAdapter.SelectListener() {
                                    @Override
                                    public void onSelect(int position,ImageView imageView) {
                                        if (results.get(position).isSelect){
                                            results.get(position).isSelect = false;
                                            imageView.setSelected(false);
                                        }else {
                                            results.get(position).isSelect = true;
                                            imageView.setSelected(true);
                                        }

//                                        offlineWashAdapter.notifyDataSetChanged();
                                    }
                                });
                            }else {
                                llEmptyDatas.setVisibility(View.VISIBLE);
                                rlDataList.setVisibility(View.GONE);
                            }
                        }else {
                            ToastUtil.shortShow(this,washEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_INTO_FACTORY:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            loadData();
                            rlAddSelect.setSelected(false);
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ADD_CLEANNING:
                if (data != null){
                    AddExecEntity addExecEntity = Parsers.getAddExecEntity(data);
                    if (addExecEntity != null){
                        if (addExecEntity.getCode() == 0){
                            if (addExecEntity.getResult() != null){
                                String id = addExecEntity.getResult().getId();
                                String itemName = addExecEntity.getResult().getItemName();
                                String cleanSn = addExecEntity.getResult().getCleanSn();
                                if (results != null){
                                    WashEntity washEntity = new WashEntity();
                                    WashEntity.WashResult result = washEntity.new WashResult();
                                    result.setId(id);
                                    result.setItemName(itemName);
                                    result.setCleanSn(cleanSn);
                                    result.setAssist("0");
                                    result.setCleanState("0");
                                    results.add(result);
                                    llEmptyDatas.setVisibility(View.GONE);
                                    rlDataList.setVisibility(View.VISIBLE);
                                    offlineWashAdapter = new OfflineWashAdapter(this, results);
                                    washList.setAdapter(offlineWashAdapter);
                                    offlineWashAdapter.setSelectListener(new OfflineWashAdapter.SelectListener() {
                                        @Override
                                        public void onSelect(int position, ImageView imageView) {
                                            if (results.get(position).isSelect){
                                                results.get(position).isSelect = false;
                                                imageView.setSelected(false);
                                            }else {
                                                results.get(position).isSelect = true;
                                                imageView.setSelected(true);
                                            }
                                        }
                                    });
                                    ToastUtil.shortShow(this,"添加成功");
                                }
                            }

                        }else if (addExecEntity.getCode() == 1){
                            //该衣物编码已经在列表中
                            String cleanSn = addExecEntity.getResult().getCleanSn();
                            for (int i = 0; i < results.size(); i++) {
                                String cleanSn1 = results.get(i).getCleanSn();
                                if (cleanSn1.equals(cleanSn)){
                                    results.get(i).isSelect = true;
                                    break;
                                }
                            }
                            offlineWashAdapter.notifyDataSetChanged();
                            ToastUtil.shortShow(this,addExecEntity.getMsg());
                        }else {
                            LogUtil.e("zhang","msg = " + addExecEntity.getMsg());
                            ToastUtil.shortShow(this,addExecEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_CLEANED_WASH:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            ToastUtil.shortShow(this,"已清洗");
                            loadData();
                            rlAddSelect.setSelected(false);
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SCAN_ADD_CLOTHES:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String clothesNumberCode = data.getStringExtra("pay_code");
                        editSearch.setText(clothesNumberCode);
                        addExec(clothesNumberCode);
                    }
                }
                break;
        }
    }
}
