package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.BuildOrderEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberNumEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.view.OnDateSetListener;
import com.shinaier.laundry.snlfactory.offlinecash.view.TimePickerDialog;
import com.shinaier.laundry.snlfactory.offlinecash.view.Type;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.AddProjectActivity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;


/**
 * 新增散客
 * Created by 张家洛 on 2017/8/2.
 */

public class OfflineAddVisitorActivity extends ToolBarActivity implements View.OnClickListener, OnDateSetListener {
    private static final int REQUEST_CODE_ADD_MEMBER_NUM = 0x1;
    private static final int REQUEST_CODE_ADD_MEMBER = 0x2;
    private static final int REQUEST_CODE_BUILD_ORDER = 0x3;

    @ViewInject(R.id.tv_member_num)
    private TextView tvMemberNum;
    @ViewInject(R.id.ed_input_member_name)
    private EditText edInputMemberName;
    @ViewInject(R.id.sex_man)
    private TextView sexMan;
    @ViewInject(R.id.sex_women)
    private TextView sexWomen;
    @ViewInject(R.id.tv_member_phone_num)
    private TextView tvMemberPhoneNum;
    @ViewInject(R.id.tv_member_birth)
    private TextView tvMemberBirth;
    private String phoneNum;
    @ViewInject(R.id.tv_confirm)
    private TextView tvConfirm;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private boolean isMan = false;//是否选择生日
    private boolean isWoman = false;
    private boolean isClickTime = false;//是否选择生日

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    TimePickerDialog mDialogYearMonthDay;
    private String memberBirth;
    private String ucode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_add_visitor_act);
        ViewInjectUtils.inject(this);
        phoneNum = getIntent().getStringExtra("phone_num");
//        loadMemberNum();
        initView();
    }

    private void loadMemberNum() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_ADD_MEMBER_NUM,REQUEST_CODE_ADD_MEMBER_NUM,
                FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("新增会员");
        tvMemberPhoneNum.setText(phoneNum);
        sexWomen.setSelected(true);
        tvConfirm.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        sexMan.setOnClickListener(this);
        sexWomen.setOnClickListener(this);
        tvMemberBirth.setOnClickListener(this);

        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
//                .setThemeColor(context.getResources().getColor(R.color.white))
                .setCallBack(this)
                .build();
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD_MEMBER_NUM:
                if(data != null){
                    OfflineMemberNumEntity offlineMemberNumEntity = Parsers.getOfflineMemberNumEntity(data);
                    if (offlineMemberNumEntity != null){
                        if (offlineMemberNumEntity.getRetcode() == 0){
                            if (offlineMemberNumEntity.getDatas() != null){
                                ucode = offlineMemberNumEntity.getDatas().getUcode();//获取会员卡号
                                tvMemberNum.setText(ucode);
                            }
                        }else {
                            ToastUtil.shortShow(this,offlineMemberNumEntity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ADD_MEMBER:
                if (data != null){
                    // TODO: 2017/12/27 先注释，添加项目页面完成之后 再看这块儿的逻辑
//                    OfflineAddVisitorEntity offlineAddVisitorEntity = Parsers.getOfflineAddVisitorEntity(data);
//
//                    if (offlineAddVisitorEntity != null){
//                        if (offlineAddVisitorEntity.getRetcode() == 0){
//                            if (offlineAddVisitorEntity.getDatas() != null){
//                                String user = offlineAddVisitorEntity.getDatas().getUser();
//                                IdentityHashMap<String,String> params = new IdentityHashMap<>();
//                                params.put("token", UserCenter.getToken(this));
//                                params.put("uid",user);
//                                requestHttpData(Constants.Urls.URL_POST_BUILD_ORDER,REQUEST_CODE_BUILD_ORDER, FProtocol.HttpMethod.POST,params);
//                            }
//                        }else {
//                            ToastUtil.shortShow(this,offlineAddVisitorEntity.getStatus());
//                        }
//                    }
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null) {
                        if (entity.getRetcode() == 0){
                            LogUtil.e("zhang","进入添加项目页面 ");
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_BUILD_ORDER:
                if(data != null){
                    BuildOrderEntity buildOrderEntity = Parsers.getBuildOrderEntity(data);
                    if (buildOrderEntity != null){
                        if(buildOrderEntity.getRetcode() == 0){
                            if(buildOrderEntity.getDatas() != null){
                                String orderId = buildOrderEntity.getDatas().getOrderId();
                                Intent intent = new Intent(this, AddProjectActivity.class);
                                intent.putExtra("extraFrom", MemberInfoActivity.FROM_MEMBER_INFO_ACT);
                                intent.putExtra("orderId", orderId);
                                startActivity(intent);
                                finish();
                            }
                        }else {
                            ToastUtil.shortShow(this,buildOrderEntity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.tv_confirm:
                String inputMemberName = edInputMemberName.getText().toString();
                if (!TextUtils.isEmpty(inputMemberName)){
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token",UserCenter.getToken(this));
//                    params.put("ucode",ucode);
                    params.put("uname",inputMemberName);
                    if (isMan){
                        params.put("sex","1");
                    }else if (isWoman){
                        params.put("sex","2");
                    }else {
                        params.put("sex","2");
                    }
                    params.put("umobile",phoneNum);
                    if (isClickTime){
                        params.put("birthday",memberBirth);
                    }else {
                        params.put("birthday","1980-01-01");
                    }
                    params.put("reg_from","2"); //2-android,3-IOS,4-pc;
                    requestHttpData(Constants.Urls.URL_POST_ADD_MEMBER,REQUEST_CODE_ADD_MEMBER, FProtocol.HttpMethod.POST,params);
                }else {
                    ToastUtil.shortShow(this,"请输入名字");
                }
                break;
            case R.id.sex_man:
                if (sexMan.isSelected()){
                    sexMan.setSelected(false);
                }else {
                    sexMan.setSelected(true);
                    if (sexWomen.isSelected()){
                        sexWomen.setSelected(false);
                    }
                    isMan = true;
                    isWoman = false;
                }

                break;
            case R.id.sex_women:
                if (sexWomen.isSelected()){
                    sexWomen.setSelected(false);
                }else {
                    sexWomen.setSelected(true);
                    if (sexMan.isSelected()){
                        sexMan.setSelected(false);
                    }
                    isWoman = true;
                    isMan = false;
                }
                break;
            case R.id.tv_member_birth:
                isClickTime = true;
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        memberBirth = getDateToString(millseconds);
        tvMemberBirth.setText(memberBirth);
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
