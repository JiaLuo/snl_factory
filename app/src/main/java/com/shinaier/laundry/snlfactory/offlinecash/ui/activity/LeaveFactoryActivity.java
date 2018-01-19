package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.DeviceUtil;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CooperativeStoreOperateEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.LeaveFactoryEntities;
import com.shinaier.laundry.snlfactory.network.entity.OptionEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.LeaveFactoryAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CheckSpinnerView;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * 出厂列表
 * Created by 张家洛 on 2017/10/28.
 */

public class LeaveFactoryActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_LEAVE_FACTORY = 0x1;
    private static final int REQUEST_CODE_COOPERATIVE_STORE_OPERATE = 0x2;
    private static final int REQUEST_CODE_LEAVE_FACTORY_OPERATE = 0x3;

    @ViewInject(R.id.rl_choose_store)
    private RelativeLayout rlChooseStore;
    @ViewInject(R.id.choose_store_name)
    private TextView chooseStoreName;
    @ViewInject(R.id.ll_empty_datas)
    private LinearLayout llEmptyDatas;
    @ViewInject(R.id.leave_factory_clothes_list)
    private ExpandableListView leaveFactoryClothesList;
    @ViewInject(R.id.rl_all_select)
    private RelativeLayout rlAllSelect;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.tv_leave_factory)
    private TextView tvLeaveFactory;

    private CheckSpinnerView mCheckSpinnerView;
    private int orderByPosition;
    private TranslateAnimation animation;
    private List<OptionEntity> optionEntities = new ArrayList<>();
    private int width;
    private LeaveFactoryAdapter intoFactoryAdapter;
    private  boolean isClickAll = false;
    private List<LeaveFactoryEntities.IntoFactoryResult.IntoFactoryData> datas;
    private List<String> leaveFactoryIds = new ArrayList<>(); //送洗的id list
    private StringBuffer stringBuffer = new StringBuffer();
    private String selectStoreId;
    private String defaultAcceptId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_factoty_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cooperativeStoreData(); //合作店铺列表数据
        loadData(); //出厂列表数据
    }

    private void cooperativeStoreData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_COOPERATIVE_STORE_OPERATE,
                REQUEST_CODE_COOPERATIVE_STORE_OPERATE, FProtocol.HttpMethod.POST,params);
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_LEAVE_FACTORY,REQUEST_CODE_LEAVE_FACTORY,
                FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("出厂");
        rlChooseStore.setOnClickListener(this);
        rlAllSelect.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        tvLeaveFactory.setOnClickListener(this);
        rlChooseStore.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = rlChooseStore.getWidth();
            }
        });
        leaveFactoryClothesList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //默认return false 设为true的话 不让收缩
                return true;
            }
        });

        mCheckSpinnerView = new CheckSpinnerView(this, new CheckSpinnerView.OnSpinnerItemClickListener() {

            @Override
            public void onItemClickListener1(AdapterView<?> parent, View view, int position, long id) {
                orderByPosition = position;
                String selectStoreName = optionEntities.get(position).getName();
                selectStoreId = optionEntities.get(position).getId();
                chooseStoreName.setText(selectStoreName);
                mCheckSpinnerView.close();
            }
        });
        animation = new TranslateAnimation(0, 0, -(DeviceUtil.getHeight(this)), 0);
        animation.setDuration(100);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.rl_choose_store:
                mCheckSpinnerView.showSpinnerPop(rlChooseStore, animation, optionEntities, orderByPosition,width);

                break;
            case R.id.rl_all_select:
                //全选
                if (!isClickAll){
                    isClickAll = true;
                    rlAllSelect.setSelected(true);
                    for (int i = 0; i < datas.size(); i++) {
                        for (int j = 0; j < datas.get(i).getLists().size(); j++) {
                            datas.get(i).getLists().get(j).isSelect = true;
                        }
                    }

                }else{
                    isClickAll = false;
                    rlAllSelect.setSelected(false);
                    for (int i = 0; i < datas.size(); i++) {
                        for (int j = 0; j < datas.get(i).getLists().size(); j++) {
                            datas.get(i).getLists().get(j).isSelect = false;
                        }
                    }
                }

                intoFactoryAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_leave_factory:
                //打包出厂
                leaveFactoryIds.clear();
                stringBuffer.delete(0,stringBuffer.length());
                for (int i = 0; i < datas.size(); i++) {
                    for (int j = 0; j < datas.get(i).getLists().size(); j++) {
                        if (datas.get(i).getLists().get(j).isSelect){
                            String id = datas.get(i).getLists().get(j).getId();
                            leaveFactoryIds.add(id);
                        }
                    }
                }

                if (leaveFactoryIds.size() > 0){
                    for (int i = 0; i < leaveFactoryIds.size(); i++) {
                        if(i == 0){
                            if(leaveFactoryIds.size() == 1){
                                stringBuffer.append(leaveFactoryIds.get(i));
                            }else {
                                stringBuffer.append(leaveFactoryIds.get(i)).append(",");
                            }
                        }else if(i > 0 && i < leaveFactoryIds.size() -1){
                            stringBuffer.append(leaveFactoryIds.get(i)).append(",");
                        }else {
                            stringBuffer.append(leaveFactoryIds.get(i));
                        }
                    }

                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token",UserCenter.getToken(this));
                    params.put("itemids",stringBuffer.toString());
                    params.put("moduleid","21");
                    if (selectStoreId != null){
                        params.put("targetmid",selectStoreId);
                    }else {
                        params.put("targetmid",defaultAcceptId);
                    }
                    params.put("type","1");
                    requestHttpData(Constants.Urls.URL_POST_GO_BACK,
                            REQUEST_CODE_LEAVE_FACTORY_OPERATE, FProtocol.HttpMethod.POST,
                            params);
                }else {
                    ToastUtil.shortShow(this,"请选择出厂项目");
                }
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_LEAVE_FACTORY:
                if (data != null){
                    LeaveFactoryEntities leaveFactoryEntities = Parsers.getLeaveFactoryEntities(data);
                    if (leaveFactoryEntities != null){
                        if (leaveFactoryEntities.getCode() == 0){
                            LeaveFactoryEntities.IntoFactoryResult result = leaveFactoryEntities.getResult();
                            if (result != null){
                                LeaveFactoryEntities.IntoFactoryResult.IntoFactoryLegue league = result.getLeague();
                                defaultAcceptId = league.getAcceptId();

                                datas = result.getDatas();
                                if (datas != null && datas.size() > 0){
                                    llEmptyDatas.setVisibility(View.GONE);
                                    leaveFactoryClothesList.setVisibility(View.VISIBLE);
                                    //和入厂公用同一个adapter
                                    intoFactoryAdapter = new LeaveFactoryAdapter(this, datas);
                                    leaveFactoryClothesList.setAdapter(intoFactoryAdapter);
                                    for (int i = 0; i < result.getDatas().size(); i++) {
                                        leaveFactoryClothesList.expandGroup(i);
                                    }

                                    intoFactoryAdapter.setSelectListener(new LeaveFactoryAdapter.SelectListener() {
                                        @Override
                                        public void onSelect(int groupPosition, int chilePosition) {
                                            if (datas.get(groupPosition).getLists().get(chilePosition).isSelect){
                                                datas.get(groupPosition).getLists().get(chilePosition).isSelect = false;
                                            }else {
                                                datas.get(groupPosition).getLists().get(chilePosition).isSelect = true;
                                            }
                                            intoFactoryAdapter.notifyDataSetChanged();

                                        }
                                    });
                                }else {
                                    leaveFactoryClothesList.setVisibility(View.GONE);
                                    llEmptyDatas.setVisibility(View.VISIBLE);
                                }
                            }else {
                                leaveFactoryClothesList.setVisibility(View.GONE);
                                llEmptyDatas.setVisibility(View.VISIBLE);
                            }
                        }else {
                            ToastUtil.shortShow(this,leaveFactoryEntities.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_COOPERATIVE_STORE_OPERATE:
                if (data != null){
                    CooperativeStoreOperateEntities cooperativeStoreOperateEntities = Parsers.getCooperativeStoreOperateEntities(data);
                    if (cooperativeStoreOperateEntities != null){
                        if (cooperativeStoreOperateEntities.getCode() == 0){
                            List<CooperativeStoreOperateEntities.CooperativeFactoryResult> results = cooperativeStoreOperateEntities.getResults();
                            if (results != null){
                                for (int i = 0; i < results.size(); i++) {
                                    OptionEntity optionEntity = new OptionEntity();
                                    String acceptId = results.get(i).getAcceptId();
                                    String mName = results.get(i).getmName();
                                    optionEntity.setName(mName);
                                    optionEntity.setId(acceptId);
                                    optionEntities.add(optionEntity);
                                }
                                if (optionEntities != null && optionEntities.size() > 0){
                                    chooseStoreName.setText(optionEntities.get(0).getName());
                                }else {
                                    chooseStoreName.setText("暂无合作门店");
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this,cooperativeStoreOperateEntities.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_LEAVE_FACTORY_OPERATE:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            ToastUtil.shortShow(this,"操作成功");
                            loadData();
                            if (rlAllSelect.isSelected()){
                                rlAllSelect.setSelected(false);
                            }
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }
}
