package com.shinaier.laundry.snlfactory.manage.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.manage.ui.fragment.OrderCategoryFragment;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shizhefei.view.indicator.IndicatorViewPager;


/**
 * Created by 张家洛 on 2017/11/30.
 */

public class OrderSearchPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter{
    private final int padding;
    private String[] tabs = new String[]{"今天", "昨天" ,"3日内" ,"7日内" ,"全部"};
    private final LayoutInflater inflater;
    private OrderCategoryFragment orderCategoryFragment1;
    private OrderCategoryFragment orderCategoryFragment2;
    private OrderCategoryFragment orderCategoryFragment3;
    private OrderCategoryFragment orderCategoryFragment4;
    private OrderCategoryFragment orderCategoryFragment5;

    public OrderSearchPagerAdapter(FragmentManager fragmentManager, Context context,
                                   OrderCategoryFragment orderCategoryFragment1, OrderCategoryFragment orderCategoryFragment2,
                                   OrderCategoryFragment orderCategoryFragment3, OrderCategoryFragment orderCategoryFragment4,
                                   OrderCategoryFragment orderCategoryFragment5) {
        super(fragmentManager);
        inflater = LayoutInflater.from(context);
        padding = CommonTools.dp2px(context,20f);
        this.orderCategoryFragment1 = orderCategoryFragment1;
        this.orderCategoryFragment2 = orderCategoryFragment2;
        this.orderCategoryFragment3 = orderCategoryFragment3;
        this.orderCategoryFragment4 = orderCategoryFragment4;
        this.orderCategoryFragment5 = orderCategoryFragment5;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public View getViewForTab(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_textview, viewGroup, false);
        TextView textView = (TextView) view;
        textView.setText(tabs[i]); //名称
        textView.setPadding(padding, 0, padding, 0);
        return view;
    }

    @Override
    public Fragment getFragmentForPage(int i) {
        switch (i){
            case 0:
                return orderCategoryFragment1;
            case 1:
                return orderCategoryFragment2;
            case 2:
                return orderCategoryFragment3;
            case 3:
                return orderCategoryFragment4;
        }
        return orderCategoryFragment5;
    }
}
