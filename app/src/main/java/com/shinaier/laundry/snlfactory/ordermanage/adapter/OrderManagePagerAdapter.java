package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.ordermanage.ui.fragment.OrderManageWillCleanFragment;
import com.shinaier.laundry.snlfactory.ordermanage.ui.fragment.OrderManageWillCleaningFragment;
import com.shinaier.laundry.snlfactory.ordermanage.ui.fragment.OrderManageWillDisposeFragment;
import com.shinaier.laundry.snlfactory.ordermanage.ui.fragment.OrderManageWillSendFragment;
import com.shinaier.laundry.snlfactory.ordermanage.ui.fragment.OrderManageWillTakeOrderFragment;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shizhefei.view.indicator.IndicatorViewPager;

/**
 * Created by 张家洛 on 2017/11/30.
 */

public class OrderManagePagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter{
    private Context context;
    private final int padding;
    private String[] tabs = new String[]{"待处理", "待收件" ,"待清洗" ,"清洗中" ,"待送达"};
    private final LayoutInflater inflater;
    private OrderManageWillDisposeFragment orderManageWillDisposeFragment;
    private OrderManageWillTakeOrderFragment orderManageWillTakeOrderFragment;
    private OrderManageWillCleanFragment orderManageWillCleanFragment;
    private OrderManageWillCleaningFragment orderManageWillCleaningFragment;
    private OrderManageWillSendFragment orderManageWillSendFragment;

    public OrderManagePagerAdapter(FragmentManager fragmentManager, Context context,
                                   OrderManageWillDisposeFragment orderManageWillDisposeFragment, OrderManageWillTakeOrderFragment orderManageWillTakeOrderFragment,
                                   OrderManageWillCleanFragment orderManageWillCleanFragment, OrderManageWillCleaningFragment orderManageWillCleaningFragment,
                                   OrderManageWillSendFragment orderManageWillSendFragment) {
        super(fragmentManager);
        this.context = context;
        inflater = LayoutInflater.from(context);
        padding = CommonTools.dp2px(context,20f);
        this.orderManageWillDisposeFragment = orderManageWillDisposeFragment;
        this.orderManageWillTakeOrderFragment = orderManageWillTakeOrderFragment;
        this.orderManageWillCleanFragment = orderManageWillCleanFragment;
        this.orderManageWillCleaningFragment = orderManageWillCleaningFragment;
        this.orderManageWillSendFragment = orderManageWillSendFragment;
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
                return orderManageWillDisposeFragment;
            case 1:
                return orderManageWillTakeOrderFragment;
            case 2:
                return orderManageWillCleanFragment;
            case 3:
                return orderManageWillCleaningFragment;
        }
        return orderManageWillSendFragment;
    }
}
