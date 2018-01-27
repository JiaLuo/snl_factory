package com.shinaier.laundry.snlfactory.setting.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.MerchantCardInfoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/7/27.
 */

public class VipCardDetailActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_UPDATE_MERCHANT_RULE = 0x1;
    private static final int REQUEST_CODE_MERCHANT_CARD_RULE = 0x2;

    @ViewInject(R.id.gold_card_discount)
    private EditText goldCardDiscount;
    @ViewInject(R.id.gold_card_money)
    private EditText goldCardMoney;
    @ViewInject(R.id.diamond_card_discount)
    private EditText diamondCardDiscount;
    @ViewInject(R.id.diamond_card_money)
    private EditText diamondCardMoney;
    @ViewInject(R.id.gold_card_member)
    private TextView goldCardMember;
    @ViewInject(R.id.diamond_card_member)
    private TextView diamondCardMember;
    @ViewInject(R.id.vip_card_confirm)
    private TextView vipCardConfirm;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    private MerchantCardInfoEntity merchantCardInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vip_card_detail_act);
        ViewInjectUtils.inject(this);
        initView();
        loadData();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MERCHANT_CARD_RULE,REQUEST_CODE_MERCHANT_CARD_RULE, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("专店会员卡");
        vipCardConfirm.setOnClickListener(this);
        leftButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vip_card_confirm:
                String goldDiscount = goldCardDiscount.getText().toString();
                String goldMoney = goldCardMoney.getText().toString();
                String diamondDiscount = diamondCardDiscount.getText().toString();
                String diamondMoney = diamondCardMoney.getText().toString();
                if (!TextUtils.isEmpty(goldDiscount)){
                    if (!TextUtils.isEmpty(goldMoney)){
                        if (!TextUtils.isEmpty(diamondDiscount)){
                            if (!TextUtils.isEmpty(diamondMoney)){
                                merchantCardInfoEntity.getResult().get(0).setDiscount(goldDiscount);
                                merchantCardInfoEntity.getResult().get(0).setPrice(goldMoney);
                                merchantCardInfoEntity.getResult().get(1).setDiscount(diamondDiscount);
                                merchantCardInfoEntity.getResult().get(1).setPrice(diamondMoney);
                                String jsonData = getJsonData(merchantCardInfoEntity.getResult());
                                editMemberRule(jsonData);
                            }else {
                                ToastUtil.shortShow(this,"请输入钻石会员充值金额");
                            }
                        }else {
                            ToastUtil.shortShow(this,"请输入钻石会员折扣");
                        }
                    }else {
                        ToastUtil.shortShow(this,"请输入黄金会员充值金额");
                    }
                }else {
                    ToastUtil.shortShow(this,"请输入黄金会员折扣");
                }
                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_UPDATE_MERCHANT_RULE:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            finish();
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_MERCHANT_CARD_RULE:
                if (data != null){
                    merchantCardInfoEntity = Parsers.getMerchantCardInfoEntity(data);
                    if (merchantCardInfoEntity != null){
                        if (merchantCardInfoEntity.getCode() == 0){
                            if (merchantCardInfoEntity.getResult() != null && merchantCardInfoEntity.getResult().size() > 0){
                                String goldCardName = merchantCardInfoEntity.getResult().get(0).getCardName();
                                String goldDiscount = merchantCardInfoEntity.getResult().get(0).getDiscount();
                                String goldPrice = merchantCardInfoEntity.getResult().get(0).getPrice();
                                String diamondCardName = merchantCardInfoEntity.getResult().get(1).getCardName();
                                String diamondDiscount = merchantCardInfoEntity.getResult().get(1).getDiscount();
                                String diamondPrice = merchantCardInfoEntity.getResult().get(1).getPrice();
                                goldCardMember.setText(goldCardName);
                                diamondCardMember.setText(diamondCardName);
                                goldCardDiscount.setText(goldDiscount);
                                goldCardMoney.setText(goldPrice);
                                diamondCardDiscount.setText(diamondDiscount);
                                diamondCardMoney.setText(diamondPrice);
                            }
                        }else {
                            ToastUtil.shortShow(this, merchantCardInfoEntity.getMsg());
                        }
                    }
                }
                break;
        }
    }

    private String getJsonData(List<MerchantCardInfoEntity.MerchantCardInfoResult> cards){
        StringBuffer stringBuffer = new StringBuffer();
        if (cards.size() > 0){
            for (int i = 0; i < cards.size(); i++) {
                if (i == 0){
                    if (cards.size() == 1){
                        stringBuffer.append('[').append('{').append('"'+"id"+'"'+":"+'"'+cards.get(i).getId()+'"'+',')
                                .append('"'+"discount"+'"'+":"+'"'+cards.get(i).getDiscount()+'"'+',')
                                .append('"'+"price"+'"'+":"+'"'+ cards.get(i).getPrice()+'"'+'}').append("]");
                    }else{
                        stringBuffer.append('[').append('{').append('"'+"id"+'"'+":"+'"'+cards.get(i).getId()+'"'+',')
                                .append('"'+"discount"+'"'+":"+'"'+cards.get(i).getDiscount()+'"'+',')
                                .append('"'+"price"+'"'+":"+'"'+ cards.get(i).getPrice()+'"'+'}').append(",");
                    }
                }else if (i >0 && i<cards.size()-1){
                    stringBuffer.append('{').append('"'+"id"+'"'+":"+'"'+cards.get(i).getId()+'"'+',')
                            .append('"'+"discount"+'"'+":"+'"'+cards.get(i).getDiscount()+'"'+',')
                            .append('"'+"price"+'"'+":"+'"'+ cards.get(i).getPrice()+'"'+'}').append(",");
                }else{
                    stringBuffer.append('{').append('"'+"id"+'"'+":"+'"'+cards.get(i).getId()+'"'+',')
                            .append('"'+"discount"+'"'+":"+'"'+cards.get(i).getDiscount()+'"'+',')
                            .append('"'+"price"+'"'+":"+'"'+ cards.get(i).getPrice()+'"'+'}').append("]");
                }
            }
        }else {
            ToastUtil.shortShow(this,"暂时没卡");

        }
        return stringBuffer.toString();
    }

    //编辑价格
    private void editMemberRule(String jsonData){
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("cards",jsonData);
        requestHttpData(Constants.Urls.URL_POST_UPDATE_MERCHANT_RULE,REQUEST_CODE_UPDATE_MERCHANT_RULE,
                FProtocol.HttpMethod.POST,params);
    }
}
