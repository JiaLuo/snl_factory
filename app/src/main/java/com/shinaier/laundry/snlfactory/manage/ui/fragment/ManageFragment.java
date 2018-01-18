package com.shinaier.laundry.snlfactory.manage.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.DeviceUtil;
import com.common.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.fragment.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.ui.activity.CashBackActivity;
import com.shinaier.laundry.snlfactory.manage.ui.activity.CashCouponCenterActivity;
import com.shinaier.laundry.snlfactory.manage.ui.activity.ManageCommodityActivity;
import com.shinaier.laundry.snlfactory.manage.ui.activity.ManageEmployeeActivity;
import com.shinaier.laundry.snlfactory.manage.ui.activity.ManageFinanceActivity;
import com.shinaier.laundry.snlfactory.manage.ui.activity.MessageNoticeActivity;
import com.shinaier.laundry.snlfactory.manage.ui.activity.OperateAnalysisActivity;
import com.shinaier.laundry.snlfactory.manage.ui.activity.OrderSearchActivity;
import com.shinaier.laundry.snlfactory.manage.ui.activity.UserEvaluateActivity;
import com.shinaier.laundry.snlfactory.manage.view.SwitchView;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.StoreEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;

import java.text.DecimalFormat;
import java.util.IdentityHashMap;



/**
 * 管理
 * Created by 张家洛 on 2017/2/7.
 */

