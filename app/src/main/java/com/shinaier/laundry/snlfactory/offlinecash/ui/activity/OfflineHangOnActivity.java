package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.shinaier.laundry.snlfactory.network.entity.OfflineHangOnEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineHangOnAdapter;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.ClearEditText;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * 上挂列表
 * Created by 张家洛 on 2017/7/25.
 */

public class OfflineHangOnActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ADD_CLEANNING = 0x1;
    private static final int REQUEST_CODE_CLEANED_HANGON = 0x2;
    private static final int REQUEST_CODE_HANG_ON_LIST = 0x3;
    private static final int CLOTHES_NUMBER_CODE = 0x5;

    @ViewInject(R.id.hang_on_list)
    private ListView hangOnList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.edit_search_hang_on)
    private ClearEditText editSearchHangOn;
    @ViewInject(R.id.btn_add_hang_on)
    private TextView btnAddHangOn;
    @ViewInject(R.id.ll_empty_datas_hang_on)
    private LinearLayout llEmptyDatasHangOn;
    @ViewInject(R.id.rl_data_list_hang_on)
    private RelativeLayout rlDataListHangOn;
    @ViewInject(R.id.iv_scan_qrcode_hang_on)
    private ImageView ivScanQrcodeHangOn;
    @ViewInject(R.id.rl_add_select_hang_on)
    private RelativeLayout rlAddSelectHangOn;
    @ViewInject(R.id.left_bt_operation)
    private TextView leftBtOperation;
    @ViewInject(R.id.right_bt_operation)
    private TextView rightBtOperation;

    private CollectClothesDialog collectClothesDialog;
    private OfflineHangOnAdapter offlineHangOnAdapter;
    private List<OfflineHangOnEntity.OfflineHangOnResult> results;
    private List<String> washIds = new ArrayList<>(); //送洗的id list
    private StringBuffer stringBuffer = new StringBuffer();

    private  boolean isClickAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_hang_on);
        ViewInjectUtils.inject(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 上挂列表
     *
     */
    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_OFFLINE_HANG_ON_LIST,
                REQUEST_CODE_HANG_ON_LIST, FProtocol.HttpMethod.POST,params); //新版送洗
    }

    private void initView() {
        setCenterTitle("上挂");
        leftButton.setOnClickListener(this);
        btnAddHangOn.setOnClickListener(this);
        ivScanQrcodeHangOn.setOnClickListener(this);
        rlAddSelectHangOn.setOnClickListener(this);
        rightBtOperation.setOnClickListener(this);
        leftBtOperation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.btn_add_hang_on:
                String inputCleanNum = editSearchHangOn.getText().toString();
                if (!TextUtils.isEmpty(inputCleanNum)){
                    addExec(inputCleanNum);
                }else {
                    ToastUtil.shortShow(this,"请输入衣物编码");
                }
                break;
            case R.id.iv_scan_qrcode_hang_on:
                startActivityForResult(new Intent(this,ScanActivity.class),
                        CLOTHES_NUMBER_CODE);
                break;
            case R.id.rl_add_select_hang_on:
                if (!isClickAll){
                    isClickAll = true;
                    rlAddSelectHangOn.setSelected(true);
                    for (int i = 0; i < results.size(); i++) {
                        String assist = results.get(i).getAssist();
                        if (assist.equals("0")){
                            results.get(i).isSelect = true;
                        }
                    }

                }else{
                    isClickAll = false;
                    rlAddSelectHangOn.setSelected(false);
                    for (int i = 0; i < results.size(); i++) {
                        String assist = results.get(i).getAssist();
                        if (assist.equals("0")){
                            results.get(i).isSelect = false;
                        }
                    }
                }

                offlineHangOnAdapter.notifyDataSetChanged();
                break;
            case R.id.right_bt_operation:
                //上挂操作
                int right = 2;
                refluxOrHangOn(right);
                break;
            case R.id.left_bt_operation:
                //返流
                int left = 1;
                refluxOrHangOn(left);
                break;
        }
    }

    private void refluxOrHangOn(int which) {
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
                    intent.putExtra("item_id",stringBuffer.toString());
                    startActivity(intent);
                }else {
                    ToastUtil.shortShow(this,"返流操作只能单件处理");
                }
            }else {
                LogUtil.e("zhang","stringbuffer = " + stringBuffer.toString());
                View view = View.inflate(this,R.layout.hang_on_view,null);
                final EditText edBeginOrderNum = (EditText) view.findViewById(R.id.ed_begin_order_num);
                final EditText edEndOrderNum = (EditText) view.findViewById(R.id.ed_end_order_num);
                TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
                TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm);

                collectClothesDialog = new CollectClothesDialog(this, R.style.DialogTheme,view);
                collectClothesDialog.show();
                revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        collectClothesDialog.dismiss();
                    }
                });
                revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String beginInputNum = edBeginOrderNum.getText().toString();
                        String endInputNum = edEndOrderNum.getText().toString();

                        if (!TextUtils.isEmpty(beginInputNum) && !TextUtils.isEmpty(endInputNum)){
                            int begin = Integer.valueOf(beginInputNum);
                            int end = Integer.valueOf(endInputNum);
                            if (end >= begin){ // 结束挂号要比开始挂号大
//                                dialog.setContent("加载中");
//                                dialog.show();
                                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                params.put("token",UserCenter.getToken(OfflineHangOnActivity.this));
                                params.put("itemids",stringBuffer.toString());
                                params.put("moduleid","91");
                                params.put("startnum",beginInputNum);
                                requestHttpData(Constants.Urls.URL_POST_HANG_ON,REQUEST_CODE_CLEANED_HANGON,
                                        FProtocol.HttpMethod.POST,params);
                            }else {
                                ToastUtil.shortShow(OfflineHangOnActivity.this,"您输入的挂号不正确，请重新输入");
                            }
                        }else {
                            ToastUtil.shortShow(OfflineHangOnActivity.this,"请输入挂号");
                        }
                    }
                });


            }
        }else {
            if (which == 1){
                ToastUtil.shortShow(this,"请选择返流项目");
            }else {
                ToastUtil.shortShow(this,"请选择上挂项目");
            }
        }
    }

    private void addExec(String inputCleanNum) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        params.put("status","91");
        params.put("clean_sn",inputCleanNum);
        requestHttpData(Constants.Urls.URL_POST_ADD_CLEANNING,
                REQUEST_CODE_ADD_CLEANNING, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_HANG_ON_LIST:
                if (data != null){
                    OfflineHangOnEntity offlineHangOnEntity = Parsers.getOfflineHangOnEntity(data);

                    if (offlineHangOnEntity != null){
                        if (offlineHangOnEntity.getCode() == 0){
                            results = offlineHangOnEntity.getResults();
                            if (results != null && results.size() > 0){
                                llEmptyDatasHangOn.setVisibility(View.GONE);
                                rlDataListHangOn.setVisibility(View.VISIBLE);
                                offlineHangOnAdapter = new OfflineHangOnAdapter(this,
                                        results);
                                hangOnList.setAdapter(offlineHangOnAdapter);
                                offlineHangOnAdapter.setSelectListener(new OfflineHangOnAdapter.SelectListener() {
                                    @Override
                                    public void onSelect(int position, ImageView imageView) {
                                        if (results.get(position).isSelect){
                                            results.get(position).isSelect = false;
//                                            imageView.setSelected(false);
                                        }else {
                                            results.get(position).isSelect = true;
//                                            imageView.setSelected(true);
                                        }
                                        offlineHangOnAdapter.notifyDataSetChanged();
                                    }
                                });

                            }else {
                                llEmptyDatasHangOn.setVisibility(View.VISIBLE);
                                rlDataListHangOn.setVisibility(View.GONE);
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineHangOnEntity.getMsg());
                        }
                    }else {
                        llEmptyDatasHangOn.setVisibility(View.VISIBLE);
                        rlDataListHangOn.setVisibility(View.GONE);
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
                                    OfflineHangOnEntity offlineHangOnEntity = new OfflineHangOnEntity();
                                    OfflineHangOnEntity.OfflineHangOnResult result = offlineHangOnEntity.new OfflineHangOnResult();
                                    result.setId(id);
                                    result.setItemName(itemName);
                                    result.setCleanSn(cleanSn);
                                    result.setState("0");
                                    result.setAssist("0");
                                    results.add(result);

                                    llEmptyDatasHangOn.setVisibility(View.GONE);
                                    rlDataListHangOn.setVisibility(View.VISIBLE);
                                    offlineHangOnAdapter = new OfflineHangOnAdapter(this, results);
                                    hangOnList.setAdapter(offlineHangOnAdapter);
                                    offlineHangOnAdapter.setSelectListener(new OfflineHangOnAdapter.SelectListener() {
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
                                if (cleanSn1 != null){
                                    if (cleanSn1.equals(cleanSn)){
                                        results.get(i).isSelect = true;
                                        break;
                                    }
                                }
                            }
                            offlineHangOnAdapter.notifyDataSetChanged();
                            ToastUtil.shortShow(this,addExecEntity.getMsg());
                        } else {
                            ToastUtil.shortShow(this,addExecEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_CLEANED_HANGON:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            ToastUtil.shortShow(this,"上挂成功");
                            collectClothesDialog.dismiss();
                            loadData(); //上挂列表获取数据
                            rlAddSelectHangOn.setSelected(false);
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
            case CLOTHES_NUMBER_CODE:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String clothesNumberCode = data.getStringExtra("pay_code");
                        editSearchHangOn.setText(clothesNumberCode);
                        addExec(clothesNumberCode);
                    }
                }
                break;
        }
    }
}
