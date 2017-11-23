package com.shinaier.laundry.snlfactory.manage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shinaier.laundry.snlfactory.manage.ui.ManageFinanceFragment;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/10.
 */

public class ManageFinanceAdapter extends FragmentPagerAdapter {
    private List<ManageFinanceFragment> fragments;

    public ManageFinanceAdapter(FragmentManager fm, List<ManageFinanceFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