public class ManageFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_MANAGE = 0x1;
    private static final int REQUEST_CODE_STORE_STATUS = 0x2;

    private Context context;
    private View view;
    private TextView storeName;
    private SimpleDraweeView storeImg;
    private RatingBar ratingBarStore;
    private TextView collectStarInfo;
    private TextView todayBusinessVolume;
    private TextView todayOrderNum;
    private StoreEntity storeEntity;
    private SwitchView ivStoreSwitch;
    private TextView tvStoreSwitch;
    private TextView storeLocation;
    private ImageView ivMessageNotice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.manage_frag,null);
        context = getActivity();
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        requestHttpData(Constants.Urls.URL_POST_MANAGE,REQUEST_CODE_MANAGE, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        ivMessageNotice = view.findViewById(R.id.iv_message_notice);
        storeImg =  view.findViewById(R.id.store_img);
        storeName =  view.findViewById(R.id.store_name);
        ratingBarStore =  view.findViewById(R.id.rating_bar_store);
        collectStarInfo =  view.findViewById(R.id.collect_star_info);
        todayBusinessVolume =  view.findViewById(R.id.today_business_volume);
        todayOrderNum =  view.findViewById(R.id.today_order_num);
        ivStoreSwitch =  view.findViewById(R.id.iv_store_switch);
        tvStoreSwitch =  view.findViewById(R.id.tv_store_switch);
        storeLocation = view.findViewById(R.id.store_location);
        LinearLayout llManageFinance =  view.findViewById(R.id.ll_manage_finance);
        LinearLayout llCommodityManage =  view.findViewById(R.id.ll_commodity_manage);
        LinearLayout llInviteFriend =  view.findViewById(R.id.ll_invite_friend);
        LinearLayout llUserEvaluate =  view.findViewById(R.id.ll_user_evaluate);
        LinearLayout llOrderInquiry =  view.findViewById(R.id.ll_order_inquiry);
        LinearLayout llManageEmployee =  view.findViewById(R.id.ll_manage_employee);
        LinearLayout llCashBackRecord =  view.findViewById(R.id.ll_cash_back_record);
        LinearLayout llOperateAnalysis =  view.findViewById(R.id.ll_operate_analysis);
        LinearLayout llMakeCashCoupon = view.findViewById(R.id.ll_make_cash_coupon);
        llManageFinance.setOnClickListener(this);
        llCommodityManage.setOnClickListener(this);
        llInviteFriend.setOnClickListener(this);
        llUserEvaluate.setOnClickListener(this);
        ivStoreSwitch.setOnClickListener(this);
        tvStoreSwitch.setOnClickListener(this);
        llOrderInquiry.setOnClickListener(this);
        llManageEmployee.setOnClickListener(this);
        llCashBackRecord.setOnClickListener(this);
        llOperateAnalysis.setOnClickListener(this);
        ivMessageNotice.setOnClickListener(this);
        llMakeCashCoupon.setOnClickListener(this);
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_MANAGE:
                if(data != null){
                    StoreEntity storeEntity = Parsers.getStoreEntity(data);
                    if (storeEntity != null){
                        if (!storeEntity.getMessageCount().equals("0")){ //是否有未读消息
                            ivMessageNotice.setSelected(true);
                        }else {
                            ivMessageNotice.setSelected(false);
                        }
                        if (storeEntity.getCode() == 0){
                            StoreEntity.StoreResult result = storeEntity.getResult();
                            setStoreInfo(result);
                        }else {
                            ToastUtil.shortShow(context,storeEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_STORE_STATUS:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() != 0){
                        ToastUtil.shortShow(context,entity.getStatus());
                    }
                }
                break;
        }

    }

    private void setStoreInfo(StoreEntity.StoreResult result) {
        //设置店铺名称
        storeName.setText(result.getMname());
        //设置店铺logo
        String path = result.getMlogo();
        Uri uri = Uri.parse(path);
        storeImg.setImageURI(uri);
        //设置店铺评分
        ratingBarStore.setRating(Float.parseFloat(result.getMlevel()));
        collectStarInfo.setText(result.getMlevel());

        SpannableStringBuilder ssb1 = new SpannableStringBuilder();
        String total = formatMoney(result.getAmount());
        ssb1.append("￥" + total);
        if(result.getAmount() >= 0 && result.getAmount() < 10){
            ssb1.setSpan(new AbsoluteSizeSpan(DeviceUtil.dp_to_px(context,23)),1,2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }else if(result.getAmount() >= 10 && result.getAmount() < 100){
            ssb1.setSpan(new AbsoluteSizeSpan(DeviceUtil.dp_to_px(context,23)),1,3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }else if(result.getAmount() >= 100 && result.getAmount() < 1000){
            ssb1.setSpan(new AbsoluteSizeSpan(DeviceUtil.dp_to_px(context,23)),1,4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if (result.getMstatus().equals("10")){
            ivStoreSwitch.setOpened(true);
            tvStoreSwitch.setText("营业中");
            tvStoreSwitch.setTextColor(Color.parseColor("#ff6f43"));
        }else {
            ivStoreSwitch.setOpened(false);
            tvStoreSwitch.setText("休息中");
            tvStoreSwitch.setTextColor(Color.parseColor("#b2b2b2"));
        }
        todayBusinessVolume.setText(ssb1);
        todayOrderNum.setText(result.getOrderCount());

        storeLocation.setText(result.getMaddress());

    }

    public String formatMoney(double money){
        DecimalFormat df = new DecimalFormat("###0.00");
        return df.format(money);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_store_switch:
            case R.id.tv_store_switch:
                String isBusiness;
                if (ivStoreSwitch.isOpened()){
                    isBusiness = "1";
                    tvStoreSwitch.setText("营业中");
                    tvStoreSwitch.setTextColor(Color.parseColor("#ff6f43"));
                }else {
                    isBusiness = "0";
                    tvStoreSwitch.setText("休息中");
                    tvStoreSwitch.setTextColor(Color.parseColor("#b2b2b2"));
                }
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("token",UserCenter.getToken(context));
                params.put("open", isBusiness);
                requestHttpData(Constants.Urls.URL_POST_STORE_STATUS,REQUEST_CODE_STORE_STATUS, FProtocol.HttpMethod.POST,params);

                break;
            case R.id.ll_manage_finance:
                //财务管理
                startActivity(new Intent(context,ManageFinanceActivity.class));
                break;
            case R.id.ll_operate_analysis:
                startActivity(new Intent(context,OperateAnalysisActivity.class));
                break;
            case R.id.ll_commodity_manage:
                //商品管理
                startActivity(new Intent(context,ManageCommodityActivity.class));
                break;
            case R.id.ll_invite_friend:
                //邀请好友
                // TODO: 2017/10/26  邀请好友麻烦 最后弄
//                startActivity(new Intent(context,InviteFriendActivity.class));
                break;
            case R.id.ll_user_evaluate:
                //用户评价
                startActivity(new Intent(context,UserEvaluateActivity.class));
                break;
            case R.id.iv_message_notice:
                //消息通知
                startActivity(new Intent(context,MessageNoticeActivity.class));
                break;
            case R.id.ll_manage_employee:
                //员工管理
                startActivity(new Intent(context,ManageEmployeeActivity.class));
                break;
            case R.id.ll_cash_back_record:
                //返现记录
                startActivity(new Intent(context,CashBackActivity.class));
                break;
            case R.id.ll_order_inquiry:
                //订单查询
//                startActivity(new Intent(context,OrderInquiryActivity.class));
                startActivity(new Intent(context,OrderSearchActivity.class));
                break;
            case R.id.ll_make_cash_coupon:
                //制作代金券
//                startActivity(new Intent(context,MakeCashCouponActivity.class));
                startActivity(new Intent(context,CashCouponCenterActivity.class));
                break;
        }
    }
}
