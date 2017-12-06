package com.shinaier.laundry.snlfactory.manage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.manage.adapter.OrderInquiryAdapter;
import com.shinaier.laundry.snlfactory.manage.fragment.InquiryCategoryFragment;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张家洛 on 2017/7/18.
 */

public class OrderInquiryActivity extends ToolBarActivity {
    private static final int TODAY = 1;
    private static final int YESTERDAY = 2;
    private static final int ABOUTTHREEDAY = 3;
    private static final int ABOUTWEEKDAY = 4;
    private static final int ALL = 5;

    @ViewInject(R.id.edit_search)
    private ClearEditText editSearch;
    @ViewInject(R.id.btn_start_search)
    private TextView btnStartSearch;
    @ViewInject(R.id.order_inquiry_category)
    private RadioGroup orderInquiryCategory;
    @ViewInject(R.id.order_inquiry_today)
    private RadioButton orderInquiryToday;
    @ViewInject(R.id.order_inquiry_yesterday)
    private RadioButton orderInquiryYesterday;
    @ViewInject(R.id.order_inquiry_about_three_day)
    private RadioButton orderInquiryAboutThreeDay;
    @ViewInject(R.id.order_inquiry_about_week)
    private RadioButton orderInquiryAboutWeek;
    @ViewInject(R.id.order_inquiry_all)
    private RadioButton orderInquiryAll;
    @ViewInject(R.id.order_inquiry)
    private ViewPager orderInquiry;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<InquiryCategoryFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_inquiry_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("订单查询");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        InquiryCategoryFragment inquiryCategoryFragment1 = new InquiryCategoryFragment();
        InquiryCategoryFragment inquiryCategoryFragment2 = new InquiryCategoryFragment();
        InquiryCategoryFragment inquiryCategoryFragment3 = new InquiryCategoryFragment();
        InquiryCategoryFragment inquiryCategoryFragment4 = new InquiryCategoryFragment();
        InquiryCategoryFragment inquiryCategoryFragment5 = new InquiryCategoryFragment();
        fragmentList.add(inquiryCategoryFragment1);
        fragmentList.add(inquiryCategoryFragment2);
        fragmentList.add(inquiryCategoryFragment3);
        fragmentList.add(inquiryCategoryFragment4);
        fragmentList.add(inquiryCategoryFragment5);
        inquiryCategoryFragment1.setArgs(this,TODAY);
        inquiryCategoryFragment2.setArgs(this,YESTERDAY);
        inquiryCategoryFragment3.setArgs(this,ABOUTTHREEDAY);
        inquiryCategoryFragment4.setArgs(this,ABOUTWEEKDAY);
        inquiryCategoryFragment5.setArgs(this,ALL);
        OrderInquiryAdapter orderInquiryAdapter = new OrderInquiryAdapter(getSupportFragmentManager(),fragmentList);
        orderInquiry.setAdapter(orderInquiryAdapter);
        orderInquiryCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int currentItem = orderInquiry.getCurrentItem();
                switch (i) {
                    case R.id.order_inquiry_today:
                        if (currentItem != 0) {
                            orderInquiry.setCurrentItem(0);
                            changeTextColor(0);
                        }
                        break;
                    case R.id.order_inquiry_yesterday:
                        if (currentItem != 1) {
                            orderInquiry.setCurrentItem(1);
                            changeTextColor(1);
                        }
                        break;
                    case R.id.order_inquiry_about_three_day:
                        if (currentItem != 2) {
                            orderInquiry.setCurrentItem(2);
                            changeTextColor(2);
                        }
                        break;
                    case R.id.order_inquiry_about_week:
                        if (currentItem != 3) {
                            orderInquiry.setCurrentItem(3);
                            changeTextColor(3);
                        }
                        break;
                    case R.id.order_inquiry_all:
                        if (currentItem != 4) {
                            orderInquiry.setCurrentItem(4);
                            changeTextColor(4);
                        }
                        break;
                }
            }
        });


        orderInquiry.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        orderInquiryCategory.check(R.id.order_inquiry_today);
                        break;
                    case 1:
                        orderInquiryCategory.check(R.id.order_inquiry_yesterday);
                        break;
                    case 2:
                        orderInquiryCategory.check(R.id.order_inquiry_about_three_day);
                        break;
                    case 3:
                        orderInquiryCategory.check(R.id.order_inquiry_about_week);
                        break;
                    case 4:
                        orderInquiryCategory.check(R.id.order_inquiry_all);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInquiryActivity.this,OrderInquiryResultActivity.class);
                String orderNum = editSearch.getText().toString();
                intent.putExtra("orderNum",orderNum);
                startActivity(intent);
            }

        });
    }

    private void changeTextColor(int position) {
        orderInquiryToday.setTextColor(getResources().getColor(R.color.black_text));
        orderInquiryYesterday.setTextColor(getResources().getColor(R.color.black_text));
        orderInquiryAboutThreeDay.setTextColor(getResources().getColor(R.color.black_text));
        orderInquiryAboutWeek.setTextColor(getResources().getColor(R.color.black_text));
        orderInquiryAll.setTextColor(getResources().getColor(R.color.black_text));
        switch (position) {
            case 0:
                orderInquiryToday.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 1:
                orderInquiryYesterday.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 2:
                orderInquiryAboutThreeDay.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 3:
                orderInquiryAboutWeek.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 4:
                orderInquiryAll.setTextColor(getResources().getColor(R.color.base_color));
                break;
        }
    }
}
