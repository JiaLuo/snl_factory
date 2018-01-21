package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.TakeClothesEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.SingleTakeClothesAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.TakeClothesAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.entities.SingleTakeClothesEntity;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/7/25.
 */

public class TakeClothesActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_SINGLE_TAKE_CLOTHES = 0x1;
    private static final int REQUEST_CODE_TAKE_CLOTHES_LIST = 0x2;
    public static final int FROM_TAKE_CLOTHES = 0x4;
    private static final int REQUEST_CODE_ALL_TAKE_CLOTHES = 0x5;

    @ViewInject(R.id.take_clothes_list)
    private FootLoadingListView takeClothesList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private TakeClothesAdapter takeClothesAdapter;
    private CollectClothesDialog collectClothesDialog;
    private String phoneNum;
    private CommonDialog dialog; //自定义加载dialog
    private List<SingleTakeClothesEntity> singleTakeClothesEntities = new ArrayList<>();
    private List<String> singleTakeClothesStrings = new ArrayList<>(); //送洗的id list
    private CollectClothesDialog confirmDialog;
    private CollectClothesDialog confirmTakeEnd;
    private List<TakeClothesEntity.TakeClothesResult.TakeClothesList> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_clothes_act);
        ViewInjectUtils.inject(this);
        phoneNum = getIntent().getStringExtra("phoneNum");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        takeOrderData(phoneNum);
    }

    private void takeOrderData(String number) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        params.put("number",number);
        requestHttpData(Constants.Urls.URL_POST_TAKE_CLOTHES_LIST,REQUEST_CODE_TAKE_CLOTHES_LIST, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("取衣");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
        takeClothesList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                takeOrderData(phoneNum);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });

        dialog = new CommonDialog(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_SINGLE_TAKE_CLOTHES:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if(entity.getRetcode() == 0){
                            dialog.dismiss();
                            collectClothesDialog.dismiss();
                            takeOrderData(phoneNum); //重新刷一下界面
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_TAKE_CLOTHES_LIST:
                TakeClothesEntity takeClothesEntity = Parsers.getTakeClothesEntity(data); //获取取衣列表数据
                takeClothesList.onRefreshComplete();
                setLoadingStatus(LoadingStatus.GONE);
                if(takeClothesEntity != null){
                    if(takeClothesEntity.getCode() == 0){
                        final TakeClothesEntity.TakeClothesResult result = takeClothesEntity.getResult();
                        if (result.getLists() != null && result.getLists().size() > 0){
                            lists = result.getLists();
                            takeClothesAdapter = new TakeClothesAdapter(this, lists);
                            takeClothesAdapter.setCountNum(result.getCountTotal() + "",result.getUserInfo().getuName(),result.getUserInfo().getuMobile()); // 每个订单显示排序数字
                            takeClothesList.setAdapter(takeClothesAdapter);
                            takeClothesAdapter.setTelPhoneListener(new TakeClothesAdapter.TelPhoneListener() {
                                @Override
                                public void onTelPhone(int position) {
                                    tell(result.getUserInfo().getuMobile());
                                }
                            });

                            takeClothesAdapter.setTakeClothesShowMoreListener(new TakeClothesAdapter.TakeClothesShowMoreListener() {
                                @Override
                                public void onClick(int position, ImageView iv, TextView tv) {
                                    if (lists.get(position).isOpen){
                                        iv.setBackgroundResource(R.drawable.ic_up_arrow);
                                        tv.setText("隐藏更多选项");
                                    }else{
                                        iv.setBackgroundResource(R.drawable.ic_down_arrow);
                                        tv.setText("查看更多");
                                    }
                                    takeClothesAdapter.notifyDataSetChanged();
                                }
                            });

                            takeClothesAdapter.setPositionListener(new TakeClothesAdapter.PositionListener() {

                                private SingleTakeClothesAdapter singleTakeClothesAdapter;
                                private String clothesStatus;
                                private boolean isSelect;
                                private int item;


                                @Override
                                public void onCheck(final int position) {
                                    //单件取衣
                                    item = position;
                                    if (lists.get(item).getPayState().equals("1")){
                                        singleTakeClothesEntities.clear();
                                        for (int i = 0; i < lists.get(item).getItemses().size(); i++) {
                                            clothesStatus = lists.get(item).getItemses().get(i).getStatus();
                                            if (clothesStatus.equals("91")){// 当state是91 说明上挂 可取
                                                String itemId = lists.get(item).getItemses().get(i).getId();
                                                String name = lists.get(item).getItemses().get(i).getItemName();
                                                String putSn = lists.get(item).getItemses().get(i).getPutSn();
                                                String status = lists.get(item).getItemses().get(i).getStatus();
                                                SingleTakeClothesEntity singleTakeClothesEntity  = new SingleTakeClothesEntity(itemId,"",status,putSn,name,"");
                                                singleTakeClothesEntities.add(singleTakeClothesEntity);
                                            }
                                        }
                                        if (singleTakeClothesEntities.size() > 0){
                                            View view = View.inflate(TakeClothesActivity.this, R.layout.single_take_clothes, null);
                                            ListView singleTakeClothesList = (ListView) view.findViewById(R.id.single_take_clothes_list);
                                            TextView singleTakeClothesCancel = (TextView) view.findViewById(R.id.single_take_clothes_cancel);
                                            TextView singleTakeClothesConfirm = (TextView) view.findViewById(R.id.single_take_clothes_confirm);
                                            singleTakeClothesAdapter = new SingleTakeClothesAdapter(TakeClothesActivity.this,
                                                    singleTakeClothesEntities);
                                            singleTakeClothesList.setAdapter(singleTakeClothesAdapter);
                                            singleTakeClothesAdapter.setSelectListener(new SingleTakeClothesAdapter.SelectListener() {
                                                @Override
                                                public void onSelect(int position) {

                                                    isSelect = singleTakeClothesEntities.get(position).isSelect;
                                                    if (isSelect) {
                                                        singleTakeClothesEntities.get(position).isSelect = false;
                                                    } else {
                                                        singleTakeClothesEntities.get(position).isSelect = true;
                                                    }
                                                    singleTakeClothesAdapter.notifyDataSetChanged();

                                                }
                                            });
                                            collectClothesDialog = new CollectClothesDialog(TakeClothesActivity.this, R.style.DialogTheme, view);
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
                                                    singleTakeClothesStrings.clear();
                                                    StringBuffer stringBuffer = new StringBuffer();
                                                    for (int i = 0; i < singleTakeClothesEntities.size(); i++) {
                                                        if (singleTakeClothesEntities.get(i).isSelect) {
                                                            String itemId = singleTakeClothesEntities.get(i).getItemId();
                                                            singleTakeClothesStrings.add(itemId);
                                                        }
                                                    }

                                                    if (singleTakeClothesStrings.size() > 0) {
                                                        for (int i = 0; i < singleTakeClothesStrings.size(); i++) {
                                                            if (i == 0) {
                                                                if (singleTakeClothesStrings.size() == 1) {
                                                                    stringBuffer.append(singleTakeClothesStrings.get(i));
                                                                } else {
                                                                    stringBuffer.append(singleTakeClothesStrings.get(i)).append(",");
                                                                }
                                                            } else if (i > 0 && i < singleTakeClothesStrings.size() - 1) {
                                                                stringBuffer.append(singleTakeClothesStrings.get(i)).append(",");
                                                            } else {
                                                                stringBuffer.append(singleTakeClothesStrings.get(i));
                                                            }
                                                        }
                                                    } else {
                                                        ToastUtil.shortShow(TakeClothesActivity.this, "请勾选衣服");
                                                        return;
                                                    }

                                                    dialog.setContent("加载中");
                                                    dialog.show();
                                                    IdentityHashMap<String, String> params = new IdentityHashMap<String, String>();
                                                    params.put("token", UserCenter.getToken(TakeClothesActivity.this));
                                                    params.put("moduleid","100");
                                                    params.put("itemids", stringBuffer.toString());
                                                    params.put("type","1");
                                                    LogUtil.e("zhang","stringbuffer = " + stringBuffer.toString());
                                                    requestHttpData(Constants.Urls.URL_POST_SINGLE_TAKE_CLOTHES, REQUEST_CODE_SINGLE_TAKE_CLOTHES,
                                                            FProtocol.HttpMethod.POST, params);

                                                }
                                            });
                                        }else {
                                            ToastUtil.shortShow(TakeClothesActivity.this,"没有可取衣服");
                                        }
                                    }else if (lists.get(item).getPayState().equals("0")){
                                        View view = View.inflate(TakeClothesActivity.this, R.layout.confirm_view,null);
                                        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel); //取消按钮
                                        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm); //确定按钮
                                        confirmDialog = new CollectClothesDialog(TakeClothesActivity.this, R.style.DialogTheme,view);
                                        confirmDialog.show();
                                        tvCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                confirmDialog.dismiss();
                                            }
                                        });

                                        tvConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(TakeClothesActivity.this,OrderPayActivity.class);
                                                LogUtil.e("zhang","id = " + lists.get(position).getId());
                                                intent.putExtra("order_id",lists.get(position).getId());
                                                intent.putExtra("extra_from",FROM_TAKE_CLOTHES);
                                                startActivity(intent);
                                                confirmDialog.dismiss();
                                            }
                                        });

                                    } else {
                                        ToastUtil.shortShow(TakeClothesActivity.this,"没有可取衣服");
                                    }
                                }

                                @Override
                                public void onChecked(final int position) {
                                    if(lists.get(position).getPayState().equals("0")){
                                        Intent intent = new Intent(TakeClothesActivity.this,OrderPayActivity.class);
                                        intent.putExtra("order_id",lists.get(position).getId());
                                        intent.putExtra("extra_from",FROM_TAKE_CLOTHES);
                                        startActivity(intent);

                                    }else {
                                        View view = View.inflate(TakeClothesActivity.this,R.layout.confirm_view,null);
                                        TextView tvInputNum = (TextView) view.findViewById(R.id.tv_input_num);
                                        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
                                        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
                                        tvInputNum.setText("确认取衣结单？");
                                        confirmTakeEnd = new CollectClothesDialog(TakeClothesActivity.this,
                                                R.style.DialogTheme,view);
                                        confirmTakeEnd.show();
                                        tvCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                confirmTakeEnd.dismiss();
                                            }
                                        });
                                        tvConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                IdentityHashMap<String, String> params = new IdentityHashMap<String, String>();
                                                params.put("token", UserCenter.getToken(TakeClothesActivity.this));
                                                params.put("moduleid","100");
                                                params.put("itemids", "");
                                                params.put("type","2");
                                                params.put("orderid",lists.get(item).getId());
                                                requestHttpData(Constants.Urls.URL_POST_SINGLE_TAKE_CLOTHES, REQUEST_CODE_SINGLE_TAKE_CLOTHES,
                                                        FProtocol.HttpMethod.POST, params);
                                            }
                                        });

                                    }
                                }
                            });
                        }else {
                            setLoadingStatus(LoadingStatus.EMPTY);
                        }
                    }else {
                        ToastUtil.shortShow(this,takeClothesEntity.getMsg());
                    }
                }
                break;
            case REQUEST_CODE_ALL_TAKE_CLOTHES:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            if (confirmTakeEnd != null){
                                confirmTakeEnd.dismiss();
                            }
                            takeOrderData(phoneNum); //重新刷一下界面
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                takeOrderData(phoneNum); //重新刷一下界面
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_TAKE_CLOTHES_LIST:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
