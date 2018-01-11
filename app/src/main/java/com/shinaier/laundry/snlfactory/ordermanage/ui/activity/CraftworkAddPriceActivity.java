package com.shinaier.laundry.snlfactory.ordermanage.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CraftworkAddPriceEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.MemberInfoActivity;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CraftworkAddPriceAdapter;
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
    private static final int REQUEST_CODE_CONFIRM_CONSIGEE = 0x3;
    public static final int EXTRA_FROM = 0x4;
    public static final int REQUEST_CODE_EDIT_ITEM = 0x6;
    private static final int EDIT_PRICE_CODE = 0x7;

    @ViewInject(R.id.editor_price_list)
    private WrapHeightListView editorPriceList;
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
    //    @ViewInject(R.id.ll_craftwork_edit)
//    private LinearLayout llCraftworkEdit;
    @ViewInject(R.id.craftwork_total_num)
    private TextView craftworkTotalNum;

    private String id;
    private CraftworkAddPriceEntities craftworkAddPriceEntities;
    private int extraFrom;
    private String isOnline;
    private boolean isCleanSn;

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
        params.put("oid",id);
        requestHttpData(Constants.Urls.URL_POST_CRAFTWORK_PLUS_PRICE,REQUEST_CODE_CRAFTWORK_PLUS_PRICE, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("编辑价格");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
//        llCraftworkEdit.setOnClickListener(this);
        confirmConsignee.setOnClickListener(this);
        editorPriceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CraftworkAddPriceEntities.CraftworkAddPriceResult.CraftworkAddPriceItems craftworkAddPriceItems =
                        craftworkAddPriceEntities.getResult().getItemses().get(i);
                Intent intent = new Intent(CraftworkAddPriceActivity.this,EditorPriceActivity.class);
                intent.putExtra("is_online",isOnline);
                intent.putExtra("position",i);
                intent.putExtra("craftwork_add_price_items",craftworkAddPriceItems);
                startActivityForResult(intent,EDIT_PRICE_CODE);
            }
        });
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
                            clothesTotalNum.setText(craftworkAddPriceEntities.getResult().getItemCount());
                            clothesTotalPrice.setText("￥" + formatMoney(Double.parseDouble(craftworkAddPriceEntities.getResult().getAmount())));
                            isOnline = craftworkAddPriceEntities.getResult().getIsOnline();
                            if (isOnline.equals("0")){ //判断线上订单还是线下订单，服务费显示与不显示 1 为线上 然 则线下
                                clothesFreight.setVisibility(View.GONE);
                                clothesFreightInfo.setVisibility(View.GONE);
                            }else {
                                clothesFreight.setVisibility(View.VISIBLE);
                                clothesFreightInfo.setVisibility(View.VISIBLE);
                                clothesFreightInfo.setText("￥" + formatMoney(Double.parseDouble(craftworkAddPriceEntities.getResult().getFreightPrice())));
                            }
                            totalPrice.setText("￥" + formatMoney(Double.parseDouble(craftworkAddPriceEntities.getResult().getTotalAmount())));
                            craftworkTotalNum.setText("￥" + formatMoney(Double.parseDouble(craftworkAddPriceEntities.getResult().getTotalAmount())));

                            CraftworkAddPriceAdapter craftworkAddPriceAdapter = new CraftworkAddPriceAdapter(this, craftworkAddPriceEntities.getResult().getItemses(),
                                    craftworkAddPriceEntities.getResult().getIsOnline());
                            editorPriceList.setAdapter(craftworkAddPriceAdapter);

                        }else {
                            setLoadingStatus(LoadingStatus.EMPTY);
                        }
                }
                break;
            case REQUEST_CODE_CONFIRM_CONSIGEE:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        ExitManager.instance.exitItemActivity();
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
            case R.id.confirm_consignee:
                if (extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
                    for (int i = 0; i < craftworkAddPriceEntities.getResult().getItemses().size(); i++) {
                        if (craftworkAddPriceEntities.getResult().getItemses().get(i).getCleanSn() == null){
                            ToastUtil.shortShow(this,"项目未挂号");
                            isCleanSn = false;
                            return;
                        }
                    }
                    if (!isCleanSn){
                        Intent intent = new Intent(CraftworkAddPriceActivity.this, CheckClothesActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("extraFrom",extraFrom);
                        startActivity(intent);
                    }

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("是否确认收件？");
                    builder.setNegativeButton("取消",null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        ExitManager.instance.exitItemActivity();
                            IdentityHashMap<String,String> params = new IdentityHashMap<>();
                            params.put("token",UserCenter.getToken(CraftworkAddPriceActivity.this));
                            params.put("oid",id);
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
            //暂时不要编辑功能
//            case R.id.ll_craftwork_edit:
//                Intent intent = new Intent(this,AddProjectActivity.class);
//                intent.putExtra("orderId",id);
//                intent.putExtra("extraFrom",EXTRA_FROM);
//                startActivityForResult(intent,REQUEST_CODE_EDIT_ITEM);
//                break;
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
            finish();
        }else {
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
//            case REQUEST_CODE_EDIT_ITEM:
//                if (resultCode == RESULT_OK){
//                    if (data != null){
//                        //添加项目传过来的已经选择项目的信息集合
////                        selectEntity = data.getParcelableArrayListExtra("select_entity");
////                        loadData();
//                    }
//                }
//                break;
            case EDIT_PRICE_CODE:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        setLoadingStatus(LoadingStatus.LOADING);
                        loadData();

                    }
                }
                break;
        }
    }
}
