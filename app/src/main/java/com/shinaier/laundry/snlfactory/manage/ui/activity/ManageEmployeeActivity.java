package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.common.network.FProtocol;
import com.common.utils.StringUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.ManageEmployeeAdapter;
import com.shinaier.laundry.snlfactory.manage.view.AddEmployeeDialog;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.EmployeeEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.VerifyCodeEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by 张家洛 on 2017/2/16.
 */

public class ManageEmployeeActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_MANAGE_EMPLOYEE = 0x1;
    private static final int REQUEST_CODE_VERIFY = 0x2;
    private static final int REQUEST_CODE_ADD_EMPLOYEE = 0x3;
    private static final int REQUEST_CODE_EDIT_EMPLOYEE = 0x4;
    private static final int REQUEST_CODE_DELETE_EMPLOY = 0x5;

    @ViewInject(R.id.ll_add_employee)
    private LinearLayout llAddEmployee;
    @ViewInject(R.id.employee_list)
    private ListView employeeList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private Handler handler = new Handler(){
        private int isEdit;
        private Bundle data;
        private int code;
        private String url;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Bundle msgData = msg.getData();
                    phone = msgData.getString("phone");
                    countDownTimer = (CountDownTimer) msg.obj;
                    IdentityHashMap<String,String> codeParams = new IdentityHashMap<>();
                    codeParams.put("mobile", phone);
                    codeParams.put("token", UserCenter.getToken(ManageEmployeeActivity.this));
                    requestHttpData(Constants.Urls.URL_POST_GET_VERIFY_CODE,REQUEST_CODE_VERIFY, FProtocol.HttpMethod.POST,codeParams);
                    break;
                case 2:
                    data = msg.getData();
                    addEmployData();

                    break;
                case 3:
                    data = msg.getData();
                    isEdit = data.getInt("isEdit");
                    addEmployData();
                    break;
            }
        }

        private void addEmployData() {
            String addEmployeeName = data.getString("name");
            String addEmployeeVerifyCode = data.getString("verifyCode");
            String addEmployeePass = data.getString("password");

            Pattern pat = Pattern.compile("[\\da-zA-Z]{6,18}");
            Pattern patno = Pattern.compile(".*\\d.*");
            Pattern paten = Pattern.compile(".*[a-zA-Z].*");

            Matcher matBygonePassword = pat.matcher(addEmployeePass);
            Matcher matnoBygonePassword = patno.matcher(addEmployeePass);
            Matcher matenBygonePassword = paten.matcher(addEmployeePass);

            if(!TextUtils.isEmpty(addEmployeeName) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(addEmployeePass)){
                if(checkPhoneNumber(phone)){
                    if(!TextUtils.isEmpty(verifyCodeEntityData) && MD5(addEmployeeVerifyCode).equals(verifyCodeEntityData)) {
                        if(matnoBygonePassword.matches()&& matenBygonePassword.matches() && matBygonePassword.matches()){
                            IdentityHashMap<String,String> addEmploy = new IdentityHashMap<>();
                            addEmploy.put("token",UserCenter.getToken(ManageEmployeeActivity.this));
                            addEmploy.put("nickname",addEmployeeName);
                            addEmploy.put("username",phone);
                            addEmploy.put("password",addEmployeePass);
                            if (isEdit == 3){
                                addEmploy.put("id",editEntity.getId());
                                url = Constants.Urls.URL_POST_EDIT_EMPLOY_INFO;
                                code = REQUEST_CODE_EDIT_EMPLOYEE;
                            }else {
                                url = Constants.Urls.URL_POST_GET_VERIFY_CODE;
                                code = REQUEST_CODE_ADD_EMPLOYEE;
                            }
                            requestHttpData(url,code, FProtocol.HttpMethod.POST,addEmploy);
                        }else {
                            ToastUtil.shortShow(ManageEmployeeActivity.this,"密码请输入正确格式");
                        }
                    }else {
                        ToastUtil.shortShow(ManageEmployeeActivity.this,"验证码输入不正确");
                    }
                }
            }else {
                ToastUtil.shortShow(ManageEmployeeActivity.this,"信息不能为空");
            }
        }
    };
    private String verifyCodeEntityData;
    private CountDownTimer countDownTimer;
    private String phone;
    private AddEmployeeDialog addEmployeeDialog;
    private List<EmployeeEntity> employeeEntity;
    private EmployeeEntity editEntity;
    private AddEmployeeDialog editDialog;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_employee_act);
        ViewInjectUtils.inject(this);
        loadData();
        initView();
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
                    employeeEntity = Parsers.getEmployeeEntity(data);
                    if(employeeEntity != null){
                        setLoadingStatus(LoadingStatus.GONE);
                        ManageEmployeeAdapter manageEmployeeAdapter = new ManageEmployeeAdapter(this, employeeEntity);
                        employeeList.setAdapter(manageEmployeeAdapter);
                        manageEmployeeAdapter.setOnEditorListener(new ManageEmployeeAdapter.OnEditorListener() {
                            @Override
                            public void edit(int position) {
                                editEntity = employeeEntity.get(position);
                                editDialog = new AddEmployeeDialog(ManageEmployeeActivity.this, R.style.timerDialog,handler, editEntity);
                                editDialog.setView();
                                editDialog.show();
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
                                        params.put("id",employeeEntity.get(position).getId());
                                        requestHttpData(Constants.Urls.URL_POST_DELETE_EMPLOY_INFO,REQUEST_CODE_DELETE_EMPLOY, FProtocol.HttpMethod.POST,params);
                                    }
                                });
                                alertDialog = builder.create();
                                alertDialog.show();

                            }

                        });
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;

            case REQUEST_CODE_VERIFY:
                if(data != null){
                    VerifyCodeEntity verifyCodeEntity = Parsers.getVerifyCodeEntity(data);
                    if(verifyCodeEntity.getRetcode() == 0){
                        countDownTimer.start();
                        verifyCodeEntityData = verifyCodeEntity.getData();
                    }else {
                        ToastUtil.shortShow(this,verifyCodeEntity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_ADD_EMPLOYEE:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0 ){
                        addEmployeeDialog.dismiss();
                        setLoadingStatus(LoadingStatus.LOADING);
                        loadData();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_EDIT_EMPLOYEE:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0 ){
                        editDialog.dismiss();
                        setLoadingStatus(LoadingStatus.LOADING);
                        loadData();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
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
//                    addEmployeeDialog = new AddEmployeeDialog(this, R.style.timerDialog,handler,null);
//                    addEmployeeDialog.setView();
//                    addEmployeeDialog.show();

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

    public static String MD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(data.getBytes());
            return bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
        }
        return data;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private boolean checkPhoneNumber(String phoneNum) {
        if (StringUtil.isEmpty(phoneNum)){
            ToastUtil.shortShow(this, "请输入手机号!");
            return false;
        } else if(!"1".equals(phoneNum.trim().substring(0,1))) {
            ToastUtil.shortShow(this, "请输入正确手机号");
            return false;
        } else if(phoneNum.trim().length() != 11){
            ToastUtil.shortShow(this, "请输入正确手机号");
            return false;
        } else if(!CommonTools.checkPhoneNum(phoneNum)){
            ToastUtil.shortShow(this, "请输入正确手机号");
            return false;
        }
        return true;
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
