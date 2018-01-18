package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.AddEmployeeJurisdictionAdapter;
import com.shinaier.laundry.snlfactory.manage.entities.AddEmployeeJurisdictionEntity;
import com.shinaier.laundry.snlfactory.manage.entities.EmployeeAuthorityList;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.EditEmployeeInfoEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 新增员工
 * Created by 张家洛 on 2017/10/30.
 */

public class AddEmployeeActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_GET_AUTHORITY = 0x1;
    private static final int REQUEST_CODE_ADD_EMPLOYEE = 0x2;
    private static final int REQUEST_CODE_EDIT_GET_AUTHORITY = 0x3;

    @ViewInject(R.id.gv_jurisdiction_list)
    private WrapHeightGridView gvJurisdictionList;
    @ViewInject(R.id.ed_add_employee_name)
    private EditText edAddEmployeeName;
    @ViewInject(R.id.et_input_phone_num)
    private EditText etInputPhoneNum;
    @ViewInject(R.id.et_input_pass)
    private EditText etInputPass;
    @ViewInject(R.id.commit_info)
    private TextView commitInfo;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private AddEmployeeJurisdictionAdapter adapter;
    private List<String> stringList = new ArrayList<>();
    private int editEmployee;
    private String employeeId;
    private List<EmployeeAuthorityList> employeeAuthorityLists = new ArrayList<>();
    private AddEmployeeJurisdictionAdapter addEmployeeJurisdictionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee_act);
        ViewInjectUtils.inject(this);
        editEmployee = getIntent().getIntExtra("edit_employee", 0); //员工管理页面传过来的 判断是添加还是编辑
        employeeId = getIntent().getStringExtra("employee_id"); //员工id
        initView();
        loadData();
    }

    private void loadData() {
        int code;
        String url;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        if (editEmployee == ManageEmployeeActivity.EDIT_EMPLOYEE){ // 如果是编辑就多传一个员工id
            params.put("staff_id",employeeId);
            url = Constants.Urls.URL_POST_EDIT_EMPLOYEE;
            code = REQUEST_CODE_EDIT_GET_AUTHORITY;
        }else {
            url = Constants.Urls.URL_POST_ADD_EMPLOYEE;
            code = REQUEST_CODE_GET_AUTHORITY;
        }
        requestHttpData(url,code, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("新增员工");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        commitInfo.setOnClickListener(this);
        leftButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.commit_info:
                stringList.clear();
                for (int i = 0; i < employeeAuthorityLists.size(); i++) {
                    if (employeeAuthorityLists.get(i).isSelect){
                        stringList.add(employeeAuthorityLists.get(i).getNum());
                    }
                }
                //点击确认按钮
                addEmployee();

                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    private void addEmployee() {
        int code;
        String url = "";

        String employeeName = edAddEmployeeName.getText().toString();
        String phoneNum = etInputPhoneNum.getText().toString();
        String passNum = etInputPass.getText().toString();
        Pattern pat = Pattern.compile("[\\da-zA-Z]{6,18}");
        Pattern patno = Pattern.compile(".*\\d.*");
        Pattern paten = Pattern.compile(".*[a-zA-Z].*");

        Matcher matBygonePassword = pat.matcher(passNum);
        Matcher matnoBygonePassword = patno.matcher(passNum);
        Matcher matenBygonePassword = paten.matcher(passNum);

        if (!TextUtils.isEmpty(employeeName)){ //判断用户名是否为空
            if (!TextUtils.isEmpty(phoneNum)){ //判断手机号是否为空
                if (matBygonePassword.matches() && matnoBygonePassword.matches() && matenBygonePassword.matches()){ //用正则判断 密码是否 6 - 18位 字母加数字组合
                    if (stringList.size() > 0){ //判断权限的集合里是否有值
                        IdentityHashMap<String,String> addEmploy = new IdentityHashMap<>();
                        addEmploy.put("token",UserCenter.getToken(this));
                        addEmploy.put("name",employeeName);
                        addEmploy.put("phone",phoneNum);
                        addEmploy.put("password",passNum);
                        addEmploy.put("auth",stringList.toString());
                        if (editEmployee == ManageEmployeeActivity.EDIT_EMPLOYEE){ //如果是编辑的话多传一个员工id
                            addEmploy.put("staff_id",employeeId);
                            url = Constants.Urls.URL_POST_EDIT_EMPLOYEE;
                        }else {
                            url = Constants.Urls.URL_POST_ADD_EMPLOYEE;
                        }
                        code = REQUEST_CODE_ADD_EMPLOYEE;
                        requestHttpData(url,code, FProtocol.HttpMethod.POST,addEmploy);
                    }else {
                        ToastUtil.shortShow(this,"请选择权限");
                    }
                }else {
                    ToastUtil.shortShow(this,"密码请输入正确格式密码");
                }
            }else {
                ToastUtil.shortShow(this,"请输入电话号码");
            }
        }else {
            ToastUtil.shortShow(this,"请输入名字");
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_GET_AUTHORITY: //添加员工获取所有权限
                EmployeeAuthorityList employeeAuthorityList;
                if (data != null){
                    setLoadingStatus(LoadingStatus.GONE);
                    AddEmployeeJurisdictionEntity addEmployeeJurisdictionEntity = Parsers.getAddEmployeeJurisdictionEntity(data);
                    if (addEmployeeJurisdictionEntity != null){
                        if (addEmployeeJurisdictionEntity.getCode() == 0){
                            if (addEmployeeJurisdictionEntity.getResults() != null &&
                                    addEmployeeJurisdictionEntity.getResults().size() > 0){
                                employeeAuthorityLists.clear();

                                //遍历获取下来的权限集合，装在另一个集合里，因为编辑的时候也要用，所以用同一个集合来放进adapter
                                for (int i = 0; i < addEmployeeJurisdictionEntity.getResults().size(); i++) {
                                    String module = addEmployeeJurisdictionEntity.getResults().get(i).getModule();
                                    String moduleName = addEmployeeJurisdictionEntity.getResults().get(i).getModuleName();
                                    employeeAuthorityList = new EmployeeAuthorityList();
                                    employeeAuthorityList.setName(moduleName);
                                    employeeAuthorityList.setNum(module);
                                    employeeAuthorityLists.add(employeeAuthorityList);
                                }
                                adapter = new AddEmployeeJurisdictionAdapter(this,employeeAuthorityLists);
                                gvJurisdictionList.setAdapter(adapter);
                                gvJurisdictionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        if (employeeAuthorityLists.get(i).isSelect){
                                            employeeAuthorityLists.get(i).isSelect = false;
                                        }else {
                                            employeeAuthorityLists.get(i).isSelect = true;
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(this, addEmployeeJurisdictionEntity.getMsg());
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_ADD_EMPLOYEE:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            if (editEmployee == ManageEmployeeActivity.EDIT_EMPLOYEE){
                                ToastUtil.shortShow(this,"编辑成功");
                            }else {
                                ToastUtil.shortShow(this,"添加成功");
                            }
                            finish();
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_EDIT_GET_AUTHORITY:
                if (data != null){
                    setLoadingStatus(LoadingStatus.GONE);
                    EditEmployeeInfoEntity editEmployeeInfoEntity = Parsers.getEditEmployeeInfoEntity(data);
                    if (editEmployeeInfoEntity != null){
                        if (editEmployeeInfoEntity.getCode() == 0){
                            if (editEmployeeInfoEntity.getResult() != null){
                                if (editEmployeeInfoEntity.getResult().getData() != null){
                                    edAddEmployeeName.setText(editEmployeeInfoEntity.getResult().getData().getaName());
                                    etInputPhoneNum.setText(editEmployeeInfoEntity.getResult().getData().getAccount());
                                }
                                employeeAuthorityLists.clear();
                                if (editEmployeeInfoEntity.getResult().getSelectAuthorities() != null && editEmployeeInfoEntity.getResult().getSelectAuthorities().size() > 0){

                                    //遍历集合放到同上的一个集合里
                                    for (int i = 0; i < editEmployeeInfoEntity.getResult().getSelectAuthorities().size(); i++) {
                                        String module = editEmployeeInfoEntity.getResult().getSelectAuthorities().get(i).getModule();
                                        String moduleName = editEmployeeInfoEntity.getResult().getSelectAuthorities().get(i).getModule_name();
                                        String state = editEmployeeInfoEntity.getResult().getSelectAuthorities().get(i).getState();
                                        employeeAuthorityList = new EmployeeAuthorityList();
                                        employeeAuthorityList.setName(moduleName);
                                        employeeAuthorityList.setNum(module);
                                        employeeAuthorityList.setState(state);
                                        employeeAuthorityLists.add(employeeAuthorityList);
                                    }

                                    //遍历用户之前选择的权限集合 然后进行对比，如果有相同的值就勾选上
                                    for (int i = 0; i < employeeAuthorityLists.size(); i++) {
                                        if (employeeAuthorityLists.get(i).getState().equals("1")){
                                            employeeAuthorityLists.get(i).isSelect = true;
                                        }
                                    }

                                    addEmployeeJurisdictionAdapter = new AddEmployeeJurisdictionAdapter(this,employeeAuthorityLists);
                                    gvJurisdictionList.setAdapter(addEmployeeJurisdictionAdapter);

                                    gvJurisdictionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            if (employeeAuthorityLists.get(i).isSelect){
                                                employeeAuthorityLists.get(i).isSelect = false;
                                            }else {
                                                employeeAuthorityLists.get(i).isSelect = true;
                                            }
                                            addEmployeeJurisdictionAdapter.notifyDataSetChanged();
                                        }
                                    });

                                }

                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(this,editEmployeeInfoEntity.getMsg());
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_GET_AUTHORITY:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
