package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.ManageEmployeeAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.EmployeeEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/2/16.
 */

public class ManageEmployeeActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_MANAGE_EMPLOYEE = 0x1;
    private static final int REQUEST_CODE_DELETE_EMPLOY = 0x5;
    public static final int EDIT_EMPLOYEE = 0x6;

    @ViewInject(R.id.ll_add_employee)
    private LinearLayout llAddEmployee;
    @ViewInject(R.id.employee_list)
    private ListView employeeList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private AlertDialog alertDialog;
    private List<EmployeeEntity.EmployeeResult> results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_employee_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MANAGE_EMPLOYEE,REQUEST_CODE_MANAGE_EMPLOYEE, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("员工管理");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        llAddEmployee.setOnClickListener(this);
        leftButton.setOnClickListener(this);
    }

    @Override
    protected void parseData(final int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_MANAGE_EMPLOYEE:
                if(data != null){
                    EmployeeEntity employeeEntity = Parsers.getEmployeeEntity(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if(employeeEntity != null){
                        if (employeeEntity.getCode() == 0){
                            results = employeeEntity.getResults();
                            if (results != null && results.size() > 0){
                                setLoadingStatus(LoadingStatus.GONE);
                                employeeList.setVisibility(View.VISIBLE);
                                ManageEmployeeAdapter manageEmployeeAdapter = new ManageEmployeeAdapter(this, results);
                                employeeList.setAdapter(manageEmployeeAdapter);
                                manageEmployeeAdapter.setOnEditorListener(new ManageEmployeeAdapter.OnEditorListener() {
                                    @Override
                                    public void edit(int position) {
                                        Intent intent = new Intent(ManageEmployeeActivity.this,AddEmployeeActivity.class);
                                        intent.putExtra("edit_employee",EDIT_EMPLOYEE);
                                        intent.putExtra("employee_id",results.get(position).getId());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void delete(final int position) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ManageEmployeeActivity.this);
                                        builder.setTitle("提示");
                                        builder.setMessage("确定要删除员工吗？");
                                        builder.setNegativeButton("取消", null);
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                IdentityHashMap<String,String> params = new IdentityHashMap<String, String>();
                                                params.put("token",UserCenter.getToken(ManageEmployeeActivity.this));
                                                params.put("staff_id",results.get(position).getId());
                                                requestHttpData(Constants.Urls.URL_POST_DELETE_EMPLOY_INFO,REQUEST_CODE_DELETE_EMPLOY, FProtocol.HttpMethod.POST,params);
                                            }
                                        });
                                        alertDialog = builder.create();
                                        alertDialog.show();

                                    }

                                });
                            }else {
                                ManageEmployeeAdapter manageEmployeeAdapter = new ManageEmployeeAdapter(this, new ArrayList<EmployeeEntity.EmployeeResult>());
                                employeeList.setAdapter(manageEmployeeAdapter);
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(this,employeeEntity.getMsg());
                        }

                    }else {
                        ManageEmployeeAdapter manageEmployeeAdapter = new ManageEmployeeAdapter(this, new ArrayList<EmployeeEntity.EmployeeResult>());
                        employeeList.setAdapter(manageEmployeeAdapter);
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    ManageEmployeeAdapter manageEmployeeAdapter = new ManageEmployeeAdapter(this, new ArrayList<EmployeeEntity.EmployeeResult>());
                    employeeList.setAdapter(manageEmployeeAdapter);
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;

            case REQUEST_CODE_DELETE_EMPLOY:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0 ){
                        alertDialog.dismiss();
                        loadData();
                        setLoadingStatus(LoadingStatus.LOADING);
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
            case R.id.ll_add_employee:
                //之前新增员工的逻辑
                startActivity(new Intent(this,AddEmployeeActivity.class));
                break;
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_layout:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_MANAGE_EMPLOYEE:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }

}
