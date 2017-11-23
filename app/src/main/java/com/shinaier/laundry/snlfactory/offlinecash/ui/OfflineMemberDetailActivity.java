package com.shinaier.laundry.snlfactory.offlinecash.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberDetailEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineMemberBalanceInfoAdapter;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.util.TimeUtils;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;
import com.shinaier.laundry.snlfactory.view.WrapHeightListView;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * 线下会员详情
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberDetailActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_MEMBER_DETAIL = 0x1;

    @ViewInject(R.id.member_detail_num_info)
    private TextView memberDetailNumInfo;
    @ViewInject(R.id.member_detail_name_info)
    private TextView memberDetailNameInfo;
    @ViewInject(R.id.member_detail_sex_info)
    private TextView memberDetailSexInfo;
    @ViewInject(R.id.member_detail_member_type_info)
    private TextView memberDetailMemberTypeInfo;
    @ViewInject(R.id.member_detail_mobile_info)
    private TextView memberDetailMobileInfo;
    @ViewInject(R.id.member_detail_member_balance_info)
    private TextView memberDetailMemberBalanceInfo;
    @ViewInject(R.id.member_detail_member_birth_info)
    private TextView memberDetailMemberBirthInfo;
    @ViewInject(R.id.member_detail_deal_time_info)
    private TextView memberDetailDealTimeInfo;
    @ViewInject(R.id.member_detail_member_add_info)
    private TextView memberDetailMemberAddInfo;
    @ViewInject(R.id.member_detail_member_remark_info)
    private TextView memberDetailMemberRemarkInfo;
    @ViewInject(R.id.offline_member_balance_list)
    private WrapHeightListView offlineMemberBalanceList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.member_detail_name)
    private TextView memberDetailName;
    @ViewInject(R.id.rl_personal_sex)
    private RelativeLayout rlPersonalSex;
    @ViewInject(R.id.iv_changed_phone_num)
    private ImageView ivChangedPhoneNum;
    @ViewInject(R.id.iv_changed_discount_num)
    private ImageView ivChangedDiscountNum;
    @ViewInject(R.id.member_detail_member_birth)
    private TextView memberDetailMemberBirth;


    private CommonDialog dialog;
    private int memberType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_member_detail_act);
        ViewInjectUtils.inject(this);
        //从会员管理页面传递下来用户输入的会员卡或者手机号
        String memberNumber = getIntent().getStringExtra("member_number");
        //从会员管理页面传递下来的 判断是个人会员还是企业会员
        memberType = getIntent().getIntExtra("member_type",0);
        initView();
        loadData(memberNumber);
    }

    private void loadData(String memberNumber) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("number", memberNumber);
        requestHttpData(Constants.Urls.URL_POST_OFFLINE_MEMBER_DETAIL,REQUEST_CODE_MEMBER_DETAIL,
                FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("会员详情");
        initLoadingView(this);
        dialog = new CommonDialog(this);
        dialog.setContent("加载中");
        dialog.show();
        setLoadingStatus(LoadingStatus.LOADING);
        leftButton.setOnClickListener(this);
        ivChangedPhoneNum.setOnClickListener(this);
        ivChangedDiscountNum.setOnClickListener(this);
        if (memberType == OfflineMemberManageActivity.PERSONAL_MEMBER){
            memberDetailName.setText("姓名：");
            rlPersonalSex.setVisibility(View.VISIBLE);
            memberDetailMemberBirth.setText("会员生日：");
        }else {
            memberDetailName.setText("如家酒店：");
            rlPersonalSex.setVisibility(View.GONE);
            memberDetailMemberBirth.setText("会员折扣：");
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_MEMBER_DETAIL:
                if(data != null){
                    OfflineMemberDetailEntity offlineMemberDetailEntity = Parsers.getOfflineMemberDetailEntity(data);
                    if(offlineMemberDetailEntity != null){
                        if(offlineMemberDetailEntity.getRetcode() == 0){
                            if(offlineMemberDetailEntity.getDetailDatas() != null
                                    && offlineMemberDetailEntity.getDetailDatas().getUser() != null){
                                setMemberInfo(offlineMemberDetailEntity.getDetailDatas().getUser()); //设置会员信息
                                setMemberBalanceList(offlineMemberDetailEntity.getDetailDatas().getRecord());
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this,offlineMemberDetailEntity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    private void setMemberBalanceList(List<OfflineMemberDetailEntity.OfflineMemberDetailDatas.OfflineMemberDetailRecord> record) {
        if (record != null && record.size() > 0){
            setLoadingStatus(LoadingStatus.GONE);
            OfflineMemberBalanceInfoAdapter offlineMemberBalanceInfoAdapter = new OfflineMemberBalanceInfoAdapter(this,record);
            offlineMemberBalanceList.setAdapter(offlineMemberBalanceInfoAdapter);
        }else {
            setLoadingStatus(LoadingStatus.EMPTY);
            OfflineMemberBalanceInfoAdapter offlineMemberBalanceInfoAdapter = new OfflineMemberBalanceInfoAdapter(this,
                    new ArrayList<OfflineMemberDetailEntity.OfflineMemberDetailDatas.OfflineMemberDetailRecord>());
            offlineMemberBalanceList.setAdapter(offlineMemberBalanceInfoAdapter);
        }
    }

    //设置会员信息
    private void setMemberInfo(OfflineMemberDetailEntity.OfflineMemberDetailDatas.OfflineMemberDetailUser user) {
        memberDetailNumInfo.setText(user.getUcode());
        memberDetailNameInfo.setText(user.getUserName());
        if (user.getSex() != null){
            if(user.getSex().equals("1")){
                memberDetailSexInfo.setText("男");
            }else {
                memberDetailSexInfo.setText("女");
            }
        }else {
            memberDetailSexInfo.setText("女");
        }
        memberDetailMemberTypeInfo.setText(user.getCardName());
        memberDetailMobileInfo.setText(user.getMobileNumber());
        memberDetailMemberBalanceInfo.setText("￥" + user.getBalance());
        if (user.getBirthday().equals("0")){
            memberDetailMemberBirthInfo.setText("1980-01-01");
        }else {
            memberDetailMemberBirthInfo.setText(user.getBirthday());
        }
        memberDetailDealTimeInfo.setText(TimeUtils.formatTime(user.getRegisterTime()));
        if(user.getAddress() != null && !TextUtils.isEmpty(user.getAddress())){
            memberDetailMemberAddInfo.setText(user.getAddress());
        }else {
            memberDetailMemberAddInfo.setText("");
        }

        if(user.getRemark() != null && !TextUtils.isEmpty(user.getRemark())){
            memberDetailMemberRemarkInfo.setText(user.getRemark());
        }else {
            memberDetailMemberRemarkInfo.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.iv_changed_phone_num://修改手机号
                View changedPhone = View.inflate(this,R.layout.changed_phone_view,null);
                CollectClothesDialog editPhoneNum = new CollectClothesDialog(this, R.style.DialogTheme,changedPhone);
                editPhoneNum.show();
                break;
            case R.id.iv_changed_discount_num://修改会员折扣 个人不修改会员折扣做隐藏处理
//                View changedPhone = View.inflate(this,R.layout.changed_discount_view,null);
                break;
        }
    }
}
