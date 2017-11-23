package com.shinaier.laundry.snlfactory.setting.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.IdentityHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by 张家洛 on 2017/2/18.
 */

public class RevisePasswordActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_REVISE_PASSWORD = 0x1;

    @ViewInject(R.id.bygone_num)
    private EditText bygoneNum;
    @ViewInject(R.id.new_num)
    private EditText newNum;
    @ViewInject(R.id.confirm_num)
    private EditText confirmNum;
    @ViewInject(R.id.confirm_revise_bt)
    private TextView confirmReviseBt;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revise_phone_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("修改密码");
        confirmReviseBt.setOnClickListener(this);
        leftButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm_revise_bt:
                String bygonePassword = bygoneNum.getText().toString();
                String newPassword = newNum.getText().toString();
                String confirmPassword = confirmNum.getText().toString();

                Pattern pat = Pattern.compile("[\\da-zA-Z]{6,18}");
                Pattern patno = Pattern.compile(".*\\d.*");
                Pattern paten = Pattern.compile(".*[a-zA-Z].*");

                Matcher matBygonePassword = pat.matcher(bygonePassword);
                Matcher matnoBygonePassword = patno.matcher(bygonePassword);
                Matcher matenBygonePassword = paten.matcher(bygonePassword);

                Matcher matNewPassword = pat.matcher(newPassword);
                Matcher matnoNewPassword = patno.matcher(newPassword);
                Matcher matenNewPassword = paten.matcher(newPassword);

                if(!TextUtils.isEmpty(bygonePassword) && !TextUtils.isEmpty(newPassword)
                        && !TextUtils.isEmpty(confirmPassword)){
                    if(matnoBygonePassword.matches()&& matenBygonePassword.matches() && matBygonePassword.matches()){
                        if(matnoNewPassword.matches()&& matenNewPassword.matches() && matNewPassword.matches()){
                            if(newPassword.equals(confirmPassword)){
                                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                params.put("new_password",newPassword);
                                params.put("old_password",bygonePassword);
                                params.put("token", UserCenter.getToken(this));
                                params.put("uid",UserCenter.getUid(this));
                                requestHttpData(Constants.Urls.URL_POST_REVISE_PASSWORD,REQUEST_CODE_REVISE_PASSWORD, FProtocol.HttpMethod.POST,params);
                            }else {
                                ToastUtil.shortShow(this,"新密码两次输入不一致");
                            }
                        }else {
                            ToastUtil.shortShow(this,"新密码请输入正确格式密码");
                        }
                    }else {
                        ToastUtil.shortShow(this,"原密码请输入正确格式密码");
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
            case REQUEST_CODE_REVISE_PASSWORD:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 2){
                        ToastUtil.shortShow(this,entity.getStatus());
                    }else if(entity.getRetcode() == 0){
                        ToastUtil.shortShow(this,"修改密码成功");
                         finish();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
        }
    }
}
