package com.shinaier.laundry.snlfactory.main.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.PreferencesUtils;
import com.common.utils.StringUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.base.activity.WebViewActivity;
import com.shinaier.laundry.snlfactory.main.MainActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.PhotoVerifyCodeEntity;
import com.shinaier.laundry.snlfactory.network.entity.UserEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.util.InputMethodUtil;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by 张家洛 on 2017/2/8.
 */

public class LoginActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_LOGIN = 0x1;
    private static final int REQUEST_CODE_PHOTO_VERIFY_CODE = 0x2;

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
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.picture_verify_code_img)
    private SimpleDraweeView pictureVerifyCodeImg;
    @ViewInject(R.id.picture_verify_code)
    private EditText pictureVerifyCode;

    private String captcha;
    private String unique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);
        ViewInjectUtils.inject(this);
        loadImgVerifyData();
        initView();
    }

    private void loadImgVerifyData() {
        //获取图片验证码
        requestHttpData(Constants.Urls.URL_GET_PHOTO_VERIFY_CODE,REQUEST_CODE_PHOTO_VERIFY_CODE);
    }

    private void initView() {
        setCenterTitle("商家登录");

        leftButton.setVisibility(View.GONE);
        loginBt.setOnClickListener(this);
        personProtocol.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        pictureVerifyCodeImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_bt:
                String phoneNum = loginPutNum.getText().toString();
                String userPassword = loginPutPassword.getText().toString();
                String photoCode = pictureVerifyCode.getText().toString();
                if(checkPhoneNumber(phoneNum)){
                    if(!TextUtils.isEmpty(userPassword)){
                        if (!TextUtils.isEmpty(photoCode)){
                            login(phoneNum,userPassword,photoCode);
                        }else {
                            ToastUtil.shortShow(this,"请输入图片验证码");
                        }
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
            case R.id.picture_verify_code_img:
                //更换图片验证码
                loadImgVerifyData();
                break;
        }
    }

    private void login(String phoneNum,String userPassword,String photoCode) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("mobile_number",phoneNum);
        params.put("passwd",userPassword);
        params.put("captcha",captcha);
        params.put("unique",unique);
        params.put("code",photoCode);
        params.put("bind_id", JPushInterface.getRegistrationID(this));
        params.put("is_factory","1");
        requestHttpData(Constants.Urls.URL_POST_LOGIN,REQUEST_CODE_LOGIN, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_PHOTO_VERIFY_CODE:
                if (data != null){
                    PhotoVerifyCodeEntity photoVerifyCodeEntity = Parsers.getPhotoVerifyCodeEntity(data);
                    if (photoVerifyCodeEntity != null){
                        if (photoVerifyCodeEntity.getCode() == 0){
                            //加密码
                            captcha = photoVerifyCodeEntity.getResult().getCaptcha();
                            //唯一码
                            unique = photoVerifyCodeEntity.getResult().getUnique();
                            //base 64之后的图片信息
                            String image = photoVerifyCodeEntity.getResult().getImage();
                            //截取后台返回的图片信息 转bitmap
                            String newImg = image.substring(image.indexOf(",") + 1,image.length() );
                            //base64图片信息转bitmap
                            Bitmap bitmap = CommonTools.stringtoBitmap(newImg);
                            pictureVerifyCodeImg.setImageBitmap(bitmap);
                        }else {
                            ToastUtil.shortShow(this,photoVerifyCodeEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_LOGIN:
                if(data != null){
                    UserEntity userEntity = Parsers.getUserEntity(data);
                    if(userEntity.getCode() == 0){
                        UserCenter.setToken(this,userEntity.getToken());
                        UserCenter.setRoot(this,userEntity.getIsRoot());
//                        UserCenter.setUid(this,userEntity.getData().getUid());
//                        UserCenter.setRole(this,userEntity.getData().getRole());
                        UserCenter.setState(this,userEntity.getOrder());
                        UserCenter.saveLoginStatus(this,true);
                        startActivity(new Intent(this, MainActivity.class));
                        finish();

                    } else {
                        ToastUtil.shortShow(this,userEntity.getMsg());
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
