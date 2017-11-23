package com.shinaier.laundry.snlfactory.manage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shinaier.laundry.snlfactory.manage.ui.InquiryCategoryFragment;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/22.
 */

public class OrderInquiryAdapter extends FragmentPagerAdapter {
    private List<InquiryCategoryFragment> fragmentList;
    public OrderInquiryAdapter(FragmentManager fm, List<InquiryCategoryFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
