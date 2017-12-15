package com.shinaier.laundry.snlfactory.setting.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

/**
 * 设置商家信息
 * Created by 张家洛 on 2017/12/14.
 */

public class SetStoreInfoActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CHANGE_STORE_CONT = 0x1;

    @ViewInject(R.id.store_content)
    private EditText storeContent;
    @ViewInject(R.id.store_content_max_num)
    private TextView storeContentMaxNum;
    @ViewInject(R.id.store_content_button)
    private TextView storeContentButton;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_store_info_act);
        ViewInjectUtils.inject(this);
        String storeContent = getIntent().getStringExtra("store_content");
        initView(storeContent);
    }

    private void initView(String storeCont) {
        setCenterTitle("商家信息");
        storeContent.setText(storeCont);
        storeContentButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        storeContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                storeContentMaxNum.setText(s.length()+ "/120");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.store_content_button:
                //提交
                String storeCont = storeContent.getText().toString();
                if (!TextUtils.isEmpty(storeCont)){
                    IdentityHashMap<String,String> revisePhoneParams = new IdentityHashMap<>();
                    revisePhoneParams.put("token", UserCenter.getToken(this));
                    revisePhoneParams.put("mdesc", storeCont);
                    requestHttpData(Constants.Urls.URL_POST_STORE_INFO,REQUEST_CODE_CHANGE_STORE_CONT, FProtocol.HttpMethod.POST,revisePhoneParams);
                }else {
                    // TODO: 2017/12/12 问下这里需要做非空判断
                }
                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        switch (requestCode){
            case REQUEST_CODE_CHANGE_STORE_CONT:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            ToastUtil.shortShow(this,"修改成功");
                            finish();
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }
}
