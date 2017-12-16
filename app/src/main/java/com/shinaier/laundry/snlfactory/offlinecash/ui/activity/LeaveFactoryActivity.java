package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.utils.DeviceUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.network.entity.OptionEntity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CheckSpinnerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张家洛 on 2017/10/28.
 */

public class LeaveFactoryActivity extends ToolBarActivity implements View.OnClickListener {
    @ViewInject(R.id.rl_choose_store)
    private RelativeLayout rlChooseStore;
    @ViewInject(R.id.choose_store_name)
    private TextView chooseStoreName;

    private CheckSpinnerView mCheckSpinnerView;
    private int orderByPosition;
    private TranslateAnimation animation;

    private List<OptionEntity> optionEntities = new ArrayList<>();
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_factoty_act);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        setCenterTitle("出厂");
        rlChooseStore.setOnClickListener(this);
        rlChooseStore.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = rlChooseStore.getWidth();
            }
        });
        OptionEntity optionEntity1 = new OptionEntity();
        optionEntity1.setName("如家");
        OptionEntity optionEntity2 = new OptionEntity();
        optionEntity2.setName("大望路");
        OptionEntity optionEntity3 = new OptionEntity();
        optionEntity3.setName("万达");
        OptionEntity optionEntity4 = new OptionEntity();
        optionEntity4.setName("建国门");
        OptionEntity optionEntity5 = new OptionEntity();
        optionEntity5.setName("啊啊啊");
        optionEntities.add(optionEntity1);
        optionEntities.add(optionEntity2);
        optionEntities.add(optionEntity3);
        optionEntities.add(optionEntity4);
        optionEntities.add(optionEntity5);
        mCheckSpinnerView = new CheckSpinnerView(this, new CheckSpinnerView.OnSpinnerItemClickListener() {

            @Override
            public void onItemClickListener1(AdapterView<?> parent, View view, int position, long id) {
                orderByPosition = position;
                chooseStoreName.setText(optionEntities.get(position).getName());
                mCheckSpinnerView.close();
//                loadData(operateAnalysisEntities.getDatas().getDates().get(position));
            }
        });
        animation = new TranslateAnimation(0, 0, -(DeviceUtil.getHeight(this)), 0);
        animation.setDuration(100);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_choose_store:
                mCheckSpinnerView.showSpinnerPop(rlChooseStore, animation, optionEntities, orderByPosition,width);
                break;
        }
    }
}
