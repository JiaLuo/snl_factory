package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OfflineCustomInfoEntity;
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
    public static final int PERSONAL_MEMBER = 0x3;
    private static final int REQUEST_CODE_MEMBER_ADD = 0x5;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_member_mamage_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        String storeLogo = intent.getStringExtra("store_logo");
        String storeName = intent.getStringExtra("store_name");
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
                tvInputNum.setText("请输入会员手机号/会员卡号");
                edPhoneOrderNum.setHint("请输入会员手机号/会员卡号");
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
                        isWhichClick = 1;
                        memberNum = edPhoneOrderNum.getText().toString();
                        if(!TextUtils.isEmpty(memberNum)){
//                            if (CommonTools.checkPhoneNumber(OfflineMemberManageActivity.this,false,memberNum)){
                                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                params.put("token", UserCenter.getToken(OfflineMemberManageActivity.this));
                                params.put("number",memberNum);
                                requestHttpData(Constants.Urls.URL_POST_CUSTOM_INFO,REQUEST_CODE_MEMBER_ADD, FProtocol.HttpMethod.POST,params);
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
                View memberChangeView = View.inflate(this, R.layout.member_info_changed_view,null);
                final TextView tvPersonalMember = memberChangeView.findViewById(R.id.tv_personal_member);
                TextView tvEnterpriseMember = memberChangeView.findViewById(R.id.tv_enterprise_member);
                memberChangedDialog = new CollectClothesDialog(this, R.style.DialogTheme,memberChangeView);
                memberChangedDialog.show();
                tvPersonalMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       if (tvPersonalMember.isSelected()){
                           tvPersonalMember.setSelected(false);
                           tvPersonalMember.setTextColor(OfflineMemberManageActivity.this.getResources().getColor(R.color.black_text));
                       }else {
                           tvPersonalMember.setSelected(true);
                           tvPersonalMember.setTextColor(Color.parseColor("#fe433a"));
//                           startActivity(new Intent(OfflineMemberManageActivity.this,OfflineMemberAddActivity.class));
                           //新增会员
                           Intent intent = new Intent(OfflineMemberManageActivity.this,OfflineMemberAddActivity.class);
                           intent.putExtra("phone_num",memberNum);
                           startActivity(intent);
                           memberChangedDialog.dismiss();
                       }
                    }
                });

                tvEnterpriseMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tvPersonalMember.isSelected()){
                            tvPersonalMember.setSelected(false);
                            tvPersonalMember.setTextColor(OfflineMemberManageActivity.this.getResources().getColor(R.color.black_text));
                        }else {
                            tvPersonalMember.setSelected(true);
                            tvPersonalMember.setTextColor(Color.parseColor("#fe433a"));
                            startActivity(new Intent(OfflineMemberManageActivity.this,OfflineEnterpriseAddActivity.class));
                            memberChangedDialog.dismiss();
                        }
                    }
                });
                break;
            case R.id.offline_member_consume:

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
                        isWhichClick = 3;
                        memberNum = memberRechargeViewPhoneOrderNum.getText().toString();
                        if(!TextUtils.isEmpty(memberNum)){
                            if (CommonTools.checkPhoneNumber(OfflineMemberManageActivity.this,false,memberNum)){
                                dialog.setContent("加载中");
                                dialog.show();
                                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                params.put("token", UserCenter.getToken(OfflineMemberManageActivity.this));
                                params.put("number",memberNum);
                                requestHttpData(Constants.Urls.URL_POST_CUSTOM_INFO,REQUEST_CODE_MEMBER_ADD, FProtocol.HttpMethod.POST,params);
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

                View memberAddView = View.inflate(this, R.layout.collect_clothes_view,null);
                ImageView memberAddViewScan = (ImageView) memberAddView.findViewById(R.id.iv_scan);
                TextView memberAddViewInputNum = (TextView) memberAddView.findViewById(R.id.tv_input_num);
                final EditText memberAddViewPhoneOrderNum = (EditText) memberAddView.findViewById(R.id.ed_phone_order_num);
                TextView memberAddViewCancel = (TextView) memberAddView.findViewById(R.id.revise_phone_cancel);
                TextView memberAddViewConfirm = (TextView) memberAddView.findViewById(R.id.revise_phone_confirm);
                memberAddViewScan.setVisibility(View.GONE);
                memberAddViewInputNum.setText("请输入会员手机号");
                memberAddViewPhoneOrderNum.setHint("请输入会员手机号");
                collectClothesDialog = new CollectClothesDialog(this, R.style.DialogTheme,memberAddView);
                collectClothesDialog.show();

                memberAddViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        collectClothesDialog.dismiss();
                    }
                });

                memberAddViewConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isWhichClick = 2;
                        memberNum = memberAddViewPhoneOrderNum.getText().toString();
                        if(!TextUtils.isEmpty(memberNum)){
                            if (CommonTools.checkPhoneNumber(OfflineMemberManageActivity.this,false,memberNum)){
                                dialog.setContent("加载中");
                                dialog.show();
                                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                params.put("token", UserCenter.getToken(OfflineMemberManageActivity.this));
                                params.put("number",memberNum);
                                requestHttpData(Constants.Urls.URL_POST_CUSTOM_INFO,REQUEST_CODE_MEMBER_ADD, FProtocol.HttpMethod.POST,params);
                            }
                        }else {
                            ToastUtil.shortShow(OfflineMemberManageActivity.this,"请输入手机号");
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case  REQUEST_CODE_MEMBER_ADD:
                if(data != null){
                    OfflineCustomInfoEntity offlineCustomInfoEntity = Parsers.getOfflineCustomInfoEntity(data);
                    if(offlineCustomInfoEntity != null){
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if(offlineCustomInfoEntity.getRetcode() == 0){
                            if (isWhichClick == 1){
                                Intent intent = new Intent(this,OfflineMemberDetailActivity.class);
                                intent.putExtra("member_number",memberNum);
                                intent.putExtra("member_type",PERSONAL_MEMBER);
                                startActivity(intent);
                                collectClothesDialog.dismiss();
                            }else if (isWhichClick == 2){
                                //有会员存在
                                ToastUtil.shortShow(this,"此用户已是本店会员，不用重复添加");
                            }else {
                                Intent intent = new Intent(this,OfflineMemberRechargeActivity.class);
                                intent.putExtra("user_id",offlineCustomInfoEntity.getDatas().getId());
                                startActivity(intent);
                                collectClothesDialog.dismiss();
                            }
                        }else if (offlineCustomInfoEntity.getRetcode() == 1){
                            //会员不存在 新增散客
                            if (isWhichClick == 1){
                                ToastUtil.shortShow(this,offlineCustomInfoEntity.getStatus());
                            } else if (isWhichClick == 2){
                                //新增会员
                                Intent intent = new Intent(this,OfflineMemberAddActivity.class);
                                intent.putExtra("phone_num",memberNum);
                                startActivity(intent);
                                collectClothesDialog.dismiss();
                            }else {
                                LogUtil.e("zhang","其他的");
                                ToastUtil.shortShow(this,"此用户不是是本店会员，请先添加会员");
                            }
                        }else {
                            ToastUtil.shortShow(this,offlineCustomInfoEntity.getStatus());
                        }
                    }
                }
                break;
        }
    }
}
