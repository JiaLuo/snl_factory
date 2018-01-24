package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.setting.view.CollectClothesDialog;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.util.IdentityHashMap;


/**
 * 线下会员管理
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberManageActivity extends ToolBarActivity implements View.OnClickListener {
    public static final int CONSUME = 0x1;
    public static final int RECHARGE = 0x2;
    private static final int REQUEST_CODE_ADD_MEMBER = 0x3;
    private static final int REQUEST_CODE_SEARCH_MEMBER = 0x4;
    private static final int REQUEST_CODE_MEMBER_ADD = 0x5;
    private static final int REQUEST_CODE_MEMBER_INFO_CHANGE = 0x7;
    public static final int MEMBER_INFO_CHANGE = 0x8;
    private static final int REQUEST_CODE_MEMBER_RECHARGE = 0x9;

    @ViewInject(R.id.offline_member_manage_img)
    private SimpleDraweeView offlineMemberManageImg;
    @ViewInject(R.id.offline_search_member)
    private ImageView offlineSearchMember;
    @ViewInject(R.id.offline_member_store_name)
    private TextView offlineMemberStoreName;
    @ViewInject(R.id.offline_member_consume_list)
    private TextView offlineMemberConsumeList;
    @ViewInject(R.id.offline_member_recharge_list)
    private TextView offlineMemberRechargeList;
    @ViewInject(R.id.offline_member_balance)
    private TextView offlineMemberBalance;
    @ViewInject(R.id.offline_member_add)
    private TextView offlineMemberAdd;
    @ViewInject(R.id.offline_member_consume)
    private TextView offlineMemberConsume;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.offline_member_changed)
    private TextView offlineMemberChanged;


    private CollectClothesDialog collectClothesDialog;
    private String memberNum;
    // 自定dialog
    private CommonDialog dialog;
    private int isWhichClick = 0;
    private CollectClothesDialog memberChangedDialog;
    private int searchMember; // 1 会员管理点击搜索图标
    private CollectClothesDialog addMemberView;
    private int whichMember; // 选择个人会员还是企业会员
    private String addMemberPhoneNum;
    private String inputPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_member_mamage_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        String storeLogo = intent.getStringExtra("m_logo");
        String storeName = intent.getStringExtra("m_name");
        initView(storeLogo,storeName);
    }

    private void initView(String logo, String name) {
        setCenterTitle("会员管理");
        offlineMemberManageImg.setImageURI(Uri.parse(logo)); //设置logo
        offlineMemberStoreName.setText(name); //设置店名字

        offlineSearchMember.setOnClickListener(this);
        offlineMemberConsumeList.setOnClickListener(this);
        offlineMemberRechargeList.setOnClickListener(this);
        offlineMemberBalance.setOnClickListener(this);
        offlineMemberAdd.setOnClickListener(this);
        offlineMemberConsume.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        offlineMemberChanged.setOnClickListener(this);
        dialog = new CommonDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.offline_search_member:
                View view = View.inflate(this, R.layout.collect_clothes_view,null);
                ImageView ivScan = (ImageView) view.findViewById(R.id.iv_scan);
                TextView tvInputNum = (TextView) view.findViewById(R.id.tv_input_num);
                final EditText edPhoneOrderNum = (EditText) view.findViewById(R.id.ed_phone_order_num);
                TextView revisePhoneCancel = (TextView) view.findViewById(R.id.revise_phone_cancel);
                TextView revisePhoneConfirm = (TextView) view.findViewById(R.id.revise_phone_confirm);
                ivScan.setVisibility(View.GONE);
                tvInputNum.setText("请输入会员手机号");
                edPhoneOrderNum.setHint("请输入会员手机号");
                collectClothesDialog = new CollectClothesDialog(this, R.style.DialogTheme,view);
                collectClothesDialog.show();

                revisePhoneCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        collectClothesDialog.dismiss();
                    }
                });

                revisePhoneConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchMember = 1;
                        memberNum = edPhoneOrderNum.getText().toString();
                        if(!TextUtils.isEmpty(memberNum)){
//                            if (CommonTools.checkPhoneNumber(OfflineMemberManageActivity.this,false,memberNum)){
                            //判断手机号是否存在
                                dialog.setContent("加载中");
                                dialog.show();
                                existMember(searchMember,memberNum);
//                            }
                        }else {
                            ToastUtil.shortShow(OfflineMemberManageActivity.this,"请输入手机号/会员卡号");
                        }
                    }
                });
                break;
            case R.id.offline_member_consume_list:
                //会员消费列表
                Intent consumeIntent = new Intent(this,OfflineMemberConsumeActivity.class);
                consumeIntent.putExtra("extra_from",CONSUME);
                startActivity(consumeIntent);
                break;
            case R.id.offline_member_recharge_list:
                //会员充值列表
                Intent rechargeIntent = new Intent(this,OfflineMemberConsumeActivity.class);
                rechargeIntent.putExtra("extra_from",RECHARGE);
                startActivity(rechargeIntent);
                break;
            case R.id.offline_member_balance:
                //会员余额
                startActivity(new Intent(this,OfflineMemberBalanceActivity.class));
                break;
            case R.id.offline_member_add:
                //新增会员
                View memberAddView = View.inflate(this,R.layout.member_add_view,null);
                final EditText etAddMemberPhoneNum = (EditText) memberAddView.findViewById(R.id.et_add_member_phone_num);
                final ImageView ivPersonalMember = (ImageView) memberAddView.findViewById(R.id.iv_personal_member);
                LinearLayout llPersonalMember = (LinearLayout) memberAddView.findViewById(R.id.ll_personal_member);
                final ImageView ivEnterpriseMember = (ImageView) memberAddView.findViewById(R.id.iv_enterprise_member);
                LinearLayout llEnterpriseMember = (LinearLayout) memberAddView.findViewById(R.id.ll_enterprise_member);
                TextView etAddMemberCancel = (TextView) memberAddView.findViewById(R.id.et_add_member_cancel);
                TextView etAddMemberConfirm = (TextView) memberAddView.findViewById(R.id.et_add_member_confirm);
                ivPersonalMember.setSelected(true);
                addMemberView = new CollectClothesDialog(this, R.style.DialogTheme,memberAddView);
                addMemberView.show();
                whichMember = 0;
                llPersonalMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ivPersonalMember.isSelected()){
                            ivPersonalMember.setSelected(false);
                            whichMember = 0;
                        }else {
                            ivPersonalMember.setSelected(true);
                            ivEnterpriseMember.setSelected(false);
                            whichMember = 1; //个人会员
                        }
                    }
                });

                llEnterpriseMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ivEnterpriseMember.isSelected()){
                            ivEnterpriseMember.setSelected(false);
                            whichMember = 0;
                        }else {
                            ivEnterpriseMember.setSelected(true);
                            ivPersonalMember.setSelected(false);
                            whichMember = 2; //企业会员
                        }
                    }
                });

                etAddMemberCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addMemberView.dismiss();
                    }
                });

                etAddMemberConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchMember = 2;
                        addMemberPhoneNum = etAddMemberPhoneNum.getText().toString();
                        if (CommonTools.checkPhoneNumber(OfflineMemberManageActivity.this,false, addMemberPhoneNum)){
                            if (ivPersonalMember.isSelected() || ivEnterpriseMember.isSelected()){
                                dialog.setContent("加载中");
                                dialog.show();
                                existMember(searchMember,addMemberPhoneNum);
                            }else {
                                ToastUtil.shortShow(OfflineMemberManageActivity.this,"请选择会员类型");
                            }
                        }
                    }
                });
                break;
            case R.id.offline_member_consume:
                //会员充值
                View memberRechargeView = View.inflate(this, R.layout.collect_clothes_view,null);
                ImageView memberRechargeViewScan = (ImageView) memberRechargeView.findViewById(R.id.iv_scan);
                TextView memberRechargeViewInputNum = (TextView) memberRechargeView.findViewById(R.id.tv_input_num);
                final EditText memberRechargeViewPhoneOrderNum = (EditText) memberRechargeView.findViewById(R.id.ed_phone_order_num);
                TextView memberRechargeViewCancel = (TextView) memberRechargeView.findViewById(R.id.revise_phone_cancel);
                TextView memberRechargeViewConfirm = (TextView) memberRechargeView.findViewById(R.id.revise_phone_confirm);
                memberRechargeViewScan.setVisibility(View.GONE);
                memberRechargeViewInputNum.setText("请输入会员手机号");
                memberRechargeViewPhoneOrderNum.setHint("请输入会员手机号");
                collectClothesDialog = new CollectClothesDialog(this, R.style.DialogTheme,memberRechargeView);
                collectClothesDialog.show();

                memberRechargeViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        collectClothesDialog.dismiss();
                    }
                });

                memberRechargeViewConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchMember = 4;
                        memberNum = memberRechargeViewPhoneOrderNum.getText().toString();
                        if(!TextUtils.isEmpty(memberNum)){
                            if (CommonTools.checkPhoneNumber(OfflineMemberManageActivity.this,false,memberNum)){
                                dialog.setContent("加载中");
                                dialog.show();
                                existMember(searchMember,memberNum);
                            }
                        }else {
                            ToastUtil.shortShow(OfflineMemberManageActivity.this,"请输入手机号");
                        }
                    }
                });


                //会员充值
//                startActivity(new Intent(this,OfflineMemberRechargeActivity.class));
                break;
            case R.id.left_button:
                finish();
                break;
            case R.id.offline_member_changed:
                //会员信息变更

                View memberInfoChange = View.inflate(this, R.layout.collect_clothes_view,null);
                ImageView MemberInfoChangeScan = (ImageView) memberInfoChange.findViewById(R.id.iv_scan);
                TextView MemberInfoChangeInputNum = (TextView) memberInfoChange.findViewById(R.id.tv_input_num);
                final EditText MemberInfoChangePhoneNum = (EditText) memberInfoChange.findViewById(R.id.ed_phone_order_num);
                TextView MemberInfoChangeCancel = (TextView) memberInfoChange.findViewById(R.id.revise_phone_cancel);
                TextView MemberInfoChangeConfirm = (TextView) memberInfoChange.findViewById(R.id.revise_phone_confirm);
                MemberInfoChangeScan.setVisibility(View.GONE);
                MemberInfoChangeInputNum.setText("请输入会员手机号");
                MemberInfoChangePhoneNum.setHint("请输入会员手机号");
                collectClothesDialog = new CollectClothesDialog(this, R.style.DialogTheme,memberInfoChange);
                collectClothesDialog.show();

                MemberInfoChangeCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        collectClothesDialog.dismiss();
                    }
                });

                MemberInfoChangeConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchMember = 3;
                        inputPhoneNum = MemberInfoChangePhoneNum.getText().toString();
                        if(!TextUtils.isEmpty(inputPhoneNum)){
                            if (CommonTools.checkPhoneNumber(OfflineMemberManageActivity.this,false, inputPhoneNum)){
                                dialog.setContent("加载中");
                                dialog.show();
                                existMember(searchMember, inputPhoneNum);
//                                isMember(inputPhoneNum);
                            }
                        }else {
                            ToastUtil.shortShow(OfflineMemberManageActivity.this,"请输入手机号/会员卡号");
                        }
                    }
                });
                break;
        }
    }

    /**
     * 判断手机号是否是会员
     * @param searchMember 从哪里进来的这个方法
     * @param memberNum 手机号
     */
    private void existMember(int searchMember, String memberNum) {
        int code = 0;
        String url = "";
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(OfflineMemberManageActivity.this));
        params.put("number", memberNum);
        if (searchMember == 1){  // 点击会员管理的搜索图标
            code = REQUEST_CODE_SEARCH_MEMBER;
            url = Constants.Urls.URL_POST_CUSTOM_INFO;
        }else if (searchMember == 2){ //新增会员按钮
            code = REQUEST_CODE_ADD_MEMBER;
            url = Constants.Urls.URL_POST_IS_MEMBER;
        }else if (searchMember == 3){
            code = REQUEST_CODE_MEMBER_INFO_CHANGE;
            url = Constants.Urls.URL_POST_CUSTOM_INFO;
        }else if (searchMember == 4){
            code = REQUEST_CODE_MEMBER_RECHARGE;
            url = Constants.Urls.URL_POST_CUSTOM_INFO;
        }
        requestHttpData(url,code, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case  REQUEST_CODE_MEMBER_ADD:
//                if(data != null){
//                    OfflineCustomInfoEntity offlineCustomInfoEntity = Parsers.getOfflineCustomInfoEntity(data);
//                    if(offlineCustomInfoEntity != null){
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        if(offlineCustomInfoEntity.getRetcode() == 0){
//                            if (isWhichClick == 1){
//                                Intent intent = new Intent(this,OfflineMemberDetailActivity.class);
//                                intent.putExtra("member_number",memberNum);
//                                intent.putExtra("member_type",PERSONAL_MEMBER);
//                                startActivity(intent);
//                                collectClothesDialog.dismiss();
//                            }else if (isWhichClick == 2){
//                                //有会员存在
//                                ToastUtil.shortShow(this,"此用户已是本店会员，不用重复添加");
//                            }else {
//                                Intent intent = new Intent(this,OfflineMemberRechargeActivity.class);
//                                intent.putExtra("user_id",offlineCustomInfoEntity.getDatas().getId());
//                                startActivity(intent);
//                                collectClothesDialog.dismiss();
//                            }
//                        }else if (offlineCustomInfoEntity.getRetcode() == 1){
//                            //会员不存在 新增散客
//                            if (isWhichClick == 1){
//                                ToastUtil.shortShow(this,offlineCustomInfoEntity.getStatus());
//                            } else if (isWhichClick == 2){
//                                //新增会员
//                                Intent intent = new Intent(this,OfflineMemberAddActivity.class);
//                                intent.putExtra("phone_num",memberNum);
//                                startActivity(intent);
//                                collectClothesDialog.dismiss();
//                            }else {
//                                LogUtil.e("zhang","其他的");
//                                ToastUtil.shortShow(this,"此用户不是是本店会员，请先添加会员");
//                            }
//                        }else {
//                            ToastUtil.shortShow(this,offlineCustomInfoEntity.getStatus());
//                        }
//                    }
//                }
                break;
            case REQUEST_CODE_MEMBER_INFO_CHANGE:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if (entity.getRetcode() == 0){
                        Intent intent = new Intent(this,OfflineChangeMemberInfoActivity.class);

                        intent.putExtra("member_number",inputPhoneNum);
                        startActivity(intent);
                        if (collectClothesDialog.isShowing()){
                            collectClothesDialog.dismiss();
                        }
                    }else {
                        ToastUtil.shortShow(this,"此用户不是是本店会员，请先添加会员");
                    }
                }
                break;
            case REQUEST_CODE_MEMBER_RECHARGE:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            if (collectClothesDialog != null){
                                if (collectClothesDialog.isShowing()){
                                    collectClothesDialog.dismiss();
                                }
                            }
                            Intent intent = new Intent(this,OfflineMemberRechargeActivity.class);
                            intent.putExtra("member_num",memberNum);
                            startActivity(intent);

                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ADD_MEMBER:
                Entity entity = Parsers.getEntity(data);
                if (entity != null){
                    if (entity.getRetcode() == 0){
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                        if (addMemberView != null){
                            if (addMemberView.isShowing()){
                                addMemberView.dismiss();
                            }
                        }
                        Intent intent = new Intent(this,OfflineMemberAddActivity.class);
//                                intent.putExtra("member_code",ucode);
                        intent.putExtra("member_type",whichMember);
                        intent.putExtra("phone_num",addMemberPhoneNum);
                        startActivity(intent);

                    }else if (entity.getRetcode() == 75){
                        //retcode == 75 说明会员已经存在，弹窗diss了
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                        ToastUtil.shortShow(this,"此用户已是本店会员，不用重复添加");
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
            case REQUEST_CODE_SEARCH_MEMBER:
                if(data != null){
                    Entity entity1 = Parsers.getEntity(data);

                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if (entity1 != null){
                        if (entity1.getRetcode() == 0){
                            // 说明会员存在，正常进入会员详情
                            Intent intent = new Intent(this,OfflineMemberDetailActivity.class);
                            intent.putExtra("member_number",memberNum);
                            startActivity(intent);
                            if (collectClothesDialog.isShowing()){
                                collectClothesDialog.dismiss();
                            }
                        }else {
                            ToastUtil.shortShow(this,"此用户不是是本店会员，请先添加会员");

                        }
                    }
                }
                break;
        }
    }
}
