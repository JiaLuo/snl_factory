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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * Created by 张家洛 on 2017/2/18.
 */

public class FeedbackActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_FEEDBACK = 0x1;

    @ViewInject(R.id.feedback_content)
    private EditText feedbackContent;
    @ViewInject(R.id.feedback_edit_max_num)
    private TextView feedbackEditMaxNum;
    @ViewInject(R.id.feedback_button)
    private TextView feedbackButton;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private Editable temp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("意见反馈");
        feedbackButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        feedbackContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                feedbackEditMaxNum.setText(charSequence.length()+ "/100");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                temp = editable;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.feedback_button:
                if(temp != null && !TextUtils.isEmpty(temp)){
                    String result = stringFilter(temp.toString());
                    loadData(result);
                }else {
                    ToastUtil.shortShow(this,"请输入内容");
                }
                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    private void loadData(String result) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("content",result);
        requestHttpData(Constants.Urls.URL_POST_FEEDBACK,REQUEST_CODE_FEEDBACK, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_FEEDBACK:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 1){
                        ToastUtil.shortShow(this,entity.getStatus());
                    }else if(entity.getRetcode() == 0){
                        ToastUtil.shortShow(this,"反馈成功");
                        finish();
                    }
                }
                break;
        }
    }

    public static String stringFilter(String str)throws PatternSyntaxException {
        String regEx = "[/\\:*?<>|\"\n\t]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
}
