package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.view.ChangeMemberPhoneDialog;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineChangeMemberInfoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.view.OnDateSetListener;
import com.shinaier.laundry.snlfactory.offlinecash.view.TimePickerDialog;
import com.shinaier.laundry.snlfactory.offlinecash.view.Type;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;


/**
 * 会员信息变更
 * Created by 张家洛 on 2017/12/26.
 */

public class OfflineChangeMemberInfoActivity extends ToolBarActivity implements View.OnClickListener, OnDateSetListener {
    private static final int REQUEST_CODE_CHANGE_MEMBER_INFO = 0x1;
    private static final int REQUEST_CODE_CHANGE_MEMBER_INFO_SMS = 0x2;
    private static final int REQUEST_CODE_CHANGE_MEMBER_SUBMIT = 0x3;

//    @ViewInject(R.id.change_info_member_num)
//    private TextView changeInfoMemberNum;
    @ViewInject(R.id.change_info_member_name)
    private TextView changeInfoMemberName;
    @ViewInject(R.id.change_info_input_member_name)
    private EditText changeInfoInputMemberName;
    @ViewInject(R.id.rl_change_info_member_sex)
    private RelativeLayout rlChangeInfoMemberSex;
    @ViewInject(R.id.change_info_sex_man)
    private TextView changeInfoSexMan;
    @ViewInject(R.id.change_info_sex_women)
    private TextView changeInfoSexWomen;
    @ViewInject(R.id.change_info_member_mobile)
    private EditText changeInfoMemberMobile;
    @ViewInject(R.id.rl_change_info_member_birth)
    private RelativeLayout rlChangeInfoMemberBirth;
    @ViewInject(R.id.change_info_member_birth)
    private TextView changeInfoMemberBirth;
    @ViewInject(R.id.rl_change_info_member_discount)
    private RelativeLayout rlChangeInfoMemberDiscount;
    @ViewInject(R.id.change_info_input_discount_num)
    private EditText changeInfoInputDiscountNum;
    @ViewInject(R.id.change_info_input_member_address)
    private EditText changeInfoInputMemberAddress;
    @ViewInject(R.id.change_info_input_member_mark)
    private EditText changeInfoInputMemberMark;
    @ViewInject(R.id.change_info_confirm)
    private TextView changeInfoConfirm;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private OfflineChangeMemberInfoEntity.OfflineChangeMemberInfoResult memberInfoResult;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private TimePickerDialog mDialogYearMonthDay;
    private boolean isClickTime,isMan,isWoman = false;
    private String memberBirth;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    countDownTimer = (CountDownTimer) msg.obj;

                    sendSms();
                    break;
                case 2:
                    Bundle data = msg.getData();
                    String verifyCode = data.getString("verifyCode");
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token", UserCenter.getToken(OfflineChangeMemberInfoActivity.this));
                    params.put("uid",memberInfoResult.getId());
                    params.put("uname",inputMemberName);

                    if (memberInfoResult.getIsCompany().equals("1")){
                        params.put("cdiscount",inputMemberDiscount);
                    }else {
                        if (isMan){
                            params.put("sex","1");
                        }else if (isWoman){
                            params.put("sex","2");
                        }else {
                            params.put("sex","2");
                        }
                        if (isClickTime){
                            if (memberBirth != null){
                                params.put("birthday",memberBirth);
                            }else {
                                params.put("birthday",memberInfoResult.getBirthday());
                            }
                        }else {
                            params.put("birthday","1980-01-01");
                        }
                    }
                    params.put("umobile",inputMemberMobile);
                    params.put("addr",inputMemberAddr);
                    params.put("remark",inputMemberRemark);
                    params.put("sms_code",verifyCode);
                    requestHttpData(Constants.Urls.URL_POST_CHANGE_MEMBER_SUBMIT,REQUEST_CODE_CHANGE_MEMBER_SUBMIT,
                            FProtocol.HttpMethod.POST,params);

