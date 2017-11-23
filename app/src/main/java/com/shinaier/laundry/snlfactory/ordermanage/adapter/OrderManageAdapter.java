package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shinaier.laundry.snlfactory.ordermanage.ui.OrderCategoryFragment;

import java.util.List;


/**
 * Created by 张家洛 on 2017/2/24.
 */

public class OrderManageAdapter extends FragmentPagerAdapter {
    private List<OrderCategoryFragment> fragments;
    public OrderManageAdapter(FragmentManager fm, List<OrderCategoryFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
