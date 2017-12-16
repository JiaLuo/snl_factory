package com.shinaier.laundry.snlfactory.ordermanage.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.base.fragment.BaseFragment;


/**
 * Created by 张家洛 on 2017/12/16.
 */

public class OrderManageWillCleaningFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("清洗中");
        return textView;
    }
}