                    break;
            }
        }
    };
    private CountDownTimer countDownTimer;
    private String inputMemberAddr;
    private String inputMemberRemark;
    private String inputMemberName;
    private String inputMemberMobile;
    private ChangeMemberPhoneDialog editPhoneNumView;
    private String inputMemberDiscount;
    private String memberNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_change_member_info_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        memberNumber = intent.getStringExtra("member_number");
        loadData(memberNumber);
        initView();
    }

    private void loadData(String number) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("number",number);
        requestHttpData(Constants.Urls.URL_POST_CHANGE_MEMBER_INFO,REQUEST_CODE_CHANGE_MEMBER_INFO, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("会员信息变更");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
        changeInfoConfirm.setOnClickListener(this);
        changeInfoMemberBirth.setOnClickListener(this);
        changeInfoSexMan.setOnClickListener(this);
        changeInfoSexWomen.setOnClickListener(this);

        //初始化时间选择器
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(this)
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.change_info_confirm:
                //确定按钮，提交数据
                //名字
                inputMemberName = changeInfoInputMemberName.getText().toString();
                //手机号
                inputMemberMobile = changeInfoMemberMobile.getText().toString();
                //地址
                inputMemberAddr = changeInfoInputMemberAddress.getText().toString();
                //备注
                inputMemberRemark = changeInfoInputMemberMark.getText().toString();
                //折扣
                inputMemberDiscount = changeInfoInputDiscountNum.getText().toString();
                if (!TextUtils.isEmpty(inputMemberName)){
                    if (!TextUtils.isEmpty(inputMemberMobile)){
                        if (memberInfoResult.getIsCompany().equals("1")){
                            if (!TextUtils.isEmpty(inputMemberDiscount)){
                                editPhoneNumView = new ChangeMemberPhoneDialog(this, R.style.DialogTheme,handler,
                                        memberInfoResult.getuMobile());
                                editPhoneNumView.setView();
                                editPhoneNumView.show();
                            }else {
                                ToastUtil.shortShow(this,"请输入折扣");
                            }
                        }else {
//                         //发送验证码
//                             sendSms();
                            editPhoneNumView = new ChangeMemberPhoneDialog(this, R.style.DialogTheme,handler,
                                    memberInfoResult.getuMobile());
                            editPhoneNumView.setView();
                            editPhoneNumView.show();
                        }
                    }else {
                        ToastUtil.shortShow(this,"请输入电话号码");
                    }

                }else {
                    ToastUtil.shortShow(this,"请输入名称");
                }
                break;
            case R.id.change_info_member_birth:
                //选择生日
                isClickTime = true;
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.change_info_sex_man:
                if (changeInfoSexMan.isSelected()){
                    changeInfoSexMan.setSelected(false);
                }else {
                    changeInfoSexMan.setSelected(true);
                    if (changeInfoSexWomen.isSelected()){
                        changeInfoSexWomen.setSelected(false);
                    }
                    isMan = true;
                    isWoman = false;
                }
                break;
            case R.id.change_info_sex_women:
                if (changeInfoSexWomen.isSelected()){
                    changeInfoSexWomen.setSelected(false);
                }else {
                    changeInfoSexWomen.setSelected(true);
                    if (changeInfoSexMan.isSelected()){
                        changeInfoSexMan.setSelected(false);
                    }
                    isWoman = true;
                    isMan = false;
                }
                break;
            case R.id.loading_layout:
                loadData(memberNumber);
                break;
        }
    }

    private void sendSms() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        params.put("uid",memberInfoResult.getId());
        requestHttpData(Constants.Urls.URL_POST_CHANGE_MEMBER_INFO_SMS,
                REQUEST_CODE_CHANGE_MEMBER_INFO_SMS, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CHANGE_MEMBER_INFO:
                if (data != null){
                    OfflineChangeMemberInfoEntity offlineChangeMemberInfoEntity = Parsers.getOfflineChangeMemberInfoEntity(data);
                    if (offlineChangeMemberInfoEntity != null){
                        if (offlineChangeMemberInfoEntity.getCode() == 0){
                            if (offlineChangeMemberInfoEntity.getResult() != null){
                                setLoadingStatus(LoadingStatus.GONE);
                                memberInfoResult = offlineChangeMemberInfoEntity.getResult();
                                String isCompany = memberInfoResult.getIsCompany();
                                if (isCompany.equals("1")){ //是否为企业会员:1-是；0-否
                                    setCompanyInfo();
                                }else {
                                    setPersonalInfo();
                                }

                            }else {
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(this,offlineChangeMemberInfoEntity.getMsg());
                        }
                    }else {
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }
                break;
            case REQUEST_CODE_CHANGE_MEMBER_INFO_SMS:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity.getRetcode() == 0){
                        countDownTimer.start();
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_CHANGE_MEMBER_SUBMIT:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            if (editPhoneNumView != null){
                                if (editPhoneNumView.isShowing()){
                                    editPhoneNumView.dismiss();
                                }
                            }
                            finish();
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    /**
     * 设置个人会员信息
     */
    private void setPersonalInfo() {
        changeInfoMemberName.setText("姓名：");
        changeInfoInputMemberName.setText(memberInfoResult.getuName());
        rlChangeInfoMemberSex.setVisibility(View.VISIBLE);
        switch (memberInfoResult.getSex()) {
            case "1":
                changeInfoSexMan.setSelected(true);
                break;
            case "2":
                changeInfoSexWomen.setSelected(true);
                break;
            default:
                changeInfoSexWomen.setSelected(true);
                break;
        }
        changeInfoMemberMobile.setText(memberInfoResult.getuMobile());
        rlChangeInfoMemberBirth.setVisibility(View.VISIBLE);
        changeInfoMemberBirth.setText(memberInfoResult.getBirthday());
        rlChangeInfoMemberDiscount.setVisibility(View.GONE);
        changeInfoInputMemberAddress.setText(memberInfoResult.getAddr());
        changeInfoInputMemberMark.setText(memberInfoResult.getRemark());
    }

    /**
     * 设置企业会员信息
     */
    private void setCompanyInfo() {
        changeInfoMemberName.setText("企业名称：");
        changeInfoInputMemberName.setText(memberInfoResult.getuName());
        rlChangeInfoMemberSex.setVisibility(View.GONE);
        changeInfoMemberMobile.setText(memberInfoResult.getuMobile());
        rlChangeInfoMemberBirth.setVisibility(View.GONE);
        rlChangeInfoMemberDiscount.setVisibility(View.VISIBLE);
        changeInfoInputDiscountNum.setText(memberInfoResult.getCdiscount());
        changeInfoInputMemberAddress.setText(memberInfoResult.getAddr());
        changeInfoInputMemberMark.setText(memberInfoResult.getRemark());

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        memberBirth = getDateToString(millseconds);
        changeInfoMemberBirth.setText(memberBirth);
    }

    //格式化时间
    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_CHANGE_MEMBER_INFO:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
