package com.shinaier.laundry.snlfactory.ordermanage.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.shinaier.laundry.snlfactory.network.entity.AddProjectOfflineConfirmEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.StoreDetailEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.MemberInfoActivity;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CarAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.ViewpagerAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.entities.GoodsBean;
import com.shinaier.laundry.snlfactory.ordermanage.entities.SelectClothesEntity;
import com.shinaier.laundry.snlfactory.ordermanage.entities.TypeBean;
import com.shinaier.laundry.snlfactory.ordermanage.ui.fragment.GoodsFragment;
import com.shinaier.laundry.snlfactory.ordermanage.view.AddWidget;
import com.shinaier.laundry.snlfactory.ordermanage.view.ShopCarView;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;

import cn.lightsky.infiniteindicator.ViewUtils;


/**
 * Created by 张家洛 on 2018/1/8.
 */

public class AddProjectsActivity extends ToolBarActivity implements AddWidget.OnAddClick {
    public static final String CAR_ACTION = "handleCar";
    public static final String CLEARCAR_ACTION = "clearCar";
    private static final int REQUEST_CODE_ADD_PROJECT_SHOW = 0x1;
    private static final int REQUEST_ADD_PROJECT_CONFIRM = 0x2;
    private static final int REQUEST_CODE_ADD_PROJECT_OFFLINE_SHOW = 0x3;
    private static final int REQUEST_CODE_ADD_PROJECT_OFFLINE_CONFIRM = 0x4;

