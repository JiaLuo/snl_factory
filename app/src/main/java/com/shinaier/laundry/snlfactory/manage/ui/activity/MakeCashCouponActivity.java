package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CashCouponEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.view.OnDateSetListener;
import com.shinaier.laundry.snlfactory.offlinecash.view.TimePickerDialog;
import com.shinaier.laundry.snlfactory.offlinecash.view.Type;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;

/**
 * 制作代金券
 * Created by 张家洛 on 2017/10/30.
 */

public class MakeCashCouponActivity extends ToolBarActivity implements View.OnClickListener, OnDateSetListener {
    private static final int REQUEST_CODE_OFFLINE_CASH_CONPON = 0x1;

    @ViewInject(R.id.et_make_cash_coupon_num)
    private EditText etMakeCashCouponNum;
    @ViewInject(R.id.et_cash_coupon_money)
    private EditText etCashCouponMoney;
    @ViewInject(R.id.rl_cash_coupon_start_time)
    private RelativeLayout rlCashCouponStartTime;
    @ViewInject(R.id.cash_coupon_start_time)
    private TextView cashCouponStartTime;
    @ViewInject(R.id.rl_cash_coupon_end_time)
    private RelativeLayout rlCashCouponEndTime;
    @ViewInject(R.id.cash_coupon_end_time)
    private TextView cashCouponEndTime;
    @ViewInject(R.id.generate_bt)
    private TextView generateBt;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    private TimePickerDialog mDialogYearMonthDay;

    private boolean isClickStartTime = false;
    private boolean isClickEndTime = false;
    private String startTime;
    private String endTime;
    private long selectStartTime;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private int extraFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_cash_coupon_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        extraFrom = intent.getIntExtra("extra_from", 0);
        ExitManager.instance.addCashCouponActivity(this);
        initView();
    }

    private void initView() {
        if (extraFrom == CashCouponCenterActivity.CASH_COUPON){
            setCenterTitle("制作代金券");
        }else {
            setCenterTitle("制作充值卡");
        }
        rlCashCouponStartTime.setOnClickListener(this);
        rlCashCouponEndTime.setOnClickListener(this);
        generateBt.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
//                .setThemeColor(context.getResources().getColor(R.color.white))
                .setCallBack(this)
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.rl_cash_coupon_start_time:
                //开始使用时间
                isClickStartTime = true;
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.rl_cash_coupon_end_time:
                //结束使用时间
                isClickEndTime = true;
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.generate_bt:
                String cashCouponNum = etMakeCashCouponNum.getText().toString();
                //代金券数量不能超过100个 转换成double类型做判断。
                int cashNum = Integer.parseInt(cashCouponNum);
                String cashCouponMoney = etCashCouponMoney.getText().toString();
                if (!TextUtils.isEmpty(cashCouponNum)){
                    if (cashNum < 101){
                        if (!TextUtils.isEmpty(cashCouponMoney)){
                            if (!TextUtils.isEmpty(startTime)){
                                if (!TextUtils.isEmpty(endTime)){
                                    String url;
                                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                                    params.put("token", UserCenter.getToken(this));
                                    params.put("count",cashCouponNum);
                                    params.put("value",cashCouponMoney);
                                    params.put("start_time",startTime);
                                    params.put("end_time",endTime);
                                    if (extraFrom == CashCouponCenterActivity.CASH_COUPON){
                                        url = Constants.Urls.URL_POST_OFFLINE_CASH_CONPON;
                                    }else {
                                        url = Constants.Urls.URL_POST_RECHARGE_CARD;
                                    }
                                    //生成优惠券接口

                                    requestHttpData(url,REQUEST_CODE_OFFLINE_CASH_CONPON, FProtocol.HttpMethod.POST,params);
                                }else {
                                    ToastUtil.shortShow(this,"请选择结束使用日期");
                                }
                            }else {
                                ToastUtil.shortShow(this,"请选择开始使用日期");
                            }
                        }else {
                            ToastUtil.shortShow(this,"请填写代金券金额");
                        }
                    }else {
                        ToastUtil.shortShow(this,"代金券数量过大");
                    }
                }else {
                    ToastUtil.shortShow(this,"请填写制作张数");
                }
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_OFFLINE_CASH_CONPON:
                if (data != null){
                    CashCouponEntity cashCouponEntity = Parsers.getCashCouponEntity(data);
                    if (cashCouponEntity != null){
                        if (cashCouponEntity.getCode() == 0){
                            if (cashCouponEntity.getResult() != null){
                                ArrayList<CashCouponEntity.CashCouponResult> results = (ArrayList<CashCouponEntity.CashCouponResult>) cashCouponEntity.getResult();
                                Intent intent = new Intent(this,CashCouponActivity.class);
                                intent.putExtra("cash_coupon_results",results);
                                startActivity(intent);
                            }

                        }else {
                            ToastUtil.shortShow(this,cashCouponEntity.getMsg());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        long l = millseconds +( 60 * 60 * 24 * 1000);
        if(isClickStartTime){
            selectStartTime = millseconds;
            if(timeInMillis < l){
                startTime = getDateToString(selectStartTime);
                cashCouponStartTime.setText(getDateToString(selectStartTime));
            }else {
                ToastUtil.shortShow(this,"选择时间不能超过当前时间");
            }
            isClickStartTime = false;
        }

        if(isClickEndTime){
            if (timeInMillis < millseconds){
                if (millseconds >= selectStartTime){
                    endTime = getDateToString(millseconds);
                    cashCouponEndTime.setText(getDateToString(millseconds));
                }else {
                    ToastUtil.shortShow(this,"选择时间不能大于开始时间");
                }
            }else {
                ToastUtil.shortShow(this,"选择时间不能超过当前时间");
            }
            isClickEndTime = false;
        }
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
