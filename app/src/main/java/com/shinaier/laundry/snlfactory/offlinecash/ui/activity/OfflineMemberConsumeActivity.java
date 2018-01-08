package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberConsumeListEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberRechargeListEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineMemberConsumeAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.OfflineMemberRechargeAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.view.OnDateSetListener;
import com.shinaier.laundry.snlfactory.offlinecash.view.TimePickerDialog;
import com.shinaier.laundry.snlfactory.offlinecash.view.Type;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;


/**
 * 会员消费报表
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberConsumeActivity extends ToolBarActivity implements View.OnClickListener, OnDateSetListener {
    private static final int REQUEST_CODE_OFFLINE_MEMBER_CONSUME_LIST = 0x1;
    private static final int REQUEST_CODE_OFFLINE_MEMBER_RECHARGE_LIST = 0x2;
    private static final int REQUEST_CODE_OFFLINE_MEMBER_CONSUME_LIST_MORE = 0x3;
    private static final int REQUEST_CODE_OFFLINE_MEMBER_RECHARGE_LIST_MORE = 0x4;

    @ViewInject(R.id.offline_member_consume_list)
    private FootLoadingListView offlineMemberConsumeList;
    @ViewInject(R.id.tv_total_order)
    private TextView tvTotalOrder;
    @ViewInject(R.id.tv_total_order_info)
    private TextView tvTotalOrderInfo;
    @ViewInject(R.id.iv_total_order)
    private ImageView ivTotalOrder;
    @ViewInject(R.id.iv_total_order_num)
    private ImageView ivTotalOrderNum;
    @ViewInject(R.id.tv_total_order_num)
    private TextView tvTotalOrderNum;
    @ViewInject(R.id.tv_total_order_money)
    private TextView tvTotalOrderMoney;
    @ViewInject(R.id.tv_member_detail)
    private TextView tvMemberDetail;
    @ViewInject(R.id.offline_member_search)
    private TextView offlineMemberSearch;
    @ViewInject(R.id.tv_start_time)
    private TextView tvStartTime; //开始时间
    @ViewInject(R.id.tv_end_time)
    private TextView tvEndTime; //结束时间
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private int extraFrom;
    private boolean isClickStartTime = false;
    private boolean isClickEndTime = false;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    TimePickerDialog mDialogYearMonthDay;
    private String startTime;
    private String endTime;
    // 自定dialog
    private CommonDialog dialog;
    private OfflineMemberConsumeAdapter offlineMemberConsumeAdapter;
    private OfflineMemberRechargeAdapter offlineMemberRechargeAdapter;

    private long selectStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_member_consume_act);
        ViewInjectUtils.inject(this);
        extraFrom = getIntent().getIntExtra("extra_from", 0);
        loadData(getTodayTime(-1),getTodayTime(-1),false);
        initView();
    }

    private void loadData(String startTime,String endTime,boolean isMore) {
        int code = 0;
        String url = "";
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token",UserCenter.getToken(this));
        params.put("start",startTime);
        params.put("end",endTime);
        if (extraFrom == OfflineMemberManageActivity.CONSUME){
            params.put("type","1");
            if (isMore){
                params.put("page",offlineMemberConsumeAdapter.getPage() + 1 + "");
                code = REQUEST_CODE_OFFLINE_MEMBER_CONSUME_LIST_MORE;
            }else {
                params.put("page","1");
                code = REQUEST_CODE_OFFLINE_MEMBER_CONSUME_LIST;
            }
            url = Constants.Urls.URL_POST_CONSUME_LIST;
        }else {
            if (isMore){
                params.put("page",offlineMemberRechargeAdapter.getPage() + 1 + "");
                code = REQUEST_CODE_OFFLINE_MEMBER_RECHARGE_LIST_MORE;
            }else {
                params.put("page","1");
                code = REQUEST_CODE_OFFLINE_MEMBER_RECHARGE_LIST;
            }
            url = Constants.Urls.URL_POST_RECHARGE_LIST;
        }
        params.put("limit","10");

        requestHttpData(url,code,
                FProtocol.HttpMethod.POST,params);
    }


    private void initView() {
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        offlineMemberSearch.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(this)
                .build();

        if(extraFrom == OfflineMemberManageActivity.CONSUME){
            //消费
            setCenterTitle("会员消费报表");
            ivTotalOrder.setBackground(this.getResources().getDrawable(R.drawable.total_order_img));
            ivTotalOrderNum.setBackground(this.getResources().getDrawable(R.drawable.total_order_num_img));
            tvTotalOrder.setText("累计订单数");
            tvTotalOrderNum.setText("累计订单总额");
            tvMemberDetail.setText("消费明细");
        }else {
            //充值
            setCenterTitle("会员充值列表");
            ivTotalOrder.setBackground(this.getResources().getDrawable(R.drawable.total_rechar_money_img));
            ivTotalOrderNum.setBackground(this.getResources().getDrawable(R.drawable.total_give_money_img));
            tvTotalOrder.setText("累计充值金额");
            tvTotalOrderNum.setText("累计赠送金额");
            tvMemberDetail.setText("充值明细");
        }

        dialog = new CommonDialog(this);//初始化自定义dialog

        offlineMemberConsumeList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if ((startTime != null && !TextUtils.isEmpty(startTime)) || (endTime != null && !TextUtils.isEmpty(endTime)) ){
                    loadData(startTime,endTime,false);
                }else {
                    loadData(getTodayTime(-1),getTodayTime(-1),false);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if ((startTime != null && !TextUtils.isEmpty(startTime)) || (endTime != null && !TextUtils.isEmpty(endTime)) ){
                    loadData(startTime,endTime,true);
                }else {
                    loadData(getTodayTime(-1),getTodayTime(-1),true);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.offline_member_search:
                //时间选择后查询
                if (!TextUtils.isEmpty(startTime)){
                    if (!TextUtils.isEmpty(endTime)){
                        dialog.setContent("加载中");
                        dialog.show();
                        loadData(startTime,endTime,false);
                    }else {
                        ToastUtil.shortShow(this,"请选择结束时间");
                    }
                }else {
                    ToastUtil.shortShow(this,"请选择开始时间");
                }
                break;
            case R.id.tv_start_time:
                isClickStartTime = true;
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.tv_end_time:
                isClickEndTime = true;
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_OFFLINE_MEMBER_CONSUME_LIST:
                if(data != null){
                    OfflineMemberConsumeListEntity offlineMemberConsumeListEntity = Parsers.getOfflineMemberConsumeEntity(data);
                    if (offlineMemberConsumeListEntity != null){
                        if (offlineMemberConsumeListEntity.getCode() == 0){
                            if (offlineMemberConsumeListEntity.getResult() != null){
                                //设置消费报表
                                setConsumeData(offlineMemberConsumeListEntity);
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineMemberConsumeListEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_OFFLINE_MEMBER_RECHARGE_LIST:
                if (data != null){
                    OfflineMemberRechargeListEntity offlineMemberRechargeListEntity = Parsers.getOfflineMemberRechargeListEntity(data);
                    if (offlineMemberRechargeListEntity != null){
                        if (offlineMemberRechargeListEntity.getCode() == 0){
                            if (offlineMemberRechargeListEntity.getResult() != null){
                                //设置充值报表
                                setRechargeData(offlineMemberRechargeListEntity);
                            }
                        }else {
                            ToastUtil.shortShow(this, offlineMemberRechargeListEntity.getMsg());
                        }
                    }
                }
                break;
            case REQUEST_CODE_OFFLINE_MEMBER_CONSUME_LIST_MORE:
                offlineMemberConsumeList.onRefreshComplete();
                if(data != null){
                    OfflineMemberConsumeListEntity offlineMemberConsumeListEntity = Parsers.getOfflineMemberConsumeEntity(data);
                    offlineMemberConsumeAdapter.addDatas(offlineMemberConsumeListEntity.getResult().getConsumeRecord());
                    if( offlineMemberConsumeAdapter.getPage() < offlineMemberConsumeListEntity.getResult().getPageCount()){
                        offlineMemberConsumeList.setCanAddMore(true);
                    }else {
                        offlineMemberConsumeList.setCanAddMore(false);
                    }
                }
                break;
            case REQUEST_CODE_OFFLINE_MEMBER_RECHARGE_LIST_MORE:
                offlineMemberConsumeList.onRefreshComplete();
                if(data != null){
                    OfflineMemberRechargeListEntity offlineMemberRechargeListEntity = Parsers.getOfflineMemberRechargeListEntity(data);
                    offlineMemberRechargeAdapter.addDatas(offlineMemberRechargeListEntity.getResult().getRechargeRecords());
                    if( offlineMemberRechargeAdapter.getPage() < offlineMemberRechargeListEntity.getResult().getPageCount()){
                        offlineMemberConsumeList.setCanAddMore(true);
                    }else {
                        offlineMemberConsumeList.setCanAddMore(false);
                    }
                }
                break;
        }
    }

    private void setRechargeData(OfflineMemberRechargeListEntity offlineMemberRechargeListEntity) {
        //设置充值金额
        if (offlineMemberRechargeListEntity.getResult().getRecharge() != null &&
                !offlineMemberRechargeListEntity.getResult().getRecharge().equals("0")){
            tvTotalOrderInfo.setText("￥" + offlineMemberRechargeListEntity.getResult().getRecharge());
        }else {
            tvTotalOrderInfo.setText("￥0.00");
        }

        //设置赠送金额
        if (offlineMemberRechargeListEntity.getResult().getGive() != null && !offlineMemberRechargeListEntity
                .getResult().getGive().equals("0")){
            tvTotalOrderMoney.setText("￥" + offlineMemberRechargeListEntity.getResult().getGive());
        }else {
            tvTotalOrderMoney.setText("￥0.00");
        }

        if (dialog.isShowing()){
            dialog.dismiss();
        }
        offlineMemberConsumeList.onRefreshComplete();
        //充值明细
        if (offlineMemberRechargeListEntity.getResult().getRechargeRecords() != null &&
                offlineMemberRechargeListEntity.getResult().getRechargeRecords().size() > 0){
            setLoadingStatus(LoadingStatus.GONE);


            offlineMemberRechargeAdapter = new OfflineMemberRechargeAdapter(this,
                    offlineMemberRechargeListEntity.getResult().getRechargeRecords());
            offlineMemberConsumeList.setAdapter(offlineMemberRechargeAdapter);
            if( offlineMemberRechargeAdapter.getPage() < offlineMemberRechargeListEntity.getResult().getPageCount()){
                offlineMemberConsumeList.setCanAddMore(true);
            }else {
                offlineMemberConsumeList.setCanAddMore(false);
            }
        }else {
            setLoadingStatus(LoadingStatus.EMPTY);
            OfflineMemberRechargeAdapter offlineMemberRechargeAdapter = new OfflineMemberRechargeAdapter(this,
                    new ArrayList<OfflineMemberRechargeListEntity.OfflineMemberRechargeResult.OfflineMemberRechargeRecord>());
            offlineMemberConsumeList.setAdapter(offlineMemberRechargeAdapter);
        }
    }

    /**
     * 设置消费报表
     * @param offlineMemberConsumeListEntity 消费报表数据
     */
    private void setConsumeData(OfflineMemberConsumeListEntity offlineMemberConsumeListEntity) {
        //设置订单数
        if (offlineMemberConsumeListEntity.getResult().getCount() != null){
            tvTotalOrderInfo.setText(offlineMemberConsumeListEntity.getResult().getCount());
        }else {
            tvTotalOrderInfo.setText("0");
        }

        //设置订单金额
        if (offlineMemberConsumeListEntity.getResult().getSum() != null && !offlineMemberConsumeListEntity.getResult()
                .getSum().equals("0")){
            tvTotalOrderMoney.setText("￥" + offlineMemberConsumeListEntity.getResult().getSum());
        }else {
            tvTotalOrderMoney.setText("￥0.00");
        }

        if (dialog.isShowing()){
            dialog.dismiss();
        }
        offlineMemberConsumeList.onRefreshComplete();
        //消费明细
        if (offlineMemberConsumeListEntity.getResult().getConsumeRecord() != null &&
                offlineMemberConsumeListEntity.getResult().getConsumeRecord().size() > 0){
            setLoadingStatus(LoadingStatus.GONE);


            offlineMemberConsumeAdapter = new OfflineMemberConsumeAdapter(this,
                    offlineMemberConsumeListEntity.getResult().getConsumeRecord());
            offlineMemberConsumeList.setAdapter(offlineMemberConsumeAdapter);

            if( offlineMemberConsumeAdapter.getPage() < offlineMemberConsumeListEntity.getResult().getPageCount()){
                offlineMemberConsumeList.setCanAddMore(true);
            }else {
                offlineMemberConsumeList.setCanAddMore(false);
            }
        }else {
            setLoadingStatus(LoadingStatus.EMPTY);
            OfflineMemberConsumeAdapter offlineMemberConsumeAdapter = new OfflineMemberConsumeAdapter(this,
                    new ArrayList<OfflineMemberConsumeListEntity.OfflineMemberConsumeResult.OfflineMemberConsumeRecord>());
            offlineMemberConsumeList.setAdapter(offlineMemberConsumeAdapter);
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();

        if(isClickStartTime){
            selectStartTime = millseconds;
            if(timeInMillis > selectStartTime){
                startTime = getDateToString(selectStartTime);
                tvStartTime.setText(getDateToString(selectStartTime));
            }else {
                ToastUtil.shortShow(this,"选择时间不能超过当前时间");
            }
            isClickStartTime = false;
        }

        if(isClickEndTime){
            if (timeInMillis > millseconds){
                if (millseconds >= selectStartTime){
                    endTime = getDateToString(millseconds);
                    tvEndTime.setText(getDateToString(millseconds));
                }else{
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

    private String getTodayTime(int number){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, number);
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        String qt = sdf.format(calendar.getTime());
        return qt;
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_OFFLINE_MEMBER_CONSUME_LIST:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_OFFLINE_MEMBER_RECHARGE_LIST:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
