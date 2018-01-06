package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.StatisticsAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.ui.fragment.StatisticsFragment;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张家洛 on 2017/7/26.
 */

public class StatisticsActivity extends ToolBarActivity {
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.rg_statistics_category)
    private RadioGroup rgStatisticsCategory;
    @ViewInject(R.id.income_statistics)
    private RadioButton incomeStatistics; //收银统计
    @ViewInject(R.id.arrearage_statistics)
    private RadioButton arrearageStatistics; // 未付款统计
    @ViewInject(R.id.un_statement_statistics)
    private RadioButton unStatementStatistics; // 为结单统计
    @ViewInject(R.id.vp_statistics)
    private ViewPager vpStatistics;

    private List<StatisticsFragment> statisticsFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("业务统计");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StatisticsFragment statisticsFragment1 = new StatisticsFragment();
        StatisticsFragment statisticsFragment2 = new StatisticsFragment();
        StatisticsFragment statisticsFragment3 = new StatisticsFragment();
        statisticsFragment1.setArgs(this,1);
        statisticsFragment2.setArgs(this,2);
        statisticsFragment3.setArgs(this,3);
        statisticsFragments.add(statisticsFragment1);
        statisticsFragments.add(statisticsFragment2);
        statisticsFragments.add(statisticsFragment3);
        StatisticsAdapter statisticsAdapter = new StatisticsAdapter(getSupportFragmentManager(),statisticsFragments);
        vpStatistics.setAdapter(statisticsAdapter);

        rgStatisticsCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int currentItem = vpStatistics.getCurrentItem();
                switch (checkedId) {
                    case R.id.income_statistics:
                        if (currentItem != 0) {
                            vpStatistics.setCurrentItem(0);
                            changeTextColor(0);
                        }
                        break;
                    case R.id.arrearage_statistics:
                        if (currentItem != 1) {
                            vpStatistics.setCurrentItem(1);
                            changeTextColor(1);
                        }
                        break;
                    case R.id.un_statement_statistics:
                        if (currentItem != 2) {
                            vpStatistics.setCurrentItem(2);
                            changeTextColor(2);
                        }
                        break;
                }
            }
        });

        vpStatistics.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rgStatisticsCategory.check(R.id.income_statistics);
                        break;
                    case 1:
                        rgStatisticsCategory.check(R.id.arrearage_statistics);
                        break;
                    case 2:
                        rgStatisticsCategory.check(R.id.un_statement_statistics);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeTextColor(int position) {
        incomeStatistics.setTextColor(getResources().getColor(R.color.black_text));
        arrearageStatistics.setTextColor(getResources().getColor(R.color.black_text));
        unStatementStatistics.setTextColor(getResources().getColor(R.color.black_text));
        switch (position) {
            case 0:
                incomeStatistics.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 1:
                arrearageStatistics.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 2:
                unStatementStatistics.setTextColor(getResources().getColor(R.color.base_color));
                break;
        }
    }
}
