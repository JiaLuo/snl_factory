package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.manage.adapter.AddEmployeeJurisdictionAdapter;
import com.shinaier.laundry.snlfactory.manage.entities.AddEmployeeJurisdictionEntity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增员工
 * Created by 张家洛 on 2017/10/30.
 */

public class AddEmployeeActivity extends ToolBarActivity {
    @ViewInject(R.id.gv_jurisdiction_list)
    private WrapHeightGridView gvJurisdictionList;

    private List<AddEmployeeJurisdictionEntity> addEmployeeJurisdictionEntities = new ArrayList<>();
    private AddEmployeeJurisdictionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("新增员工");
        AddEmployeeJurisdictionEntity addEmployeeJurisdictionEntity1 = new AddEmployeeJurisdictionEntity("订单处理");
        AddEmployeeJurisdictionEntity addEmployeeJurisdictionEntity2 = new AddEmployeeJurisdictionEntity("收件");
        AddEmployeeJurisdictionEntity addEmployeeJurisdictionEntity3 = new AddEmployeeJurisdictionEntity("送洗");
        AddEmployeeJurisdictionEntity addEmployeeJurisdictionEntity4 = new AddEmployeeJurisdictionEntity("熨烫");
        AddEmployeeJurisdictionEntity addEmployeeJurisdictionEntity5 = new AddEmployeeJurisdictionEntity("上挂");
        AddEmployeeJurisdictionEntity addEmployeeJurisdictionEntity6 = new AddEmployeeJurisdictionEntity("取衣");
        AddEmployeeJurisdictionEntity addEmployeeJurisdictionEntity7 = new AddEmployeeJurisdictionEntity("业务统计");
        AddEmployeeJurisdictionEntity addEmployeeJurisdictionEntity8 = new AddEmployeeJurisdictionEntity("会员管理");
        addEmployeeJurisdictionEntities.add(addEmployeeJurisdictionEntity1);
        addEmployeeJurisdictionEntities.add(addEmployeeJurisdictionEntity2);
        addEmployeeJurisdictionEntities.add(addEmployeeJurisdictionEntity3);
        addEmployeeJurisdictionEntities.add(addEmployeeJurisdictionEntity4);
        addEmployeeJurisdictionEntities.add(addEmployeeJurisdictionEntity5);
        addEmployeeJurisdictionEntities.add(addEmployeeJurisdictionEntity6);
        addEmployeeJurisdictionEntities.add(addEmployeeJurisdictionEntity7);
        addEmployeeJurisdictionEntities.add(addEmployeeJurisdictionEntity8);
        adapter = new AddEmployeeJurisdictionAdapter(this,addEmployeeJurisdictionEntities);
        gvJurisdictionList.setAdapter(adapter);
        gvJurisdictionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (addEmployeeJurisdictionEntities.get(i).isSelect){
                    addEmployeeJurisdictionEntities.get(i).isSelect = false;
                }else {
                    addEmployeeJurisdictionEntities.get(i).isSelect = true;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
