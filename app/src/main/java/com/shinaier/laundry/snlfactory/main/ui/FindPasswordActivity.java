package com.shinaier.laundry.snlfactory.main.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.StringUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by 张家洛 on 2017/3/14.
 */

public class FindPasswordActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_VERIFY_CODE = 0x1;
    private static final int REQUEST_CODE_FORGET_PASSWORD = 0x2;

    @ViewInject(R.id.pass_num)
    private EditText passNum;
    @ViewInject(R.id.pass_num_again)
    private EditText passNumAgain;
    @ViewInject(R.id.input_num)
    private EditText inputNum;
    @ViewInject(R.id.ed_add_employee_verify_code)
    private EditText edAddEmployeeVerifyCode;
    @ViewInject(R.id.again_verify_code)
    private TextView againVerifyCode;
    @ViewInject(R.id.confirm_revise_bt)
    private TextView confirmReviseBt;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private CountDownTimer countDownTimer;
    private String phoneNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pass_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("找回密码");
        againVerifyCode.setOnClickListener(this);
        confirmReviseBt.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                againVerifyCode.setEnabled(false);
                againVerifyCode.setText(time + "秒");
            }
            @Override
            public void onFinish() {
                if (!TextUtils.isEmpty(inputNum.getText())){
                    againVerifyCode.setEnabled(true);
                }
                againVerifyCode.setText("重新获取");
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.again_verify_code:
                phoneNums = inputNum.getText().toString();
                if(checkPhoneNumber(phoneNums)){
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token", UserCenter.getToken(this));
                    params.put("username", phoneNums);
                    requestHttpData(Constants.Urls.URL_POST_GAIN_VERIFY_CODE,REQUEST_CODE_VERIFY_CODE, FProtocol.HttpMethod.POST,params);
                }else {
                    ToastUtil.shortShow(this,"请输入手机号");
                }
                break;
            case R.id.confirm_revise_bt:
                String passwordNum = passNum.getText().toString();
                String passwordNumAgain = passNumAgain.getText().toString();
                String verifyCode = edAddEmployeeVerifyCode.getText().toString();

                Pattern pat = Pattern.compile("[\\da-zA-Z]{6,18}");
                Pattern patno = Pattern.compile(".*\\d.*");
                Pattern paten = Pattern.compile(".*[a-zA-Z].*");

                Matcher matBygonePassword = pat.matcher(passwordNum);
                Matcher matnoBygonePassword = patno.matcher(passwordNum);
                Matcher matenBygonePassword = paten.matcher(passwordNum);

                Matcher matNewPassword = pat.matcher(passwordNumAgain);
                Matcher matnoNewPassword = patno.matcher(passwordNumAgain);
                Matcher matenNewPassword = paten.matcher(passwordNumAgain);

                if(!TextUtils.isEmpty(passwordNum) && !TextUtils.isEmpty(passwordNumAgain)){
                    if(matnoBygonePassword.matches()&& matenBygonePassword.matches() && matBygonePassword.matches()){
                        if(matnoNewPassword.matches()&& matenNewPassword.matches() && matNewPassword.matches()){
                            if(passwordNum.equals(passwordNumAgain)){
                                if(checkPhoneNumber(phoneNums)){
                                    if(!TextUtils.isEmpty(verifyCode)){
                                        IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                        params.put("username",phoneNums);
                                        params.put("password",passwordNum);
                                        params.put("token", UserCenter.getToken(this));
                                        params.put("code",verifyCode);
                                        requestHttpData(Constants.Urls.URL_POST_FORGET_PASSWORD_CONFIRM,REQUEST_CODE_FORGET_PASSWORD,
                                                FProtocol.HttpMethod.POST,params);
                                    }else {
                                        ToastUtil.shortShow(this,"验证码不能为空");
                                    }
                                }
                            }else {
                                ToastUtil.shortShow(this,"密码两次输入不一致");
                            }
                        }else {
                            ToastUtil.shortShow(this,"密码请输入正确格式密码");
                        }
                    }else {
                        ToastUtil.shortShow(this,"密码请输入正确格式密码");
                    }
                }else {
                    ToastUtil.shortShow(this,"密码不能为空");
                }
                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_VERIFY_CODE:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        countDownTimer.start();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_FORGET_PASSWORD:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        finish();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
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
}
