package com.shinaier.laundry.snlfactory.manage.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.utils.ToastUtil;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.fragment.BaseFragment;
import com.shinaier.laundry.snlfactory.manage.adapter.OrderSearchPagerAdapter;
import com.shinaier.laundry.snlfactory.manage.ui.activity.OrderInquiryResultActivity;
import com.shinaier.laundry.snlfactory.view.ClearEditText;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import cn.lightsky.infiniteindicator.ViewUtils;



/**
 * Created by 张家洛 on 2017/11/30.
 */

public class OrderSearchFragment extends BaseFragment {
    private static final String TODAY = "today";
    private static final String YESTERDAY = "yesterday";
    private static final String ABOUTTHREEDAY = "3days";
    private static final String ABOUTWEEKDAY = "7days";
    private static final String ALL = "";

    private boolean isOnline;
    private Context context;
    private ClearEditText editSearch;

    public void setArgs(Context context, boolean isOnline) {
        this.isOnline = isOnline;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_search_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        editSearch = (ClearEditText) view.findViewById(R.id.edit_search);
        ScrollIndicatorView orderSearchDate = (ScrollIndicatorView) view.findViewById(R.id.order_search_date);
        ViewPager vpOrderSearch = (ViewPager) view.findViewById(R.id.vp_order_search);
        TextView btnStartSearch = (TextView) view.findViewById(R.id.btn_start_search);

        ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.base_color), 6,
                ScrollBar.Gravity.BOTTOM);
        colorBar.setWidth(ViewUtils.dip2px(context, 60));
        orderSearchDate.setScrollBar(colorBar);
        orderSearchDate.setSplitAuto(true);
        orderSearchDate.setOnTransitionListener(new OnTransitionTextListener().setColor(ContextCompat.getColor(context,
                R.color.base_color), ContextCompat.getColor(context, R.color.black_text)));
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(orderSearchDate, vpOrderSearch);

        OrderCategoryFragment orderCategoryFragment1 = new OrderCategoryFragment();
        OrderCategoryFragment orderCategoryFragment2 = new OrderCategoryFragment();
        OrderCategoryFragment orderCategoryFragment3 = new OrderCategoryFragment();
        OrderCategoryFragment orderCategoryFragment4 = new OrderCategoryFragment();
        OrderCategoryFragment orderCategoryFragment5 = new OrderCategoryFragment();
        orderCategoryFragment1.setArgs(isOnline,TODAY);
        orderCategoryFragment2.setArgs(isOnline,YESTERDAY);
        orderCategoryFragment3.setArgs(isOnline,ABOUTTHREEDAY);
        orderCategoryFragment4.setArgs(isOnline,ABOUTWEEKDAY);
        orderCategoryFragment5.setArgs(isOnline,ALL);

        OrderSearchPagerAdapter orderSearchPagerAdapter = new OrderSearchPagerAdapter(getChildFragmentManager(),context, orderCategoryFragment1,
                orderCategoryFragment2, orderCategoryFragment3, orderCategoryFragment4, orderCategoryFragment5);
        indicatorViewPager.setAdapter(orderSearchPagerAdapter);

        btnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OrderInquiryResultActivity.class);
                String orderNum = editSearch.getText().toString();
                if (!TextUtils.isEmpty(orderNum)){
                    intent.putExtra("orderNum",orderNum);
                    intent.putExtra("is_online",isOnline);
                    intent.putExtra("date",ALL);
                    startActivity(intent);
                }else {
                    ToastUtil.shortShow(context,"请输入查询订单号");
                }
            }

        });
    }
}
