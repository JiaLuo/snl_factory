package com.shinaier.laundry.snlfactory.offlinecash.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.widget.FootLoadingListView;
import com.common.widget.PullToRefreshBase;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.BaseFragment;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.StatisticsIncomeEntity;
import com.shinaier.laundry.snlfactory.network.entity.StatisticsNoPayEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.StatisticsIncomeAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.StatisticsNoPayAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.view.OnDateSetListener;
import com.shinaier.laundry.snlfactory.offlinecash.view.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;

/**
 * Created by 张家洛 on 2017/7/26.
 */

public class StatisticsFragment extends BaseFragment implements View.OnClickListener, OnDateSetListener {
    private static final int REQUEST_CODE_INCOME_STATISTICS = 0x1;
    private static final int REQUEST_CODE_NO_PAY_STATISTICS = 0x2;
    private static final int REQUEST_CODE_NO_DONE_STATISTICS = 0x3;
    private static final int REQUEST_CODE_INCOME_STATISTICS_MORE = 0x4;
    private static final int REQUEST_CODE_NO_PAY_STATISTICS_MORE = 0x5;
    private static final int REQUEST_CODE_NO_DONE_STATISTICS_MORE = 0x6;
    private Context context;
    private int status; // 1.收银统计 2.未付款统计 3.未接单统计

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    TimePickerDialog mDialogYearMonthDay;
    private boolean isClickStartTime = false;
    private boolean isClickEndTime = false;
    private String startTime;
    private String endTime;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private FootLoadingListView statisticsList;
    private TextView totalIncome;
    private StatisticsIncomeAdapter statisticsIncomeAdapter;
    private StatisticsNoPayAdapter statisticsNoPayAdapter;
    private StatisticsNoPayAdapter statisticsNoPayAdapter1;
    private long selectStartTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLoadingStatus(LoadingStatus.LOADING);
        loadData(false,getTodayTime(-1),getTodayTime(-1));
        initView(view);
    }

    private void loadData(boolean isMore, String startTime, String endTime) {
        String path = "";
        int code = 0;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(context));
        params.put("start_time",startTime);
        params.put("end_time",endTime);
        params.put("limit","10");
        if(status == 1){
            path = Constants.Urls.URL_POST_INCOME_STATISTICS;
            if (isMore){
                params.put("page",statisticsIncomeAdapter.getPage() + 1 + "");
                code = REQUEST_CODE_INCOME_STATISTICS_MORE;
            }else {
                params.put("page","1");
                code = REQUEST_CODE_INCOME_STATISTICS;
            }
        }else if(status == 2){
            path = Constants.Urls.URL_POST_NO_PAY_STATISTICS;
            if (isMore){
                params.put("page",statisticsNoPayAdapter.getPage() + 1 + "");
                code = REQUEST_CODE_NO_PAY_STATISTICS_MORE;
            }else {
                params.put("page","1");
                code = REQUEST_CODE_NO_PAY_STATISTICS;
            }
        }else if(status == 3){
            path = Constants.Urls.URL_POST_NO_DONE_STATISTICS;
            if (isMore){
                params.put("page",statisticsNoPayAdapter1.getPage() + 1 + "");
                code = REQUEST_CODE_NO_DONE_STATISTICS_MORE;
            }else {
                params.put("page","1");
                code = REQUEST_CODE_NO_DONE_STATISTICS;
            }
        }
        requestHttpData(path,code, FProtocol.HttpMethod.POST,params);
    }

    private void initView(View view) {
        initLoadingView(this,view);
        setLoadingStatus(LoadingStatus.LOADING);
        LinearLayout llStartTime = (LinearLayout) view.findViewById(R.id.ll_start_time);
        LinearLayout llEndTime = (LinearLayout) view.findViewById(R.id.ll_end_time);
        statisticsList = (FootLoadingListView) view.findViewById(R.id.statistics_list);
        TextView statisticsSearch = (TextView) view.findViewById(R.id.statistics_search);
        RelativeLayout rlTotalAmount = (RelativeLayout) view.findViewById(R.id.rl_total_amount);
        totalIncome = (TextView) view.findViewById(R.id.total_income);

        tvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
        tvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
        tvStartTime.setHint(getTodayTime(-1));//开始时间显示前一天时间
        tvEndTime.setHint(getTodayTime(-1));
        if(status == 1){
            rlTotalAmount.setVisibility(View.VISIBLE);
        }else {
            rlTotalAmount.setVisibility(View.GONE);
        }
        llStartTime.setOnClickListener(this);
        llEndTime.setOnClickListener(this);
        statisticsSearch.setOnClickListener(this);


        mDialogYearMonthDay = new TimePickerDialog.Builder()
//                .setType(Type.YEAR_MONTH_DAY)
//                .setThemeColor(context.getResources().getColor(R.color.white))
                .setCallBack(this)
                .build();
        statisticsList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (startTime != null && !TextUtils.isEmpty(startTime) && endTime != null
                        && !TextUtils.isEmpty(endTime)){
                        loadData(false,startTime,endTime);
                }else {
                    loadData(false,getTodayTime(-1),getTodayTime(-1));
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (startTime != null && !TextUtils.isEmpty(startTime) && endTime != null
                        && !TextUtils.isEmpty(endTime)){
                    loadData(true,startTime,endTime);
                }else {
                    loadData(true,getTodayTime(-1),getTodayTime(-1));
                }
            }
        });
    }

    public void setArgs(Context context, int status){
        this.context = context;
        this.status = status;
    }

    @Override
    public void success(int requestCode, String data) {
        super.success(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_INCOME_STATISTICS:
                if (data != null){
                    StatisticsIncomeEntity statisticsIncomeEntity = Parsers.getStatisticsIncomeEntity(data);
                    statisticsList.onRefreshComplete();
                    if(statisticsIncomeEntity != null){
                        if(statisticsIncomeEntity.getRetcode() == 0){
                            if (statisticsIncomeEntity.getDatas() != null){
                                if(statisticsIncomeEntity.getDatas().getRecords() != null &&
                                        statisticsIncomeEntity.getDatas().getRecords().size() > 0){
                                    if (statisticsIncomeEntity.getDatas().getTotalAmount() != null &&
                                            !TextUtils.isEmpty(statisticsIncomeEntity.getDatas().getTotalAmount())){
                                        totalIncome.setText("￥" + statisticsIncomeEntity.getDatas().getTotalAmount());
                                    }
                                    setLoadingStatus(LoadingStatus.GONE);
                                    statisticsList.setVisibility(View.VISIBLE);
                                    statisticsIncomeAdapter = new StatisticsIncomeAdapter(context,
                                            statisticsIncomeEntity.getDatas().getRecords());
                                    statisticsList.setAdapter(statisticsIncomeAdapter);
                                    if( statisticsIncomeAdapter.getPage() < statisticsIncomeEntity.getDatas().getPageCount()){
                                        statisticsList.setCanAddMore(true);
                                    }else {
                                        statisticsList.setCanAddMore(false);
                                    }
                                }else {
                                    if (statisticsIncomeEntity.getDatas().getTotalAmount() == null){
                                        totalIncome.setText("￥0.00");
                                    }
                                    statisticsList.setVisibility(View.GONE);
                                    setLoadingStatus(LoadingStatus.EMPTY);
                                    statisticsIncomeAdapter = new StatisticsIncomeAdapter(context, new ArrayList<StatisticsIncomeEntity.StatisticsIncomeDatas.StatisticsIncomeRecord>());
                                    statisticsList.setAdapter(statisticsIncomeAdapter);
                                    statisticsList.setCanAddMore(false);
                                }
                            }else {
                                statisticsList.setVisibility(View.GONE);
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(context,statisticsIncomeEntity.getStatus());
                        }
                    }else {
                        statisticsList.setVisibility(View.GONE);
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    statisticsList.setVisibility(View.GONE);
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_NO_PAY_STATISTICS:
                if (data != null){
                    final StatisticsNoPayEntity statisticsNoPayEntity = Parsers.getStatisticsNoPayEntity(data);
                    statisticsList.onRefreshComplete();
                    if (statisticsNoPayEntity != null){
                        if (statisticsNoPayEntity.getRetcode() == 0){
                            if(statisticsNoPayEntity.getData() != null){
                                if(statisticsNoPayEntity.getData().getOrders() != null && statisticsNoPayEntity.getData().getOrders()
                                        .size() > 0){
                                    statisticsList.setVisibility(View.VISIBLE);
                                    setLoadingStatus(LoadingStatus.GONE);
                                    statisticsNoPayAdapter = new StatisticsNoPayAdapter(context,statisticsNoPayEntity.getData()
                                    .getOrders());
                                    statisticsList.setAdapter(statisticsNoPayAdapter);
                                    if( statisticsNoPayAdapter.getPage() < statisticsNoPayEntity.getData().getPageCount()){
                                        statisticsList.setCanAddMore(true);
                                    }else {
                                        statisticsList.setCanAddMore(false);
                                    }

                                    statisticsNoPayAdapter.setPositionListener(new StatisticsNoPayAdapter.PositionListener() {
                                        @Override
                                        public void onClick(int position, int innerPosition) {
                                            Intent intent = new Intent(context,OfflineOrderDetailActivity.class);
                                            intent.putExtra("order_id",statisticsNoPayEntity.getData().getOrders().get(position).getInnerOrders()
                                            .get(innerPosition).getId());
                                            startActivity(intent);
                                        }
                                    });
                                }else {
                                    statisticsList.setVisibility(View.GONE);
                                    setLoadingStatus(LoadingStatus.EMPTY);
                                    statisticsNoPayAdapter = new StatisticsNoPayAdapter(context,new ArrayList<StatisticsNoPayEntity.StatisticsData.StatisticsOrders>());
                                    statisticsList.setAdapter(statisticsNoPayAdapter);
                                    statisticsList.setCanAddMore(false);
                                }
                            }else {
                                statisticsList.setVisibility(View.GONE);
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(context,statisticsNoPayEntity.getStatus());
                        }
                    }else {
                        statisticsList.setVisibility(View.GONE);
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    statisticsList.setVisibility(View.GONE);
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_NO_DONE_STATISTICS:
                if (data != null){
                    final StatisticsNoPayEntity statisticsNoPayEntity = Parsers.getStatisticsNoPayEntity(data);
                    statisticsList.onRefreshComplete();
                    if (statisticsNoPayEntity != null){
                        if (statisticsNoPayEntity.getRetcode() == 0){
                            if(statisticsNoPayEntity.getData() != null){
                                if(statisticsNoPayEntity.getData().getOrders() != null && statisticsNoPayEntity.getData().getOrders()
                                        .size() > 0){
                                    statisticsList.setVisibility(View.VISIBLE);
                                    setLoadingStatus(LoadingStatus.GONE);
                                    statisticsNoPayAdapter1 = new StatisticsNoPayAdapter(context,statisticsNoPayEntity.getData()
                                            .getOrders());
                                    statisticsList.setAdapter(statisticsNoPayAdapter1);
                                    if( statisticsNoPayAdapter1.getPage() < statisticsNoPayEntity.getData().getPageCount()){
                                        statisticsList.setCanAddMore(true);
                                    }else {
                                        statisticsList.setCanAddMore(false);
                                    }
                                    statisticsNoPayAdapter1.setPositionListener(new StatisticsNoPayAdapter.PositionListener() {
                                        @Override
                                        public void onClick(int position, int innerPosition) {
                                            Intent intent = new Intent(context,OfflineOrderDetailActivity.class);
                                            intent.putExtra("order_id",statisticsNoPayEntity.getData().getOrders().get(position).getInnerOrders()
                                                    .get(innerPosition).getId());
                                            startActivity(intent);
                                        }
                                    });
                                }else {
                                    statisticsList.setVisibility(View.GONE);
                                    setLoadingStatus(LoadingStatus.EMPTY);
                                    statisticsNoPayAdapter1 = new StatisticsNoPayAdapter(context,new ArrayList<StatisticsNoPayEntity.StatisticsData.StatisticsOrders>());
                                    statisticsList.setAdapter(statisticsNoPayAdapter1);
                                    statisticsList.setCanAddMore(false);
                                }
                            }else {
                                statisticsList.setVisibility(View.GONE);
                                setLoadingStatus(LoadingStatus.EMPTY);
                            }
                        }else {
                            ToastUtil.shortShow(context,statisticsNoPayEntity.getStatus());
                        }
                    }else {
                        statisticsList.setVisibility(View.GONE);
                        setLoadingStatus(LoadingStatus.EMPTY);
                    }
                }else {
                    statisticsList.setVisibility(View.GONE);
                    setLoadingStatus(LoadingStatus.EMPTY);
                }
                break;
            case REQUEST_CODE_INCOME_STATISTICS_MORE:
                statisticsList.onRefreshComplete();
                if(data != null){
                    StatisticsIncomeEntity statisticsIncomeEntity = Parsers.getStatisticsIncomeEntity(data);
                    statisticsIncomeAdapter.addDatas(statisticsIncomeEntity.getDatas().getRecords());
                    if( statisticsIncomeAdapter.getPage() < statisticsIncomeEntity.getDatas().getPageCount()){
                        statisticsList.setCanAddMore(true);
                    }else {
                        statisticsList.setCanAddMore(false);
                    }
                }
                break;
            case REQUEST_CODE_NO_PAY_STATISTICS_MORE:
                statisticsList.onRefreshComplete();
                if(data != null){
                    StatisticsNoPayEntity statisticsNoPayEntity = Parsers.getStatisticsNoPayEntity(data);
                    statisticsNoPayAdapter.addDatas(statisticsNoPayEntity.getData().getOrders());
                    if( statisticsNoPayAdapter.getPage() < statisticsNoPayEntity.getData().getPageCount()){
                        statisticsList.setCanAddMore(true);
                    }else {
                        statisticsList.setCanAddMore(false);
                    }
                }
                break;
            case REQUEST_CODE_NO_DONE_STATISTICS_MORE:
                statisticsList.onRefreshComplete();
                if(data != null){
                    StatisticsNoPayEntity statisticsNoPayEntity = Parsers.getStatisticsNoPayEntity(data);
                    statisticsNoPayAdapter1.addDatas(statisticsNoPayEntity.getData().getOrders());
                    if( statisticsNoPayAdapter1.getPage() < statisticsNoPayEntity.getData().getPageCount()){
                        statisticsList.setCanAddMore(true);
                    }else {
                        statisticsList.setCanAddMore(false);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_start_time:
                isClickStartTime = true;
                mDialogYearMonthDay.show(getFragmentManager(), "year_month_day");
                break;
            case R.id.ll_end_time:
                isClickEndTime = true;
                mDialogYearMonthDay.show(getFragmentManager(), "year_month_day");
                break;
            case R.id.statistics_search:
                if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)){
                    setLoadingStatus(LoadingStatus.LOADING);
                        loadData(false,startTime,endTime);
                }else {
                    ToastUtil.shortShow(context,"请输入时间");
                }
                break;
            case R.id.loading_img_refresh:
                loadData(false,getTodayTime(-1),getTodayTime(-1));
                break;
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
                ToastUtil.shortShow(context,"选择时间不能超过当前时间");
            }
            isClickStartTime = false;
        }

        if(isClickEndTime){
            if (timeInMillis > millseconds){
                if (millseconds >= selectStartTime){
                    endTime = getDateToString(millseconds);
                    tvEndTime.setText(getDateToString(millseconds));
                }else {
                    ToastUtil.shortShow(context,"选择时间不能大于开始时间");
                }
            }else {
                ToastUtil.shortShow(context,"选择时间不能超过当前时间");
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
            case REQUEST_CODE_INCOME_STATISTICS:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_NO_PAY_STATISTICS:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
            case REQUEST_CODE_NO_DONE_STATISTICS:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }
}
