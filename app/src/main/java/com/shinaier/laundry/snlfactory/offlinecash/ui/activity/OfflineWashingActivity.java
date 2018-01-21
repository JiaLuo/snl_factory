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
import com.shinaier.laundry.snlfactory.network.entity.WashingEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineWashingAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.ui.fragment.OfflineCashFragment;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.ClearEditText;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * Created by 张家洛 on 2018/1/18.
 */

public class OfflineWashingActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_WASHING_LIST = 0x1;
    private static final int REQUEST_CODE_ADD_CLEANNING = 0x2;
    private static final int REQUEST_CODE_CLEANED_WASHING = 0x3;
    private static final int SCAN_ADD_CLOTHES = 0x4;

    @ViewInject(R.id.edit_search_washing)
    private ClearEditText editSearchWashing;
    @ViewInject(R.id.btn_add_washing)
    private TextView btnAddWashing;
    @ViewInject(R.id.iv_scan_qrcode_washing)
    private ImageView ivScanQrcodeWashing;
    @ViewInject(R.id.ll_empty_datas_washing)
    private LinearLayout llEmptyDatasWashing;
    @ViewInject(R.id.rl_data_list_washing)
    private RelativeLayout rlDataListWashing;
    @ViewInject(R.id.washing_list)
    private ListView washingList;
    @ViewInject(R.id.rl_add_select_washing)
    private RelativeLayout rlAddSelectWashing;
    @ViewInject(R.id.left_bt_operation_washing)
    private TextView leftBtOperationWashing;
    @ViewInject(R.id.right_bt_operation_washing)
    private TextView rightBtOperationWashing;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private int extraFrom;
    private List<WashingEntity.WashingResult> results;
    private OfflineWashingAdapter offlineWashingAdapter;
    private List<String> washIds = new ArrayList<>(); //送洗的id list
    private StringBuffer stringBuffer = new StringBuffer();

    private  boolean isClickAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_washing_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        extraFrom = intent.getIntExtra("extra_from", 0);

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
        if (extraFrom == OfflineCashFragment.DRYING){
            params.put("state","50");
        }else if (extraFrom == OfflineCashFragment.IRONING){
            params.put("state","51");
        }else {
            params.put("state","52");
        }
        requestHttpData(Constants.Urls.URL_POST_WASHING_LIST,REQUEST_CODE_WASHING_LIST,
                FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        if (extraFrom == OfflineCashFragment.DRYING){
            setCenterTitle("烘干");
            rightBtOperationWashing.setText("已烘干");
        }else if (extraFrom == OfflineCashFragment.IRONING){
            setCenterTitle("熨烫");
            rightBtOperationWashing.setText("已熨烫");
        }else {
            setCenterTitle("质检");
            rightBtOperationWashing.setText("已质检");
        }
        leftButton.setOnClickListener(this);
        btnAddWashing.setOnClickListener(this);
        rlAddSelectWashing.setOnClickListener(this);
        rightBtOperationWashing.setOnClickListener(this);
        ivScanQrcodeWashing.setOnClickListener(this);
        leftBtOperationWashing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.btn_add_washing:
                //添加操作
                String inputCleanNum = editSearchWashing.getText().toString();
                if (!TextUtils.isEmpty(inputCleanNum)){
                    addExec(inputCleanNum);
                }else {
                    ToastUtil.shortShow(this,"请输入衣物编码");
                }
                break;
            case R.id.rl_add_select_washing:
                if (!isClickAll){
                    isClickAll = true;
                    rlAddSelectWashing.setSelected(true);
                    for (int i = 0; i < results.size(); i++) {
                        String assist = results.get(i).getAssist();
                        if (assist.equals("0")){
                            results.get(i).isSelect = true;
                        }
                    }

                }else{
                    isClickAll = false;
                    rlAddSelectWashing.setSelected(false);
                    for (int i = 0; i < results.size(); i++) {
                        String assist = results.get(i).getAssist();
                        if (assist.equals("0")){
                            results.get(i).isSelect = false;
                        }
                    }
                }

                offlineWashingAdapter.notifyDataSetChanged();

                break;
            case R.id.right_bt_operation_washing:
                //已清洗
                int right = 2;
                refluxOrCleaned(right);
                break;
            case R.id.iv_scan_qrcode_washing:
                startActivityForResult(new Intent(this,ScanActivity.class),
                        SCAN_ADD_CLOTHES);
                break;
            case R.id.left_bt_operation_washing:
                //返流操作
                int left = 1;
                refluxOrCleaned(left);
                break;

        }
    }

    private void refluxOrCleaned(int which) {
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
            if (which == 1){
                //返流操作
                if (washIds.size() >= 1){
                    Intent intent = new Intent(this,OfflineRefluxEditActivity.class);
                    LogUtil.e("zhang","item_id = " + stringBuffer.toString());
                    intent.putExtra("item_id",stringBuffer.toString());
                    startActivity(intent);
                }else {
                    ToastUtil.shortShow(this,"返流操作只能单件处理");
                }
            }else {
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("token",UserCenter.getToken(this));
                params.put("itemids",stringBuffer.toString());
                if (extraFrom == OfflineCashFragment.DRYING){
                    params.put("moduleid","50");
                }else if (extraFrom == OfflineCashFragment.IRONING){
                    params.put("moduleid","51");
                }else {
                    params.put("moduleid","52");
                }
                requestHttpData(Constants.Urls.URL_POST_CLEANED_WASHING,REQUEST_CODE_CLEANED_WASHING,
                        FProtocol.HttpMethod.POST,params);
            }
        }else {
            if (which == 1){
                ToastUtil.shortShow(this,"请选择返流项目");
            }else {
                if (extraFrom == OfflineCashFragment.DRYING){
                    ToastUtil.shortShow(this,"请选择烘干项目");
                }else if (extraFrom == OfflineCashFragment.IRONING){
                    ToastUtil.shortShow(this,"请选择熨烫项目");
                }else {
                    ToastUtil.shortShow(this,"请选择质检项目");
                }
            }
        }
    }

    private void addExec(String inputCleanNum) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        if (extraFrom == OfflineCashFragment.DRYING){
            params.put("status","50");
        }else if (extraFrom == OfflineCashFragment.IRONING){
            params.put("status","51");
        }else {
            params.put("status","52");
        }
        params.put("clean_sn",inputCleanNum);
        requestHttpData(Constants.Urls.URL_POST_ADD_CLEANNING,
                REQUEST_CODE_ADD_CLEANNING, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_WASHING_LIST:
                if (data != null){
                    WashingEntity washingEntity = Parsers.getWashingEntity(data);
                    if (washingEntity != null){
                        if (washingEntity.getCode() == 0){
                            results = washingEntity.getResults();
                            if (results != null && results.size() > 0){

                                llEmptyDatasWashing.setVisibility(View.GONE);
                                rlDataListWashing.setVisibility(View.VISIBLE);
                                offlineWashingAdapter = new OfflineWashingAdapter(this, results);
                                washingList.setAdapter(offlineWashingAdapter);
                                offlineWashingAdapter.setSelectListener(new OfflineWashingAdapter.SelectWashingListener() {
                                    @Override
                                    public void onSelect(int position,ImageView imageView) {
                                        if (results.get(position).isSelect){
                                            results.get(position).isSelect = false;
//                                            imageView.setSelected(false);
                                        }else {
                                            results.get(position).isSelect = true;
//                                            imageView.setSelected(true);
                                        }

                                        offlineWashingAdapter.notifyDataSetChanged();
                                    }
                                });
                            }else {
                                llEmptyDatasWashing.setVisibility(View.VISIBLE);
                                rlDataListWashing.setVisibility(View.GONE);
                            }
                        }else {
                            ToastUtil.shortShow(this,washingEntity.getMsg());
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
                                    WashingEntity washingEntity = new WashingEntity();
                                    WashingEntity.WashingResult result = washingEntity.new WashingResult();
                                    result.setId(id);
                                    result.setItemName(itemName);
                                    result.setCleanSn(cleanSn);
                                    result.setAssist("0");
                                    results.add(result);

                                    llEmptyDatasWashing.setVisibility(View.GONE);
                                    rlDataListWashing.setVisibility(View.VISIBLE);
                                    offlineWashingAdapter = new OfflineWashingAdapter(this, results);
                                    washingList.setAdapter(offlineWashingAdapter);
                                    offlineWashingAdapter.setSelectListener(new OfflineWashingAdapter.SelectWashingListener() {
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
                            offlineWashingAdapter.notifyDataSetChanged();
                            ToastUtil.shortShow(this,addExecEntity.getMsg());
                        } else {
                            ToastUtil.shortShow(this,addExecEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_CLEANED_WASHING:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            loadData();
                            rlAddSelectWashing.setSelected(false);
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
                        editSearchWashing.setText(clothesNumberCode);
                        addExec(clothesNumberCode);
                    }
                }
                break;
        }
    }
}
