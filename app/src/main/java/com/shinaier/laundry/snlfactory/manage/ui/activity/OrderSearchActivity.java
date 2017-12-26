package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.manage.adapter.OrderSearchAdapter;
import com.shinaier.laundry.snlfactory.manage.ui.fragment.OrderSearchFragment;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * 订单查询
 * Created by 张家洛 on 2017/11/30.
 */

public class OrderSearchActivity extends ToolBarActivity {

    @ViewInject(R.id.order_search_category)
    private RadioGroup orderSearchCategory;
    @ViewInject(R.id.online_order)
    private RadioButton onlineOrder;
    @ViewInject(R.id.offline_order)
    private RadioButton offlineOrder;
    @ViewInject(R.id.vp_order_search)
    private NoScrollViewPager vpOrderSearch;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<OrderSearchFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_search_act);
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
        OrderSearchFragment orderSearchFragment1 = new OrderSearchFragment();
        OrderSearchFragment orderSearchFragment2 = new OrderSearchFragment();
        fragmentList.add(orderSearchFragment1);
        fragmentList.add(orderSearchFragment2);
        //第二个参数 true 线上订单 false 线下订单
        orderSearchFragment1.setArgs(this,true);
        orderSearchFragment2.setArgs(this,false);

        //设置viewpager不能滑动
        vpOrderSearch.setNoScroll(false);

        OrderSearchAdapter orderSearchAdapter = new OrderSearchAdapter(getSupportFragmentManager(),fragmentList);
        vpOrderSearch.setAdapter(orderSearchAdapter);

        orderSearchCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int currentItem = vpOrderSearch.getCurrentItem();
                switch (i) {
                    case R.id.online_order:
                        if (currentItem != 0) {
                            vpOrderSearch.setCurrentItem(0);
                            changeTextColor(0);
                        }
                        break;
                    case R.id.offline_order:
                        if (currentItem != 1) {
                            vpOrderSearch.setCurrentItem(1);
                            changeTextColor(1);
                        }
                        break;
                }
            }
        });
    }

    private void changeTextColor(int position) {
        onlineOrder.setTextColor(getResources().getColor(R.color.black_text));
        offlineOrder.setTextColor(getResources().getColor(R.color.black_text));
        switch (position) {
            case 0:
                onlineOrder.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 1:
                offlineOrder.setTextColor(getResources().getColor(R.color.base_color));
                break;
        }
    }
}
