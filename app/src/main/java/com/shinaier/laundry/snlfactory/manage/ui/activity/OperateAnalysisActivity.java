package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.network.FProtocol;
import com.common.utils.DeviceUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.OperateAnalysisEntities;
import com.shinaier.laundry.snlfactory.network.entity.OptionEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CheckSpinnerView;
import com.shinaier.laundry.snlfactory.view.linechartview.DefaultValueAdapter;
import com.shinaier.laundry.snlfactory.view.linechartview.Entry;
import com.shinaier.laundry.snlfactory.view.linechartview.HighLight;
import com.shinaier.laundry.snlfactory.view.linechartview.Line;
import com.shinaier.laundry.snlfactory.view.linechartview.LineChart;
import com.shinaier.laundry.snlfactory.view.linechartview.Lines;
import com.shinaier.laundry.snlfactory.view.linechartview.Utils;
import com.shinaier.laundry.snlfactory.view.linechartview.XAxis;
import com.shinaier.laundry.snlfactory.view.linechartview.YAxis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * 经营分析
 * Created by 张家洛 on 2017/9/12.
 */

public class OperateAnalysisActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_OPERATE_ANALYSIS = 0x1;

    @ViewInject(R.id.analysis_line)
    private LineChart analysisLine;
    @ViewInject(R.id.rl_analysis_time)
    private RelativeLayout rlAnalysisTime;
    @ViewInject(R.id.analysis_time)
    private TextView analysisTime;
    @ViewInject(R.id.business_volume_this_month)
    private TextView businessVolumeThisMonth;
    @ViewInject(R.id.business_volume_last_month)
    private TextView businessVolumeLastMonth;
    @ViewInject(R.id.this_month_total_business_volume)
    private TextView thisMonthTotalBusinessVolume;
    @ViewInject(R.id.last_month_total_business_volume)
    private TextView lastMonthTotalBusinessVolume;
    @ViewInject(R.id.amplitude_business_volume)
    private TextView amplitudeBusinessVolume;
    @ViewInject(R.id.this_month_total_order)
    private TextView thisMonthTotalOrder;
    @ViewInject(R.id.last_month_total_order)
    private TextView lastMonthTotalOrder;
    @ViewInject(R.id.amplitude_total_order)
    private TextView amplitudeTotalOrder;
    @ViewInject(R.id.this_month_cancel_order)
    private TextView thisMonthCancelOrder;
    @ViewInject(R.id.last_month_cancel_order)
    private TextView lastMonthCancelOrder;
    @ViewInject(R.id.amplitude_cancel_order)
    private TextView amplitudeCancelOrder;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private int orderByPosition;
    private List<OptionEntity> optionEntities = new ArrayList<>();
    Line.CallBack_OnEntryClick onEntryClick = new Line.CallBack_OnEntryClick() {
        @Override
        public void onEntry(Line line, Entry entry) { //线的点击事件
            String s = String.valueOf(entry.getX());
                Toast.makeText(OperateAnalysisActivity.this, line.getName()+ s.substring(0,s.indexOf(".")) + "号"  + "    \r\n"+ "￥:" + entry.getY(), Toast.LENGTH_SHORT).show();
        }
    };
    private OperateAnalysisEntities operateAnalysisEntities;
    private CheckSpinnerView mCheckSpinnerView;
    private TranslateAnimation animation;
    private int lineNum = 2;//显示几条线
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operate_analysis_act);
        ViewInjectUtils.inject(this);
        loadData("");
        initView();
    }

    private void initView() {
        setCenterTitle("经营分析");
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        analysisTime.setText(getDateToString(timeInMillis));
        rlAnalysisTime.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        mCheckSpinnerView = new CheckSpinnerView(this, new CheckSpinnerView.OnSpinnerItemClickListener() {

            @Override
            public void onItemClickListener1(AdapterView<?> parent, View view, int position, long id) {
                orderByPosition = position;
                analysisTime.setText(operateAnalysisEntities.getResult().getMonarr().get(position));
                mCheckSpinnerView.close();
                loadData(operateAnalysisEntities.getResult().getMonarr().get(position));
            }
        });
        animation = new TranslateAnimation(0, 0, -(DeviceUtil.getHeight(this)), 0);
        animation.setDuration(100);
    }
    /**
     *  请求数据
     */

    private void loadData(String isSelectMonth) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        if (!TextUtils.isEmpty(isSelectMonth)){
            params.put("time",isSelectMonth);
        }
        params.put("type","1");
        requestHttpData(Constants.Urls.URL_POST_OPERATE_ANALYSIS,REQUEST_CODE_OPERATE_ANALYSIS, FProtocol.HttpMethod.POST,params);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_OPERATE_ANALYSIS:
                if (data != null){
                    operateAnalysisEntities = Parsers.getOperateAnalysisEntities(data);
                    if (operateAnalysisEntities != null){
                        if (operateAnalysisEntities.getCode() == 0){
                            setChartData(analysisLine);

                            setOtherData();

                        }else {
                            ToastUtil.shortShow(this, operateAnalysisEntities.getMsg());
                        }
                    }
                }
                break;
        }
    }

    private void setOtherData() {
        //给checkview 设置数据
        OptionEntity optionEntity = null;
        for (int i = 0; i < operateAnalysisEntities.getResult().getMonarr().size(); i++) {
            String s = operateAnalysisEntities.getResult().getMonarr().get(i);
            optionEntity = new OptionEntity();
            optionEntity.setName(s);
            optionEntities.add(optionEntity);
        }
        String thisMonth = operateAnalysisEntities.getResult().getNowBusinessTotal();
        String lastMonth = operateAnalysisEntities.getResult().getBeforeBusinessTotal();
        businessVolumeLastMonth.setText("上月同期营业额:" + lastMonth);
        businessVolumeThisMonth.setText("截止目前营业额:" + thisMonth);
        thisMonthTotalBusinessVolume.setText(String.valueOf(operateAnalysisEntities.getResult().getNowInfo().getBusinessTotal()));
        thisMonthTotalOrder.setText(String.valueOf(operateAnalysisEntities.getResult().getNowInfo().getItemTotal()));
        thisMonthCancelOrder.setText(String.valueOf(operateAnalysisEntities.getResult().getNowInfo().getCancelItemTotal()));
        lastMonthTotalBusinessVolume.setText(String.valueOf(operateAnalysisEntities.getResult().getLastInfo().getBusinessTotal()));
        lastMonthTotalOrder.setText(String.valueOf(operateAnalysisEntities.getResult().getLastInfo().getItemTotal()));
        lastMonthCancelOrder.setText(String.valueOf(operateAnalysisEntities.getResult().getLastInfo().getCancelItemTotal()));
        amplitudeBusinessVolume.setText(operateAnalysisEntities.getResult().getIncrease().getBusinessTotal() + "%");
        amplitudeTotalOrder.setText(operateAnalysisEntities.getResult().getIncrease().getItemTotal() + "%");
        amplitudeCancelOrder.setText(operateAnalysisEntities.getResult().getIncrease().getCancelItemTotal() + "%");
        if (operateAnalysisEntities.getResult().getNowInfo().getBusinessTotal() > operateAnalysisEntities.getResult().getLastInfo().getBusinessTotal()){
            amplitudeBusinessVolume.setTextColor(Color.parseColor("#eb381b"));
        }else {
            amplitudeBusinessVolume.setTextColor(Color.parseColor("#09b1b0"));
        }

        if (operateAnalysisEntities.getResult().getNowInfo().getItemTotal() > operateAnalysisEntities.getResult().getLastInfo().getItemTotal()){
            amplitudeTotalOrder.setTextColor(Color.parseColor("#eb381b"));
        }else{
            amplitudeTotalOrder.setTextColor(Color.parseColor("#09b1b0"));
        }

        if (operateAnalysisEntities.getResult().getNowInfo().getCancelItemTotal() > operateAnalysisEntities.getResult().getLastInfo().getCancelItemTotal()){
            amplitudeCancelOrder.setTextColor(Color.parseColor("#eb381b"));
        }else{
            amplitudeCancelOrder.setTextColor(Color.parseColor("#09b1b0"));
        }
    }

    /**
     * 曲线图初始化
     * @param lineChart 曲线图的对象
     */
    private void setChartData(LineChart lineChart) {
        // 高亮
        HighLight highLight = lineChart.get_HighLight();
        lineChart.setCanX_zoom(false);
        lineChart.setCanY_zoom(false);
        highLight.setEnable(true);// 启用高亮显示  默认为启用状态，每条折线图想要获取点击回调，highlight需要启用

        // x,y轴上的单位
        XAxis xAxis = lineChart.get_XAxis();
        xAxis.set_unit("日");
        xAxis.set_ValueAdapter(new DefaultValueAdapter(0));

        YAxis yAxis = lineChart.get_YAxis();
        yAxis.set_unit("元");
        yAxis.set_ValueAdapter(new DefaultValueAdapter(0));

        Lines lines = new Lines();
        for (int i = 0; i < lineNum; i++) {

            // 线的颜色
            int color = Color.argb(255,
                    (new Double(Math.random() * 256)).intValue(),
                    (new Double(Math.random() * 256)).intValue(),
                    (new Double(Math.random() * 256)).intValue());

            Line line = createLine(i, color);
            lines.addLine(line);
        }
        analysisLine.setLines(lines);
        analysisLine.animateXY(); //添加动画
//                            analysisLine.setYMax_Min(1000,6000);
//                            analysisLine.setXAix_MaxMin(2, operateAnalysisEntities.getDatas().getDays().size());
//                            analysisLine.notifyDataChanged();
    }

    /**
     *  创建曲线
     * @param order 哪一条线
     * @param color 颜色
     * @return 曲线的对象
     */
    private Line createLine(int order, int color) {
        final Line line = new Line();
        List<Entry> list = new ArrayList<>();
        int nowMonthSize = operateAnalysisEntities.getResult().getNowMonths().size();
        int lastMonthSize = operateAnalysisEntities.getResult().getLastMonthData().size();
        if (order == 0){
            for (int i = 0; i < nowMonthSize; i++) {
                String s = operateAnalysisEntities.getResult().getNowMonths().get(i).getDay();
                double v = Double.parseDouble(s);
                String s1 = operateAnalysisEntities.getResult().getNowMonths().get(i).getTotal(); //本月数据
                double v1 = Double.parseDouble(s1);
                list.add(new Entry(v, v1));
            }
        }else {
            for (int i = 0; i < lastMonthSize; i++) {
                String s = operateAnalysisEntities.getResult().getLastMonthData().get(i).getDay();
                double v = Double.parseDouble(s);
                String s1 = operateAnalysisEntities.getResult().getLastMonthData().get(i).getTotal(); //上月数据
                double v1 = Double.parseDouble(s1);
                list.add(new Entry(v, v1));
            }
        }


        line.setEntries(list);
        line.setDrawLegend(true);//设置启用绘制图例
        line.setLegendWidth((int) Utils.dp2px(50));//设置图例的宽
//        line.setLegendHeight((int)Utils.dp2px(60));//设置图例的高
//        line.setLegendTextSize((int)Utils.dp2px(19));//设置图例上的字体大小
        if (order == 0){
            line.setName("本月");
        }else {
            line.setName("上月:");
        }
        line.setLineColor(color);
        line.setCircleColor(color);
        line.setOnEntryClick(onEntryClick);

        return line;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_analysis_time:
                mCheckSpinnerView.showSpinnerPop(rlAnalysisTime, animation, optionEntities, orderByPosition);
                break;
            case R.id.left_button:
                finish();
                break;
        }
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
