package com.shinaier.laundry.snlfactory.setting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.utils.LogUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.setting.adapter.CooperationStoreAdapter;
import com.shinaier.laundry.snlfactory.setting.entities.CooperationStoreEntity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张家洛 on 2017/10/30.
 */

public class CooperationStoreActivity extends ToolBarActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_empty_datas)
    private LinearLayout llEmptyDatas; //空数据的时候显示
    @ViewInject(R.id.rl_data_list)
    private RelativeLayout rlDataList; //有数据的时候显示
    @ViewInject(R.id.cooperation_store_list)
    private ListView cooperationStoreList;
    @ViewInject(R.id.cooperation_store_edit_bt)
    private TextView cooperationStoreEditBt;
    @ViewInject(R.id.cooperation_store_add_bt)
    private TextView cooperationStoreAddBt;

    private List<CooperationStoreEntity> cooperationStoreEntities = new ArrayList<>();
    private boolean isEditBtClick = false;
    private CooperationStoreAdapter cooperationStoreAdapter;

    private boolean isViewClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooperation_store_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("合作门店");
        cooperationStoreEditBt.setOnClickListener(this);
        cooperationStoreAddBt.setOnClickListener(this);
        CooperationStoreEntity cooperationStoreEntity1 = new CooperationStoreEntity("如家酒店","273");
        CooperationStoreEntity cooperationStoreEntity2 = new CooperationStoreEntity("王朝酒店","473");
        CooperationStoreEntity cooperationStoreEntity3 = new CooperationStoreEntity("天天特惠酒店","173");
        CooperationStoreEntity cooperationStoreEntity4 = new CooperationStoreEntity("觉得好","673");
        CooperationStoreEntity cooperationStoreEntity5 = new CooperationStoreEntity("速8酒店","293");
        cooperationStoreEntities.add(cooperationStoreEntity1);
        cooperationStoreEntities.add(cooperationStoreEntity2);
        cooperationStoreEntities.add(cooperationStoreEntity3);
        cooperationStoreEntities.add(cooperationStoreEntity4);
        cooperationStoreEntities.add(cooperationStoreEntity5);
        cooperationStoreAdapter = new CooperationStoreAdapter(this,cooperationStoreEntities);
        cooperationStoreList.setAdapter(cooperationStoreAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cooperation_store_edit_bt:
                LogUtil.e("zhang","isViewShow = " + isViewClick);
                LogUtil.e("zhang","cooperationStoreAdapter.isShow() = " + cooperationStoreAdapter.isShow());
                if (isEditBtClick){
                    cooperationStoreEditBt.setText("编辑");
                    isEditBtClick = false;
                    cooperationStoreAdapter.setIsShow(false);
                    cooperationStoreAdapter.notifyDataSetChanged();
                }else {
                    cooperationStoreEditBt.setText("删除");
                    isEditBtClick = true;
                    cooperationStoreAdapter.setIsShow(true);
                    cooperationStoreAdapter.notifyDataSetChanged();
                    if (cooperationStoreAdapter != null){
                        cooperationStoreAdapter.setEditStoreListener(new CooperationStoreAdapter.EditStoreListener() {
                            @Override
                            public void onClick(int position) {
                                if (cooperationStoreEntities.get(position).isSelect){
                                    cooperationStoreEntities.get(position).isSelect = false;
                                    isEditBtClick = true;
                                }else{
                                    cooperationStoreEntities.get(position).isSelect = true;
                                    isEditBtClick = false;
                                }
                                cooperationStoreAdapter.notifyDataSetChanged();
                            }

                        });
                    }

                }
                break;
            case R.id.cooperation_store_add_bt:
                startActivity(new Intent(this,AddCooperationStoreActivity.class));
                break;
        }
    }
}
