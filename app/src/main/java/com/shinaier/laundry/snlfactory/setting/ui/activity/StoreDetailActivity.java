package com.shinaier.laundry.snlfactory.setting.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.view.DoorToDoorServiceDialog;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.StoreInfoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.setting.adapter.EditStoreModularAdapter;
import com.shinaier.laundry.snlfactory.setting.adapter.MerchantCardDiscountInfoAdapter;
import com.shinaier.laundry.snlfactory.setting.adapter.StoreModularAdapter;
import com.shinaier.laundry.snlfactory.setting.entity.StoreModular;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.setting.view.RevisePhoneDialog;
import com.shinaier.laundry.snlfactory.setting.view.ReviseServiceDialog;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/2/17.
 */

public class StoreDetailActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_STORE_INFO = 0x1;
    private static final int REQUEST_CODE_REVISE_PHONE = 0x2;
//    private static final int REQUEST_CODE_REVISE_RANGE = 0x3;
//    private static final int REQUEST_CODE_REVISE_MONEY = 0x4;
    private static final int REQUEST_CODE_REVISE_STORE_MODULAR = 0x3;

    @ViewInject(R.id.store_num)
    private TextView storeNum;
    @ViewInject(R.id.store_name)
    private TextView storeName;
//    @ViewInject(R.id.rl_store_qr_code)
//    private RelativeLayout rlStoreQrCode;
    @ViewInject(R.id.store_phone_num)
    private TextView storePhoneNum;
    @ViewInject(R.id.rl_service_scope)
    private RelativeLayout rlServiceScope;
    @ViewInject(R.id.store_address_detail)
    private TextView storeAddressDetail;
    @ViewInject(R.id.service_scope_num)
    private TextView serviceScopeNum;
    @ViewInject(R.id.home_service_num)
    private TextView homeServiceNum;
    @ViewInject(R.id.fulled_subtract_num)
    private TextView fulledSubtractNum;
    @ViewInject(R.id.fulled_subtract_money)
    private TextView fulledSubtractMoney;
    @ViewInject(R.id.rl_store_phone)
    private RelativeLayout rlStorePhone;
    @ViewInject(R.id.rl_door_to_door_service)
    private RelativeLayout rlDoorToDoorService;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.rl_vip_info)
    private RelativeLayout rlVipInfo;


    @ViewInject(R.id.rl_store_info)
    private RelativeLayout rlStoreInfo;
    @ViewInject(R.id.store_info_content)
    private TextView storeInfoContent;
    @ViewInject(R.id.rl_store_modular)
    private RelativeLayout rlStoreModular;
    @ViewInject(R.id.tore_modular_list)
    private WrapHeightGridView toreModularList;
    @ViewInject(R.id.discount_info_list)
    private WrapHeightListView discountInfoList;

    private StoreInfoEntity storeInfoEntity;
    private ReviseServiceDialog serviceDialog;
    private RevisePhoneDialog phoneDialog;
    private DoorToDoorServiceDialog doorToDoorServiceDialog;
    private List<StoreModular> storeModulars = new ArrayList<>();
    private List<String> modularStrings = new ArrayList<>(); //送洗的id list
    private CollectClothesDialog collectClothesDialog;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String edRevisePhone = (String) msg.obj;
                    if(!TextUtils.isEmpty(edRevisePhone)){
                        reviseStoreInfo(msg.what,edRevisePhone,"","");
                    }else {
                        ToastUtil.shortShow(StoreDetailActivity.this,"填写内容不能为空");
                    }
                    break;
                case 2:
                    String reviseRange = (String)msg.obj;
                    if(!TextUtils.isEmpty(reviseRange)){
                        reviseStoreInfo(msg.what,reviseRange,"","");
                    }else {
                        ToastUtil.shortShow(StoreDetailActivity.this,"填写内容不能为空");
                    }
                    break;
                case 3:
                    Bundle data = msg.getData();
                    String reviseDoorServiceMoney = data.getString("reviseDoorServiceMoney");
                    String reviseClothesNumber = data.getString("reviseClothesNumber");
                    String reviseMoney = data.getString("reviseMoney");
                    if (!TextUtils.isEmpty(reviseDoorServiceMoney)){
                        if (!TextUtils.isEmpty(reviseClothesNumber)){
                            if (!TextUtils.isEmpty(reviseMoney)){
                                reviseStoreInfo(msg.what,reviseDoorServiceMoney,reviseClothesNumber,reviseMoney);
                            }else {
                                ToastUtil.shortShow(StoreDetailActivity.this,"请输入满减金额");
                            }
                        }else {
                            ToastUtil.shortShow(StoreDetailActivity.this,"请输入满减件数");
                        }
                    }else {
                        ToastUtil.shortShow(StoreDetailActivity.this,"请输入上门服务费");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initView() {
        setCenterTitle("门店信息");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
//        rlStoreQrCode.setOnClickListener(this);
        leftButton.setOnClickListener(this);

    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MERCHANT_INFO,REQUEST_CODE_STORE_INFO, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_STORE_INFO:
                if(data != null){
                    storeInfoEntity = Parsers.getStoreInfoEntity(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if (storeInfoEntity != null){
                        if (storeInfoEntity.getCode() == 0){
                            StoreInfoEntity.StoreInfoResult result = storeInfoEntity.getResult();
                            setStoreInfo(result);
                            rlStorePhone.setOnClickListener(this);
                            rlServiceScope.setOnClickListener(this);
                            rlDoorToDoorService.setOnClickListener(this);
                            rlVipInfo.setOnClickListener(this);
                            rlStoreInfo.setOnClickListener(this);
                            rlStoreModular.setOnClickListener(this);
                        }else {
                            ToastUtil.shortShow(this,storeInfoEntity.getMsg());
                        }
                    }

                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_REVISE_PHONE:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        loadData();
                        ToastUtil.shortShow(this,"修改成功");
                        dialogDismiss(phoneDialog,serviceDialog,doorToDoorServiceDialog);
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_REVISE_STORE_MODULAR:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null) {
                        if (entity.getRetcode() == 0){
                            if (collectClothesDialog != null){
                                if (collectClothesDialog.isShowing()){
                                    collectClothesDialog.dismiss();
                                    loadData();
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    /**
     * 谈起的dialog关闭
     * @param phoneDialog 修改电话的dialog
     * @param serviceDialog 服务范围的dialog
     * @param doorToDoorServiceDialog 上门服务费dialog
     */
    private void dialogDismiss(RevisePhoneDialog phoneDialog, ReviseServiceDialog serviceDialog, DoorToDoorServiceDialog doorToDoorServiceDialog) {
        if (phoneDialog != null){
            if (phoneDialog.isShowing()){
                phoneDialog.dismiss();
            }
        }

        if (serviceDialog != null){
            if (serviceDialog.isShowing()){
                serviceDialog.dismiss();
            }
        }

        if (doorToDoorServiceDialog != null){
            if (doorToDoorServiceDialog.isShowing()){
                doorToDoorServiceDialog.dismiss();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.rl_store_qr_code:
//                Intent qrCodeIntent = new Intent(this, WebViewActivity.class);
//                qrCodeIntent.putExtra(WebViewActivity.EXTRA_URL, storeInfoEntity.getQrCode());
//                qrCodeIntent.putExtra(WebViewActivity.EXTRA_TITLE, "我的二维码");
//                startActivity(qrCodeIntent);
//                break;
            case R.id.rl_store_phone:
                //弹起修改电话的dialog
                phoneDialog = new RevisePhoneDialog(this, R.style.timerDialog,handler);
                phoneDialog.setView();
                phoneDialog.show();
                break;
            case R.id.rl_service_scope:
                //弹起修改服务范围的dialog
                serviceDialog = new ReviseServiceDialog(this, R.style.timerDialog,handler);
                serviceDialog.setView();
                serviceDialog.show();
                break;
            case R.id.rl_door_to_door_service:
                //弹起上门服务费的dialog
                doorToDoorServiceDialog = new DoorToDoorServiceDialog(this, R.style.timerDialog,handler,storeInfoEntity);
                doorToDoorServiceDialog.setView();
                doorToDoorServiceDialog.show();
                break;
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;
            case R.id.rl_vip_info:
                //设置专店会员卡
                startActivity( new Intent(this,VipCardDetailActivity.class));
                break;
            case R.id.rl_store_info:
                //设置商家信息
                Intent storeContIntent = new Intent(this,SetStoreInfoActivity.class);
                storeContIntent.putExtra("store_content",storeInfoEntity.getResult().getMerchant().getmDesc());
                startActivity(storeContIntent);
                break;
            case R.id.rl_store_modular:
                //设置门店模块
                planModularDatas();
                modularView();
                break;
        }
    }



    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_STORE_INFO:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }

    /**
     * 修改门店基础信息
     * @param what 点击哪个dialog
     * @param edRevisePhone 门店电话 服务范围  上门服务费
     * @param s 满减件数
     * @param s1 满减金额
     */
    private void reviseStoreInfo(int what, String edRevisePhone, String s, String s1) {
        IdentityHashMap<String,String> revisePhoneParams = new IdentityHashMap<>();
        revisePhoneParams.put("token", UserCenter.getToken(this));
        if (what == 1){
            revisePhoneParams.put("phone_number", edRevisePhone);
        }else if (what == 2){
            revisePhoneParams.put("mrange", edRevisePhone);
        }else if (what == 3){
            revisePhoneParams.put("freight_price", edRevisePhone);
            revisePhoneParams.put("freight_free_num", s);
            revisePhoneParams.put("freight_free_amount", s1);
        }
        requestHttpData(Constants.Urls.URL_POST_STORE_INFO,REQUEST_CODE_REVISE_PHONE, FProtocol.HttpMethod.POST,revisePhoneParams);
    }

    /**
     * 设置门店模块 准备弹起view的数据
     */
    private void planModularDatas() {
        storeModulars.clear();//清空adapter的集合
        int allSize = storeInfoEntity.getAllModule().size(); //获取所有模块的size
        int size = storeInfoEntity.getResult().getModule().size(); //获取门店模块的size
        for (int i = 0; i < allSize; i++) {
            String module = storeInfoEntity.getAllModule().get(i).getModule();
            String moduleName = storeInfoEntity.getAllModule().get(i).getModuleName();
            StoreModular storeMoluar = new StoreModular(module,moduleName);
            storeModulars.add(storeMoluar);
            for (int j = 0; j < size; j++) {
                //判断相同的至为选中
                if (storeInfoEntity.getAllModule().get(i).getModule().equals(storeInfoEntity.getResult().getModule().get(j).getModule())){
                    storeModulars.get(i).isSelect = 0;
                    break;
                }
            }
        }
    }

    //门店模块的view
    private void modularView() {
        View inflate = View.inflate(this, R.layout.store_modular_select_view, null);
        WrapHeightGridView editStoreModularList = inflate.findViewById(R.id.edit_store_modular_list);
        TextView singleTakeClothesCancel = inflate.findViewById(R.id.single_take_clothes_cancel);
        TextView singleTakeClothesConfirm = inflate.findViewById(R.id.single_take_clothes_confirm);
        final EditStoreModularAdapter editStoreModularAdapter = new EditStoreModularAdapter(this,storeModulars);
        editStoreModularList.setAdapter(editStoreModularAdapter);

        editStoreModularAdapter.setSelectModularListener(new EditStoreModularAdapter.SelectModularListener() {
            @Override
            public void onClick(int position) {
                if (storeModulars.get(position).isSelect == 0){
                    storeModulars.get(position).isSelect = 1;
                }else {
                    storeModulars.get(position).isSelect = 0;
                }
                editStoreModularAdapter.notifyDataSetChanged();
            }
        });
        collectClothesDialog = new CollectClothesDialog(this, R.style.DialogTheme, inflate);
        collectClothesDialog.show();

        singleTakeClothesCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectClothesDialog.dismiss();
            }
        });
        singleTakeClothesConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modularStrings.clear();
                for (int i = 0; i < storeInfoEntity.getAllModule().size(); i++) {
                    if (storeModulars.get(i).isSelect == 0){
                        modularStrings.add(storeInfoEntity.getAllModule().get(i).getModule());
                    }
                }

                IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
                params.put("token",UserCenter.getToken(StoreDetailActivity.this));
                params.put("modules",modularStrings.toString());
                requestHttpData(Constants.Urls.URL_POST_REVISE_STORE_MODULAR,REQUEST_CODE_REVISE_STORE_MODULAR, FProtocol.HttpMethod.POST,params);
            }
        });
    }

    /**
     * 这是页面信息
     * @param result 返回来的数据
     */
    private void setStoreInfo(StoreInfoEntity.StoreInfoResult result) {
        storeNum.setText(result.getMerchant().getId()); //门店编号
        storeName.setText(result.getMerchant().getmName()); //门店名称
        storePhoneNum.setText(result.getMerchant().getPhoneNumber()); //门店电话
        serviceScopeNum.setText(result.getMerchant().getmRange() + "km"); //门店服务范围
        storeAddressDetail.setText(result.getMerchant().getmAddress()); //门店地址
        homeServiceNum.setText("上门服务费:" + result.getMerchant().getFreightPrice() + "元");
        fulledSubtractNum.setText("满减件数:" + result.getMerchant().getFreightFreeNum() + "件");
        fulledSubtractMoney.setText("满减金额：" + result.getMerchant().getFreightFreeAmount() + "元");
        storeInfoContent.setText(result.getMerchant().getmDesc()); //商家信息
        //专店会员卡信息
        MerchantCardDiscountInfoAdapter merchantCardDiscountInfoAdapter = new MerchantCardDiscountInfoAdapter(this,result.getCards());
        discountInfoList.setAdapter(merchantCardDiscountInfoAdapter);
        //门店模块
        StoreModularAdapter storeModularAdapter = new StoreModularAdapter(this,result.getModule());
        toreModularList.setAdapter(storeModularAdapter);
    }
}
