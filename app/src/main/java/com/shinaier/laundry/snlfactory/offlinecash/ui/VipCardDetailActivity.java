package com.shinaier.laundry.snlfactory.offlinecash.ui;

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
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.StoreInfoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/7/27.
 */

public class VipCardDetailActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_UPDATE_MERCHANT_RULE = 0x1;

    @ViewInject(R.id.normal_card_money)
    private EditText normalCardMoney;
    @ViewInject(R.id.gold_card_discount)
    private EditText goldCardDiscount;
    @ViewInject(R.id.gold_card_money)
    private EditText goldCardMoney;
    @ViewInject(R.id.diamond_card_discount)
    private EditText diamondCardDiscount;
    @ViewInject(R.id.diamond_card_money)
    private EditText diamondCardMoney;
    @ViewInject(R.id.vip_card_confirm)
    private TextView vipCardConfirm;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    private ArrayList<StoreInfoEntity.StoreINfoCards> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vip_card_detail_act);
        ViewInjectUtils.inject(this);
        cards = getIntent().getParcelableArrayListExtra("cards");
        initView();
    }

    private void initView() {
        setCenterTitle("专店会员卡");
        String rawValueNormalPrice = cards.get(0).getPrice();
        String rawValueGoldDiscount = cards.get(1).getDiscount();
        String rawValueGoldPrice = cards.get(1).getPrice();
        String rawValueDiamondDiscount = cards.get(2).getDiscount();
        String rawValueDiamondPrice = cards.get(2).getPrice();
        normalCardMoney.setHint(subString(rawValueNormalPrice));
        goldCardDiscount.setHint(subString(rawValueGoldDiscount));
        goldCardMoney.setHint(subString(rawValueGoldPrice));
        diamondCardDiscount.setHint(subString(rawValueDiamondDiscount));
        diamondCardMoney.setHint(subString(rawValueDiamondPrice));
        vipCardConfirm.setOnClickListener(this);
        leftButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vip_card_confirm:
                String normalMoney = normalCardMoney.getText().toString();
                String goldDiscount = goldCardDiscount.getText().toString();
                String goldMoney = goldCardMoney.getText().toString();
                String diamondDiscount = diamondCardDiscount.getText().toString();
                String diamondMoney = diamondCardMoney.getText().toString();
                if (!TextUtils.isEmpty(normalMoney)){
                    if (!TextUtils.isEmpty(goldDiscount)){
                        if (!TextUtils.isEmpty(goldMoney)){
                            if (!TextUtils.isEmpty(diamondDiscount)){
                                if (!TextUtils.isEmpty(diamondMoney)){
                                    cards.get(0).setDiscount("10");
                                    cards.get(0).setPrice(normalMoney);
                                    cards.get(1).setDiscount(goldDiscount);
                                    cards.get(1).setPrice(goldMoney);
                                    cards.get(2).setDiscount(diamondDiscount);
                                    cards.get(2).setPrice(diamondMoney);
                                    String jsonData = getJsonData(cards);
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
                }else {
                    ToastUtil.shortShow(this,"请输入普通会员充值金额");
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
        }
    }

    private String subString(String data){
        return data.substring(0,data.indexOf("."));
    }


    private String getJsonData(ArrayList<StoreInfoEntity.StoreINfoCards> cards){
        StringBuffer stringBuffer = new StringBuffer();
        if (cards.size() > 0){
            for (int i = 0; i < cards.size(); i++) {
                if (i == 0){
                    if (cards.size() == 1){
                        stringBuffer.append('[').append('{').append('"'+"id"+'"'+":"+'"'+cards.get(i).getId()+'"'+',')
                                .append('"'+"card_name"+'"'+":"+'"'+cards.get(i).getCardName()+'"'+',')
                                .append('"'+"discount"+'"'+":"+'"'+cards.get(i).getDiscount()+'"'+',')
                                .append('"'+"price"+'"'+":"+'"'+ cards.get(i).getPrice()+'"'+'}').append("]");
                    }else{
                        stringBuffer.append('[').append('{').append('"'+"id"+'"'+":"+'"'+cards.get(i).getId()+'"'+',')
                                .append('"'+"card_name"+'"'+":"+'"'+cards.get(i).getCardName()+'"'+',')
                                .append('"'+"discount"+'"'+":"+'"'+cards.get(i).getDiscount()+'"'+',')
                                .append('"'+"price"+'"'+":"+'"'+ cards.get(i).getPrice()+'"'+'}').append(",");
                    }
                }else if (i >0 && i<cards.size()-1){
                    stringBuffer.append('{').append('"'+"id"+'"'+":"+'"'+cards.get(i).getId()+'"'+',')
                            .append('"'+"card_name"+'"'+":"+'"'+cards.get(i).getCardName()+'"'+',')
                            .append('"'+"discount"+'"'+":"+'"'+cards.get(i).getDiscount()+'"'+',')
                            .append('"'+"price"+'"'+":"+'"'+ cards.get(i).getPrice()+'"'+'}').append(",");
                }else{
                    stringBuffer.append('{').append('"'+"id"+'"'+":"+'"'+cards.get(i).getId()+'"'+',')
                            .append('"'+"card_name"+'"'+":"+'"'+cards.get(i).getCardName()+'"'+',')
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
        params.put("json_data",jsonData);
        requestHttpData(Constants.Urls.URL_POST_UPDATE_MERCHANT_RULE,REQUEST_CODE_UPDATE_MERCHANT_RULE,
                FProtocol.HttpMethod.POST,params);
    }
}
