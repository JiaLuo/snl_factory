package com.shinaier.laundry.snlfactory.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.PreferencesUtils;
import com.common.utils.StringUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.BaseActivity;
import com.shinaier.laundry.snlfactory.base.WebViewActivity;
import com.shinaier.laundry.snlfactory.main.MainActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.UserEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.util.InputMethodUtil;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;



/**
 * Created by 张家洛 on 2017/2/8.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_LOGIN = 0x1;

    @ViewInject(R.id.login_put_num)
    private EditText loginPutNum;
    @ViewInject(R.id.login_put_password)
    private EditText loginPutPassword;
    @ViewInject(R.id.login_bt)
    private TextView loginBt;
    @ViewInject(R.id.forget_password)
    private TextView forgetPassword;
    @ViewInject(R.id.person_protocol)
    private TextView personProtocol;
    private String phoneNum;
    private String userPassword;
    private String registrationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        loginBt.setOnClickListener(this);
        personProtocol.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_bt:
                phoneNum = loginPutNum.getText().toString();
                userPassword = loginPutPassword.getText().toString();
                if(checkPhoneNumber(phoneNum)){
                    if(!TextUtils.isEmpty(userPassword)){
                        login();
                    }else {
                        ToastUtil.shortShow(this,"请输入密码");
                    }
                }
                break;
            case R.id.person_protocol:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL, "http://xiyi.wzj.dev.shuxier.com/merchant.php/Home/Index/deal");
                intent.putExtra(WebViewActivity.EXTRA_TITLE, "用户协议");
                startActivity(intent);
                break;
            case R.id.forget_password:
                startActivity(new Intent(this,FindPasswordActivity.class));
                break;
        }
    }

    private void login() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("username",phoneNum);
        params.put("password",userPassword);
        params.put("registration_id", PreferencesUtils.getString(this,"registrationID"));
        requestHttpData(Constants.Urls.URL_POST_LOGIN,REQUEST_CODE_LOGIN, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_LOGIN:
                if(data != null){
                    UserEntity userEntity = Parsers.getUserEntity(data);
                    if(userEntity.getRetcode() == 0){
                        UserCenter.setToken(this,userEntity.getData().getToken());
                        UserCenter.setUid(this,userEntity.getData().getUid());
                        UserCenter.setRole(this,userEntity.getData().getRole());
                        UserCenter.saveLoginStatus(this,true);
                        startActivity(new Intent(this, MainActivity.class));
                        finish();

                    } else if(userEntity.getRetcode() == 1){
                        ToastUtil.shortShow(this,userEntity.getStatus());
                    }
                }
                break;
        }
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
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodUtil.closeInputMethod(this);
        return super.onTouchEvent(event);
    }
}
