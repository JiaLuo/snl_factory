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
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineHangOnEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineSendLaundryEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineHangOnAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineSendLaundryAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.ui.fragment.OfflineCashFragment;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.ClearEditText;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/7/25.
 */

public class SendLaundryActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_SEND_LAUNDRY_LIST = 0x1;
    private static final int REQUEST_CODE_SEND_LAUNDRY = 0x2;
    private static final int REQUEST_CODE_HANG_ON_LIST = 0x3;
    private static final int REQUEST_CODE_HANG_ON = 0x4;

    @ViewInject(R.id.send_laundry_list)
    private ListView sendLaundryList;
    @ViewInject(R.id.send_laundry_confirm)
    private TextView sendLaundryConfirm;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.edit_search)
    private ClearEditText editSearch;
    @ViewInject(R.id.btn_start_search)
    private TextView btnStartSearch;
    @ViewInject(R.id.ll_empty_datas)
    private LinearLayout llEmptyDatas;
    @ViewInject(R.id.rl_data_list)
    private RelativeLayout rlDataList;

    private int extraFrom;
    private CollectClothesDialog collectClothesDialog;
    private OfflineSendLaundryEntity offlineSendLaundryEntity;

    private List<String> sendLaundryIds = new ArrayList<>(); //送洗的id list
    private List<String> hangOnIds = new ArrayList<>(); //上挂的id list
    private OfflineHangOnEntity offlineHangOnEntity;
    private OfflineSendLaundryAdapter offlineSendLaundryAdapter;
    private OfflineHangOnAdapter offlineHangOnAdapter;
    private CommonDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_laundry_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        extraFrom = intent.getIntExtra("extra_from", 0);
        initView();
    }

    private void sendLaundryData(String orderNumber) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("ordersn",orderNumber);
        requestHttpData(Constants.Urls.URL_POST_OFFLINE_SEND_LAUNDRY_LIST,REQUEST_CODE_SEND_LAUNDRY_LIST, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        dialog = new CommonDialog(this);
        if(extraFrom == OfflineCashFragment.HANGON){
            setCenterTitle("上挂");
            sendLaundryConfirm.setText("确定");
            hangOnData(""); //上挂列表获取数据
        }else {
            setCenterTitle("送洗");
            sendLaundryData(""); //送洗列表获取数据
        }
        sendLaundryConfirm.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        btnStartSearch.setOnClickListener(this);
    }

    private void hangOnData(String orderNum) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("ordersn",orderNum);
        requestHttpData(Constants.Urls.URL_POST_HANG_ON_LIST,REQUEST_CODE_HANG_ON_LIST, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_laundry_confirm:
                if(extraFrom == OfflineCashFragment.HANGON){
                    hangOnIds.clear();
                    final StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < offlineHangOnEntity.getDatas().size(); i++) {
                        if (offlineHangOnEntity.getDatas().get(i).isSelect){
                            String id = offlineHangOnEntity.getDatas().get(i).getId();
                            hangOnIds.add(id);
                        }
                    }

                    if (hangOnIds.size() > 0){
                        for (int i = 0; i < hangOnIds.size(); i++) {
                            if(i == 0){
                                if(hangOnIds.size() == 1){
                                    stringBuffer.append("[").append('"').append(hangOnIds.get(i)).append('"').append("]");
                                }else {
                                    stringBuffer.append("[").append('"').append(hangOnIds.get(i)).append('"').append(",");
                                }
                            }else if(i > 0 && i < hangOnIds.size() -1){
                                stringBuffer.append('"').append(hangOnIds.get(i)).append('"').append(",");
                            }else {
                                stringBuffer.append('"').append(hangOnIds.get(i)).append('"').append("]");
                            }
                        }

                        //上挂
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
                                    if (end > begin){ // 结束挂号要比开始挂号大
                                        dialog.setContent("加载中");
                                        dialog.show();
                                        IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
                                        params.put("token",UserCenter.getToken(SendLaundryActivity.this));
                                        params.put("json_data",stringBuffer.toString());
                                        params.put("start","" + begin);
                                        params.put("end","" + end);
                                        requestHttpData(Constants.Urls.URL_POST_HANG_ON,REQUEST_CODE_HANG_ON, FProtocol.HttpMethod.POST,params);
                                    }else {
                                        ToastUtil.shortShow(SendLaundryActivity.this,"您输入的挂号不正确，请重新输入");
                                    }
                                }else {
                                    ToastUtil.shortShow(SendLaundryActivity.this,"请输入挂号");
                                }
                            }
                        });

                    }else {
                        ToastUtil.shortShow(this,"请选择上挂项目");
                        return;
                    }

                }else {
                    //送洗
                    sendLaundryIds.clear();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < offlineSendLaundryEntity.getDatas().size(); i++) {
                        if (offlineSendLaundryEntity.getDatas().get(i).isSelect){
                            String id = offlineSendLaundryEntity.getDatas().get(i).getId();
                            sendLaundryIds.add(id);
                        }
                    }

                    if (sendLaundryIds.size() > 0){
                        for (int i = 0; i < sendLaundryIds.size(); i++) {
                            if(i == 0){
                                if(sendLaundryIds.size() == 1){
                                    stringBuffer.append("[").append('"').append(sendLaundryIds.get(i)).append('"').append("]");
                                }else {
                                    stringBuffer.append("[").append('"').append(sendLaundryIds.get(i)).append('"').append(",");
                                }
                            }else if(i > 0 && i < sendLaundryIds.size() -1){
                                stringBuffer.append('"').append(sendLaundryIds.get(i)).append('"').append(",");
                            }else {
                                stringBuffer.append('"').append(sendLaundryIds.get(i)).append('"').append("]");
                            }
                        }
                    }else {
                        ToastUtil.shortShow(this,"请选择送洗项目");
                        return;
                    }
                    dialog.setContent("加载中");
                    dialog.show();
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token",UserCenter.getToken(this));
                    params.put("items",stringBuffer.toString());
                    requestHttpData(Constants.Urls.URL_POST_OFFLINE_SEND_LAUNDRY,REQUEST_CODE_SEND_LAUNDRY, FProtocol.HttpMethod.POST,params);
                }
                break;
            case R.id.left_button:
                finish();
                break;
            case R.id.btn_start_search:
                String inputOrderNum = editSearch.getText().toString();
                dialog.setContent("搜索中");
                dialog.show();
                if (extraFrom == OfflineCashFragment.HANGON){
                    hangOnData(inputOrderNum);
                }else {
                    sendLaundryData(inputOrderNum);
                }
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_SEND_LAUNDRY_LIST:
                if (data != null){
                    offlineSendLaundryEntity = Parsers.getOfflineSendLaundryEntity(data);
                    if (offlineSendLaundryEntity != null){
                        if (offlineSendLaundryEntity.getRetcode() == 0){
                            if (offlineSendLaundryEntity.getDatas() != null && offlineSendLaundryEntity.getDatas().size() > 0){
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                                llEmptyDatas.setVisibility(View.GONE);
                                rlDataList.setVisibility(View.VISIBLE);
                                offlineSendLaundryAdapter = new OfflineSendLaundryAdapter(this,
                                        offlineSendLaundryEntity.getDatas());
                                sendLaundryList.setAdapter(offlineSendLaundryAdapter);

                                offlineSendLaundryAdapter.setSelectListener(new OfflineSendLaundryAdapter.SelectListener() {
                                    @Override
                                    public void onSelect(int position) {
                                        if (offlineSendLaundryEntity.getDatas().get(position).isSelect){
                                            offlineSendLaundryEntity.getDatas().get(position).isSelect = false;
                                        }else {
                                            offlineSendLaundryEntity.getDatas().get(position).isSelect = true;
                                        }

                                        offlineSendLaundryAdapter.notifyDataSetChanged();
                                    }
                                });

                            }else {
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                                llEmptyDatas.setVisibility(View.VISIBLE);
                                rlDataList.setVisibility(View.GONE);
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineSendLaundryEntity.getStatus());
                        }
                    }else {
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                        llEmptyDatas.setVisibility(View.VISIBLE);
                        rlDataList.setVisibility(View.GONE);
                    }
                }
                break;
            case REQUEST_CODE_SEND_LAUNDRY:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            ToastUtil.shortShow(this,"送洗成功");
                            sendLaundryData(""); //送洗列表获取数据
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_HANG_ON_LIST:
                if (data != null){
                    offlineHangOnEntity = Parsers.getOfflineHangOnEntity(data);
                    if (offlineHangOnEntity != null){
                        if (offlineHangOnEntity.getRetcode() == 0){
                            if (offlineHangOnEntity.getDatas() != null && offlineHangOnEntity.getDatas().size() > 0){
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                                llEmptyDatas.setVisibility(View.GONE);
                                rlDataList.setVisibility(View.VISIBLE);
                                offlineHangOnAdapter = new OfflineHangOnAdapter(this,
                                        offlineHangOnEntity.getDatas());
                                sendLaundryList.setAdapter(offlineHangOnAdapter);
                                offlineHangOnAdapter.setSelectListener(new OfflineHangOnAdapter.SelectListener() {
                                    @Override
                                    public void onSelect(int position) {
                                        if (offlineHangOnEntity.getDatas().get(position).isSelect){
                                            offlineHangOnEntity.getDatas().get(position).isSelect = false;
                                        }else {
                                            offlineHangOnEntity.getDatas().get(position).isSelect = true;
                                        }
                                        offlineHangOnAdapter.notifyDataSetChanged();
                                    }
                                });
                            }else {
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                                llEmptyDatas.setVisibility(View.VISIBLE);
                                rlDataList.setVisibility(View.GONE);
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineHangOnEntity.getStatus());
                        }
                    }else {
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                        llEmptyDatas.setVisibility(View.VISIBLE);
                        rlDataList.setVisibility(View.GONE);
                    }
                }
                break;
            case REQUEST_CODE_HANG_ON:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            ToastUtil.shortShow(this,"上挂成功");
                            collectClothesDialog.dismiss();
                            hangOnData(""); //上挂列表获取数据
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }
}
