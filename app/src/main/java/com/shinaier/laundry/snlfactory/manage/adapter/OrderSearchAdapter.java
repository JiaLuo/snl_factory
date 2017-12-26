package com.shinaier.laundry.snlfactory.manage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shinaier.laundry.snlfactory.manage.ui.fragment.OrderSearchFragment;

import java.util.List;

/**
 * Created by 张家洛 on 2017/11/30.
 */

public class OrderSearchAdapter extends FragmentPagerAdapter {
    private List<OrderSearchFragment> fragmentList;
    public OrderSearchAdapter(FragmentManager fm, List<OrderSearchFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
