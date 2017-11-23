package com.shinaier.laundry.snlfactory.ordermanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.EditItemShowEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.ui.MemberInfoActivity;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.EditItemExpandableAdapter;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;



/**
 * Created by 张家洛 on 2017/3/16.
 */

public class AddProjectActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_EDIT_ITEM_SHOW = 0x3;
    private static final int REQUEST_CODE_SUBMIT_EDIT_ITEM = 0x5;

    @ViewInject(R.id.color_confirm)
    private TextView colorConfirm;
    @ViewInject(R.id.clothes_item_list)
    private ExpandableListView clothesItemList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private String orderId;
    private List<EditItemShowEntities.Item> item = new ArrayList<>();
    private int extraFrom;
    private List<EditItemShowEntities> editItemShowEntities;
    private EditItemExpandableAdapter editItemExpandableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_project_act);
        ViewInjectUtils.inject(this);
        ExitManager.instance.addItemActivity(this);
        ExitManager.instance.addOfflineCollectActivity(this);
        ExitManager.instance.editOfflineCollectActivity(this);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        extraFrom = intent.getIntExtra("extraFrom", 0);
        LogUtil.e("zhang","AddProjectActivity extraFrom = " + extraFrom);
        iniView();
        if(extraFrom == CraftworkAddPriceActivity.EXTRA_FROM){
            colorConfirm.setText("确定");
        }
        loadData();
    }

    private void iniView() {
        setCenterTitle("添加项目");
        clothesItemList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        colorConfirm.setOnClickListener(this);
        leftButton.setOnClickListener(this);
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("orderid",orderId);
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_EDIT_ITEM_SHOW,REQUEST_CODE_EDIT_ITEM_SHOW, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_EDIT_ITEM_SHOW:
                if(data != null){
                    editItemShowEntities = Parsers.getEditItemShowEntities(data);
                    if(editItemShowEntities != null && editItemShowEntities.size() > 0){
                        for (int i = 0; i < editItemShowEntities.size(); i++) {
                            List<EditItemShowEntities.Item> editItems = editItemShowEntities.get(i).getItems();
                            for (int j = 0; j < editItems.size(); j++) {
                                editItems.get(j).extraFrom = true;
                            }
                        }

                        editItemExpandableAdapter = new EditItemExpandableAdapter(
                                editItemShowEntities,this);
                        clothesItemList.setAdapter(editItemExpandableAdapter);
                        for (int i = 0; i < editItemShowEntities.size(); i++) {
                            clothesItemList.expandGroup(i);
                        }

                        editItemExpandableAdapter.setOnAddReduceClickListener(new EditItemExpandableAdapter.OnAddReduceClickListener() {
                            @Override
                            public void onAdd(int groupPosition, int childPosition) {
                                int num = editItemShowEntities.get(groupPosition).getItems().get(childPosition).getNum();
                                num += 1;
                                editItemShowEntities.get(groupPosition).getItems().get(childPosition).setNum(num);
                                editItemExpandableAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onReduce(int groupPosition, int childPosition) {
                                int num = editItemShowEntities.get(groupPosition).getItems().get(childPosition).getNum();
                                num -= 1;
                                if(num >= 0){
                                    editItemShowEntities.get(groupPosition).getItems().get(childPosition).setNum(num);
                                    editItemExpandableAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onSelect(int groupPosition, int childPosition) {
                                editItemShowEntities.get(groupPosition).getItems().get(childPosition).extraFrom = false;
                                if (editItemShowEntities.get(groupPosition).getItems().get(childPosition).isSelect){
                                    editItemShowEntities.get(groupPosition).getItems().get(childPosition).isSelect = false;
                                    if (editItemShowEntities.get(groupPosition).getItems().get(childPosition).getNum() > 0){
                                        editItemShowEntities.get(groupPosition).getItems().get(childPosition).setNum(0);
                                    }
                                }else{
                                    editItemShowEntities.get(groupPosition).getItems().get(childPosition).isSelect = true;
                                    if (editItemShowEntities.get(groupPosition).getItems().get(childPosition).getNum() == 0){
                                        editItemShowEntities.get(groupPosition).getItems().get(childPosition).setNum(1);
                                    }
                                }
                                editItemExpandableAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                break;
            case REQUEST_CODE_SUBMIT_EDIT_ITEM:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        Intent intent = new Intent(this,CraftworkAddPriceActivity.class);
                        intent.putExtra("id",orderId);
                        if (extraFrom == CraftworkAddPriceActivity.EXTRA_FROM){
                            LogUtil.e("zhang","zhang");
                            setResult(RESULT_OK,intent);
                            finish();
                        }else {
                            LogUtil.e("zhang","zhaohui");
                            intent.putExtra("extra_from",extraFrom);
                            startActivity(intent);
                        }
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
            case R.id.color_confirm:
                    item.clear();
                    StringBuffer stringBuffer = new StringBuffer();
                    if (editItemShowEntities != null) {
                        for (int i = 0; i < editItemShowEntities.size(); i++) {
                            for (int j = 0; j < editItemShowEntities.get(i).getItems().size(); j++) {
                                if(editItemShowEntities.get(i).getItems().get(j).getNum() != 0 ||
                                        editItemShowEntities.get(i).getItems().get(j).isSelect &&
                                        editItemShowEntities.get(i).getItems().get(j).getStateType() != 1){
                                    item.add(editItemShowEntities.get(i).getItems().get(j));
                                }
                            }
                        }
                        if (item.size() > 0){
                            for (int i = 0; i < item.size(); i++) {
                                if(item.get(i).getNum() == 0){
                                    ToastUtil.shortShow(this,"请选择服务项目");
                                    return;
                                }
                                if (i == 0){
                                    if (item.size() == 1){
                                        stringBuffer.append('[').append('{').append('"'+"orderid"+'"'+":"+'"'+orderId+'"'+',')
                                                .append('"'+"type"+'"'+":"+'"'+item.get(i).getId()+'"'+',')
                                                .append('"'+"price"+'"'+":"+'"'+item.get(i).getPrice()+'"'+',')
                                                .append('"'+"itemcount"+'"'+":"+'"'+ item.get(i).getNum()+'"'+'}').append("]");
                                    }else{
                                        stringBuffer.append('[').append('{').append('"'+"orderid"+'"'+":"+'"'+orderId+'"'+',')
                                                .append('"'+"type"+'"'+":"+'"'+item.get(i).getId()+'"'+',')
                                                .append('"'+"price"+'"'+":"+'"'+item.get(i).getPrice()+'"'+',')
                                                .append('"'+"itemcount"+'"'+":"+'"'+ item.get(i).getNum()+'"'+'}').append(",");
                                    }
                                }else if (i >0 && i<item.size()-1){
                                    stringBuffer.append('{').append('"'+"orderid"+'"'+":"+'"'+orderId+'"'+',')
                                            .append('"'+"type"+'"'+":"+'"'+item.get(i).getId()+'"'+',')
                                            .append('"'+"price"+'"'+":"+'"'+item.get(i).getPrice()+'"'+',')
                                            .append('"'+"itemcount"+'"'+":"+'"'+ item.get(i).getNum()+'"'+'}').append(",");
                                }else{
                                    stringBuffer.append('{').append('"'+"orderid"+'"'+":"+'"'+orderId+'"'+',')
                                            .append('"'+"type"+'"'+":"+'"'+item.get(i).getId()+'"'+',')
                                            .append('"'+"price"+'"'+":"+'"'+item.get(i).getPrice()+'"'+',')
                                            .append('"'+"itemcount"+'"'+":"+'"'+ item.get(i).getNum()+'"'+'}').append("]");
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this,"请选择服务项目");
                            return;
                        }
                        IdentityHashMap<String,String> params = new IdentityHashMap<>();
                        params.put("token",UserCenter.getToken(this));
                        params.put("id",orderId);
                        params.put("val",stringBuffer.toString());
                        if (extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
                            LogUtil.e("zhang","1111111111111111");
                            requestHttpData(Constants.Urls.URL_POST_OFFLINE_EDIT_ITEM_SHOW,REQUEST_CODE_SUBMIT_EDIT_ITEM, FProtocol.HttpMethod.POST,params);
                        }else {
                            requestHttpData(Constants.Urls.URL_POST_EDIT_ITEM_SHOW,REQUEST_CODE_SUBMIT_EDIT_ITEM, FProtocol.HttpMethod.POST,params);
                            LogUtil.e("zhang","2222222222222222");
                        }
                    }
                break;
            case R.id.left_button:
                if (extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
                    LogUtil.e("zhang","adjfkdjksjflkjdskljfklds");
                    finish();
                }else {
                    LogUtil.e("zhang","122222333333334444444441");
                    ExitManager.instance.exitEditOfflineCollectActivity();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
            finish();
        }else {
            ExitManager.instance.exitEditOfflineCollectActivity();
        }
    }

}
