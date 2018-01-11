package com.shinaier.laundry.snlfactory.ordermanage.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.network.entity.StoreDetailEntity;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CategoryAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.GoodsAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.entities.GoodsBean;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.AddProjectsActivity;
import com.shinaier.laundry.snlfactory.ordermanage.view.AddWidget;
import com.shinaier.laundry.snlfactory.ordermanage.view.SimpleDividerDecoration;

import java.util.ArrayList;
import java.util.List;

public class GoodsFragment extends Fragment {

    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView2;
    private GridLayoutManager gridLayoutManager;
    private boolean move;
    private int index;
    private GoodsAdapter goodsAdapter;
    private TextView tvStickyHeaderView;
    private View stickView;
    private View view;
    private List<StoreDetailEntity.StoreDetailResult> results;
    private ArrayList<GoodsBean> goodsBeanList;
    private Context context;

    public void initData(List<StoreDetailEntity.StoreDetailResult> results, ArrayList<GoodsBean> goodsBeans) {
        this.results = results;
        this.goodsBeanList = goodsBeans;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods, container, false);
        context = getActivity();
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryAdapter = new CategoryAdapter(results, recyclerView1, getActivity());
        recyclerView1.setAdapter(categoryAdapter);
        recyclerView1.addItemDecoration(new SimpleDividerDecoration(getActivity()));
        ((DefaultItemAnimator) recyclerView1.getItemAnimator()).setSupportsChangeAnimations(false);

        categoryAdapter.setMyItemClickListener(new CategoryAdapter.OnMyItemClickListener() {
            @Override
            public void onMyItemClick(StoreDetailEntity.StoreDetailResult result, int position) {
                if (recyclerView2.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
//                    categoryAdapter.fromClick = true;
//                    categoryAdapter.setChecked(position);
                    for (int ii = 0; ii < goodsBeanList.size(); ii++) {
                        if (goodsBeanList.get(ii).getTypeName().equals(result.getCate_name())) {
//                            index = ii;
                            moveToPosition(ii);
                            categoryAdapter.moveToPosition(position);
                            break;
                        }
                    }
                }
            }
        });

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler2);
        ((DefaultItemAnimator) recyclerView2.getItemAnimator()).setSupportsChangeAnimations(false);
        setAddClick((AddProjectsActivity) getActivity());
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
//        int firstItem = gridLayoutManager.findFirstVisibleItemPosition();
//        int lastItem = gridLayoutManager.findLastVisibleItemPosition();
//        //然后区分情况
//        if (n <= firstItem) {
//            //当要置顶的项在当前显示的第一个项的前面时
//            recyclerView2.scrollToPosition(n);
//        } else if (n <= lastItem) {
//            //当要置顶的项已经在屏幕上显示时
//            int top = recyclerView2.getChildAt(n - firstItem).getTop();
//            recyclerView2.scrollBy(0, top);
//        } else {
//            //当要置顶的项在当前显示的最后一项的后面时
//            recyclerView2.scrollToPosition(n);
//            //这里这个变量是用在RecyclerView滚动监听里面的
//            move = true;
//        }
        gridLayoutManager.scrollToPositionWithOffset(n,0);
    }

    public void setAddClick(AddWidget.OnAddClick onAddClick) {

        goodsAdapter = new GoodsAdapter(context,goodsBeanList, onAddClick);
        stickView = view.findViewById(R.id.stick_header);
        tvStickyHeaderView = (TextView) view.findViewById(R.id.tv_header);
        tvStickyHeaderView.setText(results.get(0).getCate_name());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (goodsBeanList.get(position).getType() == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recyclerView2.setLayoutManager(gridLayoutManager);
        recyclerView2.setAdapter(goodsAdapter);
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int position = gridLayoutManager.findFirstVisibleItemPosition();
                for (int i = 0; i < results.size(); i++) {
                    if (goodsBeanList.get(position).getTypeName().equals(results.get(i).getCate_name())){
                        categoryAdapter.moveToPosition(i);
                        tvStickyHeaderView.setText(results.get(i).getCate_name());
                    }
                }
            }
        });



        goodsAdapter.setGoodsItemClickListener(new GoodsAdapter.OnGoodsItemClickListener() {
            @Override
            public void onGoodsItemClick(View view, AddWidget.OnAddClick onMyAddClick, GoodsBean goodsBean, int position) {
                goodsBean.setSelectCount(goodsBean.getSelectCount() + 1);
                onMyAddClick.onAddClick(view, goodsBean);

            }
        });
    }

    public GoodsAdapter getFoodAdapter() {
        return goodsAdapter;
    }

    public CategoryAdapter getTypeAdapter() {
        return categoryAdapter;
    }
}
