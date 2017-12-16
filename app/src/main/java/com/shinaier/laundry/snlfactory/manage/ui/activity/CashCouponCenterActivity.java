package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.CashCouponCenterAdapter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CashCouponCenterEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.view.OnDateSetListener;
import com.shinaier.laundry.snlfactory.offlinecash.view.TimePickerDialog;
import com.shinaier.laundry.snlfactory.offlinecash.view.Type;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;

/**
 * Created by 张家洛 on 2017/12/2.
 */

public class CashCouponCenterActivity extends ToolBarActivity implements View.OnClickListener, OnDateSetListener {
    private static final int REQUEST_CODE_CASH_COUPON_CENTER = 0x1;
    public static final int CASH_COUPON = 0x2;
    public static final int RECHARGE_CARD = 0x3;

    @ViewInject(R.id.iv_make_cash_coupon)
    private ImageView ivMakeCashCoupon;
    @ViewInject(R.id.iv_make_recharge_card)
    private ImageView ivMakeRechargeCard;
    @ViewInject(R.id.iv_select_time)
    private ImageView ivSelectTime;
    @ViewInject(R.id.tv_select_time)
    private TextView tvSelectTime;
    @ViewInject(R.id.card_coupons_list)
    private ListView cardCouponsList;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
    TimePickerDialog mDialogYearMonthDay;
    private String nowTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_coupon_center_act);
        ViewInjectUtils.inject(this);
        initView();

    }

    private void initView() {
        setCenterTitle("卡券中心");
        ivMakeCashCoupon.setOnClickListener(this);
        ivMakeRechargeCard.setOnClickListener(this);
        ivSelectTime.setOnClickListener(this);
        tvSelectTime.setOnClickListener(this);

        //获取当前月份
        Calendar calendar = Calendar.getInstance();
        nowTime = getDateToString(calendar.getTimeInMillis());

        tvSelectTime.setText(nowTime);

        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
//                .setThemeColor(context.getResources().getColor(R.color.white))
                .setCallBack(this)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(nowTime);
    }

    private void loadData(String time) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("date", time);
        requestHttpData(Constants.Urls.URL_POST_CASH_COUPON_CENTER,REQUEST_CODE_CASH_COUPON_CENTER,
                FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CASH_COUPON_CENTER:
                if (data != null){
                    final CashCouponCenterEntity cashCouponCenterEntity = Parsers.getCashCouponCenterEntity(data);
                    if (cashCouponCenterEntity != null){
                        if (cashCouponCenterEntity.getCode() == 0){
                            if (cashCouponCenterEntity.getResult() != null && cashCouponCenterEntity.getResult().size() > 0){
                                CashCouponCenterAdapter cashCouponCenterAdapter = new CashCouponCenterAdapter(this,cashCouponCenterEntity.getResult());
                                cardCouponsList.setAdapter(cashCouponCenterAdapter);
                                cashCouponCenterAdapter.setPositionListener(new CashCouponCenterAdapter.PositionListener() {
                                    @Override
                                    public void onClick(int position, int innerPosition) {
                                        Intent intent = new Intent(CashCouponCenterActivity.this,CashCouponActivity.class);
                                        intent.putExtra("id",cashCouponCenterEntity.getResult().get(position).getRecordses()
                                        .get(innerPosition).getId());
                                        intent.putExtra("type",cashCouponCenterEntity.getResult().get(position).getRecordses()
                                                .get(innerPosition).getType());
                                        startActivity(intent);
                                    }
                                });
                            }

                        }else {
                            ToastUtil.shortShow(this,cashCouponCenterEntity.getMsg());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_make_cash_coupon:
                //制作优惠券
                Intent intentCashCoupon = new Intent(this,MakeCashCouponActivity.class);
                intentCashCoupon.putExtra("extra_from",CASH_COUPON);
                startActivity(intentCashCoupon);
                break;
            case R.id.iv_make_recharge_card:
                //制作充值卡
                Intent intentRechargeCard = new Intent(this,MakeCashCouponActivity.class);
                intentRechargeCard.putExtra("extra_from",RECHARGE_CARD);
                startActivity(intentRechargeCard);
                break;
            case R.id.iv_select_time:
            case R.id.tv_select_time:
                //时间选择
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month");
                break;
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String dateToString = getDateToString(millseconds);
        tvSelectTime.setText(dateToString);
        loadData(dateToString);
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