    @ViewInject(R.id.car_mainfl)
    private ShopCarView shopCarView;
    @ViewInject(R.id.car_limit)
    private TextView tv_limit;
    @ViewInject(R.id.blackview)
    private View blackView;
    @ViewInject(R.id.car_recyclerview)
    private RecyclerView carRecView;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    public BottomSheetBehavior behavior;
    public ArrayList<TypeBean> typeBean;//分类
    private ArrayList<GoodsBean> goodsBeans;
    private GoodsFragment goodsFragment;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    public static CarAdapter carAdapter;
    private List<SelectClothesEntity> selectClothesEntities = new ArrayList<>();
    private String orderId;
    private List<StoreDetailEntity.StoreDetailResult> result;
    private String userId;
    private int extraFrom;
    private String oid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_projects_act);
        ViewInjectUtils.inject(this);
        ExitManager.instance.addItemActivity(this);
        ExitManager.instance.addOfflineCollectActivity(this);

        Intent intent = getIntent();
        orderId = intent.getStringExtra("order_id");
        userId = intent.getStringExtra("user_id");
        extraFrom = intent.getIntExtra("extraFrom", 0);

        if (extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
            loadOfflineData();
        }else {
            loadData();
        }
        initViews();
        IntentFilter intentFilter = new IntentFilter(CAR_ACTION);
        intentFilter.addAction(CLEARCAR_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void loadOfflineData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("uid",userId);
        requestHttpData(Constants.Urls.URL_POST_ADD_PROJECT_OFFLINE_SHOW,
                REQUEST_CODE_ADD_PROJECT_OFFLINE_SHOW, FProtocol.HttpMethod.POST,params);
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("oid",orderId);
        requestHttpData(Constants.Urls.URL_POST_ADD_PROJECT_SHOW,
                REQUEST_CODE_ADD_PROJECT_SHOW, FProtocol.HttpMethod.POST,params);
    }

    private void initViews() {
        setCenterTitle("添加项目");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initShopCar();
    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD_PROJECT_SHOW:
                if (data != null){
                    StoreDetailEntity storeDetailEntity = Parsers.getStoreDetailEntity(data);


                    if (storeDetailEntity != null){
                        if (storeDetailEntity.getCode() == 0){
                            if (storeDetailEntity.getResult() != null){
                                result = storeDetailEntity.getResult();
//                                HashMap<String, Long> typeSelect = new HashMap<>();//更新左侧类别badge用
//                                HashMap<String,Long> foodsSelect = new HashMap<>(); //更新右侧badge用
                                goodsBeans = new ArrayList<>();
                                goodsFragment = new GoodsFragment();
                                for (int i = 0; i < result.size(); i++) {
                                    GoodsBean goodsBeanCategory = new GoodsBean();
                                    goodsBeanCategory.setTypeName(result.get(i).getCate_name());
                                    goodsBeanCategory.setTypeNameEn(result.get(i).getCateEn());
                                    goodsBeanCategory.setType(0);
                                    goodsBeans.add(goodsBeanCategory);
                                    for (int j = 0; j < result.get(i).getItems().size(); j++) {
                                        result.get(i).getItems().get(j).setType(1);
                                        result.get(i).getItems().get(j).setTypeName(result.get(i).getCate_name());
                                        goodsBeans.add(result.get(i).getItems().get(j));
                                    }
                                }



                                //用于填充遮挡部分
                                GoodsBean goodsBeanCategory = new GoodsBean();
                                goodsBeanCategory.setTypeName("填充遮挡位置");
                                goodsBeanCategory.setType(3);
                                goodsBeans.add(goodsBeanCategory);


                                goodsFragment.initData(result, goodsBeans);
                                fragmentList.add(0, goodsFragment);
                                initViewpager();
                            }
                        }else {
                            ToastUtil.shortShow(this,storeDetailEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_ADD_PROJECT_CONFIRM:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            LogUtil.e("zhang","添加成功");
                            Intent intent = new Intent(this,CraftworkAddPriceActivity.class);
                            intent.putExtra("id",orderId);
                            startActivity(intent);
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ADD_PROJECT_OFFLINE_SHOW:
                if (data != null){
                    StoreDetailEntity storeDetailEntity = Parsers.getStoreDetailEntity(data);


                    if (storeDetailEntity != null){
                        if (storeDetailEntity.getCode() == 0){
                            if (storeDetailEntity.getResult() != null){
                                result = storeDetailEntity.getResult();
//                                HashMap<String, Long> typeSelect = new HashMap<>();//更新左侧类别badge用
//                                HashMap<String,Long> foodsSelect = new HashMap<>(); //更新右侧badge用
                                goodsBeans = new ArrayList<>();
                                goodsFragment = new GoodsFragment();
                                for (int i = 0; i < result.size(); i++) {
                                    GoodsBean goodsBeanCategory = new GoodsBean();
                                    goodsBeanCategory.setTypeName(result.get(i).getCate_name());
                                    goodsBeanCategory.setTypeNameEn(result.get(i).getCateEn());
                                    goodsBeanCategory.setType(0);
                                    goodsBeans.add(goodsBeanCategory);
                                    for (int j = 0; j < result.get(i).getItems().size(); j++) {
                                        result.get(i).getItems().get(j).setType(1);
                                        result.get(i).getItems().get(j).setTypeName(result.get(i).getCate_name());
                                        goodsBeans.add(result.get(i).getItems().get(j));
                                    }
                                }



                                //用于填充遮挡部分
                                GoodsBean goodsBeanCategory = new GoodsBean();
                                goodsBeanCategory.setTypeName("填充遮挡位置");
                                goodsBeanCategory.setType(3);
                                goodsBeans.add(goodsBeanCategory);


                                goodsFragment.initData(result, goodsBeans);
                                fragmentList.add(0, goodsFragment);
                                initViewpager();
                            }
                        }else {
                            ToastUtil.shortShow(this,storeDetailEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ADD_PROJECT_OFFLINE_CONFIRM:
                if (data != null){
                    AddProjectOfflineConfirmEntity addProjectOfflineConfirmEntity = Parsers.getAddProjectOfflineConfirmEntity(data);
                    if (addProjectOfflineConfirmEntity != null){
                        if (addProjectOfflineConfirmEntity.getCode() == 0){
                            oid = addProjectOfflineConfirmEntity.getOid();
                            Intent intent = new Intent(this,CraftworkAddPriceActivity.class);
                            intent.putExtra("extra_from",extraFrom);
                            intent.putExtra("id", oid);
                            startActivity(intent);
                        }else {
                            ToastUtil.shortShow(this,addProjectOfflineConfirmEntity.getMsg());
                        }
                    }
                }
                break;
        }
    }

    private void initShopCar() {
        behavior = BottomSheetBehavior.from(findViewById(R.id.car_container));

        shopCarView.setBehavior(behavior, blackView);
//		carRecView.setNestedScrollingEnabled(false);
        carRecView.setLayoutManager(new LinearLayoutManager(this));
        ((DefaultItemAnimator) carRecView.getItemAnimator()).setSupportsChangeAnimations(false);
        carAdapter = new CarAdapter(new ArrayList<GoodsBean>(), this);
        carAdapter.bindToRecyclerView(carRecView);

        tv_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carAdapter.getItemCount() > 0){
                    selectClothesEntities.clear();
                    List<GoodsBean> goodsBeens = carAdapter.getData();
                    for (int i = 0; i < goodsBeens.size(); i++) {
                        int id = goodsBeens.get(i).getId();
                        long selectCount = goodsBeens.get(i).getSelectCount();
                        SelectClothesEntity selectClothesEntity = new SelectClothesEntity(id,selectCount);
                        selectClothesEntities.add(selectClothesEntity);
                    }

                    String s = Parsers.gson.toJson(selectClothesEntities);
                    LogUtil.e("zhang","s = " + s);

                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token", UserCenter.getToken(AddProjectsActivity.this));
                    params.put("items",s);
                    //线下入口进来
                    if (extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
                        LogUtil.e("zhang","ssssssssssssssssssssssss");
                        if (oid != null){
                            params.put("oid",oid);
                        }
                        params.put("uid",userId);
                        requestHttpData(Constants.Urls.URL_POST_ADD_PROJECT_OFFLINE_CONFIRM,
                                REQUEST_CODE_ADD_PROJECT_OFFLINE_CONFIRM,
                                FProtocol.HttpMethod.POST,params);
                    }else {
                        params.put("oid",orderId);
                        requestHttpData(Constants.Urls.URL_POST_ADD_PROJECT_CONFIRM,REQUEST_ADD_PROJECT_CONFIRM, FProtocol.HttpMethod.POST,params);
                    }
                }else {
                    ToastUtil.shortShow(AddProjectsActivity.this,"请添加项目");
                    return;
                }

            }
        });

    }

    private void initViewpager() {
        ScrollIndicatorView mSv = (ScrollIndicatorView) findViewById(R.id.indicator);
        ColorBar colorBar = new ColorBar(this, ContextCompat.getColor(this, R.color.base_color), 6, ScrollBar.Gravity.BOTTOM);
        colorBar.setWidth(ViewUtils.dip2px(this, 30));
        mSv.setScrollBar(colorBar);
        mSv.setSplitAuto(false);
        mSv.setOnTransitionListener(new OnTransitionTextListener().setColor(ContextCompat.getColor(this, R.color.base_color),
                ContextCompat.getColor(this, R.color.black)));
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mSv, mViewPager);
        ViewpagerAdapter myAdapter = new ViewpagerAdapter(this,fragmentList,getSupportFragmentManager());
        indicatorViewPager.setAdapter(myAdapter);
    }

    public void dealCar(GoodsBean foodBean) {
        HashMap<String, Long> typeSelect = new HashMap<>();//更新左侧类别badge用
        HashMap<String,Long> foodsSelect = new HashMap<>(); //更新右侧badge用


        BigDecimal amount = new BigDecimal(0.0);
        int total = 0;
        boolean hasFood = false;
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            goodsFragment.getFoodAdapter().notifyDataSetChanged();
        }
        List<GoodsBean> flist = carAdapter.getData();
        int p = -1;
        for (int i = 0; i < flist.size(); i++) {
            GoodsBean fb = flist.get(i);
            if (fb.getId() == foodBean.getId()) {
                fb = foodBean;
                hasFood = true;
                if (foodBean.getSelectCount() == 0) {
                    p = i;
                } else {
                    carAdapter.setData(i, foodBean);
                }
            }
            total += fb.getSelectCount();
            if (typeSelect.containsKey(fb.getTypeName())) {
                typeSelect.put(fb.getTypeName(), typeSelect.get(fb.getTypeName()) + fb.getSelectCount());
            } else {
                typeSelect.put(fb.getTypeName(), fb.getSelectCount());
            }

            if (foodsSelect.containsKey(fb.getItem_name())){
                foodsSelect.put(fb.getItem_name(), foodsSelect.get(fb.getItem_name()) + fb.getSelectCount());
            }else {
                foodsSelect.put(fb.getItem_name(), fb.getSelectCount());
            }

            amount = amount.add(fb.getItem_price().multiply(BigDecimal.valueOf(fb.getSelectCount())));
        }
        if (p >= 0) {
            carAdapter.remove(p);
        } else if (!hasFood && foodBean.getSelectCount() > 0) {
            carAdapter.addData(foodBean);
            if (typeSelect.containsKey(foodBean.getTypeName())) {
                typeSelect.put(foodBean.getTypeName(), typeSelect.get(foodBean.getTypeName()) + foodBean.getSelectCount());
            } else {
                typeSelect.put(foodBean.getTypeName(), foodBean.getSelectCount());
            }

            if (foodsSelect.containsKey(foodBean.getItem_name())){
                foodsSelect.put(foodBean.getItem_name(), foodsSelect.get(foodBean.getItem_name()) + foodBean.getSelectCount());
            }else {
                foodsSelect.put(foodBean.getItem_name(), foodBean.getSelectCount());
            }

            amount = amount.add(foodBean.getItem_price().multiply(BigDecimal.valueOf(foodBean.getSelectCount())));
            total += foodBean.getSelectCount();

        }
        shopCarView.showBadge(total);
        goodsFragment.getTypeAdapter().updateBadge(typeSelect);
        shopCarView.updateAmount(amount);
        goodsFragment.getFoodAdapter().updateBadge(foodsSelect);



    }

    public void clearCar(View view) {
        CommonTools.showClearCar(this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearCar();
            }
        });
    }

    private void clearCar() {
        List<GoodsBean> flist = carAdapter.getData();
        for (int i = 0; i < flist.size(); i++) {
            GoodsBean fb = flist.get(i);
            fb.setSelectCount(0);
        }
        carAdapter.setNewData(new ArrayList<GoodsBean>());
        goodsFragment.getFoodAdapter().notifyDataSetChanged();
        shopCarView.showBadge(0);
        goodsFragment.getTypeAdapter().updateBadge(new HashMap<String, Long>());
        goodsFragment.getFoodAdapter().updateBadge(new HashMap<String, Long>());
        shopCarView.updateAmount(new BigDecimal(0.0));
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CAR_ACTION:
                    //点开商品详情点击加入购物车回调
                    GoodsBean foodBean = (GoodsBean) intent.getSerializableExtra("foodbean");
                    GoodsBean fb = foodBean;
                    int p = intent.getIntExtra("position", -1);
                    if (p >= 0 && p < goodsFragment.getFoodAdapter().getItemCount()) {
                        fb = goodsFragment.getFoodAdapter().getItem(p);
                        fb.setSelectCount(foodBean.getSelectCount());
//                        goodsFragment.getFoodAdapter().setData(p, fb);
                    } else {
                        for (int i = 0; i < goodsFragment.getFoodAdapter().getItemCount(); i++) {
                            fb = goodsFragment.getFoodAdapter().getItem(i);
                            if (fb.getId() == foodBean.getId()) {
                                fb.setSelectCount(foodBean.getSelectCount());
//                                goodsFragment.getFoodAdapter().setData(i, fb);
                                break;
                            }
                        }
                    }
                    dealCar(fb);
                    break;
                case CLEARCAR_ACTION:
                    clearCar();
                    break;
            }
            if (CAR_ACTION.equals(intent.getAction())) {

            }
        }
    };

    @Override
    public void onAddClick(View view, GoodsBean fb) {
        dealCar(fb);
    }

    @Override
    public void onSubClick(GoodsBean fb) {
        dealCar(fb);
    }
}
