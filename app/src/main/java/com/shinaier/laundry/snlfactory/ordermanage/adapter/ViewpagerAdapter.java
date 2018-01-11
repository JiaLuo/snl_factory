package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.List;

import cn.lightsky.infiniteindicator.ViewUtils;

/**
 * Created by 张家洛 on 2018/1/8.
 */

public class ViewpagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private LayoutInflater inflater;
    private int padding;
    private String[] tabs = new String[]{"洗护项目"};
    private List<Fragment> fragmentList;

    public ViewpagerAdapter(Context context, List<Fragment> fragmentList, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
        inflater = LayoutInflater.from(context);
        padding = ViewUtils.dip2px(context, 20);
    }

    @Override
    public int getCount() {
        return 1;
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
        return fragmentList.get(i);
    }
}
