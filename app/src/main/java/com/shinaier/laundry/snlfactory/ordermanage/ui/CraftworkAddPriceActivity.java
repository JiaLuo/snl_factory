package com.shinaier.laundry.snlfactory.ordermanage.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CraftworkAddPriceEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.ui.MemberInfoActivity;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CraftworkAddPriceAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.view.EditPriceDialog;
import com.shinaier.laundry.snlfactory.ordermanage.view.ReviseMaintainValueDialog;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.text.DecimalFormat;
import java.util.IdentityHashMap;
import java.util.regex.Pattern;



/**
 * Created by 张家洛 on 2017/3/1.
 */

public class CraftworkAddPriceActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CRAFTWORK_PLUS_PRICE = 0x1;
    private static final int REQUEST_CODE_REVISE_CRAFTWORK_PRICE = 0x2;
    private static final int REQUEST_CODE_CONFIRM_CONSIGEE = 0x3;
    private static final int REQUEST_CODE_MAINTAIN_VALUE = 0x5;
    public static final int EXTRA_FROM = 0x4;
    public static final int REQUEST_CODE_EDIT_ITEM = 0x6;

    @ViewInject(R.id.craftwork_plus_list)
    private WrapHeightListView craftworkPlusList;
    @ViewInject(R.id.clothes_total_num)
    private TextView clothesTotalNum;
    @ViewInject(R.id.clothes_total_price)
    private TextView clothesTotalPrice;
    @ViewInject(R.id.clothes_freight)
    private TextView clothesFreight;
    @ViewInject(R.id.clothes_freight_info)
    private TextView clothesFreightInfo;
    @ViewInject(R.id.total_price)
    private TextView totalPrice;
    @ViewInject(R.id.confirm_consignee)
    private TextView confirmConsignee;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.ll_craftwork_edit)
    private LinearLayout llCraftworkEdit;
    @ViewInject(R.id.craftwork_total_num)
    private TextView craftworkTotalNum;

    private String id;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Bundle data = msg.getData();
                    String price = data.getString("price");
                    String remarks = data.getString("remarks");
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token", UserCenter.getToken(CraftworkAddPriceActivity.this));
                    params.put("id",itemId);
                    if (!TextUtils.isEmpty(price)){
                        if (!TextUtils.isEmpty(remarks)){
                            params.put("special",price);
                            params.put("special_comment",remarks);
                        }else {
                            ToastUtil.shortShow(CraftworkAddPriceActivity.this,"请输入加价备注");
                            return;
                        }
                    }else {
                        ToastUtil.shortShow(CraftworkAddPriceActivity.this,"请输入价格");
                        return;
                    }
                    requestHttpData(Constants.Urls.URL_POST_REVISE_CRAFTWORK_PRICE,REQUEST_CODE_REVISE_CRAFTWORK_PRICE,
                            FProtocol.HttpMethod.POST,params);
                    break;
                case 2:
                    String reviseMaintainValue= (String) msg.obj;
                    if (!TextUtils.isEmpty(reviseMaintainValue)){
                        if(isInteger(reviseMaintainValue)){
                            double reviseMaintainMoney = Double.valueOf(reviseMaintainValue);
                            double i = reviseMaintainMoney / 200;
                            IdentityHashMap<String,String> maintainValue = new IdentityHashMap<>();
                            maintainValue.put("token",UserCenter.getToken(CraftworkAddPriceActivity.this));
                            maintainValue.put("id",maintainId);
                            maintainValue.put("hedging", String.valueOf(i));
                            requestHttpData(Constants.Urls.URL_POST_REVISE_MAINTAIN_VALUE,REQUEST_CODE_MAINTAIN_VALUE,
                                    FProtocol.HttpMethod.POST,maintainValue);
                        }else {
                            ToastUtil.shortShow(CraftworkAddPriceActivity.this,"只能输入整数");
                        }
                    }else {
                        ToastUtil.shortShow(CraftworkAddPriceActivity.this,"请输入保值金额");
                    }
                    break;
            }
        }
    };
    private String itemId;
    private EditPriceDialog priceDialog;
    private CraftworkAddPriceEntities craftworkAddPriceEntities;
    private String maintainId;
    private ReviseMaintainValueDialog maintainValueDialog;
    private int extraFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.craftwork_add_price_act);
        ViewInjectUtils.inject(this);
        ExitManager.instance.addItemActivity(this);
        ExitManager.instance.addOfflineCollectActivity(this);
        ExitManager.instance.editOfflineCollectActivity(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        extraFrom = intent.getIntExtra("extra_from", 0);
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("id",id);
        requestHttpData(Constants.Urls.URL_POST_CRAFTWORK_PLUS_PRICE,REQUEST_CODE_CRAFTWORK_PLUS_PRICE, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("编辑价格");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
        llCraftworkEdit.setOnClickListener(this);
        confirmConsignee.setOnClickListener(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CRAFTWORK_PLUS_PRICE:
                if(data != null){
                    craftworkAddPriceEntities = Parsers.getCraftworkAddPriceEntities(data);
                    if(craftworkAddPriceEntities != null ){
                        setLoadingStatus(LoadingStatus.GONE);
                        clothesTotalNum.setText(craftworkAddPriceEntities.getTotalNum());
                        clothesTotalPrice.setText("￥" + formatMoney(Double.parseDouble(craftworkAddPriceEntities.getAmount())));
                        if (extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
                            clothesFreight.setVisibility(View.GONE);
                            clothesFreightInfo.setVisibility(View.GONE);
                        }else {
                            clothesFreight.setVisibility(View.VISIBLE);
                            clothesFreightInfo.setVisibility(View.VISIBLE);
                            clothesFreightInfo.setText("￥" + formatMoney(Double.parseDouble(craftworkAddPriceEntities.getFreight())));
                        }
                        totalPrice.setText("￥" + formatMoney(Double.parseDouble(craftworkAddPriceEntities.getTotal())));
                        craftworkTotalNum.setText("￥" + formatMoney(Double.parseDouble(craftworkAddPriceEntities.getTotal())));

                        CraftworkAddPriceAdapter craftworkAddPriceAdapter = new CraftworkAddPriceAdapter(this, craftworkAddPriceEntities.getPriceItemList());
                        craftworkPlusList.setAdapter(craftworkAddPriceAdapter);

                        craftworkAddPriceAdapter.setPositionListener(new CraftworkAddPriceAdapter.PositionListener() {
                            @Override
                            public void onClick(int position) {
                                itemId = craftworkAddPriceEntities.getPriceItemList().get(position).getId();
                                priceDialog = new EditPriceDialog(CraftworkAddPriceActivity.this, R.style.timerDialog,handler);
                                priceDialog.setView();
                                priceDialog.show();
                            }
                        });

                        craftworkAddPriceAdapter.setMaintainReviseListener(new CraftworkAddPriceAdapter.MaintainReviseListener() {
                            @Override
                            public void onMaintainRevise(int position) {
                                maintainId = craftworkAddPriceEntities.getPriceItemList().get(position).getId();
                                maintainValueDialog = new ReviseMaintainValueDialog(CraftworkAddPriceActivity.this
                                        , R.style.timerDialog,handler);
                                maintainValueDialog.setView();
                                maintainValueDialog.show();
                            }
                        });
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }
                break;
            case REQUEST_CODE_REVISE_CRAFTWORK_PRICE:
                loadData();
                priceDialog.dismiss();
                break;
            case REQUEST_CODE_CONFIRM_CONSIGEE:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
//                        if (extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
//                            Intent intent = new Intent(CraftworkAddPriceActivity.this, CheckClothesActivity.class);
//                            intent.putExtra("id",id);
//                            intent.putExtra("extraFrom",extraFrom);
//                            startActivity(intent);
//                        }else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                            builder.setTitle("提示");
//                            builder.setMessage("是否确认收件？");
//                            builder.setNegativeButton("取消",null);
//                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                        ExitManager.instance.exitItemActivity();
//                                }
//                            });
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//                        }
                        ExitManager.instance.exitItemActivity();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_MAINTAIN_VALUE:
                loadData();
                maintainValueDialog.dismiss();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm_consignee:
//                IdentityHashMap<String,String> params = new IdentityHashMap<>();
//                params.put("token",UserCenter.getToken(this));
//                params.put("id",id);
//                requestHttpData(Constants.Urls.URL_POST_CONFRIM_CONSIGNEE,REQUEST_CODE_CONFIRM_CONSIGEE,
//                        FProtocol.HttpMethod.POST,params);
                if (extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
                    Intent intent = new Intent(CraftworkAddPriceActivity.this, CheckClothesActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("extraFrom",extraFrom);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("是否确认收件？");
                    builder.setNegativeButton("取消",null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                                        ExitManager.instance.exitItemActivity();
                            IdentityHashMap<String,String> params = new IdentityHashMap<>();
                            params.put("token",UserCenter.getToken(CraftworkAddPriceActivity.this));
                            params.put("id",id);
                            requestHttpData(Constants.Urls.URL_POST_CONFRIM_CONSIGNEE,REQUEST_CODE_CONFIRM_CONSIGEE,
                                        FProtocol.HttpMethod.POST,params);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                break;
            case R.id.left_button:
                if(extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
                    finish();
                }else {
                    ExitManager.instance.exitItemActivity();
                }
                break;
            case R.id.ll_craftwork_edit:
                Intent intent = new Intent(this,AddProjectActivity.class);
                intent.putExtra("orderId",id);
                intent.putExtra("extraFrom",EXTRA_FROM);
                startActivityForResult(intent,REQUEST_CODE_EDIT_ITEM);
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;
        }
    }

    public String formatMoney(double money){
        DecimalFormat df = new DecimalFormat("###0.00");
        return df.format(money);
    }

    /**
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @Override
    public void onBackPressed() {
        if(extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
            LogUtil.e("zhang","555555555555555");
            finish();
        }else {
            LogUtil.e("zhang","66666666666666666");
            ExitManager.instance.exitItemActivity();

        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_CRAFTWORK_PLUS_PRICE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_EDIT_ITEM:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        loadData();
                    }
                }
                break;
        }
    }
}
