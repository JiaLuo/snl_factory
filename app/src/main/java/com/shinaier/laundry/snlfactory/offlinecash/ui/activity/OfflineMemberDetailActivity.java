package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberDetailEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineMemberBalanceInfoAdapter;
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
    @ViewInject(R.id.rl_member_info_sex)
    private RelativeLayout rlMemberInfoSex;
    @ViewInject(R.id.member_detail_member_birth)
    private TextView memberDetailMemberBirth;
    @ViewInject(R.id.rl_member_detail_member_discount)
    private RelativeLayout rlMemberDetailMemberDiscount;
    @ViewInject(R.id.member_detail_member_discount_info)
    private TextView memberDetailMemberDiscountInfo;


    private CommonDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_member_detail_act);
        ViewInjectUtils.inject(this);
        //从会员管理页面传递下来用户输入的会员卡或者手机号
        String memberNumber = getIntent().getStringExtra("member_number");
        initView();
        loadData(memberNumber);
    }

    private void loadData(String memberNumber) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("umobile", memberNumber);
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
    }

    @Override
    protected void parseData(int requestCode, String data) {
        LogUtil.e("zhang","data = " + data);
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_MEMBER_DETAIL:
                if(data != null){
                    OfflineMemberDetailEntity offlineMemberDetailEntity = Parsers.getOfflineMemberDetailEntity(data);
                    if(offlineMemberDetailEntity != null){
                        if(offlineMemberDetailEntity.getCode() == 0){
                            if (offlineMemberDetailEntity.getResult() != null){

                                setMemberInfo(offlineMemberDetailEntity.getResult()); //设置会员信息
                                setMemberBalanceList(offlineMemberDetailEntity.getResult().getRecord());
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineMemberDetailEntity.getMsg());
                        }
                    }
                }
                break;
        }
    }

    private void setMemberBalanceList(List<OfflineMemberDetailEntity.OfflineMemberDetailResult.OfflineMemberDetailRecord> record) {
        if (record != null && record.size() > 0){
            setLoadingStatus(LoadingStatus.GONE);
            OfflineMemberBalanceInfoAdapter offlineMemberBalanceInfoAdapter = new OfflineMemberBalanceInfoAdapter(this,record);
            offlineMemberBalanceList.setAdapter(offlineMemberBalanceInfoAdapter);
        }else {
            setLoadingStatus(LoadingStatus.EMPTY);
            OfflineMemberBalanceInfoAdapter offlineMemberBalanceInfoAdapter = new OfflineMemberBalanceInfoAdapter(this,
                    new ArrayList<OfflineMemberDetailEntity.OfflineMemberDetailResult.OfflineMemberDetailRecord>());
            offlineMemberBalanceList.setAdapter(offlineMemberBalanceInfoAdapter);
        }
    }

    //设置会员信息
    private void setMemberInfo(OfflineMemberDetailEntity.OfflineMemberDetailResult result) {

        if (result.getIsCompany().equals("0")){ //是否为企业会员:1-是，0-否
            memberDetailName.setText("姓        名：");
            rlMemberInfoSex.setVisibility(View.VISIBLE);
            rlMemberDetailMemberDiscount.setVisibility(View.VISIBLE);
            if (result.getcDiscount().equals("10.0") || result.getcDiscount().equals("0.0")){
                memberDetailMemberDiscountInfo.setText("无折扣");
            }else {
                memberDetailMemberDiscountInfo.setText(result.getcDiscount());
            }
            memberDetailMemberBirth.setText("会员生日：");
            if (result.getBirthday().equals("0")){
                memberDetailMemberBirthInfo.setText("1980-01-01");
            }else {
                memberDetailMemberBirthInfo.setText(result.getBirthday());
            }
            if (result.getSex() != null){
                if(result.getSex().equals("1")){
                    memberDetailSexInfo.setText("男");
                }else {
                    memberDetailSexInfo.setText("女");
                }
            }else {
                memberDetailSexInfo.setText("女");
            }
        }else if (result.getIsCompany().equals("1")){
            memberDetailName.setText("企业名称：");
            rlMemberInfoSex.setVisibility(View.GONE);
            rlMemberDetailMemberDiscount.setVisibility(View.GONE);
            memberDetailMemberBirth.setText("会员折扣：");
            memberDetailMemberBirthInfo.setText(result.getcDiscount());
        }else { //散客
            memberDetailName.setText("姓        名：");
            rlMemberInfoSex.setVisibility(View.VISIBLE);
            rlMemberDetailMemberDiscount.setVisibility(View.VISIBLE);
            memberDetailMemberDiscountInfo.setText("无折扣");
            memberDetailMemberBirth.setText("会员生日：");
            if (result.getBirthday().equals("0")){
                memberDetailMemberBirthInfo.setText("1980-01-01");
            }else {
                memberDetailMemberBirthInfo.setText(result.getBirthday());
            }
            if (result.getSex() != null){
                if(result.getSex().equals("1")){
                    memberDetailSexInfo.setText("男");
                }else {
                    memberDetailSexInfo.setText("女");
                }
            }else {
                memberDetailSexInfo.setText("女");
            }
        }
        memberDetailNameInfo.setText(result.getuName());

        memberDetailMemberTypeInfo.setText(result.getcName());
        memberDetailMobileInfo.setText(result.getuMobile());
        memberDetailMemberBalanceInfo.setText("￥" + result.getcBalance());

        memberDetailDealTimeInfo.setText(result.getcTime());
        if(result.getAddr() != null && !TextUtils.isEmpty(result.getAddr())){
            memberDetailMemberAddInfo.setText(result.getAddr());
        }else {
            memberDetailMemberAddInfo.setText("");
        }

        if(result.getRemark() != null && !TextUtils.isEmpty(result.getRemark())){
            memberDetailMemberRemarkInfo.setText(result.getRemark());
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
        }
    }
}
