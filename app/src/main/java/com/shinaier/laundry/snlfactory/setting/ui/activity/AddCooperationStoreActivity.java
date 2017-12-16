package com.shinaier.laundry.snlfactory.setting.ui.activity;

import android.os.Bundle;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;

/**
 * 合作门店
 * Created by 张家洛 on 2017/10/30.
 */

public class AddCooperationStoreActivity extends ToolBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cooperation_store_act);
        initView();
    }

    private void initView() {
        setCenterTitle("合作门店");
    }
}
