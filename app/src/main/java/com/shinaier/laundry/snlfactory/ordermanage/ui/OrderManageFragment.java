package com.shinaier.laundry.snlfactory.ordermanage.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.BaseFragment;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.OrderManageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张家洛 on 2017/10/25.
 */

public class OrderManageFragment extends BaseFragment {
    private static final int WILLDISPOSE = 0;
    private static final int WILLTAKEORDER = 1;
    private static final int WILLCLEAN = 2;
    private static final int WILLCLEANING = 3;
    private static final int WILLSEND = 4;

    private Context context;
    private View view;
    private List<OrderCategoryFragment> orderCategoryFragments = new ArrayList<>();
    private ViewPager orderManage;
    private RadioButton orderManageWillDispose;
    private RadioButton orderManageWillTakeOrder;
    private RadioButton orderManageWillClean;
    private RadioButton orderManageWillCleaning;
    private RadioButton orderManageWillSend;
    private RadioGroup orderManageCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_manage_frag, null);
        context = getActivity();
        initView();
        return view;
    }

    private void initView() {
        orderManageCategory = (RadioGroup) view.findViewById(R.id.order_manage_category);
        orderManageWillDispose = (RadioButton) view.findViewById(R.id.order_manage_will_dispose);
        orderManageWillTakeOrder = (RadioButton) view.findViewById(R.id.order_manage_will_take_order);
        orderManageWillClean = (RadioButton) view.findViewById(R.id.order_manage_will_clean);
        orderManageWillCleaning = (RadioButton) view.findViewById(R.id.order_manage_will_cleaning);
        orderManageWillSend = (RadioButton) view.findViewById(R.id.order_manage_will_send);
        orderManage = (ViewPager) view.findViewById(R.id.order_manage);
        OrderCategoryFragment orderCategoryFragment1 = new OrderCategoryFragment();
        OrderCategoryFragment orderCategoryFragment2 = new OrderCategoryFragment();
        OrderCategoryFragment orderCategoryFragment3 = new OrderCategoryFragment();
        OrderCategoryFragment orderCategoryFragment4 = new OrderCategoryFragment();
        OrderCategoryFragment orderCategoryFragment5 = new OrderCategoryFragment();
        orderCategoryFragment1.setArgs(context,WILLDISPOSE);
        orderCategoryFragment2.setArgs(context,WILLTAKEORDER);
        orderCategoryFragment3.setArgs(context,WILLCLEAN);
        orderCategoryFragment4.setArgs(context,WILLCLEANING);
        orderCategoryFragment5.setArgs(context,WILLSEND);
        orderCategoryFragments.add(orderCategoryFragment1);
        orderCategoryFragments.add(orderCategoryFragment2);
        orderCategoryFragments.add(orderCategoryFragment3);
        orderCategoryFragments.add(orderCategoryFragment4);
        orderCategoryFragments.add(orderCategoryFragment5);
        OrderManageAdapter orderManageAdapter = new OrderManageAdapter(getChildFragmentManager(),orderCategoryFragments);
        orderManage.setAdapter(orderManageAdapter);
        orderManageCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int currentItem = orderManage.getCurrentItem();
                switch (i) {
                    case R.id.order_manage_will_dispose:
                        if (currentItem != 0) {
                            orderManage.setCurrentItem(0);
                            changeTextColor(0);
                        }
                        break;
                    case R.id.order_manage_will_take_order:
                        if (currentItem != 1) {
                            orderManage.setCurrentItem(1);
                            changeTextColor(1);
                        }
                        break;
                    case R.id.order_manage_will_clean:
                        if (currentItem != 2) {
                            orderManage.setCurrentItem(2);
                            changeTextColor(2);
                        }
                        break;
                    case R.id.order_manage_will_cleaning:
                        if (currentItem != 3) {
                            orderManage.setCurrentItem(3);
                            changeTextColor(3);
                        }
                        break;
                    case R.id.order_manage_will_send:
                        if (currentItem != 4) {
                            orderManage.setCurrentItem(4);
                            changeTextColor(4);
                        }
                        break;
                }
            }

        });
        orderManage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        orderManageCategory.check(R.id.order_manage_will_dispose);
                        break;
                    case 1:
                        orderManageCategory.check(R.id.order_manage_will_take_order);
                        break;
                    case 2:
                        orderManageCategory.check(R.id.order_manage_will_clean);
                        break;
                    case 3:
                        orderManageCategory.check(R.id.order_manage_will_cleaning);
                        break;
                    case 4:
                        orderManageCategory.check(R.id.order_manage_will_send);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void changeTextColor(int position) {
        orderManageWillDispose.setTextColor(getResources().getColor(R.color.black_text));
        orderManageWillTakeOrder.setTextColor(getResources().getColor(R.color.black_text));
        orderManageWillClean.setTextColor(getResources().getColor(R.color.black_text));
        orderManageWillCleaning.setTextColor(getResources().getColor(R.color.black_text));
        orderManageWillSend.setTextColor(getResources().getColor(R.color.black_text));
        switch (position) {
            case 0:
                orderManageWillDispose.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 1:
                orderManageWillTakeOrder.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 2:
                orderManageWillClean.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 3:
                orderManageWillCleaning.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 4:
                orderManageWillSend.setTextColor(getResources().getColor(R.color.base_color));
                break;
        }
    }
}
