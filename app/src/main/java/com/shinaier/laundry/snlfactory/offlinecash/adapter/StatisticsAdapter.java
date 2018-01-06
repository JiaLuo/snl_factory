package com.shinaier.laundry.snlfactory.offlinecash.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shinaier.laundry.snlfactory.offlinecash.ui.fragment.StatisticsFragment;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/26.
 */

public class StatisticsAdapter extends FragmentPagerAdapter {
    private List<StatisticsFragment> statisticsFragments;
    public StatisticsAdapter(FragmentManager fm, List<StatisticsFragment> statisticsFragments) {
        super(fm);
        this.statisticsFragments = statisticsFragments;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public Fragment getItem(int position) {
        return statisticsFragments.get(position);
    }
}
