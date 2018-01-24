package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.EditCommodityEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;


/**
 * 商品编辑
 * Created by 张家洛 on 2017/12/14.
 */

public class ManageCommodityEditActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_EDIT_COMMODITY_SHOW = 0x1;
    private static final int REQUEST_CODE_COMMODITY_COMFRIM = 0x2;

    @ViewInject(R.id.commodity_edit_clothes_name)
    private TextView commodityEditClothesName;
    @ViewInject(R.id.ed_input_clothes_price)
    private EditText edInputClothesPrice;
    @ViewInject(R.id.ed_input_wash_cycle)
    private EditText edInputWashCycle;
    @ViewInject(R.id.ed_input_normal_discount)
    private EditText edInputNormalDiscount;
    @ViewInject(R.id.tv_not_normal)
    private TextView tvNotNormal;
    @ViewInject(R.id.tv_normal)
    private TextView tvNormal;
    @ViewInject(R.id.et_color_setting_describe)
    private EditText etColorSettingDescribe;
    @ViewInject(R.id.color_setting_describe_max_num)
    private TextView colorSettingDescribeMaxNum;
    @ViewInject(R.id.confirm)
    private TextView confirm;

    private String itemId;
    private EditCommodityEntity.EditCommodityResult commodityResult;
    private String isDiscount = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_commodity_edit_act);
        ViewInjectUtils.inject(this);
        itemId = getIntent().getStringExtra("item_id");
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("item_id",itemId);
        requestHttpData(Constants.Urls.URL_POST_EDIT_COMMODITY_SHOW,REQUEST_CODE_EDIT_COMMODITY_SHOW, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("商品管理");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        confirm.setOnClickListener(this);
        tvNotNormal.setOnClickListener(this);
        tvNormal.setOnClickListener(this);
        etColorSettingDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                colorSettingDescribeMaxNum.setText(s.length()+ "/10");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_EDIT_COMMODITY_SHOW:
                if (data != null){
                    EditCommodityEntity editCommodityEntity = Parsers.getEditCommodityEntity(data);
                    if (editCommodityEntity != null){
                        setLoadingStatus(LoadingStatus.GONE);
                        if (editCommodityEntity.getCode() == 0){
                            setLoadingStatus(LoadingStatus.GONE);
                            if (editCommodityEntity.getResult() != null){
                                commodityResult = editCommodityEntity.getResult();
                                setInfo();
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(this, editCommodityEntity.getMsg());
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }
                break;
            case REQUEST_CODE_COMMODITY_COMFRIM:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            ToastUtil.shortShow(this,"修改成功");
                            finish();
                        }else if(entity.getRetcode() == 1000){
                            ToastUtil.shortShow(this,"您没有任何修改");
                        } else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    private void setInfo() {
        commodityEditClothesName.setText(commodityResult.getItemName());
        edInputClothesPrice.setText(commodityResult.getItemPrice());
        edInputWashCycle.setText(commodityResult.getItemCycle());
        edInputNormalDiscount.setText(commodityResult.getItemDiscount());
        //会员是否打折：1-是；然否
        if (commodityResult.getHasDiscount().equals("1")){
            isDiscount = "1";
            tvNotNormal.setSelected(false);
            tvNormal.setSelected(true);
        }else {
            isDiscount = "0";
            tvNotNormal.setSelected(true);
            tvNormal.setSelected(false);
        }
        etColorSettingDescribe.setText(commodityResult.getItemForecast());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                //确认按钮
                String desc = etColorSettingDescribe.getText().toString();
                String inputDiscount = edInputNormalDiscount.getText().toString();
                double v1 = Double.parseDouble(inputDiscount);
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("token",UserCenter.getToken(this));
                params.put("item_id",itemId);
                params.put("item_price",edInputClothesPrice.getText().toString());
                params.put("item_cycle",edInputWashCycle.getText().toString());
                if (v1 <= 1){
                    ToastUtil.shortShow(this,"折扣不能是0折");
                    return;
                }
                params.put("item_discount",edInputNormalDiscount.getText().toString());
                params.put("has_discount",isDiscount);//是否打折：1-是，0-否
                if (!TextUtils.isEmpty(desc)){  //洗后预估可以不填，所以做一个非空判断
                    params.put("item_forecast",desc);
                }else {
                    params.put("item_forecast","");
                }
                requestHttpData(Constants.Urls.URL_POST_COMMODITY_COMFRIM,REQUEST_CODE_COMMODITY_COMFRIM,
                        FProtocol.HttpMethod.POST,params);
                break;
            case R.id.tv_normal:
                //会员打折
                if (tvNormal.isSelected()){
                    tvNormal.setSelected(false);
                }else {
                    isDiscount = "1";
                    tvNormal.setSelected(true);
                    tvNotNormal.setSelected(false);
                }
                break;
            case R.id.tv_not_normal:
                //会员不打折
                if (tvNotNormal.isSelected()){
                    tvNotNormal.setSelected(false);
                }else {
                    isDiscount = "0";
                    tvNotNormal.setSelected(true);
                    tvNormal.setSelected(false);
                }
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_EDIT_COMMODITY_SHOW:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
